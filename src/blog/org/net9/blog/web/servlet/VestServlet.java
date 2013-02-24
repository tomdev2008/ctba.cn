package org.net9.blog.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.blog.rss.BlogRssReader;
import org.net9.blog.web.BlogHelper;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.exception.SecurityException;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.rss.reader.RssReader;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogAddress;

import com.google.inject.Inject;

/**
 * Vest Address Action
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@WebModule("bv")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class VestServlet extends BusinessCommonServlet {

	private static final Log log = LogFactory.getLog(VestServlet.class);

	@Inject
	private BlogHelper blogHelper;

	/**
	 * 删除马甲地址
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "bv.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String aid = request.getParameter("aid");

		BlogAddress model = vestService.getAddress(new Integer(aid));

		// validate user
		UserHelper.authUserSimply(request, model);

		vestService.deleteAddress(new Integer(aid));
	}

	/**
	 * 马甲地址列表
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
		List models = vestService.findAddressByUser(username);
		request.setAttribute("models", models);
		if (models == null || models.size() < 1) {
			request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
					.getInstance().getMessage("blog.noVest"));
		}
		request.setAttribute("count", vestService.getAddressCnt(username));
		request.setAttribute("blogModel", blog);
		this.blogHelper.prepareCommends(request);
		request.getRequestDispatcher("/blog/addressList.jsp").forward(request,
				response);
	}

	/**
	 * 从马甲得到文章
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void rss(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String aid = request.getParameter("aid");
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isEmpty(aid)) {
			List<BlogAddress> models = vestService.findAddressByUser(username);
			for (BlogAddress address : models) {
				Map<String, Object> m = new HashMap<String, Object>();
				RssReader reader = new BlogRssReader();
				m.put("url", address.getUrl());
				m.put("username", address.getUsername());
				m.put("gotentriescnt", address.getGotEntriesCnt());
				m.put("id", address.getId());
				try {
					reader.buildRSSContent(m);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} else {
			BlogAddress address = vestService.getAddress(new Integer(aid));
			if (address != null) {
				RssReader reader = new BlogRssReader();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("url", address.getUrl());
				m.put("username", address.getUsername());
				m.put("gotentriescnt", address.getGotEntriesCnt());
				m.put("id", address.getId());
				try {
					reader.buildRSSContent(m);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		log.info("User " + username + " got contents from rss...");
		request.setAttribute(BusinessConstants.MSG_KEY, I18nMsgUtils
				.getInstance().getMessage("action.succeed"));
	}

	/**
	 * 保存马甲地址
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SecurityException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = true, url = "bv.action?method=list")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SecurityException {
		String aid = request.getParameter("aid");
		String url = request.getParameter("url");
		if (!url.startsWith(BlogRssReader.SUFFIX_PROTOCAL)) {
			url = BlogRssReader.SUFFIX_PROTOCAL + "" + url;
		}
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isNotEmpty(aid)) {
			BlogAddress model = vestService.getAddress(new Integer(aid));

			// validate user
			UserHelper.authUserSimply(request, model);

			model.setUrl(url);
			vestService.saveAddress(model);
		} else {
			BlogAddress model = new BlogAddress();
			model.setUsername(username);
			model.setUrl(url);
			model.setCreateTime(DateUtils.getNow());
			model.setUpdateTime(DateUtils.getNow());
			model.setGotEntriesCnt(0);
			vestService.saveAddress(model);
		}
	}
}