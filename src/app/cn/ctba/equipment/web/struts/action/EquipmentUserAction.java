package cn.ctba.equipment.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.UserHelper;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.EquipmentScore;
import org.net9.domain.model.ctba.EquipmentUser;

import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.service.EquipmentUserService;

import com.google.inject.Inject;

/**
 * 商品和用户关联Action
 * 
 * @author gladstone
 * 
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class EquipmentUserAction extends BusinessDispatchAction {

	static Logger logger = Logger.getLogger(EquipmentUserAction.class);
	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentUserService equipmentUserService;

	/**
	 * 保存投票，ajax加载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	public ActionForward vote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		String wid = request.getParameter("wid");
		String value = request.getParameter("value");
		Equipment ware = this.equipmentService.getEquipment(Integer
				.valueOf(wid));
		if (null == request.getSession().getAttribute(
				"ware_vote_" + wid + "" + user.getUserId())) {
			EquipmentScore model = new EquipmentScore();
			model.setCreateTime(StringUtils.getTimeStrByNow());
			model.setUid(user.getUserId());
			model.setUsername(user.getUserName());
			model.setWareId(Integer.valueOf(wid));
			model.setValue(Integer.valueOf(value));
			equipmentUserService.saveEquipmentScore(model);
			request.getSession().setAttribute(
					"ware_vote_" + wid + "" + user.getUserId(), "done");
			userService.trigeEvent(
					this.userService.getUser(user.getUserName()), ware.getId()
							+ "", UserEventType.EQUIPMENT_SCORE);
		}
		Double voteScore = equipmentUserService.getScoreSum(ware.getId());
		ware.setVoteScore(voteScore);
		this.equipmentService.saveEquipment(ware);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, voteScore);
		return mapping.findForward("message");
	}

	/**
	 * 建立装备和用户的关联
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		User user = this.userService.getUser(null, username);
		String wid = request.getParameter("wid");
		String memo = request.getParameter("memo");
		String type = request.getParameter("type");
		Equipment goodsWare = this.equipmentService.getEquipment(Integer
				.valueOf(wid));
		EquipmentUser model = this.equipmentUserService.getEquipmentUser(
				goodsWare.getId(), user.getUserId());
		if (model == null) {
			model = new EquipmentUser();

		}
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setUid(user.getUserId());
		model.setUsername(user.getUserName());
		model.setMemo(memo);
		model.setType(Integer.valueOf(type));
		model.setWareId(Integer.valueOf(wid));
		equipmentUserService.saveEquipmentUser(model);
		userService.trigeEvent(this.userService.getUser(username), goodsWare
				.getId()
				+ "#" + type, UserEventType.EQUIPMENT_REF);
		return new ActionForward(UrlConstants.EQUIPMENT + goodsWare.getId(),
				true);
	}
}
