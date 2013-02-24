package org.net9.domain.dao.core;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.MainCommend;

/**
 * MainPlace DAO
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = MainCommend.class)
public class MainCommendDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<MainCommend> findAll() {
		EntityManagerHelper.log("finding all MainCommend instances",
				Level.INFO, null);
		try {
			String queryString = "select model from MainCommend model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}
