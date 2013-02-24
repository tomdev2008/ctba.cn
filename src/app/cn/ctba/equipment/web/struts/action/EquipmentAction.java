package cn.ctba.equipment.web.struts.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.net9.common.exception.ModelNotFoundException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.EquipmentComment;
import org.net9.domain.model.ctba.EquipmentUser;
import org.net9.domain.model.ctba.ShopCategory;
import org.net9.domain.model.ctba.ShopCommend;
import org.net9.domain.model.ctba.ShopModel;

import cn.ctba.equipment.EquipmentConstant;
import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.web.EquipmentHelper;
import cn.ctba.equipment.web.struts.form.WareForm;

import com.google.inject.Inject;

/**
 * 商品Action
 * 
 * @author gladstone
 * 
 */
public class EquipmentAction extends BusinessDispatchAction {

	static Logger logger = Logger.getLogger(EquipmentAction.class);
	@Inject
	private EquipmentHelper equipmentHelper;

	/**
	 * 作者信息/操作栏 加上用户的个人汇总信息 1 多少装备 2 多少评论 3 多少星级
	 * 
	 * @param request
	 * @throws Exception
	 */
	private void _setCommonInfo(HttpServletRequest request) throws Exception {
		equipmentHelper.prepareCommonInfo(request);
		// 商铺信息
		equipmentHelper.prepareShopInfo(request);

	}

	/**
	 * 删除装备
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid = request.getParameter("wid");
		Equipment model = equipmentService.getEquipment(Integer.parseInt(wid));
		if (model != null) {

			// validate user
			String username = UserHelper.getuserFromCookie(request);
			UserHelper.authUserForCurrentPojoSimply(username, model);

			equipmentService.deleteEquipment(model);
		}
		return new ActionForward("equipment.shtml?method=manageList", true);
	}

	/**
	 * 删除物品评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward deleteComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		EquipmentComment model;
		model = equipmentCommentService.getEquipmentComment(Integer
				.parseInt(cid));

		// validate user
		String username = UserHelper.getuserFromCookie(request);
		UserHelper.authUserForCurrentPojoSimply(username, model);

		Integer wid = model.getGoodsWare().getId();
		equipmentCommentService.deleteGoodsComment(model);
		return new ActionForward(UrlConstants.EQUIPMENT + wid, true);
	}

	/**
	 * 列表 - 想收藏/已经收藏/关注
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward favList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.getFocusUser(request);
		String typeStr = request.getParameter("type");
		Integer type = 1;
		if (StringUtils.isNotEmpty(typeStr)) {
			type = new Integer(typeStr);
		}
		request.setAttribute("_request_fav_type", type);
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<Equipment> models = equipmentService.findRefEquipmentsByUsername(
				user.getUserName(), type, start, limit);
		request.setAttribute("models", this.equipmentHelper
				.wrapEquipmentList(models));
		request.setAttribute("count", this.equipmentUserService
				.getEquipmentUsersCntByUsername(user.getUserName(), type));
		_setCommonInfo(request);

		// 作者最近上传的装备(9件)
		List<Equipment> newEquipmentList = equipmentService
				.findEquipmentsByHits(EquipmentStateType.OK,
						user.getUserName(), null, 0, 9);
		request.setAttribute("newEquipmentList", newEquipmentList);
		// 作者得到的最新评论(10条)
		List<EquipmentComment> commentsList = this.equipmentCommentService
				.findEquipmentCommentsByReciever(user.getUserName(), 1, 10);
		request.setAttribute("newCommentList", commentsList);
		return mapping.findForward("fav.list");
	}

	/**
	 * 上传表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid = request.getParameter("wid");
		if (StringUtils.isNotEmpty(wid)) {
			Equipment model = equipmentService.getEquipment(Integer
					.parseInt(wid));
			request.setAttribute("model", model);
		}
		request.setAttribute("equipmentTypeList",
				EquipmentConstant.equipmentTypeList);
		request.setAttribute("brandList", EquipmentConstant.brandList);

		// 作者信息（当前登录用户信息）
		_setCommonInfo(request);

		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);

		// 作者最近上传的装备(10件)
		List<Equipment> newEquipmentList = equipmentService.findEquipments(
				EquipmentStateType.OK, null, username, null, 0, 10);
		request.setAttribute("newEquipmentList", newEquipmentList);
		// 作者得到的最新评论(10条)
		List<EquipmentComment> commentsList = this.equipmentCommentService
				.findEquipmentCommentsByReciever(user.getUserName(), 1, 10);
		request.setAttribute("newCommentList", commentsList);

		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		if (shopModel != null) {
			request.setAttribute("__request_shop", shopModel);
			List<ShopCategory> cList = this.shopService.findShopCategories(
					shopModel.getId(), 0, BusinessConstants.MAX_INT);
			request.setAttribute("categoryList", cList);
		}

		return mapping.findForward("ware.form");
	}

	/**
	 * 装备模块的首页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward indexPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// _setCommonInfo(request);
		//
		// String typeStr = request.getParameter("type");
		// Integer type = null;
		// if (CommonUtils.isNotEmpty(typeStr)) {
		// type = new Integer(typeStr);
		// }
		//
		// // 主要列表->人气单品(热门装备的列表)(15件)
		// List<Equipment> hotModels =
		// equipmentService.findEquipmentsByHits(null,
		// type, 0, 15);
		// request.setAttribute("hotModels", wrapEquipmentList(hotModels));
		//
		// // 最新装备的列表(15件)
		// List<Equipment> newModels = equipmentService.findEquipments(null,
		// null,
		// null, 0, 15);
		// request.setAttribute("newModels", wrapEquipmentList(newModels));
		//
		// request.setAttribute("count", equipmentService.getEquipmentCnt(null,
		// null, null, type));
		//
		// // 最新评论列表(20条)
		// List<EquipmentComment> commentList = equipmentCommentService
		// .findEquipmentComment(null, null, 0, 20);
		// request.setAttribute("newCommentList", commentList);
		//
		// return mapping.findForward("ware.home");
		return list(mapping, form, request, response);
	}

	/**
	 * 装备列表
	 * 
	 * 两种视图1 最新 2 最热
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("equipmentTypeList",
				EquipmentConstant.equipmentTypeList);
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
		request.setAttribute("models", this.equipmentHelper
				.wrapEquipmentList(models));

		// 最新评论列表(20条)
		List<EquipmentComment> commentList = equipmentCommentService
				.findEquipmentComment(null, null, 0, 20);
		request.setAttribute("newCommentList", commentList);

		_setCommonInfo(request);

		return mapping.findForward("ware.list");
	}

	/**
	 * 装备列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward manageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.getFocusUser(request);
		String typeStr = request.getParameter("type");
		Integer type = null;
		if (CommonUtils.isNotEmpty(typeStr)) {
			type = new Integer(typeStr);
		}
		String categoryStr = request.getParameter("category");
		Integer category = null;
		if (CommonUtils.isNotEmpty(categoryStr)) {
			category = new Integer(categoryStr);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		if (category == null) {
			List<Equipment> models = equipmentService.findEquipments(null,
					null, user.getUserName(), type, start, limit);
			request.setAttribute("models", this.equipmentHelper
					.wrapEquipmentList(models));
			request.setAttribute("count", equipmentService.getEquipmentCnt(
					null, null, user.getUserName(), null, type));
		} else {
			List<Equipment> models = equipmentService
					.findEquipmentsByCategoryId(null, category, start, limit);
			request.setAttribute("models", this.equipmentHelper
					.wrapEquipmentList(models));
			request.setAttribute("count", equipmentService
					.getEquipmentCntWithCategoryId(null, user.getUserName(),
							category));
		}
		ShopModel shopModel = this.shopService.getShopModelByUsername(user
				.getUserName());
		List<ShopCategory> cList = this.shopService.findShopCategories(
				shopModel.getId(), 0, BusinessConstants.MAX_INT);
		request.setAttribute("categoryList", cList);

		_setCommonInfo(request);

		Integer commentCnt = equipmentCommentService.getEquipmentCommentCnt(
				user.getUserId(), null);
		request.setAttribute("commentCnt", commentCnt);

		// 作者最近上传的装备(9件)
		List<Equipment> newEquipmentList = equipmentService
				.findEquipmentsByHits(EquipmentStateType.OK,
						user.getUserName(), null, 0, 9);
		request.setAttribute("newEquipmentList", newEquipmentList);
		// 作者得到的最新评论(10条)
		List<EquipmentComment> commentsList = this.equipmentCommentService
				.findEquipmentCommentsByReciever(user.getUserName(), 1, 10);
		request.setAttribute("newCommentList", commentsList);
		return mapping.findForward("ware.manageList");
	}

	/**
	 * 保存装备
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.getFocusUser(request);
		String wid = request.getParameter("wid");
		Equipment model;
		WareForm wareForm = (WareForm) form;
		FormFile imageFile = wareForm.getImageFile();
		if (StringUtils.isNotEmpty(wid)) {
			model = equipmentService.getEquipment(Integer.parseInt(wid));
		} else {
			model = new Equipment();
			model.setCreateTime(DateUtils.getNow());
			model.setHits(0);
			model.setUsername(user.getUserName());
			model.setVoteScore(0.0);
			if (this.userService.isEditor(user.getUserName())) {
				model.setState(EquipmentStateType.OK.getValue());
			} else {
				model.setState(EquipmentStateType.WAITING.getValue());
			}

		}
		model.setUid(user.getUserId());

		model.setUpdateTime(DateUtils.getNow());
		PropertyUtil.copyProperties(model, wareForm);
		if (StringUtils.isNotEmpty(wareForm.getBrandOther())) {
			model.setBrand(wareForm.getBrandOther());
		}
		try {
			String filePath = null;
			if (imageFile != null && imageFile.getFileSize() > 0
					&& CommonUtils.isNotEmpty(imageFile.getFileName())) {
				String filename = ImageUtils.wrapImageNameByTime(imageFile
						.getFileName());
				InputStream in = imageFile.getInputStream();
				String fileDir = SystemConfigVars.UPLOAD_DIR_PATH;
				String fullPath = request.getSession().getServletContext()
						.getRealPath(fileDir)
						+ File.separator + filename;
				ImageUtils.checkDir(fullPath);
				OutputStream out = new FileOutputStream(fullPath);
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
				out.close();
				in.close();
				imageFile.destroy();
				filePath = filename;
				ImageUtils.getDefaultImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), false);
				ImageUtils.getMiniImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), false);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), 32);

				// 大图图片宽 455px，首页小图宽 115px，切这两个宽度就行
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), 455);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), 115);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), 48);
				ImageUtils.getSizedImage(request.getSession()
						.getServletContext().getRealPath(
								fileDir + File.separator + filename), 80);
				model.setImage(filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		equipmentService.saveEquipment(model);
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isNotEmpty(wid)) {
			userService.trigeEvent(this.userService.getUser(username), model
					.getId()
					+ "", UserEventType.EQUIPMENT_EDIT);
			return new ActionForward(UrlConstants.EQUIPMENT + wid, true);
		} else {
			List<Equipment> eList = equipmentService.findEquipments(
					EquipmentStateType.WAITING, null, user.getUserName(), null,
					0, 1);
			if (eList.size() > 0) {
				userService.trigeEvent(this.userService.getUser(username),
						eList.get(0).getId() + "", UserEventType.EQUIPMENT_NEW);
			}
		}
		// return new ActionForward(UrlConstants.EQUIPMENT
		// + eList.get(0).getId(), true);
		// } else {
		return new ActionForward("equipment.shtml?method=manageList", true);
		// }

	}

	/**
	 * 保存物品评论
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward saveComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.getFocusUser(request);
		String cid = request.getParameter("cid");
		String wid = request.getParameter("wid");
		String parentId = request.getParameter("pid");
		EquipmentComment model;
		if (StringUtils.isNotEmpty(cid)) {
			model = equipmentCommentService.getEquipmentComment(Integer
					.parseInt(cid));
		} else {
			model = new EquipmentComment();
			model.setCreateTime(DateUtils.getNow());
			Equipment goodsWare = this.equipmentService.getEquipment(Integer
					.valueOf(wid));
			model.setGoodsWare(goodsWare);
			model.setUid(user.getUserId());
		}
		if (StringUtils.isNotEmpty(parentId)) {
			model.setParentId(Integer.parseInt(parentId));
		}
		model.setUpdateTime(DateUtils.getNow());
		model.setUsername(user.getUserName());
		PropertyUtil.populateBean(model, request);
		equipmentCommentService.saveEquipmentComment(model);

		String username = UserHelper.getuserFromCookie(request);

		userService.trigeEvent(this.userService.getUser(username), model
				.getGoodsWare().getId()
				+ "", UserEventType.EQUIPMENT_COMMENT);

		// 如果有被回复的用户，发送系统通知
		String repliedUsername = HttpUtils.getRepliedUsername(request);
		if (StringUtils.isNotEmpty(repliedUsername)) {
			String msg = I18nMsgUtils.getInstance().createMessage(
					"notice.replied",
					new Object[] {
							CommonUtils.buildUserPagelink(username),
							SimplePojoWrapper.wrapEquipment(model
									.getGoodsWare()) });
			String refererURL = HttpUtils.getReferer(request);
			userService.trigeNotice(repliedUsername, username, msg, refererURL,
					NoticeType.REPLY);
		}

		return new ActionForward("equipment/" + model.getGoodsWare().getId(),
				true);
	}

	/**
	 * 保存装备状态
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
	public ActionForward toggleState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = this.getFocusUser(request);
		String wid = request.getParameter("wid");
		Equipment model;
		model = equipmentService.getEquipment(Integer.parseInt(wid));
		if (this.userService.isEditor(user.getUserName())) {
			if (EquipmentStateType.OK.getValue().equals(model.getState())) {
				model.setState(EquipmentStateType.WAITING.getValue());
			} else {
				model.setState(EquipmentStateType.OK.getValue());
			}
			equipmentService.saveEquipment(model);
		}
		return new ActionForward(UrlConstants.EQUIPMENT + wid, true);

	}

	/**
	 * 查看装备
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid = request.getParameter("wid");
		// 装备详细情况
		Equipment model = equipmentService.getEquipment(Integer.parseInt(wid));
		if (model == null) {
			throw new ModelNotFoundException();
		}
		// 如果还未审批，需要提示信息
		if (EquipmentStateType.WAITING.getValue().equals(model.getState())
				|| model.getState() == null) {
			if (!model.getUsername().equals(
					UserHelper.getuserFromCookie(request))) {
				HttpUtils.sendError(request, response,
						"equipment.privacy.forbidden");
				return null;
			}
		}

		int hitPlus = new Random().nextInt(5) + 1;
		model.setHits(model.getHits() + hitPlus);
		equipmentService.saveEquipment(model);
		request.setAttribute("model", model);

		Double voteScore = equipmentUserService.getScoreSum(model.getId());
		if (voteScore != null) {
			request.setAttribute("voteScore", voteScore);
		} else {
			request.setAttribute("voteScore", 0.0);
		}

		Equipment nextModel = equipmentService.getNextEquipment(
				EquipmentStateType.OK, model.getId(), null, null);
		request.setAttribute("nextModel", nextModel);
		Equipment prevModel = equipmentService.getPrevEquipment(
				EquipmentStateType.OK, model.getId(), null, null);
		request.setAttribute("prevModel", prevModel);
		// 作者详细信息/其他信息
		User author = this.userService.getUser(null, model.getUsername());
		request.setAttribute("authorModel", author);
		// 相关装备
		List<Equipment> refEquipmentList = this.equipmentService
				.findRefEquipments(EquipmentStateType.OK, model.getId(), model
						.getType(), 0, 9);
		request.setAttribute("refEquipmentList", refEquipmentList);

		// 其他和此装备相关用户(10个)
		List<EquipmentUser> guList = equipmentUserService.findEquipmentUsers(
				model.getId(), 0, 10);
		List<Map<String, Object>> wrappedGuList = new ArrayList<Map<String, Object>>();
		for (EquipmentUser gu : guList) {
			Map<String, Object> m = new HashMap<String, Object>();
			User u = this.userService.getUser(null, gu.getUsername());
			m.put("user", u);
			m.put("gu", gu);
			wrappedGuList.add(m);
		}
		request.setAttribute("equipmentUsersList", wrappedGuList);
		// 装备和当前登录用户的关系（提供表单）
		String username = UserHelper.getuserFromCookie(request);
		if (StringUtils.isNotEmpty(username)) {
			User user = this.userService.getUser(null, username);
			EquipmentUser gu = this.equipmentUserService.getEquipmentUser(model
					.getId(), user.getUserId());
			request.setAttribute("equipmentUser", gu);
			// request.setAttribute("isUserEditor",
			// this.userService.isEditor(user
			// .getUserName()));

		} else {
			// request.setAttribute("isUserEditor", false);
		}

		// 其他
		request.setAttribute("equipmentType",
				EquipmentConstant.equipmentTypeMap.get(model.getType()));

		_setCommonInfo(request);

		// 评论
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<EquipmentComment> commentList = equipmentCommentService
				.findEquipmentComment(null, model.getId(), start, limit);
		List<Map<Object, Object>> commentMapList = new ArrayList<Map<Object, Object>>();
		for (EquipmentComment c : commentList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("comment", c);
			m.put("user", userService.getUser(null, c.getUsername()));
			commentMapList.add(m);
		}
		Integer count = equipmentCommentService.getEquipmentCommentCnt(null,
				model.getId());
		request.setAttribute("commentList", commentMapList);
		request.setAttribute("count", count);

		// 店铺信息
		if (model.getShopId() != null) {
			ShopModel shopModel = this.shopService.getShopModel(model
					.getShopId());
			request.setAttribute("shopModel", shopModel);
			if (shopModel != null) {
				shopModel.setHits(shopModel.getHits() + hitPlus);
				this.shopService.saveShopModel(shopModel);
			}
		}

		// 相关商铺(10家)
		List<ShopCommend> commendList = this.shopService
				.findShopCommendsByEquipmentId(model.getId(), 0, 10);
		request.setAttribute("commendList", commendList);

		// 如果单价是整数的话，可以购买（彩蛋哦）
		if (StringUtils.isNum(model.getPrice())) {
			request.setAttribute("could_buy", true);
		} else {
			request.setAttribute("could_buy", false);
		}
		return mapping.findForward("ware.view");
	}
}
