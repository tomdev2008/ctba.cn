package org.net9.core.types;

public enum MessageBoxType {

	MSG_TYPE_SEND(0), 
	
	MSG_TYPE_RECEIVE(1), 
	
	MSG_TYPE_ALL(2);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	MessageBoxType(Integer value) {
		this.value = value;
	}

	Integer value;
}
