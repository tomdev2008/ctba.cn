package org.net9.domain.model.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * NewsComment generated by MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "news_comment", uniqueConstraints = {})
public class NewsComment extends BaseModel implements java.io.Serializable {

	// Fields

	private Integer id;

	private String title;

	private String content;

	private Integer newsId;

	private String author;

	private String ip;

	private String createTime;

	private String updateTime;

	private Integer hitBad;

	private Integer hitGood;

	// Constructors

	/** default constructor */
	public NewsComment() {
	}

	/** minimal constructor */
	public NewsComment(Integer id, String title, String content,
			Integer newsId, String author, String createTime, String updateTime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.newsId = newsId;
		this.author = author;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public NewsComment(Integer id, String title, String content,
			Integer newsId, String author, String ip, String createTime,
			String updateTime, Integer hitBad, Integer hitGood) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.newsId = newsId;
		this.author = author;
		this.ip = ip;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.hitBad = hitBad;
		this.hitGood = hitGood;
	}

	@Column(name = "AUTHOR", unique = false, nullable = false, insertable = true, updatable = true, length = 85)
	public String getAuthor() {
		return this.author;
	}

	@Column(name = "CONTENT", unique = false, nullable = false, insertable = true, updatable = true, length = 65535)
	public String getContent() {
		return this.content;
	}

	@Column(name = "CREATE_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 45)
	public String getCreateTime() {
		return this.createTime;
	}

	@Column(name = "HIT_BAD", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getHitBad() {
		return this.hitBad;
	}

	@Column(name = "HIT_GOOD", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getHitGood() {
		return this.hitGood;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "IP", unique = false, nullable = true, insertable = true, updatable = true, length = 65)
	public String getIp() {
		return this.ip;
	}

	@Column(name = "NEWS_ID", unique = false, nullable = false, insertable = true, updatable = true)
	public Integer getNewsId() {
		return this.newsId;
	}

	@Column(name = "TITLE", unique = false, nullable = false, insertable = true, updatable = true, length = 245)
	public String getTitle() {
		return this.title;
	}

	@Column(name = "UPDATE_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 45)
	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setHitBad(Integer hitBad) {
		this.hitBad = hitBad;
	}

	public void setHitGood(Integer hitGood) {
		this.hitGood = hitGood;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}