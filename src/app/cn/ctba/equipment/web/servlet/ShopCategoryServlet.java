package cn.ctba.equipment.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.ShopCategory;
import org.net9.domain.model.ctba.ShopModel;

import cn.ctba.equipment.service.ShopService;
import cn.ctba.equipment.web.EquipmentHelper;

import com.google.inject.Inject;

@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@WebModule("shopCategory")
public class ShopCategoryServlet extends BusinessCommonServlet {
	static Logger logger = Logger.getLogger(ShopCategoryServlet.class);

	@Inject
	private ShopService shopService;

	@Inject
	private EquipmentHelper equipmentHelper;

	@ReturnUrl(url = "/apps/equipment/shopCategoryList.jsp", rederect = false)
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		String username = UserHelper.getuserFromCookie(request);
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		List<ShopCategory> cList = this.shopService.findShopCategories(
				shopModel.getId(), start, limit);
		request.setAttribute("modelList", this.equipmentHelper
				.formatCategoryList(cList));
		request.setAttribute("count", this.shopService
				.getShopCategoryCnt(shopModel.getId()));

	}

	/**
	 * 排名向下
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = true, url = "shopCategory.action?method=list")
	public void off(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryId = request.getParameter("id");
		ShopCategory model = shopService.getShopCategory(Integer
				.parseInt(categoryId));
		if (model != null) {
			ShopCategory upperCat = shopService.getPrevCategoryIdx(model);
			if (upperCat != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperCat.getIdx());
				upperCat.setIdx(idx);
				shopService.saveShopCategory(model);
				shopService.saveShopCategory(upperCat);
			} else {
				model.setIdx(model.getIdx() - 1);
				if (model.getIdx() < 0) {
					model.setIdx(0);
				}
				shopService.saveShopCategory(model);
			}
		}
	}

	/**
	 * 排名向上
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = true, url = "shopCategory.action?method=list")
	public void up(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryId = request.getParameter("id");
		ShopCategory model = shopService.getShopCategory(Integer
				.parseInt(categoryId));
		if (model != null) {
			ShopCategory upperCat = shopService.getNextCategoryIdx(model);
			if (upperCat != null) {
				Integer idx = model.getIdx();
				model.setIdx(upperCat.getIdx());
				upperCat.setIdx(idx);
				shopService.saveShopCategory(model);
				shopService.saveShopCategory(upperCat);
			} else {
				model.setIdx(model.getIdx() + 1);
				shopService.saveShopCategory(model);
			}
		}
	}

	@ReturnUrl(url = "shopCategory.action?method=list", rederect = true)
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String id = request.getParameter("id");
		ShopCategory model = shopService.getShopCategory(Integer.parseInt(id));
		if (model != null) {
			// validate user
			UserHelper.authUserSimply(request, model);
			shopService.deleteShopCategory(model);
		}
	}

	@ReturnUrl(url = "shopCategory.action?method=list", rederect = true)
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String id = request.getParameter("id");
		ShopCategory model = null;
		if (StringUtils.isNotEmpty(id)) {
			model = shopService.getShopCategory(Integer.parseInt(id));
		}
		String username = UserHelper.getuserFromCookie(request);
		String label = request.getParameter("label");
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		if (model != null) {
			// validate user
			UserHelper.authUserSimply(request, model);
			model.setLabel(label);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			shopService.saveShopCategory(model);
		} else {
			model = new ShopCategory();
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setEquipmentCnt(0);
			model.setIdx(0);
			model.setLabel(label);
			model.setShopModel(shopModel);
			model.setUpdateTime(StringUtils.getTimeStrByNow());
			model.setUsername(username);
			shopService.saveShopCategory(model);
		}
	}
}
