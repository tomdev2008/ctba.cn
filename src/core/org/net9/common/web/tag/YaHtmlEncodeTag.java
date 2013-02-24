package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;

/**
 * Yet another HTML encode
 * 
 * @author gladstone
 * @version
 */

public class YaHtmlEncodeTag extends SimpleTagSupport {

	private java.lang.String value;

	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			value = CommonUtils.htmlEncodeWithoutConf(value);
			out.print(value);

		} catch (java.io.IOException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	/**
	 * Setter for the value attribute.
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}

}
