package org.net9.core.web.struts.action;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;

/**
 * 基本Action，所有StrutsAction都继承此类
 * 
 * @author gladstone
 * 
 */
public abstract class GenericDispatchAction extends DispatchAction {

	public static final String SECURITY_ERROR_HANDLER = "securityError";

	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String parameter) throws Exception {
		SecurityRule securityRule = (SecurityRule) this.getClass()
				.getAnnotation(SecurityRule.class);
		String methodName = super.getMethodName(mapping, form, request,
				response, parameter);
		Method method = super.getMethod(methodName);
		if (method.isAnnotationPresent(SecurityRule.class)) {
			securityRule = method.getAnnotation(SecurityRule.class);
		}
		if (securityRule != null) {
			log.debug("the class/method require: " + securityRule.level());
			// TODO: make it common
			// 取得用户名
			String username = UserHelper.getuserFromCookie(request);
			// 如果用户没有登录，而且方法规定用户访问的话，转向到错误页面
			if (!HttpUtils.isInManageScope(request)
					&& StringUtils.isEmpty(username)
					&& securityRule.level() >= UserSecurityType.OPTION_LEVEL_USER) {
				return SECURITY_ERROR_HANDLER;
			}
		}
		return methodName;
	}

	/**
	 * 转发错误信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ActionForward securityError(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = I18nMsgUtils.getInstance().createMessage("error.noOption",
				new Object[] { "register" });
		request.setAttribute(BusinessConstants.ERROR_KEY, msg);
		request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(request,
				response);
		return null;
	}

}