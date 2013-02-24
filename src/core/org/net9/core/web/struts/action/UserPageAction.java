package org.net9.core.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.blog.service.EntryService;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.SharePrivateStateType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserPrivacySettingType;
import org.net9.core.util.UserHelper;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.core.UserNote;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.GroupTopic;

/**
 * 用户页面
 * 
 * @trac #313
 * @author gladstone
 * @since 2007/07
 */
public class UserPageAction extends BusinessDispatchAction {

	private static final Log log = LogFactory.getLog(UserPageAction.class);

	/**
	 * 社区，包括留言
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void _bbs(User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List newTopics = topicService.findTopicsByUser(user.getUserName(), 0,
				15);
		request.setAttribute("topics", newTopics);
		// 用戶留言
		String offset = request.getParameter(BusinessConstants.PAGER_OFFSET);
		int start = 0;
		if (CommonUtils.isNotEmpty(offset)) {
			start = new Integer(offset);
		}
		int limit = 15;
		List<UserNote> userNotes = userExtService.findUserNotes(user
				.getUserId(), null, start, limit);
		List<Map<Object, Object>> userNoteList = new ArrayList<Map<Object, Object>>();
		for (UserNote note : userNotes) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("user", userService.getUser(note.getAuthor_id(), null));
			map.put("note", note);
			userNoteList.add(map);
		}
		request.setAttribute("userNoteList", userNoteList);
		request.setAttribute("count", userExtService.getUserNoteCnt(user
				.getUserId(), null));
	}

	/**
	 * 博客
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked" })
	private void _blog(User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Blog blog = blogService.getBlogByUser(user.getUserName());
		if (blog != null) {
			List blogEntryies = entryService.findEntries(blog.getId(), null,
					EntryService.EntryType.NORMAL.getType(), 0, 15);
			request.setAttribute("blog", blog);
			request.setAttribute("blogEntryies", blogEntryies);
			request.setAttribute("blogEntryiesCnt", entryService
					.getEntriesCnt(blog.getId(), null,
							EntryService.EntryType.NORMAL.getType()));
		}
	}

	/**
	 * 小組
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void _group(User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用戶加入的小組
		List groups = groupService.findGroupsByUsername(user.getUserName(),
				null, 0, BusinessConstants.MAX_INT);
		request.setAttribute("groups", groups);
		// 用戶發表的小組話題
		List<GroupTopic> groupTopics = groupTopicService.findNewTopicsForUser(
				user.getUserName(), false, 0, 15);
		request.setAttribute("groupTopics", groupTopics);
	}

	/**
	 * 图片
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void _image(User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<ImageGallery> galleryList = this.imageService.findGalleries(null,
				user.getUserName(), 0, 6);
		request.setAttribute("galleryList", galleryList);
		List<ImageModel> imageList = this.imageService.findGalleryImages(user
				.getUserName(), 0, 10);
		request.setAttribute("imageList", imageList);

	}

	@SuppressWarnings("unchecked")
	private void _user(User user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 用戶的好友
		List friends = new ArrayList();
		List<String> userAndFriends = new ArrayList<String>();
		List<Friend> friendList = userService.findFriends(user.getUserName(),
				null, 0, 12);
		for (Friend friend : friendList) {
			User friendUser = userService.getUser(null, friend.getFrdUserYou());
			if (friendUser == null) {
				continue;

			}
			Map map = new HashMap();
			map.put("user", friendUser);
			friends.add(map);
			// userAndFriends.add(friendUser.getUserName());
		}
		request.setAttribute("friends", friends);

		// 用戶的事件(不包括好友)
		userAndFriends.add(user.getUserName());

		List<UserLog> userLogs = userService.findUserlogs(userAndFriends, 0,
				10, UserEventWrapper.DEFAULT_WANTED_TYPES);

		request.setAttribute("userLogs", ListWrapper.getInstance()
				.formatUserLogList(userLogs));

		user.setUserPageCount(user.getUserPageCount() + 1);
		userService.saveUser(user, true);

		// #852 (CTBA社区财富(CTB))
		Integer baseScore = this.calculateBaseScore(user.getUserName());
		Integer score = user.getUserScore() == null ? 0 : user.getUserScore();
		request.setAttribute("__request_score", baseScore + score);

	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String loginUsername = UserHelper.getuserFromCookie(request);
		User loginUser = userService.getUser(null, loginUsername);

		// =========用戶信息========>>
		String username = request.getParameter("username");
		if (StringUtils.isNotEmpty(username)) {
			username = StringUtils.getSysEncodedStr(username);
		} else {
			username = loginUsername;
		}
		String usernick = request.getParameter("usernick");
		String userId = request.getParameter("uid");

		User user = null;
		if (StringUtils.isEmpty(userId)) {
			if (StringUtils.isNotEmpty(usernick)) {
				log.info("Got nick:" + usernick);
				user = this.userService.getUserByNick(usernick);
			} else {
				user = userService.getUser(null, username);
			}

		} else {
			user = userService.getUser(new Integer(userId), null);
		}

		// 如果id和nick都没有传入的话
		// 統一使用 /u/XXX這樣的URL
		if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(usernick)) {
			return new ActionForward("/u/" + user.getUserId(), true);
		}

		username = user.getUserName();
		MainUser mainUser = userService.getUser(username);

		log.debug("visited username: " + username);

		boolean isSelf = (loginUser != null)
				&& (loginUser.getUserName().equals(user.getUserName()));

		boolean isFriend = isSelf
				|| ((loginUser != null) && (userService.isFriend(loginUser
						.getUserName(), user.getUserName())));

		// 如果用户设置了不公开，需要登录才能看
		if (loginUser == null) {
			log.debug("Not logined, check user setting");
			if (mainUser.getPrivacySetting().intValue() >= UserPrivacySettingType.MEMBER
					.getValue()) {
				String msg = I18nMsgUtils.getInstance().createMessage(
						"error.noOption",
						new Object[] { "userExt.do?method=regForm" });
				request.setAttribute(BusinessConstants.ERROR_KEY, msg);
				request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
						request, response);
				return null;
			}
		}

		if (((!isSelf) && (mainUser.getPrivacySetting()
				.equals(UserPrivacySettingType.SELF.getValue())))
				|| ((!isFriend) && (mainUser.getPrivacySetting()
						.equals(UserPrivacySettingType.FRIEND.getValue())))) {
			this.sendError(request, response, "user.privacy.forbidden");
			// request.setAttribute(BusinessConstants.ERROR_KEY, I18nMsgUtils
			// .getInstance().getMessage("user.privacy.forbidden"));
			// request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
			// request, response);
			return null;
		}

		request.setAttribute("user", user);
		request.setAttribute("main", mainUser);
		request.setAttribute("logined", loginUser != null);
		request.setAttribute("isSelf", isSelf);
		request.setAttribute("isFriend", isFriend);

		if (isSelf) {
			List<Share> shares = shareService.findShares(user.getUserName(),
					null, null, 0, 15);
			request.setAttribute("shares", shares);
		} else {
			List<Share> shares = shareService.findShares(user.getUserName(),
					null, SharePrivateStateType.PUBLIC.getValue(), 0, 15);
			request.setAttribute("shares", shares);
		}

		// <<=========用戶信息========
		// List<UserLog> pageLogs = userService.findUserlogs(null, username,
		// null,
		// 0, 9, new Integer[] { UserEventType.PAGE.getValue() });
		if (StringUtils.isNotEmpty(loginUsername)
				&& !loginUsername.equals(username)) {
			// if (!(pageLogs.size() > 0 &&
			// pageLogs.get(0).getUsername().equals(
			// loginUsername))) {
			// 用戶訪問的話，增加記錄
			userService.trigeEvent(this.userService.getUser(loginUsername),
					username, UserEventType.PAGE);
			// }
		}

		// List<Map<Object, Object>> pageLogList = new ArrayList<Map<Object,
		// Object>>();
		// for (UserLog userLog : pageLogs) {
		// Map<Object, Object> map = new HashMap<Object, Object>();
		// map.put("user", userService.getUser(null, userLog.getUsername()));
		// map.put("userLog", userLog);
		// pageLogList.add(map);
		// }
		request.setAttribute("pageLogList", userService.findUserPageLogs(
				username, 0, 9));

		this._user(user, request, response);
		this._bbs(user, request, response);
		this._blog(user, request, response);
		this._group(user, request, response);
		this._image(user, request, response);

		return mapping.findForward("view.page");
	}
}
