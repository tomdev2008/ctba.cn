package org.net9.domain.model.group;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "group_activity", uniqueConstraints = {})
public class ActivityModel extends BaseModel implements java.io.Serializable {

	private Integer id;

	private String username;

	private GroupModel groupModel;

	// '活动标题'
	private String title;

	// '内容简介',
	private String content;

	// '活动开始时间',
	private String startTime;

	// '活动结束时间',
	private String endTime;

	// '活动地点',
	private String place;

	// '活动类型
	private Integer type;

	// '活动私秘状态 0私秘 1 公开',
	private Integer privateState;

	// '活动图标',
	private String pic;

	// '活动发布时间',
	private String createTime;

	private String updateTime;

	// '活动查看次数',
	private Integer hits;

	// 推荐指数',
	private Integer recommendIndex;

	// 活动标签 各个类型用 | 分开',
	private String tag;

	// 参加人数限制
	private Integer memberLimit = 0;

	// #901 (活动增加截止报名)
	private Integer stopped = 0;

	// #908 (用户团购订单管理功能) 可以通过小组活动收费
	private Integer recieveMoney = 0;

	public String getContent() {
		return content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", unique = false, nullable = false, insertable = true, updatable = true)
	public GroupModel getGroupModel() {
		return this.groupModel;
	}

	public Integer getHits() {
		return hits;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	public Integer getMemberLimit() {
		return memberLimit;
	}

	public String getPic() {
		return pic;
	}

	public String getPlace() {
		return place;
	}

	public Integer getPrivateState() {
		return privateState;
	}

	public Integer getRecieveMoney() {
		return recieveMoney;
	}

	public Integer getRecommendIndex() {
		return recommendIndex;
	}

	public String getStartTime() {
		return startTime;
	}

	public Integer getStopped() {
		return stopped;
	}

	public String getTag() {
		return tag;
	}

	public String getTitle() {
		return title;
	}

	public Integer getType() {
		return type;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setGroupModel(GroupModel groupModel) {
		this.groupModel = groupModel;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setPrivateState(Integer privateState) {
		this.privateState = privateState;
	}

	public void setRecieveMoney(Integer recieveMoney) {
		this.recieveMoney = recieveMoney;
	}

	public void setRecommendIndex(Integer recommendIndex) {
		this.recommendIndex = recommendIndex;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setStopped(Integer stopped) {
		this.stopped = stopped;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}