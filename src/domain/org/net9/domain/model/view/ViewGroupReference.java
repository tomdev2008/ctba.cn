package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * 
 * @author gladstone
 * @since Dec 1, 2008
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "view_group_reference", uniqueConstraints = {})
public class ViewGroupReference extends BaseModel {

	private Integer gid;
	@Id
	private Integer refid;

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getRefid() {
		return refid;
	}

	public void setRefid(Integer refid) {
		this.refid = refid;
	}

}
