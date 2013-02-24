package cn.ctba.app.web.struts.action;

@SuppressWarnings("serial")
public class WrappedEvent {
	private String eventStr;
	private String mobileEventStr;
	private Integer id;

	public String getMobileEventStr() {
		return mobileEventStr;
	}

	public void setMobileEventStr(String mobileEventStr) {
		this.mobileEventStr = mobileEventStr;
	}

	private String updateTime;

	private String username;

	private String target;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getEventStr() {
		return eventStr;
	}

	public void setEventStr(String eventStr) {
		this.eventStr = eventStr;
	}
}
