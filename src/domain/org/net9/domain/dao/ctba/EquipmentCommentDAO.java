package org.net9.domain.dao.ctba;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.ctba.EquipmentComment;

@EntityClass(value = EquipmentComment.class)
public class EquipmentCommentDAO extends CachedJpaDAO {


	public EquipmentComment findById(Integer id) {
		EntityManagerHelper.log(
				"finding EquipmentComment instance with id: " + id, Level.INFO,
				null);
		try {
			EquipmentComment instance = getEntityManager().find(
					EquipmentComment.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}