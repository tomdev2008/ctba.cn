package cn.ctba.app.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.json.JSONHelper;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserEventType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.MobileUserEventWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.group.GroupModel;

/**
 * some of the ajax requests load from this
 * 
 * @author gladstone
 * @since Mar 17, 2009
 */
@AjaxResponse
public class AjaxAction extends BusinessDispatchAction {

	private static Log log = LogFactory.getLog(AjaxAction.class);

	/**
	 * list a limited num of user's friends
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward friends(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		// friends
		List friends = new ArrayList();
		List friendList = userService.findFriends(username, null, 0, 12);
		if (friendList != null) {
			Iterator it = friendList.iterator();
			while (it.hasNext()) {
				Friend friend = (Friend) it.next();
				User friendUser = userService.getUser(null, friend
						.getFrdUserYou());
				if (friendUser == null) {
					continue;
				}
				Map map = new HashMap();
				map.put("user", friendUser);
				String userFace = "images/nopic.gif";
				if (CommonUtils.isNotEmpty(friendUser.getUserFace())) {
					userFace = SystemConfigVars.UPLOAD_DIR_PATH
							+ "/"
							+ ImageUtils.getMiniImageStr(friendUser
									.getUserFace());
				}
				map.put("userFace", userFace);
				friends.add(map);
			}
		}
		request.setAttribute("models", friends);
		return mapping.findForward("ajax.friends");
	}

	/**
	 * for widget test
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward dummyUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List models = this.userService.findUsers(0, 10);
		JSONHelper helper = new JSONHelper();
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, helper
				.getPOJOListJsonStr(models));
		return mapping.findForward("message");
	}

	/**
	 * search board by board's name
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward searchBoard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String boardName = request.getParameter("q");
		String limitStr = request.getParameter("limit");
		int limit = BusinessConstants.MAX_INT;
		if (CommonUtils.isNotEmpty(limitStr)) {
			limit = new Integer(limitStr);
		}
		List list = boardService.findBoards(null, boardName, 0, limit);
		String message = "";
		if (list != null) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Board model = (Board) it.next();
				message += model.getBoardName() + "\n";
			}
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");
	}

	/**
	 * search a username
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward searchUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("q");
		if (CommonUtils.isNotEmpty(username)) {
			// username = StringUtils.getSysEncodedStr(username);
			username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		}
		log.debug("user:" + username);
		String limitStr = request.getParameter("limit");
		int limit = BusinessConstants.MAX_INT;
		if (CommonUtils.isNotEmpty(limitStr)) {
			limit = new Integer(limitStr);
		}
		List list = userService.findUsers(null, username, 0, limit);
		String message = "";
		if (list != null) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				User user = (User) it.next();
				message += user.getUserName() + "\n";
			}
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");
	}

	/**
	 * 查看group是否有转发url
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward checkGroupByUrl(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = request.getParameter("url");
		String originId = request.getParameter("originId");
		log.debug("url: " + url);
		GroupModel groupModel = groupService.getGroupsByMagicUrl(url);
		String message = "";
		if (groupModel != null) {
			if (StringUtils.isEmpty(originId)) {
				message = String.valueOf(groupModel.getId());
			} else if (Integer.valueOf(originId).intValue() != groupModel
					.getId().intValue()) {
				message = String.valueOf(groupModel.getId());
			}
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");
	}

	/**
	 * 查看是否有同名小组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkGroupByName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		log.debug("name: " + name);
		String originId = request.getParameter("originId");
		GroupModel groupModel = groupService.getGroupByName(name);
		String message = "";
		if (groupModel != null) {
			if (StringUtils.isEmpty(originId)) {
				message = String.valueOf(groupModel.getId());
			} else if (Integer.valueOf(originId).intValue() != groupModel
					.getId().intValue()) {
				message = String.valueOf(groupModel.getId());
			}
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");
	}

	/**
	 * 查看昵称是否被占用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkNickname(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nickname = request.getParameter("nickname");
		log.debug("nickname: " + nickname);
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUserByNick(nickname);
		String message = "";
		if (user != null && !user.getUserName().equals(username)) {
			message = user.getUserNick();
		}
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}

	/**
	 * share proxy, post by ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward share(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("u");
		String password = request.getParameter("p");
		String label = request.getParameter("l");
		String url = request.getParameter("url");
		if (!(StringUtils.isEmpty(label) && StringUtils.isEmpty(url))) {

			if (StringUtils.isNotEmpty(username)
					&& StringUtils.isNotEmpty(password)) {
				log.info("username: " + username);
				MainUser mainUser = userService.getUser(username);
				if (UserHelper.authPassword(mainUser, password)) {
					Share model = new Share();
					if (StringUtils.isEmpty(label)) {
						label = url;
					}
					model.setLabel(label);
					model.setUrl(url);
					model.setType(1);// by ajax
					model.setUpdateTime(StringUtils.getTimeStrByNow());
					model.setUsername(username);
					this.shareService.saveShare(model);
					model = this.shareService
							.getNewestShareByUsername(username);
					if (model != null) {
						userService.trigeEvent(mainUser, String.valueOf(model
								.getId()), UserEventType.SHARE_NEW_TOOL);
					}
					request.setAttribute("message", "OK");
				} else {
					request.setAttribute("message", "FAIL");
					log.warn("user auth faild:" + username);
				}
			} else {
				log.warn("username or password are empty:" + username);
				request.setAttribute("message", "FAIL");
			}
		} else {
			request.setAttribute("message", "FAIL");
		}
		return mapping.findForward("message");
	}

	public ActionForward timeline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<UserLog> models = userService.findUserlogs(null, null, null,
				start, limit, UserEventWrapper.DEFAULT_WANTED_TYPES);
		List<WrappedEvent> rows = new ArrayList<WrappedEvent>();
		for (UserLog logModel : models) {
			WrappedEvent result = new WrappedEvent();
			result.setId(logModel.getId());
			result.setTarget(logModel.getTarget());
			result.setUsername(logModel.getUsername());
			result.setUpdateTime(logModel.getUpdateTime());
			try {
				// String eventStr = UserEventWrapper.getInstance().wrappeEvent(
				// logModel);
				String mobileEventStr = MobileUserEventWrapper.getInstance()
						.wrappeEvent(logModel);
				// result.setEventStr(eventStr);
				result.setMobileEventStr(mobileEventStr);
				rows.add(result);
				log.debug(result.getEventStr());
			} catch (Exception e) {
				log.error(e.getMessage());
				continue;
			}

		}
		log.debug("model size: " + models.size());
		JSONHelper helper = new JSONHelper();
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, helper
				.getPOJOListJsonStr(rows));

		return mapping.findForward("message");
	}

	public ActionForward timelineCnt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer cnt = userService.getUserlogsCnt(null, null, null,
				UserEventWrapper.DEFAULT_WANTED_TYPES);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, cnt + "");
		return mapping.findForward("message");
	}

	/**
	 * 得到用户的分享数目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward shareCnt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("u");
		String password = request.getParameter("p");
		if (StringUtils.isNotEmpty(username)
				&& StringUtils.isNotEmpty(password)) {
			log.info("username: " + username);
			MainUser mainUser = userService.getUser(username);
			if (UserHelper.authPassword(mainUser, password)) {
				Integer cnt = this.shareService.getShareCnt(mainUser
						.getUsername(), null, null);
				request.setAttribute("message", cnt);
			} else {
				request.setAttribute("message", "-1");
				log.warn("user auth faild:" + username);
			}
		} else {
			log.warn("username or password are empty:" + username);
			request.setAttribute("message", "-1");
		}
		return mapping.findForward("message");
	}
}
