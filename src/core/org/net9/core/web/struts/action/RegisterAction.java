package org.net9.core.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.net9.bbs.web.struts.form.UserForm;
import org.net9.common.exception.ValidateException;
import org.net9.common.util.CookieUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.Md5Utils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.NeedValidation;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebPageVarConstant;
import org.net9.core.mail.MailSender;
import org.net9.core.types.CommendType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.util.ValidateHelper;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;

/**
 * 用戶action,管理注冊登錄等
 * 
 * @author gladstone
 */
public class RegisterAction extends BusinessDispatchAction {

	private final static Log log = LogFactory.getLog(RegisterAction.class);

	/**
	 * 检查用户名是否占用，ajax加载
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	public ActionForward checkUsername(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("username");
		log.debug("check username: " + username);
		MainUser u = null;
		u = userService.getUser(username);
		String message = I18nMsgUtils.getInstance().getMessage(
				"email.reg.allow");
		if (u != null)
			message = I18nMsgUtils.getInstance().getMessage(
					"email.reg.notAllow");
		log.debug(message);
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}

	/**
	 * 用户忘记密码
	 * 
	 * 先是發送確認信
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (HttpUtils.isMethodGet(request)) {
			if (!StringUtils.isEmpty(UserHelper.getuserFromCookie(request)))
				request.setAttribute(WebPageVarConstant.ACTION_DONE, true);
			return mapping.findForward("user.forget");
		}

		// 只處理post的情況
		String username = request.getParameter("username");
		MainUser mainUser = userService.getUser(username);
		if (mainUser == null) {
			ActionMessage error = new ActionMessage("user.notExist", username);
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return mapping.findForward("error");
		}
		// 如果用户没有设置邮箱
		if (StringUtils.isEmpty(mainUser.getEmail())) {
			ActionMessage error = new ActionMessage("user.password.noEmail");
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return mapping.findForward("error");
		}
		// 发送确认信
		try {
			MailSender.getInstance().sendConfirmMail(mainUser);
		} catch (Exception e) {
			log.error(e);
		}
		ActionMessage error = new ActionMessage("password.mail.sent");
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		saveMessages(request, errors);
		request.setAttribute(WebPageVarConstant.ACTION_DONE, true);
		return mapping.findForward("user.forget");
	}

	/**
	 * 注册提交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ValidateException
	 * @throws Exception
	 */
	@NeedValidation(validator = "register")
	public ActionForward reg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ValidateException {
		UserForm form1 = (UserForm) form;
		String username = form1.getUsername().trim();
		String password = form1.getPassword().trim();
		String email = form1.getEmail().trim();

		// 简单认证用户名等
		ValidateHelper.validateSingleField(username, "regUsername");
		ValidateHelper.validateSingleField(password, "regPassword");
		ValidateHelper.validateSingleField(email, "email");

		password = Md5Utils.getMD5(password);
		log.debug(username + " " + password);
		MainUser user = userService.getUser(username);
		// check the username
		if (user != null) {
			ActionMessage msg = new ActionMessage("user.reg.notAllow");
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			return mapping.findForward("user.reg");
		}
		user = UserHelper.populateMainUser(username, password, email);
		// user = new MainUser();
		// user.setPassword(password);
		// user.setUsername(username);
		// user.setEmail(email);
		// user.setSex(0);
		// user.setRegTime(StringUtils.getTimeStrByNow());
		// user.setPrivacySetting(UserPrivacySettingType.ALL.getValue());
		user = userService.saveMainUser(user);
		User model = UserHelper.populateUser(username, user.getId());
		// User model = new User();
		// model.setUserPageCount(0);
		// model.setUserFace("");
		// model.setUserId(new Integer(user.getId()));
		// model.setUserName(username);
		// String nick = "";
		// username.indexOf("@") > 0 ? username.substring(0,
		// username.indexOf("@")) : username;
		// model.setUserNick(nick);
		// model.setUserOption(UserSecurityType.OPTION_LEVEL_USER);
		// model.setUserIsEditor(0);
		userService.saveUser(model, false);
		CookieUtils.writeAuthCookie(response, user.getUsername(), false);
		String authType = SystemConfigUtils.getProperty("user.auth.type");
		if ("session".equalsIgnoreCase(authType)) {
			log.debug("use the session type");
			request.getSession().setAttribute(BusinessConstants.USER_NAME,
					username);
		}

		user.setLoginIp(HttpUtils.getIpAddr(request));
		user.setLoginTime(StringUtils.getTimeStrByNow());
		userService.saveMainUser(user);
		request.setAttribute("username", user.getUsername());
		// 最后发送注册信
		try {
			MailSender.getInstance().sendRegMail(user);
		} catch (Exception e) {
			log.error(e);
		}
		userService.trigeEvent(this.userService.getUser(username), username,
				UserEventType.REGISTER);

		return new ActionForward("userExt.shtml?method=regSucceed", true);
	}

	public ActionForward regForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("user.reg");
	}

	public ActionForward regSucceed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("commendBbs", this.commonService
				.findMainCommendsByType(CommendType.BBS.getValue(), 0, 10));
		request.setAttribute("commendBlog", this.commonService
				.findMainCommendsByType(CommendType.BLOG.getValue(), 0, 10));
		request.setAttribute("commendSub", this.commonService
				.findMainCommendsByType(CommendType.SUBJECT.getValue(), 0, 10));
		request.setAttribute("commendGroup", this.commonService
				.findMainCommendsByType(CommendType.GROUP.getValue(), 0, 10));
		request.setAttribute("commendNews", this.commonService
				.findMainCommendsByType(CommendType.NEWS.getValue(), 0, 10));
		request.setAttribute("commendVote", this.commonService
				.findMainCommendsByType(CommendType.VOTE.getValue(), 0, 10));
		return mapping.findForward("user.reg.succeed");
	}

	/**
	 * 用戶重新設置密碼，之前先驗證點擊的正確性
	 * 
	 * 之前發送的密碼字符串是MD5(用戶名+用戶密碼)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (HttpUtils.isMethodGet(request)) {
			String username = request.getParameter("u");
			MainUser u = userService.getUser(username);
			String confirm = request.getParameter("c");
			if (!Md5Utils.getMD5(u.getUsername() + u.getPassword()).equals(
					confirm)) {
				ActionMessage error = new ActionMessage("info.auth.notValid");
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				saveMessages(request, errors);
				return mapping.findForward("error");
			}
			request.setAttribute("username", username);
			request.setAttribute("user", u);
			return mapping.findForward("user.resetPassword");
		}
		// 用戶提交了表單之後
		String username = request.getParameter("username");
		MainUser u = userService.getUser(username);
		String newPassword = request.getParameter("newPassword");
		if (StringUtils.isNotEmpty(newPassword)) {
			newPassword = Md5Utils.getMD5(newPassword);
			u.setPassword(newPassword);
			userService.saveMainUser(u);
			ActionMessage message = new ActionMessage("user.password.done");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			saveMessages(request, messages);
			request.setAttribute(WebPageVarConstant.ACTION_DONE, true);
			// request.getSession().setAttribute(Constant.SESSION_KEY_URL, "");
		}
		return mapping.findForward("user.resetPassword");
	}

}
