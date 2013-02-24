package org.net9.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.Md5Utils;
import org.net9.common.util.StringUtils;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.service.UserService;
import org.net9.core.types.UserPrivacySettingType;
import org.net9.core.types.UserSecurityType;
import org.net9.domain.model.BaseModel;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;

/**
 * 用户帮助器，检查用户是否登录等等
 * 
 * @author gladstone
 * 
 */
public class UserHelper {

	static Log log = LogFactory.getLog(UserHelper.class);

	/**
	 * 返回用户名(邮箱)
	 * 
	 * @param request
	 * @return
	 */
	public static String getuserFromCookie(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		String username = null;
		// use the session type
		String authType = SystemConfigUtils.getProperty("user.auth.type");
		if ("session".equalsIgnoreCase(authType)) {
			username = (String) request.getSession().getAttribute(
					BusinessConstants.USER_NAME);
			return username;
		}
		if (cookies == null)
			return null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(
					SystemConfigUtils.getProperty("cookie.key.sso.auth"))) {
				Cookie userCookie = cookies[i];
				username = userCookie.getValue();
			}
		}
		log.debug("Got username:" + username);
		if (StringUtils.isNotEmpty(username)) {
			username = StringUtils.base64Decode(username);
		}
		log.debug("Decode username:" + username);
		return username;
	}

	/**
	 * #724 后台验证追加检讨
	 * 
	 * 为当前的pojo提供简单的作者认证，如果不是作者的时候，会抛出InvalidAuthorSecurityException例外
	 * 
	 * @param request
	 * @param model
	 * @throws InvalidAuthorSecurityException
	 */
	public static void authUserSimply(HttpServletRequest request,
			BaseModel model) throws InvalidAuthorSecurityException {
		String username = getuserFromCookie(request);
		authUserForCurrentPojoSimply(username, model);
	}

	/**
	 * <li>#724 后台验证追加检讨
	 * <li>#840 (系统管理员的权限)
	 * 为当前的pojo提供作者认证，如果不是作者的时候，会抛出InvalidAuthorSecurityException例外
	 * 
	 * 允许系统管理员修改
	 * 
	 * @param request
	 * @param model
	 * @throws InvalidAuthorSecurityException
	 */
	public static void authUserWithEditorOption(HttpServletRequest request,
			BaseModel model, UserService userService)
			throws InvalidAuthorSecurityException {
		String username = getuserFromCookie(request);
		if (userService.isEditor(username)) {
			return;
		}
		authUserForCurrentPojoSimply(username, model);
	}

	/**
	 * #724 后台验证追加检讨
	 * 
	 * 为当前的pojo提供简单的作者认证，如果不是作者的时候，会抛出InvalidAuthorSecurityException例外
	 * 
	 * @param username
	 * @param model
	 * @throws InvalidAuthorSecurityException
	 */
	public static void authUserForCurrentPojoSimply(String username,
			BaseModel model) throws InvalidAuthorSecurityException {
		if (model == null) {
			return;
		}
		String authorName = getAuthorName(model);
		if (StringUtils.isNotEmpty(authorName) && !authorName.equals(username)) {
			throw new InvalidAuthorSecurityException("error.noOption.noLogin");
		}
	}

	public static String getAuthorName(BaseModel model)
			throws InvalidAuthorSecurityException {
		if (model == null) {
			return "";
		}
		String authorName = null;
		Class<?> clazz = model.getClass();
		String fieldName = null;
		for (Field field : clazz.getDeclaredFields()) {
			if ("username".equalsIgnoreCase(field.getName())) {
				fieldName = field.getName();
				break;
			} else if ("author".equalsIgnoreCase(field.getName())) {
				fieldName = field.getName();
				break;
			} else if ("topicAuthor".equalsIgnoreCase(field.getName())) {
				fieldName = field.getName();
				break;
			} else if ("frdUserMe".equalsIgnoreCase(field.getName())) {
				fieldName = field.getName();
				break;
			} else if ("msgTo".equalsIgnoreCase(field.getName())) {
				fieldName = field.getName();
				break;
			}
		}
		if (StringUtils.isNotEmpty(fieldName)) {
			String getterName = "get" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			try {
				Method m = clazz.getMethod(getterName);
				authorName = (String) m.invoke(model, new Object[] {});
			} catch (Exception e) {
				// e.printStackTrace();
				log.error(e.getMessage());
				return "";
			}
		}
		return authorName;
	}

	/**
	 * 判断用户是否登录
	 * 
	 * @param request
	 * @return
	 */
	public static boolean userLogined(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String username = null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(
					SystemConfigUtils.getProperty("cookie.key.sso.auth"))) {
				Cookie userCookie = cookies[i];
				username = userCookie.getValue();
				if (CommonUtils.isEmpty(username))
					return false;
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证用户密码(MD5),
	 * 
	 * 需要注意的是,CTBA原先的用户采用的是短(64位)编码,而标准的是128位,所以不能只用等于来判断,而需要判断包含
	 * 
	 * @param mainUser
	 * @param password
	 * @return
	 */
	public static boolean authPassword(MainUser mainUser, String password) {
		if (mainUser != null
				&& Md5Utils.getMD5(password).indexOf(mainUser.getPassword()) >= 0) {
			return true;
		}
		return false;
	}

	public static MainUser populateMainUser(String username, String password,
			String email) {
		MainUser user = new MainUser();
		user.setPassword(password);
		user.setUsername(username);
		user.setEmail(email);
		user.setSex(0);
		user.setRegTime(StringUtils.getTimeStrByNow());
		user.setPrivacySetting(UserPrivacySettingType.ALL.getValue());
		return user;
	}

	public static User populateUser(String username, Integer uid) {
		User model = new User();
		model.setUserPageCount(0);
		model.setUserFace("");
		model.setUserId(uid);
		model.setUserName(username);
		model.setUserNick("");
		model.setUserOption(UserSecurityType.OPTION_LEVEL_USER);
		model.setUserIsEditor(0);
		model.setUserScore(100);// 初始化CTB100
		return model;
	}

}
