package org.net9.domain.dao.blog;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.blog.Blog;

/**
 * Data access object (DAO) for domain model class BlogBlog.
 * 
 * @see org.net9.domain.model.blog.Blog
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = Blog.class)
public class BlogBlogDAO extends CachedJpaDAO {

	public static final String AUTHOR = "author";

	public static final String NAME = "name";

	@SuppressWarnings("unchecked")
	public List<Blog> findByAuthor(Object author) {
		return findByProperty(AUTHOR, author);
	}

	public Blog findById(Integer id) {
		try {
			Blog instance = (Blog) this.getByPrimaryKey(Blog.class, id);
			// getEntityManager().find(Blog.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

	// @SuppressWarnings("unchecked")
	// public List<Blog> findByName(Object name) {
	// return findByProperty(NAME, name);
	// }
	//
	// @SuppressWarnings("unchecked")
	// public List<Blog> findByProperty(String propertyName, Object value) {
	// try {
	// String queryString = "select model from Blog model where model."
	// + propertyName + "= :propertyValue";
	// return getEntityManager().createQuery(queryString).setParameter(
	// "propertyValue", value).getResultList();
	// } catch (RuntimeException re) {
	// EntityManagerHelper.log("find by property name failed",
	// Level.SEVERE, re);
	// throw re;
	// }
	// }

}