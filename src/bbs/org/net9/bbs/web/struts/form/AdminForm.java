package org.net9.bbs.web.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * 用户管理登录表单
 * 
 * @author gladstone
 * 
 */
@SuppressWarnings("serial")
public class AdminForm extends ActionForm {
	String username;

	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
