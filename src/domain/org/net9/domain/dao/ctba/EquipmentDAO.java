package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.Equipment;

@EntityClass(value = Equipment.class,useCache=false)
public class EquipmentDAO extends CachedJpaDAO {


	public Equipment findById(Integer id) {
		EntityManagerHelper.log("finding Equipment instance with id: " + id,
				Level.INFO, null);
		try {
			Equipment instance = getEntityManager().find(Equipment.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}