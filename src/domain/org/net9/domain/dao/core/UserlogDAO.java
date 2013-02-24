package org.net9.domain.dao.core;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.UserLog;

/**
 * user log dao
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = UserLog.class)
public class UserlogDAO extends CachedJpaDAO {

	@SuppressWarnings("unchecked")
	public List<UserLog> findAll() {
		EntityManagerHelper.log("finding all UserLog instances", Level.INFO,
				null);
		try {
			String queryString = "select model from UserLog model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}
