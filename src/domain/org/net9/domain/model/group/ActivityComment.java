package org.net9.domain.model.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "group_activity_comment")
public class ActivityComment extends BaseModel implements java.io.Serializable {
	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer id;

	private String author;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "activityId", unique = false, nullable = false, insertable = true, updatable = true)
	private ActivityModel activity;

	private String body;

	private String updateTime;

	private String ip;

	public ActivityModel getActivity() {
		return activity;
	}

	public String getAuthor() {
		return author;
	}

	public String getBody() {
		return body;
	}

	public Integer getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setActivity(ActivityModel activity) {
		this.activity = activity;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}