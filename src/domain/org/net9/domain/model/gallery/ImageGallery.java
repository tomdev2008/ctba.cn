package org.net9.domain.model.gallery;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "img_gallery", uniqueConstraints = {})
public class ImageGallery extends BaseModel implements java.io.Serializable {
	@Id
	private Integer id;

	private String name;

	private String createTime;

	private String updateTime;

	// @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	// @JoinColumn(name = "username",referencedColumnName="username", unique =
	// false, nullable = false)
	// private MainUser user;
	private String username;
	private String description;

	/** 封面 */
	private String face;

	private String place;

	/** 点击量 */
	private Integer hits;

	private Integer viewOption;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	//
	// public MainUser getUser() {
	// return user;
	// }
	//
	// public void setUser(MainUser user) {
	// this.user = user;
	// }

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getViewOption() {
		return viewOption;
	}

	public void setViewOption(Integer viewOption) {
		this.viewOption = viewOption;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}