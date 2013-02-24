package org.net9.group.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.hit.HitStrategy;
import org.net9.core.service.CommonService;
import org.net9.core.service.UserService;
import org.net9.core.types.CommendType;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupUser;
import org.net9.gallery.service.ImageService;
import org.net9.group.service.ActivityService;
import org.net9.group.service.GroupExtService;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.servlet.ServletModule;

public class GroupHelper {

	static Log log = LogFactory.getLog(GroupHelper.class);

	public static final String FILE_MARK = "attachment";

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final String FILE_SPLLITER = ";";

	public static final String MARKER_IS_USER = "__group_is_user";

	public static final String MARKER_IS_MANAGER = "__group_is_manager";

	public static final String MARKER_IS_WAITING = "__group_is_waiting";

	@Inject
	private GroupTopicService groupTopicService;

	@Inject
	private GroupService groupService;

	@Inject
	private GroupExtService groupExtService;

	@Inject
	private ImageService imageService;

	@Inject
	private UserService userService;

	@Inject
	private CommonService commonService;

	@Inject
	private ActivityService activityService;

	public GroupHelper() {
		Guice.createInjector(new ServletModule()).injectMembers(this);
	}

	@Inject
	private HitStrategy hitStrategy;

	/**
	 * 小组信息渲染
	 * 
	 * @param request
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	public void info(HttpServletRequest request, GroupModel model) {
		String username = UserHelper.getuserFromCookie(request);
		GroupUser gu = groupService.getGroupUser(model.getId(), username);
		boolean isUser = gu != null
				&& gu.getRole() > GroupUserRoleType.GROUP_USER_ROLE_WAIT;

		boolean isManager = gu != null
				&& gu.getRole().equals(
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER);

		request.setAttribute("topicCnt", groupTopicService.getGroupTopicsCnt(
				model.getId(), null, false));
		boolean waiting = gu != null
				&& gu.getRole().equals(GroupUserRoleType.GROUP_USER_ROLE_WAIT);
		// int hitPlus = new Random().nextInt(5) + 1;
		// Integer hit = model.getHits();
		// if (hit == null) {
		// hit = 0;
		// }
		// model.setHits(hit + hitPlus);
		// groupService.saveGroup(model);

		hitStrategy.hitGroup(model);

		request.setAttribute(MARKER_IS_WAITING, waiting);
		request.setAttribute(MARKER_IS_USER, isUser);
		request.setAttribute(MARKER_IS_MANAGER, isManager);

		request.setAttribute("group", model);
		request.setAttribute("userCnt", groupService.getGroupUsersCnt(model
				.getId(), null));
		request.setAttribute("activityCnt", activityService.getActivitiesCnt(
				model.getId(), null, null, null));
		Integer count = imageService.getImagesCnt(model.getId(), null);
		request.setAttribute("imageCnt", count);

		// #268 最近加入该组成员列表
		List<GroupUser> newUsers = groupService.findGroupUsers(model.getId(),
				new Integer[] { GroupUserRoleType.GROUP_USER_ROLE_NORMAL,
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER }, 0, 9);

		List<Map<Object, Object>> newUserMaps = new ArrayList<Map<Object, Object>>();
		for (GroupUser groupUser : newUsers) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("groupUser", groupUser);
			User u = userService.getUser(null, groupUser.getUsername());
			m.put("user", u);
			newUserMaps.add(m);
		}

		request.setAttribute("newUserMaps", newUserMaps);

		// // 最新小组活动(6条)
		// List<ActivityModel> activityList =
		// activityService.findActivities(model
		// .getId(), null, null, null, 0, 6);
		// List<Map<Object, Object>> models = ListWrapper.getInstance()
		// .formatActivityList(activityList, username);
		// request.setAttribute("activityList", models);

		// ==========用户事件==========>>>
		if (newUsers.size() > 0) {
			List<String> uList = new ArrayList<String>();
			for (GroupUser u : newUsers) {
				uList.add((String) u.getUsername());
			}
			List<UserLog> userLogs = userService.findUserlogs(uList, 0, 10,
					UserEventWrapper.DEFAULT_WANTED_TYPES);
			List<Map<Object, Object>> userLogsMapList = ListWrapper
					.getInstance().formatUserLogList(userLogs);
			// 保持在10个
			while (userLogsMapList.size() > 6) {
				userLogsMapList.remove(userLogsMapList.size() - 1);
			}
			request.setAttribute("userEventList", userLogsMapList);
		}

		// <<<==========用户事件==========

		request.setAttribute("groupLinkList", this.groupExtService
				.findGroupLinks(model.getId(), 0, BusinessConstants.MAX_INT));

		request.setAttribute("groupRank", this.groupExtService
				.getGroupRank(model.getId()));

	}

	/**
	 * 把推荐链接加入当前会话
	 * 
	 * @param request
	 */
	public void prepareCommends(HttpServletRequest request) {
		List<MainCommend> commendList = commonService.findMainCommendsByType(
				CommendType.GROUP.getValue(), 0, 10);
		request.setAttribute("commendList", commendList);
	}

	/**
	 * 有权限操作的用户:
	 * 
	 * <li>1 作者
	 * <li>2 小组管理员
	 * 
	 * @param request
	 * @param model
	 * @param groupModel
	 * @throws InvalidAuthorSecurityException
	 */
	public void authUserForCurrentPojo(HttpServletRequest request,
			BaseModel model, GroupModel groupModel)
			throws InvalidAuthorSecurityException {
		if (model == null) {
			return;
		}

		String username = UserHelper.getuserFromCookie(request);
		GroupUser gu = groupService.getGroupUser(groupModel.getId(), username);
		boolean isManager = gu != null
				&& gu.getRole().equals(
						GroupUserRoleType.GROUP_USER_ROLE_MANAGER);

		if (isManager) {
			return; // 小组长,OK
		}

		// #840 (系统管理员的权限)
		if (userService.isEditor(username)) {
			return; // 编辑,OK
		}

		String authorName = UserHelper.getAuthorName(model);
		if (StringUtils.isNotEmpty(authorName) && !authorName.equals(username)) {
			throw new InvalidAuthorSecurityException("error.noOption.noLogin");
		}
	}

	/**
	 * 上载文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> uploadAttachments(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DiskFileUpload upload = new DiskFileUpload();
		Map<String, Object> fields = new HashMap<String, Object>();
		String attachmentStr = "";
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		if (items != null) {
			for (FileItem item : items) {
				if (item.isFormField()) {
					fields.put(item.getFieldName(), item
							.getString(DEFAULT_ENCODING));
				} else {
					fields.put(item.getFieldName(), item);
				}
				try {
					log.debug("item : " + item.getFieldName());
					// 注意这里，名称必须是有后缀的
					if (item.getFieldName().indexOf(FILE_MARK) >= 0) {
						FileItem uplFile = (FileItem) item;
						if (uplFile != null
								&& StringUtils.isNotEmpty(uplFile.getName())) {
							String filename = ImageUtils
									.wrapImageNameByTime(uplFile.getName());
							String fileDir = SystemConfigVars.UPLOAD_DIR_PATH;
							String fullPath = request
									.getSession()
									.getServletContext()
									.getRealPath(
											fileDir + File.separator + filename);
							ImageUtils.checkDir(fullPath);
							File pathToSave = new File(fullPath);
							uplFile.write(pathToSave);
							log.info("upload file: " + fullPath);
							attachmentStr += filename + FILE_SPLLITER;
						}
					}
				} catch (Exception ex) {
					log.error(ex.getMessage());
					ex.printStackTrace();
					continue;
				}

			}
		}

		if (attachmentStr.endsWith(FILE_SPLLITER)) {
			attachmentStr = attachmentStr.substring(0,
					attachmentStr.length() - 1);
		}
		fields.put(FILE_MARK, attachmentStr);
		return fields;
	}
}
