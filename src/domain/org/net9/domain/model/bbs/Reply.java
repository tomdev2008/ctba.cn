package org.net9.domain.model.bbs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * reply of topic
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "bbs_replies", uniqueConstraints = {})
public class Reply extends BaseModel {
	@Id
	Integer topicId;// 索引

	String topicTitle;// 标题

	String topicContent;// 内容

	String topicAuthor;// 作者

	Integer topicBoardId;// 所属板块

	Integer topicHits;// 点击数

	String topicIP;// ip

	Integer topicOriginId;// 指向文章

	String topicTime;// 发表时间

	String topicAttachName;// 附件名称

	String topicAttachPath;// 附件路径

	Integer topicGoodHits;

	Integer topicBadHits;

	public Integer getTopicBadHits() {
		return topicBadHits;
	}

	public void setTopicBadHits(Integer topicBadHits) {
		this.topicBadHits = topicBadHits;
	}

	public Integer getTopicGoodHits() {
		return topicGoodHits;
	}

	public void setTopicGoodHits(Integer topicGoodHits) {
		this.topicGoodHits = topicGoodHits;
	}

	public String getTopicAttachName() {
		return topicAttachName;
	}

	public void setTopicAttachName(String topicAttachName) {
		this.topicAttachName = topicAttachName;
	}

	public String getTopicAttachPath() {
		return topicAttachPath;
	}

	public void setTopicAttachPath(String topicAttachPath) {
		this.topicAttachPath = topicAttachPath;
	}

	public String getTopicAuthor() {
		return topicAuthor;
	}

	public void setTopicAuthor(String topicAuthor) {
		this.topicAuthor = topicAuthor;
	}

	public Integer getTopicBoardId() {
		return topicBoardId;
	}

	public void setTopicBoardId(Integer topicBoardId) {
		this.topicBoardId = topicBoardId;
	}

	public String getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}

	public Integer getTopicHits() {
		return topicHits;
	}

	public void setTopicHits(Integer topicHits) {
		this.topicHits = topicHits;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getTopicIP() {
		return topicIP;
	}

	public void setTopicIP(String topicIP) {
		this.topicIP = topicIP;
	}

	public Integer getTopicOriginId() {
		return topicOriginId;
	}

	public void setTopicOriginId(Integer topicOriginId) {
		this.topicOriginId = topicOriginId;
	}

	public String getTopicTime() {
		return topicTime;
	}

	public void setTopicTime(String topicTime) {
		this.topicTime = topicTime;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

}
