package org.net9.core.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.domain.model.core.SysEmail;

import com.google.inject.servlet.SessionScoped;

/**
 * 系统邮件服务
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class EmailService extends BaseService {

	private static final long serialVersionUID = -8224280800088207331L;

	/**
	 * 保存或者更新邮件
	 * 
	 * @param model
	 */
	public void saveEmail(SysEmail model) {
		if (model.getId() == null) {
			sysEmailDAO.save(model);
		} else {
			sysEmailDAO.update(model);
		}

	}

	/**
	 * 删除邮件
	 * 
	 * @param model
	 */
	public void deleteEmail(SysEmail model) {
		sysEmailDAO.remove(model);
	}

	/**
	 * 查找邮件列表
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysEmail> findEmails(String username, Integer start,
			Integer limit) {
		String jpql = "SELECT model FROM SysEmail model WHERE model.id>0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " AND model.username='" + username + "'";
		}
		jpql += " ORDER BY model.updateTime desc";
		return sysEmailDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到邮件的数目
	 * 
	 * @param username
	 * @return
	 */
	public Integer getEmailsCnt(String username) {
		String jpql = "SELECT COUNT(model) FROM SysEmail model WHERE model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " AND model.username='" + username + "'";
		}
		return sysEmailDAO.getCntByStatement(jpql);
	}
	
	/**
	 * 得到一个邮件
	 * @param eid
	 * @return
	 */
	public SysEmail getEmail(Integer eid){
		return (SysEmail)sysEmailDAO.getByPrimaryKey(SysEmail.class, eid);
	}
}
