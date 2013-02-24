package org.net9.core.types;

/**
 * #217 用户操作日志(UserLog)
 * 
 * @author gladstone
 * 
 */
public enum UserEventType {

	LOGIN(1000),

	PAGE(1001),

	EDIT_INFO(1002),

	EDIT_FACE(1003),

	ADD_FRIEND(1004),

	REGISTER(1005),

	NOTE(1006),

	SCORE_TRAD(1007), // #852 (CTBA社区财富(CTB))
	
	DONATE(1008), // 捐赠

	TOPIC_NEW(2000),

	TOPIC_REPLY(2001),

	TOPIC_FAV(2002),

	TOPIC_EDIT(2003),

	VOTE_NEW(2004),

	VOTE_DO(2005),

	VOTE_COMMENT(2006),

	GROUP_NEW(3000),

	GROUP_APPLY(3001),

	GROUP_QUIT(3002),

	GROUP_TOPIC_NEW(3003),

	GROUP_TOPIC_REPLY(3004),

	GROUP_IMG_NEW(3005),

	GROUP_ACTIVITY_NEW(3006),

	GROUP_ACTIVITY_JOIN(3007),

	GROUP_TOPIC_EDIT(3008),

	GROUP_ACTIVITY_COMMENT(3009),

	BLOG_NEW(4000),

	BLOG_EDIT(4001),

	ENTRY_NEW(4002),

	ENTRY_EDIT(4003),

	COMMENT_NEW(4004),

	ENTRY_MINI_NEW(4005),

	ENTRY_NEW_MOBILE(4006),

	ENTRY_EDIT_MOBILE(4007),

	MAIL_INVITE(5001),

	NEWS_POST(6001),

	NEWS_COMMENT(6002),

	SHARE_NEW(7001),

	SHARE_COMMENT(7002),

	SHARE_NEW_TOOL(7003),

	SHARE_NEW_MOBILE(7004),

	GALLERY_NEW(8000),

	GALLERY_EDIT(8001),

	GALLERY_IMAGE_NEW(8002),

	GALLERY_IMAGE_COMMENT(8003),

	/** 用户上传装备 */
	EQUIPMENT_NEW(9001),

	/** 用户修改装备信息 */
	EQUIPMENT_EDIT(9002),

	/** 用户评论装备 */
	EQUIPMENT_COMMENT(9003),

	/** 用户和装备建立联系 */
	EQUIPMENT_REF(9004),

	/** 用户给装备打分 */
	EQUIPMENT_SCORE(9005);

	private int value;

	private UserEventType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
