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
import org.net9.domain.model.blog.BlogCategory;

import com.google.inject.Inject;

/**
 * 日志分类
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@WebModule("cat")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class CategoryServlet extends BusinessCommonServlet {

	private static final Log log = LogFactory.getLog(CategoryServlet.class);

	@Inject
	private BlogHelper blogHelper;

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "cat.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String categoryId = request.getParameter("cid");
		BlogCategory model = blogService.getCategory(new Integer(categoryId));
		// validate user
		UserHelper.authUserSimply(request, model);

		blogService.delCategory(model);
		log.debug("delete cat:" + model.getId());
	}

	/**
	 * 列表
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
		Blog blog = blogService.getBlogByUser(username);
		if (blog == null) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.empty.new"));
			request.setAttribute("model", blog);
			request.getRequestDispatcher("/blog/blogForm.jsp").forward(request,
					response);
			return;
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List models = blogService.findCategories(blog.getId(), start, limit);
		request.setAttribute("models", models);
		if (models == null || models.size() < 1) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.noCat"));
		}
		request.setAttribute("count", blogService
				.getCategoriesCnt(blog.getId()));
		request.setAttribute("blogModel", blog);
		blogHelper.prepareCommends(request);
		request.getRequestDispatcher("/blog/categoryList.jsp").forward(request,
				response);
	}

	/**
	 * 排名向下
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = true, url = "cat.action?method=list")
	public void off(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryId = request.getParameter("cid");
		BlogCategory model = blogService.getCategory(new Integer(categoryId));
		if (model != null) {
			BlogCategory upperCat = blogService.getPrevCategoryIdx(model);
			if (upperCat != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperCat.getIdx());
				upperCat.setIdx(idx);
				blogService.updateCategory(model);
				blogService.updateCategory(upperCat);
			} else {
				model.setIdx(model.getIdx() - 1);
				if (model.getIdx() < 0) {
					model.setIdx(0);
				}
				blogService.updateCategory(model);
			}
		}
	}

	/**
	 * 保存
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	@ReturnUrl(rederect = true, url = "cat.action?method=list")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String categoryId = request.getParameter("cid");
		String name = request.getParameter("name");
		String tags = request.getParameter("name");
		String username = UserHelper.getuserFromCookie(request);
		Blog blog = blogService.getBlogByUser(username);
		if (StringUtils.isNotEmpty(categoryId)) {
			BlogCategory model = blogService
					.getCategory(new Integer(categoryId));

			// validate user
			UserHelper.authUserSimply(request, model);

			model.setName(name);
			model.setTags(tags);
			blogService.updateCategory(model);
		} else {
			BlogCategory model = new BlogCategory();
			model.setBlogBlog(blog);
			model.setName(name);
			model.setTags(tags);
			Integer idx = blogService.getMaxCategoryIdx(blog);
			if (idx == null) {
				idx = 1;
			}
			idx++;
			model.setIdx(idx);
			blogService.newCategory(model);
		}
	}

	/**
	 * 排名向上
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = true, url = "cat.action?method=list")
	public void up(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryId = request.getParameter("cid");
		BlogCategory model = blogService.getCategory(new Integer(categoryId));
		if (model != null) {
			BlogCategory upperCat = blogService.getNextCategoryIdx(model);
			if (upperCat != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperCat.getIdx());
				upperCat.setIdx(idx);
				blogService.updateCategory(model);
				blogService.updateCategory(upperCat);
			} else {
				model.setIdx(model.getIdx() + 1);
				blogService.updateCategory(model);
			}
		}
	}
}