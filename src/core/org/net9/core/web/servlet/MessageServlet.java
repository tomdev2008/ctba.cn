package org.net9.core.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.CommonSystemException;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.exception.RichSystemException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.CustomizeUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.ubb.UBBDecoder;
import org.net9.common.util.ubb.UBBSimpleTagHandler;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.mail.MailSender;
import org.net9.core.types.MessageBoxType;
import org.net9.core.types.MessageType;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.core.util.ValidateHelper;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Message;
import org.net9.domain.model.core.User;

/**
 * 站内短信
 * 
 * @author gladstone
 * @since 2008/06/19
 */
@WebModule("message")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
@SuppressWarnings("serial")
public class MessageServlet extends BusinessCommonServlet {

	static Log logger = LogFactory.getLog(MessageServlet.class);

	/**
	 * 删除信件
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = true, url = "messages")
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String msgIdStr = request.getParameter("id");
		if (StringUtils.isNotEmpty(msgIdStr)) {
			int msgId = Integer.valueOf(msgIdStr);

			Message model = userService.getMessage(new Integer(msgId));
			// validate user
			UserHelper.authUserSimply(request, model);

			userService.deleteMessage(msgId);
		}
		// 支持批量删除
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("_msg_") >= 0) {
				String value = key.substring("_msg_".length());
				logger.info("delete message " + value);

				Message model = userService.getMessage(new Integer(Integer
						.valueOf(value)));
				// validate user
				String username = UserHelper.getuserFromCookie(request);
				UserHelper.authUserForCurrentPojoSimply(username, model);

				userService.deleteMessage(Integer.valueOf(value));
			}
		}
	}

	/**
	 * 表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/message/sendMsg.jsp")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msgTo = request.getParameter("to");
		if (StringUtils.isNotEmpty(msgTo)) {
			msgTo = StringUtils.getSysEncodedStr(msgTo);
		}
		request.setAttribute("msgTo", msgTo);
	}

	/**
	 * 发送信件
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws CommonSystemException
	 */
	@ReturnUrl(rederect = false, url = "/message/sendMsgDone.jsp")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommonSystemException {
		String msgTo = request.getParameter("msgTo");
		String parent = request.getParameter("parent");
		String title = request.getParameter("title");
		String msgFrom = UserHelper.getuserFromCookie(request);

		if (StringUtils.isNotEmpty(msgTo)) {
			String[] msgToArray = msgTo.split(",");
			for (String to : msgToArray) {
				if (StringUtils.isEmpty(to.trim())) {
					continue;
				}
				MainUser userTo = userService.getUser(to.trim());
				MainUser userFrom = userService.getUser(msgFrom);
				if (userTo == null) {
					throw new RichSystemException("message.userNotExists");
				}
				String msgContent = request.getParameter("topicContent");
				if (StringUtils.isEmpty(title)) {
					title = StringUtils.getShorterStr(msgContent, 30);
				}
				if (StringUtils.isNotEmpty(msgContent)) {
					Message model = new Message();
					model.setMsgContent(msgContent);
					model.setMsgFrom(msgFrom);
					model.setMsgRead(0);
					model.setMsgTitle(title);
					model.setMsgTime(DateUtils.getNow());
					model.setMsgTo(userTo.getUsername());
					model.setMsgType(MessageType.NORMAL.getValue());

					// 验证Bean
					ValidateHelper.validateBean(model, "message");

					userService.saveMessage(model);
					// 如果用户设置了和邮箱同步，发送信件
					if (userTo.getEmailSettingMsg() != null
							&& userTo.getEmailSettingMsg() > 0) {
						MailSender.getInstance().sendMsgRemindEmail(userTo,
								userFrom, msgContent);
					}
				}

			}
		}
		request.setAttribute("parent", parent);
	}

	/**
	 * 增加星标
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = true, url = "messages")
	public void star(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msgIdStr = request.getParameter("id");
		if (StringUtils.isNotEmpty(msgIdStr)) {
			int msgId = Integer.valueOf(msgIdStr);
			Message model = userService.getMessage(msgId);
			if (model.getMsgStar() != null && model.getMsgStar() > 0) {
				model.setMsgStar(0);
			} else {
				model.setMsgStar(1);
			}
			userService.saveMessage(model);
		}

		// 支持批量
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("_msg_") >= 0) {
				String value = key.substring("_msg_".length());
				logger.info("deal star of message " + value);
				Message model = userService.getMessage(Integer.valueOf(value));
				if (model.getMsgStar() != null && model.getMsgStar() > 0) {
					model.setMsgStar(0);
				} else {
					model.setMsgStar(1);
				}
				userService.saveMessage(model);
			}
		}
	}

	/**
	 * 设置已读未读状态
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = true, url = "messages")
	public void status(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String msgIdStr = request.getParameter("id");
		if (StringUtils.isNotEmpty(msgIdStr)) {
			int msgId = Integer.valueOf(msgIdStr);
			Message model = userService.getMessage(msgId);

			// validate user
			UserHelper.authUserSimply(request, model);

			if (model.getMsgRead() > 0) {
				model.setMsgRead(0);
			} else {
				model.setMsgRead(1);
			}
			userService.saveMessage(model);
		}

		// 支持批量
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			if (key.indexOf("_msg_") >= 0) {
				String value = key.substring("_msg_".length());
				logger.info("deal status of message " + value);
				Message model = userService.getMessage(Integer.valueOf(value));

				// validate user
				UserHelper.authUserSimply(request, model);

				if (model.getMsgRead() > 0) {
					model.setMsgRead(0);
				} else {
					model.setMsgRead(1);
				}
				userService.saveMessage(model);
			}
		}
	}

	/**
	 * 信件列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = false, url = "/message/viewMsg.jsp")
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String username = UserHelper.getuserFromCookie(request);
		String messageId = request.getParameter("mid");
		if (StringUtils.isEmpty(messageId)) {
			messageId = (String) request.getAttribute("mid");
		}
		Message model = userService.getMessage(new Integer(messageId));

		// validate user
		UserHelper.authUserSimply(request, model);

		// 只有是收信人看了，才设为已读
		if (username.equals(model.getMsgTo())) {
			model.setMsgRead(1);
			userService.saveMessage(model);
		}
		request.setAttribute("model", model);
		request.setAttribute("uFrom", userService.getUser(null, model
				.getMsgFrom()));
		request
				.setAttribute("uTo", userService
						.getUser(null, model.getMsgTo()));
		String msgContent = UBBDecoder.decode(CommonUtils.htmlEncode(model
				.getMsgContent()), new UBBSimpleTagHandler(),
				UBBDecoder.MODE_IGNORE);
		msgContent = CustomizeUtils.parseEmotionImg(msgContent);
		request.setAttribute("msgContent", msgContent);
		Message nextModel = userService.getNextMessage(model.getMsgId(), null,
				model.getMsgTo());
		request.setAttribute("nextModel", nextModel);
		Message prevModel = userService.getPrevMessage(model.getMsgId(), null,
				model.getMsgTo());
		request.setAttribute("prevModel", prevModel);
	}

	/**
	 * 用户收件/发件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/message/listMsgs.jsp")
	@SuppressWarnings("unchecked")
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = UserHelper.getuserFromCookie(request);
		User user = userService.getUser(null, username);
		Integer start = HttpUtils.getStartParameter(request);
		String readStr = request.getParameter("read");
		String box = request.getParameter("box");
		String markedStr = request.getParameter("marked");
		if (StringUtils.isEmpty(box)) {
			box = "inbox";
		}
		Integer limit = WebConstants.PAGE_SIZE_30;
		boolean read = false;
		Boolean marked = false;
		if (StringUtils.isNotEmpty(readStr)) {
			read = true;
		}
		if (StringUtils.isNotEmpty(markedStr)) {
			marked = true;
		}
		Integer boxType = MessageBoxType.MSG_TYPE_RECEIVE.getValue();
		if ("outbox".equals(box)) {
			boxType = MessageBoxType.MSG_TYPE_SEND.getValue();
		}
		List<Message> list = userService.findMsgsByUser(username, boxType,
				read, marked, start, limit);
		List models = new ArrayList();
		for (Message message : list) {
			Map map = new HashMap();
			map.put("message", message);
			User u = userService.getUser(null, message.getMsgFrom());
			User uTo = userService.getUser(null, message.getMsgTo());
			map.put("isFriend", StringUtils.isNotEmpty(username)
					&& userService.isFriend(username, u.getUserName()));
			map.put("u", u);
			map.put("uTo", uTo);
			models.add(map);
		}
		request.setAttribute("count", userService.getMsgsCntByUser(username,
				boxType, read, marked));
		request.setAttribute("models", models);
		request.setAttribute("url", "user.do");
		request.setAttribute("user", user);
		request.setAttribute("box", box);
	}
}
