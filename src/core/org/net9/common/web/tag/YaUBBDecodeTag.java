package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBWithoutImgTagHandler;

/**
 * UBB encode
 * 
 * @author gladstone
 * @version
 */

public class YaUBBDecodeTag extends SimpleTagSupport {

	private java.lang.String value;

	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		try {
			value = UBBDecoder.decode(CommonUtils.htmlEncode(value),
					new UBBWithoutImgTagHandler(), UBBDecoder.MODE_IGNORE);
			value = CustomizeUtils.getStrByHtmlTagConfig(value);
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
