package cn.ctba.widget.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.json.JSONHelper;
import org.net9.common.json.base.JSONException;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.EquipmentComment;

import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.service.EquipmentCommentService;
import cn.ctba.equipment.service.EquipmentService;

import com.google.inject.Inject;

@WebModule("wrtEquipment")
public class EquipmentServlet extends BusinessCommonServlet {

	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentCommentService equipmentCommentService;

	private JSONHelper jsonHelper = new JSONHelper();

	/**
	 * 列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	@AjaxResponse
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String typeStr = request.getParameter("type");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<Equipment> models = null;
		if ("hot".equals(typeStr)) {
			models = equipmentService.findEquipmentsByHits(
					EquipmentStateType.OK, null, null, start, limit);
		} else {
			models = equipmentService.findEquipments(EquipmentStateType.OK,
					null, null, null, start, limit);
		}
		request.setAttribute("count", equipmentService.getEquipmentCnt(
				EquipmentStateType.OK, null, null, null, null));
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, this.jsonHelper
				.getPOJOListJsonStr(models));

	}

	/**
	 * 查看单个
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String id = request.getParameter("id");
		Equipment model = this.equipmentService.getEquipment(Integer
				.valueOf(id));
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, this.jsonHelper
				.getPOJOJsonStr(model));

	}

	/**
	 * 查看单个的评论 TODO:完善
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws JSONException
	 */
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void comments(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			JSONException {
		String id = request.getParameter("id");
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<EquipmentComment> models = equipmentCommentService
				.findEquipmentComment(null, Integer.valueOf(id), start, limit);

		request.setAttribute(BusinessConstants.AJAX_MESSAGE, jsonHelper
				.getPOJOListJsonStr(models));
	}

}
