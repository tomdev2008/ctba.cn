package org.net9.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBClearTagHandler;
import org.net9.common.util.ubb.UBBDecoder;

/**
 * 限制字数
 * 
 * @author gladstone
 * @since Mar 22, 2009
 */

public class LimitTag extends SimpleTagSupport {

	private String value;

	private Integer maxlength;

	private boolean flat = false;

	/** #643 ([侧栏] 新闻、博客评论列表中的多余标签) */
	private boolean ignoreUbb = false;

	private boolean quote = true;

	public void doTag() throws JspException {
		JspWriter out = getJspContext().getOut();
		try {
			if (StringUtils.isNotEmpty(value)&&ignoreUbb) {
				// #643 ([侧栏] 新闻、博客评论列表中的多余标签)
				value = UBBDecoder.decode(value, new UBBClearTagHandler(),
						UBBDecoder.MODE_IGNORE);
				value = value.replaceAll("\\[", "");
				value = value.replaceAll("\\]", "");
				if (StringUtils.isEmpty(value)) {
					value = "...";
				}
			}

			if (maxlength != null && maxlength > 2
					&& value.length() > maxlength) {
				value = StringUtils.getShorterStr(value, maxlength);
				value = CommonUtils.htmlEncodeWithoutConf(value);
				value = StringUtils.getStrWithoutHtmlTag(value);

				if (quote) {
					value += "  ";
				}
			}
			if (flat) {
				value = StringUtils.getFlatString(value);
			}
			out.print(value);
		} catch (IOException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public void setMaxlength(java.lang.Integer maxlength) {
		this.maxlength = maxlength;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public void setQuote(boolean quote) {
		this.quote = quote;
	}

	public void setIgnoreUbb(boolean ignoreUbb) {
		this.ignoreUbb = ignoreUbb;
	}
}
