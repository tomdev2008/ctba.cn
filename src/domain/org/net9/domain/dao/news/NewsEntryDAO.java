package org.net9.domain.dao.news;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.news.NewsEntry;

/**
 * Data access object (DAO) for domain model class NewsEntry.
 * 
 * @see org.net9.domain.model.news.NewsEntry
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = NewsEntry.class)
public class NewsEntryDAO extends CachedJpaDAO {
	// property constants
	public static final String TITLE = "title";

	public static final String SUBTITLE = "subtitle";

	public static final String PERMALINK = "permalink";

	public static final String CONTENT = "content";

	public static final String AUTHOR = "author";

	public static final String MANAGER = "manager";

	public static final String HITS = "hits";

	public static final String TAGS = "tags";

	public static final String STATE = "state";

	public static final String CREATE_TIME = "createTime";

	public static final String UPDATE_TIME = "updateTime";

	public static final String COMMENT_FLG = "commentFlg";

	public static final String HIT_GOOD = "hitGood";

	public static final String HIT_BAD = "hitBad";

	@Override
	@SuppressWarnings("unchecked")
	public List<NewsEntry> findAll() {
		EntityManagerHelper.log("finding all NewsEntry instances", Level.INFO,
				null);
		try {
			String queryString = "select model from NewsEntry model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsEntry> findByAuthor(Object author) {
		return findByProperty(AUTHOR, author);
	}

	public List<NewsEntry> findByCommentFlg(Object commentFlg) {
		return findByProperty(COMMENT_FLG, commentFlg);
	}

	public List<NewsEntry> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List<NewsEntry> findByCreateTime(Object createTime) {
		return findByProperty(CREATE_TIME, createTime);
	}

	public List<NewsEntry> findByHitBad(Object hitBad) {
		return findByProperty(HIT_BAD, hitBad);
	}

	public List<NewsEntry> findByHitGood(Object hitGood) {
		return findByProperty(HIT_GOOD, hitGood);
	}

	public List<NewsEntry> findByHits(Object hits) {
		return findByProperty(HITS, hits);
	}

	public NewsEntry findById(Integer id) {
		EntityManagerHelper.log("finding NewsEntry instance with id: " + id,
				Level.INFO, null);
		try {
			NewsEntry instance = getEntityManager().find(NewsEntry.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsEntry> findByManager(Object manager) {
		return findByProperty(MANAGER, manager);
	}

	public List<NewsEntry> findByPermalink(Object permalink) {
		return findByProperty(PERMALINK, permalink);
	}

	@SuppressWarnings("unchecked")
	public List<NewsEntry> findByProperty(String propertyName, Object value) {
		EntityManagerHelper.log("finding NewsEntry instance with property: "
				+ propertyName + ", value: " + value, Level.INFO, null);
		try {
			String queryString = "select model from NewsEntry model where model."
					+ propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	public List<NewsEntry> findByState(Object state) {
		return findByProperty(STATE, state);
	}

	public List<NewsEntry> findBySubtitle(Object subtitle) {
		return findByProperty(SUBTITLE, subtitle);
	}

	public List<NewsEntry> findByTags(Object tags) {
		return findByProperty(TAGS, tags);
	}

	public List<NewsEntry> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<NewsEntry> findByUpdateTime(Object updateTime) {
		return findByProperty(UPDATE_TIME, updateTime);
	}
}