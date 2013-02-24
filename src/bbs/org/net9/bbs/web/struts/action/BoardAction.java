package org.net9.bbs.web.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Reply;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.User;

/**
 * 
 * the board action , including topic options
 * 
 * @author gladstone
 */
public class BoardAction extends BusinessDispatchAction {

	private final static Log log = LogFactory.getLog(BoardAction.class);

	/**
	 * bad hit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	@Deprecated
	public ActionForward bad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rid = request.getParameter("rid");
		Reply reply = replyService.getReply(new Integer(rid));
		Integer badHits = reply.getTopicBadHits();
		if (badHits == null) {
			badHits = 0;
		}
		if (canHit(request, rid)) {
			badHits++;
		}
		reply.setTopicBadHits(badHits);
		replyService.saveReply(reply);
		String message = "" + badHits;
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@Deprecated
	private boolean canHit(HttpServletRequest request, String id) {
		boolean reval = false;
		String hitDone = (String) request.getSession().getAttribute(
				"replyHitDone_" + id);
		if (StringUtils.isEmpty(hitDone)) {
			reval = true;
			request.getSession().setAttribute("replyHitDone_" + id, "hitDone");
		}
		return reval;
	}

	/**
	 * good hit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public ActionForward good(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rid = request.getParameter("rid");
		Reply reply = replyService.getReply(new Integer(rid));
		Integer goodHits = reply.getTopicGoodHits();
		if (goodHits == null) {
			goodHits = 0;
		}
		if (canHit(request, rid)) {
			goodHits++;
		}
		reply.setTopicGoodHits(goodHits);
		replyService.saveReply(reply);
		String message = "" + goodHits;
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");
	}

	/**
	 * list the topics of a board
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public ActionForward listTopics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return new ActionForward("b.action?bid=" + request.getParameter("bid")
				+ "&type=" + request.getParameter("type"), true);
	}

	/**
	 * 
	 * used in a frame
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String boardName = null;
		String boardType = null;
		if (HttpUtils.isMethodPost(request)) {
			boardName = request.getParameter("boardName");
			boardType = request.getParameter("boardType");
			request.getSession().setAttribute("boardNameSearch", boardName);
			request.getSession().setAttribute("boardTypeSearch", boardType);
		} else {
			boardName = (String) request.getSession().getAttribute(
					"boardNameSearch");
			boardType = (String) request.getSession().getAttribute(
					"boardTypeSearch");
		}
		request.setAttribute("boardName", boardName);
		request.setAttribute("boardType", boardType);

		Integer type = null;
		if (CommonUtils.isNotEmpty(boardType)) {
			type = new Integer(boardType);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List models = boardService.findBoards(type, boardName, start, limit);
		request.setAttribute("models", models);
		request.setAttribute("boardName", boardName);
		request
				.setAttribute("count", boardService
						.getBoardCnt(type, boardName));
		return mapping.findForward("board.search");
	}

	/**
	 * transe topic to another board
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward transeTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String tid = request.getParameter("tid");
		String bid = request.getParameter("bid");
		User user = userService.getUser(null, username);
		Topic topic = topicService.getTopic(new Integer(tid));
		Board srcBoard = boardService.getBoard(topic.getTopicBoardId());
		Integer userRole = boardService.checkUserRole(user.getUserId(),
				srcBoard.getBoardId());
		log.debug("userRole>" + userRole);
		if (HttpUtils.isMethodGet(request)) {
			List<Board> boards = boardService.findBoards(null, null, 0,
					BusinessConstants.MAX_INT);
			List<Board> boardsWithParent = new ArrayList<Board>();
			for (Board b : boards) {
				if (b.getBoardParent() != null && b.getBoardParent() > 0) {
					boardsWithParent.add(b);
				}
			}
			request.setAttribute("boards", boardsWithParent);
			request.setAttribute("topic", topic);
			request.setAttribute("board", srcBoard);
			request.setAttribute("isManager", userRole != null
					&& userRole >= UserSecurityType.OPTION_LEVEL_MANAGE);
			return mapping.findForward("topic.transe");
		}
		Board tarBoard = boardService.getBoard(new Integer(bid));

		log.debug("Transelate:" + srcBoard.getBoardId() + "->>"
				+ tarBoard.getBoardId());

		if (userRole == UserSecurityType.OPTION_LEVEL_MANAGE
				&& !tarBoard.getBoardId().equals(srcBoard.getBoardId())) {
			topic.setTopicBoardId(tarBoard.getBoardId());
			topicService.saveTopic(topic, true);
			// TODO: Change this performance
			tarBoard.setBoardTopicNum(tarBoard.getBoardTopicNum() + 1);
			srcBoard.setBoardTopicNum(srcBoard.getBoardTopicNum() - 1);
			List<Reply> replies = replyService.findReplys(topic.getTopicId()
					.intValue(), 0, BusinessConstants.MAX_INT);
			for (Reply r : replies) {
				r.setTopicBoardId(tarBoard.getBoardId());
				replyService.saveReply(r);
				tarBoard.setBoardReNum(tarBoard.getBoardReNum() + 1);
				srcBoard.setBoardReNum(srcBoard.getBoardReNum() - 1);
			}
			boardService.saveBoard(tarBoard, true);
			boardService.saveBoard(srcBoard, true);
		}
		// TODO: Change this later
		return new ActionForward(UrlConstants.TOPIC + topic.getTopicId(), true);

	}

	/**
	 * view a topic
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public ActionForward viewTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return new ActionForward("t.action?tid=" + request.getParameter("tid"),
				true);
	}
}
