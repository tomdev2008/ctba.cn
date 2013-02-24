package cn.ctba.app;

public enum OrderFetchType {

	NONE(1),

	BY_SELF(2),

	BY_LOGISTICS(3);

	Integer value;

	OrderFetchType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
