package org.net9.domain.dao.group;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.ActivityUser;

/**
 * Data access object (DAO) for domain model class ActivityUser.
 * 
 * @see org.net9.domain.model.group.ActivityUser
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = ActivityUser.class)
public class ActivityUserDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<ActivityUser> findAll() {
		EntityManagerHelper.log("finding all ActivityUser instances",
				Level.INFO, null);
		try {
			String queryString = "select model from ActivityUser model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ActivityUser findById(Integer id) {
		EntityManagerHelper.log("finding ActivityUser instance with id: " + id,
				Level.INFO, null);
		try {
			ActivityUser instance = getEntityManager().find(ActivityUser.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ActivityUser> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding ActivityUser instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from ActivityUser model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}
}