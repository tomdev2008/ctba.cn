package cn.ctba.equipment.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.dao.ctba.ShopCategoryDAO;
import org.net9.domain.dao.ctba.ShopCommendDAO;
import org.net9.domain.dao.ctba.ShopModelDAO;
import org.net9.domain.model.ctba.ShopCategory;
import org.net9.domain.model.ctba.ShopCommend;
import org.net9.domain.model.ctba.ShopModel;

import com.google.inject.Inject;

/**
 * #775 (装备秀改进 - 商铺功能)
 * 
 * @author gladstone
 * 
 */
public class ShopService extends BaseService {
	private static final long serialVersionUID = 8313054074760691236L;
	@Inject
	private ShopModelDAO shopModelDAO;
	@Inject
	private ShopCommendDAO shopCommendDAO;
	@Inject
	private ShopCategoryDAO shopCategoryDAO;

	/**
	 * 删除分类
	 * 
	 * @param model
	 */
	public void deleteShopCategory(ShopCategory model) {
		shopCategoryDAO.remove(model);
	}

	public void deleteShopCommend(ShopCommend model) {
		shopCommendDAO.remove(model);
	}

	/**
	 * 得到分类列表
	 * 
	 * @param name
	 * @param username
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ShopCategory> findShopCategories(Integer shopId, Integer start,
			Integer limit) {
		String jpql = "select model from ShopCategory model where model.id>0";
		if (shopId != null) {
			jpql += " and model.shopModel.id=" + shopId;
		}
		jpql += " order by model.idx desc";
		return shopCategoryDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ShopModel> findShops(Integer start, Integer limit) {
		String jpql = "select model from ShopModel model where model.id>0";
		jpql += " order by model.id desc";
		return shopModelDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ShopCommend> findShopCommendsByShopId(Integer shopId,
			Integer commendType, Integer start, Integer limit) {
		String jpql = "select model from ShopCommend model where model.id>0";
		if (shopId != null) {
			jpql += " and model.shopModel.id=" + shopId;
		}
		if (commendType != null) {
			jpql += " and model.type=" + commendType;
		}
		jpql += " order by model.id desc";
		return shopCommendDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ShopCommend> findShopCommendsByEquipmentId(Integer equipmentId,
			Integer start, Integer limit) {
		String jpql = "select model from ShopCommend model where model.id>0";
		if (equipmentId != null) {
			jpql += " and model.equipment.id=" + equipmentId;
		}
		jpql += " order by model.id desc";
		return shopCommendDAO.findByStatement(jpql, start, limit);
	}

	public ShopCommend getShopCommendByEquipmentIdAndShopId(
			Integer equipmentId, Integer shopId) {
		String jpql = "select model from ShopCommend model where model.id>0";
		jpql += " and model.equipment.id=" + equipmentId;
		jpql += " and model.shopModel.id=" + shopId;
		jpql += " order by model.id desc";
		return (ShopCommend) shopCommendDAO.findSingleByStatement(jpql);
	}

	public ShopCommend getShopCommendById(Integer id) {
		return (ShopCommend) shopCommendDAO.findById(id);
	}

	/**
	 * 得到单个分类
	 * 
	 * @param id
	 * @return
	 */
	public ShopCategory getShopCategory(Integer id) {
		return shopCategoryDAO.findById(id);
	}

	/**
	 * 得到排名比自己高的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ShopCategory getNextCategoryIdx(ShopCategory c) {
		String jpql = "select model  from ShopCategory model where model.shopModel.id="
				+ c.getShopModel().getId()
				+ " and model.idx>"
				+ c.getIdx()
				+ " order by model.idx asc";
		List l = shopCategoryDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (ShopCategory) l.get(0) : null;
	}

	/**
	 * 得到排名比自己低的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ShopCategory getPrevCategoryIdx(ShopCategory c) {
		String jpql = "select  model  from ShopCategory model where model.shopModel.id="
				+ c.getShopModel().getId()
				+ " and model.idx<"
				+ c.getIdx()
				+ " order by model.idx desc";
		List l = shopCategoryDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (ShopCategory) l.get(0) : null;
	}

	public ShopModel getShopModel(Integer id) {
		return shopModelDAO.findById(id);
	}

	@SuppressWarnings("unchecked")
	public ShopModel getShopModelByUsername(String username) {
		String jpql = "select model from ShopModel model where model.id>0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		List<ShopModel> sList = shopModelDAO.findByStatement(jpql, 0, 1);
		return sList.size() > 0 ? sList.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public ShopModel getShopModelByManageLoginUser(String username,
			String password) {
		String jpql = "select model from ShopModel model where model.id>0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		List<ShopModel> sList = shopModelDAO.findByStatement(jpql, 0, 1);
		return sList.size() > 0 ? sList.get(0) : null;
	}

	/**
	 * 得到分类数量
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public Integer getShopCategoryCnt(Integer shopId) {
		String jpql = "select count(model) from ShopCategory model where model.id>0";
		if (shopId != null) {
			jpql += " and model.shopModel.id=" + shopId;
		}
		return shopCategoryDAO.getCntByStatement(jpql);
	}

	public Integer getShopCnt() {
		String jpql = "select count(model) from ShopModel model where model.id>0";
		return shopCategoryDAO.getCntByStatement(jpql);
	}

	/**
	 * 保存分类
	 * 
	 * @param model
	 */
	public void saveShopCategory(ShopCategory model) {
		if (model.getId() != null) {
			shopCategoryDAO.update(model);
		} else {
			shopCategoryDAO.save(model);
		}
	}

	public void saveShopModel(ShopModel model) {
		if (model.getId() != null) {
			shopModelDAO.update(model);
		} else {
			shopModelDAO.save(model);
		}
	}

	public void saveShopCommend(ShopCommend model) {
		if (model.getId() != null) {
			shopCommendDAO.update(model);
		} else {
			shopCommendDAO.save(model);
		}
	}

	public ShopCommend getShopCommend(Integer id) {
		return shopCommendDAO.findById(id);
	}
}
