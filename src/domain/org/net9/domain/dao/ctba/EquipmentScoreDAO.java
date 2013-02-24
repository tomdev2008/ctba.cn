package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.EquipmentScore;

@EntityClass(value = EquipmentScore.class)
public class EquipmentScoreDAO extends CachedJpaDAO {


	public EquipmentScore findById(Integer id) {
		EntityManagerHelper.log("finding EquipmentScore instance with id: "
				+ id, Level.INFO, null);
		try {
			EquipmentScore instance = getEntityManager().find(
					EquipmentScore.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}