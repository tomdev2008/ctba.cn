package org.net9.bbs.web.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 用户注册表单
 * @author gladstone
 * @since Feb 26, 2009
 */
@SuppressWarnings("serial")
public class UserForm extends ActionForm {
	

	String username;

	String password;

	String email;

	boolean remember;
	
	FormFile pic;

	public FormFile getPic() {
		return pic;
	}

	public void setPic(FormFile pic) {
		this.pic = pic;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
