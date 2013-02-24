package cn.ctba.equipment.service;

import java.text.ParseException;
import java.util.List;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.dao.ctba.EquipmentDAO;
import org.net9.domain.model.ctba.Equipment;

import cn.ctba.equipment.EquipmentStateType;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * 装备服务
 * 
 * @author gladstone
 */
@SessionScoped
public class EquipmentService extends BaseService {

	private static final long serialVersionUID = -463772037921048891L;
	@Inject
	private EquipmentDAO equipmentDAO;

	/**
	 * 删除装备
	 * 
	 * @param model
	 */
	public void deleteEquipment(Equipment model) {
		equipmentDAO.remove(model);
	}

	/**
	 * 得到装备列表
	 * 
	 * @param name
	 * @param username
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Equipment> findEquipments(EquipmentStateType state,
			String name, String username, Integer type, Integer start,
			Integer limit) {
		String jpql = "select model from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (StringUtils.isNotEmpty(name)) {
			jpql += " and model.name like '%" + name + "%'";
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.id desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Equipment> findEquipmentsByBrand(EquipmentStateType state,
			String brand, Integer type, Integer start, Integer limit) {
		String jpql = "select model from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (StringUtils.isNotEmpty(brand)) {
			jpql += " and model.brand='" + brand + "'";
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.id desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Equipment> findEquipmentsByCategoryId(EquipmentStateType state,
			Integer categoryId, Integer start, Integer limit) {
		String jpql = "select model from Equipment model where model.id>0 and model.shopId>0 ";
		if (categoryId != null) {
			jpql += " and model.categoryId=" + categoryId;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.id desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据点击量排序
	 * 
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Equipment> findEquipmentsByHits(EquipmentStateType state,
			String username, Integer type, Integer start, Integer limit) {
		String jpql = "select model from Equipment model where model.id>0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.voteScore desc, model.hits desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Equipment> findEquipmentsByShopId(EquipmentStateType state,
			Integer shopId, Integer start, Integer limit) {
		String jpql = "select model from Equipment model where model.id>0";
		if (shopId != null) {
			jpql += " and model.shopId=" + shopId;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.id desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Equipment> findEquipmentsWithCategory(EquipmentStateType state,
			String username, Integer categoryId, Integer start, Integer limit) {
		String jpql = "select model from Equipment model where model.id>0";
		if (categoryId != null) {
			jpql += " and model.categoryId=" + categoryId;
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.id desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 查找相关装备
	 * 
	 * @param wid
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Equipment> findRefEquipments(EquipmentStateType state,
			Integer wid, Integer type, Integer start, Integer limit) {
		String jpql = "select model from Equipment model where model.id>0";
		jpql += " and model.type=" + type;
		jpql += " and model.id<>" + wid;
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		jpql += " order by model.id desc";
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<Equipment> findRefEquipmentsByUsername(String username,
			Integer type, Integer start, Integer limit) {
		String jpql = "select model from EquipmentUser ref,Equipment model where ref.username='"
				+ username + "' and  ref.wareId=model.id and ref.type=" + type;
		return equipmentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到单个装备
	 * 
	 * @param id
	 * @return
	 */
	public Equipment getEquipment(Integer id) {
		return equipmentDAO.findById(id);
	}

	/**
	 * 得到数量
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public Integer getEquipmentCnt(EquipmentStateType state, String name,
			String username, Integer uid, Integer type) {
		String jpql = "select count(model) from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (StringUtils.isNotEmpty(name)) {
			jpql += " and model.name like '%" + name + "%'";
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (uid != null) {
			jpql += " and model.uid=" + uid;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		return equipmentDAO.getCntByStatement(jpql);
	}

	public Integer getEquipmentCntByDate(EquipmentStateType state, String date,
			Integer type) {
		String jpql = "select count(model) from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}

		if (StringUtils.isNotEmpty(date)) {
			try {
				jpql += " and model.updateTime>'" + date
						+ "' and model.updateTime<'"
						+ CommonUtils.getDateFromDateOn(date, 1) + "'";
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return equipmentDAO.getCntByStatement(jpql);
	}

	public Integer getEquipmentCntWithCategoryId(EquipmentStateType state,
			String username, Integer categoryId) {
		String jpql = "select count(model) from Equipment model where model.id>0";
		if (categoryId != null) {
			jpql += " and model.categoryId=" + categoryId;
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		return equipmentDAO.getCntByStatement(jpql);
	}

	public Integer getEquipmentsCntByBrand(EquipmentStateType state,
			String brand, Integer type) {
		String jpql = "select count(model) from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (StringUtils.isNotEmpty(brand)) {
			jpql += " and model.brand='" + brand + "'";
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		return equipmentDAO.getCntByStatement(jpql);
	}

	public Integer getEquipmentsCntByCategoryId(EquipmentStateType state,
			Integer categoryId) {
		String jpql = "select count(model) from Equipment model where model.id>0 and model.shopId>0 ";
		if (categoryId != null) {
			jpql += " and model.categoryId=" + categoryId;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		return equipmentDAO.getCntByStatement(jpql);
	}

	public Integer getEquipmentsCntByShopId(EquipmentStateType state,
			Integer shopId) {
		String jpql = "select count(model) from Equipment model where model.id>0";
		if (shopId != null) {
			jpql += " and model.shopId=" + shopId;
		}
		if (state != null) {
			jpql += " and model.state =" + state.getValue();
		}
		return equipmentDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到下一个装备
	 * 
	 * @param wid
	 *            必须
	 * @param username
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Equipment getNextEquipment(EquipmentStateType state, Integer wid,
			String username, Integer type) {
		String jpql = "select model from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		jpql += " and model.id <" + wid;
		jpql += " order by model.id desc";
		List<Equipment> list = equipmentDAO.findByStatement(jpql, 0, 1);
		if (list.size() > 0) {
			return list.get(0);
		}
		list = this.findEquipments(state, null, null, type, 0, 1);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 得到上一个装备
	 * 
	 * @param wid
	 *            必须
	 * @param username
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Equipment getPrevEquipment(EquipmentStateType state, Integer wid,
			String username, Integer type) {
		String jpql = "select model from Equipment model where model.id>0";
		if (type != null) {
			jpql += " and model.type=" + type;
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		jpql += " and model.id >" + wid;
		jpql += " order by model.id asc";
		List<Equipment> list = equipmentDAO.findByStatement(jpql, 0, 1);
		if (list.size() > 0) {
			return list.get(0);
		}
		list = this.findEquipments(state, null, null, type, 0, 1);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 保存装备
	 * 
	 * @param model
	 */
	public void saveEquipment(Equipment model) {
		if (model.getId() != null) {
			equipmentDAO.update(model);
		} else {
			equipmentDAO.save(model);
		}
	}
}
