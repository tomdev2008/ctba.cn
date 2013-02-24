package org.net9.core.wrapper;

import org.net9.bbs.service.BoardService;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.EntryService;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.UrlConstants;
import org.net9.core.service.UserService;
import org.net9.core.types.UserEventType;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;
import org.net9.gallery.service.ImageService;
import org.net9.group.service.ActivityService;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;
import org.net9.news.service.NewsService;
import org.net9.vote.service.VoteService;

import cn.ctba.share.service.ShareService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.servlet.ServletModule;

/**
 * RSS用户事件展现器
 * 
 * @author gladstone
 * 
 */
public class RssTimelineWrapper {

	private static RssTimelineWrapper instance;
	private final static int DEFAULT_TITLE_SIZE = 20;

	/**
	 * 构造版块链接字符串，形如：<code>&lt;a href='b/1'&gt;BoardName&lt;/a&gt;</code>
	 * 
	 * @param board
	 *            Board实例
	 * @return 包含有指向版块的链接字符串
	 */
	private static String getBoardLink(Board board) {
		String link = "<a href='b/" + board.getBoardId() + "'>"
				+ board.getBoardName() + "</a>";
		return link;
	}

	/**
	 * 构造图片链接字符串,指向的是小组相册
	 * 
	 * @param image
	 *            ImageModel实例
	 * @return 包含有指向小组相册的链接字符串
	 */
	private static String getImageLink(ImageModel image) {
		String link = "<a href='"
				+ UrlConstants.GROUP_GALLERY
				+ image.getGroupId()
				+ "'>"
				+ StringUtils
						.getShorterStr(image.getName(), DEFAULT_TITLE_SIZE)
				+ "</a>";
		return link;
	}

	public static RssTimelineWrapper getInstance() {
		if (instance == null) {
			instance = new RssTimelineWrapper();
		}
		return instance;
	}

	@Inject
	protected UserService userService;
	@Inject
	protected ShareService shareService;
	@Inject
	protected TopicService topicService;
	@Inject
	protected GroupService groupService;
	@Inject
	protected GroupTopicService groupTopicService;
	@Inject
	protected EntryService entryService;
	@Inject
	protected ActivityService activityService;
	@Inject
	protected NewsService newsService;
	@Inject
	protected VoteService voteService;
	@Inject
	protected ImageService imageService;
	@Inject
	protected BlogService blogService;
	@Inject
	protected BoardService boardService;

	public RssTimelineWrapper() {
		inject();
	}

	private String _wrappeBbsEvent(UserLog event) {
		if (event.getType() == UserEventType.TOPIC_EDIT.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.TOPIC_EDIT",
						new Object[] {
								getWrappedUserName(event),
								"<a href='topic/"
										+ topic.getTopicId()
										+ "'>"
										+ StringUtils.getShorterStr(topic
												.getTopicTitle(),
												DEFAULT_TITLE_SIZE) + "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(topic.getTopicContent());
			}
		} else if (event.getType() == UserEventType.TOPIC_NEW.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				Board board = boardService.getBoard(topic.getTopicBoardId());
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.TOPIC_NEW",
						new Object[] {
								getWrappedUserName(event),
								getBoardLink(board),
								"<a href='topic/"
										+ topic.getTopicId()
										+ "'>"
										+ StringUtils.getShorterStr(topic
												.getTopicTitle(),
												DEFAULT_TITLE_SIZE) + "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(topic.getTopicContent());
			}
		} else if (event.getType() == UserEventType.TOPIC_REPLY.getValue()) {
			String tid = event.getTarget();
			Topic topic = topicService.getTopic(Integer.parseInt(tid));
			if (topic != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.TOPIC_REPLY",
						new Object[] {
								getWrappedUserName(event),
								"<a href='topic/"
										+ topic.getTopicId()
										+ "'>"
										+ StringUtils.getShorterStr(topic
												.getTopicTitle(),
												DEFAULT_TITLE_SIZE) + "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(topic.getTopicContent());
			}
		} else if (event.getType() == UserEventType.VOTE_DO.getValue()) {
			Vote vote = voteService
					.getVote(Integer.parseInt(event.getTarget()));
			if (vote != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.VOTE_DO",
						new Object[] {
								getWrappedUserName(event),
								"<a href='vote/"
										+ vote.getId()
										+ "'>"
										+ StringUtils.getShorterStr(vote
												.getQuestion(),
												DEFAULT_TITLE_SIZE) + "</a>" });
				return prefix + "<br/>" + vote.getQuestion();
			}
		} else if (event.getType() == UserEventType.VOTE_COMMENT.getValue()) {
			Vote vote = voteService
					.getVote(Integer.parseInt(event.getTarget()));
			if (vote != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.VOTE_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								"<a href='vote/"
										+ vote.getId()
										+ "'>"
										+ StringUtils.getShorterStr(vote
												.getQuestion(),
												DEFAULT_TITLE_SIZE) + "</a>" });
				return prefix + "<br/>" + vote.getQuestion();
			}
		} else if (event.getType() == UserEventType.VOTE_NEW.getValue()) {
			Vote vote = voteService
					.getVote(Integer.parseInt(event.getTarget()));
			if (vote != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.VOTE_NEW",
						new Object[] {
								getWrappedUserName(event),
								"<a href='vote/"
										+ vote.getId()
										+ "'>"
										+ StringUtils.getShorterStr(vote
												.getQuestion(),
												DEFAULT_TITLE_SIZE) + "</a>" });
				return prefix + "<br/>" + vote.getQuestion();
			}
		}
		return _wrappeBlogEvent(event);
	}

	private String _wrappeBlogEvent(UserLog event) {
		if (event.getType() == UserEventType.BLOG_NEW.getValue()) {
			String prefix = I18nMsgUtils.getInstance().createMessage(
					"timeline.BLOG_NEW",
					new Object[] { getWrappedUserName(event) });
			Blog blog = this.blogService
					.getBlogByUser(getWrappedUserName(event));
			return prefix + "<br/>" + blog.getName();
		} else if (event.getType() == UserEventType.COMMENT_NEW.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.COMMENT_NEW",
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
				return prefix + "<br/>" + entry.getBody();
			}
		} else if (event.getType() == UserEventType.ENTRY_EDIT.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.ENTRY_EDIT",
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
				return prefix + "<br/>" + entry.getBody();
			}
		} else if (event.getType() == UserEventType.ENTRY_NEW.getValue()) {
			String entryId = event.getTarget();
			BlogEntry entry = entryService.getEntry(Integer.parseInt(entryId));
			if (entry != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.ENTRY_NEW",
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
				return prefix + "<br/>" + entry.getBody();
			}
		}
		return _wrappeGroupEvent(event);
	}

	private String _wrappeCoreEvent(UserLog event) {
		if (event.getType() == UserEventType.ADD_FRIEND.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"timeline.ADD_FRIEND",
					new Object[] { getWrappedUserName(event),
							getTargetUserNameLink(event) });
		} else if (event.getType() == UserEventType.EDIT_FACE.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"timeline.EDIT_FACE",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.EDIT_INFO.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"timeline.EDIT_INFO",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.REGISTER.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"timeline.REGISTER",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.NOTE.getValue()) {
			String targetUsername = event.getTarget();
			User targetUser = userService.getUser(null, targetUsername);
			if (targetUser != null
					&& !targetUser.getUserName().equalsIgnoreCase(
							event.getUsername())) {
				return I18nMsgUtils.getInstance().createMessage(
						"timeline.NOTE",
						new Object[] {
								getWrappedUserName(event),
								"<a href='u/" + targetUser.getUserId() + "'>"
										+ targetUsername + "</a>" });
			}
		} else if (event.getType() == UserEventType.MAIL_INVITE.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"timeline.MAIL_INVITE",
					new Object[] { getWrappedUserName(event) });
		} else if (event.getType() == UserEventType.SHARE_NEW.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {
				if (StringUtils.isEmpty(model.getUrl())) {
					return I18nMsgUtils.getInstance().createMessage(
							"timeline.SHARE_NEW_WORD_ONLY",
							new Object[] { getWrappedUserName(event),
									"share/" + getUser(event).getUserId(),
									model.getLabel() });
				} else {
					return I18nMsgUtils.getInstance().createMessage(
							"timeline.SHARE_NEW",
							new Object[] { getWrappedUserName(event),
									model.getUrl(), model.getLabel() });
				}
			}
		} else if (event.getType() == UserEventType.SHARE_NEW_TOOL.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {
				if (StringUtils.isEmpty(model.getUrl())) {
					return I18nMsgUtils.getInstance().createMessage(
							"timeline.SHARE_NEW_TOOL_WORD_ONLY",
							new Object[] { getWrappedUserName(event),
									"share/" + getUser(event).getUserId(),
									model.getLabel() });
				} else {
					return I18nMsgUtils.getInstance().createMessage(
							"timeline.SHARE_NEW_TOOL",
							new Object[] { getWrappedUserName(event),
									model.getUrl(), model.getLabel() });
				}
			}
		} else if (event.getType() == UserEventType.SHARE_COMMENT.getValue()) {
			Share model = this.shareService.getShare(Integer.valueOf(event
					.getTarget()));
			if (model != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"timeline.SHARE_COMMENT",
						new Object[] { getWrappedUserName(event),
								model.getId(), model.getLabel() });
			}
		}
		return _wrappeBbsEvent(event);
	}

	private String _wrappeGroupEvent(UserLog event) {
		if (event.getType() == UserEventType.GROUP_ACTIVITY_JOIN.getValue()) {
			String activityId = event.getTarget();
			ActivityModel act = activityService.getActivity(Integer
					.parseInt(activityId));
			if (act != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_ACTIVITY_JOIN",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.ACTIVITY
										+ act.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(act.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
			}
		} else if (event.getType() == UserEventType.GROUP_ACTIVITY_COMMENT
				.getValue()) {
			String activityId = event.getTarget();
			ActivityModel act = activityService.getActivity(Integer
					.parseInt(activityId));
			if (act != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_ACTIVITY_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.ACTIVITY
										+ act.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(act.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(act.getContent());
			}
		} else if (event.getType() == UserEventType.GROUP_ACTIVITY_NEW
				.getValue()) {
			String activityId = event.getTarget();
			ActivityModel act = activityService.getActivity(Integer
					.parseInt(activityId));
			if (act != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_ACTIVITY_NEW",
						new Object[] {
								getWrappedUserName(event),
								getGroupLink(act.getGroupModel()),
								"<a href='"
										+ UrlConstants.ACTIVITY
										+ act.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(act.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(act.getContent());
			}
		} else if (event.getType() == UserEventType.GROUP_APPLY.getValue()) {
			String gid = event.getTarget();
			GroupModel group = groupService.getGroup(Integer.parseInt(gid));
			if (group != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_APPLY",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.GROUP
										+ group.getId()
										+ "'>"
										+ StringUtils.getShorterStr(group
												.getName(), DEFAULT_TITLE_SIZE)
										+ "</a>" });
			}
		} else if (event.getType() == UserEventType.GROUP_IMG_NEW.getValue()) {
			ImageModel image = imageService.getImage(Integer.valueOf(event
					.getTarget()));
			if (image != null) {
				Integer groupID = image.getGroupId();
				GroupModel group = groupService.getGroup(groupID);
				return I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_GALLERY_IMAGE_NEW",
						new Object[] { getWrappedUserName(event),
								getGroupLink(group), getImageLink(image) });
			}
		} else if (event.getType() == UserEventType.GROUP_NEW.getValue()) {
			String gid = event.getTarget();
			GroupModel group = groupService.getGroup(Integer.parseInt(gid));
			if (group != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_NEW",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.GROUP
										+ group.getId()
										+ "'>"
										+ StringUtils.getShorterStr(group
												.getName(), DEFAULT_TITLE_SIZE)
										+ "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(group.getDiscription());
			}
		} else if (event.getType() == UserEventType.GROUP_QUIT.getValue()) {
			String gid = event.getTarget();
			GroupModel group = groupService.getGroup(Integer.parseInt(gid));
			if (group != null) {
				return I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_QUIT",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.GROUP
										+ group.getId()
										+ "'>"
										+ StringUtils.getShorterStr(group
												.getName(), DEFAULT_TITLE_SIZE)
										+ "</a>" });
			}
		} else if (event.getType() == UserEventType.GROUP_TOPIC_NEW.getValue()) {
			String gtid = event.getTarget();
			GroupTopic topic = groupTopicService.getTopic(Integer
					.parseInt(gtid));
			if (topic != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_TOPIC_NEW",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.GROUP
										+ topic.getGroupModel().getId()
										+ "'>"
										+ StringUtils.getShorterStr(topic
												.getGroupModel().getName(),
												DEFAULT_TITLE_SIZE) + "</a>",
								"<a href='"
										+ UrlConstants.GROUP_TOPIC
										+ topic.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(
														topic.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(topic.getContent());
			}
		} else if (event.getType() == UserEventType.GROUP_TOPIC_REPLY
				.getValue()) {
			String gtid = event.getTarget();
			GroupTopic topic = groupTopicService.getTopic(Integer
					.parseInt(gtid));
			if (topic != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_TOPIC_REPLY",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.GROUP
										+ topic.getGroupModel().getId()
										+ "'>"
										+ StringUtils.getShorterStr(topic
												.getGroupModel().getName(),
												DEFAULT_TITLE_SIZE) + "</a>",
								"<a href='"
										+ UrlConstants.GROUP_TOPIC
										+ topic.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(
														topic.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(topic.getContent());
			}
		} else if (event.getType() == UserEventType.GROUP_TOPIC_EDIT.getValue()) {
			String gtid = event.getTarget();
			GroupTopic topic = groupTopicService.getTopic(Integer
					.parseInt(gtid));
			if (topic != null) {
				String prefix = I18nMsgUtils.getInstance().createMessage(
						"timeline.GROUP_TOPIC_EDIT",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.GROUP
										+ topic.getGroupModel().getId()
										+ "'>"
										+ StringUtils.getShorterStr(topic
												.getGroupModel().getName(),
												DEFAULT_TITLE_SIZE) + "</a>",
								"<a href='"
										+ UrlConstants.GROUP_TOPIC
										+ topic.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(
														topic.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
				return prefix + "<br/>"
						+ StringUtils.parseUBBStr(topic.getContent());
			}
		}
		return this._wrappeNewsEvent(event);
	}

	private String _wrappeNewsEvent(UserLog event) {
		if (event.getType() == UserEventType.NEWS_POST.getValue()) {
			return I18nMsgUtils.getInstance().createMessage(
					"timeline.NEWS_POST",
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
						"timeline.NEWS_COMMENT",
						new Object[] {
								getWrappedUserName(event),
								"<a href='"
										+ UrlConstants.NEWS_ENRTY
										+ news.getId()
										+ "'>"
										+ StringUtils
												.getShorterStr(news.getTitle(),
														DEFAULT_TITLE_SIZE)
										+ "</a>" });
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
			String link = "<a href='"
					+ UrlConstants.GROUP
					+ group.getId()
					+ "'>"
					+ StringUtils.getShorterStr(group.getName(),
							DEFAULT_TITLE_SIZE) + "</a>";
			return link;
		} else {
			String link = "<a href='"
					+ UrlConstants.GROUP
					+ group.getMagicUrl()
					+ "'>"
					+ StringUtils.getShorterStr(group.getName(),
							DEFAULT_TITLE_SIZE) + "</a>";
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
		String link = "u/" + u.getUserId();
		String reval = "<a href='" + link + "'>" + u.getUserName() + "</a>";
		return reval;
	}

	private User getUser(UserLog event) {
		User u = userService.getUser(null, event.getUsername());
		return u;
	}

	/**
	 * 得到带链接的用户名
	 * 
	 * @param event
	 * @return
	 */

	private String getWrappedUserName(UserLog event) {
		String reval = event.getUsername();
		User u = userService.getUser(null, event.getUsername());
		// String link = UrlConstants.USER + u.getUserId();
		reval = SimplePojoWrapper.wrapUser(u);
		// "<a href='" + link + "'>" +
		// u.getUserName() + "</a>";
		return reval;
	}

	protected void inject() {
		Guice.createInjector(new ServletModule()).injectMembers(this);
	}

	/**
	 * 具体请参照资源文件
	 * 
	 * @param event
	 * @return
	 */
	public String wrappeEvent(UserLog event) {
		return this._wrappeCoreEvent(event);
	}
}
