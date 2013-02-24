package org.net9.domain.model.ctba;

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
@Table(name = "bbs_votecomments", uniqueConstraints = {})
public class VoteComment  extends BaseModel implements java.io.Serializable {

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer id;

	@Column(name = "username", unique = false, nullable = false, insertable = true, updatable = true)
	private String username;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "voteId", unique = false, nullable = false, insertable = true, updatable = true)
	private Vote vote;

	@Column(name = "body", unique = false, nullable = false, insertable = true, updatable = true, length = 65535)
	private String body;

	@Column(name = "updateTime", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String updateTime;

	@Column(name = "ip", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String ip;

	public String getBody() {
		return this.body;
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

	public String getUsername() {
		return username;
	}

	public Vote getVote() {
		return vote;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

}