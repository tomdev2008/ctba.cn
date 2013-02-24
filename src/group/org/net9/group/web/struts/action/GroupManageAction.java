package org.net9.group.web.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.DateUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.GroupTypeHelper;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.group.GroupUser;

/**
 * 小组管理
 * 
 * @author gladstone
 * @since 2008-9-3
 */
public class GroupManageAction extends BusinessDispatchAction {

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
	public ActionForward deleteActivity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aid = request.getParameter("aid");
		ActivityModel model = activityService.getActivity(new Integer(aid));
		Integer gid = model.getGroupModel().getId();
		activityService.delActivity(model);
		return new ActionForward("groupManage.shtml?method=listActivities&gid="
				+ gid, true);
	}

	/**
	 * delete group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gid = request.getParameter("gid");
		GroupModel modle = groupService.getGroup(new Integer(gid));
		if (modle != null) {
			groupService.delGroup(modle);
		}
		return new ActionForward("groupManage.shtml?method=listGroups", true);
	}

	/**
	 * delete group topic
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroupTopic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tid = request.getParameter("tid");
		GroupTopic model = groupTopicService.getTopic(new Integer(tid));
		if (model != null) {
			groupTopicService.delTopic(model);
		}
		return new ActionForward("groupManage.shtml?method=listTopics", true);
	}

	/**
	 * delete group user
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroupUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		GroupUser gu = groupService.getGroupUser(new Integer(id));
		if (gu != null
				&& !gu.getRole().equals(
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER)) {
			groupService.deleteGroupUser(gu);
		}
		return new ActionForward("groupManage.shtml?method=listGroupUsers",
				true);
	}

	/**
	 * delete group image
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ImageModel imageModel = imageService.getImage(new Integer(id));
		if (imageModel != null) {
			imageService.delImage(imageModel);
		}
		return new ActionForward("groupManage.shtml?method=listImages", true);
	}

	/**
	 * group activities
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listActivities(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String groupId = request.getParameter("gid");
		Integer gid = StringUtils.isEmpty(groupId) ? null : Integer
				.parseInt(groupId);
		List<ActivityModel> models = activityService.findActivities(gid, null,
				null, null, start, limit);
		request.setAttribute("models", models);
		return mapping.findForward("group.act.list");
	}

	/**
	 * list groups
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("groupTypes", GroupTypeHelper.typeList);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String name = request.getParameter("name");
		Integer count = groupService.getGroupsCnt(null, name);
		List models = groupService.findGroups(null, name, start, limit);
		request.setAttribute("models", models);
		request.setAttribute("name", name);
		request.setAttribute("count", count);
		return mapping.findForward("group.list");
	}

	/**
	 * group users
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listGroupUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		Integer gid = null;
		Integer count = groupService.getGroupUsersCnt(gid, null);
		List models = groupService.findGroupUsers(gid, null, start, limit);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("group.user.list");
	}

	/**
	 * iamges
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listImages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String groupId = request.getParameter("gid");
		Integer gid = null;
		if (StringUtils.isNotEmpty(groupId)) {
			gid = new Integer(groupId);
		}
		Integer count = imageService.getImagesCnt(gid, null);
		List models = imageService.findImages("g", gid, start, limit);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("group.img.list");
	}

	/**
	 * list topics
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String groupId = request.getParameter("gid");
		String searchKey = null;
		if (HttpUtils.isMethodPost(request)) {
			searchKey = request.getParameter("searchKey");
			request.getSession().setAttribute("searchKey", searchKey);
		} else {
			searchKey = (String) request.getSession().getAttribute("searchKey");
		}
		Integer gid = null;
		if (StringUtils.isNotEmpty(groupId)) {
			gid = new Integer(groupId);
		}
		Integer count = groupTopicService.getGroupTopicsCnt(gid, null, true);
		List models = groupTopicService.findTopics(gid, null, false, "id",
				start, limit);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("group.topic.list");
	}

	/**
	 * #840 (系统管理员的权限)
	 * 
	 * 所有小组管理权限（后台管理）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String description = request.getParameter("discription");
		String authLevel = request.getParameter("authLevel");
		String tags = request.getParameter("tags");
		String url = request.getParameter("url");
		if (StringUtils.isNotEmpty(tags)) {
			tags = tags.trim();
			if (!tags.endsWith(",")) {
				tags += ",";
			}
		}
		String idStr = request.getParameter("gid");
		GroupModel model = null;

		model = groupService.getGroupByName(name);
		log.debug("update GroupModel with:" + idStr);
		model = groupService.getGroup(new Integer(idStr));
		model.setDiscription(description);
		model.setType(type);
		model.setName(name);
		model.setTags(tags);
		model.setMagicUrl(url);
		model.setUpdateTime(DateUtils.getNow());
		model.setAuthLevel(new Integer(authLevel));
		groupService.saveGroup(model);
		request.setAttribute("model", model);
		groupService.flushGroupCache();
		return new ActionForward("groupManage.shtml?method=listGroups", true);
	}

	/**
	 * #840 (系统管理员的权限)
	 * 
	 * 所有小组管理权限（后台管理）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward groupForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String groupId = request.getParameter("gid");
		GroupModel group = groupService.getGroup(new Integer(groupId));
		request.setAttribute("model", group);
		request.setAttribute("groupTypes", GroupTypeHelper.typeList);
		return mapping.findForward("group.form");
	}
}
