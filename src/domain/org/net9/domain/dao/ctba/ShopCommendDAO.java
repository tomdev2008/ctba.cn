package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.ShopCommend;

@EntityClass(value = ShopCommend.class)
public class ShopCommendDAO extends CachedJpaDAO {

	public ShopCommend findById(Integer id) {
		EntityManagerHelper.log("finding ShopCommend instance with id: " + id,
				Level.INFO, null);
		try {
			ShopCommend instance = getEntityManager().find(ShopCommend.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}