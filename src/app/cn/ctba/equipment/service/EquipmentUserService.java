package cn.ctba.equipment.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.net9.core.service.BaseService;
import org.net9.domain.dao.ctba.EquipmentScoreDAO;
import org.net9.domain.dao.ctba.EquipmentUserDAO;
import org.net9.domain.model.ctba.EquipmentScore;
import org.net9.domain.model.ctba.EquipmentUser;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * 装备用户相关服务
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class EquipmentUserService extends BaseService {

	private static final long serialVersionUID = 8800878286044448933L;

	@Inject
	private EquipmentUserDAO equipmentUserDAO;

	@Inject
	private EquipmentScoreDAO equipmentScoreDAO;

	/**
	 * 根据用户的uid和装备id得到用户和装备的关联
	 * 
	 * @param wid
	 * @param userId
	 * @return
	 */
	public EquipmentUser getEquipmentUser(Integer wid, Integer userId) {
		String jpql = "select model from EquipmentUser model where model.uid="
				+ userId + " and model.wareId=" + wid;
		return (EquipmentUser) equipmentUserDAO.findSingleByStatement(jpql);
	}

	/**
	 * 根据物品得到关联的用户
	 * 
	 * @param wid
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EquipmentUser> findEquipmentUsers(Integer wid, Integer start,
			Integer limit) {
		String jpql = "select model from EquipmentUser model where model.wareId="
				+ wid + " order by model.id desc";
		return equipmentUserDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据物品得到关联的用户数量
	 * 
	 * @param wid
	 * @return
	 */
	public Integer getEquipmentUsersCnt(Integer wid) {
		String jpql = "select count(model) from EquipmentUser model where model.wareId="
				+ wid;
		return equipmentUserDAO.getCntByStatement(jpql);
	}

	public Integer getEquipmentUsersCntByUsername(String username, Integer type) {
		String jpql = "select count(model) from EquipmentUser model where model.username='"
				+ username + "'";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		return equipmentUserDAO.getCntByStatement(jpql);
	}

	/**
	 * 保存装备和用户关系
	 * 
	 * @param model
	 */
	public void saveEquipmentUser(EquipmentUser model) {
		if (model.getId() != null) {
			equipmentUserDAO.update(model);
		} else {
			equipmentUserDAO.save(model);
		}
	}

	/**
	 * 删除装备和用户关系
	 * 
	 * @param model
	 */
	public void deleteEquipmentUser(EquipmentUser model) {
		equipmentUserDAO.remove(model);
	}

	/**
	 * 得到所有打分
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EquipmentScore> findEquipmentScores(Integer start, Integer limit) {
		String jpql = "select model from EquipmentScore model";
		return equipmentScoreDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到总分
	 * 
	 * @param wareId
	 * @return
	 */
	public Double getScoreSum(Integer wareId) {
		String jpql = "select avg(model.value) from EquipmentScore model where model.wareId="
				+ wareId;
		Double reval = (Double) equipmentScoreDAO.findSingleByStatement(jpql);
		NumberFormat format = new DecimalFormat("#0.0");
		if (reval != null) {
			return Double.valueOf(format.format(reval.doubleValue()));
		} else {
			return reval;
		}

	}

	/**
	 * 保存打分
	 * 
	 * @param model
	 */
	public void saveEquipmentScore(EquipmentScore model) {
		if (model.getId() != null) {
			equipmentScoreDAO.update(model);
		} else {
			equipmentScoreDAO.save(model);
		}
	}

}
