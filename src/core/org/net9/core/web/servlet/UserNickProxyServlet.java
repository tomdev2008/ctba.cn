package org.net9.core.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.UrlConstants;
import org.net9.domain.model.core.User;

/**
 * 用户页面转向代理
 * 
 * @author gladstone
 * @since 2007/07/01
 */
 @WebModule("nick")
@Deprecated
@SuppressWarnings("serial")
public class UserNickProxyServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(UserNickProxyServlet.class);

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			CommonSystemException {
		String usernick = request.getParameter("usernick");
		log.info("Got nick:" + usernick);
		User u = this.userService.getUserByNick(usernick);
		if (u == null) {
			throw new ModelNotFoundException();
		}
		String redirect = "/" + UrlConstants.USER + u.getUserId();
		log.info("Redirect:" + redirect);
		request.getRequestDispatcher(redirect).forward(request, response);
	}
}
