package org.net9.domain.model.blog;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * blog link
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "blog_link", uniqueConstraints = {})
public class BlogLink extends BaseModel implements java.io.Serializable {

	private Integer id;

	private Integer blogId;

	private String url;

	private String title;

	private String imageUrl;

	private String updateTime;

	private Integer hit;

	// 2008/04/20

	private Integer idx;

	public Integer getBlogId() {
		return blogId;
	}

	public Integer getHit() {
		return this.hit;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	public Integer getIdx() {
		return idx;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public void setHit(Integer hit) {
		this.hit = hit;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}