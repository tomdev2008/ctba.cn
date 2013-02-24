package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.ListWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.subject.Subject;
import org.net9.domain.model.subject.SubjectTopic;
import org.net9.subject.service.SubjectService;

import com.google.inject.Inject;
@WebModule("basketballPlugin")
@SuppressWarnings("serial")
public class BasketballPluginServlet extends BusinessCommonServlet {

	public static final int CORE_BOARD_ID = 5;

	public static final int CORE_GROUP_ID = 48;

	public static final String CORE_GROUP_TAG = "篮球";

	public static final String CORE_SUBJECT_TYPE = "1";

	@Inject
	private SubjectService subjectService;

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/apps/basketball/indexPage.jsp")
	public void indexPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// ==========BBS==========>>>
		Board board = boardService.getBoard(CORE_BOARD_ID);
		request.setAttribute("board", board);

		// 最近热贴
		List<Topic> hotTopics = topicService.findHotTopicsByTime(CORE_BOARD_ID,
				15, 0, 15);
		List hotTopicsList = ListWrapper.getInstance()
				.formatTopicList(hotTopics);
		request.setAttribute("hotTopics", hotTopicsList);

		// 最近更新的文章(最近回复)
		List<Topic> lastUpdatedTopics = topicService.findTopics(CORE_BOARD_ID,
				0, 15, null);
		List lastUpdatedTopicList = ListWrapper.getInstance().formatTopicList(
				lastUpdatedTopics);
		request.setAttribute("lastUpdatedTopics", lastUpdatedTopicList);

		// <<<==========BBS==========

		// ==========group==========>>>
		List<GroupModel> gList = this.groupService.findGroupsByTag(null, null,
				CORE_GROUP_TAG, 0, 50);
		// gList.add(this.groupService.getGroup(CORE_GROUP_ID));

		Integer[] gids = new Integer[gList.size()];
		for (int i = 0; i < gList.size(); i++) {
			gids[i] = gList.get(i).getId();
		}

		// 最近更新小组文章
		List<GroupTopic> updatedGroupTopics = groupTopicService
				.findTopicsByGroupIds(gids, false, null, 0, 15);
		request.setAttribute("updatedGroupTopicsMap", ListWrapper.getInstance()
				.formatGroupTopic(updatedGroupTopics, false));
		// 最热小组文章
		List<GroupTopic> hotGroupTopics = groupTopicService
				.findTopicsByGroupIds(gids, false, "hits", 0, 15);
		request.setAttribute("hotGroupTopicsMap", ListWrapper.getInstance()
				.formatGroupTopic(hotGroupTopics, false));

		request.setAttribute("groupsMap", ListWrapper.getInstance()
				.formatGroupModelList(gList));
		// <<<==========group==========

		// ==========专题==========>>>
		List newSubjects = subjectService.findSubjects(null, CORE_SUBJECT_TYPE,
				0, 20);
		// TODO: refactor this
		List subsMapList = new ArrayList();
		for (int j = 0; j < newSubjects.size(); j++) {
			Map subMap = new HashMap();
			Subject s = (Subject) newSubjects.get(j);
			Integer topicCnt = subjectService.getSubjectTopicCnt(s.getId(),
					null);
			SubjectTopic t = subjectService.getNewSubjectTopic(s.getId());
			subMap.put("newTopic", t);
			subMap.put("subject", s);
			subMap.put("topicCnt", topicCnt);
			subsMapList.add(subMap);
		}
		request.setAttribute("newSubjects", subsMapList);

		// <<<==========专题==========

	}
}
