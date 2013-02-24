package org.net9.core.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.service.BoardService;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.EntryService;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.service.UserService;
import org.net9.core.util.ImageUtils;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.gallery.service.ImageService;
import org.net9.group.service.GroupService;
import org.net9.group.service.GroupTopicService;

import cn.ctba.share.service.ShareService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;

/**
 * 頁面涉及的列表處理，在這裡總結一下
 * 
 * @author gladstone
 * @since 2008-02-24
 */
public class ListWrapper {

	/** Logger */
	private final static Log logger = LogFactory.getLog(ListWrapper.class);
	/** 單實例 */
	private static ListWrapper instance = null;

	/**
	 * 使用單實例
	 * 
	 * @return
	 */
	public static ListWrapper getInstance() {
		if (instance == null) {
			instance = new ListWrapper();
			Injector injector = Guice.createInjector(new ServletModule());
			injector.injectMembers(instance);
		}
		return instance;
	}

	// 使用的DB服务------>>>
	@Inject
	private UserService userService;
	@Inject
	private ShareService shareService;
	@Inject
	private TopicService topicService;
	@Inject
	private BoardService boardService;
	@Inject
	private GroupTopicService groupTopicService;
	@Inject
	private GroupService groupService;
	@Inject
	private EntryService entryService;
	@Inject
	private ImageService imageService;

	// <<<------使用的DB服务
	/**
	 * 处理活动模型
	 * 
	 * @param activityList
	 * @param currUsername
	 * @return
	 */
	public List<Map<Object, Object>> formatActivityList(
			List<ActivityModel> activityList, String currUsername) {
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ActivityModel a : activityList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("activity", a);
			m.put("isManager", a.getUsername().equals(currUsername));
			m.put("user", userService.getUser(null, a.getUsername()));
			models.add(m);
		}
		return models;
	}

	/**
	 * 處理博客文章模型<br>
	 * 從List<BlogEntry> 到List<Map>
	 * 
	 * @param list
	 * @return
	 */
	public List<Map<Object, Object>> formatBlogEntryList(List<BlogEntry> list) {
		List<Map<Object, Object>> blogMapList = new ArrayList<Map<Object, Object>>();
		for (BlogEntry e : list) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("author", userService.getUser(null, e.getAuthor()));
			blogMapList.add(m);
		}
		return blogMapList;
	}

	/**
	 * 處理博客模型<br>
	 * 從List<Blog> 到List<Map>
	 * 
	 * @param list
	 * @return
	 */
	public List<Map<Object, Object>> formatBlogList(List<Blog> list) {
		List<Map<Object, Object>> blogMapList = new ArrayList<Map<Object, Object>>();
		for (Blog b : list) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("blog", b);
			BlogEntry blogEntry = entryService.getNewestEntry(b.getId(),
					EntryService.EntryType.NORMAL.getType());
			Integer entryCnt = entryService.getEntriesCnt(b.getId(), null,
					EntryService.EntryType.NORMAL.getType());
			User author = this.userService.getUser(null, b.getAuthor());
			m.put("author", author);
			m.put("entry", blogEntry);
			m.put("entrycnt", entryCnt);
			blogMapList.add(m);
		}
		return blogMapList;
	}

	/**
	 * 處理博客模型<br>
	 * 從List<Map> 到List<Map>
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> formatBlogMapList(List<Map> list) {
		List<Map<Object, Object>> blogMapList = new ArrayList<Map<Object, Object>>();
		for (Map b : list) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("blog", b);
			Integer bid = ((Long) b.get("id")).intValue();
			BlogEntry blogEntry = entryService.getNewestEntry(bid,
					EntryService.EntryType.NORMAL.getType());
			Integer entryCnt = entryService.getEntriesCnt(bid, null,
					EntryService.EntryType.NORMAL.getType());
			m.put("entry", blogEntry);
			m.put("entrycnt", entryCnt);
			blogMapList.add(m);
		}
		return blogMapList;
	}

	/**
	 * 处理首页版面列表
	 * 
	 * @param list
	 *            List<Board>
	 * @return List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map> formatBoardList4IndexPage(List<Board> list) {
		logger.debug("Format board list for indexPage...");
		List<Map> boards = new ArrayList<Map>();
		for (Board board : list) {
			if (board.getBoardId() <= 0) {
				continue;
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("board", board);
			String boardFace = "images/nobody.png";
			if (CommonUtils.isNotEmpty(board.getBoardFace())) {
				boardFace = SystemConfigVars.UPLOAD_DIR_PATH + "/"
						+ board.getBoardFace();
			}
			List subBoardsModel = boardService.findBoards(board.getBoardId(),
					null, 0, BusinessConstants.MAX_INT);
			List<Map> subBoards = new ArrayList<Map>();
			if (subBoardsModel != null) {
				Iterator itSub = subBoardsModel.iterator();
				while (itSub.hasNext()) {
					Board subBoard = (Board) itSub.next();
					Map<Object, Object> subMap = new HashMap<Object, Object>();
					subMap.put("board", subBoard);
					// Topic newest = topicService.getNewestTopic(subBoard
					// .getBoardId(), Constant.TOPIC_TYPE_NORMAL);
					// 这里添加该板块的最新文章
					// subMap.put("topic", newest);
					String subBoardFace = "images/nobody.png";
					if (CommonUtils.isNotEmpty(subBoard.getBoardFace())) {
						subBoardFace = SystemConfigVars.UPLOAD_DIR_PATH
								+ "/"
								+ ImageUtils.getMiniImageStr(subBoard
										.getBoardFace());
					}
					subMap.put("boardFace", subBoardFace);
					subMap.put("boardMaster", CommonUtils.isNotEmpty(subBoard
							.getBoardMaster1()) ? subBoard.getBoardMaster1()
							: I18nMsgUtils.getInstance().getMessage(
									"board.bm.required"));
					subBoards.add(subMap);
				}
			}
			map.put("boardFace", boardFace);
			map.put("subBoards", subBoards);
			map.put("boardMaster", CommonUtils.isNotEmpty(board
					.getBoardMaster1()) ? board.getBoardMaster1()
					: I18nMsgUtils.getInstance()
							.getMessage("board.bm.required"));
			boards.add(map);
		}
		return boards;
	}

	public List<Map<Object, Object>> formatGalleryList(
			List<ImageGallery> galleries) {
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ImageGallery g : galleries) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("gallery", g);
			m.put("cnt", this.imageService.getImagesCntByGallery(g.getId()));
			models.add(m);
		}
		return models;
	}

	/**
	 * 處理小組模型<br>
	 * 從List<Map> 到List<Map> 一般來說,是用於JDBC得到的列表到前臺所用列表的展現
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> formatGroupMapList(List<Map> list) {
		List<Map<Object, Object>> groupsMapList = new ArrayList<Map<Object, Object>>();
		for (Map<Object, Object> g : list) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("group", g);
			m.put("topicCnt", groupTopicService.getGroupTopicsCnt(new Integer(
					"" + g.get("id")), null, true));
			m.put("userCnt", groupService.getGroupUsersCnt(new Integer(""
					+ g.get("id")), null));

			String face = "images/portraitn.jpg";
			if (StringUtils.isNotEmpty("" + g.get("face"))) {
				face = "" + g.get("face");
			}
			m.put("face", face);
			GroupTopic topic = groupTopicService.getNewestGroupTopic(
					new Integer("" + g.get("id")), false);
			if (topic != null) {
				topic.setContent(StringUtils.getStrWithoutHtmlTag(topic
						.getContent()));
			}
			m.put("topic", topic);
			groupsMapList.add(m);
		}
		return groupsMapList;
	}

	/**
	 * 處理小組模型<br>
	 * 從List<GroupModel> 到List<Map>
	 * 
	 * @param list
	 * @return
	 */
	public List<Map<Object, Object>> formatGroupModelList(List<GroupModel> list) {

		List<Map<Object, Object>> groupsMapList = new ArrayList<Map<Object, Object>>();
		for (GroupModel g : list) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("group", g);
			m.put("topicCnt", groupTopicService.getGroupTopicsCnt(g.getId(),
					null, true));
			m.put("userCnt", groupService.getGroupUsersCnt(g.getId(), null));
			String face = "images/portraitn.jpg";
			if (StringUtils.isNotEmpty("" + g.getFace())) {
				face = "" + g.getFace();
			}
			m.put("face", face);
			GroupTopic topic = groupTopicService.getNewestGroupTopic(g.getId(),
					false);
			if (topic != null) {
				topic.setContent(StringUtils.getStrWithoutHtmlTag(topic
						.getContent()));
			}

			m.put("topic", topic);
			groupsMapList.add(m);
		}
		return groupsMapList;
	}

	/**
	 * 處理小組文章
	 * 
	 * @param list
	 *            List<GroupTopic>
	 * @param isContentWithTag
	 *            是否除去內容中的標籤
	 * @return List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map> formatGroupTopic(List<GroupTopic> list,
			boolean isContentWithTag) {
		List<Map> topicsMapList = new ArrayList<Map>();
		for (GroupTopic gt : list) {
			if (isContentWithTag) {
				gt
						.setContent(StringUtils.getStrWithoutHtmlTag(gt
								.getContent()));
			}
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("topic", gt);
			User user = userService.getUser(null, gt.getAuthor());
			m.put("user", user);
			topicsMapList.add(m);
		}
		return topicsMapList;
	}

	@SuppressWarnings("unchecked")
	public List<Map<Object, Object>> formatShareList(List<Share> list) {
		List wrapList = new ArrayList<Map<String, Object>>();
		for (Share s : list) {
			Map m = new HashMap<String, Object>();
			m.put("share", s);
			// #891 扯谈分享，大家的分享增加用户名及头像、发布时间
			User user = this.userService.getUser(null, s.getUsername());
			m.put("user", user);
			m.put("commentCnt", this.shareService.getShareCommentCnt(null, s
					.getId()));
			wrapList.add(m);
		}
		return wrapList;
	}

	/**
	 * 处理文章列表
	 * 
	 * @param list
	 *            List<Topic> 文章列表
	 * @return List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map> formatTopicList(List<Topic> list) {
		List<Map> topics = new ArrayList<Map>();
		for (Topic topic : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			User author = userService.getUser(null, topic.getTopicAuthor());
			map.put("user", author);
			String userFace = "images/nopic.gif";
			if (CommonUtils.isNotEmpty(author.getUserFace())) {
				userFace = SystemConfigVars.UPLOAD_DIR_PATH + "/"
						+ ImageUtils.getMiniImageStr(author.getUserFace());
			}
			map.put("userFace", userFace);
			map.put("topic", topic);
			map.put("tagStyleClass", topicService.getTopicTagClass(topic
					.getTopicId()));
			topics.add(map);
		}
		return topics;
	}

	/**
	 * 处理用戶事件模型
	 * 
	 * @param activityList
	 * @param currUsername
	 * @return
	 */
	public List<Map<Object, Object>> formatUserLogList(List<UserLog> logs) {
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (UserLog log : logs) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("user", userService.getUser(null, log.getUsername()));
			try {
				String eventStr = UserEventWrapper.getInstance().wrappeEvent(
						log);
				m.put("eventStr", eventStr);
				m.put("event", log);
				if (StringUtils.isNotEmpty(eventStr)) {
					models.add(m);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				continue;
			}
		}
		return models;
	}

	/**
	 * 渲染投票模型
	 * 
	 * @param models
	 * @return
	 */
	public List<Map<Object, Object>> formatVoteList(List<Vote> models) {
		List<Map<Object, Object>> voteMapList = new ArrayList<Map<Object, Object>>();
		for (Vote v : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			User u = userService.getUser(null, v.getUsername());
			m.put("vote", v);
			m.put("user", u);
			voteMapList.add(m);
		}
		return voteMapList;
	}
}
