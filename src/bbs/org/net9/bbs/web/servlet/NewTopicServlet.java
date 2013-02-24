package org.net9.bbs.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.web.BoardHelper;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;

/**
 * post a new topic
 * 
 * @author gladstone
 * 
 */
@WebModule("newTopic")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@SuppressWarnings("serial")
public class NewTopicServlet extends BusinessCommonServlet {

	public static Log log = LogFactory.getLog(NewTopicServlet.class);

	@ReturnUrl(rederect = false, url = "/board/newTopic.jsp")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String boardId = request.getParameter("bid");
		List<Board> bList = this.boardService.findBottomBoards(0,
				BusinessConstants.MAX_INT);
		request.setAttribute("boardList", bList);
		if (StringUtils.isNotEmpty(boardId)) {
			Board board = boardService.getBoard(new Integer(boardId));
			request.setAttribute("boardInfo", UBBDecoder.decode(CommonUtils
					.htmlEncode(board.getBoardInfo()),
					new UBBSimpleTagHandler(), UBBDecoder.MODE_IGNORE));
			request.setAttribute("board", board);
			BoardHelper.prepareHotTopics(topicService, board.getBoardId(),
					request);
		} else {
			// BoardHelper.prepareHotTopics(topicService, null, request);
		}
		request.setAttribute("forbiddenWords", commonService.getConfig()
				.getForbiddenWords());

	}

	@SuppressWarnings("unchecked")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		// User user = userService.getUser(null, username);
		String topicAttachPath = "";
		String topicAttachName = "";
		try {
			Map map = getMultiFile(request, "topicAttach");
			topicAttachPath = (String) map.get(BusinessCommonServlet.FILE_PATH);
			topicAttachName = (String) map.get(BusinessCommonServlet.FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String topicTitle = getParameter("topicTitle");
		String topicBoardIdStr = getParameter("topicBoardId");
		String topicContent = getParameter("topicContent");
		String topicPrivate = getParameter("topicPrivate");
		String topicScore = getParameter("topicScore");
		String topicIP = HttpUtils.getIpAddr(request);
		int topicBoardId = Integer.parseInt(topicBoardIdStr);
		topicTitle = topicTitle.trim();
		List filterList = commonService.getForbiddenWordsAsList();
		// check if the title and the content is dirty
		boolean dirty = CommonUtils.checkDirty(topicTitle, filterList)
				|| CommonUtils.checkDirty(topicContent, filterList);
		if (dirty) {
			this.sendError(request, response, "topic.dirtyWords");
			return;
		}
		// check the user is forbiddened or not
		boolean checkFbn = boardService.isForbiddened(new Integer(
				topicBoardIdStr), null, username);
		if (checkFbn) {
			this.sendError(request, response, "user.noOptionInBoard");
			return;
		}

		Topic model = new Topic();
		model.setTopicUpdateTime(StringUtils.getTimeStrByNow());
		model.setTopicAttachName(topicAttachName);
		model.setTopicAttachPath(topicAttachPath);
		model.setTopicAuthor(username);
		model.setTopicBoardId(topicBoardId);
		model.setTopicContent(topicContent);
		model.setTopicHits(0);
		model.setTopicIP(topicIP);
		model.setTopicIsRe(0);
		model.setTopicOriginId(-1);
		model.setTopicPrime(0);
		model.setTopicPrivate(CommonUtils.isNotEmpty(topicPrivate) ? 1 : 0);
		model.setTopicTop(0);
		model.setTopicTitle(topicTitle);
		model.setTopicTime(StringUtils.getTimeStrByNow());
		model.setTopicState(1);
		// #852 (CTBA社区财富(CTB))
		if (StringUtils.isEmpty(topicScore)) {
			model.setTopicScore(0);
		} else {
			model.setTopicScore(Integer.valueOf(topicScore));
		}

		model.setTopicReNum(0);
		topicService.saveTopic(model, false);
		Board board = boardService.getBoard(new Integer(topicBoardId));
		// 2.board's topicCnt
		board.setBoardTopicNum(board.getBoardTopicNum() + 1);
		int boardTodayNum = board.getBoardTodayNum() + 1;
		board.setBoardTodayNum(boardTodayNum);
		boardService.saveBoard(board, true);
		model = topicService.getTopic(null);
		if (model != null) {
			userService.trigeEvent(this.userService.getUser(username), model
					.getTopicId()
					+ "", UserEventType.TOPIC_NEW);
			response.sendRedirect(UrlConstants.TOPIC + model.getTopicId());
			return;
		}
		response.sendRedirect(UrlConstants.BOARD + topicBoardId);
	}

}
