package org.net9.core.web.servlet.manage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.util.PropertyUtil;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.SysConfig;

/**
 * 分享
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@WebModule("shareManage")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class ShareManageServlet extends BusinessCommonServlet {

	@ReturnUrl(rederect = false, url = "/manage/user/score.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (HttpUtils.isMethodPost(request)) {
			SysConfig model = (SysConfig) commonService.getConfig();
			PropertyUtil.populateBean(model, request);
			commonService.saveConfig(model);
		}
		SysConfig model = (SysConfig) commonService.getConfig();
		request.setAttribute("model", model);
	}

	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
