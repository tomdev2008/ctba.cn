package cn.ctba.widget.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.json.JSONHelper;
import org.net9.common.json.base.JSONException;
import org.net9.common.util.CookieUtils;
import org.net9.common.util.Md5Utils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.service.UserExtService;
import org.net9.core.types.MessageBoxType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;

import cn.ctba.widget.WidgetHelper;
import cn.ctba.widget.dto.UserInfo;

import com.google.inject.Inject;

@WebModule("wrtUser")
public class UserServlet extends BusinessCommonServlet {
	private static Log log = LogFactory.getLog(UserServlet.class);

	@Inject
	private UserExtService userExtService;
	private JSONHelper jsonHelper = new JSONHelper();

	@Inject
	private WidgetHelper widgetHelper;

	/**
	 * 得到用户的通用信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void info(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		MainUser mainUser = widgetHelper.validateUser(request);
		User user = this.userService.getUser(null, mainUser.getUsername());
		UserInfo model = new UserInfo();
		model.setIsEditor(user.getUserIsEditor());
		Integer msgCnt = userService.getMsgsCntByUser(mainUser.getUsername(),
				MessageBoxType.MSG_TYPE_RECEIVE.getValue(), true, null);
		model.setMessageCnt(msgCnt);
		Integer noticeCnt = userExtService.getNoticeCnt(mainUser.getUsername(),
				0, null);
		model.setNoticeCnt(noticeCnt);
		Integer shareCnt = this.shareService.getShareCnt(
				mainUser.getUsername(), null, null);
		model.setShareCnt(shareCnt);
		model.setTimelineCnt(userExtService.getFriendsUserlogsCnt(mainUser
				.getUsername(), null, UserEventWrapper.DEFAULT_WANTED_TYPES));
		model.setUid(user.getUserId());
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, jsonHelper
				.getPOJOJsonStr(model));
	}

	/**
	 * 用户验证登录
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();
		MainUser mainUser = userService.getUser(username);
		if (mainUser != null) {
			if (UserHelper.authPassword(mainUser, password)) {
				mainUser.setLoginTime(StringUtils.getTimeStrByNow());
				userService.saveMainUser(mainUser);
				userService.trigeEvent(mainUser, username, UserEventType.LOGIN);
				// TODO:change this
				request.setAttribute(BusinessConstants.AJAX_MESSAGE, "1");
			} else {
				request.setAttribute(BusinessConstants.AJAX_MESSAGE, "0");
			}
		} else {
			request.setAttribute(BusinessConstants.AJAX_MESSAGE, "-1");
		}
	}
//
//	/**
//	 * 使用XML
//	 * 
//	 * <li>已登录用户返回 用户的站内提醒数目
//	 * <li>未登录用户返回 -1
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 * @throws JSONException
//	 */
//	@ReturnUrl(rederect = false, url = "/apps/wrt/noticeCntXmlResponse.jsp")
//	public void noticeCnt(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException,
//			JSONException {
//		String username = UserHelper.getuserFromCookie(request);
//		if (StringUtils.isNotEmpty(username)) {
//			request.setAttribute("message", this.userExtService.getNoticeCnt(
//					username, 0, null));
//		} else {
//			request.setAttribute("message", "-1");
//		}
//		response.setContentType(BusinessConstants.MIME_TYPE_XML);
//	}

	/**
	 * 用户注册,TODO:完善
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, "");
		String username = request.getParameter("username").trim();
		String password = request.getParameter("username").trim();
		String email = request.getParameter("username").trim();

		password = Md5Utils.getMD5(password);
		log.debug(username + " " + password);
		MainUser user = userService.getUser(username);
		// check the username
		if (user == null) {
			user = UserHelper.populateMainUser(username, password, email);
			user = userService.saveMainUser(user);
			User model = UserHelper.populateUser(username, user.getId());
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
		}

	}

	/**
	 * 新鲜事
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/apps/wrt/indexPage.jsp")
	public void timeline(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			JSONException {
		MainUser mainUser = widgetHelper.validateUser(request);
		int start = HttpUtils.getStartParameter(request);
		String date = request.getParameter("date");
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<UserLog> logs = userExtService.findFriendsUserlogs(mainUser
				.getUsername(), date, start, limit,
				UserEventWrapper.DEFAULT_WANTED_TYPES);
		// FIXME:这里其实有问题
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, jsonHelper
				.getPOJOListJsonStr(ListWrapper.getInstance()
						.formatUserLogList(logs)));
	}
}
