package org.net9.blog.web.servlet;

import java.io.File;
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
import org.net9.blog.service.EntryService;
import org.net9.blog.web.BlogHelper;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.DateUtils;
import org.net9.common.util.FileUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.BlogEntryStateType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.domain.model.blog.BlogComment;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.User;
import org.net9.domain.model.view.BlogEntryMonthly;

import com.google.inject.Inject;

/**
 * blog action
 * 
 * @author gladstone
 * @since 2008-9-23
 */
@SuppressWarnings("serial")
@WebModule("bg")
public class BlogServlet extends BusinessCommonServlet {

	private static final Log log = LogFactory.getLog(BlogServlet.class);
	private static final String DEFAULT_PINGS_DEF = "blog_ping_default.txt";
	@Inject
	private BlogHelper blogHelper;

	/**
	 * 博客设置表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	@ReturnUrl(rederect = false, url = "/blog/blogForm.jsp")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("bid");
		String username = UserHelper.getuserFromCookie(request);
		Blog model = null;
		if (StringUtils.isNotEmpty(idStr)) {
			model = blogService.getBlog(new Integer(idStr));
			request.setAttribute("model", model);
		}
		if (StringUtils.isNotEmpty(username)) {
			model = blogService.getBlogByUser(username);
		}
		if (model == null) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
		}
		this.blogHelper.prepareCommends(request);
		request.setAttribute("model", model);
	}

	/**
	 * 保存博客设置
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	@ReturnUrl(rederect = true, url = "bg.action?method=form")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String image = null;
		try {
			image = (String) getMultiFile(request, "image").get(FILE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String username = UserHelper.getuserFromCookie(request);
		String name = getParameter("name");
		String listType = getParameter("listType");
		String description = getParameter("description");
		String backgroundMusic = getParameter("backgroundMusic");
		String theme = getParameter("theme");
		String idStr = getParameter("bid");
		String keyword = getParameter("keyword");
		String htmlBlock = getParameter("htmlBlock");
		String showGallery = getParameter("showGallery");
		if (StringUtils.isNotEmpty(image)) {
			// cut the image
			try {
				// create the images: mini default
				ImageUtils.getDefaultImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), false);
				ImageUtils.getMiniImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), false);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), 16);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), 80);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), 32);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), 64);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), 200);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + image), 48);

			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}

		Blog model = null;
		String pingTargets = getParameter("pingTargets");

		// #894 一个博客注册时的问题
		if (StringUtils.isEmpty(theme)) {
			theme = BusinessConstants.THEME_DEFAULT;
		}

		if (StringUtils.isEmpty(idStr)) {
			log.debug("new blog");
			model = new Blog();
			model.setAuthor(username);
			model.setCreateTime(DateUtils.getNow());
			model.setDescription(description);
			model.setName(name);
			model.setTheme(theme);// #894 一个博客注册时的问题
			// model.setTheme(BusinessConstants.THEME_DEFAULT);
			model.setUpdateTime(DateUtils.getNow());
			model.setBackgroundMusic(backgroundMusic);
			model.setHits(0);
			model.setKeyword(keyword);
			model.setHtmlBlock(htmlBlock);
			if (StringUtils.isNotEmpty(image)) {
				model.setImage(image);
			}
			model.setUrl("/blog/publish/"
					+ StringUtils.getURLStr(model.getAuthor()));

			if (StringUtils.isEmpty(pingTargets)) {
				try {
					pingTargets = FileUtils.readFileContent(this.getClass()
							.getClassLoader().getResource(DEFAULT_PINGS_DEF)
							.getFile());
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			model.setPingTargets(pingTargets);
			model.setShowGallery(0);

			blogService.newBlog(model);
			model = blogService.getBlogByUser(username);
			userService.trigeEvent(this.userService.getUser(username), model
					.getId()
					+ "", UserEventType.BLOG_NEW);

		} else {
			log.debug("update blog with:" + idStr);
			model = blogService.getBlog(new Integer(idStr));

			// validate user
			UserHelper.authUserSimply(request, model);

			model.setDescription(description);
			model.setName(name);
			model.setTheme(theme);
			model.setBackgroundMusic(backgroundMusic);
			model.setPingTargets(pingTargets);
			model.setKeyword(keyword);
			model.setHtmlBlock(htmlBlock);
			if (StringUtils.isNotEmpty(listType) && listType.equals("1")) {
				model.setListType(1);
			} else {
				model.setListType(0);
			}
			if (StringUtils.isNotEmpty(showGallery) && showGallery.equals("1")) {
				model.setShowGallery(1);
			} else {
				model.setShowGallery(0);
			}
			// TODO: fix this
			model.setUrl("/blog/publish/"
					+ StringUtils.getURLStr(model.getAuthor()));
			log.debug("update blog url:" + model.getUrl());
			model.setUpdateTime(DateUtils.getNow());
			if (StringUtils.isNotEmpty(image)) {
				model.setImage(image);
			}
			blogService.updateBlog(model);
			userService.trigeEvent(this.userService.getUser(username), model
					.getId()
					+ "", UserEventType.BLOG_EDIT);
		}

	}

	/**
	 * 每月日志数
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void summary(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");

		Blog blog = blogService.getBlog(new Integer(bid));
		if (blog == null) {
			this.sendError(request, response, "blog.empty");
			return;
		}

		blogHelper.blogInfo(request, blog);

		Integer blogId = blog.getId();
		List<BlogEntryMonthly> summaryList = entryService.findMonthlyEntries(
				null, blogId, 0, BusinessConstants.MAX_INT);
		request.setAttribute("summaryList", summaryList);

		if (StringUtils.isEmpty(blog.getTheme())
				|| "none".equals(blog.getTheme())) {
			request.getRequestDispatcher("/blog/viewSummaryWithoutTheme.jsp")
					.forward(request, response);
		} else {
			log.debug(blog.getTheme());
			request.getRequestDispatcher("/blog/viewSummary.jsp").forward(
					request, response);
		}
	}

	/**
	 * 查看博客
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String username = request.getParameter("username");
		String usernick = request.getParameter("nick");
		if (StringUtils.isNotEmpty(username)) {
			username = StringUtils.base64Decode(username);
		}
		if (StringUtils.isNotEmpty(usernick)) {
			log.debug("Got nick: " + usernick);
			User u = this.userService.getUserByNick(usernick);
			username = u.getUserName();
		}

		String bid = request.getParameter("bid");
		if (StringUtils.isEmpty(username)) {
			username = UserHelper.getuserFromCookie(request);
		}
		Blog blog = null;
		if (StringUtils.isEmpty(bid)) {
			blog = blogService.getBlogByUser(username);
			if (blog == null) {
				this.sendError(request, response, "blog.empty");
				return;
			}
			// request
			// .getRequestDispatcher(
			// "/" + UrlConstants.BLOG + blog.getId()).forward(
			// request, response);
			// response.sendRedirect(UrlConstants.BLOG + blog.getId());
			// return;
		} else {
			if (!StringUtils.isNum(bid)) {
				throw new ModelNotFoundException();
			}
			blog = blogService.getBlog(new Integer(bid));
		}

		if (blog == null) {
			// this.sendError(request, response, "blog.empty");
			// return;
			throw new ModelNotFoundException();
		}

		blogHelper.blogInfo(request, blog);

		String categoryIdStr = request.getParameter("cid");
		String monthStr = request.getParameter("month");

		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		List<BlogEntry> models = null;
		Integer cnt = 0;
		if (StringUtils.isNotEmpty(categoryIdStr)) {
			Integer categoryId = new Integer(categoryIdStr);
			BlogCategory cat = blogService.getCategory(categoryId);
			request.setAttribute("categoryModel", cat);
			models = entryService.findEntries(blog.getId(), categoryId,
					EntryService.EntryType.NORMAL.getType(), start, limit);
			cnt = entryService.getEntriesCnt(blog.getId(), categoryId,
					EntryService.EntryType.NORMAL.getType());
		} else if (StringUtils.isNotEmpty(monthStr)) {
			models = entryService.findEntriesByMonth(blog.getId(), monthStr,
					start, limit);
			cnt = entryService.getEntriesCntByMonth(blog.getId(), monthStr);
			request.setAttribute("currentMonth", monthStr);
		} else {
			models = entryService.findEntries(blog.getId(), null,
					EntryService.EntryType.NORMAL.getType(), start, limit);
			cnt = entryService.getEntriesCnt(blog.getId(), null,
					EntryService.EntryType.NORMAL.getType());
		}

		String currUsername = UserHelper.getuserFromCookie(request);
		String ownerUsername = blog.getAuthor();
		boolean isSelf = StringUtils.isNotEmpty(currUsername)
				&& (currUsername).equals(ownerUsername);
		boolean isFriend = isSelf
				|| (StringUtils.isNotEmpty(currUsername) && (userService
						.isFriend(currUsername, ownerUsername)));

		List<Map<Object, Object>> entryMapList = new ArrayList<Map<Object, Object>>();
		for (BlogEntry e : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("author", userService.getUser(null, e.getAuthor()));
			m.put("reCnt", blogCommentService.getCommentsCnt(null, e.getId()));
			// 列出16个关注此日志的用户
			List<BlogComment> eComments = blogCommentService.findComments(blog
					.getId(), e.getId(), 0, 16);
			List<User> commentUserList = new ArrayList<User>();
			for (BlogComment c : eComments) {
				commentUserList.add(userService.getUser(null, c.getAuthor()));
			}
			m.put("commentUserList", commentUserList);

			// #696增加可见度设置
			int state = e.getState();
			// 如果用户设置了不公开，需要登录才能看
			if (StringUtils.isEmpty(currUsername)) {
				log.debug("Not logined, check user setting");
				if (state >= BlogEntryStateType.USER.getValue()) {
					String msg = I18nMsgUtils.getInstance().getMessage(
							"blog.privacy.forbidden");
					m.put("errorMessage", msg);
				}
			}

			if (((!isSelf) && (state == BlogEntryStateType.FORBIDDEN.getValue()))
					|| ((!isFriend) && (state == BlogEntryStateType.FRIEND
							.getValue()))) {
				String msg = I18nMsgUtils.getInstance().getMessage(
						"blog.privacy.forbidden");
				m.put("errorMessage", msg);
			}

			entryMapList.add(m);
		}
		request.setAttribute("entryMapList", entryMapList);

		request.setAttribute("count", cnt);
		request.setAttribute("entries", models);

		if (StringUtils.isEmpty(blog.getTheme())
				|| "none".equals(blog.getTheme())) {
			request.getRequestDispatcher("/blog/viewBlogWithoutTheme.jsp")
					.forward(request, response);
		} else {
			log.debug(blog.getTheme());
			request.getRequestDispatcher("/blog/viewBlog.jsp").forward(request,
					response);
		}
	}
}