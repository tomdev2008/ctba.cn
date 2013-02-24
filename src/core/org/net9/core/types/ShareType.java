package org.net9.core.types;

public enum ShareType {

	BBS(0),

	BLOG(1),

	GROUP(2),

	NEWS(3),

	SUBJECT(4), VOTE(5),

	OTHER_INNER(5),

	OTHER_OUTTER(100),
	
	OTHER_OUTTER_WORDS_ONLY(101);

	Integer value;

	ShareType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
