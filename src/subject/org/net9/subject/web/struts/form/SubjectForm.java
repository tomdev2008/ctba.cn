package org.net9.subject.web.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * subject form model
 * 
 * @author gladstone
 * @since 2008/06/27
 */
@SuppressWarnings("serial")
public class SubjectForm extends ActionForm {
	
	Integer sid;

	String title;

	String type;
	
	FormFile pic;

	String template;

	String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FormFile getPic() {
		return pic;
	}

	public void setPic(FormFile pic) {
		this.pic = pic;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}


}
