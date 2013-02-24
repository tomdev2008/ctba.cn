package org.net9.core.rss.adaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.core.types.NewsStateType;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.news.NewsEntry;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class NewsRssAdaptor extends RssAdaptor {
	private static final long serialVersionUID = 1L;
	@Override
	public SyndEntry adaptEntry(BaseModel model) {
		DateFormat dateParser = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		SyndEntry entry;
		SyndContent description;
		NewsEntry news = (NewsEntry) model;
		// Integer commentCount = (Integer) map.get("commentCnt");
		entry = new SyndEntryImpl();
		entry.setTitle(news.getTitle());
		entry.setLink(_rootUrl + "/news/" + news.getId());
		try {
			entry.setPublishedDate(dateParser.parse(news.getCreateTime()));
		} catch (ParseException ex) {
			try {
				dateParser = new SimpleDateFormat(DATE_FORMAT);
				entry.setPublishedDate(dateParser.parse(DateUtils
						.getDateStrFromTime(news.getCreateTime())));
			} catch (ParseException ex2) {
				ex2.printStackTrace();
			}
		}
		entry.setAuthor(news.getAuthor());

		description = new SyndContentImpl();
		description.setType("text/html");
		// 加入RSS底部信息，模拟新闻页效果，美化输出效果
		String bottomInfo = "<hr style=\"height:1px;border: none; border-top: 1px solid #EEEEEE;\" />";
		bottomInfo += "<div style=\"text-align:right;color:#999999;\">"
				+ "<img src=\""
				+ _rootUrl
				+ "/images/icons/calendar_view_day.png\" align=\"absmiddle\" />&nbsp;发布时间："
				+ news.getCreateTime() + "&nbsp;|&nbsp;<img src=\"" + _rootUrl
				+ "/images/icons/note.png\" align=\"absmiddle\" />&nbsp;阅读："
				+ news.getHits() + "&nbsp;|&nbsp;";
		// <img src=\""
		// + _rootUrl
		// + "/images/icons/comment.png\" align=\"absmiddle\" />&nbsp;评论："
		// + commentCount + "&nbsp;</div>";
		description.setValue(news.getContent() + bottomInfo);
		entry.setDescription(description);

		return entry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findModels(Integer start, Integer limit, Map requestMap) {
		// 获取新闻列表
		List<NewsEntry> models = newsService.findNewses(true, NewsStateType.OK
				.getValue(), null, start, limit);
		// List<Map<Object, Object>> newsList = new ArrayList<Map<Object,
		// Object>>();
		for (NewsEntry e : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			// 包含评论数信息
			m.put("commentCnt", newsService.getCommentsCnt(e.getId()));
			// newsList.add(m);
		}
		return models;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRssTitle(Map requestMap) {
		return I18nMsgUtils.getInstance().getMessage("rss.feed.title.news");
	}

}