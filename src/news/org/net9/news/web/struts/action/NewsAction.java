package org.net9.news.web.struts.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.CommendType;
import org.net9.core.types.NewsStateType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.core.User;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsCategory;
import org.net9.domain.model.news.NewsComment;
import org.net9.domain.model.news.NewsEntry;
import org.net9.domain.model.news.NewsPost;
import org.net9.search.lucene.search.ref.LuceneNewsEntryReferenceSearcher;

import com.google.inject.Inject;

/**
 * news action
 * 
 * @author gladstone
 * 
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class NewsAction extends BusinessDispatchAction {

	@Inject
	private LuceneNewsEntryReferenceSearcher referenceSearcher;

	/**
	 * bad hit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	public ActionForward bad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nid = request.getParameter("nid");
		NewsEntry model = newsService.getNews(new Integer(nid));
		Integer badHits = model.getHitBad();
		if (badHits == null) {
			badHits = 0;
		}
		if (canHit(request, nid)) {
			badHits++;
		}
		model.setHitBad(badHits);
		newsService.saveNews(model);
		String message = "" + badHits;
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean canHit(HttpServletRequest request, String id) {
		boolean reval = false;
		String hitDone = (String) request.getSession().getAttribute(
				"hitDone_news" + id);
		if (StringUtils.isEmpty(hitDone)) {
			reval = true;
			request.getSession().setAttribute("hitDone_news" + id,
					"hitDone_news");
		}
		return reval;
	}

	/**
	 * list of categories
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward catList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);
		return mapping.findForward("news.cat.list");
	}

	/**
	 * delete news comment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		NewsComment model = newsService.getComment(new Integer(cid));

		// validate user
		String username = UserHelper.getuserFromCookie(request);
		UserHelper.authUserForCurrentPojoSimply(username, model);

		Integer nid = model.getNewsId();
		newsService.deleteComment(model);
		return new ActionForward(UrlConstants.NEWS_ENRTY + nid, true);
	}

	/**
	 * good hit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward good(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isEmpty(username)) {
			request.setAttribute("message", "login");
			return mapping.findForward("message");
		}
		String nid = request.getParameter("nid");
		NewsEntry model = newsService.getNews(new Integer(nid));
		Integer goodHits = model.getHitGood();
		if (goodHits == null) {
			goodHits = 0;
		}
		if (canHit(request, nid)) {
			goodHits++;
		}
		model.setHitGood(goodHits);
		newsService.saveNews(model);
		String message = "" + goodHits;
		request.setAttribute("message", message);
		return mapping.findForward("message");
	}

	/**
	 * 新闻首页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward indexPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);

		// 主要新闻列表
		List<NewsEntry> models = newsService.findNewses(false, NewsStateType.OK
				.getValue(), null, start, limit);
		List<Map<Object, Object>> newsList = new ArrayList<Map<Object, Object>>();
		for (NewsEntry e : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("commentCnt", newsService.getCommentsCnt(e.getId()));
			newsList.add(m);
		}
		request.setAttribute("newsList", newsList);
		request.setAttribute("models", models);
		Integer count = newsService.getNewsCnt(false, NewsStateType.OK
				.getValue(), null);
		request.setAttribute("count", count);

		// 新闻评论
		List<NewsComment> newComments = newsService.findComments(null, true, 0,
				30);
		request.setAttribute("newComments", newComments);

		// 好评新闻
		List<NewsEntry> goodNewses = newsService.findNewsesOrderByDigg(true,
				NewsStateType.OK.getValue(), null, CommonUtils
						.getDateFromTodayOn(-30), 0, 15);
		// 确认列表不为空
		if (goodNewses.size() < 1) {
			goodNewses = newsService.findNewsesOrderByDigg(true,
					NewsStateType.OK.getValue(), null, null, 0, 15);
		}
		request.setAttribute("goodNewses", goodNewses);

		// 点击量最大新闻
		// TODO: mysql performance,care 4 dime!
		// List<NewsEntry> hotNewses = newsService.findHotNewses(false, 0, 15);
		List<NewsEntry> hotNewses = newsService.findHotNewses(true, 0, 15);

		request.setAttribute("hotNewses", hotNewses);
		this.prepareCommends(request);
		return mapping.findForward("news.indexPage");
	}

	/**
	 * list newses
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String cidStr = request.getParameter("cid");
		Integer cid = null;
		if (StringUtils.isNotEmpty(cidStr)) {
			cid = new Integer(cidStr);
			NewsCategory category = newsService.getCategory(cid);
			request.setAttribute("category", category);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<NewsEntry> models = newsService.findNewses(true, NewsStateType.OK
				.getValue(), cid, start, limit);
		Integer count = newsService.getNewsCnt(true, NewsStateType.OK
				.getValue(), cid);
		List<Map<Object, Object>> newsList = new ArrayList<Map<Object, Object>>();
		for (NewsEntry e : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("commentCnt", newsService.getCommentsCnt(e.getId()));
			newsList.add(m);
		}
		request.setAttribute("newsList", newsList);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		request.setAttribute("isUser", StringUtils.isNotEmpty(username));

		// 好评新闻
		// TODO: copy-past,change this
		List<NewsEntry> goodNewses = newsService.findNewsesOrderByDigg(false,
				NewsStateType.OK.getValue(), null, CommonUtils
						.getDateFromTodayOn(-7), 0, 8);
		// 确认列表不为空
		if (goodNewses.size() < 1) {
			goodNewses = newsService.findNewsesOrderByDigg(false,
					NewsStateType.OK.getValue(), null, null, 0, 8);
		}
		request.setAttribute("goodNewses", goodNewses);
		// 最新新闻
		List<NewsEntry> lastNewses = newsService.findNewses(false,
				NewsStateType.OK.getValue(), null, 0, 8);
		request.setAttribute("lastNewses", lastNewses);
		this.prepareCommends(request);
		return mapping.findForward("news.list");
	}

	/**
	 * post a news
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward post(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		String tid = request.getParameter("tid");
		String gtid = request.getParameter("gtid");
		String eid = request.getParameter("eid");
		if (StringUtils.isNotEmpty(tid)) {
			Topic topic = this.topicService.getTopic(Integer.valueOf(tid));
			if (topic != null) {
				String topicContent = UBBDecoder.decode(CommonUtils
						.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(topic
								.getTopicContent())),
						new UBBSimpleTagHandler(), UBBDecoder.MODE_IGNORE);
				topicContent = CustomizeUtils.parseEmotionImg(topicContent);
				request.setAttribute("proxyContent", topicContent);
				request.setAttribute("proxyTitle", topic.getTopicTitle());
			}
		}
		if (StringUtils.isNotEmpty(gtid)) {
			GroupTopic topic = this.groupTopicService.getTopic(Integer
					.valueOf(gtid));
			if (topic != null) {
				String topicContent = UBBDecoder.decode(CommonUtils
						.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(topic
								.getContent())), new UBBSimpleTagHandler(),
						UBBDecoder.MODE_IGNORE);
				topicContent = CustomizeUtils.parseEmotionImg(topicContent);
				request.setAttribute("proxyContent", topicContent);
				request.setAttribute("proxyTitle", topic.getTitle());
			}
		}
		if (StringUtils.isNotEmpty(eid)) {
			BlogEntry entry = this.entryService.getEntry(Integer.valueOf(eid));
			if (entry != null) {
				request.setAttribute("proxyContent", entry.getBody());
				request.setAttribute("proxyTitle", entry.getTitle());
			}
		}

		if (StringUtils.isNotEmpty(cid)) {
			NewsCategory cat = newsService.getCategory(new Integer(cid));
			request.setAttribute("category", cat);
		}

		// 获取新闻分类列表
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);
		this.prepareCommends(request);
		return mapping.findForward("news.post");
	}

	private void prepareCommends(HttpServletRequest request) {
		List<MainCommend> commendList = commonService.findMainCommendsByType(
				CommendType.NEWS.getValue(), 0, 10);
		request.setAttribute("commendList", commendList);
	}

	/**
	 * save news comment
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward saveComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String content = request.getParameter("content");
		String title = "comment_" + username;// request.getParameter("content");
		String nid = request.getParameter("nid");
		String ip = HttpUtils.getIpAddr(request);
		NewsComment model = new NewsComment();
		model.setAuthor(username);
		model.setContent(content);
		model.setCreateTime(DateUtils.getNow());
		model.setUpdateTime(DateUtils.getNow());
		model.setIp(ip);
		model.setNewsId(new Integer(nid));
		model.setTitle(title);
		newsService.saveComment(model);
		userService.trigeEvent(this.userService.getUser(username), nid,
				UserEventType.NEWS_COMMENT);

		NewsEntry newsEntry = this.newsService.getNews(Integer.valueOf(nid));
		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapNewsEntry(newsEntry) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}

		return new ActionForward(UrlConstants.NEWS_ENRTY + nid, true);
	}

	/**
	 * save a post
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String pid = request.getParameter("pid");
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		String ip = HttpUtils.getIpAddr(request);

		// 新闻分类
		String categoryID = request.getParameter("category");

		if (StringUtils.isEmpty(pid)) {
			// NewsCategory cat = newsService.getCategory(new Integer(cid));
			NewsPost model = new NewsPost();
			model.setAuthor(username);
			model.setContent(content);
			model.setCreateTime(DateUtils.getNow());
			model.setUpdateTime(DateUtils.getNow());
			model.setIp(ip);
			model.setTitle(title);
			model.setDone(0);
			model.setCat(Integer.valueOf(categoryID));
			newsService.savePost(model);
			ActionMessage msg = new ActionMessage("news.post.saved");
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			userService.trigeEvent(this.userService.getUser(username), title
					+ "", UserEventType.NEWS_POST);

			request.setAttribute("model", model);
		} else {
			// NewsCategory cat = newsService.getCategory(new Integer(cid));
			NewsPost model = newsService.getPost(new Integer(pid));
			model.setAuthor(username);
			model.setContent(content);
			model.setCreateTime(DateUtils.getNow());
			model.setUpdateTime(DateUtils.getNow());
			model.setIp(ip);
			model.setTitle(title);
			model.setCat(Integer.valueOf(categoryID));
			newsService.savePost(model);
			ActionMessage msg = new ActionMessage("news.post.updated");
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);

			request.setAttribute("model", model);
		}

		// 新闻的分类
		NewsCategory category = newsService.getCategory(Integer
				.valueOf(categoryID));
		request.setAttribute("category", category);

		// 获取新闻分类列表
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);

		return mapping.findForward("news.post");
	}

	/**
	 * view news
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String nid = request.getParameter("nid");
		String fakeUrl = request.getParameter("fake-url");
		NewsEntry model = null;
		if (StringUtils.isEmpty(nid)) {
			model = newsService.getNewsByFakeUrl(fakeUrl);
		} else {
			model = newsService.getNews(new Integer(nid));
		}

		// #735 (对于空贴或者其他导致nullpointer例外的情况的处理)
		if (model == null) {
			throw new ModelNotFoundException();
		}

		// hit++
		Integer hit = model.getHits();
		if (hit == null) {
			hit = 0;
		}
		int hitPlus = new Random().nextInt(5) + 1;
		model.setHits(hit + hitPlus);
		newsService.saveNews(model);
		NewsCategory category = model.getNewsCategory();
		request.setAttribute("category", category);
		List<NewsComment> comments = newsService.findComments(model.getId(),
				false, start, limit);
		List commentList = new ArrayList();
		for (NewsComment comment : comments) {
			Map m = new HashMap();
			m.put("comment", comment);
			User user = userService.getUser(null, comment.getAuthor());
			m.put("user", user);
			m.put("isAuthor", user.getUserName().equals(username));
			String content = UBBDecoder.decode(CommonUtils
					.htmlEncode(CustomizeUtils.getStrByHtmlTagConfig(comment
							.getContent())), new UBBSimpleTagHandler(),
					UBBDecoder.MODE_IGNORE);
			content = CustomizeUtils.parseEmotionImg(content);
			m.put("content", content);
			comment.setContent(content);
			commentList.add(m);
		}

		Integer commentCnt = newsService.getCommentsCnt(model.getId());
		request.setAttribute("isUser", StringUtils.isNotEmpty(username));
		request.setAttribute("start", start);
		request.setAttribute("model", model);
		request.setAttribute("comments", commentList);
		request.setAttribute("commentCnt", commentCnt);
		request.setAttribute("count", commentCnt);

		// 好评新闻
		List<NewsEntry> goodNewses = newsService.findNewsesOrderByDigg(true,
				NewsStateType.OK.getValue(), null, CommonUtils
						.getDateFromTodayOn(-30), 0, 8);
		// 确认列表不为空
		if (goodNewses.size() < 1) {
			goodNewses = newsService.findNewsesOrderByDigg(true,
					NewsStateType.OK.getValue(), null, null, 0, 8);
		}
		request.setAttribute("goodNewses", goodNewses);
		// 最新新闻
		List<NewsEntry> lastNewses = newsService.findNewses(false,
				NewsStateType.OK.getValue(), null, 0, 8);
		request.setAttribute("lastNewses", lastNewses);
		this.prepareCommends(request);

		try {
			List<Map<String, String>> refEntries = referenceSearcher
					.searchByKey(model.getTitle(), UrlConstants.NEWS_ENRTY
							+ model.getId());
			request.setAttribute("refEntries", refEntries);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return mapping.findForward("news.view");
	}

}
