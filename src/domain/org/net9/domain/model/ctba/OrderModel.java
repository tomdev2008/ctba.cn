package org.net9.domain.model.ctba;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "shop_orders")
public class OrderModel extends BaseModel implements Serializable {
	@Id
	private Integer id;
	private String title;
	@Column(name = "create_time", nullable = false, length = 45)
	private String createTime;
	@Column(name = "update_time", nullable = false, length = 45)
	private String updateTime;
	@Column(name = "reference_url", length = 145)
	private String referenceUrl;
	private String description;
	private String username;
	private String phone;
	private String address;
	@Column(name = "post_number")
	private String postNumber;
	@Column(name = "reciever_name")
	private String recieverName;
	@Column(name = "fetch_type")
	private Integer fetchType;
	private String owner;// 谁将最后收费

	@Column(name = "trans_state", nullable = false, length = 45)
	private Integer transState;

	private Integer type;

	private Double price;

	public String getCreateTime() {
		return createTime;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public Double getPrice() {
		return price;
	}

	public String getReferenceUrl() {
		return referenceUrl;
	}

	public String getTitle() {
		return title;
	}

	public Integer getTransState() {
		return transState;
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

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTransState(Integer transState) {
		this.transState = transState;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRecieverName() {
		return recieverName;
	}

	public void setRecieverName(String recieverName) {
		this.recieverName = recieverName;
	}

	public Integer getFetchType() {
		return fetchType;
	}

	public void setFetchType(Integer fetchType) {
		this.fetchType = fetchType;
	}

	public String getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}

}
