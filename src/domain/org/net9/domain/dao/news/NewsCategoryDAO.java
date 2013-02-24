package org.net9.domain.dao.news;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.news.NewsCategory;

/**
 * Data access object (DAO) for domain model class NewsCategory.
 * 
 * @see org.net9.domain.model.news.NewsCategory
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = NewsCategory.class)
public class NewsCategoryDAO extends CachedJpaDAO {
	// property constants
	public static final String NAME = "name";

	public static final String CODE = "code";

	public static final String CREATE_TIME = "createTime";

	public static final String UPDATE_TIME = "updateTime";

	@SuppressWarnings("unchecked")
	public List<NewsCategory> findAll() {
		EntityManagerHelper.log("finding all NewsCategory instances",
				Level.INFO, null);
		try {
			String queryString = "select model from NewsCategory model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsCategory> findByCode(Object code) {
		return findByProperty(CODE, code);
	}

	public List<NewsCategory> findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public NewsCategory findById(Integer id) {
		EntityManagerHelper.log("finding NewsCategory instance with id: " + id,
				Level.INFO, null);
		try {
			NewsCategory instance = getEntityManager().find(NewsCategory.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsCategory> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	@SuppressWarnings("unchecked")
	public List<NewsCategory> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding NewsCategory instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from NewsCategory model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsCategory> findByUpdateTime(Object updateTime) {
		return findByProperty(UPDATE_TIME, updateTime);
	}
}