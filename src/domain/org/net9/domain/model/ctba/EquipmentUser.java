package org.net9.domain.model.ctba;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "goods_ware_users")
public class EquipmentUser extends BaseModel implements Serializable {
	@Id
	private Integer id;
    
	@Column(name = "ware_id", nullable = false)
	private Integer wareId;

	private String username;

	private Integer uid;

	private Integer type;

	private String memo;

	@Column(name = "update_time", nullable = false)
	private String updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getMemo() {
		return memo;
	}

	public Integer getType() {
		return type;
	}

	public Integer getUid() {
		return uid;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUsername() {
		return username;
	}

	public Integer getWareId() {
		return wareId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setWareId(Integer wareId) {
		this.wareId = wareId;
	}

}
