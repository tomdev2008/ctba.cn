package org.net9.core.types;

/**
 * 小组认证水平
 * 
 * @author gladstone
 * @since Mar 19, 2009
 */
public interface GroupAuthLevelType {

	/** 认证水平,0为申请会员 */
	public static final Integer AUTH_LEVEL_MEMBER = 0;
	/** 认证水平, 2为不接受申请 */
	public static final Integer AUTH_LEVEL_FORBIDDEN = 2;
	/** 认证水平, 1为需要认证 */
	public static final Integer AUTH_LEVEL_AUTH = 1;

}
