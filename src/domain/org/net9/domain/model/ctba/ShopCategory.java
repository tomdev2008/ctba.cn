package org.net9.domain.model.ctba;

import java.io.Serializable;

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
@Table(name = "shop_categories")
public class ShopCategory extends BaseModel implements Serializable {
	@Id
	private Integer id;

	@Column(name = "create_time", nullable = false, length = 45)
	private String createTime;

	@Column(name = "update_time", nullable = false, length = 45)
	private String updateTime;

	private String username;
	
	@Column(name = "equipment_cnt", nullable = false, length = 45)
	private Integer equipmentCnt = 0;

	private Integer idx = 0;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id", unique = false, nullable = false, insertable = true, updatable = true)
	private ShopModel shopModel;

	@Column(name = "label", length = 145)
	private String label;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getEquipmentCnt() {
		return equipmentCnt;
	}

	public void setEquipmentCnt(Integer equipmentCnt) {
		this.equipmentCnt = equipmentCnt;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public ShopModel getShopModel() {
		return shopModel;
	}

	public void setShopModel(ShopModel shopModel) {
		this.shopModel = shopModel;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
