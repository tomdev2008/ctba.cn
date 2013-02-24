package org.net9.core.web.servlet.manage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.MainCommend;

/**
 * 系统推荐
 * 
 * @author gladstone
 * @since Dec 8, 2008
 */
@SuppressWarnings("serial")
@WebModule("commend")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class MainCommendServlet extends BusinessCommonServlet {

	@ReturnUrl(rederect = true, url = "commend.action?method=list")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		MainCommend model = this.commonService.getMainCommend(Integer
				.valueOf(id));
		this.commonService.deleteMainCommend(model);
	}

	@ReturnUrl(rederect = false, url = "/manage/sys/commendList.jsp")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer start = HttpUtils.getStartParameter(request);
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			List<MainCommend> list = this.commonService.findMainCommends(start,
					WebConstants.PAGE_SIZE_30);
			request.setAttribute("models", list);
			request.setAttribute("count", this.commonService
					.getMainCommendCnt());
		} else {
			List<MainCommend> list = this.commonService.findMainCommendsByType(
					Integer.valueOf(type), start, WebConstants.PAGE_SIZE_30);
			request.setAttribute("models", list);
			request.setAttribute("count", this.commonService
					.getMainCommendCntByType(Integer.valueOf(type)));
		}

	}

	/**
	 * 排名向下
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = true, url = "commend.action?method=list")
	public void off(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		MainCommend model = this.commonService.getMainCommend(Integer
				.valueOf(id));
		if (model != null) {
			MainCommend upperModel = commonService.getPrevMainCommendIdx(model);
			if (upperModel != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperModel.getIdx());
				upperModel.setIdx(idx);
				commonService.saveMainCommend(model);
				commonService.saveMainCommend(upperModel);
			} else {
				model.setIdx(model.getIdx() - 1);
				if (model.getIdx() < 0) {
					model.setIdx(0);
				}
				commonService.saveMainCommend(model);
			}
		}
		commonService.flushMainCommendCache();
	}

	@ReturnUrl(rederect = true, url = "commend.action?method=list")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MainCommend model = new MainCommend();
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setCreateTime(StringUtils.getTimeStrByNow());
		PropertyUtil.populateBean(model, request);
		Integer idx = commonService.getMaxMainCommendIdx(Integer
				.valueOf(request.getParameter("type")));
		if (idx == null) {
			idx = 1;
		}
		idx++;
		model.setIdx(idx);
		this.commonService.saveMainCommend(model);
	}

	/**
	 * 排名向上
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = true, url = "commend.action?method=list")
	public void up(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		MainCommend model = this.commonService.getMainCommend(Integer
				.valueOf(id));
		if (model != null) {
			MainCommend upperModel = commonService.getNextMainCommendIdx(model);
			if (upperModel != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperModel.getIdx());
				upperModel.setIdx(idx);
				commonService.saveMainCommend(model);
				commonService.saveMainCommend(upperModel);
			} else {
				model.setIdx(model.getIdx() + 1);
				commonService.saveMainCommend(model);
			}
		}
		commonService.flushMainCommendCache();
	}
}
