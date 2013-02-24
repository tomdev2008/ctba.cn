package org.net9.core.wrapper;

import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.types.UserEventType;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;
import org.net9.gallery.service.ImageService;

/**
 * 用户事件展现器
 * 
 * @author gladstone
 * 
 */
public class MobileUserEventWrapper extends UserEventWrapper {

	private static MobileUserEventWrapper instance;
	private final static int DEFAULT_TITLE_SIZE = 20;

	/**
	 * 构造版块链接字符串
	 * 
	 * @param board
	 *            Board实例
	 * @return 包含有指向版块的链接字符串
	 */
	private static String getBoardLink(Board board) {
		String link = board.getBoardName();
		return link;
	}

	public static MobileUserEventWrapper getInstance() {
		if (instance == null) {
			instance = new MobileUserEventWrapper();
		}
		return instance;
	}

	private String _wrappeBbsEvent(UserLog event) {
		if (event.getType() == UserEventType.TOPIC_EDIT.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.TOPIC_EDIT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(
										topic.getTopicTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.TOPIC_FAV.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.TOPIC_FAV",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(
										topic.getTopicTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.TOPIC_NEW.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				Board board = boardService.getBoard(topic.getTopicBoardId());
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.TOPIC_NEW",
						new Object[] {
								getWrappedUserName(event),
								getBoardLink(board),
								StringUtils.getShorterStr(
										topic.getTopicTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.TOPIC_REPLY.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.TOPIC_REPLY",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(
										topic.getTopicTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.VOTE_DO.getValue()) {
			Vote vote = voteService
					.getVote(Integer.parseInt(event.getTarget()));
			if (vote != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.VOTE_DO",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(vote.getQuestion(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.VOTE_COMMENT.getValue()) {
			Vote vote = voteService
					.getVote(Integer.parseInt(event.getTarget()));
			if (vote != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.VOTE_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(vote.getQuestion(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.VOTE_NEW.getValue()) {
			Vote vote = voteService
					.getVote(Integer.parseInt(event.getTarget()));
			if (vote != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.VOTE_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(vote.getQuestion(),
										DEFAULT_TITLE_SIZE) });
			}
		}
		return _wrappeBlogEvent(event);
	}

	private String _wrappeBlogEvent(UserLog event) {
		if (event.getType() == UserEventType.BLOG_EDIT.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.BLOG_EDIT",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.BLOG_NEW.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.BLOG_NEW",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.COMMENT_NEW.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.COMMENT_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(entry.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.ENTRY_EDIT.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.ENTRY_EDIT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(entry.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.ENTRY_NEW.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.ENTRY_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(entry.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		}else if (event.getType() == UserEventType.ENTRY_EDIT_MOBILE.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.ENTRY_EDIT_MOBILE",
						new Object[] {
								getWrappedUserName(event),
								"<a href='blog/entry/"
										+ entry.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(
														entry.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
			}
		} else if (event.getType() == UserEventType.ENTRY_NEW_MOBILE.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.ENTRY_NEW_MOBILE",
						new Object[] {
								getWrappedUserName(event),
								"<a href='blog/entry/"
										+ entry.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(
														entry.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
			}
		}
		return _wrappeGroupEvent(event);
	}

	private String _wrappeCoreEvent(UserLog event) {
		if (event.getType() == UserEventType.ADD_FRIEND.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.ADD_FRIEND",
					new Object[] { getWrappedUserName(event),
							getTargetUserNameLink(event) });
		} else if (event.getType() == UserEventType.EDIT_FACE.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.EDIT_FACE",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.EDIT_INFO.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.EDIT_INFO",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.LOGIN.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.LOGIN",
					new Object[] { getWrappedUserName(event),
							event.getUpdateTime() });
		} else if (event.getType() == UserEventType.REGISTER.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.REGISTER",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.NOTE.getValue()) {
			String targetUsername = event.getTarget();
			User targetUser = userService.getUser(null, targetUsername);
			if (targetUser != null
					&& !targetUser.getUserName().equalsIgnoreCase(
							event.getUsername())) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.NOTE",
						new Object[] { getWrappedUserName(event),
								targetUsername });
			}
		} else if (event.getType() == UserEventType.MAIL_INVITE.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.MAIL_INVITE",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.SHARE_NEW.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {
				if (StringUtils.isEmpty(model.getUrl())) {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.SHARE_NEW_WORD_ONLY",
							new Object[] { getWrappedUserName(event),
									"share/comment/" + model.getId(),
									model.getLabel() });
				} else {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.SHARE_NEW",
							new Object[] { getWrappedUserName(event),
									"share/comment/" + model.getId(),
									model.getLabel() });
				}
			}

		} else if (event.getType() == UserEventType.SHARE_NEW_TOOL.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {

				if (StringUtils.isEmpty(model.getUrl())) {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.SHARE_NEW_TOOL_WORD_ONLY",
							new Object[] { getWrappedUserName(event),
									"share/comment/" + model.getId(),
									model.getLabel() });
				} else {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.SHARE_NEW_TOOL",
							new Object[] { getWrappedUserName(event),
									"share/comment/" + model.getId(),
									model.getLabel() });
				}
			}
		} else if (event.getType() == UserEventType.SHARE_COMMENT.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.SHARE_COMMENT",
						new Object[] { getWrappedUserName(event),
								model.getId(), model.getLabel() });
			}
		} else if (event.getType() == UserEventType.SHARE_NEW_MOBILE.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.SHARE_NEW_WORD_ONLY_MOBILE",
							new Object[] { getWrappedUserName(event),
									"share/comment/" + model.getId(),
									model.getLabel() });
			}
		}
		return _wrappeBbsEvent(event);
	}

	/**
	 * 装备相关事件渲染
	 * 
	 * @param event
	 * @return
	 */
	private String _wrappeEquipmentEvent(UserLog event) {
		if (event.getType() == UserEventType.EQUIPMENT_COMMENT.getValue()) {
			String equipmentId = event.getTarget();
			Equipment model = this.equipmentService.getEquipment(Integer
					.valueOf(equipmentId));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.EQUIPMENT_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(model.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		}
		if (event.getType() == UserEventType.EQUIPMENT_EDIT.getValue()) {
			String equipmentId = event.getTarget();
			Equipment model = this.equipmentService.getEquipment(Integer
					.valueOf(equipmentId));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.EQUIPMENT_EDIT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(model.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.EQUIPMENT_NEW.getValue()) {
			String equipmentId = event.getTarget();
			Equipment model = this.equipmentService.getEquipment(Integer
					.valueOf(equipmentId));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.EQUIPMENT_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(model.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.EQUIPMENT_REF.getValue()) {
			String array[] = event.getTarget().split("#");
			String equipmentId = array[0];
			String refType = array[1];
			// TODO:资源化
			String ref = "";
			if ("0".equals(refType)) {
				ref = "不感冒";
			} else if ("1".equals(refType)) {
				ref = "暂且关注";
			} else if ("2".equals(refType)) {
				ref = "很喜欢";
			} else if ("3".equals(refType)) {
				ref = "手头有";
			}
			Equipment model = this.equipmentService.getEquipment(Integer
					.valueOf(equipmentId));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.EQUIPMENT_REF",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(model.getName(),
										DEFAULT_TITLE_SIZE), ref });
			}
		} else if (event.getType() == UserEventType.EQUIPMENT_SCORE.getValue()) {
			String equipmentId = event.getTarget();
			Equipment model = this.equipmentService.getEquipment(Integer
					.valueOf(equipmentId));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.EQUIPMENT_SCORE",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(model.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		}
		return this._wrappeNewsEvent(event);
	}

	private String _wrappeGalleryEvent(UserLog event) {
		if (event.getType() == UserEventType.GALLERY_NEW.getValue()) {
			String galleryId = event.getTarget();
			ImageGallery imageGallery = this.imageService
					.getImageGallery(Integer.valueOf(galleryId));
			if (imageGallery != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GALLERY_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(imageGallery
										.getName(), DEFAULT_TITLE_SIZE) });
			}
		}
		if (event.getType() == UserEventType.GALLERY_EDIT.getValue()) {
			String galleryId = event.getTarget();
			ImageGallery imageGallery = this.imageService
					.getImageGallery(Integer.valueOf(galleryId));
			if (imageGallery != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GALLERY_EDIT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(imageGallery
										.getName(), DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GALLERY_IMAGE_NEW
				.getValue()) {
			// gid + "#" + cnt
			String array[] = event.getTarget().split("#");
			ImageGallery imageGallery = this.imageService
					.getImageGallery(Integer.valueOf(array[0]));
			if (imageGallery != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GALLERY_IMAGE_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(imageGallery
										.getName(), DEFAULT_TITLE_SIZE),
								array[1] });
			}
		} else if (event.getType() == UserEventType.GALLERY_IMAGE_COMMENT
				.getValue()) {
			String imageId = event.getTarget();
			ImageModel imageModel = this.imageService.getImage(Integer
					.valueOf(imageId));
			if (imageModel != null) {
				if (ImageService.isGroupImage(imageModel)) {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.GALLERY_IMAGE_COMMENT",
							new Object[] {
									getWrappedUserName(event),
									StringUtils.getShorterStr(imageModel
											.getName(), DEFAULT_TITLE_SIZE) });
				} else {
					return I18nMsgUtils.getInstance().createMessage(
							"mobile.timeline.GALLERY_IMAGE_COMMENT",
							new Object[] {
									getWrappedUserName(event),
									StringUtils.getShorterStr(imageModel
											.getName(), DEFAULT_TITLE_SIZE) });
				}

			}
		}
		return this._wrappeEquipmentEvent(event);
	}

	private String _wrappeGroupEvent(UserLog event) {
		if (event.getType() == UserEventType.GROUP_ACTIVITY_JOIN.getValue()) {
			String activityId = event.getTarget();
			ActivityModel act = activityService.getActivity(Integer
					.parseInt(activityId));
			if (act != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_ACTIVITY_JOIN",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(act.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_ACTIVITY_COMMENT
				.getValue()) {
			String activityId = event.getTarget();
			ActivityModel act = activityService.getActivity(Integer
					.parseInt(activityId));
			if (act != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_ACTIVITY_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(act.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_ACTIVITY_NEW
				.getValue()) {
			String activityId = event.getTarget();
			ActivityModel act = activityService.getActivity(Integer
					.parseInt(activityId));
			if (act != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_ACTIVITY_NEW",
						new Object[] {
								getWrappedUserName(event),
								getGroupLink(act.getGroupModel()),
								StringUtils.getShorterStr(act.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_APPLY.getValue()) {
			String gid = event.getTarget();
			GroupModel group = groupService.getGroup(Integer.parseInt(gid));
			if (group != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_APPLY",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(group.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_IMG_NEW.getValue()) {
			String array[] = event.getTarget().split("#");
			GroupModel group = this.groupService.getGroup(Integer
					.valueOf(array[0]));
			if (group != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_GALLERY_IMAGE_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(group.getName(),
										DEFAULT_TITLE_SIZE), array[1] });
			}

		} else if (event.getType() == UserEventType.GROUP_NEW.getValue()) {
			String gid = event.getTarget();
			GroupModel group = groupService.getGroup(Integer.parseInt(gid));
			if (group != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(group.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_QUIT.getValue()) {
			String gid = event.getTarget();
			GroupModel group = groupService.getGroup(Integer.parseInt(gid));
			if (group != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_QUIT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(group.getName(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_TOPIC_NEW.getValue()) {
			String gtid = event.getTarget();
			GroupTopic topic = groupTopicService.getTopic(Integer
					.parseInt(gtid));
			if (topic != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_TOPIC_NEW",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(topic.getGroupModel()
										.getName(), DEFAULT_TITLE_SIZE),

								StringUtils.getShorterStr(topic.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_TOPIC_REPLY
				.getValue()) {
			String gtid = event.getTarget();
			GroupTopic topic = groupTopicService.getTopic(Integer
					.parseInt(gtid));
			if (topic != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_TOPIC_REPLY",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(topic.getGroupModel()
										.getName(), DEFAULT_TITLE_SIZE),
								StringUtils.getShorterStr(topic.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		} else if (event.getType() == UserEventType.GROUP_TOPIC_EDIT.getValue()) {
			String gtid = event.getTarget();
			GroupTopic topic = groupTopicService.getTopic(Integer
					.parseInt(gtid));
			if (topic != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.GROUP_TOPIC_EDIT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(topic.getGroupModel()
										.getName(), DEFAULT_TITLE_SIZE),
								StringUtils.getShorterStr(topic.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		}
		return this._wrappeGalleryEvent(event);
	}

	private String _wrappeNewsEvent(UserLog event) {
		if (event.getType() == UserEventType.NEWS_POST.getValue()) {
			// 投递新闻timeline
			// 这里暂时只是存了新闻的标题
			return I18nMsgUtils.getInstance().createMessage(
					"mobile.timeline.NEWS_POST",
					new Object[] {
							getWrappedUserName(event),
							StringUtils.getShorterStr(event.getTarget(),
									DEFAULT_TITLE_SIZE) });
		} else if (event.getType() == UserEventType.NEWS_COMMENT.getValue()) {
			// 评论新闻timeline
			NewsEntry news = newsService.getNews(Integer.parseInt(event
					.getTarget()));
			if (news != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"mobile.timeline.NEWS_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								StringUtils.getShorterStr(news.getTitle(),
										DEFAULT_TITLE_SIZE) });
			}
		}
		return "";
	}

	/**
	 * 构造小组链接字符串，形如：<code>&lt;a href='g/1'&gt;GroupName&lt;/a&gt;</code>
	 * 
	 * @param group
	 *            GroupModel实例
	 * @return 包含有指向小组页面的链接字符串
	 */
	private String getGroupLink(GroupModel group) {
		if (StringUtils.isEmpty(group.getMagicUrl())) {
			String link = StringUtils.getShorterStr(group.getName(),
					DEFAULT_TITLE_SIZE);
			return link;
		} else {
			String link = StringUtils.getShorterStr(group.getName(),
					DEFAULT_TITLE_SIZE);
			return link;
		}

	}

	/**
	 * 构造目的用户链接字符串，形如：<code>&lt;a href='u/1'&gt;UserName&lt;/a&gt;</code>
	 * 
	 * @param event
	 *            UserLog实例
	 * @return 包含有指向用户页面的链接字符串
	 */
	private String getTargetUserNameLink(UserLog event) {
		User u = userService.getUser(null, event.getTarget());
		String reval = u.getUserName();
		return reval;
	}

	/**
	 * 得到带链接的用户名
	 * 
	 * @param event
	 * @return
	 */
	private String getWrappedUserName(UserLog event) {
		String reval = event.getUsername();
		return reval;
	}

	@Override
	public String wrappeEvent(UserLog event) {
		return this._wrappeCoreEvent(event);
	}

}
