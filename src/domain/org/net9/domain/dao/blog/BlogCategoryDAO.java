package org.net9.domain.dao.blog;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.blog.BlogCategory;

/**
 * Data access object (DAO) for domain model class BlogCategory.
 * 
 * @see org.net9.domain.model.blog.BlogCategory
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = BlogCategory.class)
public class BlogCategoryDAO extends CachedJpaDAO {

	public BlogCategory findById(Integer id) {
		try {
			BlogCategory instance = getEntityManager().find(BlogCategory.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

}