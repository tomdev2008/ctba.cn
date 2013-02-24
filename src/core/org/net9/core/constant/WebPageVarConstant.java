package org.net9.core.constant;

/**
 * 用于页面的系统定数
 * 
 * @author gladstone
 * @since Nov 16, 2008
 */
public interface WebPageVarConstant {

	public static final String ACTION_DONE = "__sys_action_done";

	public static final String USERNAME = "__sys_username";

	public static final String USERNAME_FOCUS = "__sys_username_focus";

	public static final String IS_SELF = "__sys_is_self";

	public static final String MSG_CNT = "__sys_msgCnt";

	public static final String NOTICE_CNT = "__sys_noticeCnt";

	/** 设置了邮件地址的用户 */
	public static final String EMAIL_USERS = "__email_users";

	/** 设置了邮件地址并且不拒绝系统信件的用户 */
	public static final String EMAIL_VALID_USERS = "__email_valid_users";

	public static final String APP_SUBJECT_TYPE_LIST = "__app_subject_type_list";

	public static final String APP_NEWS_TYPE_LIST = "__app_news_type_list";

	public static final String COMMON_IS_ROBOT = "__common_is_robot";
	/** #840 (系统管理员的权限) */
	public static final String USER_IS_EDITOR = "__user_is_editor";

}
