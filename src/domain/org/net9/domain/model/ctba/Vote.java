package org.net9.domain.model.ctba;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@Entity
@Table(name = "bbs_votes", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Vote extends BaseModel {

	@Id
	private Integer id;

	private Integer boardId;

	private Integer type;

	private Integer canBeOther;

	private String username;

	private String updateTime;

	private String overTime;

	private String question;

	private String answers;

	private String memo;

	private Integer maxNum;

	private Integer hits = 0;

	public String getAnswers() {
		return answers;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public Integer getCanBeOther() {
		return canBeOther;
	}

	public Integer getHits() {
		return hits;
	}

	public Integer getId() {
		return id;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public String getMemo() {
		return memo;
	}

	public String getOverTime() {
		return overTime;
	}

	public String getQuestion() {
		return question;
	}

	public Integer getType() {
		return type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public void setCanBeOther(Integer canBeOther) {
		this.canBeOther = canBeOther;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
