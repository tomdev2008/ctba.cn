package org.net9.domain.dao.group;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.GroupUser;

/**
 * Data access object (DAO) for domain model class GroupUser.
 * 
 * @see org.net9.domain.model.group.GroupUser
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = GroupUser.class)
public class GroupUserDAO extends CachedJpaDAO {
	// property constants
	public static final String ROLE = "role";

	public static final String DELETE_FLAG = "deleteFlag";

	@Override
	@SuppressWarnings("unchecked")
	public List<GroupUser> findAll() {
		EntityManagerHelper.log("finding all GroupUser instances", Level.INFO,
				null);
		try {
			String queryString = "select model from GroupUser model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<GroupUser> findByDeleteFlag(Object deleteFlag) {
		return findByProperty(DELETE_FLAG, deleteFlag);
	}

	public GroupUser findById(Integer id) {
		EntityManagerHelper.log("finding GroupUser instance with id: " + id,
				Level.INFO, null);
		try {
			GroupUser instance = getEntityManager().find(GroupUser.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<GroupUser> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding GroupUser instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from GroupUser model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<GroupUser> findByRole(Object role) {
		return findByProperty(ROLE, role);
	}
}