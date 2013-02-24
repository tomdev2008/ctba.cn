package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.ShopCategory;

@EntityClass(value = ShopCategory.class, useCache = false)
public class ShopCategoryDAO extends CachedJpaDAO {

	public ShopCategory findById(Integer id) {
		EntityManagerHelper.log("finding ShopCategory instance with id: " + id,
				Level.INFO, null);
		try {
			ShopCategory instance = getEntityManager().find(ShopCategory.class,
					id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}