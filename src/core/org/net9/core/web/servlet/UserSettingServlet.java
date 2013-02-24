package org.net9.core.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.Md5Utils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;
import org.net9.domain.model.gallery.ImageModel;

/**
 * 修改用户信息(Tab化对应)
 * 
 * TODO:明确post和get
 * 
 * @author gladstone
 * @since 2008-9-16
 */
@WebModule("userSetting")
@SuppressWarnings("serial")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class UserSettingServlet extends BusinessCommonServlet {
	static Log log = LogFactory.getLog(UserSettingServlet.class);

	@ReturnUrl(url = "/setting/bbs.jsp", rederect = false)
	public boolean bbs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		if (HttpUtils.isMethodPost(request)) {
			String usernick = request.getParameter("userNick");
			User checkedUser = this.userService.getUserByNick(usernick);
			if (checkedUser != null
					&& !checkedUser.getUserName().equals(username)) {
				// 已经存在,不能再使用这个昵称
				this.sendError(request, response,
						"page.setting.error.nick.taken");
				return true;
			}

			user.setUserNick(request.getParameter("userNick"));
			user.setUserQMD(request.getParameter("userQMD"));
			user.setUserSMD(request.getParameter("userSMD"));
			userService.saveUser(user, true);
			userService.trigeEvent(this.userService.getUser(username),
					username, UserEventType.EDIT_INFO);
			request.setAttribute("message", I18nMsgUtils.getInstance()
					.getMessage("user.info.updated"));
		}
		request.setAttribute("model", user);
		request.setAttribute("infoCompletePercent", this.userService
				.getUserInfoCompletePercent(username));
		return false;
	}

	@SuppressWarnings("unchecked")
	@ReturnUrl(url = "/setting/email.jsp", rederect = false)
	public void email(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		MainUser user = userService.getUser(username);
		if (HttpUtils.isMethodPost(request)) {
			PropertyUtil.populateBean(user, request);
			if (user.getEmailSettingMsg().intValue() == 1
					|| user.getEmailSettingTimeline().intValue() == 1
					|| user.getEmailSettingTopic().intValue() == 1) {
				user.setEmailFlag(1);
			}
			request.setAttribute("message", I18nMsgUtils.getInstance()
					.getMessage("user.info.updated"));
		}
		userService.saveMainUser(user);
		request.setAttribute("main", userService.getUser(user.getUsername()));
		request.setAttribute("model", userService.getUser(null, username));
		request.setAttribute("infoCompletePercent", this.userService
				.getUserInfoCompletePercent(username));
	}

	/**
	 * upload user's face
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(url = "/setting/face.jsp", rederect = false)
	@SuppressWarnings("unchecked")
	public void face(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userFace = "";
		if (HttpUtils.isMethodPost(request)) {
			try {
				Map map = getMultiFile(request, "face");
				userFace = (String) map.get(FILE_PATH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (CommonUtils.isNotEmpty(userFace)) {
			try {// create the images: mini default
				ImageUtils.getDefaultImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), false);
				ImageUtils.getMiniImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), false);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), 16);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), 80);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), 32);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), 64);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + userFace), 48);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			String username = UserHelper.getuserFromCookie(request);
			User user = userService.getUser(null, username);
			user.setUserFace(userFace);
			userService.saveUser(user, true);
			userService.trigeEvent(this.userService.getUser(username),
					username, UserEventType.EDIT_FACE);

			// 存入个人相册
			ImageModel imageModel = new ImageModel();
			imageModel.setCreateTime(StringUtils.getTimeStrByNow());
			imageModel.setDiscription(user.getUserName());
			imageModel.setHits(0);
			imageModel.setName(userFace);
			imageModel.setPath(userFace);
			imageModel.setType("f");
			imageModel.setUpdateTime(StringUtils.getTimeStrByNow());
			imageModel.setUsername(user.getUserName());
			imageService.saveImage(imageModel);

			request.setAttribute("message", I18nMsgUtils.getInstance()
					.getMessage("user.info.updated"));
		}

		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		request.setAttribute("model", user);
		if (CommonUtils.isNotEmpty(user.getUserFace())) {
			request.setAttribute("userFace", SystemConfigVars.UPLOAD_DIR_PATH
					+ "/" + ImageUtils.getDefaultImageStr(user.getUserFace()));
		}
		request.setAttribute("infoCompletePercent", this.userService
				.getUserInfoCompletePercent(username));
	}

	@ReturnUrl(url = "/setting/info.jsp", rederect = false)
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		MainUser user = userService.getUser(username);
		if (HttpUtils.isMethodPost(request)) {
			PropertyUtil.populateBean(user, request);
			userService.saveMainUser(user);
			request.setAttribute("message", I18nMsgUtils.getInstance()
					.getMessage("user.info.updated"));
		}
		request.setAttribute("main", userService.getUser(user.getUsername()));
		request.setAttribute("model", userService.getUser(null, username));
		request.setAttribute("infoCompletePercent", this.userService
				.getUserInfoCompletePercent(username));
	}

	@ReturnUrl(url = "/setting/password.jsp", rederect = false)
	public void password(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (HttpUtils.isMethodPost(request)) {
			String email = UserHelper.getuserFromCookie(request);
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			MainUser user = userService.getUser(email);
			if (!UserHelper.authPassword(user, password)) {
				request.setAttribute("message", I18nMsgUtils.getInstance()
						.getMessage("user.password.error"));
			} else {
				newPassword = Md5Utils.getMD5(newPassword);
				user.setPassword(newPassword);
				userService.saveMainUser(user);
				request.setAttribute("message", I18nMsgUtils.getInstance()
						.getMessage("user.info.updated"));
			}

		}
		String username = UserHelper.getuserFromCookie(request);
		request.setAttribute("infoCompletePercent", this.userService
				.getUserInfoCompletePercent(username));
	}

	/**
	 * 用户隐私设置
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(url = "/setting/privacy.jsp", rederect = false)
	public void privacy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		MainUser user = userService.getUser(username);
		if (HttpUtils.isMethodPost(request)) {
			PropertyUtil.populateBean(user, request);
			request.setAttribute("message", I18nMsgUtils.getInstance()
					.getMessage("user.info.updated"));
		}
		userService.saveMainUser(user);
		request.setAttribute("main", userService.getUser(user.getUsername()));
		request.setAttribute("model", userService.getUser(null, username));
		request.setAttribute("infoCompletePercent", this.userService
				.getUserInfoCompletePercent(username));
	}

}
