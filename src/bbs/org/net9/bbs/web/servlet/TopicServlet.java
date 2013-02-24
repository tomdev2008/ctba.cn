package org.net9.bbs.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.bbs.web.BoardHelper;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.User;

@SuppressWarnings("serial")
@WebModule("topic")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class TopicServlet extends BusinessCommonServlet {

	/**
	 * 删除文章
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String topicIdStr = request.getParameter("tid");
		Topic model = topicService.getTopic(new Integer(topicIdStr));
		int boardId = model.getTopicBoardId();
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);

		// validate user
		BoardHelper.authUserForCurrentPojo(request, model, this.boardService
				.getBoard(boardId), boardService, userService);

		boolean editable = user.getUserName().equals(model.getTopicAuthor())
				|| user.getUserOption() >= UserSecurityType.OPTION_LEVEL_MANAGE;
		if (editable) {
			Integer parentId = model.getTopicOriginId();
			topicService.deleteTopic(model);
			if (parentId == null || parentId <= 0) {
				userService.saveUser(user, true);
				response.sendRedirect(UrlConstants.BOARD + boardId);
			} else {
				// if it is reTopic,to the main topic
				userService.saveUser(user, true);
				response.sendRedirect(UrlConstants.TOPIC + parentId);
			}
		} else {
			this.sendError(request, response, "user.noOptionInBoard");
		}
	}

	/**
	 * 删除附件
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	public void deleteAttach(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String topicIdStr = request.getParameter("tid");
		Topic topic = topicService.getTopic(Integer.valueOf(topicIdStr));

		// validate user
		BoardHelper.authUserForCurrentPojo(request, topic, this.boardService
				.getBoard(topic.getTopicBoardId()), boardService, userService);

		topic.setTopicAttachName("");
		topic.setTopicAttachPath("");
		String topicAttachPath = topic.getTopicAttachPath();
		File file = new File(request.getSession().getServletContext()
				.getRealPath(
						SystemConfigVars.UPLOAD_DIR_PATH + File.separator
								+ topicAttachPath));
		file.delete();
		topicService.saveTopic(topic, true);
		response.sendRedirect("topic.action?method=form&tid="
				+ topic.getTopicId());
	}

	/**
	 * 编辑文章
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	public void edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String topicAttachPath = "";
		String topicAttachName = "";
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);

		try {
			Map map = getMultiFile(request, "topicAttach");
			topicAttachPath = (String) map.get(BusinessCommonServlet.FILE_PATH);
			topicAttachName = (String) map.get(BusinessCommonServlet.FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String topicTitle = getParameter("topicTitle");
		String topicIdStr = getParameter("topicId");
		String topicContent = getParameter("topicContent");
		String topicScore = getParameter("topicScore");
		String topicIP = HttpUtils.getIpAddr(request);
		topicTitle = topicTitle.trim();
		List filterList = commonService.getForbiddenWordsAsList();
		// check if the title and the content is dirty
		boolean dirty = CommonUtils.checkDirty(topicTitle, filterList)
				|| CommonUtils.checkDirty(topicContent, filterList);
		if (dirty) {
			this.sendError(request, response, "topic.dirtyWords");
			return;
		}

		Integer topicId = Integer.parseInt(topicIdStr);

		Topic topic = topicService.getTopic(topicId);

		// validate user
		BoardHelper.authUserForCurrentPojo(request, topic, this.boardService
				.getBoard(topic.getTopicBoardId()), boardService, userService);

		// TODO: check if the author is the login user
		boolean editable = user.getUserName().equals(topic.getTopicAuthor())
				|| user.getUserOption() >= UserSecurityType.OPTION_LEVEL_MANAGE;
		if (editable) {
			String plugin = I18nMsgUtils.getInstance().createMessage(
					"topic.plugin",
					new Object[] {
							username,
							StringUtils.getTimeStrByNow(),
							topicIP.substring(0, topicIP.lastIndexOf(".") + 1)
									+ "*" });
			topicContent += plugin;
			if (CommonUtils.isNotEmpty(topicAttachName)
					&& CommonUtils.isNotEmpty(topicAttachPath)) {
				topic.setTopicAttachName(topicAttachName);
				topic.setTopicAttachPath(topicAttachPath);
			}
			topic.setTopicTitle(topicTitle);
			topic.setTopicContent(topicContent);
			topic.setTopicIP(topicIP);
			// #852 (CTBA社区财富(CTB))
			if (StringUtils.isEmpty(topicScore)) {
				topic.setTopicScore(0);
			} else {
				topic.setTopicScore(Integer.valueOf(topicScore));
			}
			topic.setTopicUpdateTime(StringUtils.getTimeStrByNow());
			topicService.saveTopic(topic, true);
			userService.trigeEvent(this.userService.getUser(username), topic
					.getTopicId()
					+ "", UserEventType.TOPIC_EDIT);
		}

		response.sendRedirect(UrlConstants.TOPIC + topicId);
	}

	/**
	 * 修改文章表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/board/editTopic.jsp")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String topicId = request.getParameter("tid");
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		Topic model = topicService.getTopic(new Integer(topicId));
		List<Board> bList = this.boardService.findBottomBoards(0,
				BusinessConstants.MAX_INT);
		request.setAttribute("boardList", bList);
		Board board = boardService.getBoard(model.getTopicBoardId());
		request.setAttribute("board", board);
		request.setAttribute("boardInfo", UBBDecoder.decode(CommonUtils
				.htmlEncode(board.getBoardInfo()), new UBBSimpleTagHandler(),
				UBBDecoder.MODE_IGNORE));
		request.setAttribute("topicAttach", this.getFileAttachStr(model
				.getTopicAttachName(), model.getTopicAttachPath(), request));
		request.setAttribute("user", user);
		request.setAttribute("board", board);
		request.setAttribute("topic", model);
		BoardHelper.prepareHotTopics(topicService, board.getBoardId(), request);
	}
}
