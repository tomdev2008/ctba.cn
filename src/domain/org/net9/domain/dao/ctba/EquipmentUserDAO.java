package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.EquipmentUser;

@EntityClass(value = EquipmentUser.class)
public class EquipmentUserDAO extends CachedJpaDAO {


	public EquipmentUser findById(Integer id) {
		EntityManagerHelper.log("finding GoodsWareUsers instance with id: "
				+ id, Level.INFO, null);
		try {
			EquipmentUser instance = getEntityManager().find(
					EquipmentUser.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}