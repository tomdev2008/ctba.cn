package org.net9.domain.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.SystemConfigUtils;
import org.net9.domain.dao.annotation.EntityClass;

/**
 * 
 * the base access obj of jpa
 * 
 * @author gladstone
 * 
 */
public abstract class BaseJpaDAO {

	private static Log logger = LogFactory.getLog(BaseJpaDAO.class);

	public static boolean isConnectingToTestDb = SystemConfigUtils
			.getBoolean("db.test");

	/**
	 * execute a statement in sql
	 * 
	 * @param sql
	 */
	public void exquteNativeStatement(String sql) {
		try {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
			em.close();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("execute statement failed", Level.SEVERE,
					re);
			throw re;
		}

	}

	/**
	 * execute a jpql statement
	 * 
	 * @param jpql
	 */
	public void exquteStatement(String jpql) {
		try {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();
			em.createQuery(jpql).executeUpdate();
			em.getTransaction().commit();
			em.close();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("execute statement failed", Level.SEVERE,
					re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	public List findAll() {
		try {
			String queryString = "select model from " + this.getEntityName()
					+ " model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public List findByNativeStatement(String sql) {
		try {
			EntityManager em = getEntityManager();
			Query q = em.createNativeQuery(sql);
			List l = q.getResultList();
			em.close();
			return l;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by sql failed", Level.SEVERE, re);
			throw re;
		}
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public List findByNativeStatement(String sql, Integer start, Integer limit) {
		try {
			EntityManager em = getEntityManager();
			Query q = em.createNativeQuery(sql);
			q.setFirstResult(start);
			q.setMaxResults(limit);
			List l = q.getResultList();
			em.close();
			return l;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by sql failed", Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * find a list by property in a table
	 * 
	 * @param tableName
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByProperty(String propertyName, Object value) {
		try {
			String queryString = "select model from " + this.getEntityName()
					+ " model where model." + propertyName + "= :propertyValue";
			return getEntityManager().createQuery(queryString).setParameter(
					"propertyValue", value).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by property name failed",
					Level.SEVERE, re);
			throw re;
		}
	}

	/**
	 * get list by statement
	 * 
	 * @param jpql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByStatement(String jpql) {
		// EntityManagerHelper.log("finding instance with statement: " + jpql,
		// Level.INFO, null);
		try {
			EntityManager em = getEntityManager();
			Query q = em.createQuery(jpql);
			List l = q.getResultList();
			em.close();
			return l;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by sql failed", Level.SEVERE, re);
			throw re;
		}

	}

	/**
	 * get list by statement
	 * 
	 * @param jpql
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByStatement(String jpql, Integer start, Integer limit) {
		// EntityManagerHelper.log("finding instance with statement: " + jpql,
		// Level.INFO, null);
		try {
			EntityManager em = getEntityManager();
			Query q = em.createQuery(jpql);
			q.setFirstResult(start);
			q.setMaxResults(limit);
			List l = q.getResultList();
			em.close();
			return l;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by sql failed", Level.SEVERE, re);
			throw re;
		}
	}

	@Deprecated
	public Object findSingleByNativeStatement(String sql) {
		// EntityManagerHelper.log("finding instance with statement: " + sql,
		// Level.INFO, null);
		try {
			EntityManager em = getEntityManager();
			Query q = em.createNativeQuery(sql);
			Object o = q.getSingleResult();
			em.close();
			return o;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by sql failed", Level.SEVERE, re);
			throw re;
		}

	}

	/**
	 * get single obj
	 * 
	 * @param jpql
	 * @return
	 */
	public Object findSingleByStatement(String jpql) {
		// EntityManagerHelper.log("finding instance with statement: " + jpql,
		// Level.INFO, null);
		try {
			EntityManager em = getEntityManager();
			Query q = em.createQuery(jpql);
			q.setMaxResults(1);
			Object o = q.getSingleResult();
			em.close();
			return o;
		} catch (NoResultException e) {
			// EntityManagerHelper.log("no result", Level.FINE, e);
			return null;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find by sql failed", Level.SEVERE, re);
			throw re;
		}

	}

	/**
	 * find by pk
	 * 
	 * @param objClass
	 * @param pk
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getByPrimaryKey(Class objClass, Object pk) {
		// EntityManagerHelper.log("finding obj instance with pk: " + pk,
		// Level.INFO, null);
		try {
			EntityManager em = getEntityManager();
			Object o = em.find(objClass, pk);
			// em.getTransaction().commit();
			// em.close();
			return o;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}

	}

	public Integer getCntByStatement(String jpql) {
		// EntityManagerHelper.log("get count with statement: " + jpql,
		// Level.FINE, null);
		try {
			EntityManager em = getEntityManager();
			Query q = em.createQuery(jpql);
			Long i = (Long) q.getSingleResult();
			em.close();
			return i == null ? null : i.intValue();
		} catch (ClassCastException e) {
			EntityManager em = getEntityManager();
			Query q = em.createQuery(jpql);
			Integer i = (Integer) q.getSingleResult();
			em.close();
			return i;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("get count failed", Level.SEVERE, re);
			throw re;
		}

	}

	@SuppressWarnings("unchecked")
	private Class getEntityClass() {
		EntityClass ec = this.getClass().getAnnotation(EntityClass.class);
		if (ec != null) {
			return ec.value();
		} else {
			return null;
		}
	}

	protected EntityManager getEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	protected String getEntityName() {
		Class entityClass = this.getEntityClass();
		if (entityClass != null) {
			return entityClass.getSimpleName();
		} else {
			String name = this.getClass().getSimpleName();
			if (name.endsWith("DAO")) {
				name = name.substring(0, name.length() - "DAO".length());
			}
			return name;
		}
	}

	@SuppressWarnings("unchecked")
	protected Object getEntityPrimaryKeyValue(Object model) {
		Class entityClass = this.getEntityClass();
		if (entityClass != null) {

			for (Field field : entityClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					String pkGetterName = "get"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1,
									field.getName().length());
					logger.debug("class: " + entityClass.getName()
							+ "; getter:" + pkGetterName);
					Method getterMethod;
					try {
						getterMethod = entityClass.getMethod(pkGetterName);
						return getterMethod.invoke(model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * delete an obj
	 * 
	 * @param obj
	 */
	public void remove(Object obj) {
		// EntityManagerHelper.log("deleting instance", Level.INFO, null);
		EntityManager em = getEntityManager();
		try {
			// begin transaction
			em.getTransaction().begin();
			Object copy = em.merge(obj);// add by gladstone @2007-12
			em.remove(copy);
			// end transaction
			em.getTransaction().commit();
			// close EntityManager
			em.close();
			EntityManagerHelper.log("delete successful", Level.INFO, null);
		} catch (IllegalArgumentException e) {
			// em.close();
			// em = getEntityManager();
			// em.getTransaction().begin();
			// Object copy = em.merge(obj);
			// em.remove(copy);
			// // end transaction
			// em.getTransaction().commit();
			// // close EntityManager
			// em.close();
			// EntityManagerHelper.log("delete successful", Level.INFO, null);
			throw e;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("delete failed", Level.SEVERE, re);
			throw re;
		}

	}

	/**
	 * insert an obj
	 * 
	 * @param obj
	 */
	public void save(Object obj) {
		// EntityManagerHelper.log("save obj instance ", Level.INFO, null);
		try {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			em.close();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("save failed", Level.SEVERE, re);
			throw re;
		}

	}

	/**
	 * update an obj
	 * 
	 * @param obj
	 */
	public void update(Object obj) {
		try {
			EntityManager em = getEntityManager();
			em.getTransaction().begin();
			em.merge(obj);
			em.getTransaction().commit();
			em.close();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("update failed", Level.SEVERE, re);
			throw re;
		}
	}
}
