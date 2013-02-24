package org.net9.core.rss.adaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.net9.blog.service.EntryService;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class BlogRssAdaptor extends RssAdaptor {
	private static final long serialVersionUID = 1L;
	@Override
	public SyndEntry adaptEntry(BaseModel model) {
		DateFormat dateParser = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		SyndEntry entry;
		SyndContent description;
		BlogEntry e = (BlogEntry) model;
		entry = new SyndEntryImpl();
		entry.setTitle(e.getTitle());
		entry.setLink(_rootUrl + "/" + "blog/entry/" + e.getId());
		try {
			entry.setPublishedDate(dateParser.parse(e.getDate()));
		} catch (ParseException ex) {
			try {
				dateParser = new SimpleDateFormat(DATE_FORMAT);
				entry.setPublishedDate(dateParser.parse(DateUtils
						.getDateStrFromTime(e.getDate())));
			} catch (ParseException ex2) {
				ex2.printStackTrace();
			}
		}
		entry.setAuthor(e.getAuthor());
		Integer commentCount = commentService.getCommentsCnt(e.getBlogBlog()
				.getId(), e.getId());
		String bottomInfo = " &nbsp;发布时间：" + e.getDate() + "&nbsp;|&nbsp;阅读："
				+ e.getHits() + "&nbsp;|&nbsp;评论：" + commentCount + "&nbsp;";
		description = new SyndContentImpl();
		description.setType("text/plain");
		description.setValue(e.getBody() + bottomInfo);
		entry.setDescription(description);
		return entry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findModels(Integer start, Integer limit, Map requestMap) {
		String blogIdStr = (String) requestMap.get("bgid");
		String categoryIdStr = (String) requestMap.get("cid");
		Integer blogId = null;
		Integer categoryId = null;
		if (StringUtils.isNotEmpty(blogIdStr)) {
			blogId = new Integer(blogIdStr);
		}
		if (StringUtils.isNotEmpty(categoryIdStr)) {
			categoryId = new Integer(categoryIdStr);
		}
		List<BlogEntry> eList = entryService.findEntries(blogId, categoryId,
				EntryService.EntryType.NORMAL.getType(), start, limit);
		return eList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRssTitle(Map requestMap) {
		String blogIdStr = (String) requestMap.get("bgid");

		Integer blogId = null;
		if (StringUtils.isNotEmpty(blogIdStr)) {
			blogId = new Integer(blogIdStr);
		}

		String title = I18nMsgUtils.getInstance().getMessage(
				"rss.feed.title.blog");
		if (blogId != null) {
			Blog b = blogService.getBlog(blogId);
			title = b.getName();
		}
		
		return title;
	}

}