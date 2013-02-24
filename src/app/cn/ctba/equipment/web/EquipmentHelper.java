package cn.ctba.equipment.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.util.CommonUtils;
import org.net9.core.service.UserService;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.ShopCategory;
import org.net9.domain.model.ctba.ShopModel;

import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.service.EquipmentCommentService;
import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.service.EquipmentUserService;
import cn.ctba.equipment.service.ShopService;

import com.google.inject.Inject;

public class EquipmentHelper implements java.io.Serializable {

	private static final long serialVersionUID = 6800109066569453195L;

	@Inject
	private ShopService shopService;
	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentCommentService equipmentCommentService;
	@Inject
	private EquipmentUserService equipmentUserService;
	@Inject
	private UserService userService;

	public void prepareShopInfo(HttpServletRequest request)
			throws ServletException, IOException, CommonSystemException {
		logger.debug("==prepareShopInfo==");

		String username = UserHelper.getuserFromCookie(request);
		ShopModel shopModel = this.shopService.getShopModelByUsername(username);
		if (shopModel != null) {
			// TODO: change this!
			shopModel.setEquipmentCnt(this.equipmentService
					.getEquipmentsCntByShopId(EquipmentStateType.OK, shopModel
							.getId()));
		}
		request.setAttribute("__request_shop", shopModel);
		request.setAttribute("myShopModel", shopModel);
	}

	static Logger logger = Logger.getLogger(EquipmentHelper.class);

	/**
	 * 作者信息/操作栏 加上用户的个人汇总信息 1 多少装备 2 多少评论 3 多少星级
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void prepareCommonInfo(HttpServletRequest request) throws Exception {
		// 总共的装备数目
		Integer wareCntAll = equipmentService.getEquipmentCnt(
				EquipmentStateType.OK, null, null, null, null);
		request.setAttribute("wareCntAll", wareCntAll);

		// 今天更新的数目
		Integer wareCntToday = equipmentService.getEquipmentCntByDate(
				EquipmentStateType.OK, CommonUtils.getDateFromTodayOn(0), null);
		request.setAttribute("wareCntToday", wareCntToday);

		Integer commentCntAll = equipmentCommentService.getEquipmentCommentCnt(
				null, null);
		request.setAttribute("commentCntAll", commentCntAll);
		// 登录用户信息
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		if (user != null) {
			Integer wareCnt = equipmentService.getEquipmentCnt(
					EquipmentStateType.OK, null, user.getUserName(), user
							.getUserId(), null);
			request.setAttribute("userWareCnt", wareCnt);

			// 关注
			request.setAttribute("refCntLevel1", this.equipmentUserService
					.getEquipmentUsersCntByUsername(username, 1));
			// 想收
			request.setAttribute("refCntLevel2", this.equipmentUserService
					.getEquipmentUsersCntByUsername(username, 2));
			// 已收
			request.setAttribute("refCntLevel3", this.equipmentUserService
					.getEquipmentUsersCntByUsername(username, 3));
		}

	}

	public List<Map<Object, Object>> formatCategoryList(List<ShopCategory> cList) {
		List<Map<Object, Object>> models = new ArrayList<Map<Object, Object>>();
		for (ShopCategory c : cList) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("category", c);
			m.put("cnt", this.equipmentService.getEquipmentsCntByCategoryId(
					EquipmentStateType.OK, c.getId()));
			models.add(m);
		}
		return models;
	}

	/**
	 * @param eList
	 * @return
	 */
	public List<Map<String, Object>> wrapEquipmentList(List<Equipment> eList) {
		List<Map<String, Object>> modelMaps = new ArrayList<Map<String, Object>>();
		for (Equipment model : eList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("equipment", model);
			m.put("author", userService.getUser(null, model.getUsername()));
			m.put("refCnt", this.equipmentUserService
					.getEquipmentUsersCnt(model.getId()));
			m.put("commentCnt", this.equipmentCommentService
					.getEquipmentCommentCnt(null, model.getId()));
			Double voteScore = equipmentUserService.getScoreSum(model.getId());
			if (voteScore != null) {
				m.put("voteScore", String.valueOf(voteScore));
			} else {
				m.put("voteScore", "0.0");
			}
			if (model.getShopId() != null) {
				m.put("shopCommend", this.shopService
						.getShopCommendByEquipmentIdAndShopId(model.getId(),
								model.getShopId()));
			}
			modelMaps.add(m);

		}
		return modelMaps;
	}
}
