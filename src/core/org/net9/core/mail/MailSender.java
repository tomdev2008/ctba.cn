package org.net9.core.mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.net9.common.util.DateUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.Md5Utils;
import org.net9.common.util.StringUtils;
import org.net9.core.service.CommonService;
import org.net9.core.service.ServiceModule;
import org.net9.core.wrapper.MailListWrapper;
import org.net9.domain.model.core.MainUser;

import com.google.inject.Guice;
import com.google.inject.Inject;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 邮件发送器 采用apache common的mail模块
 * 
 * @author gladstone
 * 
 */
public class MailSender implements java.io.Serializable {

	private static final long serialVersionUID = -2145930244650624387L;

	private static Log log = LogFactory.getLog(MailSender.class);

	private static MailSender instance;

	/** 簡單的郵件 */
	public final static int EMAIL_TYPE_SIMPLE = 1;

	/** HTML郵件 */
	public final static int EMAIL_TYPE_HTML = 2;

	public static MailSender getInstance() {
		if (instance == null) {
			instance = new MailSender();
		}
		return instance;
	}

	@Inject
	private CommonService commonService;

	@Inject
	MailListWrapper listWrapper;

	MailSender() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	@SuppressWarnings("unchecked")
	private String getContentByTemplate(String templateName, Map context)
			throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		BufferedWriter writer = null;
		StringWriter sw = new StringWriter();
		writer = new BufferedWriter(sw);
		// TODO:change this to system config
		String rootDir = System.getProperty("email.template.root");
		if (StringUtils.isEmpty(rootDir)) {
			rootDir = MailSender.class.getClassLoader().getResource("template")
					.getPath();
		}
		File f = new File(rootDir);
		cfg.setDirectoryForTemplateLoading(f);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template t = cfg.getTemplate(templateName, "UTF-8");
		t.process(context, writer);
		writer.flush();
		writer.close();
		String content = sw.getBuffer().toString();
		log.debug("Content:" + content);
		// new String(sw.getBuffer().toString().getBytes("ISO-88591"),
		// "GBK");
		return content;
	}

	/**
	 * 生成收信人列表
	 * 
	 * @param addrArray
	 * @return
	 */
	private List<InternetAddress> makeAddrList(String[] addrArray) {
		List<InternetAddress> toList = new ArrayList<InternetAddress>();
		for (String s : addrArray) {
			if (StringUtils.isEmpty(s)) {
				continue;
			}
			s = s.trim();
			try {
				toList.add(new InternetAddress(s));
			} catch (AddressException e) {
				log.error(e.getMessage());
				continue;
			}
		}
		return toList;
	}

	/**
	 * 发送丢失密码的确认信件 發送的密碼字符串是MD5(用戶名+用戶密碼)
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void sendConfirmMail(MainUser user) throws Exception {
		String title = I18nMsgUtils.getInstance().getMessage(
				"email.forget.title");
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("url", "userExt.shtml?method=reset&u=" + user.getUsername()
				+ "&c="
				+ Md5Utils.getMD5(user.getUsername() + user.getPassword()));
		context.put("sendDate", StringUtils.getTimeStrByNow());
		String content = getContentByTemplate("forget.ftl", context);
		sendMail(title, content, new String[] { user.getEmail() },
				EMAIL_TYPE_HTML);

	}

	/**
	 * 發送每日内容
	 * 
	 * @param user
	 * @param contentList
	 */
	@SuppressWarnings("unchecked")
	public void sendContentEmail(MainUser user, List contentList) {
		String title = I18nMsgUtils.getInstance().createMessage(
				"email.topic.title",
				new String[] { DateUtils.getDateString(new Date()) });
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("mList", listWrapper.wrapContentList(contentList));
		context.put("contentList", contentList);
		context.put("sendDate", DateUtils.getDateString(new Date()));
		String content;
		try {
			content = getContentByTemplate("topic_daily.ftl", context);
			sendMail(title, content, new String[] { user.getEmail() },
					EMAIL_TYPE_HTML);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	public void sendDailyTimelineEmail(MainUser user,
			List<Map<Object, Object>> timelineList) {
		String title = I18nMsgUtils.getInstance().createMessage(
				"email.timeline.title",
				new String[] { DateUtils.getDateString(new Date()) });
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("timelineList", timelineList);
		context.put("sendDate", DateUtils.getDateString(new Date()));
		String content;
		try {
			content = getContentByTemplate("timeline_daily.ftl", context);
			sendMail(title, content, new String[] { user.getEmail() },
					EMAIL_TYPE_HTML);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 发送登录提醒邮件
	 * 
	 * @param user
	 * @param timelineList
	 *            目前不使用
	 */
	public void sendLoginRemindEmail(MainUser user,
			List<Map<Object, Object>> timelineList) {
		String title = I18nMsgUtils.getInstance().createMessage(
				"email.remind.title",
				new String[] { DateUtils.getDateString(new Date()) });
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("timelineList", timelineList);
		context.put("sendDate", DateUtils.getDateString(new Date()));
		String content;
		try {
			content = getContentByTemplate("login_remind.ftl", context);
			sendMail(title, content, new String[] { user.getEmail() },
					EMAIL_TYPE_HTML);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 发送邀请信
	 * 
	 * @param username
	 *            当前用户的名称
	 * @param friends
	 *            用户的好友
	 * @throws Exception
	 */
	public void sendInviteMail(String username, String[] friends)
			throws Exception {
		String title = I18nMsgUtils.getInstance().createMessage(
				"email.invite.title", new String[] { username });
		for (String friend : friends) {
			try {
				friend = friend.trim();
				if (friend.indexOf("(") > 0 && friend.endsWith(")")) {
					friend = friend.substring(0, friend.indexOf(" ("));
				}
				Map<String, String> context = new HashMap<String, String>();
				context.put("username", username);
				context.put("friend", friend);
				context.put("sendDate", StringUtils.getTimeStrByNow());
				String content;
				content = getContentByTemplate("invite.ftl", context);
				sendMail(title, content, new String[] { friend.trim() },
						EMAIL_TYPE_HTML);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				continue;
			}
		}

	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件标题
	 * @param msgText
	 *            邮件内容
	 * @param to
	 *            收件人地址的列表
	 * @param emailType
	 *            邮件的类型,<br>
	 *            EMAIL_TYPE_SIMPLE->簡單的郵件 或者 EMAIL_TYPE_HTML->HTML類型郵件
	 * @throws EmailException
	 * @throws AddressException
	 */
	public void sendMail(String subject, String msgText, String[] toArray,
			int emailType) throws EmailException, AddressException {
		List<InternetAddress> toList = makeAddrList(toArray);
		Email email;
		if (emailType == EMAIL_TYPE_HTML) {
			email = new HtmlEmail();
		} else {
			email = new SimpleEmail();
		}

		email.setFrom(this.commonService.getConfig().getMailSmtpAddress());
		email.setCharset("UTF-8");
		email.setSubject(subject);
		email.setMsg(msgText);
		email.setHostName(this.commonService.getConfig().getMailSmtpHost());
		email.setTo(toList);
		email.setAuthentication(this.commonService.getConfig()
				.getMailSmtpUsername(), this.commonService.getConfig()
				.getMailSmtpPassword());
		email.send();
	}

	/**
	 * 发送站内消息提醒
	 * 
	 * @param user
	 * @param count
	 */
	public void sendMsgRemindEmail(MainUser user, Integer count) {
		if (user == null || StringUtils.isEmpty(user.getEmail())) {
			log.error("User not exists or did not set it's email!");
			return;
		}
		String title = I18nMsgUtils.getInstance().getMessage("email.msg.title");
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("count", count + "");
		context.put("sendDate", DateUtils.getDateString(new Date()));
		String content;
		try {
			content = getContentByTemplate("msg_remind.ftl", context);
			sendMail(title, content, new String[] { user.getEmail() },
					EMAIL_TYPE_HTML);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 当用户受到信件的时候，发送提醒
	 * 
	 * @param user
	 * @param userFrom
	 * @param msgContent
	 */
	public void sendMsgRemindEmail(MainUser user, MainUser userFrom,
			String msgContent) {
		if (user == null || StringUtils.isEmpty(user.getEmail())) {
			log.error("User not exists or did not set it's email!");
			return;
		}
		String title = I18nMsgUtils.getInstance().createMessage(
				"email.msg.title.single",
				new Object[] { userFrom.getUsername() });
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("userFrom", userFrom);
		context.put("msgContent", StringUtils.getShorterStr(msgContent, 20));
		context.put("sendDate", DateUtils.getDateString(new Date()));
		String content;
		try {
			content = getContentByTemplate("msg_remind_single.ftl", context);
			sendMail(title, content, new String[] { user.getEmail() },
					EMAIL_TYPE_HTML);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 發送注冊信件
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void sendRegMail(MainUser user) throws Exception {
		String title = I18nMsgUtils.getInstance().getMessage("email.reg.title");
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("user", user);
		context.put("sendDate", StringUtils.getTimeStrByNow());
		String content = getContentByTemplate("register.ftl", context);
		sendMail(title, content, new String[] { user.getEmail() },
				EMAIL_TYPE_HTML);
	}
}
