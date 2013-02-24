package org.net9.core.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.exception.RichSystemException;
import org.net9.common.util.I18nMsgUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;

@SuppressWarnings("serial")
public class StrutsActionServlet extends ActionServlet {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			super.process(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			Throwable cause = e.getCause();
			if (cause != null && cause instanceof ModelNotFoundException) {
				log.error("Got a ModelNotFoundException By getCause");
				// #735 (对于空贴或者其他导致nullpointer例外的情况的处理)
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else if (cause != null && cause instanceof RichSystemException) {
				log.error("Got a RichSystemException By getCause");
				String msgId = ((RichSystemException) cause).getMsgId();
				request.setAttribute(BusinessConstants.ERROR_KEY, I18nMsgUtils
						.getInstance().getMessage(msgId));
				request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
						request, response);
				return;
			}
			log.error(e.getClass() + ": " + e.getMessage());
			// 防止白屏，转向到错误页面
			request.setAttribute(BusinessConstants.ERROR_KEY, e.getMessage());
			request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
					request, response);
		}
	}

}
