package cn.ctba.equipment.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.ShopModel;

import cn.ctba.equipment.service.ShopService;

import com.google.inject.Inject;

@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
@WebModule("shopManage")
public class ShopManageServlet extends BusinessCommonServlet {

	static Logger logger = Logger.getLogger(ShopManageServlet.class);

	@Inject
	private ShopService shopService;

	/**
	 * 店铺列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(url = "/manage/equipment/shopList.jsp", rederect = false)
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<ShopModel> sList = this.shopService.findShops(start, limit);
		request.setAttribute("modelList", sList);
		request.setAttribute("count", this.shopService.getShopCnt());
	}

	/**
	 * 开店/店铺设置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(url = "/manage/equipment/shopForm.jsp", rederect = false)
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			ShopModel shopModel = this.shopService.getShopModel(Integer
					.valueOf(id));
			request.setAttribute("model", shopModel);
		}
	}

	/**
	 * 保存店铺设置
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(url = "shopManage.action?method=list", rederect = true)
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String logoPath = "";
		try {
			Map map = getMultiFile(request, "logo");
			logoPath = (String) map.get(BusinessCommonServlet.FILE_PATH);
			// logoName = (String) map.get(BusinessCommonServlet.FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String mainBiz = getParameter("mainBiz");
		String username = getParameter("username");
		String name = getParameter("name");
		String description = getParameter("description");
		ShopModel model;
		// String id = request.getParameter("id");
		model = this.shopService.getShopModelByUsername(username);
		if (model == null) {
			model = new ShopModel();
		}
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setEquipmentCnt(0);
		model.setHits(0);
		model.setType(0);// TODO:change this later
		if (StringUtils.isNotEmpty(logoPath)) {
			model.setLogo(logoPath);
			try {
				// create the images: mini default
				ImageUtils.getDefaultImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + logoPath), false);
				ImageUtils.getMiniImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + logoPath), false);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + logoPath), 16);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + logoPath), 64);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								SystemConfigVars.UPLOAD_DIR_PATH
										+ File.separator + logoPath), 48);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setDescription(description);
		model.setMainBiz(mainBiz);
		model.setUsername(username);
		model.setName(name);
		this.shopService.saveShopModel(model);
	}

}
