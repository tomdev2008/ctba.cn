package org.net9.domain.model.group;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

import com.j2bb.common.search.Index;

@SuppressWarnings("serial")
@Entity
@Table(name = "group_topic", uniqueConstraints = {})
public class GroupTopic extends BaseModel implements java.io.Serializable {

    @Index(analyzed = false)
    private Integer id;
    
    @Index(analyzed = false)
    private String author;
    
    private GroupModel groupModel;
    
    @Index
    private String title;
    
    // @Index
    private String content;
    
    private Integer parent;
    
    private String createTime;
    
    @Index(analyzed = false)
    private String updateTime;
    
    private String ip;
    
    private Integer goodHits;
    
    private Integer badHits;
    
    @Index
    private Integer hits;
    @Index
    private Integer replyCnt;// 回复数，默认0
    
    private String attachment;
    
    private Integer topState;

    public Integer getTopState() {
        return topState;
    }

    public void setTopState(Integer topState) {
        this.topState = topState;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getBadHits() {
        return badHits;
    }

    public String getContent() {
        return this.content;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public Integer getGoodHits() {
        return goodHits;
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

    public String getIp() {
        return this.ip;
    }

    public Integer getParent() {
        return this.parent;
    }

    public Integer getReplyCnt() {
        return replyCnt;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBadHits(Integer badHits) {
        this.badHits = badHits;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setGoodHits(Integer goodHits) {
        this.goodHits = goodHits;
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

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public void setReplyCnt(Integer replyCnt) {
        this.replyCnt = replyCnt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}