package org.net9.core.types;

public enum MessageType {

	NORMAL(0), 
	
	SYSTEM(1), 
	
	FRIEND(2);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	MessageType(Integer value) {
		this.value = value;
	}

	Integer value;
}
