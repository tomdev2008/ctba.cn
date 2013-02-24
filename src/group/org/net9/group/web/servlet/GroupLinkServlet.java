package org.net9.group.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.DateUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.group.GroupLink;
import org.net9.domain.model.group.GroupModel;
import org.net9.group.web.GroupHelper;

/**
 * 
 * 小组友情链接
 * 
 * @author gladstone
 * @since Jan 13, 2009
 */
@WebModule("gLink")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@SuppressWarnings("serial")
public class GroupLinkServlet extends BusinessCommonServlet {

	static Log log = LogFactory.getLog(GroupLinkServlet.class);

	private final GroupHelper groupHelper = new GroupHelper();

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String glid = request.getParameter("id");
		GroupLink model = groupExtService.getGroupLink(new Integer(glid));
		Integer gid = model.getGroupModel().getId();

		// validate user
		this.groupHelper.authUserForCurrentPojo(request, model, model
				.getGroupModel());

		groupExtService.delGroupLink(model);
		response.sendRedirect("gLink.action?method=list&gid=" + gid);
	}

	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/group/groupLinkList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String gid = request.getParameter("gid");
		List<GroupLink> list = this.groupExtService.findGroupLinks(Integer
				.valueOf(gid), 0, BusinessConstants.MAX_INT);
		request.setAttribute("models", list);
		groupHelper.info(request, this.groupService.getGroup(Integer
				.valueOf(gid)));
		groupHelper.prepareCommends(request);
	}

	/**
	 * 排名向下
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void off(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String glid = request.getParameter("id");
		GroupLink model = groupExtService.getGroupLink(new Integer(glid));
		if (model != null) {
			GroupLink upperModel = groupExtService.getPrevGroupLinkIdx(model);
			if (upperModel != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperModel.getIdx());
				upperModel.setIdx(idx);
				groupExtService.saveLink(model);
				groupExtService.saveLink(upperModel);
			} else {
				model.setIdx(model.getIdx() - 1);
				if (model.getIdx() < 0) {
					model.setIdx(0);
				}
				groupExtService.saveLink(model);
			}
		}
		groupExtService.flushGroupLinkCache();
		response.sendRedirect("gLink.action?method=list&gid="
				+ model.getGroupModel().getId());
	}

	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String glid = request.getParameter("id");
		String gid = request.getParameter("gid");
		GroupLink model;

		String username = UserHelper.getuserFromCookie(request);
		String label = request.getParameter("label");
		String url = request.getParameter("url");

		if (StringUtils.isEmpty(glid)) {
			GroupModel groupModel = this.groupService.getGroup(Integer
					.valueOf(gid));
			log.debug("new GroupModel");
			model = new GroupLink();
			model.setGroupModel(groupModel);
			Integer idx = this.groupExtService.getMaxGroupLinkIdx(groupModel);
			if (idx == null) {
				idx = 1;
			}
			idx++;
			model.setIdx(idx);
			model.setUsername(username);
			model.setCreateTime(DateUtils.getNow());
		} else {
			log.debug("update GroupModel with:" + glid);
			model = groupExtService.getGroupLink(new Integer(glid));

			// validate user
			this.groupHelper.authUserForCurrentPojo(request, model, model
					.getGroupModel());
		}

		model.setLabel(label);
		model.setUrl(url);
		model.setUpdateTime(DateUtils.getNow());
		this.groupExtService.saveLink(model);
		request.setAttribute("model", model);
		response.sendRedirect("gLink.action?method=list&gid="
				+ model.getGroupModel().getId());
	}

	/**
	 * 排名向上
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void up(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String glid = request.getParameter("id");
		GroupLink model = groupExtService.getGroupLink(new Integer(glid));
		if (model != null) {
			GroupLink upperModel = groupExtService.getNextGroupLinkIdx(model);
			if (upperModel != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperModel.getIdx());
				upperModel.setIdx(idx);
				groupExtService.saveLink(model);
				groupExtService.saveLink(upperModel);
			} else {
				model.setIdx(model.getIdx() + 1);
				groupExtService.saveLink(model);
			}
		}
		groupExtService.flushGroupLinkCache();
		response.sendRedirect("gLink.action?method=list&gid="
				+ model.getGroupModel().getId());
	}

}
