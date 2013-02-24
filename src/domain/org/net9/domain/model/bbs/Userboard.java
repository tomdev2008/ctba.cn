package org.net9.domain.model.bbs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * 建立用户和板块的关联
 * 
 * @author gladstone
 * 
 */
@Entity
@Table(name = "bbs_userboard", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Userboard extends BaseModel {
	@Id
	Integer id;

	/** 用户ID */
	Integer user_id;

	/** 板块ID */
	Integer board_id;

	/** 角色 */
	Integer role;

//	Integer user_state;

	/** 创建时间 */
	String create_time;

	/** 用户发帖数 */
	Integer topic_cnt;

	// 更新的贴子数量，

	public Integer getBoard_id() {
		return board_id;
	}

	public void setBoard_id(Integer board_id) {
		this.board_id = board_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getTopic_cnt() {
		return topic_cnt;
	}

	public void setTopic_cnt(Integer topic_cnt) {
		this.topic_cnt = topic_cnt;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Integer getUser_state() {
//		return user_state;
//	}
//
//	public void setUser_state(Integer user_state) {
//		this.user_state = user_state;
//	}

}
