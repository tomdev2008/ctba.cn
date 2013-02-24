package org.net9.core.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.core.SysBlacklist;

import com.google.inject.servlet.SessionScoped;

/**
 * common service
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class SystemService extends BaseService {

	private static final long serialVersionUID = -9221133979019651817L;
	static Log log = LogFactory.getLog(SystemService.class);

	/**
	 * delete SysBlacklist
	 * 
	 * @param model
	 */
	public void deleteSysBlacklist(SysBlacklist model) {
		sysBlacklistDAO.remove(model);
	}

	/**
	 * list SysBlacklist
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysBlacklist> findSysBlacklist(String username, String ip,
			Integer start, Integer limit) {
		String jpql = "select model from SysBlacklist model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "' ";
		}
		if (StringUtils.isNotEmpty(ip)) {
			jpql += " and model.ip='" + ip + "' ";
		}
		jpql += " order by model.id desc";
		return sysBlacklistDAO.findByStatement(jpql, start, limit);
	}

	public void flushSysBlacklistCache() {
		sysBlacklistDAO.flushCache();
	}

	public SysBlacklist getSysBlacklist(Integer id) {
		return (SysBlacklist) sysBlacklistDAO.getByPrimaryKey(
				SysBlacklist.class, id);
	}

	public int getSysBlacklistCnt(String username, String ip) {
		String jpql = "select count(model) from SysBlacklist model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "' ";
		}
		if (StringUtils.isNotEmpty(ip)) {
			jpql += " and model.ip='" + ip + "' ";
		}
		return sysBlacklistDAO.getCntByStatement(jpql);
	}

	/**
	 * get blacklist by username
	 * 
	 * @param username
	 * @return
	 */
	public SysBlacklist getSysBlacklistByUsername(String username) {
		String jpql = "select model from SysBlacklist model where model.id>0 ";
		jpql += " and model.username='" + username + "' ";
		SysBlacklist model = (SysBlacklist) sysBlacklistDAO
				.findSingleByStatement(jpql);
		return model;
	}

	/**
	 * get blacklist by ip
	 * 
	 * @param ip
	 * @return
	 */
	public SysBlacklist getSysBlacklistByIp(String ip) {
		String jpql = "select model from SysBlacklist model where model.id>0 ";
		jpql += " and model.ip='" + ip + "' ";
		SysBlacklist model = (SysBlacklist) sysBlacklistDAO
				.findSingleByStatement(jpql);
		return model;
	}

	/**
	 * insert or update
	 * 
	 * @param model
	 */
	public void saveSysBlacklist(SysBlacklist model) {
		if (model.getId() == null)
			sysBlacklistDAO.save(model);
		else
			sysBlacklistDAO.update(model);
	}
}
