package org.net9.bbs.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.web.servlet.BusinessCommonServlet;

/**
 * 处理文章状态
 * 
 * @author gladstone
 * @since 2008/06/21
 */
@WebModule("topicState")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@SuppressWarnings("serial")
public class TopicStateServlet extends BusinessCommonServlet {

	/**
	 * 处理精华
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void primer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String topicIdStr = request.getParameter("topicId");
		int boardId = topicService.getTopic(Integer.valueOf(topicIdStr))
				.getTopicBoardId();
		topicService.dealPrimer(Integer.valueOf(topicIdStr));
		topicService.flushTopicCache();
		response.sendRedirect(UrlConstants.BOARD + boardId);
	}

	/**
	 * 处理是否可回复
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void re(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String topicIdStr = request.getParameter("topicId");
		int boardId = topicService.getTopic(Integer.valueOf(topicIdStr))
				.getTopicBoardId();
		topicService.dealRe(Integer.valueOf(topicIdStr));
		topicService.flushTopicCache();
		response.sendRedirect(UrlConstants.BOARD + boardId);
	}

	/**
	 * 处理是否提醒
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void remind(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String topicIdStr = request.getParameter("topicId");
		int boardId = topicService.getTopic(Integer.valueOf(topicIdStr))
				.getTopicBoardId();
		topicService.dealRemind(Integer.valueOf(topicIdStr));
		topicService.flushTopicCache();
		response.sendRedirect(UrlConstants.BOARD + boardId);
	}

	/**
	 * 处理是否置顶
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void top(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String topicIdStr = request.getParameter("topicId");
		int boardId = topicService.getTopic(Integer.valueOf(topicIdStr))
				.getTopicBoardId();
		topicService.dealTop(Integer.valueOf(topicIdStr));
		topicService.flushTopicCache();
		response.sendRedirect(UrlConstants.BOARD + boardId);
	}

}
