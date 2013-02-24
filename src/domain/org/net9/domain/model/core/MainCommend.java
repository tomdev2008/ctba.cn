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
@Table(name = "main_commend", uniqueConstraints = {})
public class MainCommend extends BaseModel {

	@Id
	private Integer id;

	private String label;

	private String link;

	private String image;

	private String updateTime;

	private String createTime;

	private Integer type;

	private Integer idx;

	public String getCreateTime() {
		return createTime;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdx() {
		return idx;
	}

	public String getImage() {
		return image;
	}

	public String getLabel() {
		return label;
	}

	public String getLink() {
		return link;
	}

	public Integer getType() {
		return type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
