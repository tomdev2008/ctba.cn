package cn.ctba.equipment;

/**
 * 装备状态 #779 (装备秀改进 - 其他)
 * 
 * @author gladstone
 * @since Jun 1, 2009
 */
public enum EquipmentStateType {

	COMMENDED(2), OK(1), WAITING(0);

	Integer value;

	EquipmentStateType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
