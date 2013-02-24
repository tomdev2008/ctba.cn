package cn.ctba.widget.dto;

/**
 * uid,信件数,通知数,分享数,今日新鲜事数目(朋友的),是否管理员
 * 
 * @author ChenChangRen
 * 
 */
public class UserInfo {

	Integer uid;
	Integer messageCnt;
	Integer noticeCnt;
	Integer shareCnt;
	Integer timelineCnt;
	Integer isEditor;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getMessageCnt() {
		return messageCnt;
	}

	public void setMessageCnt(Integer messageCnt) {
		this.messageCnt = messageCnt;
	}

	public Integer getNoticeCnt() {
		return noticeCnt;
	}

	public void setNoticeCnt(Integer noticeCnt) {
		this.noticeCnt = noticeCnt;
	}

	public Integer getShareCnt() {
		return shareCnt;
	}

	public void setShareCnt(Integer shareCnt) {
		this.shareCnt = shareCnt;
	}

	public Integer getTimelineCnt() {
		return timelineCnt;
	}

	public void setTimelineCnt(Integer timelineCnt) {
		this.timelineCnt = timelineCnt;
	}

	public Integer getIsEditor() {
		return isEditor;
	}

	public void setIsEditor(Integer isEditor) {
		this.isEditor = isEditor;
	}

}
