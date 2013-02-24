package cn.ctba.widget.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.web.servlet.BusinessCommonServlet;

@WebModule("wrtManager")
public class ManageServlet extends BusinessCommonServlet {

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// do nothing
		// 在线人数
		// 在线游客数
		// 用户反馈
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, "");
	}

}
