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
//@Table(name = "people_comments")
//public class PeopleComment extends BaseModel implements Serializable {
//	@Id
//	private Integer id;
//	private String ip;
//	private String createTime;
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
//	public String getIp() {
//		return ip;
//	}
//
//	public void setIp(String ip) {
//		this.ip = ip;
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
