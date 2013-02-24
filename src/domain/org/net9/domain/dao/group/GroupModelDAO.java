package org.net9.domain.dao.group;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.GroupModel;

/**
 * Data access object (DAO) for domain model class GroupModel.
 * 
 * @see org.net9.domain.model.group.GroupModel
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = GroupModel.class)
public class GroupModelDAO extends CachedJpaDAO {
	// property constants
	// public static final String NAME = "name";
	//
	// public static final String CREATE_TIME = "createTime";
	//
	// public static final String UPDATE_TIME = "updateTime";
	//
	// public static final String MANAGER = "manager";
	//
	// public static final String DISCRIPTION = "discription";
	//
	// public static final String FACE = "face";

	//
	// @Override
	// @SuppressWarnings("unchecked")
	// public List<GroupModel> findAll() {
	// EntityManagerHelper.log("finding all GroupModel instances", Level.INFO,
	// null);
	// try {
	// String queryString = "select model from GroupModel model";
	// return getEntityManager().createQuery(queryString).getResultList();
	// } catch (RuntimeException re) {
	// EntityManagerHelper.log("find all failed", Level.SEVERE, re);
	// throw re;
	// }
	// }

	// public List<GroupModel> findByCreateTime(Object createTime) {
	// return findByProperty(CREATE_TIME, createTime);
	// }
	//
	// public List<GroupModel> findByDiscription(Object discription) {
	// return findByProperty(DISCRIPTION, discription);
	// }
	//
	// public List<GroupModel> findByFace(Object face) {
	// return findByProperty(FACE, face);
	// }

	public GroupModel findById(Integer id) {
		EntityManagerHelper.log("finding GroupModel instance with id: " + id,
				Level.INFO, null);
		try {
			GroupModel instance = (GroupModel) this.getByPrimaryKey(
					GroupModel.class, id);
			// getEntityManager().find(GroupModel.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	// public List<GroupModel> findByManager(Object manager) {
	// return findByProperty(MANAGER, manager);
	// }
	//
	// public List<GroupModel> findByName(Object name) {
	// return findByProperty(NAME, name);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public List<GroupModel> findByProperty(String propertyName, Object value)
	// {
	// EntityManagerHelper.log("finding GroupModel instance with property: "
	// + propertyName + ", value: " + value, Level.INFO, null);
	// try {
	// String queryString = "select model from GroupModel model where model."
	// + propertyName + "= :propertyValue";
	// return getEntityManager().createQuery(queryString).setParameter(
	// "propertyValue", value).getResultList();
	// } catch (RuntimeException re) {
	// EntityManagerHelper.log("find by property name failed",
	// Level.SEVERE, re);
	// throw re;
	// }
	// }
	//
	// public List<GroupModel> findByUpdateTime(Object updateTime) {
	// return findByProperty(UPDATE_TIME, updateTime);
	// }
}