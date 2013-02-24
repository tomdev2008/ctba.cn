package org.net9.core.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.UrlConstants;
import org.net9.domain.model.core.User;

/**
 * 
 * @author gladstone
 * @since Mar 22, 2009
 */

public class UserWrapTag extends SimpleTagSupport {

	private User user;
	private Integer maxlength;
	private Boolean linkonly = false;

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	public void doTag() throws JspException {
		JspWriter out = getJspContext().getOut();
		try {
			String link = "";
			String label = "";
			if (user != null) {
				label = user.getUserName();
				if (maxlength != null) {
					label = StringUtils.getShorterStr(label, maxlength);
					label = CommonUtils.htmlEncodeWithoutConf(label);
					label = StringUtils.getStrWithoutHtmlTag(label);
				}
				if (StringUtils.isNotEmpty(user.getUserNick())) {
					link = user.getUserNick() + UrlConstants.USER_PAGE;
				} else {
					link = UrlConstants.USER + user.getUserId();
				}
			}
			if (linkonly) {
				out.print(link);
			} else {
				out.print("<a href='" + link + "'>" + label + "</a>");
			}
		} catch (IOException ex) {
			throw new JspException(ex.getMessage());
		}

	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLinkonly(Boolean linkonly) {
		this.linkonly = linkonly;
	}
}
