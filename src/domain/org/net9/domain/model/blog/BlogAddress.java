package org.net9.domain.model.blog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "blog_address", uniqueConstraints = {})
public class BlogAddress extends BaseModel implements java.io.Serializable {

	private Integer id;

	private BlogCategory cat;

	private String username;

	private String createTime;

	private String updateTime;

	private String url;

	private Integer gotEntriesCnt;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "cid", unique = false, nullable = true)
	public BlogCategory getCat() {
		return this.cat;
	}

	public String getCreateTime() {
		return createTime;
	}

	public Integer getGotEntriesCnt() {
		return gotEntriesCnt;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getId() {
		return this.id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public void setCat(BlogCategory cat) {
		this.cat = cat;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setGotEntriesCnt(Integer gotEntriesCnt) {
		this.gotEntriesCnt = gotEntriesCnt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}