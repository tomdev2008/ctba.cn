package org.net9.domain.dao.news;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.news.NewsPost;

/**
 * Data access object (DAO) for domain model class NewsPost.
 * 
 * @see org.net9.domain.model.news.NewsPost
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = NewsPost.class)
public class NewsPostDAO extends CachedJpaDAO {
	// property constants
	public static final String AUTHOR = "author";

	public static final String TITLE = "title";

	public static final String CONTENT = "content";

	public static final String CREATE_TIME = "createTime";

	public static final String UPDATE_TIME = "updateTime";

	public static final String IP = "ip";

	@Override
	@SuppressWarnings("unchecked")
	public List<NewsPost> findAll() {
		EntityManagerHelper.log("finding all NewsPost instances", Level.INFO,
				null);
		try {
			String queryString = "select model from NewsPost model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsPost> findByAuthor(Object author) {
		return findByProperty(AUTHOR, author);
	}

	public List<NewsPost> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<NewsPost> findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public NewsPost findById(Integer id) {
		EntityManagerHelper.log("finding NewsPost instance with id: " + id,
				Level.INFO, null);
		try {
			NewsPost instance = getEntityManager().find(NewsPost.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsPost> findByIp(Object ip) {
		return findByProperty(IP, ip);
	}

	@SuppressWarnings("unchecked")
	public List<NewsPost> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding NewsPost instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from NewsPost model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsPost> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<NewsPost> findByUpdateTime(Object updateTime) {
		return findByProperty(UPDATE_TIME, updateTime);
	}
}