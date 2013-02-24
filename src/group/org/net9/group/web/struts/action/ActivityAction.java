package org.net9.group.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBWithoutImgTagHandler;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.ActivityUserRoleType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.group.ActivityComment;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.ActivityUser;
import org.net9.domain.model.group.GroupModel;
import org.net9.group.web.ActivityTypeHelper;
import org.net9.group.web.GroupHelper;

/**
 * 活动 action
 * 
 * @author ChenChangRen
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class ActivityAction extends BusinessDispatchAction {

	private final GroupHelper groupHelper = new GroupHelper();

	/**
	 * delete activity
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("aid");
		ActivityModel model;
		model = activityService.getActivity(Integer.parseInt(id));

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, model, model
				.getGroupModel());

		Integer gid = model.getGroupModel().getId();
		activityService.delActivity(model);
		return new ActionForward(UrlConstants.GROUP_ACTIVITY_LIST + gid, true);
	}

	/**
	 * 删除评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws java.lang.Exception
	 */
	public ActionForward deleteComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("cid");
		ActivityComment model = activityService.getActivityComment(Integer
				.parseInt(id));

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, model, model
				.getActivity().getGroupModel());

		Integer aid = model.getActivity().getId();
		activityService.delActivityComment(model);
		return new ActionForward(UrlConstants.ACTIVITY + aid, true);
	}

	/**
	 * group activity form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("aid");
		String gid = request.getParameter("gid");

		request.setAttribute("activityTypes", ActivityTypeHelper.typeList);
		if (StringUtils.isNotEmpty(id)) {
			ActivityModel model = activityService.getActivity(new Integer(id));
			this.setActivityModelAttr(request, model);
		} else {
			// 增加拷贝活动的功能,方便周期性的发起活动
			String sourceId = request.getParameter("copy_src");
			if (StringUtils.isNotEmpty(sourceId)) {
				ActivityModel sourceModel = activityService
						.getActivity(new Integer(sourceId));
				request.setAttribute("copyMode", true);
				this.setActivityModelAttr(request, sourceModel);
				request.setAttribute("group", sourceModel.getGroupModel());
			} else {
				GroupModel groupModel = groupService.getGroup(new Integer(gid));
				request.setAttribute("group", groupModel);
			}
		}
		List<Map> timeList = new ArrayList<Map>();
		for (int i = 0; i < 24; i++) {
			String s = i < 10 ? ("0" + i) : i + "";
			Map<String, String> m = new HashMap<String, String>();
			m.put("value", s);
			m.put("lable", s);
			timeList.add(m);
		}
		request.setAttribute("timeList", timeList);
		groupHelper.prepareCommends(request);
		// String username = UserHelper.getuserFromCookie(request);
		// request.setAttribute("__user_is_editor", this.userService
		// .isEditor(username));
		return mapping.findForward("act.form");
	}

	/**
	 * 参加活动
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward join(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String aid = request.getParameter("aid");
		String username = UserHelper.getuserFromCookie(request);
		String joinCnt = request.getParameter("join_cnt");

		ActivityModel activity = activityService.getActivity(new Integer(aid));
		ActivityUser model = activityService.getActivityUser(new Integer(aid),
				username, ActivityUserRoleType.ACT_USER_ROLE_NORMAL);

		Integer memberCount = activityService.getActivityUsersCnt(activity
				.getId(), null, new Integer[] {
				ActivityUserRoleType.ACT_USER_ROLE_NORMAL,
				ActivityUserRoleType.ACT_USER_ROLE_MANAGER });
		Integer memberPlus = 1;

		if (StringUtils.isNotEmpty(joinCnt)) {
			memberPlus += Integer.valueOf(joinCnt);
		}

		// #901 活动增加截止报名
		// 活动已经停止
		if (activity.getStopped() != null && activity.getStopped() > 0) {
			this.sendError(request, response, "page.group.activity.stopped");
			return null;
		}

		// #883 (活动改进，报名增加人员限制和外挂)
		// 报名人数达到上限
		if (activity.getMemberLimit() > 0
				&& memberCount + memberPlus > activity.getMemberLimit()) {
			this.sendError(request, response,
					"page.group.activity.member.limited");
			return null;
		}

		if (model == null) {
			model = new ActivityUser();
			model.setActivityModel(activity);
			model.setUsername(username);
			// #883 (活动改进，报名增加人员限制和外挂)
			model.setJoinCnt(memberPlus);

			model.setRole(ActivityUserRoleType.ACT_USER_ROLE_NORMAL);
			activityService.saveActivityUser(model);
			userService.trigeEvent(this.userService.getUser(username), activity
					.getId()
					+ "", UserEventType.GROUP_ACTIVITY_JOIN);
			String msg = I18nMsgUtils.getInstance().createMessage(
					"activity.joined",
					new Object[] { CommonUtils.buildUserPagelink(username),
							activity.getTitle() });
			userService.trigeNotice(activity.getUsername(), username, msg,
					null, NoticeType.COMMON);
		}
		return new ActionForward(UrlConstants.ACTIVITY + aid, true);
	}

	/**
	 * list group activity
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String groupId = request.getParameter("gid");
		List<ActivityModel> activities = activityService.findActivities(Integer
				.parseInt(groupId), null, null, null, start, limit);
		GroupModel group = groupService.getGroup(new Integer(groupId));
		groupHelper.info(request, group);
		Integer count = activityService.getActivitiesCnt(Integer
				.parseInt(groupId), null, null, null);
		request.setAttribute("count", count);
		request.setAttribute("models", ListWrapper.getInstance()
				.formatActivityList(activities, username));
		groupHelper.prepareCommends(request);
		return mapping.findForward("act.list");
	}

	/**
	 * 列出全部的活动（#737）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward listAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<ActivityModel> activities = activityService.findActivities(null,
				null, null, null, start, limit);
		Integer count = activityService
				.getActivitiesCnt(null, null, null, null);
		request.setAttribute("count", count);
		request.setAttribute("models", ListWrapper.getInstance()
				.formatActivityList(activities, username));
		groupHelper.prepareCommends(request);
		return mapping.findForward("act.list.all");
	}

	/**
	 * 放弃活动
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward quit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aid = request.getParameter("aid");
		String username = UserHelper.getuserFromCookie(request);
		ActivityModel activity = activityService.getActivity(new Integer(aid));
		ActivityUser model = activityService.getActivityUser(activity.getId(),
				username, ActivityUserRoleType.ACT_USER_ROLE_NORMAL);
		if (model != null) {
			activityService.delActivityUser(model);
			String msg = I18nMsgUtils.getInstance().createMessage(
					"activity.quited",
					new Object[] { CommonUtils.buildUserPagelink(username),
							activity.getTitle() });
			userService.trigeNotice(activity.getUsername(), username, msg,
					null, NoticeType.COMMON);

		}
		return new ActionForward(UrlConstants.ACTIVITY + aid, true);
	}

	/**
	 * save activity
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String id = request.getParameter("id");
		String gid = request.getParameter("gid");

		ActivityModel model;
		if (StringUtils.isEmpty(id)) {
			GroupModel g = groupService.getGroup(Integer.parseInt(gid));
			model = new ActivityModel();
			model.setUsername(username);
			model.setCreateTime(DateUtils.getNow());
			model.setGroupModel(g);
			model.setHits(0);
		} else {
			model = activityService.getActivity(Integer.parseInt(id));

			// validate user
			this.groupHelper.authUserForCurrentPojo(request, model, model
					.getGroupModel());
		}
		PropertyUtil.populateBean(model, request);

		if (request.getParameterMap().containsKey("limited")) {
			String memberLimit = request.getParameter("memberLimit");
			if (StringUtils.isNum(memberLimit)) {
				model.setMemberLimit(Integer.valueOf(memberLimit));
			}
		} else {
			model.setMemberLimit(0);
		}

		model.setUpdateTime(DateUtils.getNow());
		model.setContent(request.getParameter("topicContent"));
		String startTime = request.getParameter("t_start");
		String endTime = request.getParameter("t_end");
		model.setStartTime(model.getStartTime() + " " + startTime);
		model.setEndTime(model.getEndTime() + " " + endTime);
		model.setStopped(0);
		activityService.saveActivity(model);
		List<ActivityModel> actList = activityService.findActivities(Integer
				.parseInt(gid), null, null, null, 0, 1);
		if (StringUtils.isEmpty(id) && actList.size() > 0) {
			userService.trigeEvent(this.userService.getUser(username), actList
					.get(0).getId()
					+ "", UserEventType.GROUP_ACTIVITY_NEW);
			model = activityService.getActivity(null);
			ActivityUser au = new ActivityUser();
			au.setActivityModel(model);
			au.setUsername(username);
			au.setRole(ActivityUserRoleType.ACT_USER_ROLE_MANAGER);
			activityService.saveActivityUser(au);
		}
		return new ActionForward(UrlConstants.GROUP_ACTIVITY_LIST + gid, true);
	}

	/**
	 * 提交活动评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String aid = request.getParameter("aid");
		ActivityModel activity = this.activityService.getActivity(Integer
				.valueOf(aid));
		ActivityComment model;
		model = new ActivityComment();
		model.setAuthor(username);
		model.setActivity(activity);
		model.setIp(HttpUtils.getIpAddr(request));

		PropertyUtil.populateBean(model, request);
		model.setUpdateTime(DateUtils.getNow());
		activityService.saveActivityComment(model);
		userService.trigeEvent(this.userService.getUser(username), activity
				.getId()
				+ "", UserEventType.GROUP_ACTIVITY_COMMENT);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapActivity(activity) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}
		return new ActionForward(UrlConstants.ACTIVITY + aid, true);
	}

	private void setActivityModelAttr(HttpServletRequest request,
			ActivityModel model) throws Exception {
		request.setAttribute("model", model);
		request.setAttribute("group", model.getGroupModel());
		request.setAttribute("startDate", model.getStartTime().substring(0, 10)
				.trim());
		request.setAttribute("endDate", model.getEndTime().substring(0, 10)
				.trim());
		request.setAttribute("t_start", model.getStartTime().substring(10 + 1));
		request.setAttribute("t_end", model.getEndTime().substring(10 + 1));
	}

	/**
	 * 切换活动停止状态
	 * 
	 * #901 活动增加截止报名
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toggleStop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aid = request.getParameter("aid");
		ActivityModel activity = this.activityService.getActivity(Integer
				.valueOf(aid));
		// validate user
		this.groupHelper.authUserForCurrentPojo(request, activity, activity
				.getGroupModel());

		if (activity.getStopped() > 0) {
			activity.setStopped(0);
		} else {
			activity.setStopped(1);
		}
		this.activityService.saveActivity(activity);

		return new ActionForward(UrlConstants.ACTIVITY + aid, true);
	}

	/**
	 * 查看活動
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("aid");
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		int hitPlus = new Random().nextInt(5) + 1;
		ActivityModel model = activityService.getActivity(new Integer(id));
		model.setHits(model.getHits() + hitPlus);
		activityService.saveActivity(model);
		request.setAttribute("model", model);
		User author = userService.getUser(null, model.getUsername());
		request.setAttribute("author", author);
		groupHelper.info(request, model.getGroupModel());
		request.setAttribute("group", model.getGroupModel());

		List<ActivityUser> activityUsers = activityService.findActivityUsers(
				model.getId(), null, new Integer[] {
						ActivityUserRoleType.ACT_USER_ROLE_NORMAL,
						ActivityUserRoleType.ACT_USER_ROLE_MANAGER }, 0,
				BusinessConstants.MAX_INT);
		List<Map<Object, Object>> activityUserMaps = new ArrayList<Map<Object, Object>>();
		for (ActivityUser au : activityUsers) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("user", userService.getUser(null, au.getUsername()));
			m.put("au", au);
			activityUserMaps.add(m);
		}
		request.setAttribute("activityUserMaps", activityUserMaps);
		request.setAttribute("activityType", ActivityTypeHelper.m.get(model
				.getType()
				+ ""));
		request.setAttribute("memberCount", activityService
				.getActivityUsersCnt(model.getId(), null, new Integer[] {
						ActivityUserRoleType.ACT_USER_ROLE_NORMAL,
						ActivityUserRoleType.ACT_USER_ROLE_MANAGER }));
		request.setAttribute("__group_is_member", (!model.getUsername().equals(
				username))
				&& null != activityService.getActivityUser(model.getId(),
						username, ActivityUserRoleType.ACT_USER_ROLE_NORMAL));
		String userQMD = UBBDecoder.decode(CommonUtils
				.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(author
						.getUserQMD())), new UBBWithoutImgTagHandler(),
				UBBDecoder.MODE_IGNORE);
		request.setAttribute("userQMD", userQMD);

		List<ActivityComment> commentList = this.activityService
				.findActivityComments(model.getId(), null, start, limit);
		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (ActivityComment comment : commentList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("user", userService.getUser(null, comment.getAuthor()));
			m.put("comment", comment);
			commentMapList.add(m);
		}
		Integer cnt = this.activityService.getActivityCommentsCnt(
				model.getId(), null);
		request.setAttribute("count", cnt);
		request.setAttribute("commentMapList", commentMapList);
		groupHelper.prepareCommends(request);

		return mapping.findForward("act.view");
	}
}
