package org.net9.core.rss.adaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.core.wrapper.RssTimelineWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.core.UserLog;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class TimelineRssAdaptor extends RssAdaptor {
	private static final long serialVersionUID = 1L;
	@Override
	public SyndEntry adaptEntry(BaseModel model) {

		DateFormat dateParser = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		SyndEntry entry;
		SyndContent description;
		UserLog log = (UserLog) model;
		entry = new SyndEntryImpl();
		entry.setTitle("[" + log.getUsername() + "]");
		entry.setLink(_rootUrl + "/u/" + userService.getUser(null, log.getUsername()).getUserId());
		try {
			entry.setPublishedDate(dateParser.parse(log.getUpdateTime()));
		} catch (ParseException ex) {
			try {
				dateParser = new SimpleDateFormat(DATE_FORMAT);
				entry.setPublishedDate(dateParser.parse(DateUtils
						.getDateStrFromTime(log.getUpdateTime())));
			} catch (ParseException ex2) {
				ex2.printStackTrace();
			}
		}
		entry.setAuthor(log.getUsername());

		description = new SyndContentImpl();
		description.setType("text/plain");

		description.setValue(RssTimelineWrapper.getInstance().wrappeEvent(
				(UserLog) model));
		entry.setDescription(description);
		return entry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findModels(Integer start, Integer limit, Map requestMap) {
		List<UserLog> userLogs = userService.findUserlogs(null, null, null,
				start, limit, UserEventWrapper.DEFAULT_WANTED_TYPES);
		return userLogs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getRssTitle(Map requestMap) {
		return I18nMsgUtils.getInstance().getMessage("rss.feed.title.timeline");
	}

}