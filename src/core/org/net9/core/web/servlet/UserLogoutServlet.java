package org.net9.core.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CookieUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.common.web.annotation.WebModule;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Online;

/**
 * 用户登出
 * 
 * @author gladstone
 * @since 2007/07/01
 */
@WebModule("userLogout")
@SuppressWarnings("serial")
// @ReturnUrl(rederect = true, url = "")
public class UserLogoutServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(UserLogoutServlet.class);

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 清除Cookie
		Cookie[] cookies = request.getCookies();
		String username = null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(
					SystemConfigUtils.getProperty("cookie.key.sso.auth"))) {
				Cookie userCookie = cookies[i];
				username = userCookie.getValue();
				log.debug("clear "
						+ username
						+ "'s cookie "
						+ SystemConfigUtils.getProperty("cookie.key.sso.auth")
						+ " to: "
						+ SystemConfigUtils
								.getProperty("cookie.key.domain.name"));
				userCookie = new Cookie(SystemConfigUtils
						.getProperty("cookie.key.sso.auth"), null);
				userCookie.setMaxAge(0);
				response.addCookie(userCookie);
			}
		}
		CookieUtils.removeCookies(response);
		String authType = SystemConfigUtils.getProperty("user.auth.type");
		if ("session".equalsIgnoreCase(authType)) {
			request.getSession().invalidate();
		}

		// 清除在线
		Online model = commonService.getOnlineByUsernameOrIp(username, null);
		if (model != null) {
			log.info("Delete the online whose ip is " + model.getIp()
					+ " and user is " + model.getUsername());
			commonService.deleteOnline(model);
			// // 设置用户的最后活动时间
			MainUser user = userService.getUser(model.getUsername());
			if (user != null) {
				user.setLoginTime(model.getUpdateTime());
				userService.saveMainUser(user);
			}
		}
		response.sendRedirect("/" + request.getContextPath());
	}
}
