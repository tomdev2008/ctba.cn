package org.net9.common.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.constant.WebPageVarConstant;

/**
 * 显示附件
 * 
 * @author gladstone
 * @since 2008-9-21
 */
public class AttachmentTag extends SimpleTagSupport {

	private java.lang.String name;
	private java.lang.String path;

	@Override
	public void doTag() throws JspException {
		String username = (String) this.getJspContext().findAttribute(
				WebPageVarConstant.USERNAME);
		JspWriter out = getJspContext().getOut();
		if (StringUtils.isNotEmpty(username)
				|| SystemConfigVars.ATTACH_VIEW_ALLOW_GUEST) {
			// 允许游客查看附件
			String staticRoot = SystemConfigUtils
					.getProperty("url.static.root");
			if (StringUtils.isEmpty(staticRoot)) {
				staticRoot = SystemConfigVars.UPLOAD_DIR_PATH;
			}
			try {
				if (StringUtils.isEmpty(name)) {
					name = path;
				}
				out
						.print(CommonUtils.showAttach(name, staticRoot + "/"
								+ path));
			} catch (java.io.IOException ex) {
				throw new JspException(ex.getMessage());
			}
		} else {

			// 禁止游客查看附件
			String msg = I18nMsgUtils.getInstance().getMessage(
					"error.noOption.attachment");
			try {
				out.print("<div class='quote'>" + msg + "</div>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setPath(java.lang.String path) {
		this.path = path;
	}

}
