package cn.ctba.equipment;

/**
 * type目前主要做
 * <li>优先级1： (1)球鞋,(2)网羽
 * <li>优先级2： (3)服饰,(4)数码
 * 
 * @author gladstone
 * @since May 20, 2009
 */
public class EquipmentBrandType {

	private Integer type;

	private Integer index;

	private String name;// EN

	private String alias; // CH

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
