package org.net9.domain.dao.gallery;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.gallery.ImageModel;

/**
 * Data access object (DAO) for domain model class ImageModel.
 * 
 * @see org.net9.domain.model.gallery.ImageModel
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = ImageModel.class)
public class ImageModelDAO extends CachedJpaDAO {
	// property constants
	public static final String NAME = "name";

	public static final String CREATE_TIME = "createTime";

	public static final String UPDATE_TIME = "updateTime";

	@Override
	@SuppressWarnings("unchecked")
	public List<ImageModel> findAll() {
		EntityManagerHelper.log("finding all ImageModel instances", Level.INFO,
				null);
		try {
			String queryString = "select model from ImageModel model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<ImageModel> findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public ImageModel findById(Integer id) {
		EntityManagerHelper.log("finding ImageModel instance with id: " + id,
				Level.INFO, null);
		try {
			ImageModel instance = getEntityManager().find(ImageModel.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<ImageModel> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	@SuppressWarnings("unchecked")
	public List<ImageModel> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding ImageModel instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from ImageModel model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<ImageModel> findByUpdateTime(Object updateTime) {
		return findByProperty(UPDATE_TIME, updateTime);
	}
}