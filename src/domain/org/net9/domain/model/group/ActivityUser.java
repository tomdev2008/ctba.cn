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
@Table(name = "group_activity_user", uniqueConstraints = {})
public class ActivityUser extends BaseModel implements java.io.Serializable {

	private Integer id;

	private String username;

	private ActivityModel activityModel;

	private Integer role;

	private Integer joinCnt = 1;

	public Integer getJoinCnt() {
		return joinCnt;
	}

	public void setJoinCnt(Integer joinCnt) {
		this.joinCnt = joinCnt;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "activity_id", unique = false, nullable = false, insertable = true, updatable = true)
	public ActivityModel getActivityModel() {
		return this.activityModel;
	}

	public void setActivityModel(ActivityModel activityModel) {
		this.activityModel = activityModel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}