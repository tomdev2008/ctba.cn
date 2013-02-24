package org.net9.core.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.service.UserService;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;

import com.google.inject.Inject;

/**
 * 发送文章列表渲染
 * 
 * 主要用于各种列表的统一表现
 * 
 * @author gladstone
 * @since 2008-8-17
 */
public class MailListWrapper {

	public static final String URL = "url";

	public static final String TYPE = "type";

	public static final String AUTHOR = "author";

	public static final String LABEL = "label";

	public static final int LABEL_LIMIT = 50;

	@Inject
	private UserService userService;

	/**
	 * 
	 * 发送文章列表渲染
	 * 
	 * 返回的是List[Map],Map里面的Key有
	 * url(String),author(User),label(String),type(String)
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> wrapContentList(List contentList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Object object : contentList) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				if (object instanceof Topic) {
					Topic topic = (Topic) object;
					map.put(URL, "topic/" + topic.getTopicId());
					map.put(AUTHOR, userService.getUser(null, topic
							.getTopicAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(topic
							.getTopicTitle(), LABEL_LIMIT));
					map.put(TYPE, I18nMsgUtils.getInstance().getMessage(
							"email.content.bbs"));
				} else if (object instanceof BlogEntry) {
					BlogEntry entry = (BlogEntry) object;
					map.put(URL, "blog/entry/" + entry.getId());
					map.put(AUTHOR, userService
							.getUser(null, entry.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(entry.getTitle(),
							LABEL_LIMIT));
					map.put(TYPE, I18nMsgUtils.getInstance().getMessage(
							"email.content.blog"));
				} else if (object instanceof GroupTopic) {
					GroupTopic topic = (GroupTopic) object;
					map.put(URL, "group/topic/" + topic.getId());
					map.put(AUTHOR, userService
							.getUser(null, topic.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(topic.getTitle(),
							LABEL_LIMIT));
					map.put(TYPE, I18nMsgUtils.getInstance().getMessage(
							"email.content.group"));
				} else if (object instanceof NewsEntry) {
					NewsEntry entry = (NewsEntry) object;
					map.put(URL, "news/" + entry.getId());
					map.put(AUTHOR, userService
							.getUser(null, entry.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(entry.getTitle(),
							LABEL_LIMIT));
					map.put(TYPE, I18nMsgUtils.getInstance().getMessage(
							"email.content.news"));
				}
				if (map.get(AUTHOR) != null) {
					list.add(map);
				}

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return list;
	}
}
