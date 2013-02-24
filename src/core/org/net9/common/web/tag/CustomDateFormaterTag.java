package org.net9.common.web.tag;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CustomDateFormater;
import org.net9.common.util.StringUtils;

/**
 * Date Formater Tag
 * 
 * @see org.net9.common.util.CustomDateFormater
 */
public class CustomDateFormaterTag extends SimpleTagSupport {

	static Log logger = LogFactory.getLog(CustomDateFormaterTag.class);

	/**
	 * 待格式化的时间
	 */
	private String time;

	/**
	 * 用于将<code>time</code>转换成Date的pattern字符串
	 */
	private String pattern;

	public CustomDateFormaterTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		try {
			if (StringUtils.isEmpty(pattern)) {
				pattern = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sFmt = new SimpleDateFormat(pattern);
			Date datetime = sFmt.parse(time);

			CustomDateFormater formater = new CustomDateFormater();
			String formatedTime = formater.getTime(datetime);

			out.print(formatedTime);
		} catch (ParseException e) {
			try {
				pattern = "yyyy-MM-dd";
				SimpleDateFormat sFmt = new SimpleDateFormat(pattern);
				Date datetime = sFmt.parse(time);

				CustomDateFormater formater = new CustomDateFormater();
				String formatedTime = formater.getTime(datetime);

				out.print(formatedTime);
			} catch (ParseException e1) {
				logger.error(e1.getMessage());
				// 这里，出错了也不要抛例外
				// throw new JspException("无法解析time的值：" + time, e1);
				out.print(time);
			}
		}
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
