package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;
//FIXME:慢查询，亟需改善
@SuppressWarnings("serial")
@Entity
@Table(name = "view_rank_user", uniqueConstraints = {})
public class ViewHotUser extends BaseModel{
	@Id
	private Integer uid;

	private String face;

	private String username;

	private Integer cnt;

	public Integer getCnt() {
		return cnt;
	}

	public String getFace() {
		return face;
	}

	public Integer getUid() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
