package org.net9.core.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.User;

/**
 * 好友
 * 
 * @author gladstone
 * 
 */
@WebModule("friend")
@SuppressWarnings("serial")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class FriendServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(FriendServlet.class);

	/**
	 * 删除好友
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = true, url = "user.do?method=listFriends")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String userName = UserHelper.getuserFromCookie(request);
		String id = request.getParameter("id");
		Friend model = this.userService.getFriendById(Integer.valueOf(id));
		if (model != null
				&& userService.isFriend(userName, model.getFrdUserYou())) {

			// validate user
			String username = UserHelper.getuserFromCookie(request);
			UserHelper.authUserForCurrentPojoSimply(username, model);

			userService.deleteFriend(Integer.valueOf(id));
		}

	}

	@ReturnUrl(rederect = false, url = "/user/friendDone.jsp")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		String userFriend = request.getParameter("username");
		userFriend = StringUtils.base64Decode(userFriend);
		log.debug("Got f:" + userFriend);
		User u = userService.getUser(null, userFriend);
		if (u != null && !userService.isFriend(username, userFriend)) {
			Friend model = new Friend();
			model.setFrdMemo(userFriend);
			model.setFrdTag(userFriend);
			model.setFrdUserMe(username);
			model.setFrdUserYou(userFriend);
			userService.saveFriend(model, false);

			String msg = I18nMsgUtils.getInstance().createMessage(
					"friend.added",
					new Object[] { CommonUtils.buildUserPagelink(username) });
			userService.trigeEvent(this.userService.getUser(username),
					userFriend, UserEventType.ADD_FRIEND);
			userService.trigeNotice(userFriend, username, msg, null,
					NoticeType.COMMON);

		}
		request.setAttribute("friend", u);
	}

}
