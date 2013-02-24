package org.net9.core.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jml.MsnContact;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.types.NoticeType;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.ext.google.GtalkContactManager;
import org.net9.ext.msn.ContactMessenger;


import com.xdatasystem.contactsimporter.ContactListImporter;
import com.xdatasystem.contactsimporter.ContactListImporterException;
import com.xdatasystem.contactsimporter.ContactListImporterFactory;
import com.xdatasystem.user.Contact;

/**
 * Contact Action
 * 
 * @author gladstone
 * @since 2009/02/13
 */
@SuppressWarnings("serial")
@WebModule("c")
public class ContactServlet extends BusinessCommonServlet {

	private static final Log logger = LogFactory.getLog(ContactServlet.class);

	/**
	 * 邮箱联系人
	 * 
	 * @param request
	 * @param response
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 */
	@ReturnUrl(rederect = false, url = "/core/foundContacts.jsp")
	public boolean email(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			InterruptedException {

		String emailUsername = request.getParameter("emailUsername");
		String emailPassword = request.getParameter("emailPassword");
		String emailHost = request.getParameter("emailHost");

		ContactListImporter importer = ContactListImporterFactory.guess(
				emailUsername + emailHost, emailPassword);

		logger.info("email: " + emailUsername + emailHost);
		List<Contact> contacts;
		try {
			contacts = importer.getContactList();
		} catch (ContactListImporterException e) {
			logger.error(e.getMessage());
			this.sendError(request, response, "contact.timeOut");
			return true;
		}
		List<String> emailList = new ArrayList<String>();

		for (Contact c : contacts) {
			if ((StringUtils.isNotEmpty(c.getEmailAddress()))) {
				logger.debug(c.getEmailAddress());
				emailList.add(c.getEmailAddress());
			}
		}

		request.getSession().setAttribute("sessionContactList", emailList);
		String username = UserHelper.getuserFromCookie(request);
		List<MainUser> uList = userService.findUsersByEmailList(emailList);
		request.setAttribute("contactList", filteringContactListFromUserList(
				username, uList));
		request.setAttribute("friendList", filteringFriendListFromUserList(
				username, uList));
		return false;
	}

	/**
	 * 去除已经是好友的用户
	 * 
	 * @param contactList
	 * @return
	 */
	private List<MainUser> filteringContactListFromUserList(
			String currUsername, List<MainUser> uList) {
		List<MainUser> newList = new ArrayList<MainUser>();
		if (uList.size() == 0) {
			return newList;
		}
		for (MainUser u : uList) {
			if (!userService.isFriend(currUsername, u.getUsername())) {
				newList.add(u);
			}
		}
		return newList;
	}

	/**
	 * 得到已经是好友的用户
	 * 
	 * @param currUsername
	 * @param uList
	 * @return
	 */
	private List<MainUser> filteringFriendListFromUserList(String currUsername,
			List<MainUser> uList) {
		List<MainUser> newList = new ArrayList<MainUser>();
		if (uList.size() == 0) {
			return newList;
		}
		for (MainUser u : uList) {
			if (userService.isFriend(currUsername, u.getUsername())) {
				newList.add(u);
			}
		}
		return newList;
	}

	/**
	 * 找联系人
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	@ReturnUrl(rederect = false, url = "/core/inviteFriend.jsp")
	public void find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException {
		List<String> contactList = (List<String>) request.getSession()
				.getAttribute("sessionContactList");
		String username = UserHelper.getuserFromCookie(request);
		Enumeration e = request.getParameterNames();
		int addedCnt = 0;
		while (e.hasMoreElements()) {
			String param = (String) e.nextElement();
			if (param.startsWith("contact_")) {
				String contactName = param.substring("contact_".length());
				logger.debug("Contact:" + contactName);
				if (contactList != null && contactList.size() > 0
						&& contactList.contains(contactName)) {
					contactList.remove(contactName);
				}
				if (!userService.isFriend(username, contactName)) {
					Friend model = new Friend();
					model.setFrdMemo(contactName);
					model.setFrdTag(contactName);
					model.setFrdUserMe(username);
					model.setFrdUserYou(contactName);
					userService.saveFriend(model, false);
					String msg = I18nMsgUtils.getInstance().createMessage(
							"friend.added",
							new Object[] { CommonUtils
									.buildUserPagelink(username) });

					userService.trigeNotice(contactName, username, msg, null,
							NoticeType.COMMON);
					addedCnt++;
				}
			}
		}

		request.setAttribute("contactList", makeContactListStr(contactList));
		request.setAttribute("addedCnt", addedCnt);
	}

	/**
	 * google talk 联系人
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@ReturnUrl(rederect = false, url = "/core/foundContacts.jsp")
	public boolean gtalk(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String gtalkUsername = request.getParameter("gtalkUsername");
		String gtalkPassword = request.getParameter("gtalkPassword");
		GtalkContactManager manager = new GtalkContactManager(gtalkUsername,
				gtalkPassword);
		List<String> emailList = manager.getContactList();
		if (emailList == null || emailList.size() == 0) {
			this.sendError(request, response, "contact.timeOut");
			return true;
		}

		request.getSession().setAttribute("sessionContactList", emailList);
		String username = UserHelper.getuserFromCookie(request);
		List<MainUser> uList = userService.findUsersByEmailList(emailList);
		request.setAttribute("contactList", filteringContactListFromUserList(
				username, uList));
		request.setAttribute("friendList", filteringFriendListFromUserList(
				username, uList));
		return false;

	}

	/**
	 * 拼装联系人列表
	 * 
	 * @param contactList
	 * @return
	 */
	private String makeContactListStr(List<String> contactList) {
		if (contactList == null || contactList.size() == 0) {
			return "";
		}
		String reval = "";
		for (String s : contactList) {
			reval += s + "\n";
		}
		return reval;
	}

	/**
	 * msn 联系人
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@ReturnUrl(rederect = false, url = "/core/foundContacts.jsp")
	public boolean msn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException {

		int maxWaiteTime = 30000;
		int firstWaiteTime = 5000;
		int plusWaiteTime = 6000;
		String msnUsername = request.getParameter("msnUsername");
		String msnPassword = request.getParameter("msnPassword");
		ContactMessenger m = new ContactMessenger(msnUsername, msnPassword);
		m.start();
		Thread.sleep(firstWaiteTime);
		while (m.getContactList().size() == 0) {
			firstWaiteTime += plusWaiteTime;
			if (maxWaiteTime <= firstWaiteTime) {
				break;
			}
			logger.debug("firstWaiteTime:" + firstWaiteTime);
			Thread.sleep(firstWaiteTime);
		}

		m.stop();
		if (m.getContactList().size() == 0) {
			this.sendError(request, response, "contact.timeOut");
			return true;
		}

		List<String> emailList = new ArrayList<String>();
		for (MsnContact c : m.getContactList()) {
			if ((c.getEmail() != null && StringUtils.isNotEmpty(c.getEmail()
					.getEmailAddress()))) {
				logger.debug(c.getEmail());
				emailList.add(c.getEmail().getEmailAddress());
			}
		}

		request.getSession().setAttribute("sessionContactList", emailList);
		String username = UserHelper.getuserFromCookie(request);
		List<MainUser> uList = userService.findUsersByEmailList(emailList);
		request.setAttribute("contactList", filteringContactListFromUserList(
				username, uList));
		request.setAttribute("friendList", filteringFriendListFromUserList(
				username, uList));
		return false;
	}
}