package org.net9.domain.dao.group;

import java.util.List;
import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.ActivityComment;

@EntityClass(value = ActivityComment.class)
public class ActivityCommentDAO extends CachedJpaDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<ActivityComment> findAll() {
		EntityManagerHelper.log("finding all ActivityComment instances",
				Level.INFO, null);
		try {
			String queryString = "select model from ActivityComment model";
			return getEntityManager().createQuery(queryString).getResultList();
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find all failed", Level.SEVERE, re);
			throw re;
		}
	}

	public ActivityComment findById(Integer id) {
		EntityManagerHelper.log("finding ActivityComment instance with id: "
				+ id, Level.INFO, null);
		try {
			ActivityComment instance = getEntityManager().find(
					ActivityComment.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}

}