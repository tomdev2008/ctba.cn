package org.net9.group.web.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.exception.RichSystemException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.GroupAuthLevelType;
import org.net9.core.types.GroupTypeHelper;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.group.GroupUser;
import org.net9.group.web.GroupHelper;

/**
 * group action
 * 
 * @author gladstone
 * 
 */
public class GroupAction extends BusinessDispatchAction {

	private final GroupHelper groupHelper = new GroupHelper();

	/**
	 * bad hit
	 * 
	 * @deprecated
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	public ActionForward bad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tid = request.getParameter("tid");

		GroupTopic topic = groupTopicService.getTopic(new Integer(tid));
		Integer badHits = topic.getBadHits();
		if (badHits == null) {
			badHits = 0;
		}
		if (canHit(request, tid)) {
			badHits++;
		}
		topic.setBadHits(badHits);
		groupTopicService.saveTopic(topic);
		String message = "" + badHits;
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean canHit(HttpServletRequest request, String id) {
		boolean reval = false;
		String hitDone = (String) request.getSession().getAttribute(
				"hitDone_" + id);
		if (StringUtils.isEmpty(hitDone)) {
			reval = true;
			request.getSession().setAttribute("hitDone_" + id, "hitDone");
		}
		return reval;
	}

	/**
	 * good hit
	 * 
	 * @deprecated
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward good(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tid = request.getParameter("tid");
		GroupTopic topic = groupTopicService.getTopic(new Integer(tid));
		Integer goodHits = topic.getGoodHits();
		if (goodHits == null) {
			goodHits = 0;
		}
		if (canHit(request, tid)) {
			goodHits++;
		}
		topic.setGoodHits(goodHits);
		groupTopicService.saveTopic(topic);
		String message = "" + goodHits;
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");
	}

	/**
	 * invate a user, not done
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward invite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// no need to auth
		String gid = request.getParameter("gid");
		String role = request.getParameter("role");
		GroupModel group = groupService.getGroup(new Integer(gid));
		request.setAttribute("group", group);
		// 用户管理的
		List<GroupModel> manageGroups = groupService.findGroupsByUsername(
				UserHelper.getuserFromCookie(request),
				GroupUserRoleType.GROUP_USER_ROLE_MANAGER, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("manageGroupList", manageGroups);

		if (HttpUtils.isMethodPost(request)) {
			String usernameArrayStr = request.getParameter("username");
			if (StringUtils.isNotEmpty(usernameArrayStr)) {
				String[] userToArray = usernameArrayStr.split(",");
				for (String username : userToArray) {
					if (StringUtils.isEmpty(username)) {
						continue;
					}

					User user = this.userService.getUser(null, username);
					if (user == null) {
						throw new RichSystemException("message.userNotExists");
					}
					GroupUser gu = groupService.getGroupUser(new Integer(gid),
							username);
					if (gu == null) {
						gu = new GroupUser();
						gu.setDeleteFlag(0);
						gu.setGroupModel(group);
						gu.setUsername(username);
						if (StringUtils.isEmpty(role)) {
							gu
									.setRole(GroupUserRoleType.GROUP_USER_ROLE_NORMAL);
						} else {
							gu.setRole(Integer.valueOf(role));
						}
						groupService.saveGroupUser(gu);

						String noticeMsg = I18nMsgUtils
								.getInstance()
								.createMessage(
										"group.invited",
										new Object[] {
												CommonUtils
														.buildUserPagelink(group
																.getManager()),
												group.getName(), });
						userService.trigeNotice(username, group.getManager(),
								noticeMsg, null, NoticeType.COMMON);

						// TODO: 提示，成功邀请N个用户
						request.setAttribute("done", true);
					} else {
						request.setAttribute("userInGroup", true);
						request.setAttribute("userInGroupUser", username);
					}
				}
			}
		}

		return mapping.findForward("group.invite");
	}

	/**
	 * 申请加入小组
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
		String username = UserHelper.getuserFromCookie(request);
		String gid = request.getParameter("gid");
		GroupModel group = groupService.getGroup(new Integer(gid));
		Integer authLevel = group.getAuthLevel();
		GroupUser gu = groupService.getGroupUser(new Integer(gid), username);
		if (gu == null) {
			gu = new GroupUser();
			gu.setDeleteFlag(0);
			gu.setGroupModel(group);
			gu.setUsername(username);
			if (authLevel >= GroupAuthLevelType.AUTH_LEVEL_AUTH) {
				// 需要认证，发送系统通知给小组长
				gu.setRole(GroupUserRoleType.GROUP_USER_ROLE_WAIT);
				groupService.saveGroupUser(gu);
				request.setAttribute("needAuth", true);
				String msg = I18nMsgUtils.getInstance().createMessage(
						"group.join.needAuth",
						new Object[] { CommonUtils.buildUserPagelink(username),
								SimplePojoWrapper.wrapGroupMember(group) });
				userService.trigeNotice(group.getManager(), username, msg,
						null, NoticeType.COMMON);

			} else {
				// 不需要认证，发送系统通知给小组长
				gu.setRole(GroupUserRoleType.GROUP_USER_ROLE_NORMAL);
				groupService.saveGroupUser(gu);
				request.setAttribute("done", true);

				String msg = I18nMsgUtils.getInstance().createMessage(
						"group.join.needNoAuth",
						new Object[] { CommonUtils.buildUserPagelink(username),
								SimplePojoWrapper.wrapGroupMember(group) });
				userService.trigeNotice(group.getManager(), username, msg,
						null, NoticeType.COMMON);

			}
		}
		userService.trigeEvent(this.userService.getUser(username), group
				.getId()
				+ "", UserEventType.GROUP_APPLY);
		request.setAttribute("group", group);
		return mapping.findForward("group.join");
	}

	/**
	 * 离开小组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward leave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String gid = request.getParameter("gid");
		String confirm = request.getParameter("confirm");
		GroupModel group = groupService.getGroup(new Integer(gid));
		request.setAttribute("group", group);
		if (StringUtils.isNotEmpty(confirm)) {
			GroupUser gu = groupService
					.getGroupUser(new Integer(gid), username);
			if (gu != null) {
				// TODO: add time line limit
				groupService.deleteGroupUser(gu);
				String msg = I18nMsgUtils.getInstance().createMessage(
						"group.leave",
						new Object[] { CommonUtils.buildUserPagelink(username),
								SimplePojoWrapper.wrapGroup(group) });
				userService.trigeNotice(group.getManager(), username, msg,
						null, NoticeType.COMMON);

				request.setAttribute("done", true);
				userService.trigeEvent(this.userService.getUser(username),
						group.getId() + "", UserEventType.GROUP_QUIT);
				return mapping.findForward("group.leave");
			}
		} else {
			request.setAttribute("needConfirm", true);
			return mapping.findForward("group.leave");
		}
		return new ActionForward(UrlConstants.GROUP + gid, true);
	}

	/**
	 * 列举我创建/加入的小组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward myGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String username = UserHelper.getuserFromCookie(request);
		String loadType = request.getParameter("loadType");
		// 用户管理的
		List<GroupModel> manageGroups = groupService.findGroupsByUsername(
				username, GroupUserRoleType.GROUP_USER_ROLE_MANAGER, start,
				limit);

		// 用户参加的
		List<GroupModel> groups = groupService.findGroupsByUsername(username,
				GroupUserRoleType.GROUP_USER_ROLE_NORMAL, start, limit);

		// 用户正在申请的
		List<GroupModel> waitingGroups = groupService.findGroupsByUsername(
				username, GroupUserRoleType.GROUP_USER_ROLE_WAIT, start, limit);
		Integer count = groupService.getGroupsCntByUsername(username, null);
		request.setAttribute("groups", ListWrapper.getInstance()
				.formatGroupModelList(groups));
		request.setAttribute("waitingGroups", ListWrapper.getInstance()
				.formatGroupModelList(waitingGroups));
		request.setAttribute("manageGroups", ListWrapper.getInstance()
				.formatGroupModelList(manageGroups));
		request.setAttribute("count", count);

		// 小組類型列表
		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		if ("ajax".equalsIgnoreCase(loadType)) {
			request.setAttribute("title", I18nMsgUtils.getInstance()
					.getMessage("group.mine"));
			return mapping.findForward("group.list.simple");
		}

		groupHelper.prepareCommends(request);
		return mapping.findForward("group.mine");
	}

	/**
	 * 通过小组申请
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward pass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String guid = request.getParameter("id");
		GroupUser model = groupService.getGroupUser(new Integer(guid));
		Integer gid = model.getGroupModel().getId();
		if (model.getRole().equals(GroupUserRoleType.GROUP_USER_ROLE_WAIT)) {
			String sourceUsername = model.getGroupModel().getName();
			String msg = I18nMsgUtils.getInstance().createMessage(
					"group.join.succeed",
					new Object[] { CommonUtils
							.buildUserPagelink(sourceUsername) });
			userService.trigeNotice(model.getUsername(), sourceUsername, msg,
					null, NoticeType.COMMON);
			model.setRole(GroupUserRoleType.GROUP_USER_ROLE_NORMAL);
			groupService.saveGroupUser(model);
			groupService.flushGroupUserCache();
		}
		return new ActionForward(UrlConstants.GROUP_MEMBER + gid, true);
	}
}
