package cn.ctba.equipment.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.ShopCommend;
import org.net9.domain.model.ctba.ShopModel;

import cn.ctba.equipment.CommendType;
import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.service.ShopService;
import cn.ctba.equipment.web.EquipmentHelper;

import com.google.inject.Inject;

/**
 * 商品推荐
 * 
 * @author gladstone
 * 
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@WebModule("shopCommend")
public class ShopCommendServlet extends BusinessCommonServlet {

	static Logger logger = Logger.getLogger(ShopCommendServlet.class);

	@Inject
	private ShopService shopService;
	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentHelper equipmentHelper;

	/**
	 * 推荐商品
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(url = "/apps/equipment/commendForm.jsp", rederect = false)
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		String username = UserHelper.getuserFromCookie(request);
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		request.setAttribute("model", shopModel);
	}

	/**
	 * 保存推荐
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String eid = request.getParameter("eid");
		String label = request.getParameter("label");
		String typeStr = request.getParameter("type");
		Integer type = 0;// TODO: change this: 0-> 本店推荐 1-> 别店商品 2->站外链接
		if (StringUtils.isNotEmpty(typeStr)) {
			type = Integer.valueOf(typeStr);
		}
		Equipment equipmentModel = equipmentService.getEquipment(Integer
				.parseInt(eid));
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);

		ShopCommend model = this.shopService
				.getShopCommendByEquipmentIdAndShopId(equipmentModel.getId(),
						shopModel.getId());
		if (model == null) {
			model = new ShopCommend();
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setEquipment(equipmentModel);
			if (StringUtils.isEmpty(label)) {
				label = equipmentModel.getName();
			}
			model.setLabel(label);
			model.setShopModel(shopModel);
			model.setType(type);
			model.setUsername(username);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			this.shopService.saveShopCommend(model);
		}

		request.setAttribute(BusinessConstants.AJAX_MESSAGE, "done");
	}

	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void saveSuperCommend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		String eid = request.getParameter("eid");
		String label = request.getParameter("label");
		String url = request.getParameter("url");
		Integer type = CommendType.EXTERNAL.getValue();
		Equipment equipmentModel = equipmentService.getEquipment(Integer
				.parseInt(eid));
		ShopCommend model = new ShopCommend();
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setEquipment(equipmentModel);
		if (StringUtils.isEmpty(label)) {
			label = equipmentModel.getName();
		}
		model.setLabel(label);
		model.setType(type);
		model.setUrl(url);
		model.setUsername(username);
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		this.shopService.saveShopCommend(model);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, "done");
	}

	/**
	 * 取消推荐
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String eid = request.getParameter("eid");
		Equipment equipmentModel = equipmentService.getEquipment(Integer
				.parseInt(eid));
		ShopModel shopModel = this.shopService.getShopModel(equipmentModel
				.getShopId());
		ShopCommend model = this.shopService
				.getShopCommendByEquipmentIdAndShopId(equipmentModel.getId(),
						shopModel.getId());
		if (model != null) {
			// validate user
			UserHelper.authUserWithEditorOption(request, model, userService);
			// .authUserSimply(request, model);
			this.shopService.deleteShopCommend(model);
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, "done");
	}
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
	@AjaxResponse
	@ReturnUrl(rederect = false, url = UrlConstants.AJAX_PAGE)
	public void deleteSuperCommend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ShopCommend model = this.shopService
				.getShopCommend(Integer.valueOf(id));
		if (model != null) {
			// validate user
			UserHelper.authUserWithEditorOption(request, model, userService);
			// .authUserSimply(request, model);
			this.shopService.deleteShopCommend(model);
		}
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, "done");
	}
}
