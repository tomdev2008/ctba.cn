package org.net9.blog.web.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.blog.service.EntryService;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogComment;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.blog.BlogLink;

/**
 * manage action for blog
 * 
 * @author gladstone
 * @since Nov 14, 2008
 */
public class BlogManageAction extends BusinessDispatchAction {

	/**
	 * delete blog
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteBlog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bid = request.getParameter("bid");
		Blog model = blogService.getBlog(new Integer(bid));
		if (model != null) {
			blogService.delBlog(model);
		}
		return new ActionForward("blogManage.shtml?method=listBlogs", true);
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
	public ActionForward deleteCat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		BlogCategory model = blogService.getCategory(new Integer(cid));
		blogService.delCategory(model);
		return new ActionForward("blogManage.shtml?method=listBlogCats", true);
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
		BlogComment model = commentService.getComment(new Integer(cid));
		if (model != null) {
			commentService.delComment(model);
		}
		return new ActionForward("blogManage.shtml?method=listComments", true);
	}

	/**
	 * delete an entry
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String eid = request.getParameter("eid");
		BlogEntry model = entryService.getEntry(new Integer(eid));
		if (model != null) {
			entryService.delEntry(model);
		}
		return new ActionForward("blogManage.shtml?method=listEntries", true);
	}

	/**
	 * delete blog link
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lid = request.getParameter("lid");
		BlogLink model = linkService.getLink(new Integer(lid));
		linkService.delLink(model);
		return new ActionForward("blogManage.shtml?method=listLinks", true);
	}

	/**
	 * delete vest
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String aid = request.getParameter("aid");
		vestService.deleteAddress(new Integer(aid));
		return new ActionForward("blogManage.shtml?method=listVests", true);
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
	@SuppressWarnings("unchecked")
	public ActionForward listBlogCats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List models = blogService.findCategories(null, start, limit);
		Integer count = blogService.getCategoriesCnt(null);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("cat.list");
	}

	/**
	 * list blogs
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listBlogs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		// #254
		int limit = WebConstants.PAGE_SIZE_30;
		List<Blog> models = blogService.findBlogs(start, limit);
		Integer count = blogService.getBlogsCnt();
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("blog.list");
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
		int limit = WebConstants.PAGE_SIZE_15;
		List models = commentService.findComments(null, start, limit);
		Integer count = commentService.getCommentsCnt(null, null);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("comment.list");
	}

	/**
	 * blog entries
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listEntries(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List models = entryService.findEntries(0, 0,
				EntryService.EntryType.NORMAL.getType(), start, limit);
		Integer count = entryService.getEntriesCnt(0, 0,
				EntryService.EntryType.NORMAL.getType());
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("entry.list");
	}

	/**
	 * save a share
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listLinks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List models = linkService.listLinks(null, start, limit);
		Integer count = linkService.getLinksCnt(null);
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("link.list");
	}

	/**
	 * list vests
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listVests(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List models = vestService.findAddress(start, limit);
		Integer count = vestService.getAddressCnt();
		request.setAttribute("models", models);
		request.setAttribute("count", count);
		return mapping.findForward("vest.list");
	}

	/**
	 * #840 (系统管理员的权限)
	 * 
	 * 所有日志的正文管理权限（后台管理，因为和个人博客的ping有关）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String entryId = request.getParameter("id");
		String categoryIdStr = request.getParameter("categoryId");
		String body = request.getParameter("body");
		String title = request.getParameter("title");
		String state = request.getParameter("state");
		Integer categoryId = 0;
		if (StringUtils.isNotEmpty(categoryIdStr)) {
			categoryId = new Integer(categoryIdStr);
		}
		BlogEntry model = null;
		// update
		model = entryService.getEntry(new Integer(entryId));
		model.setBody(body);
		model.setState(Integer.valueOf(state));
		model.setCategoryId(categoryId);
		model.setTitle(title);
		entryService.updateEntry(model);
		// 即时能看到修改效果
		entryService.flushEntryCache();
		return new ActionForward("blogManage.shtml?method=listEntries", true);
	}

	/**
	 * #840 (系统管理员的权限)
	 * 
	 * 所有日志的正文管理权限（后台管理，因为和个人博客的ping有关）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward entryForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idStr = request.getParameter("eid");
		BlogEntry model = entryService.getEntry(new Integer(idStr));
		request.setAttribute("model", model);
		List<BlogCategory> cats = blogService.findCategories(model
				.getBlogBlog().getId(), 0, BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);
		return mapping.findForward("entry.form");
	}
}
