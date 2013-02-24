package cn.ctba.app;

public enum OrderStateType {

	NEW(0), // 新建

	PAID(1), // 已付款

	SENT(2), // 已发货

	DONE(3);// 成交

	Integer value;

	OrderStateType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
