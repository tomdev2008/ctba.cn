package org.net9.domain.model.ctba;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "goods_ware_scores")
public class EquipmentScore extends BaseModel implements Serializable {

    private Integer id;
    private String createTime;
    private String username;
    private Integer wareId;
    private Integer uid;
    private Integer value;

    @Column(name = "create_time", nullable = false, length = 45)
    public String getCreateTime() {
        return this.createTime;
    }

    @Id
    public Integer getId() {
        return this.id;
    }

    public Integer getUid() {
        return uid;
    }

    @Column(name = "username", nullable = false, length = 45)
    public String getUsername() {
        return this.username;
    }

    public Integer getValue() {
        return value;
    }

    @Column(name = "ware_id", nullable = false)
    public Integer getWareId() {
        return this.wareId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setWareId(Integer wareId) {
        this.wareId = wareId;
    }
}
