package org.net9.core.types;

public enum TopicType {

	NORMAL(1),

	PRIME(2),

	TOP(3);

	int value;

	TopicType(Integer value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
