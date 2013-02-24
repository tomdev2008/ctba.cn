package org.net9.core.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.json.JSONHelper;
import org.net9.common.json.base.JSONException;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.tempdto.SelectorItem;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.Friend;

import com.google.inject.Inject;

/**
 * 用户选择
 * 
 * @author gladstone
 * @since 2007/07/01
 */
@AjaxResponse
@WebModule("userSelector")
@SuppressWarnings("serial")
public class UserSelectorServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(UserSelectorServlet.class);

	@Inject
	JSONHelper helper;

	/**
	 * 取得真实好友数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void items(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String username = UserHelper.getuserFromCookie(request);
		List<Friend> fList = this.userService.findFriends(username, null, 0,
				BusinessConstants.MAX_INT);
		List<SelectorItem> models = new ArrayList<SelectorItem>();
		for (Friend f : fList) {
			SelectorItem model = new SelectorItem();
			model.setName(f.getFrdUserYou());
			model.setId(f.getFrdUserYou());
			models.add(model);
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, helper
				.getPOJOListJsonStr(models));
	}

	/**
	 * 取得分组数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void group(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		SelectorItem model = new SelectorItem();
		model.setName("ALL");
		List<SelectorItem> models = new ArrayList<SelectorItem>();
		models.add(model);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, helper
				.getPOJOListJsonStr(models));
	}

	/**
	 * 取的建议用户数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void suggest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String username = UserHelper.getuserFromCookie(request);
		String text = request.getParameter("text");
		log.debug(text);
		List<Friend> fList = this.userService.findFriends(username, null, 0,
				BusinessConstants.MAX_INT);
		List<SelectorItem> models = new ArrayList<SelectorItem>();
		for (Friend f : fList) {
			if (f.getFrdUserYou().contains(text)) {
				SelectorItem model = new SelectorItem();
				model.setName(f.getFrdUserYou());
				model.setId(f.getFrdUserYou());
				models.add(model);
			}
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, helper
				.getPOJOListJsonStr(models));
	}
}
