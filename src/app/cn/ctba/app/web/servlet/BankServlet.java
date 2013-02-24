package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.exception.RichSystemException;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;

/**
 * 
 * #852 (CTBA社区财富(CTB))
 * 
 * @author gladstone
 * @since May 17, 2009
 */
@WebModule("bank")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class BankServlet extends BusinessCommonServlet {

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/apps/bank/indexPage.jsp")
	public void indexPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		Integer baseScore = this.calculateBaseScore(username);
		request.setAttribute("baseScore", baseScore);
		request.setAttribute("currScore", baseScore + user.getUserScore());
		// 最新操作记录（10条）
		List<UserLog> newLogs = this.userService.findAllUserlogs(username, 0,
				10, new Integer[] { UserEventType.SCORE_TRAD.getValue() });
		request.setAttribute("newLogs", newLogs);

	}

	/**
	 * 积分增减记录
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/apps/bank/logs.jsp")
	public void logs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<UserLog> logs = this.userService.findAllUserlogs(username, start,
				limit, new Integer[] { UserEventType.SCORE_TRAD.getValue() });
		request.setAttribute("logs", logs);
		Integer logCnt = this.userService.getAllUserlogCnt(username,
				UserEventType.SCORE_TRAD.getValue());
		request.setAttribute("count", logCnt);

	}

	@ReturnUrl(rederect = false, url = "/apps/bank/fun.jsp")
	public void fun(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		Integer baseScore = this.calculateBaseScore(username);
		request.setAttribute("baseScore", baseScore);
		request.setAttribute("currScore", baseScore + user.getUserScore());
	}

	/**
	 * CTB转账
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/apps/bank/trans.jsp")
	public void trans(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		Integer baseScore = this.calculateBaseScore(username);
		request.setAttribute("baseScore", baseScore);
		request.setAttribute("currScore", baseScore + user.getUserScore());
	}

	/**
	 * CTB转账
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws RichSystemException
	 */
	@ReturnUrl(rederect = false, url = "/apps/bank/transDone.jsp")
	public void saveTrans(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			RichSystemException {
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		Integer baseScore = this.calculateBaseScore(username);
		request.setAttribute("baseScore", baseScore);
		request.setAttribute("currScore", baseScore + user.getUserScore());
		String value = request.getParameter("value");

		String targetStr = request.getParameter("target");

		String target = targetStr.split(",")[0];

		String hint = request.getParameter("hint");
		User targetUser = this.userService.getUser(null, target);
		if (Integer.valueOf(value) < 0) {
			throw new RichSystemException(""); // TODO: 国际化字符串信息
		}
		if (baseScore + user.getUserScore() - Integer.valueOf(value) < 0) {
			throw new RichSystemException(""); // TODO: 国际化字符串信息
		}

		user.setUserScore(user.getUserScore() - Integer.valueOf(value));
		targetUser.setUserScore(user.getUserScore() + Integer.valueOf(value));
		this.userService.saveUser(user, true);
		this.userService.saveUser(targetUser, true);
		request.setAttribute("value", value);
		request.setAttribute("targetUser", targetUser);

		// 发送提示
		if (StringUtils.isEmpty(hint)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"user.score.trans.notice.withoutmsg",
					new String[] { username, value });
			String msgReverse = I18nMsgUtils.getInstance().createMessage(
					"user.score.trans.notice.withoutmsg.reverse",
					new String[] { target, value });
			this.userService.trigeNotice(target, username, msg, HttpUtils
					.getReferer(request), NoticeType.COMMON);

			// 交易记录(给自己)
			this.userService.trigeEvent(this.userService.getUser(username),
					msgReverse, UserEventType.SCORE_TRAD);
			// 交易记录
			this.userService.trigeEvent(this.userService.getUser(target), msg,
					UserEventType.SCORE_TRAD);

		} else {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"user.score.trans.notice",
					new String[] { username, value, hint });
			String msgReverse = I18nMsgUtils.getInstance().createMessage(
					"user.score.trans.notice.reverse",
					new String[] { target, value, hint });
			this.userService.trigeNotice(target, username, msg, HttpUtils
					.getReferer(request), NoticeType.COMMON);

			// 交易记录(给自己)
			this.userService.trigeEvent(this.userService.getUser(username),
					msgReverse, UserEventType.SCORE_TRAD);
			// 交易记录
			this.userService.trigeEvent(this.userService.getUser(target), msg,
					UserEventType.SCORE_TRAD);
		}

	}
}
