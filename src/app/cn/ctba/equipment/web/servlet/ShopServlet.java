package cn.ctba.equipment.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.GroupUserRoleType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.ShopCategory;
import org.net9.domain.model.ctba.ShopCommend;
import org.net9.domain.model.ctba.ShopModel;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupTopic;

import cn.ctba.equipment.EquipmentCommendType;
import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.service.ShopService;
import cn.ctba.equipment.web.EquipmentHelper;

import com.google.inject.Inject;

@WebModule("shop")
public class ShopServlet extends BusinessCommonServlet {

	static Logger logger = Logger.getLogger(ShopServlet.class);

	@Inject
	private ShopService shopService;
	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentHelper equipmentHelper;

	/**
	 * 商铺收到的评论
	 * 
	 * TODO:
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Deprecated
	public void commentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
	}

	/**
	 * 开店/店铺设置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(url = "/apps/equipment/shopForm.jsp", rederect = false)
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		String username = UserHelper.getuserFromCookie(request);
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		request.setAttribute("model", shopModel);
		List<GroupModel> gList = this.groupService
				.findGroupsByUsernameAndRoles(
						username,
						new Integer[] { GroupUserRoleType.GROUP_USER_ROLE_MANAGER },
						0, BusinessConstants.MAX_INT);
		List<GroupModel> groupList = new ArrayList<GroupModel>();
		groupList.addAll(gList);
		GroupModel dummyModel = new GroupModel();
		dummyModel.setId(-1);
		dummyModel.setName("无");
		groupList.add(dummyModel);
		request.setAttribute("groupList", groupList);
	}

	/**
	 * 店铺列表
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(url = "/apps/equipment/shopList.jsp", rederect = false)
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		List<ShopModel> sList = this.shopService.findShops(start, limit);
		request.setAttribute("shopList", sList);
		request.setAttribute("count", this.shopService.getShopCnt());
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
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	@ReturnUrl(url = "shop.action?method=form", rederect = true)
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		// equipmentHelper.prepareShopInfo(request);

		String logoPath = "";
		// String logoName = "";
		try {
			Map map = getMultiFile(request, "logo");
			logoPath = (String) map.get(BusinessCommonServlet.FILE_PATH);
			// logoName = (String) map.get(BusinessCommonServlet.FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String refGroupId = getParameter("refGroupId");
		String mainBiz = getParameter("mainBiz");
		String name = getParameter("name");
		String description = getParameter("description");

		String username = UserHelper.getuserFromCookie(request);
		ShopModel model = this.shopService.getShopModelByUsername(username);
		if (model == null) {
			model = new ShopModel();
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setEquipmentCnt(0);
			model.setHits(0);
			model.setType(0);// TODO:change this later
		}
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
		if (StringUtils.isNotEmpty(refGroupId) && StringUtils.isNum(refGroupId)) {
			model.setRefGroupId(Integer.valueOf(refGroupId));
		}
		this.shopService.saveShopModel(model);
	}

	/**
	 * 查看店铺
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ReturnUrl(url = "/apps/equipment/shopView.jsp", rederect = false)
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		equipmentHelper.prepareShopInfo(request);
		ShopModel model = null;
		String id = request.getParameter("id");
		String categoryId = request.getParameter("categoryId");
		if (StringUtils.isEmpty(id)) {
			String username = UserHelper.getuserFromCookie(request);
			model = this.shopService.getShopModelByUsername(username);
		} else {
			model = this.shopService.getShopModel(Integer.valueOf(id));
		}
		int hitPlus = new Random().nextInt(5) + 1;
		model.setHits(model.getHits() + hitPlus);
		model.setEquipmentCnt(this.equipmentService.getEquipmentsCntByShopId(
				EquipmentStateType.OK, model.getId()));
		this.shopService.saveShopModel(model);

		request.setAttribute("shopModel", model);

		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;

		if (StringUtils.isNotEmpty(categoryId)) {
			ShopCategory category = this.shopService.getShopCategory(Integer
					.valueOf(categoryId));
			request.setAttribute("categoryModel", category);
			List<Equipment> eList = this.equipmentService
					.findEquipmentsByCategoryId(EquipmentStateType.OK, category
							.getId(), start, limit);
			request.setAttribute("equipmentList", eList);
			request.setAttribute("count", this.equipmentService
					.getEquipmentsCntByCategoryId(EquipmentStateType.OK,
							category.getId()));
		} else {
			List<Equipment> eList = this.equipmentService
					.findEquipmentsByShopId(EquipmentStateType.OK, model
							.getId(), start, limit);
			request.setAttribute("equipmentList", eList);
			request.setAttribute("count", this.equipmentService
					.getEquipmentsCntByShopId(EquipmentStateType.OK, model
							.getId()));
		}

		// 掌柜推荐(6件)
		List<ShopCommend> commendList = this.shopService
				.findShopCommendsByShopId(model.getId(),
						EquipmentCommendType.LOCAL.getValue(), 0, 6);
		request.setAttribute("commendList", commendList);

		// 分类
		List<ShopCategory> cList = this.shopService.findShopCategories(model
				.getId(), 0, BusinessConstants.MAX_INT);
		request.setAttribute("categoryList", this.equipmentHelper
				.formatCategoryList(cList));

		// 相关小组
		if (model.getRefGroupId() != null) {
			GroupModel groupModel = this.groupService.getGroup(model
					.getRefGroupId());
			request.setAttribute("groupModel", groupModel);

			if (groupModel != null) {
				// 相关话题(10件)
				List<GroupTopic> groupTopicList = this.groupTopicService
						.findTopics(groupModel.getId(), false, false, null, 0,
								10);
				request.setAttribute("groupTopicList", groupTopicList);
			}
		}
	}
}
