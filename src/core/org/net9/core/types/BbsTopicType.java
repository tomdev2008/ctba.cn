package org.net9.core.types;

public enum BbsTopicType {
	
	TOPIC_TYPE_NORMAL(1), 
	
	TOPIC_TYPE_PRIME(2),
	
	TOPIC_TYPE_TOP(3);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	BbsTopicType(Integer value) {
		this.value = value;
	}

	Integer value;
}
