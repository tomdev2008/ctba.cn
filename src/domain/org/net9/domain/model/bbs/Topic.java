package org.net9.domain.model.bbs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

import com.j2bb.common.search.Index;

@SuppressWarnings("serial")
@Entity
@Table(name = "bbs_topics", uniqueConstraints = {})
public class Topic extends BaseModel implements java.io.Serializable {
	@Id
	@Index(analyzed = false)
	private Integer topicId;// 索引

	@Index
	private String topicTitle;// 标题
	// @Index
	private String topicContent;// 内容

	@Index(analyzed = false)
	private String topicAuthor;// 作者

	@Index(analyzed = false)
	private Integer topicBoardId;// 所属板块

	private Integer topicHits;// 点击数

	private Integer topicReNum;// 多少re文

	private String topicIP;// ip

	private Integer topicIsRe;// 是否re文

	private Integer topicOriginId;// 指向文章

	@Index(analyzed = false)
	private String topicTime;// 发表时间

	private String topicAttachName;// 附件名称

	private String topicAttachPath;// 附件路径

	private Integer topicState;// 状态1-原文 2-不可re(只读)4-标记为提醒

	/** #852 (CTBA社区财富(CTB)) 积分交易 */
	private Integer topicScore;// 分数

	private Integer topicPrime;// 精华

	private Integer topicTop;// 置顶

	private Integer topicPrivate;// added by gladstone 文章是否公开

	private String topicUpdateTime;

	public String getTopicUpdateTime() {
		return topicUpdateTime;
	}

	public void setTopicUpdateTime(String topicUpdateTime) {
		this.topicUpdateTime = topicUpdateTime;
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

	public String getTopicIP() {
		return topicIP;
	}

	public void setTopicIP(String topicIP) {
		this.topicIP = topicIP;
	}

	public Integer getTopicIsRe() {
		return topicIsRe;
	}

	public void setTopicIsRe(Integer topicIsRe) {
		this.topicIsRe = topicIsRe;
	}

	public Integer getTopicOriginId() {
		return topicOriginId;
	}

	public void setTopicOriginId(Integer topicOriginId) {
		this.topicOriginId = topicOriginId;
	}

	public Integer getTopicPrime() {
		return topicPrime;
	}

	public void setTopicPrime(Integer topicPrime) {
		this.topicPrime = topicPrime;
	}

	public Integer getTopicReNum() {
		return topicReNum;
	}

	public void setTopicReNum(Integer topicReNum) {
		this.topicReNum = topicReNum;
	}

	public Integer getTopicScore() {
		return topicScore;
	}

	public void setTopicScore(Integer topicScore) {
		this.topicScore = topicScore;
	}

	public Integer getTopicState() {
		return topicState;
	}

	public void setTopicState(Integer topicState) {
		this.topicState = topicState;
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

	public Integer getTopicTop() {
		return topicTop;
	}

	public void setTopicTop(Integer topicTop) {
		this.topicTop = topicTop;
	}

	public Integer getTopicPrivate() {
		return topicPrivate;
	}

	public void setTopicPrivate(Integer topicPrivate) {
		this.topicPrivate = topicPrivate;
	}

	// public Integer getTopicInIndex() {
	// return topicInIndex;
	// }
	//
	// public void setTopicInIndex(Integer topicInIndex) {
	// this.topicInIndex = topicInIndex;
	// }

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

}
