package org.net9.domain.model.subject;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

import com.j2bb.common.search.Index;

/**
 * subject topic
 * 
 * @author gladstone
 * 
 */
@Entity
@Table(name = "subject_topic", uniqueConstraints = {})
@SuppressWarnings("serial")
public class SubjectTopic extends BaseModel {
	@Id
	@Index(analyzed = false)
	Integer id;

	@Index
	String title;

	String keyword;

	Integer hits;

	@Index
	String description;

	String content;

	@Index(analyzed = false)
	String author;

	@Index(analyzed = false)
	String createTime;

	String updateTime;

	// 'e'- blog entry,'t'-bbs topic
	String type;

	@Index(analyzed = false)
	Integer topicId;

	@Index(analyzed = false)
	Integer subjectId;

	@Index(analyzed = false)
	Integer entryId;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

}
