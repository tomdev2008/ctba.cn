package org.net9.core.web.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.service.DBAdminService;
import org.net9.core.util.HttpUtils;
import org.net9.domain.model.core.SysAdmin;
import org.net9.domain.model.core.SysAdminGroup;
import org.net9.domain.model.core.SysGroup;

import com.google.inject.Inject;

/**
 * sytem amdin action
 * 
 * @author gladstone
 */
public class SystemAdminAction extends BusinessDispatchAction {

	/** Logger */
	public static Log log = LogFactory.getLog(SystemAdminAction.class);

	/** DB admin service */
	@Inject
	DBAdminService dbAdminService;

	/**
	 * admin from
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward adminForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("username");
		if (StringUtils.isNotEmpty(username)) {
			SysAdmin model = dbAdminService.getAdmin(username);
			request.setAttribute("model", model);
			List<SysAdminGroup> adminGroups = dbAdminService
					.findAdminGroups(username);
			List<SysGroup> groups = dbAdminService.findGroups(0,
					BusinessConstants.MAX_INT);
			request.setAttribute("groups", groups);
			request.setAttribute("adminGroups", adminGroups);
		}
		return mapping.findForward("admin.form");
	}

	/**
	 * delete an admin
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("username");
		SysAdmin model = dbAdminService.getAdmin(username);
		dbAdminService.deleteAdmin(model);
		return new ActionForward("sysAdmin.shtml?method=listAdmins", true);
	}

	/**
	 * delete admin group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAdminGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String admingGroupId = request.getParameter("id");
		SysAdminGroup model = dbAdminService.getAdminGroup(new Integer(
				admingGroupId));
		String username = model.getUsername();
		dbAdminService.deleteAdminGroup(model.getId());
		return new ActionForward("sysAdmin.shtml?method=adminForm&username="
				+ username, true);
	}

	/**
	 * delete group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idStr = request.getParameter("gid");
		SysGroup model = dbAdminService.getGroup(new Integer(idStr));
		dbAdminService.deleteGroup(model);
		return new ActionForward("sysAdmin.shtml?method=listGroups", true);
	}

	/**
	 * group form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward groupForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idStr = request.getParameter("gid");
		if (StringUtils.isNotEmpty(idStr)) {
			SysGroup model = dbAdminService.getGroup(new Integer(idStr));
			request.setAttribute("model", model);
		}

		return mapping.findForward("group.form");
	}

	/**
	 * list admins
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listAdmins(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List admins = dbAdminService.findAdmins(start, limit);
		request.setAttribute("models", admins);
		return mapping.findForward("admin.list");
	}

	/**
	 * list system groups
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		Integer limit = WebConstants.PAGE_SIZE_30;
		List models = dbAdminService.findGroups(start, limit);
		Integer cnt = dbAdminService.getGroupCnt();
		request.setAttribute("models", models);
		request.setAttribute("count", cnt);
		return mapping.findForward("group.list");
	}

	/**
	 * 
	 * modify password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward password(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String adminUsername = (String) request.getSession().getAttribute(
				BusinessConstants.ADMIN_NAME);
		log.debug("Username:" + adminUsername);
		if (dbAdminService.isSuperAdmin(adminUsername)) {
			// error msg
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage("error.noOption.noLogin");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, msgs);
			request.setAttribute("forbidden", true);
		} else {
			if (HttpUtils.isMethodPost(request)) {
				String password = request.getParameter("userPassword0").trim();
				String newPassword = request.getParameter("userPassword1")
						.trim();
				SysAdmin model = dbAdminService.getAdmin(adminUsername);
				if (model != null) {
					if (model.getPassword().equals(password)) {
						model.setPassword(newPassword);
						model.setUpdateTime(StringUtils.getTimeStrByNow());
						dbAdminService.saveAdmin(model);
						ActionMessages msgs = new ActionMessages();
						ActionMessage msg = new ActionMessage("action.succeed");
						msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
						saveMessages(request, msgs);
					} else {
						// error msg
						ActionMessages msgs = new ActionMessages();
						ActionMessage msg = new ActionMessage(
								"password.notValid");
						msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
						saveMessages(request, msgs);
					}
				}
			}
			request.setAttribute("forbidden", false);
		}
		return mapping.findForward("admin.password");
	}

	/**
	 * save an admin
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("adminUsername");
		String password = request.getParameter("adminPassword");
		SysAdmin model = null;
		log.debug("Got username:" + username);
		if (StringUtils.isNotEmpty(username)) {
			model = dbAdminService.getAdmin(username);
			if (model == null) {
				model = new SysAdmin();
				model.setCreateTime(StringUtils.getTimeStrByNow());
				model.setUsername(username);
			}
			model.setPassword(password);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			dbAdminService.saveAdmin(model);

		}

		return new ActionForward("sysAdmin.shtml?method=listAdmins", true);
	}

	/**
	 * save admin group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAdminGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("adminUsername");
		String groupId = request.getParameter("gid");
		if (StringUtils.isNotEmpty(username)) {
			log.debug("Got username: " + username + ",Gid: " + groupId);
			SysAdminGroup model = dbAdminService.getAdminGroup(username,
					new Integer(groupId));
			if (model == null) {
				SysGroup group = dbAdminService.getGroup(new Integer(groupId));
				SysAdmin admin = dbAdminService.getAdmin(username);
				dbAdminService.insertAdminGroup(group, admin, group
						.getOptionStr());
			}
		}
		return new ActionForward("sysAdmin.shtml?method=adminForm&username="
				+ username, true);
	}

	/**
	 * insert or update group
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idStr = request.getParameter("gid");
		String name = request.getParameter("name");
		String optionStr = request.getParameter("optionStr");
		SysGroup model = null;
		if (StringUtils.isNotEmpty(idStr)) {
			model = dbAdminService.getGroup(new Integer(idStr));
		} else {
			model = new SysGroup();
			model.setCreateTime(StringUtils.getTimeStrByNow());
		}
		model.setName(name);
		model.setOptionStr(optionStr);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		dbAdminService.saveGroup(model);
		return new ActionForward("sysAdmin.shtml?method=listGroups", true);
	}

}
