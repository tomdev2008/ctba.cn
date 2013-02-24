package org.net9.blog.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.blog.web.BlogHelper;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogLink;

import com.google.inject.Inject;

/**
 * Links Action
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@WebModule("bl")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class LinkServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(LinkServlet.class);

	@Inject
	private BlogHelper blogHelper;

	/**
	 * 删除链接
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "bl.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String linkId = request.getParameter("lid");
		BlogLink model = linkService.getLink(new Integer(linkId));
		if (model != null) {

			// validate user
			UserHelper.authUserSimply(request, model);

			linkService.delLink(model);
		}
	}

	/**
	 * 链接列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		Integer blogId = 0;
		Blog blog = blogService.getBlogByUser(username);
		if (blog == null) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
			request.setAttribute("model", blog);
			request.getRequestDispatcher("/blog/blogForm.jsp").forward(request,
					response);
			return;
		}
		blogId = blog.getId();
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List models = linkService.listLinks(blogId, start, limit);

		request.setAttribute("links", models);

		request.setAttribute("count", linkService.getLinksCnt(blogId));
		log.debug(linkService.getLinksCnt(blogId) + "___" + models.size());
		request.setAttribute("blogModel", blog);
		this.blogHelper.prepareCommends(request);
		request.getRequestDispatcher("/blog/linkList.jsp").forward(request,
				response);
	}

	/**
	 * 链接排名向下
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "bl.action?method=list")
	public void off(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String linkId = request.getParameter("lid");
		BlogLink model = linkService.getLink(new Integer(linkId));

		if (model != null) {

			// validate user
			UserHelper.authUserSimply(request, model);

			BlogLink offerLink = linkService.getPrevLinksIdx(model);
			if (offerLink != null) {
				Integer idx = model.getIdx();
				model.setIdx(offerLink.getIdx());
				offerLink.setIdx(idx);
				linkService.updateLink(model);
				linkService.updateLink(offerLink);
			} else {
				model.setIdx(model.getIdx() - 1);
				if (model.getIdx() < 0) {
					model.setIdx(0);
				}
				linkService.updateLink(model);
			}
		}
	}

	/**
	 * 保存链接
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	@ReturnUrl(rederect = true, url = "bl.action?method=list")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String linkId = request.getParameter("lid");
		String url = request.getParameter("url");
		String title = request.getParameter("title");
		String username = UserHelper.getuserFromCookie(request);
		Blog blog = blogService.getBlogByUser(username);
		BlogLink model = null;
		if (StringUtils.isEmpty(linkId)) {
			// insert
			model = new BlogLink();
			model.setBlogId(blog.getId());
			model.setHit(0);
			model.setImageUrl("");
			model.setTitle(title);
			model.setUpdateTime(DateUtils.getNow());
			model.setUrl(url);
			Integer maxIndex = this.linkService.getMaxLinksIdx(blog.getId());
			if (maxIndex == null) {
				maxIndex = 0;
			}
			model.setIdx(maxIndex + 1);
			linkService.newLink(model);
		} else {
			// update
			model = linkService.getLink(new Integer(linkId));

			// validate user
			UserHelper.authUserSimply(request, model);

			model.setTitle(title);
			model.setUpdateTime(DateUtils.getNow());
			model.setUrl(url);
			linkService.updateLink(model);
		}
	}

	/**
	 * 链接排名向上
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "bl.action?method=list")
	public void up(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String linkId = request.getParameter("lid");
		BlogLink model = linkService.getLink(new Integer(linkId));
		if (model != null) {

			// validate user
			UserHelper.authUserSimply(request, model);

			BlogLink upperLink = linkService.getNextLinksIdx(model);
			if (upperLink != null) {
				log.debug("next link is:" + upperLink.getIdx());
				Integer idx = new Integer(model.getIdx().intValue());
				model.setIdx(upperLink.getIdx());
				upperLink.setIdx(idx);
				linkService.updateLink(model);
				linkService.updateLink(upperLink);
			} else {
				model.setIdx(model.getIdx() + 1);
				linkService.updateLink(model);
			}
		}
	}
}