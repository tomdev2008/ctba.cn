package org.net9.core.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.domain.dao.core.SysAdminDAO;
import org.net9.domain.dao.core.SysAdminGroupDAO;
import org.net9.domain.dao.core.SysGroupDAO;
import org.net9.domain.model.core.SysAdmin;
import org.net9.domain.model.core.SysAdminGroup;
import org.net9.domain.model.core.SysGroup;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * user service,with the file ,from the db
 * 
 * @author gladstone
 * @since 2008-2-9
 */
@SessionScoped
public class DBAdminService extends BaseService {

	private static final long serialVersionUID = 781823012836406115L;

	public static String OPTION_STR_WRITE = "W";

	public static String OPTION_STR_READ = "R";

	@Inject
	SysAdminDAO sysAdminDAO;

	@Inject
	SysGroupDAO sysGroupDAO;

	@Inject
	SysAdminGroupDAO sysAdminGroupDAO;

	/**
	 * delete an admin
	 * 
	 * @param model
	 */
	public void deleteAdmin(SysAdmin model) {
		sysAdminDAO.remove(model);

	}

	/**
	 * delete an admin_group
	 * 
	 * @param model
	 */
	public void deleteAdminGroup(Integer id) {
		SysAdminGroup model = getAdminGroup(id);
		if (model != null) {
			sysAdminGroupDAO.remove(model);
		}
	}

	/**
	 * delete an group
	 * 
	 * @param model
	 */
	public void deleteGroup(SysGroup model) {
		sysGroupDAO.remove(model);

	}

	/**
	 * list groups by username
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysAdminGroup> findAdminGroups(String username) {
		String jpql = "SELECT model FROM SysAdminGroup model "
				+ " WHERE model.username='" + username + "'";
		List<SysAdminGroup> list = sysAdminGroupDAO.findByStatement(jpql);
		return list;
	}

	/**
	 * list sys admins
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysAdmin> findAdmins(Integer start, Integer limit) {
		List<SysAdmin> list = sysAdminDAO.findByStatement(
				"select model from SysAdmin model order by model.id desc ",
				start, limit);
		return list;
	}

	/**
	 * list sys groups
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysGroup> findGroups(Integer start, Integer limit) {
		List<SysGroup> list = sysGroupDAO.findByStatement(
				"select model from SysGroup model order by model.id desc ",
				start, limit);
		return list;
	}

	/**
	 * get a admin by username
	 * 
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SysAdmin getAdmin(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		String jpql = "SELECT model FROM SysAdmin model WHERE model.username='"
				+ username + "'";
		List l = sysAdminDAO.findByStatement(jpql);
		return l == null || l.size() == 0 ? null : (SysAdmin) l.get(0);
	}

	/**
	 * get count of admins
	 * 
	 * @return
	 */
	public Integer getAdminCnt() {
		return sysAdminDAO
				.getCntByStatement("SELECT COUNT(model) FROM SysAdmin model");
	}

	/**
	 * get admin_group
	 * 
	 * @param id
	 * @return
	 */
	public SysAdminGroup getAdminGroup(Integer id) {
		return (SysAdminGroup) sysAdminGroupDAO.getByPrimaryKey(
				SysAdminGroup.class, id);

	}

	/**
	 * 
	 * @param username
	 * @param groupId
	 * @return
	 */
	public SysAdminGroup getAdminGroup(String username, Integer groupId) {
		String jpql = "SELECT model FROM SysAdminGroup model "
				+ " WHERE model.username='" + username + "' AND model.groupId="
				+ groupId;
		return (SysAdminGroup) sysAdminGroupDAO.findSingleByStatement(jpql);

	}

	/**
	 * get group
	 * 
	 * @param id
	 * @return
	 */
	public SysGroup getGroup(Integer id) {
		return (SysGroup) sysGroupDAO.getByPrimaryKey(SysGroup.class, id);

	}

	/**
	 * get count of groups
	 * 
	 * @return
	 */
	public Integer getGroupCnt() {
		return sysGroupDAO
				.getCntByStatement("SELECT COUNT(model) FROM SysGroup model ");
	}

	/**
	 * insert admin_group,options
	 * 
	 * @param group
	 * @param admin
	 * @param optionStr
	 */
	public void insertAdminGroup(SysGroup group, SysAdmin admin,
			String optionStr) {
		SysAdminGroup model = new SysAdminGroup();
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setGroupId(group.getId());
		model.setUsername(admin.getUsername());
		model.setOptionStr(optionStr);
		sysAdminGroupDAO.save(model);

	}

	/**
	 * an admin is super admin or not
	 * 
	 * @param username
	 * @return
	 */
	public boolean isSuperAdmin(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		List<String> admins = DefaultFileAdminService.getInstance().listAdmins(
				0, BusinessConstants.MAX_INT);
		for (String admin : admins) {
			if (StringUtils.isNotEmpty(admin)
					&& admin.equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * save an admin
	 * 
	 * @param model
	 */
	public void saveAdmin(SysAdmin model) {
		if (model.getId() != null) {
			sysAdminDAO.update(model);
		} else {
			sysAdminDAO.save(model);
		}

	}

	/**
	 * save an group
	 * 
	 * @param model
	 */
	public void saveGroup(SysGroup model) {
		if (model.getId() != null) {
			sysGroupDAO.update(model);
		} else {
			sysGroupDAO.save(model);
		}
	}

}
