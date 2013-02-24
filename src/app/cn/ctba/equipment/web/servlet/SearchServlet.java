package cn.ctba.equipment.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.Equipment;

import cn.ctba.equipment.EquipmentConstant;
import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.web.EquipmentHelper;

import com.google.inject.Inject;

/**
 * #766 (装备秀改进 - 品牌分类搜索)
 * 
 * @author ChenChangRen
 * 
 */
@WebModule("equipmentSearch")
public class SearchServlet extends BusinessCommonServlet {
	static Logger logger = Logger.getLogger(SearchServlet.class);

	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentHelper equipmentHelper;

	@ReturnUrl(url = "/apps/equipment/searchList.jsp", rederect = false)
	public void search(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("equipmentTypeList",
				EquipmentConstant.equipmentTypeList);
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String brand = request.getParameter("brand");
		request.setAttribute("brandName", brand);
		Integer type = null;
		String typeStr = request.getParameter("type");
		if (StringUtils.isNotEmpty(typeStr)) {
			type = Integer.valueOf(typeStr);
			request.setAttribute("typeName", EquipmentConstant.equipmentTypeMap
					.get(type));
			if (StringUtils.isNotEmpty(brand)) {
				request.setAttribute("typeName",
						EquipmentConstant.equipmentTypeMap.get(type) + " + "
								+ brand);
			}
		}
		List<Equipment> eList = this.equipmentService.findEquipmentsByBrand(
				EquipmentStateType.OK, brand, type, start, limit);
		request.setAttribute("equipmentList", this.equipmentHelper
				.wrapEquipmentList(eList));
		request.setAttribute("count", this.equipmentService
				.getEquipmentsCntByBrand(EquipmentStateType.OK, brand, type));
	}

	/**
	 * 品牌列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(url = "/apps/equipment/brandList.jsp", rederect = false)
	public void brandList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("equipmentTypeList",
				EquipmentConstant.equipmentTypeList);
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
	}

}
