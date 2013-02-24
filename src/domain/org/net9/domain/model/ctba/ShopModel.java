package org.net9.domain.model.ctba;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "shop_models")
public class ShopModel extends BaseModel implements Serializable {
	@Id
	private Integer id;

	private String name;

	@Column(name = "create_time", nullable = false, length = 45)
	private String createTime;

	@Column(name = "update_time", nullable = false, length = 45)
	private String updateTime;

	private String logo;

	private Integer hits;

	@Column(name = "ref_group_id")
	private Integer refGroupId;

	private String description;

	@Column(name = "equipment_cnt")
	private Integer equipmentCnt = 0;

	private String username;

	private Integer type;

	@Column(name = "main_biz")
	private String mainBiz;

	@Column(name = "login_username", nullable = true)
	private String loginUsername;

	@Column(name = "login_password", nullable = true)
	private String loginPassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getRefGroupId() {
		return refGroupId;
	}

	public void setRefGroupId(Integer refGroupId) {
		this.refGroupId = refGroupId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEquipmentCnt() {
		return equipmentCnt;
	}

	public void setEquipmentCnt(Integer equipmentCnt) {
		this.equipmentCnt = equipmentCnt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMainBiz() {
		return mainBiz;
	}

	public void setMainBiz(String mainBiz) {
		this.mainBiz = mainBiz;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
