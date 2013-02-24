package cn.ctba.share.web.struts.action;

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
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.SharePrivateStateType;
import org.net9.core.types.ShareType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.ShareComment;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;
import org.net9.domain.model.subject.Subject;

/**
 * share action
 * 
 * @author gladstone
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class ShareAction extends BusinessDispatchAction {

	private static Log log = LogFactory.getLog(ShareAction.class);

	/**
	 * delete a share
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteShare(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Share model = shareService.getShare(Integer.valueOf(id));

		// validate user
		UserHelper.authUserSimply(request, model);

		shareService.deleteShare(model);
		return new ActionForward("share.shtml?method=shares", true);
	}

	/**
	 * 删除分享评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward deleteShareComment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ShareComment model = shareService.getShareComment(Integer.valueOf(id));

		// validate user
		UserHelper.authUserSimply(request, model);

		Integer sid = model.getShare().getId();
		shareService.deleteShareComment(model);

		return new ActionForward(UrlConstants.SHARE_COMMENT + sid, true);
	}

	/**
	 * 保存分享评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward saveShareComment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sid = request.getParameter("sid");
		String username = UserHelper.getuserFromCookie(request);
		Share share = this.shareService.getShare(Integer.valueOf(sid));
		ShareComment model = new ShareComment();
		PropertyUtil.populateBean(model, request);
		model.setIp(HttpUtils.getIpAddr(request));
		model.setShare(share);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setUsername(username);
		shareService.saveShareComment(model);
		userService.trigeEvent(this.userService.getUser(username), String
				.valueOf(share.getId()), UserEventType.SHARE_COMMENT);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapShare(share) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}

		return new ActionForward(UrlConstants.SHARE_COMMENT + share.getId(),
				true);
	}

	/**
	 * 添加分享
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveShare(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String url = request.getParameter("url");
		String label = request.getParameter("label");
		String typeStr = request.getParameter("type");
		// #674 (apps 的浏览权限)
		String stateStr = request.getParameter("state");
		String username = UserHelper.getuserFromCookie(request);
		Share model = new Share();
		if (StringUtils.isEmpty(label)) {
			label = url;
		}
		Integer type = ShareType.OTHER_OUTTER.getValue();
		if (StringUtils.isNotEmpty(typeStr)) {
			type = Integer.valueOf(typeStr);
		}

		Integer state = SharePrivateStateType.PUBLIC.getValue();
		if (StringUtils.isNotEmpty(stateStr)) {
			state = SharePrivateStateType.UN_PUBLIC.getValue();
		}
		log.info("stateStr:" + stateStr + " state:" + state);
		model.setLabel(label);
		model.setType(type);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setUsername(username);
		model.setUrl(url);
		model.setState(state);
		if (StringUtils.isEmpty(url)) {
			model.setType(ShareType.OTHER_OUTTER_WORDS_ONLY.getValue());
		}
		shareService.saveShare(model);
		if (model.getState().equals(SharePrivateStateType.PUBLIC.getValue())) {
			model = this.shareService.getNewestShareByUsername(username);
			if (model != null) {
				userService.trigeEvent(this.userService.getUser(username),
						String.valueOf(model.getId()), UserEventType.SHARE_NEW);
			}
		}

		return new ActionForward("share.shtml?method=shares", true);
	}

	/**
	 * 分享评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward shareComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sid = request.getParameter("sid");
		Integer start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<ShareComment> list = shareService.findShareComments(null, Integer
				.valueOf(sid), false, start, limit);
		request.setAttribute("count", shareService.getShareCommentCnt(null,
				Integer.valueOf(sid)));

		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (ShareComment comment : list) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("comment", comment);
			m
					.put("user", this.userService.getUser(null, comment
							.getUsername()));
			commentMapList.add(m);
		}
		request.setAttribute("models", commentMapList);
		Share share = this.shareService.getShare(Integer.valueOf(sid));
		int hitPlus = CommonUtils.getHitPlus();
		share.setHits(share.getHits() + hitPlus);
		this.shareService.saveShare(share);
		request.setAttribute("shareModel", share);
		// #674 (apps 的浏览权限)
		if (SharePrivateStateType.UN_PUBLIC.getValue().equals(share.getState())) {
			String username = UserHelper.getuserFromCookie(request);
			if (StringUtils.isEmpty(username)
					|| (!username.equals(share.getUsername()))) {
				// 不是用户本人，不准查看
				this.sendError(request, response, "share.privacy.forbidden");
				// request.setAttribute(BusinessConstants.ERROR_KEY,
				// I18nMsgUtils
				// .getInstance().getMessage("share.privacy.forbidden"));
				// request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
				// request, response);
				return null;
			}
		}

		request.setAttribute("authorModel", this.userService.getUser(null,
				share.getUsername()));
		return mapping.findForward("share.comment.list");
	}

	public ActionForward shareProxy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String typeStr = request.getParameter("type");
		String username = UserHelper.getuserFromCookie(request);
		Share model = new Share();
		Integer type = Integer.valueOf(typeStr);
		String label = "";
		String url = "";
		if (ShareType.BBS.getValue().equals(type)) {
			Topic topic = topicService.getTopic(Integer.valueOf(id));
			label = topic.getTopicTitle();
			url = UrlConstants.TOPIC + id;
		} else if (ShareType.BLOG.getValue().equals(type)) {
			BlogEntry e = entryService.getEntry(Integer.valueOf(id));
			label = e.getTitle();
			url = UrlConstants.BLOG_ENRTY + id;
		} else if (ShareType.GROUP.getValue().equals(type)) {
			GroupTopic t = groupTopicService.getTopic(Integer.valueOf(id));
			label = t.getTitle();
			url = UrlConstants.GROUP_TOPIC + id;
		} else if (ShareType.NEWS.getValue().equals(type)) {
			NewsEntry e = newsService.getNews(Integer.valueOf(id));
			label = e.getTitle();
			url = UrlConstants.NEWS_ENRTY + id;
		} else if (ShareType.SUBJECT.getValue().equals(type)) {
			Subject s = subjectService.getSubject(Integer.valueOf(id));
			label = s.getTitle();
			url = "sub/article/" + id;
		} else if (ShareType.VOTE.getValue().equals(type)) {
			Vote vote = voteService.getVote(Integer.valueOf(id));
			label = vote.getQuestion();
			url = UrlConstants.VOTE + id;
		}

		log.debug("share> " + url + ":" + label);
		model.setLabel(label);
		model.setType(type);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setUsername(username);
		model.setUrl(url);
		shareService.saveShare(model);
		model = this.shareService.getNewestShareByUsername(username);
		if (model != null) {
			userService.trigeEvent(this.userService.getUser(username), String
					.valueOf(model.getId()), UserEventType.SHARE_NEW);
		}

		return new ActionForward("share.shtml?method=shares", true);
	}

	/**
	 * 分享列表
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
	public ActionForward shares(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.getFocusUser(request);
		request.setAttribute("user", user);
		String username = UserHelper.getuserFromCookie(request);
		boolean isSelf = StringUtils.isNotEmpty(username)
				&& user.getUserName().equals(username);
		boolean all = StringUtils.isNotEmpty(request.getParameter("type"));
		if (user == null) {
			all = true;
		}
		request.setAttribute("__share_all", all ? "true" : null);
		Integer start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<Share> list = null;
		if (all) {
			// 大家的分享
			list = shareService.findShares(null, null,
					SharePrivateStateType.PUBLIC.getValue(), start, limit);
			request.setAttribute("count", shareService.getShareCnt(null, null,
					SharePrivateStateType.PUBLIC.getValue()));

			// 所有的评论
			List<ShareComment> allCommentList = shareService.findShareComments(
					null, null, true, 0, 10);
			request.setAttribute("allCommentList", allCommentList);
		} else {
			// #674 (apps 的浏览权限)
			if (isSelf) {
				list = shareService.findShares(user.getUserName(), null, null,
						start, limit);
				request.setAttribute("count", shareService.getShareCnt(user
						.getUserName(), null, null));
			} else {
				list = shareService.findShares(user.getUserName(), null,
						SharePrivateStateType.PUBLIC.getValue(), start, limit);
				request.setAttribute("count", shareService.getShareCnt(user
						.getUserName(), null, SharePrivateStateType.PUBLIC
						.getValue()));
			}

			// 用户收到的评论
			List<ShareComment> myCommentList = shareService
					.findShareCommentsByOwner(user.getUserName(), 0, 10);
			request.setAttribute("allCommentList", myCommentList);
		}

		request.setAttribute("models", ListWrapper.getInstance()
				.formatShareList(list));

		return mapping.findForward("share.list");
	}

	/**
	 * 分享功能首页
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
	public ActionForward shareIndexPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);

		if (StringUtils.isNotEmpty(username)) {
			// 自己分享
			List<Share> list = shareService.findShares(username, null, null, 0,
					30);
			request.setAttribute("myShareCnt", shareService.getShareCnt(
					username, null, null));
			request.setAttribute("myShareList", ListWrapper.getInstance()
					.formatShareList(list));

			// 用户收到的评论
			List<ShareComment> myCommentList = shareService
					.findShareCommentsByOwner(username, 0, 10);
			request.setAttribute("myCommentList", myCommentList);

			// 用戶的好友
			List<Friend> friendList = userService.findFriends(username, null,
					0, 50);
			if (friendList.size() > 0) {
				List<String> friends = new ArrayList<String>();
				for (Friend friend : friendList) {
					friends.add(friend.getFrdUserYou());
				}
				List<Share> friendsShareList = shareService.findSharesByUsers(
						friends, null, 0, 30);
				request.setAttribute("friendsShareList", ListWrapper
						.getInstance().formatShareList(friendsShareList));
			}

		}

		// 大家的分享
		List<Share> allShareList = shareService.findAllShares(null, 0, 30);
		request.setAttribute("allShareList", ListWrapper.getInstance()
				.formatShareList(allShareList));
		// 所有的评论
		List<ShareComment> allCommentList = shareService.findShareComments(
				null, null, true, 0, 10);
		request.setAttribute("allCommentList", allCommentList);
		return mapping.findForward("share.home");
	}
}
