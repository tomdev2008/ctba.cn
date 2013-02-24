package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.StringUtils;

/**
 * url encode
 * 
 * @author gladstone
 * 
 */
public class URLEncodeTag extends SimpleTagSupport {

	private java.lang.String value;

	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			// out.print(URLEncoder.encode(value, "UTF-8"));
			value = StringUtils.base64Encode(value);
			// value = value.replaceAll("\\+", "%2B");
			// value = value.replaceAll("\\\"", "%22");
			// value = value.replaceAll("\\'", "%27");
			value = StringUtils.urlEncode(value);
			// value = URLEncoder.encode(value);
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
