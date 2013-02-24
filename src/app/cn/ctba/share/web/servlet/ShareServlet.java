package cn.ctba.share.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.SharePrivateStateType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.core.wrapper.ListWrapper;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.ShareComment;
import org.net9.domain.model.core.User;

/**
 * [widget]分享的js展现
 * 
 * @author gladstone
 * 
 */
@WebModule("share")
@SuppressWarnings("serial")
public class ShareServlet extends BusinessCommonServlet {

	/**
	 * #688 ([widget]分享的js展现)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/apps/share/shareWidget.jsp")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@SuppressWarnings("unchecked")
	public void widget(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("uid");
		// String heightStr = request.getParameter("height");
		String widthStr = request.getParameter("width");
		String limitStr = request.getParameter("limit");
		String hidelogo = request.getParameter("hidelogo");
		Integer start = 0;
		Integer limit = 10;
		// Integer height = 300;
		Integer width = 120;
		if (StringUtils.isNotEmpty(limitStr) && StringUtils.isNum(limitStr)) {
			limit = Integer.valueOf(limitStr);
		}
		if (StringUtils.isNotEmpty(widthStr) && StringUtils.isNum(widthStr)) {
			width = Integer.valueOf(widthStr);
			// 默认自适应
			request.setAttribute("width", width);
		}
		// if (StringUtils.isNotEmpty(heightStr)) {
		// height = Integer.valueOf(heightStr);
		// request.setAttribute("height", height);
		// }
		//		
		request.setAttribute("hidelogo", hidelogo);
		User user = this.userService.getUser(Integer.valueOf(uid), null);
		List<Share> sList = this.shareService.findShares(user.getUserName(),
				null, SharePrivateStateType.PUBLIC.getValue(), start, limit);
		request.setAttribute("shareList", ListWrapper.getInstance()
				.formatShareList(sList));
	}

	/**
	 * #688 ([widget]分享的js展现)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/apps/share/shareWidgetBuilder.jsp")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	@SuppressWarnings("unchecked")
	public void builder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		User u = userService.getUser(null, username);
		String widthStr = request.getParameter("width");
		String limitStr = request.getParameter("limit");
		String hidelogo = request.getParameter("hidelogo");
		request.setAttribute("user", u);
		// if(StringUtils.isEmpty(widthStr)){
		// widthStr="169";
		// }
		if (StringUtils.isEmpty(limitStr)) {
			limitStr = "10";
		}
		request.setAttribute("width", widthStr);
		request.setAttribute("limit", limitStr);
		request.setAttribute("hidelogo", hidelogo);
	}

	/**
	 * 用户收到的评论
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/apps/share/myCommentList.jsp")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public void myComments(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);

		Integer start = 0;
		Integer limit = WebConstants.PAGE_SIZE_30;

		List<ShareComment> commentList = shareService.findShareCommentsByOwner(
				username, start, limit);
		// request.setAttribute("commentList", commentList);

		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (ShareComment comment : commentList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("comment", comment);
			m
					.put("user", this.userService.getUser(null, comment
							.getUsername()));
			commentMapList.add(m);
		}
		request.setAttribute("models", commentMapList);

		request.setAttribute("count", shareService
				.getShareCommentsCntByOwner(username));

	}

}
