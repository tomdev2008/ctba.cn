package org.net9.domain.model.group;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "group_link", uniqueConstraints = {})
public class GroupLink extends BaseModel implements java.io.Serializable {
	@Id
	private Integer id;

	private String username;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "groupId", unique = false, nullable = false, insertable = true, updatable = true)
	private GroupModel groupModel;

	private String label;

	private String url;

	private Integer idx;

	private String createTime;

	private String updateTime;

	public String getCreateTime() {
		return createTime;
	}

	public GroupModel getGroupModel() {
		return groupModel;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdx() {
		return idx;
	}

	public String getLabel() {
		return label;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setGroupModel(GroupModel groupModel) {
		this.groupModel = groupModel;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}