package cn.ctba.equipment.service;

import java.util.List;

import org.net9.core.service.BaseService;
import org.net9.domain.dao.ctba.EquipmentCommentDAO;
import org.net9.domain.model.ctba.EquipmentComment;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * 物品评论服务
 * 
 * @author gladstone
 * @since Mar 8, 2009
 */
@SessionScoped
public class EquipmentCommentService extends BaseService {

	private static final long serialVersionUID = 9042323515999257055L;
	@Inject
	private EquipmentCommentDAO equipmentCommentDAO;

	/**
	 * 删除评论
	 * 
	 * @param model
	 */
	public void deleteGoodsComment(EquipmentComment model) {
		equipmentCommentDAO.remove(model);
	}

	/**
	 * 得到评论列表
	 * 
	 * @param username
	 * @param wareId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EquipmentComment> findEquipmentComment(Integer uid,
			Integer wareId, Integer start, Integer limit) {
		String jpql = "select model from EquipmentComment model where model.id>0";
		if (wareId != null) {
			jpql += " and model.goodsWare.id=" + wareId;
		}
		if (uid != null) {
			jpql += " and model.uid = " + uid;
		}
		jpql += " order by model.id ";
		return equipmentCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据接收者用户得到评论列表
	 * 
	 * @param recieverUsername
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EquipmentComment> findEquipmentCommentsByReciever(
			String recieverUsername, Integer start, Integer limit) {
		String jpql = "select model from EquipmentComment model where model.id>0";
		jpql += " and model.goodsWare.username='" + recieverUsername + "'";
		jpql += " order by model.id desc";
		return equipmentCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到评论
	 * 
	 * @param id
	 * @return
	 */
	public EquipmentComment getEquipmentComment(Integer id) {
		return equipmentCommentDAO.findById(id);
	}

	/**
	 * 得到评论数量
	 * 
	 * @param uid
	 * @param wareId
	 * @return
	 */
	public Integer getEquipmentCommentCnt(Integer uid, Integer wareId) {
		String jpql = "select count(model) from EquipmentComment model where model.id>0";
		if (uid != null) {
			jpql += " and model.uid =" + uid;
		}
		if (wareId != null) {
			jpql += " and model.goodsWare.id=" + wareId;
		}
		return equipmentCommentDAO.getCntByStatement(jpql);
	}

	/**
	 * 保存评论
	 * 
	 * @param model
	 */
	public void saveEquipmentComment(EquipmentComment model) {
		if (model.getId() != null) {
			equipmentCommentDAO.update(model);
		} else {
			equipmentCommentDAO.save(model);
		}
	}

}
