package org.net9.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.util.ImageUtils;

/**
 * image tag, create a mini size or default size image
 * 
 * @author gladstone
 * @version
 */

public class ImageTag extends SimpleTagSupport {

	private java.lang.String value;

	private java.lang.String type;

	private java.lang.Integer width;

	@Override
	public void doTag() throws JspException {

		JspWriter out = getJspContext().getOut();

		String staticRoot = SystemConfigUtils.getProperty("url.static.root");
		if (StringUtils.isEmpty(staticRoot)) {
			staticRoot = SystemConfigVars.UPLOAD_DIR_PATH;
		}
		try {
			String s = "images/" + "nopic.gif";
			if (CommonUtils.isNotEmpty(value)) {
				if ("mini".equals(type)) {
					s = staticRoot + "/" + ImageUtils.getMiniImageStr(value);
				} else if ("sized".equals(type)) {
					s = staticRoot
							+ "/"
							+ ImageUtils.getSizedImageStr(
									"sized" + "/" + value, width);
				} else if ("nba".equals(type)) {
					s = CustomizeUtils.getNbaTeamLogoByName(value);
				} else if ("none".equals(type)) {
					s = staticRoot + "/" + value;
				} else {
					s = staticRoot + "/" + ImageUtils.getDefaultImageStr(value);
				}
			}
			out.print(s);

		} catch (java.io.IOException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	public java.lang.String getType() {
		return type;
	}

	public java.lang.String getValue() {
		return value;
	}

	public java.lang.Integer getWidth() {
		return width;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}

	public void setWidth(java.lang.Integer width) {
		this.width = width;
	}

}
