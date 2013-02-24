package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "view_timeline_friend", uniqueConstraints = {})
public class ViewTimelineFriend extends BaseModel {
	@Id
	private Integer id;

	private String updateTime;

	private String username;

	private String currUsername;

	private String target;

	private Integer type;

	private Integer hide;

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

	public String getCurrUsername() {
		return currUsername;
	}

	public void setCurrUsername(String currUsername) {
		this.currUsername = currUsername;
	}

}
