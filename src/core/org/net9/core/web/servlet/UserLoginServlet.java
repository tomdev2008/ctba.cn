package org.net9.core.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.CookieUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserEventType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;

/**
 * 用户登录
 * 
 * @author gladstone
 * @since 2007/07/01
 */
@WebModule("userLogin")
@SuppressWarnings("serial")
public class UserLoginServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(UserLoginServlet.class);

	@Override
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		String remember = "on";
		// request.getParameter("remember");
		if (CommonUtils.isEmpty(username) || CommonUtils.isEmpty(password)) {
			String msg = I18nMsgUtils.getInstance().getMessage(
					"user.nameOrPasswordnotNull");
			request.setAttribute(BusinessConstants.ERROR_KEY, msg);
			RequestDispatcher rd = request
					.getRequestDispatcher(UrlConstants.ERROR_PAGE);
			rd.forward(request, response);
			return;
		}
		RequestDispatcher rd;
		String msg;
		// set the user's score and main user' login info
		User user = userService.getUser(null, username);
		if (user != null) {
			// if (user.getUserScore() == null) {
			// user.setUserScore(3);
			// }
			MainUser mainUser = userService.getUser(username);
			if (UserHelper.authPassword(mainUser, password)) {
				mainUser.setLoginIp(HttpUtils.getIpAddr(request));
				mainUser.setLoginTime(StringUtils.getTimeStrByNow());
				userService.saveMainUser(mainUser);
				userService.saveUser(user, true);
				// #369 解决用户名不区分导致的问题
				CookieUtils.writeAuthCookie(response, mainUser.getUsername(),
						remember != null);
				String authType = SystemConfigUtils
						.getProperty("user.auth.type");
				if ("session".equalsIgnoreCase(authType)) {
					log.debug("use the session type");
					request.getSession().setAttribute(
							BusinessConstants.USER_NAME, username);
				}
				userService.trigeEvent(mainUser, username, UserEventType.LOGIN);
			} else {
				msg = (I18nMsgUtils.getInstance().getMessage("auth.notValid"));
				request.setAttribute(BusinessConstants.ERROR_KEY, msg);
				rd = request.getRequestDispatcher(UrlConstants.ERROR_PAGE);
				rd.forward(request, response);
				return;
			}
		} else {
			msg = (I18nMsgUtils.getInstance().getMessage("auth.notValid"));
			request.setAttribute(BusinessConstants.ERROR_KEY, msg);
			rd = request.getRequestDispatcher(UrlConstants.ERROR_PAGE);
			rd.forward(request, response);
			return;
		}
		String refererURL = HttpUtils.getReferer(request);
		log.info("refererURL:" + refererURL);
		if (CommonUtils.isNotEmpty(refererURL)
				&& !refererURL.contains(UrlConstants.USER_LOGIN_ACTION)) {
			response.sendRedirect(refererURL);
		} else {
			response.sendRedirect("/" + request.getContextPath());
		}
	}
}
