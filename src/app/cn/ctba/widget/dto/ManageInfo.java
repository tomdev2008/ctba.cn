package cn.ctba.widget.dto;

/**
 * 在线人数 // 在线游客数 // 用户反馈数
 * 
 * @author ChenChangRen
 * 
 */
public class ManageInfo {
	Integer onlineCnt;
	Integer guestCnt;
	Integer feedbackCnt;

	public Integer getOnlineCnt() {
		return onlineCnt;
	}

	public void setOnlineCnt(Integer onlineCnt) {
		this.onlineCnt = onlineCnt;
	}

	public Integer getGuestCnt() {
		return guestCnt;
	}

	public void setGuestCnt(Integer guestCnt) {
		this.guestCnt = guestCnt;
	}

	public Integer getFeedbackCnt() {
		return feedbackCnt;
	}

	public void setFeedbackCnt(Integer feedbackCnt) {
		this.feedbackCnt = feedbackCnt;
	}
}
