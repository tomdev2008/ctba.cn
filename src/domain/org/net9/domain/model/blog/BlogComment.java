package org.net9.domain.model.blog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * BlogComment generated by MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "blog_comment", uniqueConstraints = {})
public class BlogComment extends BaseModel implements java.io.Serializable {

	// Fields
	private Long id;

	private String author;

	private BlogEntry blogBlogentry;

	private String body;

	private String email;

	private String updateTime;

	private String ip;

	private Short authenticated;

	// Constructors

	/** default constructor */
	public BlogComment() {
	}

	/** minimal constructor */
	public BlogComment(Long id, String author, BlogEntry blogBlogentry,
			String body, String email) {
		this.id = id;
		this.author = author;
		this.blogBlogentry = blogBlogentry;
		this.body = body;
		this.email = email;
	}

	/** full constructor */
	public BlogComment(Long id, String author, BlogEntry blogBlogentry,
			String body, String email, Short authenticated) {
		this.id = id;
		this.author = author;
		this.blogBlogentry = blogBlogentry;
		this.body = body;
		this.email = email;
		this.authenticated = authenticated;
	}

	@Column(name = "authenticated", unique = false, nullable = true, insertable = true, updatable = true)
	public Short getAuthenticated() {
		return this.authenticated;
	}

	@Column(name = "author", unique = false, nullable = false, insertable = true, updatable = true)
	public String getAuthor() {
		return author;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent", unique = false, nullable = false, insertable = true, updatable = true)
	public BlogEntry getBlogBlogentry() {
		return this.blogBlogentry;
	}

	@Column(name = "body", unique = false, nullable = false, insertable = true, updatable = true, length = 65535)
	public String getBody() {
		return this.body;
	}

	@Column(name = "email", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getEmail() {
		return this.email;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
	// Property accessors
	public Long getId() {
		return this.id;
	}

	@Column(name = "ip", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getIp() {
		return ip;
	}

	@Column(name = "updateTime", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getUpdateTime() {
		return updateTime;
	}

	public void setAuthenticated(Short authenticated) {
		this.authenticated = authenticated;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBlogBlogentry(BlogEntry blogBlogentry) {
		this.blogBlogentry = blogBlogentry;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}