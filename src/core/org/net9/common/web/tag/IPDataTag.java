package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.ip.IPSeeker;

/**
 * ip data
 * 
 * @author gladstone
 * 
 */
public class IPDataTag extends SimpleTagSupport {

	static Log log = LogFactory.getLog(IPDataTag.class);

	private java.lang.String value;

	@Override
	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			IPSeeker seeker = IPSeeker.getInstance();
			log.debug(seeker.getCountry(value) + "|" + seeker.getArea(value)
					+ "|");
			value = seeker.getCountry(value);
			// seeker.getAddress(value);
			if (value.indexOf("IP") > 0) {
				value = "";
			}
			// value = value.substring(0, value.lastIndexOf(".") + 1) + "*";
			out.print(value);

		} catch (Exception ex) {
			log.error(ex.getMessage());
			// throw new JspException(ex.getMessage());
		}

	}

	public java.lang.String getValue() {
		return value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

}
