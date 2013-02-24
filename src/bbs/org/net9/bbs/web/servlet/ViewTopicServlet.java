package org.net9.bbs.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.service.TopicService;
import org.net9.bbs.web.BoardHelper;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.common.util.ubb.UBBWithoutImgTagHandler;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.hit.HitStrategy;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.dao.bbs.TopicsDAO;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Reply;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.User;
import org.net9.search.lucene.search.ref.LuceneTopicReferenceSearcher;

import com.google.inject.Inject;

/**
 * 查看版面文章 改用Servlet
 * 
 * @author gladstone
 * @since 2008/04/20
 */
@WebModule("t")
@SuppressWarnings("serial")
@ReturnUrl(rederect = false, url = "/board/viewTopic.jsp")
public class ViewTopicServlet extends BusinessCommonServlet {

	private static final Log log = LogFactory.getLog(ViewTopicServlet.class);
	@Inject
	private LuceneTopicReferenceSearcher referenceSearcher;

	@Inject
	private HitStrategy hitStrategy;

	@Override
	@SuppressWarnings("unchecked")
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			CommonSystemException {

		String topicId = request.getParameter("tid");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		Integer userRole = UserSecurityType.OPTION_LEVEL_ALL;
		Topic model = topicService.getTopic(new Integer(topicId));
		if (model == null) {
			throw new ModelNotFoundException();
		}
		Board board = null;
		Integer reCnt = replyService.getRepliesCntByTopic(model.getTopicId()
				.intValue());

		if (model.getTopicReNum() != reCnt) {
			model.setTopicReNum(reCnt);
			topicService.saveTopic(model, true);
		}
		// model.setTopicHits(model.getTopicHits() + CommonUtils.getHitPlus());
		hitStrategy.hitTopic(model);

		// #757 (论坛列表的 titile 中，字首的空格没有过滤)
		model.setTopicTitle(model.getTopicTitle().trim());
		board = boardService.getBoard(model.getTopicBoardId());
		Board parentBoard = boardService.getBoard(board.getBoardParent());
		request.setAttribute("parentBoard", parentBoard);
		if (user != null) {
			userRole = boardService.checkUserRole(user.getUserId(), board
					.getBoardId());

			Integer baseScore = this.calculateBaseScore(username);
			request.setAttribute("currScore", baseScore + user.getUserScore());
		}

		try {
			List<Map<String, String>> refTopics = referenceSearcher
					.searchByKey(model.getTopicTitle(), UrlConstants.TOPIC
							+ model.getTopicId());
			request.setAttribute("refTopics", refTopics);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		// 处理回复
		List<Reply> reTopics = replyService.findReplys(new Integer(topicId),
				start, limit);
		List reModels = new ArrayList();
		for (Reply topic : reTopics) {
			try {
				User author = userService.getUser(null, topic.getTopicAuthor());
				Map map = new HashMap();
				map.put("topic", topic);
				map.put("author", author);
				map.put("isAuthor", (author != null)
						&& author.getUserName().equals(username));
				String userFace = "images/nopic.gif";
				if (CommonUtils.isNotEmpty(author.getUserFace())) {
					userFace = SystemConfigVars.UPLOAD_DIR_PATH + "/"
							+ ImageUtils.getMiniImageStr(author.getUserFace());
				}
				String topicContent = UBBDecoder.decode(CommonUtils
						.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(topic
								.getTopicContent())),
						new UBBSimpleTagHandler(), UBBDecoder.MODE_IGNORE);
				topicContent = CustomizeUtils.parseEmotionImg(topicContent);
				map.put("userFace", userFace);
				map.put("topicAttach", this.getFileAttachStr(topic
						.getTopicAttachName(), topic.getTopicAttachPath(),
						request));
				map.put("topicContent", topicContent);
				reModels.add(map);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
				continue; // 如果一个回帖有问题,
			}

		}

		int state = model.getTopicState();
		String[] stateStrs = TopicsDAO.decodeTopicState(state);
		boolean logined = user != null;

		boolean topicCanRe = CommonUtils
				.isEmpty(stateStrs[TopicService.TOPIC_STATE_INDEX_RE]);
		String time = DateUtils.getDateStrFromTime(model.getTopicTime());
		String endTime = "";
		try {
			endTime = CommonUtils.getDateFromDateOn(time,
					BusinessConstants.TOPIC_DELETABLE_LINE);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		boolean outOfDiff = DateUtils.diff(StringUtils.getTimeStrByNow(),
				endTime) > 0;
		request.setAttribute("outOfDiff", outOfDiff);

		request.setAttribute("count", reCnt);
		request.setAttribute("topicCanPost", logined && topicCanRe);
		// FIXME:这里由于慢查询的问题暂时去掉。
		// request.setAttribute("nextTopic", topicService.getNextTopic(
		// new Integer(topicId), model.getTopicBoardId()));
		// request.setAttribute("prevTopic", topicService.getPrevTopic(
		// new Integer(topicId), model.getTopicBoardId()));

		request.setAttribute("logined", logined);

		request.setAttribute("reTopics", reModels);

		User author = userService.getUser(null, model.getTopicAuthor());
		request.setAttribute("author", author);

		String userFace = "images/nopic.gif";
		if (CommonUtils.isNotEmpty(author.getUserFace())) {
			userFace = SystemConfigVars.UPLOAD_DIR_PATH + "/"
					+ ImageUtils.getMiniImageStr(author.getUserFace());
		}
		request.setAttribute("userFace", userFace);

		request.setAttribute("topic", model);
		request.setAttribute("isAuthor", user != null
				&& user.getUserName().equals(model.getTopicAuthor()));
		request.setAttribute("manager", userRole != null
				&& userRole >= UserSecurityType.OPTION_LEVEL_MANAGE);

		String topicContent = StringUtils.parseUBBStr(model.getTopicContent());
		request.setAttribute("topicContent", topicContent);

		// #137 去除簽名檔的img標籤
		// #327 签名档中的UBB代码转换有问题
		request.setAttribute("userQMD", UBBDecoder.decode(CommonUtils
				.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(author
						.getUserQMD())), new UBBWithoutImgTagHandler(),
				UBBDecoder.MODE_IGNORE));

		request.setAttribute("topicAttach", this.getFileAttachStr(model
				.getTopicAttachName(), model.getTopicAttachPath(), request));

		request.setAttribute("board", board);
		request.setAttribute("start", start);
		BoardHelper.prepareHotTopics(topicService, board.getBoardId(), request);
		BoardHelper.prepareCommends(commonService, request);

		// 版面信息
		// request.setAttribute("voteCnt", voteService.getVotesCnt(board
		// .getBoardId(), null, null));

	}
}
