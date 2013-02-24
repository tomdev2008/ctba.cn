package org.net9.core.types;

public enum ImageOptionType {

	PRIVATE(2),

	PROTECTED(1),

	PUBLIC(0);

	int value;

	ImageOptionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
