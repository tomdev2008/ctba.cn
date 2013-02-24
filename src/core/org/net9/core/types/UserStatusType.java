package org.net9.core.types;

public enum UserStatusType {

	USER_STATUS_OFFLINE(0),

	USER_STATUS_ONLINE(1);


	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	UserStatusType(Integer value) {
		this.value = value;
	}

	Integer value;
}
