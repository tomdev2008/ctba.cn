package org.net9.core.types;
/**
 * #630 (增加封禁IP功能) 系统封禁类型
 * @author gladstone
 * @since Feb 14, 2009
 */
public enum SysBlacklistType {
	
	USER(0), 
	
	IP(1);

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	SysBlacklistType(Integer value) {
		this.value = value;
	}

	Integer value;
}
