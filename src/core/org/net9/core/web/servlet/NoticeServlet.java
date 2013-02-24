package org.net9.core.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.json.base.JSONException;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.constant.WebPageVarConstant;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.Notice;
import org.net9.domain.model.core.User;

/**
 * 站内通知
 * 
 * @author gladstone
 * @since Feb 22, 2009
 */
@WebModule("notice")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@SuppressWarnings("serial")
public class NoticeServlet extends BusinessCommonServlet {

	static Log logger = LogFactory.getLog(NoticeServlet.class);

	/**
	 * 删除通知
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = true, url = "notice.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String id = request.getParameter("id");
		String username = UserHelper.getuserFromCookie(request);

		if (StringUtils.isNotEmpty(id)) {
			Notice model = this.userExtService.getNotice(Integer.valueOf(id));

			// validate user
			UserHelper.authUserForCurrentPojoSimply(username, model);

			this.userExtService.deleteNotice(model);
		}
		// 支持批量删除
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("_notice_") >= 0) {
				String value = key.substring("_notice_".length());
				logger.info("delete notice " + value);
				Notice model = this.userExtService.getNotice(Integer
						.valueOf(value));

				// validate user
				UserHelper.authUserForCurrentPojoSimply(username, model);

				this.userExtService.deleteNotice(model);
			}
		}
	}

	/**
	 * 站内通知列表
	 * 
	 * 只列出未过期的
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/user/noticeList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		Integer start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List<Notice> list = this.userExtService.findNotices(username, null,
				null, start, limit);
		List models = new ArrayList();
		for (Notice n : list) {
			Map map = new HashMap();
			map.put("notice", n);
			User u = userService.getUser(null, n.getUsername());
			map.put("user", u);
			models.add(map);
		}
		request.setAttribute("models", models);
		request.setAttribute("count", this.userExtService.getNoticeCnt(
				username, null, null));
		this.userExtService.expireNotices(username, null);
		request.setAttribute(WebPageVarConstant.NOTICE_CNT, 0);
	}
	
	/**
	 * 使用XML
	 * 
	 * <li>已登录用户返回 用户的站内提醒数目
	 * <li>未登录用户返回 -1
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@ReturnUrl(rederect = false, url = "/apps/wrt/noticeCntXmlResponse.jsp")
	public void noticeCnt(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			JSONException {
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isNotEmpty(username)) {
			request.setAttribute("message", this.userExtService.getNoticeCnt(
					username, 0, null));
		} else {
			request.setAttribute("message", "-1");
		}
		response.setContentType(BusinessConstants.MIME_TYPE_XML);
	}

}
