package cn.ctba.equipment.web.struts.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.AjaxResponse;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.ImageUtils;
import org.net9.core.web.struts.action.BusinessDispatchAction;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.EquipmentComment;

import cn.ctba.equipment.EquipmentConstant;
import cn.ctba.equipment.EquipmentStateType;
import cn.ctba.equipment.service.EquipmentCommentService;
import cn.ctba.equipment.service.EquipmentService;
import cn.ctba.equipment.web.struts.form.WareForm;

import com.google.inject.Inject;

/**
 * 装备的管理action
 * 
 * @author gladstone
 * @since 2008-8-3
 */
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_ADMIN)
public class ManageAction extends BusinessDispatchAction {

	@Inject
	private EquipmentService equipmentService;
	@Inject
	private EquipmentCommentService equipmentCommentService;

	/**
	 * 刪除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid = request.getParameter("wid");
		Equipment model = equipmentService.getEquipment(Integer.parseInt(wid));
		if (model != null) {
			equipmentService.deleteEquipment(model);
		}
		return new ActionForward("equipmentManage.shtml?method=listEquipments",
				true);
	}

	/**
	 * 刪除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cid = request.getParameter("cid");
		EquipmentComment model = equipmentCommentService
				.getEquipmentComment(Integer.parseInt(cid));
		if (model != null) {
			equipmentCommentService.deleteGoodsComment(model);
		}
		return new ActionForward("equipmentManage.shtml?method=listComments",
				true);
	}

	/**
	 * 商品表单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
		return mapping.findForward("ware.form");
	}

	/**
	 * 评论列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listComments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String widStr = request.getParameter("wid");
		Integer wid = null;
		if (CommonUtils.isNotEmpty(widStr)) {
			wid = new Integer(widStr);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		List<EquipmentComment> models = equipmentCommentService
				.findEquipmentComment(null, wid, start, limit);
		request.setAttribute("models", models);
		request.setAttribute("count", equipmentCommentService
				.getEquipmentCommentCnt(null, wid));
		return mapping.findForward("ware.list");
	}

	/**
	 * 物品列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listEquipments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String typeStr = request.getParameter("t");
		String state = request.getParameter("state");
		Integer type = null;
		if (CommonUtils.isNotEmpty(typeStr)) {
			type = new Integer(typeStr);
		}
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_30;
		EquipmentStateType equipmentStateType = null;
		if ("1".equals(state)) {
			equipmentStateType = EquipmentStateType.OK;
		} else if ("0".equals(state)) {
			equipmentStateType = EquipmentStateType.WAITING;
		} else if ("2".equals(state)) {
			equipmentStateType = EquipmentStateType.COMMENDED;
		}
		List<Equipment> models = equipmentService.findEquipments(
				equipmentStateType, null, null, type, start, limit);
		request.setAttribute("models", models);
		request.setAttribute("count", equipmentService.getEquipmentCnt(
				equipmentStateType, null, null, null, type));
		return mapping.findForward("ware.list");
	}

	/**
	 * 装备状态 #779 (装备秀改进 - 其他)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AjaxResponse
	public ActionForward toggleState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid = request.getParameter("wid");
		// String state = request.getParameter("state");
		Equipment ware = this.equipmentService.getEquipment(Integer
				.valueOf(wid));
		if (EquipmentStateType.OK.getValue().equals(ware.getState())) {
			ware.setState(EquipmentStateType.WAITING.getValue());
		} else {
			ware.setState(EquipmentStateType.OK.getValue());
		}
		this.equipmentService.saveEquipment(ware);
		request.setAttribute(BusinessConstants.AJAX_MESSAGE, ware.getState());
		return mapping.findForward("message");
	}

	/**
	 * 保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String wid = request.getParameter("wid");
		String adminUsername = (String) request.getSession().getAttribute(
				BusinessConstants.ADMIN_NAME);
		Equipment model;

		WareForm wareForm = (WareForm) form;
		FormFile imageFile = wareForm.getImageFile();
		if (StringUtils.isNotEmpty(wid)) {
			model = equipmentService.getEquipment(Integer.parseInt(wid));
		} else {
			model = new Equipment();
			model.setCreateTime(DateUtils.getNow());
			model.setHits(0);
			model.setUsername(adminUsername);
		}
		model.setUpdateTime(DateUtils.getNow());
		PropertyUtil.copyProperties(model, wareForm);
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
			ImageUtils.getDefaultImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), false);
			ImageUtils.getMiniImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), false);
			ImageUtils.getSizedImage(request.getSession().getServletContext()
					.getRealPath(fileDir + File.separator + filename), 32);
			model.setImage(filePath);
		}

		equipmentService.saveEquipment(model);
		return new ActionForward("equipmentManage.shtml?method=listWares", true);
	}
}
