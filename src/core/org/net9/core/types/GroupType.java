package org.net9.core.types;


/**
 * group types
 * 
 * @author gladstone
 * 
 */
public class GroupType implements Comparable<GroupType>{

	private Integer index;

	private String name;

	private String[] tags;

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

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

	public int compareTo(GroupType o) {
		if(this.index.intValue()>o.index.intValue()){
			return 1;
		}
		return 0;
	}

}
