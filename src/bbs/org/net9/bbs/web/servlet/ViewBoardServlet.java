package org.net9.bbs.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.web.BoardHelper;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.TopicType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.User;

/**
 * 版面 改用Servlet
 * 
 * @author gladstone
 * 
 */
@WebModule("b")
@SuppressWarnings("serial")
@ReturnUrl(rederect = false, url = "/board/listTopics.jsp")
public class ViewBoardServlet extends BusinessCommonServlet {

	static Log log = LogFactory.getLog(ViewBoardServlet.class);

	@SuppressWarnings("unchecked")
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			CommonSystemException {
		String boardId = request.getParameter("bid");
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		Board board = boardService.getBoard(new Integer(boardId));
		if (board != null) {
			Board parentBoard = boardService.getBoard(board.getBoardParent());
			request.setAttribute("parentBoard", parentBoard);
		} else {
			throw new ModelNotFoundException();
		}

		String type = request.getParameter("type");
		Integer typeInt = TopicType.NORMAL.getValue();
		if ("prime".equals(type)) {
			// 精华文章
			typeInt = TopicType.PRIME.getValue();
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List models = topicService.findTopics(new Integer(boardId), start,
				limit, typeInt);
		// 置顶
		List topTopics = topicService.findTopics(new Integer(boardId), 0,
				BusinessConstants.MAX_INT, TopicType.TOP.getValue());
		int count = topicService.getTopicCnt(new Integer(boardId), typeInt);

		Integer userRole = UserSecurityType.OPTION_LEVEL_ALL;
		if (user != null) {
			userRole = boardService.checkUserRole(user.getUserId(),
					new Integer(boardId));
		}

		List topics = new ArrayList();
		// 合并文章列表
		if (topics != null) {
			if (topTopics != null)
				topics.addAll(topTopics);
			if (models != null)
				topics.addAll(models);
		} else {
			topics = models;
		}

		// 处理列表
		models = new ArrayList();
		if (topics != null) {
			Iterator it = topics.iterator();
			while (it.hasNext()) {
				Topic topic = (Topic) it.next();
				Map map = new HashMap();
				User author = userService.getUser(null, topic.getTopicAuthor());
				map.put("user", author);
				map.put("isAuthor", user != null
						&& author.equals(user.getUserName()));
				// #757 (论坛列表的 titile 中，字首的空格没有过滤)
				topic.setTopicTitle(topic.getTopicTitle().trim());
				map.put("topic", topic);
				map.put("tagStyleClass", topicService.getTopicTagClass(topic
						.getTopicId()));
				models.add(map);
			}
		}

		request.setAttribute("manager", userRole != null
				&& userRole >= UserSecurityType.OPTION_LEVEL_MANAGE);
		request.setAttribute("logined", user != null);
		request.setAttribute("count", count);
		request.setAttribute("board", board);

		request.setAttribute("boardType", board.getBoardType());
		// 版面信息
		request.setAttribute("boardInfo", UBBDecoder.decode(CommonUtils
				.htmlEncode(board.getBoardInfo()), new UBBSimpleTagHandler(),
				UBBDecoder.MODE_IGNORE));

		request.setAttribute("type", type);
		request.setAttribute("models", models);
		if (CommonUtils.isNotEmpty(board.getBoardMaster1())) {
			User bm = userService.getUser(null, board.getBoardMaster1());
			request.setAttribute("bm", bm);
		}
		BoardHelper.prepareHotTopics(topicService, board.getBoardId(), request);
		BoardHelper.prepareCommends(commonService, request);
	}
}
