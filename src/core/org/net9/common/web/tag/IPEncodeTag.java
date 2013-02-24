package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * url encode
 * 
 * @author gladstone
 * 
 */
public class IPEncodeTag extends SimpleTagSupport {

	private java.lang.String value;

	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			value = value.substring(0, value.lastIndexOf(".") + 1) + "*";
			out.print(value);

		} catch (java.io.IOException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

}
