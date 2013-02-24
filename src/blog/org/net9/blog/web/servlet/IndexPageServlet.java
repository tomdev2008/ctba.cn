package org.net9.blog.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.blog.service.EntryService;
import org.net9.blog.web.BlogHelper;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.ListWrapper;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogComment;
import org.net9.domain.model.blog.BlogEntry;

import com.google.inject.Inject;

/**
 * 博客系统首页
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@WebModule("bhome")
@ReturnUrl(rederect = false, url = "/blog/indexPage.jsp")
public class IndexPageServlet extends BusinessCommonServlet {

	@Inject
	private BlogHelper blogHelper;

	@Override
	@SuppressWarnings("unchecked")
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 最新博客 10个
		List<Blog> newBlogs = blogService.findBlogs(0, 10);
		List<Map<Object, Object>> newBlogsList = ListWrapper.getInstance()
				.formatBlogList(newBlogs);
		request.setAttribute("newBlogs", newBlogsList);
		// 活跃博客(文章最多) 10个
		List<Blog> activeBlogs = blogService.findHotBlogs(0, 10);
		List<Map<Object, Object>> activeBlogsMapList = ListWrapper
				.getInstance().formatBlogList(activeBlogs);

		request.setAttribute("activeBlogs", activeBlogsMapList);

		// 热门博客(大图) 16个
		List<Blog> hotBlogs = blogService.findHotBlogsByHits(0, 16);
		List<Map<Object, Object>> hotBlogsMapList = ListWrapper.getInstance()
				.formatBlogList(hotBlogs);
		request.setAttribute("hotBlogs", hotBlogsMapList);

		// 最新文章 15个
		List<BlogEntry> newEntries = entryService.findNewEntries(null,
				EntryService.EntryType.NORMAL.getType(), 0, 15);
		request.setAttribute("newEntries", newEntries);
		List<Map<Object, Object>> newEntriesMapList = ListWrapper.getInstance()
				.formatBlogEntryList(newEntries);
		request.setAttribute("newEntriesMapList", newEntriesMapList);

		// 点击最多 15个
		List<BlogEntry> hotEntries = entryService.findHotEntries(null,
				EntryService.EntryType.NORMAL.getType(), 0, 15);
		request.setAttribute("hotEntries", hotEntries);
		List<Map<Object, Object>> hotEntriesMapList = ListWrapper.getInstance()
				.formatBlogEntryList(hotEntries);
		request.setAttribute("hotEntriesMapList", hotEntriesMapList);

		// 评论最多 15个
		List<BlogEntry> mostCommentedEntries = entryService
				.findMostCommentedEntries(EntryService.EntryType.NORMAL
						.getType(), 0, 15);
		request.setAttribute("mostCommentedEntriesMapList", ListWrapper
				.getInstance().formatBlogEntryList(mostCommentedEntries));

		// 最新的评论列表 10个
		List<BlogComment> comments = blogCommentService.findComments(null, 0, 10);
		request.setAttribute("comments", comments);

		// 博客数目
		Integer blogCnt = blogService.getBlogsCnt();
		request.setAttribute("blogCnt", blogCnt);

		Integer entryCnt = entryService.getEntriesCnt(null, null, null);
		this.blogHelper.prepareCommends(request);
		request.setAttribute("entryCnt", entryCnt);

	}

}