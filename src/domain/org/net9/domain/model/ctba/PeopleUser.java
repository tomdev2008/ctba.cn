//package org.net9.domain.model.ctba;
//
//import java.io.Serializable;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.net9.domain.model.BaseModel;
//
//@SuppressWarnings("serial")
//@Entity
//@Table(name = "people_users")
//public class PeopleUser extends BaseModel implements Serializable {
//	@Id
//	private Integer id;
//	private Integer type;
//	private String createTime;
//	private String updateTime;
//	private String username;
//	private String content;
//	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
//	@JoinColumn(name = "peopleId", unique = false, nullable = true)
//	private People people;
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public Integer getType() {
//		return type;
//	}
//
//	public void setType(Integer type) {
//		this.type = type;
//	}
//
//	public String getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(String createTime) {
//		this.createTime = createTime;
//	}
//
//	public String getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(String updateTime) {
//		this.updateTime = updateTime;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public People getPeople() {
//		return people;
//	}
//
//	public void setPeople(People people) {
//		this.people = people;
//	}
//
//}
