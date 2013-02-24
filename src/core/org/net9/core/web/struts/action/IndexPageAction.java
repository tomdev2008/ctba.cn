package org.net9.core.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.blog.service.EntryService;
import org.net9.common.util.CommonUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.types.CommendType;
import org.net9.core.types.GroupTypeHelper;
import org.net9.core.types.NewsStateType;
import org.net9.core.types.SharePrivateStateType;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.gallery.ImageGallery;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsCategory;
import org.net9.domain.model.news.NewsEntry;

/**
 * 
 * 社区系统首页
 * 
 * @author gladstone
 */
public class IndexPageAction extends BusinessDispatchAction {

	/** Logger */
	public static Log log = LogFactory.getLog(IndexPageAction.class);

	@SuppressWarnings("unchecked")
	private void _bbs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ==========BBS==========>>>
		// 顶级版面
		List<Board> topBorads = boardService.findTopBoards(0,
				BusinessConstants.MAX_INT);
		List boardsList = ListWrapper.getInstance().formatBoardList4IndexPage(
				topBorads);
		request.setAttribute("boards", boardsList);

		// 最新文章
		List<Topic> newTopics = topicService.findNewTopicsForIndex(0, 15);
		// .getTopics(null, 0, 10, Constant.TOPIC_TYPE_NORMAL);
		List newTopicsList = ListWrapper.getInstance().formatTopicList(
				newTopics);
		request.setAttribute("topics", newTopicsList);

		// 最近热贴
		List<Topic> hotTopics = topicService.findHotTopicsByTime(null, 10, 0,
				15);
		// .getHotTopicsForIndex();
		List hotTopicsList = ListWrapper.getInstance().formatTopicList(
				hotTopics);
		request.setAttribute("hotTopics", hotTopicsList);

		// 最近更新的文章(最近回复)
		List<Topic> lastUpdatedTopics = topicService.findTopics(null, 0, 15,
				null);
		List lastUpdatedTopicList = ListWrapper.getInstance().formatTopicList(
				lastUpdatedTopics);
		request.setAttribute("lastUpdatedTopics", lastUpdatedTopicList);

		// <<<==========BBS==========

	}

	@SuppressWarnings("unchecked")
	private void _blog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// =========博客==========>>>
		// 活跃博客
		List<Blog> activeBlogs = blogService.findHotBlogs(0, 16);
		List<Map<Object, Object>> hotBlogMapList = ListWrapper.getInstance()
				.formatBlogList(activeBlogs);
		request.setAttribute("hotBlogs", hotBlogMapList);

		// 最新文章
		List<BlogEntry> newEntries = entryService.findNewEntries(null,
				EntryService.EntryType.NORMAL.getType(), 0, 15);
		request.setAttribute("newEntries", newEntries);
		List<Map<Object, Object>> newEntriesMapList = ListWrapper.getInstance()
				.formatBlogEntryList(newEntries);
		request.setAttribute("newEntriesMapList", newEntriesMapList);

		// 最热文章
		List<BlogEntry> hotEntries = entryService.findHotEntries(null,
				EntryService.EntryType.NORMAL.getType(), 0, 12);
		request.setAttribute("hotEntries", hotEntries);

		// <<<=========博客==========

	}

	@SuppressWarnings("unchecked")
	private void _common(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ==========共通==========>>>
		// 用户数目
		request.setAttribute("userCnt", 1 + userService.getUserCnt(null, null));

		// // 在线用户数目
		// Integer onlineCnt = commonService.getOnlineCnt();
		// request.setAttribute("onlineCnt", onlineCnt);

		// 新用户
		List newUsers = userService.findUsers(0, 5);
		request.setAttribute("newUsers", newUsers);

		// // 活跃用户
		// List<ViewHotUser> stars = boardService.findHotUsersForIndex();
		// request.setAttribute("stars", stars);
		// <<<==========共通==========

		// ==========用户事件==========>>>
		List<UserLog> userLogs = userService.findUserlogs(null, null, null, 0,
				26, UserEventWrapper.DEFAULT_WANTED_TYPES);
		List<Map<Object, Object>> userLogsMapList = ListWrapper.getInstance()
				.formatUserLogList(userLogs);
		// 保持在22个
		while (userLogsMapList.size() > 22) {
			try {
				// #859 (由“分享”回“首页”出现错误)
				userLogsMapList.remove(userLogsMapList.size() - 1);
			} catch (Exception e) {
				break;
			}
		}
		request.setAttribute("userEventList", userLogsMapList);
		// Integer eventCntToday = userService.getUserlogsCntBe4SomeDay(null,
		// null, UserEventWrapper.DEFAULT_WANTED_TYPES, CommonUtils
		// .getDateFromTodayOn(0));
		// request.setAttribute("eventCntToday", eventCntToday);
		// <<<==========用户事件==========

		List<MainCommend> commendList = this.commonService
				.findMainCommendsByType(CommendType.INDEX_PAGE.getValue(), 0,
						10);
		request.setAttribute("commendList", commendList);

		// 最近的用户分享（6条）
		List<Share> shareList = this.shareService.findShares(null, null,
				SharePrivateStateType.PUBLIC.getValue(), 0, 6);
		request.setAttribute("shareList", shareList);

//		// 最近的投票(3条)
//		List<Vote> voteList = voteService.findVotes(null, null, null, null, 0,
//				3);
//		request.setAttribute("voteList", voteList);

	}

	/**
	 * ctba相关数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void _ctba(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	private void _gallery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ImageGallery> galleryList = this.imageService.findGalleries(null,
				null, 0, 9);
		request.setAttribute("galleryList", galleryList);
		List<ImageModel> imageList = this.imageService.findGalleryImages(null,
				0, 10);
		request.setAttribute("imageList", imageList);
	}

	@SuppressWarnings("unchecked")
	private void _group(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ==========小组==========>>>
		// 最热小组
		List<GroupModel> hotGroups = groupService.findHotGroups(0, 12);
		request.setAttribute("hotGroups", hotGroups);

		// 最新小组文章
		List<GroupTopic> newGroupTopics = groupTopicService.findTopics(null,
				null, false, "id", 0, 15);
		List newTopicsMap = ListWrapper.getInstance().formatGroupTopic(
				newGroupTopics, false);

		request.setAttribute("newTopics", newGroupTopics);
		request.setAttribute("newTopicsMap", newTopicsMap);

		// 最近更新小组文章
		List<GroupTopic> updatedGroupTopics = groupTopicService.findTopics(
				null, null, false, null, 0, 15);
		request.setAttribute("updatedGroupTopicsMap", ListWrapper.getInstance()
				.formatGroupTopic(updatedGroupTopics, false));

		// 最热小组文章
		// List<GroupTopic> hotGroupTopics = groupTopicService.findTopics(null,
		// null, false, "hits", 0, 15);
		List<GroupTopic> hotGroupTopics = groupTopicService
				.findHotTopicsByTime(null, 30, 0, 15);
		// 如果一个月内没有记录，去全部
		if (hotGroupTopics.size() == 0) {
			hotGroupTopics = groupTopicService.findTopics(null, null, false,
					"hits", 0, 15);
		}

		request.setAttribute("hotGroupTopicsMap", ListWrapper.getInstance()
				.formatGroupTopic(hotGroupTopics, false));

		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		// 小组活动(6条)
		List<ActivityModel> activityList = activityService.findActivities(null,
				null, null, null, 0, 6);
		request.setAttribute("newActivityList", activityList);

		// <<<==========小组==========

	}

	@SuppressWarnings("unchecked")
	private void _news(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// =========新闻==========>>>
		List<NewsCategory> newsCats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("newsCats", newsCats);

		// 当日随机Digg新闻列表
		List<NewsEntry> newsModels = newsService.findNewsesOrderByDigg(false,
				NewsStateType.OK.getValue(), null, CommonUtils
						.getDateFromTodayOn(0), 0, 3);
		// 确认列表不为空
		if (newsModels.size() < 1) {
			newsModels = newsService.findNewsesOrderByDigg(false,
					NewsStateType.OK.getValue(), null, CommonUtils
							.getDateFromTodayOn(-7), 0, 3);
			if (newsModels.size() < 1) {
				newsModels = newsService.findNewsesOrderByDigg(false,
						NewsStateType.OK.getValue(), null, null, 0, 3);
			}
		}
		List<Map<Object, Object>> newsList = new ArrayList<Map<Object, Object>>();
		for (NewsEntry e : newsModels) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("commentCnt", newsService.getCommentsCnt(e.getId()));
			m.put("user", userService.getUser(null, e.getAuthor()));
			newsList.add(m);
		}
		request.setAttribute("newsList", newsList);
		if (newsModels.size() > 0) {
			Integer newsIndex = new Random().nextInt(newsModels.size());
			request.setAttribute("newsIndex", newsIndex);
		}
		// 最新新闻列表
		List<NewsEntry> newestNews = newsService.findNewses(true,
				NewsStateType.OK.getValue(), null, 0, 5);
		List<Map<Object, Object>> newestNewsList = new ArrayList<Map<Object, Object>>();
		for (NewsEntry e : newestNews) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("commentCnt", newsService.getCommentsCnt(e.getId()));
			newestNewsList.add(m);
		}
		request.setAttribute("newestNewsList", newestNewsList);

		// <<<=========新闻==========

	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this._common(mapping, form, request, response);
		this._bbs(mapping, form, request, response);
		this._blog(mapping, form, request, response);
		this._group(mapping, form, request, response);
		this._news(mapping, form, request, response);
		this._ctba(mapping, form, request, response);
		this._gallery(mapping, form, request, response);
		log.debug("forwarding...");
		return mapping.findForward("index");
	}
}
