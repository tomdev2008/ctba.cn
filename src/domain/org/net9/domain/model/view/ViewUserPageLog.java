package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "view_user_page_log", uniqueConstraints = {})
public class ViewUserPageLog extends BaseModel {
	@Id
	private Integer uid;

	private String target;

	private String username;

	private String face;

	private String updatetime;

	public String getFace() {
		return face;
	}

	public String getTarget() {
		return target;
	}

	public Integer getUid() {
		return uid;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public String getUsername() {
		return username;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
