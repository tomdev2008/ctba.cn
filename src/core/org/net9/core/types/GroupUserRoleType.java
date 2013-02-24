package org.net9.core.types;

/**
 * 小组用户角色类型
 * 
 * @author gladstone
 * @since Mar 19, 2009
 */
public interface GroupUserRoleType {

	/**
	 * 小组管理员
	 */
    public static final Integer GROUP_USER_ROLE_MANAGER = 2;
    /**
     * 普通会员
     */
    public static final Integer GROUP_USER_ROLE_NORMAL = 1;
    /** 等待认证中 */
    public static final Integer GROUP_USER_ROLE_WAIT = 0;
}
