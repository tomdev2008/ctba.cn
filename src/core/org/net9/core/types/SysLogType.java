package org.net9.core.types;

public enum SysLogType {

	SYS_GUEST_SESSION_IN(1),

	SYS_GUEST_SESSION_OFF(2),

	SYS_ADMIN_LOGIN(3),

	SYS_ADMIN_LOGOUT(4),

	SYS_ADMIN_LOGIN_ERROR(5),

	SYS_INDEX_PAGE_VIEW(6);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	SysLogType(Integer value) {
		this.value = value;
	}

	Integer value;
}
