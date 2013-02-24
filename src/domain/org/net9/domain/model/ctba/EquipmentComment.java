package org.net9.domain.model.ctba;

import java.io.Serializable;

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
@Table(name = "goods_comments")
public class EquipmentComment extends BaseModel implements Serializable {

    @Id
    private Integer id;
    @Column(name = "create_time", nullable = false, length = 45)
    private String createTime;
    @Column(name = "update_time", nullable = false, length = 45)
    private String updateTime;
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ware_id", unique = false, nullable = false, insertable = true, updatable = true)
    private Equipment goodsWare;
    @Column(name = "parent_id")
    private Integer parentId;
    private Integer uid;
    @Column(name = "title", length = 145)
    private String title;
    @Column(name = "content", nullable = false, length = 65535)
    private String content;

    public String getContent() {
        return this.content;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getUid() {
        return uid;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public String getUsername() {
        return this.username;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Equipment getGoodsWare() {
        return goodsWare;
    }

    public void setGoodsWare(Equipment goodsWare) {
        this.goodsWare = goodsWare;
    }
}
