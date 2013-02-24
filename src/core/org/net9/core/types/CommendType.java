package org.net9.core.types;

public enum CommendType {

	BBS(0),

	BLOG(1),

	GROUP(2),

	NEWS(3),

	SUBJECT(4), VOTE(5),

	USER(6), COMMON(7),

	INDEX_PAGE(100);

	Integer value;

	CommendType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
