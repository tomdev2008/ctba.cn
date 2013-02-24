package org.net9.domain.dao.group;

import java.util.logging.Level;

import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.EntityManagerHelper;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.group.GroupTopic;

/**
 * Data access object (DAO) for domain model class GroupTopic.
 * 
 * @see org.net9.domain.model.group.GroupTopic
 * @author MyEclipse Persistence Tools
 */
@EntityClass(value = GroupTopic.class)
public class GroupTopicDAO extends CachedJpaDAO {
	// property constants
	public static final String TITLE = "title";

	public static final String CONTENT = "content";

	public static final String PARENT = "parent";

	public static final String CREATE_TIME = "createTime";

	public static final String UPDATE_TIME = "updateTime";

	public static final String IP = "ip";

	// @Override
	// @SuppressWarnings("unchecked")
	// public List<GroupTopic> findAll() {
	// EntityManagerHelper.log("finding all GroupTopic instances", Level.INFO,
	// null);
	// try {
	// String queryString = "select model from GroupTopic model";
	// return getEntityManager().createQuery(queryString).getResultList();
	// } catch (RuntimeException re) {
	// EntityManagerHelper.log("find all failed", Level.SEVERE, re);
	// throw re;
	// }
	// }

	// public List<GroupTopic> findByContent(Object content) {
	// return findByProperty(CONTENT, content);
	// }
	//
	// public List<GroupTopic> findByCreateTime(Object createTime) {
	// return findByProperty(CREATE_TIME, createTime);
	// }

	public GroupTopic findById(Integer id) {
		EntityManagerHelper.log("finding GroupTopic instance with id: " + id,
				Level.INFO, null);
		try {
			GroupTopic instance = (GroupTopic) this.getByPrimaryKey(
					GroupTopic.class, id);
			// getEntityManager().find(GroupTopic.class, id);
			return instance;
		} catch (RuntimeException re) {
			EntityManagerHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
	// public List<GroupTopic> findByIp(Object ip) {
	// return findByProperty(IP, ip);
	// }
	//
	// public List<GroupTopic> findByParent(Object parent) {
	// return findByProperty(PARENT, parent);
	// }

	// @SuppressWarnings("unchecked")
	// public List<GroupTopic> findByProperty(String propertyName, Object value)
	// {
	// EntityManagerHelper.log("finding GroupTopic instance with property: "
	// + propertyName + ", value: " + value, Level.INFO, null);
	// try {
	// String queryString = "select model from GroupTopic model where model."
	// + propertyName + "= :propertyValue";
	// return getEntityManager().createQuery(queryString).setParameter(
	// "propertyValue", value).getResultList();
	// } catch (RuntimeException re) {
	// EntityManagerHelper.log("find by property name failed",
	// Level.SEVERE, re);
	// throw re;
	// }
	// }

	// public List<GroupTopic> findByTitle(Object title) {
	// return findByProperty(TITLE, title);
	// }
	//
	// public List<GroupTopic> findByUpdateTime(Object updateTime) {
	// return findByProperty(UPDATE_TIME, updateTime);
	// }
}