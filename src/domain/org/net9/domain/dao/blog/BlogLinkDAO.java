package org.net9.domain.dao.blog;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.blog.BlogLink;

/**
 * Data access object (DAO) for domain model class BlogLink.
 * 
 * @see org.net9.domain.model.blog.BlogLink
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = BlogLink.class, useCache = false)
public class BlogLinkDAO extends CachedJpaDAO {

	public BlogLink findById(Integer id) {
		try {
			BlogLink instance = getEntityManager().find(BlogLink.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}