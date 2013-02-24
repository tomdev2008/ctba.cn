package org.net9.domain.dao.blog;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.blog.BlogComment;

/**
 * Data access object (DAO) for domain model class BlogComment.
 * 
 * @see org.net9.domain.model.blog.BlogComment
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = BlogComment.class)
public class BlogCommentDAO extends CachedJpaDAO {

	public BlogComment findById(Long id) {
		try {
			BlogComment instance = getEntityManager().find(BlogComment.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

}