package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "bbs_userlogs", uniqueConstraints = {})
public class UserLog extends BaseModel {

	@Id
	private Integer id;

	private String updateTime;

	private String username;

	private String target;
	
	private Integer type;

	private Integer hide = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getHide() {
		return hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}

}
