package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;
@Entity
@Table(name = "bbs_friends", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Friend extends BaseModel {
	@Id
	Integer frdId;

	String frdUserMe;

	String frdUserYou;

	String frdMemo;
	
	String frdTag;

	public String getFrdTag() {
		return frdTag;
	}

	public void setFrdTag(String frdTag) {
		this.frdTag = frdTag;
	}

	public Integer getFrdId() {
		return frdId;
	}

	public void setFrdId(Integer frdId) {
		this.frdId = frdId;
	}

	public String getFrdMemo() {
		return frdMemo;
	}

	public void setFrdMemo(String frdMemo) {
		this.frdMemo = frdMemo;
	}

	public String getFrdUserMe() {
		return frdUserMe;
	}

	public void setFrdUserMe(String frdUserMe) {
		this.frdUserMe = frdUserMe;
	}

	public String getFrdUserYou() {
		return frdUserYou;
	}

	public void setFrdUserYou(String frdUserYou) {
		this.frdUserYou = frdUserYou;
	}

}
