package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.ShopModel;

@EntityClass(value = ShopModel.class)
public class ShopModelDAO extends CachedJpaDAO {

	public ShopModel findById(Integer id) {
		EntityManagerHelper.log("finding ShopModel instance with id: " + id,
				Level.INFO, null);
		try {
			ShopModel instance = getEntityManager().find(ShopModel.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}