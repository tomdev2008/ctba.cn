package org.net9.domain.model.group;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "group_model", uniqueConstraints = {})
public class GroupModel extends BaseModel implements java.io.Serializable {

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

	/** 是否删除 */
	private Integer deleteFlag;

	/** 删除理由 */
	private String deleteReason;

	private Set<GroupTopic> groupTopics = new HashSet<GroupTopic>(0);

	private Set<GroupUser> groupUsers = new HashSet<GroupUser>(0);

	/* ######Added by gladstone @2007-11###### */
	/** 代码，唯一 */
	private String code;

	/** 点击量 */
	private Integer hits;

	/** log 功能 */
	private Integer useLog4us;

	/** 认证水平,0为会员 1为申请会员，2为需要认证，默认1 */
	private Integer authLevel;

	private String tags;
	
	private String magicUrl;
	
	private Integer galleryShowType=0;
	
	/* ###### End Added by gladstone @2007-11###### */

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getDiscription() {
		return this.discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getFace() {
		return this.face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "groupModel")
	public Set<GroupTopic> getGroupTopics() {
		return this.groupTopics;
	}

	public void setGroupTopics(Set<GroupTopic> groupTopics) {
		this.groupTopics = groupTopics;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "groupModel")
	public Set<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public Integer getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(Integer authLevel) {
		this.authLevel = authLevel;
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

	public Integer getUseLog4us() {
		return useLog4us;
	}

	public void setUseLog4us(Integer useLog4us) {
		this.useLog4us = useLog4us;
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

}