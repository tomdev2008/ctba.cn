package org.net9.core.web.servlet.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.User;

/**
 * #852 (CTBA社区财富(CTB))
 * 
 * @author gladstone
 * @since Dec 8, 2008
 */
@SuppressWarnings("serial")
@WebModule("scoreManage")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class UserScoreServlet extends BusinessCommonServlet {

	@ReturnUrl(rederect = false, url = "/manage/user/score.jsp")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("uid");
		User u = this.userService.getUser(Integer.valueOf(uid), null);
		request.setAttribute("model", u);
		Integer baseScore = this.calculateBaseScore(u.getUserName());
		request.setAttribute("baseScore", baseScore);
	}

	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void plus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String plusValue = request.getParameter("value");
		String notice = request.getParameter("notice");
		String log = request.getParameter("log");
		User u = this.userService.getUser(Integer.valueOf(uid), null);
		Integer baseScore = this.calculateBaseScore(u.getUserName());
		Integer value = Integer.valueOf(plusValue);
		if (baseScore - value < 0) {
			value = -baseScore;
		}
		this.userService.plusScore(u.getUserName(), Integer.valueOf(plusValue));
		this.userService.trigeEvent(this.userService.getUser(u.getUserName()),
				log, UserEventType.SCORE_TRAD);
		if ("true".equalsIgnoreCase(notice)) {
			this.userService.trigeNotice(u.getUserName(), null, log,
					"bank.action?method=logs", NoticeType.SYSTEM);
		}
		u = this.userService.getUser(Integer.valueOf(uid), null);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, baseScore
				+ u.getUserScore());
	}

}
