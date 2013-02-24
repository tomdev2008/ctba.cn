package org.net9.core.types;

/**
 * #840 (系统管理员的权限)
 * 
 * @author gladstone
 * @since May 30, 2009
 */
public interface UserSecurityType {

	/** 游客 */
	public static final int OPTION_LEVEL_ALL = 0;
	/** 用户 */
	public static final int OPTION_LEVEL_USER = 1;
	/** 版主或者小组长 */
	public static final int OPTION_LEVEL_MANAGE = 2;
	/** 编辑 */
	public static final int OPTION_LEVEL_EDITOR = 3;
	/** 后台管理员 */
	public static final int OPTION_LEVEL_ADMIN = 4;

}
