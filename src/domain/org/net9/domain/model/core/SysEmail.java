package org.net9.domain.model.core;

import javax.persistence.Column;
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
@Table(name = "sys_emails", uniqueConstraints = {})
public class SysEmail extends BaseModel {
	
	@Id
	Integer id;

	String title;

	String content;

	String cc;

	@Column(name="UPDATE_TIME")
	String updateTime;
	
	String username;
	
	@Column(name="FROM_ADDR")
	String fromAddr;
	
	@Column(name="TO_ADDR")
	String toAddr;

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
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


}
