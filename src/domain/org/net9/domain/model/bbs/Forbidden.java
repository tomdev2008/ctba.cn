package org.net9.domain.model.bbs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@Entity
@Table(name = "bbs_forbiddens", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Forbidden extends BaseModel {
	@Id
	@Column(name = "fbnId", unique = true)
	Long fbnId;

	String fbnUser;

	Integer fbnBoardId;

	String fbnReason;

	String fbnTime;

	Integer fbnDays;

	String fbnBm;

	public String getFbnBm() {
		return fbnBm;
	}

	public void setFbnBm(String fbnBm) {
		this.fbnBm = fbnBm;
	}

	public Integer getFbnBoardId() {
		return fbnBoardId;
	}

	public void setFbnBoardId(Integer fbnBoardId) {
		this.fbnBoardId = fbnBoardId;
	}

	public Integer getFbnDays() {
		return fbnDays;
	}

	public void setFbnDays(Integer fbnDays) {
		this.fbnDays = fbnDays;
	}

	public Long getFbnId() {
		return fbnId;
	}

	public void setFbnId(Long fbnId) {
		this.fbnId = fbnId;
	}

	public String getFbnReason() {
		return fbnReason;
	}

	public void setFbnReason(String fbnReason) {
		this.fbnReason = fbnReason;
	}

	public String getFbnTime() {
		return fbnTime;
	}

	public void setFbnTime(String fbnTime) {
		this.fbnTime = fbnTime;
	}

	public String getFbnUser() {
		return fbnUser;
	}

	public void setFbnUser(String fbnUser) {
		this.fbnUser = fbnUser;
	}

}
