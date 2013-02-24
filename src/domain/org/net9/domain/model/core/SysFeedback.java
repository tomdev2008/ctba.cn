package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * #807 (帖子设置举报按钮，与用户积分联系)
 * 
 * @author gladstone
 * 
 */
@Entity
@Table(name = "sys_feedbacks", uniqueConstraints = {})
@SuppressWarnings("serial")
public class SysFeedback extends BaseModel {

	@Id
	private Integer id;

	private String username;

	private String url;

	private String description;

	private String createTime;

	private Integer scoreAdded;

	private Integer type;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getScoreAdded() {
		return scoreAdded;
	}

	public void setScoreAdded(Integer scoreAdded) {
		this.scoreAdded = scoreAdded;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
