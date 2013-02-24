package org.net9.group.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.GroupTypeHelper;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.ListWrapper;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.view.ViewRandomGroup;
import org.net9.group.web.GroupHelper;

/**
 * 小组系统首页<br>
 * 
 * @author gladstone
 * 
 */
@WebModule("ghome")
@ReturnUrl(rederect = false, url = "/group/indexPage.jsp")
@SuppressWarnings("serial")
public class IndexPageServlet extends BusinessCommonServlet {

	private final GroupHelper groupHelper = new GroupHelper();

	@Override
	@SuppressWarnings("unchecked")
	public void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ==========用户==========>>>
		// 最新用户
		List newUsers = groupService.findNewGroupUsers(0, 5);
		request.setAttribute("newUsers", newUsers);

		// <<<==========用户==========

		// ==========小组==========>>>
		// 新创建小组
		List<GroupModel> newGroups = groupService.findNewGroups(null, 0, 6);
		request.setAttribute("newGroups", newGroups);
		List newGroupsMap = ListWrapper.getInstance().formatGroupModelList(
				newGroups);
		request.setAttribute("newGroupsMap", newGroupsMap);

		// 随机小组
		List<ViewRandomGroup> randomGroups = groupService.findRandomGroups(0,
				12);
		request.setAttribute("randomGroups", randomGroups);

		// 热门小组
		List<GroupModel> hotGroups = groupService.findHotGroups(0, 6);
		List hotGroupsMap = ListWrapper.getInstance().formatGroupModelList(
				hotGroups);
		request.setAttribute("hotGroupsMap", hotGroupsMap);

		request.setAttribute("groupCnt", groupService.getGroupsCnt(null, null));
		// <<<==========小组==========

		// ==========小组话题和其他==========>>>
		// 新发表的小组话题
		List<GroupTopic> newTopics = groupTopicService.findTopics(null, null,
				false, "id", 0, 10);
		request.setAttribute("newTopics", newTopics);
		List<Map> newTopicsMap = ListWrapper.getInstance().formatGroupTopic(
				newTopics, false);
		request.setAttribute("newTopicsMap", newTopicsMap);

		// 最新的小組相片
		List<ImageModel> images = imageService.findImages("g", null, 0, 8);
		request.setAttribute("images", images);

		// 小組類型列表
		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		// 当前用户加入小组的最新话题
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isNotEmpty(username)) {
			List<GroupTopic> topics = groupTopicService.findNewTopicsForUser(
					username, false, 0, 10);

			request.setAttribute("topics4User", topics);
			List<Map> topicsMap4User = ListWrapper.getInstance()
					.formatGroupTopic(topics, false);
			request.setAttribute("topicsMap4User", topicsMap4User);

		}
		// <<<==========小组话题和其他==========

		// ==========小组活动和其他==========>>>
		List<ActivityModel> activityList = activityService.findActivities(null,
				null, null, null, 0, 12);
		List<Map<Object, Object>> models = ListWrapper.getInstance()
				.formatActivityList(activityList, username);
		request.setAttribute("newActivityList", models);
		// <<<==========小组活动和其他==========
		groupHelper.prepareCommends(request);
	}
}