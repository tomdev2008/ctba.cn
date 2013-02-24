package org.net9.group.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.GroupAuthLevelType;
import org.net9.core.types.GroupTypeHelper;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupUser;
import org.net9.group.web.GroupHelper;

/**
 * 处理小组的控制器
 * 
 * @author gladstone
 * @since 2008-8-10
 */
@SuppressWarnings("serial")
@WebModule("g")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class GroupServlet extends BusinessCommonServlet {

	static Log log = LogFactory.getLog(GroupServlet.class);
	private final GroupHelper groupHelper = new GroupHelper();

	/**
	 * 删除小组成员
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void delMember(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SecurityException {
		String guid = request.getParameter("id");
		GroupUser model = groupService.getGroupUser(new Integer(guid));

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, model, model
				.getGroupModel());

		Integer gid = model.getGroupModel().getId();
		if (model.getRole().equals(GroupUserRoleType.GROUP_USER_ROLE_WAIT)) {
			// 拒绝申请
			String msg = I18nMsgUtils.getInstance().createMessage(
					"group.join.failed",
					new Object[] { SimplePojoWrapper.wrapGroupMember(model
							.getGroupModel()) });
			userService.trigeNotice(model.getUsername(), model.getGroupModel()
					.getManager(), msg, null, NoticeType.COMMON);
		}
		if (model.getRole().equals(GroupUserRoleType.GROUP_USER_ROLE_NORMAL)) {
			// 踢出组外
			String msg = I18nMsgUtils.getInstance().createMessage("group.out",
					new Object[] { model.getGroupModel().getName() });
			userService.trigeNotice(model.getUsername(), model.getGroupModel()
					.getManager(), msg, null, NoticeType.COMMON);
		}
		if (!model.getRole().equals(GroupUserRoleType.GROUP_USER_ROLE_MANAGER)) {
			groupService.deleteGroupUser(model);
		}
		response.sendRedirect(UrlConstants.GROUP_MEMBER + gid);

	}

	/**
	 * 小组表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/group/groupForm.jsp")
	public boolean form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("gid");
		String username = UserHelper.getuserFromCookie(request);
		Integer cnt = groupService.getGroupsCntByUsername(username,
				GroupUserRoleType.GROUP_USER_ROLE_MANAGER);

		if (StringUtils.isNotEmpty(idStr)) {
			GroupModel model = groupService.getGroup(new Integer(idStr));
			request.setAttribute("model", model);
		} else {
			if (cnt >= BusinessConstants.USER_MAX_GROUP_SIZE) {
				// the max size
				String message = I18nMsgUtils.getInstance().createMessage(
						"group.user.max", new Object[] { cnt });
				request.setAttribute(BusinessConstants.ERROR_KEY, message);
				request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
						request, response);
				return true;
			}
		}
		request.setAttribute("groupTypes", GroupTypeHelper.typeList);
		groupHelper.prepareCommends(request);
		return false;
	}

	/**
	 * 小组列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@ReturnUrl(rederect = false, url = "/group/groupList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String searchKey = null;
		String groupType = request.getParameter("t");
		String groupTag = request.getParameter("tag");
		String tabIndex = request.getParameter("t_index");
		request.setAttribute("t_index", tabIndex);
		groupTag = StringUtils.base64Decode(groupTag).trim();

		if (StringUtils.isNotEmpty(groupType)) {
			request.setAttribute("currType", GroupTypeHelper.typeMap
					.get(groupType));
		}
		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		if (HttpUtils.isMethodPost(request)) {
			searchKey = request.getParameter("groupSearchKey");
			request.getSession().setAttribute("groupSearchKey", searchKey);
		} else {
			searchKey = (String) request.getSession().getAttribute(
					"groupSearchKey");
		}
		// #851 (小组分类 tab 中，“查看全部”无效)
		if (StringUtils.isNotEmpty(groupType)
				|| StringUtils.isNotEmpty(groupTag)) {
			searchKey = "";
			request.getSession().setAttribute("groupSearchKey", searchKey);
		}

		request.setAttribute("groupSearchKey", searchKey);

		List<GroupModel> groups = null;
		Integer cnt = 0;
		if (StringUtils.isNotEmpty(groupTag)) {
			groups = groupService.findGroupsByTag(groupType, groupTag,
					groupTag, start, limit);
			cnt = groupService.getGroupsCntByTag(groupType, groupTag, groupTag);
		} else {
			groups = groupService
					.findGroups(groupType, searchKey, start, limit);
			cnt = groupService.getGroupsCnt(groupType, searchKey);
		}

		request.setAttribute("groups", groups);
		request.setAttribute("count", cnt);
		request.setAttribute("groupsMap", this.wrapGroupList(groups, username));
		groupHelper.prepareCommends(request);
	}

	/**
	 * 列出小组成员
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@ReturnUrl(rederect = false, url = "/group/memberList.jsp")
	public void members(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gid = request.getParameter("gid");
		GroupModel group = groupService.getGroup(new Integer(gid));
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<GroupUser> users = groupService.findGroupUsers(new Integer(gid),
				new Integer[] { GroupUserRoleType.GROUP_USER_ROLE_NORMAL,
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER }, start,
				limit);

		List userList = new ArrayList();
		for (GroupUser user : users) {
			Map m = new HashMap();
			m.put("gu", user);
			m.put("user", userService.getUser(null, user.getUsername()));
			userList.add(m);
		}

		// 正在等待审批的用户
		List<GroupUser> usersWaiting = groupService.findGroupUsers(new Integer(
				gid), new Integer[] { GroupUserRoleType.GROUP_USER_ROLE_WAIT },
				start, limit);
		List userListWaiting = new ArrayList();
		for (GroupUser user : usersWaiting) {
			Map m = new HashMap();
			m.put("gu", user);
			m.put("user", userService.getUser(null, user.getUsername()));
			userListWaiting.add(m);
		}
		Integer count = groupService.getGroupUsersCnt(new Integer(gid), null);
		request.setAttribute("users", userList);
		request.setAttribute("usersWaiting", userListWaiting);
		request.setAttribute("count", count);
		User manager = userService.getUser(null, group.getManager());
		request.setAttribute("manager", manager);
		groupHelper.info(request, group);
	}

	/**
	 * 我的小组
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void mine(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String username = UserHelper.getuserFromCookie(request);
		Integer cnt = groupService.getGroupsCntByUsername(username,
				GroupUserRoleType.GROUP_USER_ROLE_MANAGER);
		request.setAttribute("canNew",
				cnt < BusinessConstants.USER_MAX_GROUP_SIZE);
		groupHelper.prepareCommends(request);
		if ("join".equalsIgnoreCase(action)) {
			String gid = request.getParameter("gid");
			GroupModel group = groupService.getGroup(new Integer(gid));
			Integer authLevel = group.getAuthLevel();
			// 直接可以加入
			if (authLevel <= GroupAuthLevelType.AUTH_LEVEL_MEMBER) {
				response.sendRedirect("group.shtml?method=join&gid=" + gid);
				return;
			}

			request.setAttribute("forbidden",
					authLevel >= GroupAuthLevelType.AUTH_LEVEL_FORBIDDEN);

			request.setAttribute("group", group);
			request.getRequestDispatcher("/group/joinGroup.jsp").forward(
					request, response);
			return;
		}
		if ("leave".equalsIgnoreCase(action)) {
			String gid = request.getParameter("gid");
			// GroupModel group = groupService.getGroup(new Integer(gid));
			GroupUser gu = groupService
					.getGroupUser(new Integer(gid), username);
			if (gu != null) {
				groupService.deleteGroupUser(gu);
			}
			response.sendRedirect("listGroups.action");
		}
		if ("list".equalsIgnoreCase(action)) {
			int start = HttpUtils.getStartParameter(request);
			int limit = WebConstants.PAGE_SIZE_15;
			String searchKey = request.getParameter("searchKey");
			List<GroupModel> groups = groupService.findGroups(null, searchKey,
					start, limit);
			request.setAttribute("groups", groups);
			// List groupsMap = new ArrayList();
			// for (GroupModel g : groups) {
			// Map m = new HashMap();
			// m.put("isManager", g.getManager().equals(username));
			// m.put("group", g);
			// m.put("topicCnt", groupTopicService.getGroupTopicsCnt(
			// g.getId(), null, true));
			// groupsMap.add(m);
			// }
			Integer count = groupService.getGroupsCntByUsername(username, null);
			request.setAttribute("searchKey", searchKey);
			request.setAttribute("groupsMap", this.wrapGroupList(groups,
					username));
			request.setAttribute("count", count);

			request.getRequestDispatcher("/group/myGroups.jsp").forward(
					request, response);
		}

	}

	@SuppressWarnings( { "unchecked" })
	private List wrapGroupList(List<GroupModel> groups, String username) {
		List groupsMap = new ArrayList();
		for (GroupModel g : groups) {
			Map m = new HashMap();
			m.put("isManager", g.getManager().equals(username));
			m.put("group", g);
			m.put("userCnt", groupService.getGroupUsersCnt(g.getId(), null));
			m.put("topicCnt", groupTopicService.getGroupTopicsCnt(g.getId(),
					null, true));
			groupsMap.add(m);
		}

		return groupsMap;
	}

	/**
	 * 保存小组
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String face = null;
		try {
			face = (String) getMultiFile(request, "face").get(FILE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String username = UserHelper.getuserFromCookie(request);
		String name = getParameter("name");
		String type = getParameter("type");
		String description = getParameter("discription");
		String authLevel = getParameter("authLevel");
		String tags = getParameter("tags");
		String url = getParameter("url");
		if (StringUtils.isNotEmpty(tags)) {
			tags = tags.trim();
			if (!tags.endsWith(",")) {
				tags += ",";
			}
		}
		String idStr = getParameter("gid");
		GroupModel model = null;

		model = groupService.getGroupByName(name);
		if (model != null && StringUtils.isEmpty(idStr)) {
			String msg = I18nMsgUtils.getInstance().getMessage(
					"group.name.another");
			request.setAttribute(BusinessConstants.ERROR_KEY, msg);
			request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
					request, response);
			return;
		}

		log.debug("url" + url);
		if (StringUtils.isNotEmpty(url)) {
			GroupModel groupModel = groupService.getGroupsByMagicUrl(url);
			if (groupModel != null) {
				// 确定不是本小组
				if (StringUtils.isEmpty(idStr)
						|| (StringUtils.isNotEmpty(idStr) && !groupModel
								.getId().equals(Integer.valueOf(idStr)))) {
					String msg = I18nMsgUtils.getInstance().getMessage(
							"group.url.another");
					request.setAttribute(BusinessConstants.ERROR_KEY, msg);
					request.getRequestDispatcher(UrlConstants.ERROR_PAGE)
							.forward(request, response);
					return;
				}

			}
		}

		if (StringUtils.isNotEmpty(face)) {
			// cut the image
			try {
				// create the images: mini default
				ImageUtils.getDefaultImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), false);
				ImageUtils.getMiniImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), false);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), 16);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), 80);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), 64);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), 48);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + face), 32);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isEmpty(idStr)) {
			log.debug("new GroupModel");
			model = new GroupModel();
			model.setManager(username);
			model.setCreateTime(DateUtils.getNow());
			model.setDiscription(description);
			model.setName(name);
			model.setTags(tags);
			model.setUpdateTime(DateUtils.getNow());
			model.setType(type);
			model.setAuthLevel(new Integer(authLevel));
			model.setUseLog4us(0);
			model.setMagicUrl(url);
			model.setHits(0);
			if (StringUtils.isNotEmpty(face)) {
				model.setFace(face);
			}
			model.setDeleteFlag(0);
			int groupCnt = groupService.getGroupsCnt(null, null);
			String code = "00000" + groupCnt;
			if (code.length() > 5) {
				code = code.substring(code.length() - 5);
			}
			model.setCode(code);
			groupService.saveGroup(model);

			model = groupService.getLatestGroupByManager(username);
			GroupUser gu = new GroupUser();
			gu.setDeleteFlag(0);
			gu.setGroupModel(model);
			gu.setRole(GroupUserRoleType.GROUP_USER_ROLE_MANAGER);
			gu.setUsername(username);
			groupService.saveGroupUser(gu);
			// TODO:注意这里的并发性
			userService.trigeEvent(this.userService.getUser(username),
					groupService.getGroup(null).getId() + "",
					UserEventType.GROUP_NEW);

		} else {
			log.debug("update GroupModel with:" + idStr);
			model = groupService.getGroup(new Integer(idStr));
			model.setDiscription(description);
			model.setType(type);
			model.setName(name);
			model.setTags(tags);
			model.setDeleteFlag(0);
			model.setMagicUrl(url);
			model.setUpdateTime(DateUtils.getNow());
			if (StringUtils.isNotEmpty(face)) {
				model.setFace(face);
			}
			model.setAuthLevel(new Integer(authLevel));
			model.setUseLog4us(0);
			groupService.saveGroup(model);
		}
		request.setAttribute("model", model);
		groupService.flushGroupCache();
		if (StringUtils.isEmpty(model.getMagicUrl())) {
			response.sendRedirect(UrlConstants.GROUP + model.getId());
		} else {
			response.sendRedirect(UrlConstants.GROUP + model.getMagicUrl());
		}

	}

	/**
	 * 小组类型的推荐tag，ajax加载
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@ReturnUrl(rederect = false, url = "/group/_groupTags.jsp")
	public void tags(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String groupType = request.getParameter("t");
		if (StringUtils.isNotEmpty(groupType)) {
			request.setAttribute("currType", GroupTypeHelper.typeMap
					.get(Integer.valueOf(groupType)));
		}
		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);
	}
}
