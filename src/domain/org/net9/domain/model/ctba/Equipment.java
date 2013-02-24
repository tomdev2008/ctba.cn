package org.net9.domain.model.ctba;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "goods_wares")
public class Equipment extends BaseModel implements Serializable {

	private Integer id;
	private Integer uid;
	private String username;
	private String name;
	private String discription;
	private String brand;
	private String price;
	private String saleTime;
	private String shortname;
	private String num;
	private String keyword;
	private String createTime;
	private String updateTime;
	private String tech;
	private String officailprice;
	private Integer hits;
	private String image;
	private String link;
	private Integer type;
	private Integer sextype;
	private Double voteScore;
	private Integer categoryId;
	private Integer shopId;
	private Integer state;

	// private Integer commend = 0;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "brand", length = 145)
	public String getBrand() {
		return this.brand;
	}

	@Column(name = "create_time", nullable = false, length = 145)
	public String getCreateTime() {
		return this.createTime;
	}

	@Column(name = "discription", nullable = false, length = 65535)
	public String getDiscription() {
		return this.discription;
	}

	@Column(name = "hits", nullable = false)
	public Integer getHits() {
		return this.hits;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	@Column(name = "image", length = 145)
	public String getImage() {
		return this.image;
	}

	@Column(name = "keyword", length = 145)
	public String getKeyword() {
		return this.keyword;
	}

	@Column(name = "link", length = 145)
	public String getLink() {
		return this.link;
	}

	@Column(name = "name", nullable = false, length = 145)
	public String getName() {
		return this.name;
	}

	@Column(name = "num", length = 145)
	public String getNum() {
		return this.num;
	}

	@Column(name = "officailprice", length = 145)
	public String getOfficailprice() {
		return this.officailprice;
	}

	@Column(name = "price", length = 145)
	public String getPrice() {
		return this.price;
	}

	@Column(name = "saleTime", length = 145)
	public String getSaleTime() {
		return this.saleTime;
	}

	@Column(name = "sextype")
	public Integer getSextype() {
		return this.sextype;
	}

	@Column(name = "shortname", length = 145)
	public String getShortname() {
		return this.shortname;
	}

	@Column(name = "tech", length = 145)
	public String getTech() {
		return this.tech;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public Integer getUid() {
		return uid;
	}

	@Column(name = "update_time", nullable = false, length = 145)
	public String getUpdateTime() {
		return this.updateTime;
	}

	@Column(name = "username", nullable = false, length = 145)
	public String getUsername() {
		return this.username;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setOfficailprice(String officailprice) {
		this.officailprice = officailprice;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public void setSextype(Integer sextype) {
		this.sextype = sextype;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public void setTech(String tech) {
		this.tech = tech;
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

	public Double getVoteScore() {
		return voteScore;
	}

	public void setVoteScore(Double voteScore) {
		this.voteScore = voteScore;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

}
