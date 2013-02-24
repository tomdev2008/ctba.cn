package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

@Entity
@Table(name = "bbs_messages", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Message extends BaseModel {
	@Id
	Integer msgId;

	String msgTo;

	String msgTitle;

	String msgFrom;

	String msgContent;

	String msgTime;

	Integer msgRead;

	Integer msgStar;

	Integer msgType = 0;

	String msgAttachment;

	public String getMsgAttachment() {
		return msgAttachment;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public String getMsgFrom() {
		return msgFrom;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public Integer getMsgRead() {
		return msgRead;
	}

	public Integer getMsgStar() {
		return msgStar;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public String getMsgTo() {
		return msgTo;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgAttachment(String msgAttachment) {
		this.msgAttachment = msgAttachment;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public void setMsgRead(Integer msgRead) {
		this.msgRead = msgRead;
	}

	public void setMsgStar(Integer msgStar) {
		this.msgStar = msgStar;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

}
