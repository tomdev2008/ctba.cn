package org.net9.core.types;

public enum SharePrivateStateType {
	
	PUBLIC(0), 
	
	UN_PUBLIC(1);
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	SharePrivateStateType(Integer value) {
		this.value = value;
	}

	Integer value;
}
