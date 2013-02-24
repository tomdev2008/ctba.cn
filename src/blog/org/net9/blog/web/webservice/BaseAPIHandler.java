package org.net9.blog.web.webservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.net9.blog.service.AddressService;
import org.net9.blog.service.BlogService;
import org.net9.blog.service.CommentService;
import org.net9.blog.service.EntryService;
import org.net9.blog.service.LinkService;
import org.net9.core.constant.UrlConstants;
import org.net9.core.service.CommonService;
import org.net9.core.service.UserExtService;
import org.net9.core.service.UserService;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.core.MainUser;

import cn.ctba.share.service.ShareService;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;

/**
 * Base API handler does user validation, provides exception types, etc.
 * 
 * @author David M Johnson
 * @author Gladstone Chen
 */
@SuppressWarnings("serial")
public class BaseAPIHandler implements java.io.Serializable {

	private static Log logger = LogFactory.getFactory().getInstance(
			BaseAPIHandler.class);
	@Inject
	protected CommonService commonService;

	@Inject
	protected UserService userService;

	@Inject
	protected UserExtService userExtService;

	@Inject
	protected BlogService blogService;

	@Inject
	protected EntryService entryService;

	@Inject
	protected CommentService commentService;

	@Inject
	protected LinkService linkService;

	@Inject
	protected AddressService vestService;

	@Inject
	protected ShareService shareService;

	public static final int AUTHORIZATION_EXCEPTION = 0001;
	public static final String AUTHORIZATION_EXCEPTION_MSG = "Invalid Username and/or Password";

	public static final int UNKNOWN_EXCEPTION = 1000;
	public static final String UNKNOWN_EXCEPTION_MSG = "An error occured processing your request";

	public static final int UNSUPPORTED_EXCEPTION = 1001;
	public static final String UNSUPPORTED_EXCEPTION_MSG = "Unsupported method - Roller does not support this method";

	public static final int USER_DISABLED = 1002;
	public static final String USER_DISABLED_MSG = "User is disabled";

	public static final int WEBLOG_NOT_FOUND = 1003;
	public static final String WEBLOG_NOT_FOUND_MSG = "Weblog is not found or is disabled";

	public static final int WEBLOG_DISABLED = 1004;
	public static final String WEBLOG_DISABLED_MSG = "Weblog is not found or is disabled";

	public static final int BLOGGERAPI_DISABLED = 1005;
	public static final String BLOGGERAPI_DISABLED_MSG = "Weblog does not exist or XML-RPC disabled in web";

	public static final int BLOGGERAPI_INCOMPLETE_POST = 1006;
	public static final String BLOGGERAPI_INCOMPLETE_POST_MSG = "Incomplete weblog entry";

	public static final int INVALID_POSTID = 2000;
	public static final String INVALID_POSTID_MSG = "The entry postid you submitted is invalid";

	// public static final int NOBLOGS_EXCEPTION = 3000;
	// public static final String NOBLOGS_EXCEPTION_MSG =
	// "There are no categories defined for your user";

	public static final int UPLOAD_DENIED_EXCEPTION = 4000;
	public static final String UPLOAD_DENIED_EXCEPTION_MSG = "Upload denied";

	public BaseAPIHandler() {
		// 加入Guice配置
		Injector injector = Guice.createInjector(new ServletModule());
		logger.debug("Inject members with guice");
		injector.injectMembers(this);
	}

	/**
	 * Returns website, but only if user authenticates and is authorized to
	 * edit.
	 * 
	 * @param blogid
	 *            Blogid sent in request (used as website's hanldle)
	 * @param username
	 *            Username sent in request
	 * @param password
	 *            Password sent in requeset
	 */
	protected boolean validate(String blogid, String username, String password)
			throws Exception {
		boolean authenticated = false;
		boolean weblogFound = false;

		Blog blogModel = this.blogService.getBlog(Integer.valueOf(blogid));
		weblogFound = (blogModel != null);

		authenticated = this.validateUser(username, password)
				&& blogModel.getAuthor().equals(username);
		if (!authenticated) {
			throw new XmlRpcException(AUTHORIZATION_EXCEPTION,
					AUTHORIZATION_EXCEPTION_MSG);
		}
		if (!weblogFound) {
			throw new XmlRpcException(WEBLOG_NOT_FOUND, WEBLOG_NOT_FOUND_MSG);
		}
		return true;
	}

	protected String getBlogUrl(Blog blogModel) {
		return this.commonService.getConfig().getDomainRoot() + "/"
				+ UrlConstants.BLOG + blogModel.getId();
	}

	/**
	 * Returns true if username/password are valid and user is not disabled.
	 * 
	 * @param username
	 *            Username sent in request
	 * @param password
	 *            Password sent in requeset
	 */
	protected boolean validateUser(String username, String password)
			throws Exception {
		boolean authenticated = false;
		MainUser mainUser = this.userService.getUser(username);
		authenticated = UserHelper.authPassword(mainUser, password);
		if (!authenticated) {
			throw new XmlRpcException(AUTHORIZATION_EXCEPTION,
					AUTHORIZATION_EXCEPTION_MSG);
		}
		return authenticated;
	}

}
