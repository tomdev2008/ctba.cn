package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * not in use
 * 
 * @author gladstone
 * @since Dec 1, 2008
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "view_random_group", uniqueConstraints = {})
public class ViewRandomGroup extends BaseModel {
	@Id
	private Integer id;

	/** 名称 */
	private String name;

	/** 创建时间 */
	private String createTime;

	/** 更新时间 */
	private String updateTime;

	/** 责任者 */
	private String manager;

	/** 描述 */
	private String discription;

	/** 图片 */
	private String face;

	/** 类型 */
	private String type;

	/** 点击量 */
	private Integer hits;

	public String getCreateTime() {
		return createTime;
	}

	public String getDiscription() {
		return discription;
	}

	public String getFace() {
		return face;
	}

	public Integer getHits() {
		return hits;
	}

	public Integer getId() {
		return id;
	}

	public String getManager() {
		return manager;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
