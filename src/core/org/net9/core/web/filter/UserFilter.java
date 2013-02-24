package org.net9.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.common.web.filter.BaseFilter;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebPageVarConstant;
import org.net9.core.service.SystemService;
import org.net9.core.service.UserExtService;
import org.net9.core.service.UserService;
import org.net9.core.types.MessageBoxType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.SysBlacklist;

import com.google.inject.Guice;
import com.google.inject.servlet.ServletModule;

/**
 * user security filter
 * 
 * @author gladstone
 * 
 */
public class UserFilter extends BaseFilter {

	/** Logger */
	private static Log log = LogFactory.getLog(UserFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String username = UserHelper.getuserFromCookie(httpRequest);

		String authType = SystemConfigUtils.getProperty("user.auth.type");
		if ("session".equalsIgnoreCase(authType)) {
			log.debug("use the session auth type");
			username = (String) httpRequest.getSession().getAttribute(
					BusinessConstants.USER_NAME);
		}

		httpRequest.setAttribute(WebPageVarConstant.USERNAME, StringUtils
				.isEmpty(username) ? null : username);

		if (StringUtils.isNotEmpty(username)) {
			UserService userService = Guice.createInjector(new ServletModule())
					.getInstance(UserService.class);
			UserExtService userExtService = Guice.createInjector(
					new ServletModule()).getInstance(UserExtService.class);
			Integer msgCnt = userService.getMsgsCntByUser(username,
					MessageBoxType.MSG_TYPE_RECEIVE.getValue(), true, null);
			httpRequest.setAttribute(WebPageVarConstant.MSG_CNT, msgCnt);
			Integer noticeCnt = userExtService.getNoticeCnt(username, 0, null);
			log.debug("noticeCnt: " + noticeCnt);
			httpRequest.setAttribute(WebPageVarConstant.NOTICE_CNT, noticeCnt);

			httpRequest.setAttribute(WebPageVarConstant.USER_IS_EDITOR,
					userService.isEditor(username));
		} else {
			// #840 (系统管理员的权限)
			httpRequest.setAttribute(WebPageVarConstant.USER_IS_EDITOR, false);
		}

		SystemService systemService = Guice.createInjector(new ServletModule())
				.getInstance(SystemService.class);

		// 禁止已经被全站封禁的用户
		if (!HttpUtils.isInManageScope(httpRequest)) {
			// #630 (增加封禁IP功能)
			String ip = HttpUtils.getIpAddr(httpRequest);
			SysBlacklist black = systemService.getSysBlacklistByIp(ip);
			// 如果不是IP封禁，根据用户查找
			if (black == null && StringUtils.isNotEmpty(username)) {
				black = systemService.getSysBlacklistByUsername(username);
			}
			if (black != null) {
				httpRequest.setAttribute(BusinessConstants.ERROR_KEY,
						I18nMsgUtils.getInstance().getMessage("user.isBlack"));
				RequestDispatcher rd = httpRequest
						.getRequestDispatcher("/common/forbidden.jsp");
				try {
					rd.forward(httpRequest, httpResponse);
					return;
				} catch (ServletException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}

		try {
			chain.doFilter(request, response);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (ServletException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
