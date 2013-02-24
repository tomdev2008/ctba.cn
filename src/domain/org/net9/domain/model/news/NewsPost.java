package org.net9.domain.model.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "news_post", uniqueConstraints = {})
public class NewsPost extends BaseModel implements java.io.Serializable {

	private Integer id;

	private String author;

	private String title;

	private String content;

	private String createTime;

	private String updateTime;

	private String ip;

	private Integer done;

	private Integer cat;

	@Column(name = "AUTHOR", unique = false, nullable = false, insertable = true, updatable = true, length = 85)
	public String getAuthor() {
		return this.author;
	}

	public Integer getCat() {
		return cat;
	}

	@Column(name = "CONTENT", unique = false, nullable = false, insertable = true, updatable = true, length = 65535)
	public String getContent() {
		return this.content;
	}

	@Column(name = "CREATE_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 45)
	public String getCreateTime() {
		return this.createTime;
	}

	public Integer getDone() {
		return done;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "IP", unique = false, nullable = true, insertable = true, updatable = true, length = 45)
	public String getIp() {
		return this.ip;
	}

	@Column(name = "TITLE", unique = false, nullable = false, insertable = true, updatable = true, length = 245)
	public String getTitle() {
		return this.title;
	}

	@Column(name = "UPDATE_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 45)
	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setCat(Integer cat) {
		this.cat = cat;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}