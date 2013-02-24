package org.net9.core.rss.adaptor;

import java.util.List;
import java.util.Map;

import org.net9.bbs.service.BoardService;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.CommentService;
import org.net9.blog.service.EntryService;
import org.net9.core.service.CommonService;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.domain.model.BaseModel;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;
import org.net9.news.service.NewsService;
import org.net9.subject.service.SubjectService;

import cn.ctba.share.service.ShareService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.sun.syndication.feed.synd.SyndEntry;

public abstract class RssAdaptor implements java.io.Serializable {

	protected static final String DATE_FORMAT = "yyyy-MM-dd";

	/*
	 * 日期格式化字符串，包含有时间信息(小时、分钟、秒)
	 */
	protected static final String DATE_FORMAT_WITH_TIME = "yyyy-MM-dd HH:mm:ss";

	protected String _rootUrl;

	@Inject
	protected CommonService commonService;

	@Inject
	protected UserService userService;

	@Inject
	protected BoardService boardService;

	@Inject
	protected TopicService topicService;

	@Inject
	protected GroupService groupService;

	@Inject
	protected GroupTopicService groupTopicService;

	@Inject
	protected BlogService blogService;

	@Inject
	protected EntryService entryService;

	@Inject
	protected CommentService commentService;

	@Inject
	protected SubjectService subjectService;

	@Inject
	protected NewsService newsService;

	@Inject
	protected ShareService shareService;

	public RssAdaptor() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	@SuppressWarnings("unchecked")
	abstract public SyndEntry adaptEntry(BaseModel model);

	@SuppressWarnings("unchecked")
	abstract public String getRssTitle(Map requestMap);

	@SuppressWarnings("unchecked")
	abstract public List findModels(Integer start, Integer limit, Map requestMap);

	public void setRootUrl(String url) {
		_rootUrl = url;
	}
}