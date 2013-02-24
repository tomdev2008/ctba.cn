package cn.ctba.app.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.web.servlet.BusinessCommonServlet;

@WebModule("apps")
public class AppIndexPageServlet extends BusinessCommonServlet {

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/apps/indexPage.jsp")
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// do nothing
	}

}
