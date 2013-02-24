package org.net9.core.wrapper;

import org.net9.common.util.StringUtils;
import org.net9.core.constant.UrlConstants;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;
import org.net9.gallery.service.ImageService;

/**
 * 
 * @author gladstone
 * @since Feb 26, 2009
 */
public class SimplePojoWrapper {

	public static String wrapGroupTopic(GroupTopic model) {
		return "<a href='" + UrlConstants.GROUP_TOPIC + model.getId()
				+ "' target='_blank'>" + model.getTitle() + "</a>";
	}

	public static String wrapEquipment(Equipment model) {
		return "<a href='" + UrlConstants.EQUIPMENT + model.getId()
				+ "' target='_blank'>" + model.getName() + "</a>";
	}

	public static String wrapGroupMember(GroupModel model) {
		return "<a href='" + UrlConstants.GROUP_MEMBER + model.getId()
				+ "' target='_blank'>" + model.getName() + "</a>";
	}

	public static String wrapGroup(GroupModel model) {
		if (StringUtils.isEmpty(model.getMagicUrl())) {
			return "<a href='" + UrlConstants.GROUP + model.getId()
					+ "' target='_blank'>" + model.getName() + "</a>";
		} else {
			return "<a href='" + UrlConstants.GROUP + model.getMagicUrl()
					+ "' target='_blank'>" + model.getName() + "</a>";
		}

	}

	public static String wrapTopic(Topic model) {
		return "<a href='" + UrlConstants.TOPIC + model.getTopicId()
				+ "' target='_blank'>" + model.getTopicTitle() + "</a>";
	}

	public static String wrapEntry(BlogEntry model) {
		return "<a href='" + UrlConstants.BLOG_ENRTY + model.getId()
				+ "' target='_blank'>" + model.getTitle() + "</a>";
	}

	public static String wrapActivity(ActivityModel model) {
		return "<a href='" + UrlConstants.ACTIVITY + model.getId()
				+ "' target='_blank'>" + model.getTitle() + "</a>";
	}

	public static String wrapNewsEntry(NewsEntry model) {
		return "<a href='" + UrlConstants.NEWS_ENRTY + model.getId()
				+ "' target='_blank'>" + model.getTitle() + "</a>";
	}

	public static String wrapVote(Vote model) {
		return "<a href='" + UrlConstants.VOTE + model.getId()
				+ "' target='_blank'>" + model.getQuestion() + "</a>";
	}

	public static String wrapShare(Share model) {
		return "<a href='" + UrlConstants.SHARE_COMMENT + model.getId()
				+ "' target='_blank'>" + model.getLabel() + "</a>";
	}

	public static String wrapUser(User model) {
		if (StringUtils.isNotEmpty(model.getUserNick())) {
			return "<a href='" + model.getUserNick() + UrlConstants.USER_PAGE
					+ "' target='_blank'>" + model.getUserName() + "</a>";
		} else {
			return "<a href='" + UrlConstants.USER + model.getUserId()
					+ "' target='_blank'>" + model.getUserName() + "</a>";
		}

	}

	public static String wrapImageModel(ImageModel model) {
		if (ImageService.isGroupImage(model)) {
			return "<a href='" + UrlConstants.GROUP_GALLERY_PHOTO
					+ model.getId() + "' target='_blank'>" + model.getName()
					+ "</a>";
		} else {
			return "<a href='" + UrlConstants.GALLERY_PHOTO + model.getId()
					+ "' target='_blank'>" + model.getName() + "</a>";
		}
	}
}
