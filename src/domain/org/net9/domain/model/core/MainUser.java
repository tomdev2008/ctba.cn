package org.net9.domain.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "main_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class MainUser extends BaseModel {
	@Id
	@Column(name = "id", unique = true)
	private Integer id;

	private String username;

	private String password;

	private Integer sex;

	private String regTime;

	private String loginTime;

	private String loginIp;

	private String state;// 在线状态

	private String email;

	private Integer enable;// TODO:use this

	private String birthday;

	private String qq;

	private String msn;

	private Integer emailFlag;

	private Integer emailSettingMsg;

	private Integer emailSettingTimeline;

	private Integer emailSettingTopic;

	private Integer privacySetting;
	
	private Integer timelineSetting;

	private String gtalk;

	public String getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	public Integer getEmailFlag() {
		return emailFlag;
	}

	public Integer getEmailSettingMsg() {
		return emailSettingMsg;
	}

	public Integer getEmailSettingTimeline() {
		return emailSettingTimeline;
	}

	public Integer getEmailSettingTopic() {
		return emailSettingTopic;
	}

	public Integer getEnable() {
		return enable;
	}

	public String getGtalk() {
		return gtalk;
	}

	public Integer getId() {
		return id;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public String getMsn() {
		return msn;
	}

	public String getPassword() {
		return password;
	}

	public Integer getPrivacySetting() {
		return privacySetting;
	}

	public String getQq() {
		return qq;
	}

	public String getRegTime() {
		return regTime;
	}

	public Integer getSex() {
		return sex;
	}

	public String getState() {
		return state;
	}

	public String getUsername() {
		return username;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailFlag(Integer emailFlag) {
		this.emailFlag = emailFlag;
	}

	public void setEmailSettingMsg(Integer emailSettingMsg) {
		this.emailSettingMsg = emailSettingMsg;
	}

	public void setEmailSettingTimeline(Integer emailSettingTimeline) {
		this.emailSettingTimeline = emailSettingTimeline;
	}

	public void setEmailSettingTopic(Integer emailSettingTopic) {
		this.emailSettingTopic = emailSettingTopic;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public void setGtalk(String gtalk) {
		this.gtalk = gtalk;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPrivacySetting(Integer privacySetting) {
		this.privacySetting = privacySetting;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	// private String tel;
	// private String mobile;
	// private String address;

	public Integer getTimelineSetting() {
		return timelineSetting;
	}

	public void setTimelineSetting(Integer timelineSetting) {
		this.timelineSetting = timelineSetting;
	}
}