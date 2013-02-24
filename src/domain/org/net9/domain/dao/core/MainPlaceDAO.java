package org.net9.domain.dao.core;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.core.MainPlace;

/**
 * MainPlace DAO
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = MainPlace.class)
public class MainPlaceDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<MainPlace> findAll() {
		EntityManagerHelper.log("finding all MainPlace instances", Level.INFO,
				null);
		try {
			String queryString = "select model from MainPlace model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

}
