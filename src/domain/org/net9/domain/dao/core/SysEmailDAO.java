package org.net9.domain.dao.core;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.SysEmail;

/**
 * system email dao
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = SysEmail.class)
public class SysEmailDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<SysEmail> findAll() {
		EntityManagerHelper.log("finding all SysEmail instances", Level.INFO,
				null);
		try {
			String queryString = "select model from SysEmail model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}
