package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@Entity
@Table(name = "sys_config", uniqueConstraints = {})
@SuppressWarnings("serial")
public class SysConfig extends BaseModel {

	@Id
	private Integer id;

	private String domainRoot;

	private String rssTitlePrefix;

	private String mailSmtpHost;

	private String mailSmtpUsername;

	private String mailSmtpPassword;

	private String mailSmtpAddress;

	private String forbiddenWords;

	public String getDomainRoot() {
		return domainRoot;
	}

	public String getForbiddenWords() {
		return forbiddenWords;
	}

	public Integer getId() {
		return id;
	}

	public String getMailSmtpAddress() {
		return mailSmtpAddress;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public String getMailSmtpPassword() {
		return mailSmtpPassword;
	}

	public String getMailSmtpUsername() {
		return mailSmtpUsername;
	}

	public String getRssTitlePrefix() {
		return rssTitlePrefix;
	}

	public void setDomainRoot(String domainRoot) {
		this.domainRoot = domainRoot;
	}

	public void setForbiddenWords(String forbiddenWords) {
		this.forbiddenWords = forbiddenWords;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMailSmtpAddress(String mailSmtpAddress) {
		this.mailSmtpAddress = mailSmtpAddress;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public void setMailSmtpPassword(String mailSmtpPassword) {
		this.mailSmtpPassword = mailSmtpPassword;
	}

	public void setMailSmtpUsername(String mailSmtpUsername) {
		this.mailSmtpUsername = mailSmtpUsername;
	}

	public void setRssTitlePrefix(String rssTitlePrefix) {
		this.rssTitlePrefix = rssTitlePrefix;
	}

}
