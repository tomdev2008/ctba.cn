package org.net9.core.types;

public enum NewsStateType {

	OK(2), WAITING(0);

	Integer value;

	NewsStateType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
