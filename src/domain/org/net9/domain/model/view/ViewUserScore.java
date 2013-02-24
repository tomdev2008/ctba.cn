package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "view_user_score", uniqueConstraints = {})
public class ViewUserScore extends BaseModel {
	@Id
	private Integer uid;

	private String face;

	private String username;

	private String nick;

	private Integer score;

	public String getFace() {
		return face;
	}

	public Integer getUid() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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
