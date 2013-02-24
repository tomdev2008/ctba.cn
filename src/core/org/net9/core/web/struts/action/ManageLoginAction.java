package org.net9.core.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.net9.bbs.web.struts.form.AdminForm;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.service.DBAdminService;
import org.net9.domain.model.core.SysAdmin;

import com.google.inject.Inject;

/**
 * 管理登录的action
 * 
 * @author gladstone
 */
public class ManageLoginAction extends BusinessDispatchAction {

	public static Log log = LogFactory.getLog(ManageLoginAction.class);

	/** DB admin service */
	@Inject
	DBAdminService dbAdminService;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdminForm form1 = (AdminForm) form;
		String username = form1.getUsername();
		String password = form1.getPassword();
		String target = request.getParameter("target");
		boolean admin = manageService.authAdmin(username, password);
		// 不是文件系统用户，就到数据库查找
		if (!admin) {
			SysAdmin sysAdmin = dbAdminService.getAdmin(username);
			if (sysAdmin != null) {
				if (sysAdmin.getPassword().equalsIgnoreCase(password)) {
					admin = true;
				}
			}
		}

		if (!admin) {
			ActionMessage error = new ActionMessage("auth.notValid");
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			saveMessages(request, errors);
			return mapping.findForward("manage.login");
		} else {
			request.getSession().setAttribute(BusinessConstants.ADMIN_NAME, username);
			if ("front".equals(target)) {
				return new ActionForward("/index.jsp", true);
			}
			return new ActionForward("index.jsp", true);
		}
	}
}
