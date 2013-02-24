package org.net9.core.types;

public enum FileType {

	IMAGE(0),

	FACE(1),

	ATTACHMENT(2),

	OTHER(100);

	Integer value;

	FileType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
