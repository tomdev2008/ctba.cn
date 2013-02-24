package org.net9.bbs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.net9.bbs.service.BoardService;
import org.net9.bbs.service.TopicService;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.StringUtils;
import org.net9.core.service.CommonService;
import org.net9.core.service.UserService;
import org.net9.core.types.CommendType;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.MainCommend;

public class BoardHelper {

	public static void prepareCommends(CommonService commonService,
			HttpServletRequest request) {
		List<MainCommend> commendList = commonService.findMainCommendsByType(
				CommendType.BBS.getValue(), 0, 10);
		request.setAttribute("commendList", commendList);
	}

	/**
	 * 有权限操作的用户:
	 * 
	 * <li>1 作者
	 * <li>2 版主
	 * 
	 * @param request
	 * @param model
	 * @param groupModel
	 * @throws InvalidAuthorSecurityException
	 */
	public static void authUserForCurrentPojo(HttpServletRequest request,
			BaseModel model, Board board, BoardService boardService,
			UserService userService) throws InvalidAuthorSecurityException {
		if (model == null) {
			return;
		}
		String username = UserHelper.getuserFromCookie(request);

		if (boardService.isBM(username, board)) {
			return; // 版主,OK
		}
		// #840 (系统管理员的权限)
		if (userService.isEditor(username)) {
			return; // 编辑,OK
		}
		String authorName = UserHelper.getAuthorName(model);
		if (StringUtils.isNotEmpty(authorName) && !authorName.equals(username)) {
			throw new InvalidAuthorSecurityException("error.noOption.noLogin");
		}
	}

	@SuppressWarnings("unchecked")
	public static void prepareHotTopics(TopicService topicService,
			Integer boardId, HttpServletRequest request) {
		int start = 0;
		int limit = 15;
		List<Topic> models = topicService.findHotTopicsByTime(boardId, 8,
				start, limit);
		if (models.size() < 2) {
			models = topicService.findHotTopics(boardId, start, limit);
		}
		List topics = new ArrayList();
		for (Topic t : models) {
			Map m = new HashMap();
			// t.setTopicTitle(StringUtils.getShorterStr(t.getTopicTitle(),
			// 12));
			m.put("topic", t);
			m.put("topicId", t.getTopicId() + "");
			topics.add(m);
		}
		request.setAttribute("boardHotTopics", topics);
	}
}
