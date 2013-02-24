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
@Table(name = "main_place", uniqueConstraints = {})
public class MainPlace extends BaseModel {
	
	@Id
	Integer id;

	String name;

	String description;

	@Column(name="UPDATE_TIME")
	String updateTime;
	
	@Column(name="CREATE_TIME")
	String createTime;
	
	Integer type;
	
	Integer parent;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

}
