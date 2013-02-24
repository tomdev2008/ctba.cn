package org.net9.domain.model.blog;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

import com.j2bb.common.search.Index;

@SuppressWarnings("serial")
@Entity
@Table(name = "blog_blogentry", uniqueConstraints = {})
public class BlogEntry extends BaseModel implements java.io.Serializable {
	@Index(analyzed = false)
	private Integer id;

	private Blog blogBlog;

	/** 未使用 */
	private String permalink;
	// @Index
	private String body;

	@Index(analyzed = false)
	private String author;

	@Index(analyzed = false)
	private Integer hits;

	@Index(analyzed = false)
	private String date;

	private String publishDate;

	@Index
	private String title;

	/** 未使用 */
	private String subtitle;

	/** 未使用 */
	private String tags;

	/** 未使用 */
	private Short commentsEnabled;

	private Integer state;
	@Index
	private Integer categoryId;

	private Integer type = 1000;

	@Index
	private Integer commentCnt = 0;

	private Set<BlogComment> blogComments = new HashSet<BlogComment>(0);

	@Column(name = "author", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getAuthor() {
		return this.author;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "blogId", unique = false, nullable = false, insertable = true, updatable = true)
	public Blog getBlogBlog() {
		return this.blogBlog;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "blogBlogentry")
	public Set<BlogComment> getBlogComments() {
		return this.blogComments;
	}

	@Column(name = "body", unique = false, nullable = false, insertable = true, updatable = true, length = 65535)
	public String getBody() {
		return this.body;
	}

	@Column(name = "categoryId", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public Integer getCommentCnt() {
		return commentCnt;
	}

	@Column(name = "commentsEnabled", unique = false, nullable = true, insertable = true, updatable = true)
	public Short getCommentsEnabled() {
		return this.commentsEnabled;
	}

	@Column(name = "date", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getDate() {
		return this.date;
	}

	@Column(name = "hits", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getHits() {
		return this.hits;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "permalink", unique = false, nullable = true, insertable = true, updatable = true, length = 150)
	public String getPermalink() {
		return this.permalink;
	}

	@Column(name = "state", unique = false, nullable = true, insertable = true, updatable = true)
	public Integer getState() {
		return this.state;
	}

	@Column(name = "subtitle", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getSubtitle() {
		return this.subtitle;
	}

	@Column(name = "tags", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getTags() {
		return this.tags;
	}

	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getTitle() {
		return this.title;
	}

	public Integer getType() {
		return type;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBlogBlog(Blog blogBlog) {
		this.blogBlog = blogBlog;
	}

	public void setBlogComments(Set<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}

	public void setCommentsEnabled(Short commentsEnabled) {
		this.commentsEnabled = commentsEnabled;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

}