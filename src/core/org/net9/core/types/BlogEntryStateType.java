package org.net9.core.types;

public enum BlogEntryStateType {
	
	ALL(0), 
	
	USER(1),
	
	FRIEND(2),
	
	FORBIDDEN(3);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	BlogEntryStateType(Integer value) {
		this.value = value;
	}

	Integer value;
}
