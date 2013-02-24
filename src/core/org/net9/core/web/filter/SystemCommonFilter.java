package org.net9.core.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CookieUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.filter.BaseFilter;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.service.CommonService;
import org.net9.core.service.ServiceModule;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.OnlineInfoKeeper;
import org.net9.domain.model.core.Online;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * system common filter
 * 
 * @author gladstone
 * @since Feb 15, 2009
 */
public class SystemCommonFilter extends BaseFilter {

	public SystemCommonFilter() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	/** Logger */
	private static Log log = LogFactory.getLog(SystemCommonFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		this.checkOnlines(httpRequest);
		request.setAttribute("onlineCntOfAll", OnlineInfoKeeper.getInstance()
				.getOnLineCount());

		Cookie[] cookies = httpRequest.getCookies();
		Cookie themeCookie = CookieUtils.getCookie(cookies,
				BusinessConstants.COOKIE_KEY_THEME);
		String themeDir = "default";
		if (themeCookie != null) {
			themeDir = themeCookie.getValue();
		}
		request.setAttribute("themeDir", themeDir);
		request.setAttribute("jsessionidStr", httpRequest.getSession().getId());
		chain.doFilter(request, response);
	}

	@Inject
	private CommonService commonService;

	/**
	 * 检查在线用户
	 * 
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkOnlines(HttpServletRequest request)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		String uri = HttpUtils.getURL(request);
		String ip = HttpUtils.getIpAddr(request);
		String agent = request.getHeader("user-agent");
		if (isAppFileStr(uri) && StringUtils.isNotEmpty(username)) {
			Online online = commonService.getOnlineByUsernameOrIp(username,
					null);
			if (online == null) {
				online = new Online();
				online.setAgent(agent);
				online.setInstanceCnt(1);
				online.setUpdateTime(StringUtils.getTimeStrByNow());
				online.setUsername(username);
				online.setIp(ip);
			} else {
				online.setUpdateTime(StringUtils.getTimeStrByNow());
				online.setIp(ip);
			}
			log.debug("User:" + username + " online > "
					+ online.getUpdateTime());
			commonService.saveOnline(online);
		} else {
			log.debug("Anonymous:" + " online > "
					+ StringUtils.getTimeStrByNow());
		}
	}
}
