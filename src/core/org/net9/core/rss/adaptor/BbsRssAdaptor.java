package org.net9.core.rss.adaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.types.TopicType;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class BbsRssAdaptor extends RssAdaptor {
	private static final long serialVersionUID = 1L;
	@Override
	public SyndEntry adaptEntry(BaseModel model) {
		DateFormat dateParser = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		SyndEntry entry;
		SyndContent description;
		Topic topic = (Topic) model;
		entry = new SyndEntryImpl();
		entry.setTitle(topic.getTopicTitle());
		entry.setLink(_rootUrl + "/" + "topic/" + topic.getTopicId());
		try {
			entry.setPublishedDate(dateParser.parse(topic.getTopicTime()));
		} catch (ParseException ex) {
			try {
				dateParser = new SimpleDateFormat(DATE_FORMAT);
				entry.setPublishedDate(dateParser.parse(DateUtils
						.getDateStrFromTime(topic.getTopicTime())));
			} catch (ParseException ex2) {
				ex2.printStackTrace();
			}
		}
		entry.setAuthor(topic.getTopicAuthor());
		// 增加UBB解析
		String topicContent = UBBDecoder.decode(CommonUtils
				.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(topic
						.getTopicContent())), new UBBSimpleTagHandler(),
				UBBDecoder.MODE_IGNORE);
		topicContent = CustomizeUtils.parseEmotionImg(topicContent);
		description = new SyndContentImpl();
		description.setType("text/plain");
		description.setValue(topicContent);
		entry.setDescription(description);
		return entry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findModels(Integer start, Integer limit, Map requestMap) {
		String boardIdStr = (String) requestMap.get("bid");
		String withTop = (String) requestMap.get("with_top");
		String boardTopicType = (String) requestMap.get("b_type");

		Integer boardId = null;
		if (StringUtils.isNotEmpty(boardIdStr)) {
			boardId = new Integer(boardIdStr);
		}

		// 1 最新发表
		// 2 最近更新 [默认]
		// 3 最强主题
		String title = "";
		List<Topic> tList = null;
		if ("best".equalsIgnoreCase(boardTopicType)) {
			// 最强主题
			tList = topicService.findHotTopicsByTime(boardId, 10, start, limit);
			title = I18nMsgUtils.getInstance()
					.getMessage("rss.feed.title.best");
		} else if ("new".equalsIgnoreCase(boardTopicType)) {
			// 最新发表
			tList = topicService.findNewTopicsForIndex(start, limit);
			title = I18nMsgUtils.getInstance().getMessage("rss.feed.title.new");
		} else {
			// 最近更新
			tList = topicService.findTopics(boardId, start, limit,
					TopicType.NORMAL.getValue());
			title = I18nMsgUtils.getInstance().getMessage(
					"rss.feed.title.update");
		}
		if (boardId != null) {
			Board b = boardService.getBoard(boardId);
			title += " " + b.getBoardName();
		}

		if ("true".equals(withTop)) {
			// Top topics
			List<Topic> topTopicList = topicService.findTopics(boardId, 0,
					BusinessConstants.MAX_INT, TopicType.TOP.getValue());
			if (tList != null) {
				tList.addAll(0, topTopicList);
			}
		}
		while (tList.size() > limit) {
			tList.remove(tList.size() - 1);
		}

		return tList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRssTitle(Map requestMap) {
		String boardIdStr = (String) requestMap.get("bid");
		String boardTopicType = (String) requestMap.get("b_type");
		Integer boardId = null;
		if (StringUtils.isNotEmpty(boardIdStr)) {
			boardId = new Integer(boardIdStr);
		}

		// 1 最新发表
		// 2 最近更新 [默认]
		// 3 最强主题
		String title = "";
		if ("best".equalsIgnoreCase(boardTopicType)) {
			title = I18nMsgUtils.getInstance()
					.getMessage("rss.feed.title.best");
		} else if ("new".equalsIgnoreCase(boardTopicType)) {
			title = I18nMsgUtils.getInstance().getMessage("rss.feed.title.new");
		} else {
			title = I18nMsgUtils.getInstance().getMessage(
					"rss.feed.title.update");
		}
		if (boardId != null) {
			Board b = boardService.getBoard(boardId);
			title += " " + b.getBoardName();
		}

		return title;
	}

}