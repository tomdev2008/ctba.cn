package org.net9.domain.dao.news;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.news.NewsComment;

/**
 * Data access object (DAO) for domain model class NewsComment.
 * 
 * @see org.net9.domain.model.news.NewsComment
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = NewsComment.class)
public class NewsCommentDAO extends CachedJpaDAO {
	// property constants
	public static final String TITLE = "title";

	public static final String CONTENT = "content";

	public static final String NEWS_ID = "newsId";

	public static final String AUTHOR = "author";

	public static final String IP = "ip";

	public static final String CREATE_TIME = "createTime";

	public static final String UPDATE_TIME = "updateTime";

	public static final String HIT_BAD = "hitBad";

	public static final String HIT_GOOD = "hitGood";

	@Override
	@SuppressWarnings("unchecked")
	public List<NewsComment> findAll() {
		EntityManagerHelper.log("finding all NewsComment instances",
				Level.INFO, null);
		try {
			String queryString = "select model from NewsComment model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsComment> findByAuthor(Object author) {
		return findByProperty(AUTHOR, author);
	}

	public List<NewsComment> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<NewsComment> findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List<NewsComment> findByHitBad(Object hitBad) {
		return findByProperty(HIT_BAD, hitBad);
	}

	public List<NewsComment> findByHitGood(Object hitGood) {
		return findByProperty(HIT_GOOD, hitGood);
	}

	public NewsComment findById(Integer id) {
		EntityManagerHelper.log("finding NewsComment instance with id: " + id,
				Level.INFO, null);
		try {
			NewsComment instance = getEntityManager().find(NewsComment.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsComment> findByIp(Object ip) {
		return findByProperty(IP, ip);
	}

	public List<NewsComment> findByNewsId(Object newsId) {
		return findByProperty(NEWS_ID, newsId);
	}

	@SuppressWarnings("unchecked")
	public List<NewsComment> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding NewsComment instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from NewsComment model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsComment> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<NewsComment> findByUpdateTime(Object updateTime) {
		return findByProperty(UPDATE_TIME, updateTime);
	}
}