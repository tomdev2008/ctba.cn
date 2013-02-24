package org.net9.domain.dao.group;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.GroupLink;

@EntityClass(value = GroupLink.class)
public class GroupLinkDAO extends CachedJpaDAO {

	public GroupLink findById(Integer id) {
		EntityManagerHelper.log("finding GroupLink instance with id: " + id,
				Level.INFO, null);
		try {
			GroupLink instance = getEntityManager().find(GroupLink.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
}