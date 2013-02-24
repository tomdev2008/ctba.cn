package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.GroupTypeHelper;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.view.ViewGroupRank;

/**
 * 
 * 热榜
 * 
 * <li>人物
 * <li>小组
 * <li>新闻
 * <li>装备
 * <li>话题
 * <li>日志
 * 
 * @author gladstone
 * @since May 26, 2009
 */
@WebModule("rank")
public class RankServlet extends BusinessCommonServlet {

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/apps/rank/indexPage.jsp")
	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// do nothing
	}

	/**
	 * 人物(注册会员)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void people(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// do nothing
	}

	public void equipment(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// do nothing
	}

	/**
	 * 小组排名热榜列表
	 * 
	 * #818 (小组改进- 个人小组话题和小组排位)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/apps/rank/groupList.jsp")
	public void group(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		List<ViewGroupRank> groups = this.groupExtService.findGroupsByRank(
				start, limit);
		Integer cnt = groupService.getGroupsCnt(null, null);

		request.setAttribute("groups", groups);
		request.setAttribute("count", cnt);
	}

	@SuppressWarnings("unchecked")
	public void news(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		List<ViewGroupRank> groups = this.groupExtService.findGroupsByRank(
				start, limit);
		Integer cnt = groupService.getGroupsCnt(null, null);

		request.setAttribute("groups", groups);
		request.setAttribute("count", cnt);
	}

	/**
	 * 日志
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void blog(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		List typeList = GroupTypeHelper.typeList;
		request.setAttribute("typeList", typeList);

		List<ViewGroupRank> groups = this.groupExtService.findGroupsByRank(
				start, limit);
		Integer cnt = groupService.getGroupsCnt(null, null);

		request.setAttribute("groups", groups);
		request.setAttribute("count", cnt);
	}

}
