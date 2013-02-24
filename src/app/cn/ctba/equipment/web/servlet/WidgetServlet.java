package cn.ctba.equipment.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.json.JSONHelper;
import org.net9.common.json.base.JSONException;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Equipment;

import cn.ctba.equipment.web.EquipmentHelper;

import com.google.inject.Inject;

/**
 * #872 (装备秀改进 - widget)
 * 
 * @author gladstone
 * 
 */
@WebModule("equipmentWidget")
@SuppressWarnings("serial")
public class WidgetServlet extends BusinessCommonServlet {
	@Inject
	private EquipmentHelper equipmentHelper;
	private JSONHelper jsonHelper = new JSONHelper();

	/**
	 * #872 (装备秀改进 - widget)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/apps/equipment/widget.jsp")
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
		request.setAttribute("hidelogo", hidelogo);
		User user = this.userService.getUser(Integer.valueOf(uid), null);

		List<Equipment> models = equipmentService.findEquipments(null, null,
				user.getUserName(), null, start, limit);
		request.setAttribute("models", this.equipmentHelper
				.wrapEquipmentList(models));
	}

	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@SuppressWarnings("unchecked")
	public void flashList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			JSONException {
		String uid = request.getParameter("uid");
		Integer start = 0;
		Integer limit = 10;
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");

		if (StringUtils.isNotEmpty(limitStr) && StringUtils.isNum(limitStr)) {
			limit = Integer.valueOf(limitStr);
		}
		if (StringUtils.isNotEmpty(startStr) && StringUtils.isNum(startStr)) {
			start = Integer.valueOf(startStr);
		}
		User user = this.userService.getUser(Integer.valueOf(uid), null);
		List<Equipment> models = equipmentService.findEquipments(null, null,
				user.getUserName(), null, start, limit);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, this.jsonHelper
				.getPOJOListJsonStr(models));
	}

	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ALL)
	@SuppressWarnings("unchecked")
	public void getUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String uid = request.getParameter("uid");
		User user = this.userService.getUser(Integer.valueOf(uid), null);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, this.jsonHelper
				.getPOJOJsonStr(user));
	}

	/**
	 * #872 (装备秀改进 - widget)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/apps/equipment/widgetBuilder.jsp")
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	@SuppressWarnings("unchecked")
	public void builder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		String username = UserHelper.getuserFromCookie(request);
		User u = userService.getUser(null, username);
		String widthStr = request.getParameter("width");
		String limitStr = request.getParameter("limit");
		String hidelogo = request.getParameter("hidelogo");
		request.setAttribute("user", u);
		if (StringUtils.isEmpty(limitStr)) {
			limitStr = "10";
		}
		request.setAttribute("width", widthStr);
		request.setAttribute("limit", limitStr);
		request.setAttribute("hidelogo", hidelogo);
	}

}
