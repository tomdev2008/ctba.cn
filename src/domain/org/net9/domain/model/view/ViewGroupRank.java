package org.net9.domain.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * not in use
 * 
 * @author gladstone
 * @since Dec 1, 2008
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "view_group_rank", uniqueConstraints = {})
public class ViewGroupRank extends BaseModel {
	@Id
	private Integer id;

	/** 名称 */
	private String name;

	/** 创建时间 */
	private String createTime;

	/** 更新时间 */
	private String updateTime;

	/** 责任者 */
	private String manager;

	/** 描述 */
	private String discription;

	/** 图片 */
	private String face;

	/** 类型 */
	private String type;

	/** 代码，唯一 */
	private String code;

	/** 点击量 */
	private Integer hits;

	private String tags;

	private String magicUrl;

	private Integer galleryShowType = 0;

	@Column(name = "topic_cnt")
	private Integer topicCnt = 0;
	@Column(name = "reply_cnt")
	private Integer replyCnt = 0;
	@Column(name = "user_cnt")
	private Integer userCnt = 0;
	@Column(name = "rank_rate")
	private Double rankRate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getMagicUrl() {
		return magicUrl;
	}

	public void setMagicUrl(String magicUrl) {
		this.magicUrl = magicUrl;
	}

	public Integer getGalleryShowType() {
		return galleryShowType;
	}

	public void setGalleryShowType(Integer galleryShowType) {
		this.galleryShowType = galleryShowType;
	}

	public Integer getTopicCnt() {
		return topicCnt;
	}

	public void setTopicCnt(Integer topicCnt) {
		this.topicCnt = topicCnt;
	}

	public Integer getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(Integer replyCnt) {
		this.replyCnt = replyCnt;
	}

	public Integer getUserCnt() {
		return userCnt;
	}

	public void setUserCnt(Integer userCnt) {
		this.userCnt = userCnt;
	}

	public Double getRankRate() {
		return rankRate;
	}

	public void setRankRate(Double rankRate) {
		this.rankRate = rankRate;
	}
}
