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
import org.net9.blog.web.BlogHelper;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogComment;
import org.net9.domain.model.blog.BlogEntry;

import com.google.inject.Inject;

@SuppressWarnings("serial")
@WebModule("com")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class CommentServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(CommentServlet.class);

	@Inject
	private BlogHelper blogHelper;

	/**
	 * 删除评论
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "com.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String commentId = request.getParameter("cid");
		BlogComment model = blogCommentService.getComment(new Integer(commentId));

		// 只有博主能够删除
		String username = UserHelper.getuserFromCookie(request);
		if (username.equals(model.getBlogBlogentry().getBlogBlog().getAuthor())) {
			BlogEntry entry = model.getBlogBlogentry();
			entry.setCommentCnt(entry.getCommentCnt() - 1);
			entryService.updateEntry(entry);

			blogCommentService.delComment(model);
		}

	}

	/**
	 * 评论列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		String entryId = request.getParameter("eid");
		String loadType = request.getParameter("loadType");
		Integer eid = null;
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		Blog blog = StringUtils.isNotEmpty(username) ? blogService
				.getBlogByUser(username) : null;
		if (StringUtils.isNotEmpty(entryId)) {
			eid = new Integer(entryId);
			BlogEntry e = entryService.getEntry(eid);
			request.setAttribute("entryModel", e);
			blog = e.getBlogBlog();
		}
		if (blog == null && !"ajax".equals(loadType)) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
			request.setAttribute("model", blog);
			request.getRequestDispatcher("/blog/blogForm.jsp").forward(request,
					response);
			return;
		}
		List<BlogComment> models = blogCommentService.findComments(
				blog == null ? null : blog.getId(), eid, start, limit);
		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (BlogComment c : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("comment", c);
			m.put("author", userService.getUser(null, c.getAuthor()));
			commentMapList.add(m);
		}
		Integer cnt = blogCommentService.getCommentsCnt(blog == null ? null : blog
				.getId(), eid);
		log.debug("models:" + cnt);
		request.setAttribute("models", models);
		request.setAttribute("commentMapList", commentMapList);
		request.setAttribute("count", cnt);
		request.setAttribute("blogModel", blog);
		request.setAttribute("limit", limit);
		request.setAttribute("start", start);
		this.blogHelper.prepareCommends(request);
		if ("ajax".equals(loadType)) {
			request.getRequestDispatcher("/blog/commentFrame.jsp").forward(
					request, response);
			return;
		}
		request.getRequestDispatcher("/blog/commentList.jsp").forward(request,
				response);

	}

	/**
	 * 添加评论
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String entryId = request.getParameter("eid");
		String body = request.getParameter("body");
		BlogComment model = null;
		String username = UserHelper.getuserFromCookie(request);
		BlogEntry entry = entryService.getEntry(new Integer(entryId));
		model = new BlogComment();
		model.setAuthenticated((short) 0);
		model.setBlogBlogentry(entry);
		model.setBody(body);
		model.setEmail("");
		model.setAuthor(username);
		model.setUpdateTime(DateUtils.getNow());
		model.setIp(HttpUtils.getIpAddr(request));
		blogCommentService.newComment(model);
		entry.setCommentCnt(entry.getCommentCnt() + 1);
		entryService.updateEntry(entry);

		// #675 (站内通知机制)
		// 如果有被回复的用户，发送系统通知
		String repliedUsername = request
				.getParameter(WebConstants.PARAMETER_REPLY_TO);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] { CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapEntry(entry) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}

		userService.trigeEvent(this.userService.getUser(username), entryId,
				UserEventType.COMMENT_NEW);
		request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
				.getInstance().getMessage("action.succeed"));

	}
}