package org.net9.news.web.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.DateUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NewsStateType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.news.NewsCategory;
import org.net9.domain.model.news.NewsComment;
import org.net9.domain.model.news.NewsEntry;
import org.net9.domain.model.news.NewsPost;

/**
 * manage action for news
 * 
 * @author Administrator
 * 
 */
public class NewsManageAction extends BusinessDispatchAction {
	/**
	 * delete category
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteCat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		NewsCategory model = newsService.getCategory(new Integer(cid));
		newsService.deleteCategory(model);
		return new ActionForward("newsManage.shtml?method=listCategories", true);
	}

	/**
	 * delete comment
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
		newsService.deleteComment(model);
		return new ActionForward("newsManage.shtml?method=listComments", true);
	}

	/**
	 * delete news
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nid = request.getParameter("nid");
		NewsEntry model = newsService.getNews(new Integer(nid));
		if (model != null) {
			newsService.deleteNews(model);
		}
		return new ActionForward("newsManage.shtml?method=listNewses", true);
	}

	/**
	 * delete an post
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = request.getParameter("pid");
		NewsPost model = newsService.getPost(new Integer(pid));
		if (model != null) {
			newsService.deletePost(model);
		}
		return new ActionForward("newsManage.shtml?method=listPosts", true);
	}

	/**
	 * list categories
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listCategories(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<NewsCategory> models = newsService.findCats(true, start, limit);
		log.debug("##" + models.size());
		Integer count = newsService.getCatsCnt(true);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("cat.list");
	}

	/**
	 * list comments
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List models = newsService.findComments(null, true, start, limit);
		Integer count = newsService.getCommentsCnt(null);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("comment.list");
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
	public ActionForward listNewses(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);
		List<NewsEntry> models = newsService.findNewses(true, null, null,
				start, limit);
		Integer count = newsService.getNewsCnt(true, null, null);

		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("news.list");
	}

	/**
	 * list user posts
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listPosts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		String showAll = request.getParameter("all");
		List<NewsPost> models = newsService.findPosts(!"true"
				.equalsIgnoreCase(showAll), start, limit);
		Integer count = newsService.getPostsCnt(!"true"
				.equalsIgnoreCase(showAll));
		request.setAttribute("models", models);
		request.setAttribute("count", count);

		// 获取新闻分类列表
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);

		return mapping.findForward("post.list");
	}

	/**
	 * news form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newsForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nid = request.getParameter("nid");
		String pid = request.getParameter("pid");
		String author = request.getParameter("author");
		if (StringUtils.isNotEmpty(nid)) {
			NewsEntry model = newsService.getNews(new Integer(nid));
			request.setAttribute("model", model);
			author = model.getAuthor();
			request.setAttribute("author", author);
		}

		if (StringUtils.isNotEmpty(pid)) {
			NewsPost post = newsService.getPost(new Integer(pid));
			post.setDone(1);
			newsService.savePost(post);

			NewsEntry model = new NewsEntry();
			model.setTitle(post.getTitle());
			model.setSubtitle(post.getContent());
			model.setContent(post.getContent());
			model.setAuthor(author);
			model.setUpdateTime(post.getUpdateTime());
			request.setAttribute("model", model);
			author = post.getAuthor();
			request.setAttribute("author", author);
			request.setAttribute("selectedCat", post.getCat());
		}
		if (StringUtils.isEmpty(author)) {
			author = (String) request.getSession().getAttribute(
					BusinessConstants.ADMIN_NAME);
			request.setAttribute("author", author);
		}
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);
		return mapping.findForward("news.form");
	}

	/**
	 * 用户投递的状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	public ActionForward postState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = request.getParameter("pid");
		NewsPost model = newsService.getPost(new Integer(pid));
		String message = "DONE";
		if (model.getDone() == 1) {
			model.setDone(0);
			message = "NEW";
		} else {
			model.setDone(1);
		}
		newsService.savePost(model);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, message);
		return mapping.findForward("message");

	}

	/**
	 * delete category
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveCat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		NewsCategory model = null;
		if (StringUtils.isEmpty(cid)) {
			model = new NewsCategory();
			model.setCreateTime(DateUtils.getNow());
			model.setUpdateTime(DateUtils.getNow());
		} else {
			model = newsService.getCategory(new Integer(cid));
			model.setUpdateTime(DateUtils.getNow());
		}
		PropertyUtil.populateBean(model, request);
		newsService.saveCategory(model);
		return new ActionForward("newsManage.shtml?method=listCategories", true);
	}

	/**
	 * save news
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nid = request.getParameter("nid");
		NewsEntry model = null;
		String cid = request.getParameter("cid");
		NewsCategory cat = newsService.getCategory(new Integer(cid));
		if (StringUtils.isEmpty(nid)) {
			model = new NewsEntry();
			model.setCreateTime(DateUtils.getNow());
			model.setHitGood(0);
			model.setHitBad(0);
			model.setUpdateTime(DateUtils.getNow());
			model.setCommentFlg(1);
			model.setHits(0);
			model.setNewsCategory(cat);
			model.setState(NewsStateType.OK.getValue());
		} else {
			model = newsService.getNews(new Integer(nid));
			model.setUpdateTime(DateUtils.getNow());
			model.setNewsCategory(cat);
		}
		model.setManager((String) request.getSession().getAttribute(
				BusinessConstants.ADMIN_NAME));
		PropertyUtil.populateBean(model, request);
		if (StringUtils.isEmpty(model.getSubtitle())) {
			model.setSubtitle(StringUtils.getStrWithoutHtmlTag(StringUtils
					.getShorterStr(model.getContent(), 300)));
		}
		if (StringUtils.isEmpty(model.getUpdateTime())) {
			model.setUpdateTime(DateUtils.getNow());
		}
		newsService.saveNews(model);
		newsService.flushNewsEntryCache();
		return new ActionForward("newsManage.shtml?method=listNewses", true);
	}

	/**
	 * change the state of a news model
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward state(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nid = request.getParameter("nid");
		NewsEntry model = newsService.getNews(new Integer(nid));
		String message = "OK";
		if (NewsStateType.OK.getValue().equals(model.getState())) {
			model.setState(NewsStateType.WAITING.getValue());
			message = "WAITING";
		} else {
			model.setState(NewsStateType.OK.getValue());
		}
		newsService.saveNews(model);
		request.setAttribute("message", message);
		return mapping.findForward("message");

	}
}
