package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.OrderModel;

@EntityClass(value = OrderModel.class, useCache = false)
public class OrderModelDAO extends CachedJpaDAO {

	public OrderModel findById(Integer id) {
		EntityManagerHelper.log("finding OrderModel instance with id: " + id,
				Level.INFO, null);
		try {
			OrderModel instance = getEntityManager().find(OrderModel.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}