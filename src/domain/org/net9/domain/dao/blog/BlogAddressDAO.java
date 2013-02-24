package org.net9.domain.dao.blog;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.blog.BlogAddress;

/**
 * Data access object (DAO) for domain model class BlogAddress.
 * 
 * @see org.net9.domain.model.blog.BlogAddress
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = BlogAddress.class)
public class BlogAddressDAO extends CachedJpaDAO {

	public BlogAddress findById(Integer id) {
		try {
			BlogAddress instance = getEntityManager().find(BlogAddress.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}