package org.net9.core.web.servlet.manage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.SysFeedback;

/**
 * 用户反馈
 * 
 * @author gladstone
 * 
 */
@WebModule("feedbackManage")
@SuppressWarnings("serial")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class FeedbackManageServlet extends BusinessCommonServlet {
	
	@ReturnUrl(rederect = true, url = "feedbackManage.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String id = request.getParameter("id");
		SysFeedback model = commonService.getFeedback(Integer.valueOf(id));
		if (model != null) {
			this.commonService.deleteFeedback(model);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = false, url = "/manage/sys/feedbackList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<SysFeedback> feedbackList = this.commonService.findFeedbacks(
				start, limit);
		request.setAttribute("models", feedbackList);
		request.setAttribute("count", this.commonService.getFeedbackCnt());

	}

}
