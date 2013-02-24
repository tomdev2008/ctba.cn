package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * online count
 * 
 * @author gladstone
 * 
 */
@Entity
@Table(name = "bbs_online", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Online extends BaseModel {
	@Id
	Integer id;

	String username;

	String updateTime;

	String ip;

	String agent;

	Integer instanceCnt;

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstanceCnt() {
		return instanceCnt;
	}

	public void setInstanceCnt(Integer instanceCnt) {
		this.instanceCnt = instanceCnt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
