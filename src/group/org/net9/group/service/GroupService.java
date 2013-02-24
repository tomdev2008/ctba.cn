package org.net9.group.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.dao.view.ViewGroupReferenceDAO;
import org.net9.domain.model.core.User;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.group.GroupUser;
import org.net9.domain.model.view.ViewGroupReference;
import org.net9.domain.model.view.ViewRandomGroup;

import com.google.inject.Inject;

/**
 * group service
 * 
 * @author gladstone
 * 
 */
public class GroupService extends BaseService {

	private static final long serialVersionUID = -5484745651645746118L;
	static Log log = LogFactory.getLog(GroupService.class);

	@Inject
	private ViewGroupReferenceDAO viewGroupReferenceDAO;

	/**
	 * delete a group user
	 * 
	 * @param model
	 */
	public void deleteGroupUser(GroupUser model) {
		groupUserDAO.remove(model);
	}

	/**
	 * delete group
	 * 
	 * @param model
	 */
	public void delGroup(GroupModel model) {
		groupModelDAO.remove(model);
	}

	/**
	 * get a list of groups
	 * 
	 * @param name
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findGroups(String groupType, String name, Integer start,
			Integer limit) {
		String jpql = "select model from GroupModel model where model.id>0 and model.deleteFlag=0 ";
		if (StringUtils.isNotEmpty(name)) {
			jpql += " and model.name like '%" + name + "%' ";
		}
		if (StringUtils.isNotEmpty(groupType)) {
			jpql += " and model.type ='" + groupType + "' ";
		}
		jpql += " order by model.id desc";
		return groupModelDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List findGroupsByTag(String groupType, String name, String tag,
			Integer start, Integer limit) {
		String jpql = "select model from GroupModel model where model.id>0 and model.deleteFlag=0 ";

		if (StringUtils.isNotEmpty(tag)) {
			jpql += " and (model.tags like '%" + tag + "," + "%' ";
			if (StringUtils.isNotEmpty(name)) {
				jpql += " or model.name like '%" + name + "%' ";
			}
			jpql += ")";
		}
		if (StringUtils.isNotEmpty(groupType)) {
			jpql += " and model.type ='" + groupType + "' ";
		}
		jpql += " order by model.id desc";
		return groupModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * my groups
	 * 
	 * @param username
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findGroupsByUsername(String username, Integer role,
			Integer start, Integer limit) {
		String jpql = "select model from GroupModel model,GroupUser gu where model.id>0 and model.id=gu.groupModel.id and model.deleteFlag=0  and '"
				+ username + "'=gu.username";
		if (null != role) {
			jpql += " and gu.role = " + role + " ";
		}
		jpql += " order by model.id desc";
		log.debug(jpql);
		return groupModelDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List findGroupsByUsernameAndRoles(String username, Integer[] roles,
			Integer start, Integer limit) {
		String jpql = "select model from GroupModel model,GroupUser gu where model.id>0 and model.id=gu.groupModel.id and model.deleteFlag=0  and '"
				+ username + "'=gu.username";

		jpql += " and gu.role in( ";
		for (Integer role : roles) {
			jpql += role + ",";
		}
		jpql = jpql.substring(0, jpql.length() - 1) + ")";
		jpql += " order by model.id desc";
		log.debug(jpql);
		return groupModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * find group users by group id
	 * 
	 * @param gid
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupUser> findGroupUsers(Integer gid, Integer[] roles,
			Integer start, Integer limit) {
		String jpql = "select model from GroupUser model where model.id>0 ";
		if (roles != null) {
			jpql += " and model.role in( ";
			for (Integer role : roles) {
				jpql += role + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += ") ";
		}
		if (gid != null) {
			jpql += " and model.groupModel.id= " + gid;
		}
		jpql += " order by model.id desc";
		return groupUserDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 点击量最多的小组
	 * 
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupModel> findHotGroups(Integer start, Integer limit) {
		return groupModelDAO.findByStatement(
				"select model from GroupModel model order by model.hits desc",
				start, limit);
	}

	/**
	 * new groups
	 * 
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupModel> findNewGroups(String type, Integer start,
			Integer limit) {
		String jpql = "select model from GroupModel model where model.id>0 and model.deleteFlag=0 ";
		if (StringUtils.isNotEmpty(type)) {
			jpql += " and model.type = '%" + type + "%'";
		}
		jpql += " order by model.id desc";
		return groupModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * new users for index page TODO:注意，这里取出的不应该是user的所有字段
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findNewGroupUsers(Integer start, Integer limit) {
		return userDAO
				.findByStatement(
						"select distinct u from GroupUser gu ,User u where u.userName=gu.username order by gu.id desc",
						start, limit);
	}

	/**
	 * random groups
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewRandomGroup> findRandomGroups(Integer start, Integer limit) {
		// Integer maxId = (Integer) groupModelDAO
		// .findSingleByStatement("select max(g.id) from GroupModel g ");
		// if (maxId == null) {
		// return null;
		// }
		//
		// List<GroupModel> gList = new ArrayList<GroupModel>();
		// List<Integer> ids = new ArrayList<Integer>();
		// Random random = new Random();
		// while (ids.size() < limit) {
		// Integer id = random.nextInt(maxId);
		// if (!ids.contains(id)) {
		// ids.add(id);
		// }
		// }
		//
		// for (Integer id : ids) {
		// gList.addAll(groupModelDAO.findByStatement(
		// "select g from GroupModel g where g.id>=" + id
		// + " and g.id<=" + maxId + " order by g.id asc ", 0,
		// 1));
		// }
		// return groupModelDAO.findByNativeStatement(
		// "select g.id from group_model g order by rand() desc", start,
		// limit);
		return viewRandomGroupDAO
				.findByStatement("select model from ViewRandomGroup model");
	}

	/**
	 * #668 (增加相关小组功能)
	 * 
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewGroupReference> findReferenceGroups(Integer gid,
			Integer start, Integer limit) {
		String jpql = "select distinct model from ViewGroupReference model where model.gid="
				+ gid;
		jpql += " order by model.refid desc";
		return viewGroupReferenceDAO.findByStatement(jpql, start, limit);
	}

	public void flushGroupCache() {
		groupModelDAO.flushCache();
	}

	public void flushGroupUserCache() {
		groupUserDAO.flushCache();
	}

	/**
	 * get a group
	 * 
	 * @param id
	 *            can be null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupModel getGroup(Integer id) {
		if (id == null) {
			List<GroupModel> list = findGroups(null, null, 0, 1);
			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		}
		return (GroupModel) groupModelDAO.findById(id);
	}

	/**
	 * get a group by name
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupModel getGroupByName(String name) {
		List l = groupModelDAO.findByProperty("name", name);
		// .findByName(name);
		return (l == null || l.size() < 1) ? null : (GroupModel) l.get(0);
	}

	/**
	 * 根据magicUrl找到小组
	 * 
	 * @param magicUrl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupModel getGroupsByMagicUrl(String magicUrl) {
		List<GroupModel> list = groupModelDAO.findByStatement(
				"select model from GroupModel model where model.magicUrl ='"
						+ magicUrl + "' order by model.id desc", 0, 1);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * get the count of groups
	 * 
	 * @param name
	 * @return
	 */
	public Integer getGroupsCnt(String groupType, String name) {
		String jpql = "select count(model) from GroupModel model where model.id>0 and model.deleteFlag=0 ";
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(name)) {
			jpql += " and model.name like '%" + name + "%'";
		}
		if (StringUtils.isNotEmpty(groupType)) {
			jpql += " and model.type ='" + groupType + "' ";
		}
		return groupModelDAO.getCntByStatement(jpql);
	}

	public Integer getGroupsCntByTag(String groupType, String name, String tag) {
		String jpql = "select count(model) from GroupModel model where model.id>0 and model.deleteFlag=0 ";
		if (StringUtils.isNotEmpty(tag)) {
			jpql += " and (model.tags like '%" + tag + "," + "%' ";
			if (StringUtils.isNotEmpty(name)) {
				jpql += " or model.name like '%" + name + "%' ";
			}
			jpql += ")";
		}
		if (StringUtils.isNotEmpty(groupType)) {
			jpql += " and model.type ='" + groupType + "' ";
		}
		return groupModelDAO.getCntByStatement(jpql);
	}

	/**
	 * get count
	 * 
	 * @param username
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	public Integer getGroupsCntByUsername(String username, Integer role) {
		String jpql = "select count(model) from GroupModel model,GroupUser gu where model.id>0 and model.id=gu.groupModel.id and model.deleteFlag=0 and '"
				+ username + "'=gu.username";
		if (null != role) {
			jpql += " and gu.role = " + role + " ";
		}
		return groupModelDAO.getCntByStatement(jpql);
	}

	public GroupUser getGroupUser(Integer guid) {
		return (GroupUser) groupUserDAO.findById(guid);
	}

	/**
	 * get group user
	 * 
	 * @param gid
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupUser getGroupUser(Integer gid, String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		String jpql = "select model from GroupUser model where model.id>0 ";
		jpql += " and model.groupModel.id= " + gid;
		jpql += " and model.username='" + username + "'";
		jpql += " order by model.id desc";
		List l = groupUserDAO.findByStatement(jpql);
		return l == null || l.size() < 1 ? null : (GroupUser) l.get(0);
	}

	/**
	 * get count
	 * 
	 * @param gid
	 * @param role
	 * @return
	 */
	public Integer getGroupUsersCnt(Integer gid, Integer[] roles) {
		String jpql = "select count(model) from GroupUser model where model.id>0 ";
		if (roles != null) {
			jpql += " and model.role in( ";
			for (Integer role : roles) {
				jpql += role + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += ") ";
		}
		if (gid != null) {
			jpql += " and model.groupModel.id= " + gid;
		}
		jpql += " order by model.id desc";
		return groupUserDAO.getCntByStatement(jpql);
	}

	/**
	 * get latest group createed by user
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupModel getLatestGroupByManager(String username) {
		String jpql = "select model from GroupModel model where model.id>0 and model.deleteFlag=0  and model.manager='"
				+ username + "' order by model.id desc ";
		List l = groupModelDAO.findByStatement(jpql);
		return (l == null || l.size() < 1) ? null : (GroupModel) l.get(0);
	}

	/**
	 * new group
	 * 
	 * @param model
	 */
	public void saveGroup(GroupModel model) {
		if (model.getId() == null) {
			groupModelDAO.save(model);
		} else {
			groupModelDAO.update(model);
		}
	}

	/**
	 * save group user
	 * 
	 * @param model
	 */
	public void saveGroupUser(GroupUser model) {
		if (model.getId() == null) {
			groupUserDAO.save(model);
		} else {
			groupUserDAO.update(model);
		}
	}

	public void updateGroupHit(Integer id, Integer hit) {
		groupModelDAO.exquteStatement("update GroupModel set hits=" + hit
				+ " where id=" + id);
	}
}
