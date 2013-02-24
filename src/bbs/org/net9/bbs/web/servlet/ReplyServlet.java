package org.net9.bbs.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.exception.RichSystemException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Reply;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.User;

/**
 * 回复Servlet
 * 
 * @author gladstone
 * 
 */
@WebModule("reply")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@SuppressWarnings("serial")
public class ReplyServlet extends BusinessCommonServlet {
	/**
	 * 删除回复
	 * <li>1.delete reply
	 * <li>2.board's topicCnt
	 * <li>3.user's topicCnt
	 * <li>4.user's topicCnt in the board
	 * <li>5.user's score
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String topicIdStr = request.getParameter("tid");
		Reply model = replyService.getReply(new Integer(topicIdStr));
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);

		boolean editable = user.getUserName().equals(model.getTopicAuthor())
				|| user.getUserOption() >= UserSecurityType.OPTION_LEVEL_MANAGE;
		if (editable) {
			Integer parentId = model.getTopicOriginId();
			replyService.delReply(model);
			// if it is reTopic,to the main topic
			userService.saveUser(user, true);
			Topic t = topicService.getTopic(parentId);
			if (t != null) {
				t.setTopicReNum(t.getTopicReNum() - 1);
				topicService.saveTopic(t, true);
			}
			response.sendRedirect("topic/" + parentId);
		} else {
			this.sendError(request, response, "user.noOptionInBoard");
		}
	}

	/**
	 * 回复文章
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws RichSystemException
	 */
	@SuppressWarnings("unchecked")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, RichSystemException {
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
		String topicTitle = "Re:" + getParameter("topicTitle");
		String topicBoardIdStr = getParameter("topicBoardId");
		String topicContent = getParameter("topicContent");
		long topicId = Long.parseLong(getParameter("topicId"));
		String topicIP = HttpUtils.getIpAddr(request);
		int topicBoardId = Integer.parseInt(topicBoardIdStr);

		boolean checkFbn = boardService.isForbiddened(topicBoardId, null,
				username);
		if (checkFbn) {
			this.sendError(request, response, "user.noOptionInBoard");
			return;
		}
		// check if the title and the content is dirty
		List filterList = commonService.getForbiddenWordsAsList();
		boolean dirty = CommonUtils.checkDirty(topicTitle, filterList)
				|| CommonUtils.checkDirty(topicContent, filterList);
		if (dirty) {
			this.sendError(request, response, "topic.dirtyWords");
			return;
		}

		Reply model = new Reply();
		model.setTopicAttachName(topicAttachName);
		model.setTopicAttachPath(topicAttachPath);
		model.setTopicAuthor(username);
		model.setTopicBoardId(topicBoardId);
		model.setTopicContent(topicContent);
		model.setTopicHits(0);
		model.setTopicIP(topicIP);
		model.setTopicOriginId((int) topicId);
		model.setTopicTitle(topicTitle);
		model.setTopicTime(StringUtils.getTimeStrByNow());
		replyService.saveReply(model);
		Topic topic = topicService.getTopic((int) topicId);
		topic.setTopicReNum(topic.getTopicReNum() + 1);
		topic.setTopicUpdateTime(StringUtils.getTimeStrByNow());
		topicService.saveTopic(topic, true);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapTopic(topic) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}

		userService.trigeEvent(this.userService.getUser(username), topic
				.getTopicId()
				+ "", UserEventType.TOPIC_REPLY);
		// deal the boardReNum
		Board b = boardService.getBoard(topicBoardId);
		if (b != null) {
			b.setBoardReNum(b.getBoardReNum() + 1);
			boardService.saveBoard(b, true);
		}

		// #852 (CTBA社区财富(CTB))
		String target = topic.getTopicAuthor();
		if ((!username.equals(target)) && topic.getTopicScore() != null
				&& topic.getTopicScore() > 0) {
			User user = this.userService.getUser(null, username);
			Integer baseScore = this.calculateBaseScore(username);

			User targetUser = this.userService.getUser(null, target);
			if (baseScore + user.getUserScore() - topic.getTopicScore() < 0) {
				throw new RichSystemException(""); // TODO: 国际化字符串信息
			}

			user.setUserScore(user.getUserScore() - topic.getTopicScore());
			targetUser
					.setUserScore(user.getUserScore() + topic.getTopicScore());
			this.userService.saveUser(user, true);
			this.userService.saveUser(targetUser, true);

			String msg = I18nMsgUtils.getInstance().createMessage(
					"user.score.trans.notice.topic",
					new String[] { SimplePojoWrapper.wrapTopic(topic),
							username, "" + topic.getTopicScore() });
			String msgReverse = I18nMsgUtils.getInstance().createMessage(
					"user.score.trans.notice.topic.reverse",
					new String[] { SimplePojoWrapper.wrapTopic(topic), target,
							"" + topic.getTopicScore() });

			// 交易记录(给自己)
			this.userService.trigeEvent(this.userService.getUser(username),
					msgReverse, UserEventType.SCORE_TRAD);
			// 交易记录
			this.userService.trigeEvent(this.userService.getUser(target), msg,
					UserEventType.SCORE_TRAD);
		}

		response.sendRedirect(UrlConstants.TOPIC + topicId);

	}

}
