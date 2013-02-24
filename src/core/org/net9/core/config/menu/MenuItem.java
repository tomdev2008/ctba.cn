package org.net9.core.config.menu;

import java.util.List;

/**
 * manu item model
 * 
 * @author gladstone
 * 
 */
public class MenuItem implements java.io.Serializable {

	private static final long serialVersionUID = -5988994311302266647L;

	private List<MenuItem> children;

	private String name;

	private String link;

	private String role;

	private String confirm;

	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		String str = "Name: " + this.getName() + "[" + this.getRole()
				+ "] Link:" + this.getLink();
		if (children != null && children.size() > 0) {
			str += "\n{\n";
			for (MenuItem child : children) {
				str += child.toString() + "\n";
			}

			str += "}\n";
		}

		return str;
	}
}
