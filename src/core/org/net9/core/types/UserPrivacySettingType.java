package org.net9.core.types;

public enum UserPrivacySettingType {

	SELF(3),

	FRIEND(2),

	MEMBER(1),

	ALL(0);

	int value;

	UserPrivacySettingType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
