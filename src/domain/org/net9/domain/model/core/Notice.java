package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@Entity
@Table(name = "main_notices", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Notice extends BaseModel {
	@Id
	private Integer id;

	private String username;

	private String title;

	private String source;//来源，通常是其他用户

	private String updateTime;
	
	private String refererURL;//网页来源，可以跳转到这个网页

	private Integer expired = 0;

	private Integer type = 0;//see: NoticeType

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getExpired() {
		return expired;
	}

	public void setExpired(Integer expired) {
		this.expired = expired;
	}

	public String getRefererURL() {
		return refererURL;
	}

	public void setRefererURL(String refererURL) {
		this.refererURL = refererURL;
	}

}
