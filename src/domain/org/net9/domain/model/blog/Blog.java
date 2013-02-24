package org.net9.domain.model.blog;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "blog_blog", uniqueConstraints = {})
public class Blog extends BaseModel implements java.io.Serializable {

	private Integer id;

	private String theme;

	private String url;

	private String createTime;

	private String updateTime;

	private String image;

	private String description;

	private String author;

	private String name;

	private String backgroundMusic;

	private Integer hits;

	private Integer entryCnt;

	private String pingTargets;

	private String keyword;

	private String htmlBlock;

	private Integer listType = 0;// 0:全文,1:标题

	private Integer showGallery = 0;

	private Set<BlogEntry> blogBlogentries = new HashSet<BlogEntry>(0);

	private Set<BlogCategory> blogCategories = new HashSet<BlogCategory>(0);

	@Column(name = "author", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getAuthor() {
		return this.author;
	}

	public String getBackgroundMusic() {
		return backgroundMusic;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "blogBlog")
	public Set<BlogEntry> getBlogBlogentries() {
		return this.blogBlogentries;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "blogBlog")
	public Set<BlogCategory> getBlogCategories() {
		return this.blogCategories;
	}

	@Column(name = "createTime", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getCreateTime() {
		return this.createTime;
	}

	@Column(name = "description", unique = false, nullable = false, insertable = true, updatable = true, length = 250)
	public String getDescription() {
		return this.description;
	}

	public Integer getEntryCnt() {
		return entryCnt;
	}

	public Integer getHits() {
		return hits;
	}

	public String getHtmlBlock() {
		return htmlBlock;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getId() {
		return this.id;
	}

	@Column(name = "image", unique = false, nullable = true, insertable = true, updatable = true, length = 150)
	public String getImage() {
		return this.image;
	}

	public String getKeyword() {
		return keyword;
	}

	public Integer getListType() {
		return listType;
	}

	@Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getName() {
		return this.name;
	}

	public String getPingTargets() {
		return pingTargets;
	}

	public Integer getShowGallery() {
		return showGallery;
	}

	@Column(name = "theme", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getTheme() {
		return this.theme;
	}

	@Column(name = "updateTime", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getUpdateTime() {
		return this.updateTime;
	}

	@Column(name = "url", unique = false, nullable = true, insertable = true, updatable = true, length = 250)
	public String getUrl() {
		return this.url;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setBackgroundMusic(String backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}

	public void setBlogBlogentries(Set<BlogEntry> blogBlogentries) {
		this.blogBlogentries = blogBlogentries;
	}

	public void setBlogCategories(Set<BlogCategory> blogCategories) {
		this.blogCategories = blogCategories;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEntryCnt(Integer entryCnt) {
		this.entryCnt = entryCnt;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setHtmlBlock(String htmlBlock) {
		this.htmlBlock = htmlBlock;
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

	public void setListType(Integer listType) {
		this.listType = listType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPingTargets(String pingTargets) {
		this.pingTargets = pingTargets;
	}

	public void setShowGallery(Integer showGallery) {
		this.showGallery = showGallery;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}