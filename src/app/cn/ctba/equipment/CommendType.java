package cn.ctba.equipment;

/**
 * 0-> 本店推荐 1-> // 别店商品 2->站外链接
 * 
 * @author ChenChangRen
 * 
 */
public enum CommendType {

	SELF(0),

	OTHER(1),

	EXTERNAL(2);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	CommendType(Integer value) {
		this.value = value;
	}

	Integer value;
}
