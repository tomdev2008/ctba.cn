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
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class GroupRssAdaptor extends RssAdaptor {
	private static final long serialVersionUID = 1L;
	@Override
	public SyndEntry adaptEntry(BaseModel model) {
		DateFormat dateParser = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		SyndEntry entry;
		SyndContent description;
		GroupTopic topic = (GroupTopic) model;
		// 增加UBB解析
		String topicContent = UBBDecoder.decode(CommonUtils
				.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(topic
						.getContent())), new UBBSimpleTagHandler(),
				UBBDecoder.MODE_IGNORE);
		topicContent = CustomizeUtils.parseEmotionImg(topicContent);

		entry = new SyndEntryImpl();
		entry.setTitle(topic.getTitle());
		entry.setLink(_rootUrl + "/" + "group/topic/" + topic.getId());
		try {
			entry.setPublishedDate(dateParser.parse(topic.getUpdateTime()));
		} catch (ParseException ex) {
			try {
				dateParser = new SimpleDateFormat(DATE_FORMAT);
				entry.setPublishedDate(dateParser.parse(DateUtils
						.getDateStrFromTime(topic.getUpdateTime())));
			} catch (ParseException ex2) {
				ex2.printStackTrace();
			}
		}
		entry.setAuthor(topic.getAuthor());

		description = new SyndContentImpl();
		description.setType("text/plain");
		description.setValue(topicContent);
		entry.setDescription(description);
		return entry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findModels(Integer start, Integer limit, Map requestMap) {
		String groupIdStr = (String) requestMap.get("gid");
		Integer groupId = null;
		if (StringUtils.isNotEmpty(groupIdStr)) {
			groupId = new Integer(groupIdStr);
		}

		List<GroupTopic> gtList = groupTopicService.findTopics(groupId, null,
				false, null, start, limit);
		String title = I18nMsgUtils.getInstance().getMessage(
				"rss.feed.title.group");
		if (groupId != null) {
			GroupModel g = groupService.getGroup(groupId);
			title += " " + g.getName();
		}
		return gtList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRssTitle( Map requestMap) {
		return I18nMsgUtils.getInstance().getMessage("rss.feed.title.group");
	}

}