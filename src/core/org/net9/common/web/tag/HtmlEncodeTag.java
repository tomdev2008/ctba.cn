package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;

/**
 * HTML encode
 * 
 * @author Sobranie
 * @version
 */

public class HtmlEncodeTag extends SimpleTagSupport {

	private java.lang.String value;

	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			value = CommonUtils.htmlEncode(value);
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
