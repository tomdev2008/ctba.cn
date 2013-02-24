package org.net9.domain.dao.blog;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.blog.BlogEntry;

/**
 * Data access object (DAO) for domain model class BlogBlogentry.
 * 
 * @see org.net9.domain.model.blog.BlogEntry
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = BlogEntry.class)
public class BlogBlogentryDAO extends CachedJpaDAO {

	public BlogEntry findById(Integer id) {
		try {
			BlogEntry instance = (BlogEntry) this.getByPrimaryKey(
					BlogEntry.class, id);// getEntityManager().find(BlogEntry.class,
											// id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

}
