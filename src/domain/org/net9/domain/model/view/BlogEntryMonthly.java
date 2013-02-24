package org.net9.domain.model.view;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = "view_blog_entry_monthly", uniqueConstraints = {})
public class BlogEntryMonthly extends BaseModel {

	private Integer bid;

	@Id
	private String month;

	private Integer cnt;

	public Integer getBid() {
		return bid;
	}

	public Integer getCnt() {
		return cnt;
	}

	public String getMonth() {
		return month;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
