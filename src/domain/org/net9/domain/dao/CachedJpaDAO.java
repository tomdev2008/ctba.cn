package org.net9.domain.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.cache.CacheManager;
import org.net9.common.cache.provider.CacheProviderFactory;
import org.net9.common.exception.CacheException;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.SystemConfigVars;
import org.net9.domain.dao.annotation.EntityClass;

/**
 * 支持三级缓存的jpa dao
 * 
 * @author gladstone
 * @since 2008-9-27
 */
public abstract class CachedJpaDAO extends BaseJpaDAO {

	public static final boolean useCache = SystemConfigUtils
			.getBoolean("db.cache");

	private static Log logger = LogFactory.getLog(CachedJpaDAO.class);

	public static final String QUERY_TYPE_FIND = "_find_";

	public static final String QUERY_TYPE_GET = "_get_";

	private CacheManager getFindCacheManager() {
		CacheManager cacheManager = CacheManager.getInstance(QUERY_TYPE_FIND
				+ this.getEntityName());
		if (!SystemConfigVars.DB_CACHE_USE_MEMCACHE) {
			cacheManager.setCacheProvider(CacheProviderFactory
					.getWhirlycacheProvider());
		}
		return cacheManager;
	}

	private CacheManager getGetCacheManager() {
		CacheManager cacheManager = CacheManager.getInstance(QUERY_TYPE_GET
				+ this.getEntityName());
		if (!SystemConfigVars.DB_CACHE_USE_MEMCACHE) {
			cacheManager.setCacheProvider(CacheProviderFactory
					.getWhirlycacheProvider());
		}
		return cacheManager;
	}

	/**
	 * get list by statement
	 * 
	 * @param jpql
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List findByStatement(String jpql) {
		if (!useCache || !this.isCacheUsedInBeanScope()) {
			return super.findByStatement(jpql);
		}

		List reval = null;
		try {
			reval = (List) getFindCacheManager().get(jpql);
		} catch (CacheException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (reval == null) {
			reval = super.findByStatement(jpql);
			logger.debug("get objs in " + QUERY_TYPE_FIND
					+ this.getEntityName() + ": " + jpql);
			// 插入缓存
			try {
				getFindCacheManager().put(jpql, reval);
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return reval;
	}

	/**
	 * get list by statement
	 * 
	 * @param jpql
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List findByStatement(String jpql, Integer start, Integer limit) {
		if (!useCache || !this.isCacheUsedInBeanScope()) {
			return super.findByStatement(jpql, start, limit);
		}
		List reval = null;
		try {
			reval = (List) getFindCacheManager().get(
					jpql + "__" + start + "_" + limit);
		} catch (CacheException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (reval == null) {
			reval = super.findByStatement(jpql, start, limit);
			logger.debug("get objs in " + QUERY_TYPE_FIND
					+ this.getEntityName() + ": " + jpql + "__" + start + "_"
					+ limit);
			// 插入缓存
			try {
				getFindCacheManager().put(jpql + "__" + start + "_" + limit,
						reval);
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return reval;
	}

	/**
	 * get single obj
	 * 
	 * @param jpql
	 * @return
	 */
	@Override
	public Object findSingleByStatement(String jpql) {
		if (!useCache || !this.isCacheUsedInBeanScope()) {
			return super.findSingleByStatement(jpql);
		}
		Object reval = null;
		try {
			reval = (Object) getFindCacheManager().get(jpql);
		} catch (CacheException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (reval == null) {
			reval = super.findSingleByStatement(jpql);
			// 插入缓存
			try {
				getFindCacheManager().put(jpql, reval);
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.debug("Got obj in cache:" + QUERY_TYPE_FIND
					+ this.getEntityName());
		}
		return reval;
	}

	public void flushCache() {
		if (useCache && this.isCacheUsedInBeanScope()) {
			try {
				// logger.debug("flushCache:" + QUERY_TYPE_FIND
				// + this.getEntityName());
				getFindCacheManager().flush();
				// CacheManager
				// .getInstance(QUERY_TYPE_FIND + this.getEntityName())
				// .removeAll();
				// logger.debug("flushCache:" + QUERY_TYPE_GET
				// + this.getEntityName());
				getGetCacheManager().flush();
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * find by pk
	 * 
	 * @param objClass
	 * @param pk
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getByPrimaryKey(Class objClass, Object pk) {
		if (!useCache || !this.isCacheUsedInBeanScope()) {
			return super.getByPrimaryKey(objClass, pk);
		}

		Object reval = null;
		try {
			reval = (Object) getGetCacheManager().get(
					QUERY_TYPE_GET + this.getEntityName() + pk);
		} catch (CacheException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		if (reval == null) {
			reval = super.getByPrimaryKey(objClass, pk);
			// 插入缓存
			try {
				// CacheManager.getInstance(QUERY_TYPE_GET +
				// this.getEntityName())
				// .put(QUERY_TYPE_GET + this.getEntityName() + pk, reval);
				getGetCacheManager().put(
						QUERY_TYPE_GET + this.getEntityName() + pk, reval);
				logger.debug("now cache obj class: [" + QUERY_TYPE_GET
						+ this.getEntityName() + pk + "]"
				// + reval.getClass().getName()
						);
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.debug("cached obj class: [" + QUERY_TYPE_GET
					+ this.getEntityName() + pk + "]"
					+ reval.getClass().getName());

		}
		return reval;
	}

	@Override
	public Integer getCntByStatement(String jpql) {
		if (!useCache || !this.isCacheUsedInBeanScope()) {
			return super.getCntByStatement(jpql);
		}
		Integer reval = null;
		try {
			reval = (Integer) getFindCacheManager().get(jpql);
			logger.debug("get cnt in " + QUERY_TYPE_FIND + this.getEntityName()
					+ ": " + jpql);
		} catch (CacheException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		if (reval == null) {
			reval = super.getCntByStatement(jpql);
			// 插入缓存
			try {
				getFindCacheManager().put(jpql, reval);
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return reval;
	}

	private boolean isCacheUsedInBeanScope() {
		EntityClass ec = this.getClass().getAnnotation(EntityClass.class);
		return ec.useCache();
	}

	/**
	 * delete an obj
	 * 
	 * @param obj
	 */
	@Override
	public void remove(Object obj) {
		super.remove(obj);
		if (useCache && this.isCacheUsedInBeanScope()) {

			// 刷新当前table的所有_FIND_缓存
			// 刷新当前物件的_GET_缓存
			try {
				getFindCacheManager().removeAll();
				getGetCacheManager().remove(
						QUERY_TYPE_GET + this.getEntityName()
								+ this.getEntityPrimaryKeyValue(obj));

			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * insert an obj
	 * 
	 * @param obj
	 */
	@Override
	public void save(Object obj) {
		super.save(obj);
		if (useCache && this.isCacheUsedInBeanScope()) {
			// 刷新当前table的所有_FIND_缓存
			try {
				getFindCacheManager().removeAll();
				logger.debug("remove all cache in " + QUERY_TYPE_FIND
						+ this.getEntityName());
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * update an obj
	 * 
	 * @param obj
	 */
	@Override
	public void update(Object obj) {
		super.update(obj);
		if (useCache && this.isCacheUsedInBeanScope()) {
			// 不用刷新当前table的所有缓存，而只是刷新被缓存的obj(_GET_)
			try {
				getGetCacheManager().remove(
						QUERY_TYPE_GET + this.getEntityName()
								+ this.getEntityPrimaryKeyValue(obj));

				logger.debug("remove cache in " + QUERY_TYPE_GET
						+ this.getEntityName());
			} catch (CacheException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
