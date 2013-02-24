package org.net9.domain.dao.group;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.ActivityModel;

/**
 * Data access object (DAO) for domain model class ActivityModel.
 * 
 * @see org.net9.domain.model.group.ActivityModel
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = ActivityModel.class)
public class ActivityModelDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<ActivityModel> findAll() {
		EntityManagerHelper.log("finding all ActivityModel instances",
				Level.INFO, null);
		try {
			String queryString = "select model from ActivityModel model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ActivityModel findById(Integer id) {
		EntityManagerHelper.log(
				"finding ActivityModel instance with id: " + id, Level.INFO,
				null);
		try {
			ActivityModel instance = getEntityManager().find(
					ActivityModel.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ActivityModel> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log(
				"finding ActivityModel instance with property: " + propertyName
						+ ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from ActivityModel model where model."
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