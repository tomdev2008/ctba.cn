package org.net9.core.rss.adaptor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.types.SharePrivateStateType;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class ShareRssAdaptor extends RssAdaptor {
	private static final long serialVersionUID = 1L;

	@Override
	public SyndEntry adaptEntry(BaseModel model) {
		DateFormat dateParser = new SimpleDateFormat(DATE_FORMAT_WITH_TIME);
		SyndEntry entry;
		SyndContent description;
		Share share = (Share) model;
		entry = new SyndEntryImpl();
		try {
			entry.setPublishedDate(dateParser.parse(share.getUpdateTime()));
		} catch (ParseException ex) {
			try {
				dateParser = new SimpleDateFormat(DATE_FORMAT);
				entry.setPublishedDate(dateParser.parse(DateUtils
						.getDateStrFromTime(share.getUpdateTime())));
			} catch (ParseException ex2) {
				ex2.printStackTrace();
			}
		}
		entry.setAuthor(share.getUsername());
		// 分享页面的地址
		entry.setLink(_rootUrl + "/share/comment/" + share.getId());
		if (wrapIt) {
			entry.setTitle(share.getUsername() + ":" + share.getLabel());
		} else {
			entry.setTitle(share.getLabel());
		}
		// 构造RSS主体内容
		String content = "";
		if (share.getUrl() == null || share.getUrl().trim().equals("")) {
			// 只分享了文字内容
			// entry.setTitle(share.getUsername() + " 说");
			content += share.getLabel();
		} else {
			// 分享了一个链接
			// entry.setTitle(share.getUsername() + " 分享了一个链接");
			content += "<a href='" + share.getUrl() + "' target='_blank'>"
					+ share.getLabel() + "</a>";
		}
		if (wrapIt) {
			// 底部信息，包括：评论数
			int shareCommentsCount = shareService.getShareCommentCnt(null,
					share.getId());
			String bottomInfo = "<hr style=\"height:1px;border: none; border-top: 1px solid #EEEEEE;margin-top:20px\" />";
			bottomInfo += "<div style='color:#999999;'>" + "<a href='"
					+ _rootUrl + "/share/comment/" + share.getId()
					+ "' target='_blank'>comments (" + shareCommentsCount
					+ ")</a>" + "</div>";
			content += bottomInfo;
		}
		description = new SyndContentImpl();
		description.setType("text/html");
		description.setValue(content);
		entry.setDescription(description);
		return entry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List findModels(Integer start, Integer limit, Map requestMap) {
		String usernick = (String) requestMap.get("usernick");
		String userid = (String) requestMap.get("uid");
		String username = "";
		if (StringUtils.isNotEmpty(usernick)) {
			User u = this.userService.getUserByNick(usernick);
			username = u == null ? null : u.getUserName();
		} else if (StringUtils.isNotEmpty(userid)) {
			User u = this.userService.getUser(Integer.getInteger(userid), null);
			username = u == null ? null : u.getUserName();
		}

		String wrapItStr = (String) requestMap.get("wrap");
		if ("false".equals(wrapItStr)) {
			wrapIt = false;
		}
		if (StringUtils.isNotEmpty(username)) {
			return shareService.findShares(username, null,
					SharePrivateStateType.PUBLIC.getValue(), start, limit);
		} else {
			List<Share> shares = shareService.findAllShares(null, start, limit);
			return shares;
		}
	}

	/** 是否加上附加信息 */
	private boolean wrapIt = true;

	@SuppressWarnings("unchecked")
	@Override
	public String getRssTitle(Map requestMap) {
		String usernick = (String) requestMap.get("usernick");
		String userid = (String) requestMap.get("uid");
		String username = "";
		if (StringUtils.isNotEmpty(usernick)) {
			User u = this.userService.getUserByNick(usernick);
			username = u == null ? null : u.getUserName();
		} else if (StringUtils.isNotEmpty(userid)) {
			User u = this.userService.getUser(Integer.getInteger(userid), null);
			username = u == null ? null : u.getUserName();
		}
		if (StringUtils.isNotEmpty(username)) {
			return username
					+ " "
					+ I18nMsgUtils.getInstance().getMessage(
							"rss.feed.title.shares");
		} else {
			return I18nMsgUtils.getInstance().getMessage(
					"rss.feed.title.shares");
		}

	}

}