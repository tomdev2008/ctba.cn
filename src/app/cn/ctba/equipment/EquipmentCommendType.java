package cn.ctba.equipment;

public enum EquipmentCommendType {

	LOCAL(0),

	OTHER(1);

	Integer value;

	EquipmentCommendType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
