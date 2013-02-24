package org.net9.core.types;

public enum BbsTopicOptionType {
	
	TOPIC_OPTION_ALL(0), 
	
	TOPIC_OPTION_USER(1),
	
	TOPIC_OPTION_FORBIDDEN(2);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	BbsTopicOptionType(Integer value) {
		this.value = value;
	}

	Integer value;
}
