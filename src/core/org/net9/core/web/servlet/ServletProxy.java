package org.net9.core.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.exception.RichSystemException;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.WebModuleManager;

/**
 * Servlet proxy
 * 
 * @author gladstone
 * @since 2008-10-22
 */
@SuppressWarnings("serial")
public class ServletProxy extends HttpServlet {

	private static final Log log = LogFactory.getLog(ServletProxy.class);

	private String methodParameter = "method";

	private static final String DEFAULT_HANDLER_METHOD = "doProcess";

	private WebModuleManager actionFactory;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public void init() {
		String actions = getServletConfig().getInitParameter("actions");
		if (StringUtils.isNotEmpty(getServletConfig().getInitParameter(
				"methodParameter"))) {
			methodParameter = getServletConfig().getInitParameter(
					"methodParameter");
		}
		this.actionFactory = new WebModuleManager(actions);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String actionName = request.getRequestURI();
		log.debug(actionName);
		if (actionName.indexOf("?") > -1) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}

		int index = actionName.lastIndexOf("/");
		actionName = actionName.substring(index + 1, actionName
				.lastIndexOf("."));
		BusinessCommonServlet action;
		ReturnUrl returnUrl;
		SecurityRule securityRule;
		Method method = null;
		try {
			action = actionFactory.getAction(actionName);
			Class clazz = action.getClass();
			securityRule = (SecurityRule) clazz
					.getAnnotation(SecurityRule.class);
			returnUrl = (ReturnUrl) clazz.getAnnotation(ReturnUrl.class);
			log.debug("Action is " + actionName);
			String methodName = null;
			if (request.getParameterMap().keySet().contains(methodParameter)) {
				methodName = request.getParameter(methodParameter);
			} else {
				methodName = DEFAULT_HANDLER_METHOD;
			}

			if (StringUtils.isNotEmpty(methodName)) {
				log.debug("Method is " + methodName);
				method = clazz.getMethod(methodName, HttpServletRequest.class,
						HttpServletResponse.class);
				if (method.isAnnotationPresent(SecurityRule.class)) {
					securityRule = method.getAnnotation(SecurityRule.class);
				}
				if (method.isAnnotationPresent(ReturnUrl.class)) {
					returnUrl = method.getAnnotation(ReturnUrl.class);
				}
			}

			if (securityRule != null) {
				log.debug("the class/method require: " + securityRule.level());
				// 取得用户名
				String username = UserHelper.getuserFromCookie(request);
				// 如果用户没有登录，而且方法规定用户访问的话，转向到错误页面
				if (!HttpUtils.isInManageScope(request)
						&& StringUtils.isEmpty(username)
						&& securityRule.level() >= UserSecurityType.OPTION_LEVEL_USER) {
					String msg = I18nMsgUtils.getInstance().createMessage(
							"error.noOption", new Object[] { "register" });
					request.setAttribute(BusinessConstants.ERROR_KEY, msg);
					request.getRequestDispatcher(UrlConstants.ERROR_PAGE)
							.forward(request, response);
					return;
				}
			}
			Boolean isRederectDealed = null;
			if (method != null) {
				isRederectDealed = (Boolean) method.invoke(action, request,
						response);
			} else {
				throw new ModelNotFoundException();
				// action.service(request, response);
			}

			if ((isRederectDealed == null || isRederectDealed.booleanValue() == false)
					&& (returnUrl != null && StringUtils.isNotEmpty(returnUrl
							.url()))) {
				if (returnUrl.rederect()) {
					log.debug("send to new page:" + returnUrl.url());
					response.sendRedirect(returnUrl.url());
				} else {
					log.debug("send to native jsp:" + returnUrl.url());
					request.getRequestDispatcher(returnUrl.url()).forward(
							request, response);
				}
			}

		} catch (ModelNotFoundException e) {
			// 其实会被InvocationTargetException包装掉，不会走到这一步
			log.error("Got a ModelNotFoundException");
			// #735 (对于空贴或者其他导致nullpointer例外的情况的处理)
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
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
