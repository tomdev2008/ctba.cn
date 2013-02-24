package org.net9.blog.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.lang.jpath.encoding.HtmlEncoder;
import org.net9.blog.ping.WeblogUpdatePinger;
import org.net9.blog.service.EntryService;
import org.net9.blog.web.BlogHelper;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.hit.HitStrategy;
import org.net9.core.types.BlogEntryStateType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.search.lucene.search.ref.LuceneBlogEntryReferenceSearcher;

import com.google.inject.Inject;

/**
 * Entry Action
 * 
 * @author gladstone
 * @since 2008-9-8
 */
@SuppressWarnings("serial")
@WebModule("be")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class EntryServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(EntryServlet.class);
	@Inject
	private LuceneBlogEntryReferenceSearcher referenceSearcher;
	@Inject
	private BlogHelper blogHelper;

	/**
	 * 删除日志
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@SuppressWarnings("deprecation")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String entryId = request.getParameter("eid");
		String paggerOffset = request.getParameter("p");
		BlogEntry model = entryService.getEntry(new Integer(entryId));

		// validate user
		UserHelper.authUserSimply(request, model);

		int type = model.getType();
		entryService.delEntry(model);
		Blog blog = model.getBlogBlog();
		blog.setEntryCnt(blog.getEntryCnt() - 1);
		Integer bid = model.getBlogBlog().getId();
		if (StringUtils.isNotEmpty(paggerOffset)) {
			Integer cnt = entryService.getEntriesCnt(bid, null,
					EntryService.EntryType.NORMAL.getType());
			int offset = Integer.parseInt(paggerOffset);
			if (offset >= cnt) {
				offset -= WebConstants.PAGE_SIZE_15;
			}

			// if (type == EntryService.EntryType.MINI.getType()) {
			// response.sendRedirect("be.action?method=miniList&"
			// + Constant.PAGER_OFFSET + "=" + offset);
			// } else {
			response.sendRedirect("be.action?method=list&"
					+ BusinessConstants.PAGER_OFFSET + "=" + offset);
			// }
		} else {
			if (type == EntryService.EntryType.DRAFT.getType()) {
				response.sendRedirect("be.action?method=drafts");
			} else {
				response.sendRedirect("be.action?method=list");
			}
		}

	}

	/**
	 * 日志表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("eid");
		String blogId = request.getParameter("bid");
		Blog blog = null;
		if (StringUtils.isNotEmpty(idStr)) {
			BlogEntry model = entryService.getEntry(new Integer(idStr));
			model.setBody(HtmlEncoder.encode(model.getBody()));
			request.setAttribute("model", model);
		}
		if (StringUtils.isNotEmpty(blogId)) {
			blog = blogService.getBlog(new Integer(blogId));
		} else {
			blog = blogService.getBlogByUser(UserHelper
					.getuserFromCookie(request));
		}
		if (blog == null) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
			request.setAttribute("model", blog);
			request.getRequestDispatcher("/blog/blogForm.jsp").forward(request,
					response);
			return;
		}
		List<BlogCategory> cats = blogService.findCategories(blog.getId(), 0,
				BusinessConstants.MAX_INT);
		if (cats == null || cats.size() < 1) {
			String msg = I18nMsgUtils.getInstance().getMessage("blog.noCat");
			request.setAttribute(BusinessConstants.MSG_KEY, msg);
			request.getRequestDispatcher("/blog/categoryList.jsp").forward(
					request, response);
			return;
		}
		request.setAttribute("cats", cats);
		request.setAttribute("blogModel", blog);
		this.blogHelper.prepareCommends(request);
		request.getRequestDispatcher("/blog/entryForm.jsp").forward(request,
				response);
	}

	/**
	 * 转换ping列表
	 * 
	 * @param blog
	 * @return
	 */
	private String[] getPingTargetList(Blog blog) {
		if (blog == null || StringUtils.isEmpty(blog.getPingTargets())) {
			return new String[] {};
		}
		String[] array = blog.getPingTargets().split("\r\n");
		return array;
	}

	/**
	 * 日志列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryIdStr = request.getParameter("cid");
		String username = UserHelper.getuserFromCookie(request);
		Blog blog = blogService.getBlogByUser(username);
		if (blog == null) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
			request.setAttribute("model", blog);
			request.getRequestDispatcher("/blog/blogForm.jsp").forward(request,
					response);
			return;
		}
		Integer blogId = blog.getId();
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<BlogEntry> models;
		Integer cnt = 0;
		if (StringUtils.isNotEmpty(categoryIdStr)) {
			Integer categoryId = new Integer(categoryIdStr);
			// BlogCategory cat = blogService.getCategory(categoryId);
			// if (cat.getName().equals(
			// I18nMsgUtils.getInstance().getMessage("category.mini"))) {
			// models = entryService.findEntries(blogId, categoryId,
			// EntryService.EntryType.MINI.getType(), start, limit);
			// cnt = entryService.getEntriesCnt(blogId, categoryId,
			// EntryService.EntryType.MINI.getType());
			// } else {
			models = entryService.findEntries(blogId, categoryId,
					EntryService.EntryType.NORMAL.getType(), start, limit);
			cnt = entryService.getEntriesCnt(blogId, categoryId,
					EntryService.EntryType.NORMAL.getType());
			// }
		} else {
			models = entryService.findEntries(blogId, null,
					EntryService.EntryType.NORMAL.getType(), start, limit);
			cnt = entryService.getEntriesCnt(blogId, null,
					EntryService.EntryType.NORMAL.getType());
		}

		List<Map<Object, Object>> entryMapList = new ArrayList<Map<Object, Object>>();
		for (BlogEntry e : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("reCnt", blogCommentService.getCommentsCnt(null, e.getId()));
			m.put("category", this.blogService.getCategory(e.getCategoryId()));
			entryMapList.add(m);
		}

		request.setAttribute("entries", models);
		request.setAttribute("entryMaps", entryMapList);
		request.setAttribute("count", cnt);
		request.setAttribute("blogModel", blog);
		request.setAttribute("blogAuthor", userService.getUser(null, blog
				.getAuthor()));
		request.setAttribute("logined", StringUtils.isNotEmpty(username));
		log.debug(blog.getAuthor());
		this.blogHelper.prepareCommends(request);
		request.getRequestDispatcher("/blog/entryList.jsp").forward(request,
				response);
	}

	/**
	 * #648 博客增加草稿箱
	 * 
	 * 草稿列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void drafts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryIdStr = request.getParameter("cid");
		String username = UserHelper.getuserFromCookie(request);
		Blog blog = blogService.getBlogByUser(username);
		if (blog == null) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
			request.setAttribute("model", blog);
			request.getRequestDispatcher("/blog/blogForm.jsp").forward(request,
					response);
			return;
		}
		Integer blogId = blog.getId();
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<BlogEntry> models;
		Integer cnt = 0;
		if (StringUtils.isNotEmpty(categoryIdStr)) {
			Integer categoryId = new Integer(categoryIdStr);
			models = entryService.findEntries(blogId, categoryId,
					EntryService.EntryType.DRAFT.getType(), start, limit);
			cnt = entryService.getEntriesCnt(blogId, categoryId,
					EntryService.EntryType.DRAFT.getType());
		} else {
			models = entryService.findEntries(blogId, null,
					EntryService.EntryType.DRAFT.getType(), start, limit);
			cnt = entryService.getEntriesCnt(blogId, null,
					EntryService.EntryType.DRAFT.getType());
		}
		request.setAttribute("entries", models);
		request.setAttribute("count", cnt);
		request.setAttribute("blogModel", blog);
		log.debug(blog.getAuthor());
		this.blogHelper.prepareCommends(request);
		request.getRequestDispatcher("/blog/draftList.jsp").forward(request,
				response);
	}

	/**
	 * 保存日志
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	@ReturnUrl(rederect = true, url = "be.action?method=list")
	public boolean save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String username = UserHelper.getuserFromCookie(request);
		String entryId = request.getParameter("id");
		String categoryIdStr = request.getParameter("categoryId");
		String commentsEnabledStr = request.getParameter("commentsEnabled");
		String blogId = request.getParameter("blogId");
		String body = request.getParameter("body");
		String subtitle = request.getParameter("subtitle");
		String publishDate = request.getParameter("publishDate");
		String tags = request.getParameter("tags");
		String title = request.getParameter("title");
		String state = request.getParameter("state");
		String type = request.getParameter("type");
		boolean isDraft = StringUtils.isNotEmpty(type)
				&& EntryService.EntryType.DRAFT.getType() == Integer.valueOf(
						type).intValue();
		log.info("type: " + type);
		log.info("body: " + body);
		Blog blog = blogService.getBlog(new Integer(blogId));
		Integer commentsEnabled = 0;
		Integer categoryId = 0;
		if (StringUtils.isNotEmpty(categoryIdStr)) {
			categoryId = new Integer(categoryIdStr);
		}
		if (StringUtils.isNotEmpty(commentsEnabledStr)) {
			commentsEnabled = 1;
		}
		BlogEntry model = null;
		if (StringUtils.isEmpty(entryId)) {
			// insert
			model = new BlogEntry();
			model.setAuthor(username);
			model.setBlogBlog(blog);
			model.setBody(body);
			model.setCategoryId(categoryId);
			model.setCommentsEnabled(commentsEnabled.shortValue());
			model.setDate(DateUtils.getNow());
			model.setPublishDate(DateUtils.getNow());
			model.setHits(0);
			model.setSubtitle(subtitle);
			model.setTags(tags);
			model.setTitle(title);
			model.setState(Integer.valueOf(state));
			model.setPermalink(model.getCategoryId() + "/"
					+ StringUtils.createFileName("") + "_"
					+ java.util.UUID.randomUUID().toString() + ".html");
			if (isDraft) {
				model.setType(EntryService.EntryType.DRAFT.getType());
			} else {
				model.setType(EntryService.EntryType.NORMAL.getType());
			}

			entryService.newEntry(model);

			if (!isDraft) {
				List<BlogEntry> entries = entryService.findEntries(
						blog.getId(), null, EntryService.EntryType.NORMAL
								.getType(), 0, 1);
				if (entries.size() > 0) {
					userService.trigeEvent(this.userService.getUser(username),
							entries.get(0).getId() + "",
							UserEventType.ENTRY_NEW);
					if (blog.getEntryCnt() == null) {
						blog.setEntryCnt(1);
					} else {
						blog.setEntryCnt(blog.getEntryCnt() + 1);
					}
					blogService.updateBlog(blog);
					sendPing(blog);
				}
			}
		} else {
			// update
			model = entryService.getEntry(new Integer(entryId));

			// validate user
			UserHelper.authUserSimply(request, model);

			model.setBody(body);
			model.setState(Integer.valueOf(state));
			model.setCategoryId(categoryId);
			if (StringUtils.isNotEmpty(publishDate)) {
				model.setPublishDate(publishDate);
			} else {
				model.setPublishDate(DateUtils.getNow());
			}
			model.setCommentsEnabled(commentsEnabled.shortValue());
			// #265 博客的修改日期
			// model.setDate(DateUtils.getNow());
			if (isDraft) {
				model.setType(EntryService.EntryType.DRAFT.getType());
				model.setSubtitle(subtitle);
				model.setTags(tags);
				model.setTitle(title);
				entryService.updateEntry(model);
			} else {

				model.setSubtitle(subtitle);
				model.setTags(tags);
				model.setTitle(title);

				// 如果是草稿转来的，删掉重建（更新id）
				if (EntryService.EntryType.DRAFT.getType() == model.getType()
						.intValue()) {
					BlogEntry newModel = new BlogEntry();
					try {
						PropertyUtil.copyProperties(newModel, model);
					} catch (Exception ex) {
						log.error(ex.getMessage());
					}
					newModel.setId(null);
					newModel.setDate(StringUtils.getTimeStrByNow());
					newModel.setType(EntryService.EntryType.NORMAL.getType());
					newModel.setPublishDate(DateUtils.getNow());
					entryService.newEntry(newModel);
					entryService.delEntry(model);

					List<BlogEntry> entries = entryService.findEntries(blog
							.getId(), null, EntryService.EntryType.NORMAL
							.getType(), 0, 1);
					if (entries.size() > 0) {
						userService.trigeEvent(this.userService
								.getUser(username),
								entries.get(0).getId() + "",
								UserEventType.ENTRY_NEW);
						sendPing(blog);
					}
				} else {
					model.setType(EntryService.EntryType.NORMAL.getType());
					entryService.updateEntry(model);
					userService.trigeEvent(this.userService.getUser(username),
							model.getId() + "", UserEventType.ENTRY_EDIT);
				}
			}
		}
		if (StringUtils.isNotEmpty(type)
				&& EntryService.EntryType.DRAFT.getType() == Integer
						.valueOf(type)) {
			response.sendRedirect("be.action?method=drafts");
			return true;
		}
		return false;
	}

	/**
	 * send ping
	 * 
	 * @param blog
	 */
	private void sendPing(Blog blog) {
		List<BlogEntry> entries = entryService.findEntries(blog.getId(), null,
				EntryService.EntryType.NORMAL.getType(), 0, 1);
		if (entries.size() > 0) {
			for (String pingTarget : getPingTargetList(blog)) {
				try {
					log.info("send ping to: " + pingTarget);
					WeblogUpdatePinger.sendPing(pingTarget.trim(), blog
							.getName(), this.commonService.getConfig()
							.getDomainRoot()
							+ "/blog/" + blog.getId(), this.commonService
							.getConfig().getDomainRoot()
							+ "/entry/" + entries.get(0).getId(),
							this.commonService.getConfig().getDomainRoot()
									+ "/rss.shtml?type=blog&bgid="
									+ blog.getId());
				} catch (Exception e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	@Inject
	private HitStrategy hitStrategy;

	/**
	 * 查看日志
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@SuppressWarnings("unchecked")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String idStr = request.getParameter("eid");
		Blog blog = null;
		BlogEntry model = entryService.getEntry(new Integer(idStr));

		// #735 (对于空贴或者其他导致nullpointer例外的情况的处理)
		if (model == null) {
			throw new ModelNotFoundException();
		}

		hitStrategy.hitBlogEntry(model);

		blog = blogService.getBlog(model.getBlogBlog().getId());

		// #696增加可见度设置
		int state = model.getState();
		String ownerUsername = model.getAuthor();
		String currUsername = UserHelper.getuserFromCookie(request);

		boolean isSelf = StringUtils.isNotEmpty(currUsername)
				&& (currUsername).equals(ownerUsername);

		boolean isFriend = isSelf
				|| (StringUtils.isNotEmpty(currUsername) && (userService
						.isFriend(currUsername, ownerUsername)));

		// 如果用户设置了不公开，需要登录才能看

		if (StringUtils.isEmpty(currUsername)) {
			log.debug("Not logined, check user setting");
			if (state >= BlogEntryStateType.USER.getValue()) {
				this.sendError(request, response, "error.noOption");
				return;
			}
		}

		if (((!isSelf) && (state == BlogEntryStateType.FORBIDDEN.getValue()))
				|| ((!isFriend) && (state == BlogEntryStateType.FRIEND
						.getValue()))) {
			this.sendError(request, response, "blog.privacy.forbidden");
			return;
		}

		// 如果该日志还是草稿
		if (model.getType().intValue() == EntryService.EntryType.DRAFT
				.getType()) {
			request.setAttribute(BusinessConstants.ERROR_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.privacy.draft"));
			request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(
					request, response);
			return;
		}
		blogHelper.blogInfo(request, blog);

		BlogEntry prevModel = entryService.getPrevEntry(model);
		BlogEntry nextModel = entryService.getNextEntry(model);
		log.debug("nextModel:" + nextModel);
		log.debug("prevModel:" + prevModel);

		request.setAttribute("prevModel", prevModel);
		request.setAttribute("nextModel", nextModel);
		request.setAttribute("entryModel", model);
		request.setAttribute("blogModel", blog);
		request.setAttribute("author", userService.getUser(null, model
				.getAuthor()));
		request.setAttribute("commentCnt", blogCommentService.getCommentsCnt(
				null, model.getId()));

		try {
			List<Map<String, String>> refEntries = referenceSearcher
					.searchByKey(model.getTitle(), UrlConstants.BLOG_ENRTY
							+ model.getId());
			request.setAttribute("refEntries", refEntries);
		} catch (Exception e) {
			log.error(e.getMessage());
		} catch (Error e) {
			log.error(e.getMessage());
		}

		if (StringUtils.isEmpty(blog.getTheme())
				|| "none".equals(blog.getTheme())) {
			request.getRequestDispatcher("/blog/viewEntryWithoutTheme.jsp")
					.forward(request, response);
		} else {
			request.getRequestDispatcher("/blog/viewEntry.jsp").forward(
					request, response);
		}
	}
}