package org.net9.core.types;

public enum NoticeType {

	SYSTEM(2),
	
	COMMON(0),

	REPLY(1);

	private int value;

	private NoticeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
