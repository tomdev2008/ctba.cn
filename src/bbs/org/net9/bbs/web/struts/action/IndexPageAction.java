package org.net9.bbs.web.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.types.CommendType;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.ListWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.view.ViewHotUser;

/**
 * 
 * BBS模块首页
 * 
 * @author gladstone
 */
public class IndexPageAction extends BusinessDispatchAction {

	/** Logger */
	public static Log log = LogFactory.getLog(IndexPageAction.class);


	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ==========BBS==========>>>
		// 顶级版面
		List<Board> topBorads = boardService.findTopBoards(0,
				BusinessConstants.MAX_INT);
		List boardsList = ListWrapper.getInstance().formatBoardList4IndexPage(
				topBorads);
		request.setAttribute("boards", boardsList);

		// 最新文章
		List<Topic> newTopics = topicService.findNewTopicsForIndex(0, 15);
		// .getTopics(null, 0, 10, Constant.TOPIC_TYPE_NORMAL);
		List newTopicsList = ListWrapper.getInstance().formatTopicList(
				newTopics);
		request.setAttribute("topics", newTopicsList);

		// 最近热贴
		List<Topic> hotTopics = topicService.findHotTopicsByTime(null, 10, 0,
				15);
		// .getHotTopicsForIndex();
		List hotTopicsList = ListWrapper.getInstance().formatTopicList(
				hotTopics);
		request.setAttribute("hotTopics", hotTopicsList);

		// 最近更新的文章(最近回复)
		List<Topic> lastUpdatedTopics = topicService.findTopics(null, 0, 15,
				null);
		List lastUpdatedTopicList = ListWrapper.getInstance().formatTopicList(
				lastUpdatedTopics);
		request.setAttribute("lastUpdatedTopics", lastUpdatedTopicList);

		// // BBS标题
		// request.setAttribute("bbsHead", commonService.getBBSHead());
		// <<<==========BBS==========
		
		
		// ==========共通==========>>>
		// 用户数目
		request.setAttribute("userCnt", 1 + userService.getUserCnt(null, null));

		// 在线用户数目
		Integer onlineCnt = commonService.getOnlineCnt();
		request.setAttribute("onlineCnt", onlineCnt);

		// 新用户
		List newUsers = userService.findUsers(0, 5);
		request.setAttribute("newUsers", newUsers);

		// 活跃用户
		List<ViewHotUser> stars = boardService.findHotUsersForIndex();
		request.setAttribute("stars", stars);
		// <<<==========共通==========

		List<MainCommend> commendList = this.commonService
				.findMainCommendsByType(CommendType.INDEX_PAGE.getValue(), 0,
						10);
		request.setAttribute("commendList", commendList);
		log.debug("forwarding...");
		return mapping.findForward("index");
	}
}
