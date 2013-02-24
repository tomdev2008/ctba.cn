package org.net9.core.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.mail.MailSender;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.OnlineInfoKeeper;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Reply;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.Online;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.core.UserNote;

/**
 * 用戶action
 * 
 * @author gladstone
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class UserAction extends BusinessDispatchAction {

	private static Log log = LogFactory.getLog(UserAction.class);

	/**
	 * 删除用户留言
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteUserNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nid = request.getParameter("nid");
		UserNote model = userExtService.getUserNote(Integer.parseInt(nid));

		// validate user
		UserHelper.authUserSimply(request, model);

		Integer uid = model.getUser_id();
		userExtService.deleteUserNote(model);
		return new ActionForward("userPage.shtml?uid=" + uid, true);
	}

	/**
	 * 在线游客
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward guests(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<Online> onlines = new ArrayList<Online>();
		Map<String, Online> onlineMapper = OnlineInfoKeeper.getInstance()
				.getOnlineMapper();
		Set<Entry<String, Online>> e = onlineMapper.entrySet();
		int i = 0;
		for (Entry<String, Online> model : e) {
			if (start <= i && start + limit > i) {
				onlines.add(model.getValue());
			}
			i++;
		}
		Integer onlineCnt = OnlineInfoKeeper.getInstance().getOnLineCount();
		request.setAttribute("models", onlines);
		request.setAttribute("count", onlineCnt);
		return mapping.findForward("list.guests");
	}

	/**
	 * 给朋友发送邀请信 <br>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward invite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (HttpUtils.isMethodPost(request)) {
			String username = UserHelper.getuserFromCookie(request);
			userService.trigeEvent(this.userService.getUser(username),
					username, UserEventType.MAIL_INVITE);
			String friendsStr = request.getParameter("friends");
			String[] friendsArray = friendsStr.split("\n");
			try {
				MailSender.getInstance().sendInviteMail(username, friendsArray);
				// User user = userService.getUser(null, username);
				// user
				// .setUserScore(user.getUserScore() + friendsArray.length
				// * 2);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			ActionMessage message = new ActionMessage("invite.mail.sent");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, message);
			saveMessages(request, messages);

		}
		return mapping.findForward("user.invite");
	}

	/**
	 * 好友列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward listFriends(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer start = HttpUtils.getStartParameter(request);
		User user = this.getFocusUser(request);
		List<Friend> list = userService.findFriends(user.getUserName(), null,
				start, WebConstants.PAGE_SIZE_30);
		List models = new ArrayList();
		for (Friend friend : list) {
			Map map = new HashMap();
			map.put("user", userService.getUser(null, friend.getFrdUserYou()));
			map.put("friend", friend);
			models.add(map);
		}
		request.setAttribute("username", user);
		request.setAttribute("models", models);
		request.setAttribute("count", userService.getFriendsCnt(user
				.getUserName(), null));
		return mapping.findForward("list.friend");
	}

	/**
	 * list user's topics
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward listMyTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer start = HttpUtils.getStartParameter(request);
		User user = this.getFocusUser(request);

		String re = request.getParameter("re");
		Integer limit = WebConstants.PAGE_SIZE_30;
		List list = null;
		Integer count = 0;
		List models = new ArrayList();
		if (CommonUtils.isEmpty(re)) {
			list = topicService.findTopicsByUser(user.getUserName(), start,
					limit);
			count = topicService.getTopicsCntByUser(user.getUserName());
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Topic topic = (Topic) it.next();
					Board board = boardService
							.getBoard(topic.getTopicBoardId());
					if (board == null || topic == null) {
						continue;
					}
					Map map = new HashMap();
					map.put("topic", topic);
					map.put("board", board);
					map.put("boardName", board.getBoardName());
					models.add(map);
				}
			}
		} else {
			list = replyService.findRepliesByUser(user.getUserName(), start,
					limit);
			count = replyService.getRepliesCntByUser(user.getUserName());
			if (list != null) {
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Reply topic = (Reply) it.next();
					Board board = boardService
							.getBoard(topic.getTopicBoardId());
					if (board == null || topic == null) {
						continue;
					}
					Map map = new HashMap();
					map.put("topic", topic);
					map.put("board", board);
					Topic parent = topicService.getTopic(topic
							.getTopicOriginId());
					map.put("parent", parent);
					map.put("boardName", board.getBoardName());
					models.add(map);
				}
			}
		}
		request.setAttribute("count", count);
		request.setAttribute("re", re);
		request.setAttribute("models", models);
		return mapping.findForward("list.topics");
	}

	/**
	 * 在线用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_MANAGE)
	public ActionForward online(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<Online> onlines = commonService.findOnlines(start, limit);
		List<Map<Object, Object>> onlineModels = new ArrayList<Map<Object, Object>>();
		for (Online model : onlines) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("online", model);
			User u = userService.getUser(null, model.getUsername());
			m.put("user", u);
			try {
				m.put("isFriend", StringUtils.isNotEmpty(username)
						&& userService.isFriend(username, u.getUserName()));
			} catch (Exception e) {
				m.put("isFriend", false);
			}
			onlineModels.add(m);
		}
		Integer onlineCnt = commonService.getOnlineCnt();
		request.setAttribute("models", onlineModels);
		request.setAttribute("count", onlineCnt);
		return mapping.findForward("list.onlines");
	}

	/**
	 * 保存用户留言
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveUserNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String tagetUsername = request.getParameter("u");
		String parentStr = request.getParameter("parent");
		String content = request.getParameter("content");
		Integer parent = StringUtils.isEmpty(parentStr) ? 0 : Integer
				.parseInt(parentStr);
		User user = userService.getUser(null, username);
		User tagetUser = userService.getUser(null, tagetUsername);
		UserNote model = new UserNote();
		model.setAuthor_id(user.getUserId());
		model.setCreate_time(DateUtils.getNow());
		model.setIp(HttpUtils.getIpAddr(request));
		model.setParent(parent);
		model.setUser_id(tagetUser.getUserId());
		model.setType(0);
		model.setTitle(StringUtils.getShorterStr(content, 20));
		model.setContent(content);

		userExtService.saveUserNote(model, false);
		userService.trigeEvent(this.userService.getUser(username), tagetUser
				.getUserName(), UserEventType.NOTE);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			// 针对某一楼的回复
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapUser(tagetUser) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		} else {
			// 只是普通的用户留言，不是针对某一楼的回复
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.usernote",
					new Object[] { CommonUtils.buildUserPagelink(username),
							UrlConstants.USER + tagetUser.getUserId() });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(tagetUser.getUserName(), username, msg,
					refererURL, NoticeType.REPLY);
		}
		return new ActionForward(UrlConstants.USER + tagetUser.getUserId(),
				true);
	}

	/**
	 * 查找用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = null;
		if (HttpUtils.isMethodPost(request)) {
			username = request.getParameter("username");
			request.getSession().setAttribute("__search_user", username);
		} else {
			username = (String) request.getSession().getAttribute(
					"__search_user");
		}

		request.setAttribute("key", username);

		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;

		if (StringUtils.isNotEmpty(username)) {
			List<User> users = userService.findUsers(null, username, start,
					limit);
			request.setAttribute("models", users);
			request.setAttribute("count", userService
					.getUserCnt(null, username));

		} else {
			request.setAttribute("count", 0);
		}

		return mapping.findForward("list.search");
	}

	/**
	 * /timeline这个url对应的,如果当前用户已经登录,转到朋友新鲜事的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward timelineProxy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isEmpty(username)) {
			return timeline(mapping, form, request, response);
		} else {
			return friendTimeline(mapping, form, request, response);
		}
	}

	/**
	 * 用戶事件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward timeline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		String mine = request.getParameter("mine");
		String date = request.getParameter("date");
		List<String> dateList = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			dateList.add(CommonUtils.getDateFromTodayOn(-i));
		}
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<UserLog> logs = userService.findUserlogs(
				"true".equals(mine) ? username : null, null, date, start,
				limit, UserEventWrapper.DEFAULT_WANTED_TYPES);
		request.setAttribute("models", ListWrapper.getInstance()
				.formatUserLogList(logs));
		request.setAttribute("count", userService.getUserlogsCnt("true"
				.equals(mine) ? username : null, null, date,
				UserEventWrapper.DEFAULT_WANTED_TYPES));
		Integer eventCntToday = userService.getUserlogsCntBe4SomeDay(null,
				null, UserEventWrapper.DEFAULT_WANTED_TYPES, CommonUtils
						.getDateFromTodayOn(0));
		request.setAttribute("eventCntToday", eventCntToday);
		request.setAttribute("dateList", dateList);
		request.setAttribute("currDate", date);
		return mapping.findForward("user.timeline");
	}

	/**
	 * 用戶好友的事件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward friendTimeline(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		String date = request.getParameter("date");
		List<String> dateList = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			dateList.add(CommonUtils.getDateFromTodayOn(-i));
		}
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<UserLog> logs = userExtService.findFriendsUserlogs(username, date,
				start, limit, UserEventWrapper.DEFAULT_WANTED_TYPES);
		request.setAttribute("models", ListWrapper.getInstance()
				.formatUserLogList(logs));
		request.setAttribute("count", userExtService.getFriendsUserlogsCnt(
				username, date, UserEventWrapper.DEFAULT_WANTED_TYPES));
		Integer eventCntToday = userService.getUserlogsCntBe4SomeDay(null,
				null, UserEventWrapper.DEFAULT_WANTED_TYPES, CommonUtils
						.getDateFromTodayOn(0));
		request.setAttribute("eventCntToday", eventCntToday);
		request.setAttribute("dateList", dateList);
		request.setAttribute("currDate", date);
		return mapping.findForward("user.timeline.friend");
	}

}
