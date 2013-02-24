package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "main_share_comment", uniqueConstraints = {})
public class ShareComment extends BaseModel {

	@Id
	private Integer id;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shareId", unique = false, nullable = true)
	private Share share;

	private String username;

	private String body;

	private String updateTime;

	private String ip;

	public String getBody() {
		return body;
	}

	public Integer getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public Share getShare() {
		return share;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
