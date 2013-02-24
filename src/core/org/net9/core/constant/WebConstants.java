package org.net9.core.constant;

/**
 * web相关定数
 * 
 * @author ChenChangRen
 */
public interface WebConstants {

	/** 分页每页的条数(30) */
	public static final int PAGE_SIZE_30 = 30;
	/** 分页每页的条数(15) */
	public final static int PAGE_SIZE_15 = 15;

	public static final String USER_NAME = "j2bb_auth_username";
	public static final String ADMIN_NAME = "admin";
	public static final String SESSION_IMAGE_NUM = "imageNum";

	public static final String PAGER_OFFSET = "pager.offset";
	public static final String MANAGE_PREFIX = "manage";
	public static final String SESSION_KEY_URL = "session_url";
	public static final String LOAD_TYPE_AJAX_STR = "loadType=ajax";
	public static final String COOKIE_KEY_THEME = "ctba_j2bb3_community_them";
	public static final String COOKIE_KEY_SSO_AUTH = "ctba_j2bb3_community_sso";
	public static final String PARAMETER_REPLY_TO = "para_reply_to";
	/** #727 针对搜索引擎来源用户的提示 */
	public static final String REQUEST_SEARCH_KEYWORD = "request_search_keyword";
	/** #727 针对搜索引擎来源用户的提示 */
	public static final String REQUEST_SEARCH_RESULT = "request_search_result";
}
