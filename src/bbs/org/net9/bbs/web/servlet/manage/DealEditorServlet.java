package org.net9.bbs.web.servlet.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.UserSecurityType;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.User;

/**
 * 
 * 切换网站编辑权限
 * 
 * @author gladstone
 * @since 2007/07/01
 */
@Deprecated
@WebModule("dealEditor")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class DealEditorServlet extends BusinessCommonServlet {

	private static final long serialVersionUID = 1086243151965154328L;

	public void doProcess(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("userName");
		// if (StringUtils.isNotEmpty(userName))
		// userName = StringUtils.getSysEncodedStr(userName);
		User user = userService.getUser(null, userName);
		if (user.getUserIsEditor() > 0) {
			user.setUserIsEditor(0);
		} else {
			user.setUserIsEditor(1);
		}
		userService.saveUser(user, true);
		resp.sendRedirect("manage/manage.do?method=users");
	}
}
