package org.net9.vote.web.struts.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.ctba.VoteAnswer;
import org.net9.domain.model.ctba.VoteComment;

/**
 * 投票action
 * 
 * @author gladstone
 * @since 2007/10
 */
public class VoteAction extends BusinessDispatchAction {

	private final static Log log = LogFactory.getLog(VoteAction.class);

	/**
	 * 准备右侧共通部分
	 * 
	 * @param request
	 */
	private void _right(HttpServletRequest request) {
		List<Vote> newestVoteList = voteService.findVotes(null, null, null,
				null, 0, 15);
		request.setAttribute("newestVoteList", newestVoteList);
		List<Vote> hotVoteList = voteService.findHotVotes(null, null, null, 0,
				15);
		request.setAttribute("hotVoteList", hotVoteList);
	}

	/**
	 * buld answer string from request
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String buildAnswer(HttpServletRequest request) {
		Enumeration e = request.getParameterNames();
		String spliter = "\n";
		String reval = "";
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("answer_") >= 0) {
				String val = key.substring(key.indexOf("answer_")
						+ "answer_".length());// request.getParameter(key);
				reval += val + spliter;
			}
		}
		if (reval.endsWith(spliter)) {
			reval = reval.substring(0, reval.lastIndexOf(spliter));
		}
		return reval;
	}

	/**
	 * 构建投票结果
	 * 
	 * @param answersList
	 * @param realAnswers
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List buildResultList(List<String> answersList,
			List<VoteAnswer> realAnswers) {
		List resultList = new ArrayList();
		for (String ans : answersList) {
			Map m = new HashMap();
			m.put("answer", ans);
			Integer votedCnt = 0;
			double percent = 0;
			m.put("votedCnt", votedCnt);
			m.put("percent", percent);
			resultList.add(m);
		}
		if (realAnswers == null) {
			return resultList;
		}

		int totalCnt = 0;
		for (VoteAnswer va : realAnswers) {
			List ans = parseAnswers(va.getAnswers());
			for (int i = 0; i < ans.size(); i++) {
				totalCnt++;
			}
		}
		for (VoteAnswer va : realAnswers) {
			List ans = parseAnswers(va.getAnswers());
			for (int i = 0; i < ans.size(); i++) {
				int index = new Integer((String) ans.get(i));
				Map m = (Map) resultList.get(index);
				Integer votedCnt = (Integer) m.get("votedCnt");
				votedCnt++;
				double percent = (double) votedCnt / (double) totalCnt;
				m.put("votedCnt", votedCnt);
				m.put("percent", percent);
			}
		}
		return resultList;
	}

	/**
	 * 删除评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		VoteComment model = voteService.getVoteComment(new Integer(vid));

		// validate user
		UserHelper.authUserSimply(request, model);

		Integer voteId = model.getVote().getId();
		// if (model.getUsername().equals(username)) {
		voteService.delVoteComment(model);
		// }
		return new ActionForward(UrlConstants.VOTE + voteId, true);
	}

	/**
	 * 刪除投票
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		Vote model = voteService.getVote(new Integer(vid));

		// validate user
		UserHelper.authUserSimply(request, model);

		Integer bid = model.getBoardId();
		// if (model.getUsername().equals(username)) {
		voteService.delVote(model);
		// }
		return new ActionForward("vote.shtml?method=listVotes&bid=" + bid, true);
	}

	/**
	 * do vote form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		String username = UserHelper.getuserFromCookie(request);
		Vote v = voteService.getVote(new Integer(vid));
		if (v.getBoardId() != null && v.getBoardId() > 0) {
			Board b = boardService.getBoard(v.getBoardId());
			request.setAttribute("board", b);
		}

		// check if the user already voted
		Integer voted = voteService.getVotesAnswerCnt(v.getId(), username);
		if (voted > 0) {
			this.sendError(request, response, "vote.done");
			return null;
		}
		// check if the vote is over
		String now = StringUtils.getTimeStrByNow();
		if (DateUtils.diff(now, v.getOverTime()) > 0) {
			this.sendError(request, response, "vote.vote.over");
			return null;
		}
		Integer voteCnt = voteService.getVotesAnswerCnt(v.getId(), null);
		String answers = v.getAnswers();
		List<String> answersList = parseAnswers(answers);
		request.setAttribute("answersCnt", answersList.size());
		request.setAttribute("answersList", answersList);
		request.setAttribute("vote", v);
		request.setAttribute("voteCnt", voteCnt);
		User u = userService.getUser(null, v.getUsername());
		request.setAttribute("user", u);
		this._right(request);
		return mapping.findForward("vote.do");
	}

	/**
	 * list votes
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listVotes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		Integer bid = null;
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		String username = "";
		if ("mine".equals(type)) {
			username = UserHelper.getuserFromCookie(request);
			request.setAttribute("_is_mine", "true");
		}
		Integer count = voteService.getVotesCnt(bid, username, null);
		List<Vote> models = voteService.findVotes(bid, username, null, null,
				start, limit);
		List<Map<Object, Object>> voteMapList = ListWrapper.getInstance()
				.formatVoteList(models);
		request.setAttribute("voteMapList", voteMapList);
		log.debug(count);
		request.setAttribute("count", count);
		request.setAttribute("models", models);
		this._right(request);
		return mapping.findForward("vote.list");
	}

	/**
	 * parse answer to a list
	 * 
	 * @param answers
	 * @return
	 */
	private List<String> parseAnswers(String answers) {
		String spliter = "\n";
		if (CommonUtils.isEmpty(answers)) {
			return null;
		}
		String array[] = answers.split(spliter);
		List<String> l = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			l.add(array[i]);
		}
		return l;
	}

	/**
	 * do vote and save the answer
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAnswer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		String username = UserHelper.getuserFromCookie(request);
		Vote v = voteService.getVote(new Integer(vid));
		// check if the user already voted
		Integer voted = voteService.getVotesAnswerCnt(v.getId(), username);
		if (voted > 0) {
			// String msg = I18nMsgUtils.getInstance().getMessage("vote.done");
			// request.setAttribute(BusinessConstants.ERROR_KEY, msg);
			// RequestDispatcher rd = request
			// .getRequestDispatcher(UrlConstants.ERROR_PAGE);
			// rd.forward(request, response);
			this.sendError(request, response, "vote.done");
			return null;
		}
		// check if the vote is over
		String now = StringUtils.getTimeStrByNow();
		if (DateUtils.diff(now, v.getOverTime()) > 0) {
			// String msg = I18nMsgUtils.getInstance().getMessage("vote.over");
			// request.setAttribute(BusinessConstants.ERROR_KEY, msg);
			// RequestDispatcher rd = request
			// .getRequestDispatcher(UrlConstants.ERROR_PAGE);
			// rd.forward(request, response);
			this.sendError(request, response, "vote.over");
			return null;
		}

		VoteAnswer an = new VoteAnswer();
		String answers = buildAnswer(request);
		log.debug("got built answers:" + answers);
		String otherText = null;
		an.setAnswers(answers);
		an.setCreateTime(StringUtils.getTimeStrByNow());
		an.setOtherText(otherText);
		an.setUsername(username);
		an.setVoteId(new Integer(vid));
		voteService.saveVoteAnswer(an);
		userService.trigeEvent(this.userService.getUser(username), v.getId()
				+ "", UserEventType.VOTE_DO);
		return new ActionForward(UrlConstants.VOTE + vid, true);
	}

	public ActionForward saveComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		String username = UserHelper.getuserFromCookie(request);
		Vote v = voteService.getVote(new Integer(vid));
		VoteComment model = new VoteComment();
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setBody(request.getParameter("body"));
		model.setUsername(username);
		model.setVote(v);
		model.setIp(HttpUtils.getIpAddr(request));
		voteService.saveVoteComment(model);
		userService.trigeEvent(this.userService.getUser(username), v.getId()
				+ "", UserEventType.VOTE_COMMENT);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapVote(v) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}

		return new ActionForward(UrlConstants.VOTE + vid, true);
	}

	/**
	 * save vote
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String bid = request.getParameter("bid");
		String vid = request.getParameter("vid");
		String answers = request.getParameter("answers");
		String canBeOther = request.getParameter("canBeOther");
		Vote model = null;
		if (CommonUtils.isNotEmpty(vid)) {
			model = voteService.getVote(new Integer(vid));

			// validate user
			UserHelper.authUserSimply(request, model);

			model.setUpdateTime(StringUtils.getTimeStrByNow());
			model.setAnswers(answers);

		} else {
			model = new Vote();
			model.setUsername(username);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			if (CommonUtils.isNotEmpty(bid)) {
				model.setBoardId(new Integer(bid));
			}
			model.setAnswers(answers);
		}
		PropertyUtil.populateBean(model, request);
		model.setCanBeOther(CommonUtils.isNotEmpty(canBeOther) ? 1 : 0);
		voteService.saveVote(model);

		if (CommonUtils.isNotEmpty(vid)) {
			return new ActionForward(UrlConstants.VOTE + vid, true);
		} else {
			model = voteService.getLastVote(null);
			userService.trigeEvent(this.userService.getUser(username), ""
					+ model.getId(), UserEventType.VOTE_NEW);
			return new ActionForward("vote.shtml?method=listVotes&bid=" + bid,
					true);
		}
	}

	/**
	 * view vote result
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward viewVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		String username = UserHelper.getuserFromCookie(request);
		Vote v = voteService.getVote(new Integer(vid));

		// #105 点击数
		int hitPlus = new Random().nextInt(5) + 1;
		v.setHits(v.getHits() + hitPlus);
		voteService.saveVote(v);

		boolean isAuthor = v.getUsername().equals(username);
		if (v.getBoardId() != null && v.getBoardId() > 0) {
			Board b = boardService.getBoard(v.getBoardId());
			request.setAttribute("board", b);
		}
		Integer voteCnt = voteService.getVotesAnswerCnt(v.getId(), null);
		String answers = v.getAnswers();
		List<String> answersList = parseAnswers(answers);

		List<VoteAnswer> realAnswers = voteService.findVoteAnswers(v.getId(),
				null, 0, BusinessConstants.MAX_INT);
		if (v.getBoardId() != null && v.getBoardId() > 0) {
			Board b = boardService.getBoard(v.getBoardId());
			request.setAttribute("board", b);
		}
		List resultList = buildResultList(answersList, realAnswers);
		User u = userService.getUser(null, v.getUsername());
		request.setAttribute("user", u);
		request.setAttribute("answersCnt", answersList.size());
		request.setAttribute("resultList", resultList);
		request.setAttribute("answersList", answersList);
		request.setAttribute("vote", v);
		request.setAttribute("isAuthor", isAuthor);
		request.setAttribute("voteCnt", voteCnt);

		int start = HttpUtils.getStartParameter(request);
		int limit = 15;
		Integer count = voteService.getVotesCommentCnt(v.getId(), null);
		List<VoteComment> commentList = voteService.findVoteComments(v.getId(),
				null, start, limit);
		List<Map<String, Object>> commentMapList = new ArrayList<Map<String, Object>>();
		for (VoteComment comment : commentList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("comment", comment);
			m.put("user", userService.getUser(null, comment.getUsername()));
			commentMapList.add(m);
		}

		request.setAttribute("count", count);
		request.setAttribute("commentMapList", commentMapList);
		this._right(request);
		return mapping.findForward("vote.view");
	}

	/**
	 * vote form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward voteForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String vid = request.getParameter("vid");
		String bid = request.getParameter("bid");
		if (CommonUtils.isNotEmpty(vid)) {
			Vote v = voteService.getVote(new Integer(vid));
			List<VoteAnswer> realAnswers = voteService.findVoteAnswers(v
					.getId(), null, 0, BusinessConstants.MAX_INT);
			if (realAnswers != null && realAnswers.size() > 0) {
				// 已经有人投票了，表单不能再修改
				// String msg = I18nMsgUtils.getInstance().getMessage(
				// "bbs.vote.voted");
				// request.setAttribute(BusinessConstants.ERROR_KEY, msg);
				// RequestDispatcher rd = request
				// .getRequestDispatcher(UrlConstants.ERROR_PAGE);
				// rd.forward(request, response);
				this.sendError(request, response, "bbs.vote.voted");
				return null;
			}
			request.setAttribute("vote", v);
		}
		if (CommonUtils.isNotEmpty(bid)) {
			Board b = boardService.getBoard(new Integer(bid));
			request.setAttribute("board", b);
		}
		this._right(request);
		return mapping.findForward("vote.form");
	}
}
