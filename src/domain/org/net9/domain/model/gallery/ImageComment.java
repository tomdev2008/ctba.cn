package org.net9.domain.model.gallery;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "img_comment", uniqueConstraints = {})
public class ImageComment extends BaseModel implements java.io.Serializable {

	@Id
	private Integer id;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "imageId", unique = false, nullable = true)
	private ImageModel imageModel;

	private String username;

	private String body;

	private String updateTime;

	private String ip;

	public String getBody() {
		return body;
	}

	public Integer getId() {
		return id;
	}

	public ImageModel getImageModel() {
		return imageModel;
	}

	public String getIp() {
		return ip;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImageModel(ImageModel imageModel) {
		this.imageModel = imageModel;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}