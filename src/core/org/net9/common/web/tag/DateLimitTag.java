package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 
 * @author gladstone
 * @version
 */

public class DateLimitTag extends SimpleTagSupport {

	private java.lang.String value;

	private java.lang.Integer start;

	private java.lang.Integer limit;

	private boolean quote = false;

	public void setQuote(boolean quote) {
		this.quote = quote;
	}

	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			// value = value.substring(start);
			if (limit > start && limit <= value.length())
				value = value.substring(start, limit);
			if (quote)
				value += "...";
			out.print(value);

		} catch (java.io.IOException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	// /**
	// * Initialization of maxLength property.
	// */
	// private java.lang.String maxLength;

	/**
	 * Setter for the value attribute.
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public void setStart(java.lang.Integer start) {
		this.start = start;
	}

	public java.lang.Integer getLimit() {
		return limit;
	}

	public void setLimit(java.lang.Integer limit) {
		this.limit = limit;
	}

}
