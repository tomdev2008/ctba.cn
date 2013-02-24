package org.net9.search.web.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.service.BoardService;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.EntryService;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.UrlConstants;
import org.net9.core.service.UserService;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;
import org.net9.domain.model.subject.Subject;
import org.net9.domain.model.subject.SubjectTopic;
import org.net9.group.service.GroupTopicService;
import org.net9.news.service.NewsService;
import org.net9.subject.service.SubjectService;

import com.google.inject.Inject;
import com.j2bb.common.search.search.SearchResponse;

/**
 * 搜索结果渲染
 * 
 * 主要用于各种搜索的统一表现
 * 
 * 
 * @author gladstone
 * @since 2008-8-17
 */
public class SearchReponseWrapper implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public static final String URL = "url";
	public static final String AUTHOR = "author";
	public static final String CREATE_TIME = "createTime";
	public static final String LABEL = "label";
	public static final String CATEGORY = "category";
	public static final int LABEL_LIMIT = 100;
	@Inject
	private UserService userService;
	@Inject
	private TopicService topicService;
	@Inject
	private EntryService entryService;
	@Inject
	private NewsService newsService;
	@Inject
	private BoardService boardService;
	@Inject
	private GroupTopicService groupTopicService;
	@Inject
	private SubjectService subjectService;

	/**
	 * 
	 * 搜索结果渲染
	 * 
	 * 返回的是List[Map],Map里面的Key有 url(String),author(User),label(String)
	 * 
	 * @param response
	 * @return
	 */
	public List<Map<String, Object>> wrapResponse(SearchResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Object object : response.getResults()) {
			try {
				Map<String, Object> map = new HashMap<String, Object>();
				if (object instanceof Topic) {
					Topic topic = (Topic) object;
					map.put(URL, UrlConstants.TOPIC + topic.getTopicId());
					// TODO:现在利用率不高所以可能这么弄，考虑性能的话，不应该在这里再取一遍
					topic = this.topicService.getTopic(topic.getTopicId());
					map.put(AUTHOR, userService.getUser(null, topic
							.getTopicAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(topic
							.getTopicTitle(), LABEL_LIMIT));
					map.put(CREATE_TIME, topic.getTopicTime());
					Board board = this.boardService.getBoard(topic
							.getTopicBoardId());
					map.put(CATEGORY, "<a href='board/" + board.getBoardId()
							+ "'>" + board.getBoardName() + "</a>");
				} else if (object instanceof BlogEntry) {
					BlogEntry entry = (BlogEntry) object;
					map.put(URL, UrlConstants.BLOG_ENRTY + entry.getId());
					entry = this.entryService.getEntry(entry.getId());
					map.put(AUTHOR, userService
							.getUser(null, entry.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(entry.getTitle(),
							LABEL_LIMIT));
					map.put(CREATE_TIME, entry.getDate());
					map.put(CATEGORY, "<a href='blog/"
							+ entry.getBlogBlog().getId() + "'>"
							+ entry.getBlogBlog().getName() + "</a>");
				} else if (object instanceof GroupTopic) {
					GroupTopic topic = (GroupTopic) object;
					map.put(URL, UrlConstants.GROUP_TOPIC + topic.getId());
					topic = this.groupTopicService.getTopic(topic.getId());
					map.put(AUTHOR, userService
							.getUser(null, topic.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(topic.getTitle(),
							LABEL_LIMIT));
					map.put(CREATE_TIME, topic.getCreateTime());
					map.put(CATEGORY, "<a href='group/"
							+ topic.getGroupModel().getId() + "'>"
							+ topic.getGroupModel().getName() + "</a>");
				} else if (object instanceof NewsEntry) {
					NewsEntry entry = (NewsEntry) object;
					map.put(URL, UrlConstants.NEWS_ENRTY + entry.getId());
					entry = this.newsService.getNews(entry.getId());
					map.put(AUTHOR, userService
							.getUser(null, entry.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(entry.getTitle(),
							LABEL_LIMIT));
					map.put(CREATE_TIME, entry.getCreateTime());
					map.put(CATEGORY, "<a href='news.shtml?method=list&cid="
							+ entry.getNewsCategory().getCode() + "'>"
							+ entry.getNewsCategory().getName() + "</a>");
				} else if (object instanceof SubjectTopic) {
					SubjectTopic entry = (SubjectTopic) object;
					map.put(URL, UrlConstants.SUBJECT_ARTICLE + entry.getId());
					entry = this.subjectService.getSubjectTopic(entry.getId());
					map.put(AUTHOR, userService
							.getUser(null, entry.getAuthor()));
					map.put(LABEL, StringUtils.getShorterStr(entry.getTitle(),
							LABEL_LIMIT));
					map.put(CREATE_TIME, entry.getCreateTime());
					Subject sub = this.subjectService.getSubject(entry
							.getSubjectId());
					map.put(CATEGORY, "<a href='sub/" + sub.getId() + "'>"
							+ sub.getTitle() + "</a>");

				}
				list.add(map);
			} catch (Exception e) {
				log.error(e.getMessage());
				// e.printStackTrace();
				continue;
			}
		}
		return list;
	}

	private static Log log = LogFactory.getLog(SearchReponseWrapper.class);
}
