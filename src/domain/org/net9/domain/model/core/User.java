package org.net9.domain.model.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * 用户的社区信息，完整信息见MainUser
 * 
 * @author gladstone
 * 
 */
@Entity
@Table(name = "bbs_users", uniqueConstraints = {})
@SuppressWarnings("serial")
public class User extends BaseModel {

	@Id
	Integer userId;

	String userName;// 用户名

	Integer userOption;// 权限 匿名用户是0 普通用户是1 版主是2 站务是3 vip用户是4

	String userNick;// 昵称

	String userFace;// 头像

	Integer userScore;// 分数

	String userSMD;// 说明档

	String userQMD;// 签名档

	Integer userPageCount;// 用戶頁面被訪問次數

	Integer userIsEditor;

	public String getUserFace() {
		return userFace;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getUserIsEditor() {
		return userIsEditor;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserNick() {
		return userNick;
	}

	public Integer getUserOption() {
		return userOption;
	}

	public Integer getUserPageCount() {
		return userPageCount;
	}

	public String getUserQMD() {
		return userQMD;
	}

	public Integer getUserScore() {
		if(userScore==null){
			return 0;
		}
		return userScore;
	}

	public String getUserSMD() {
		return userSMD;
	}

	public void setUserFace(String userFace) {
		this.userFace = userFace;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserIsEditor(Integer userIsEditor) {
		this.userIsEditor = userIsEditor;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public void setUserOption(Integer userOption) {
		this.userOption = userOption;
	}

	public void setUserPageCount(Integer userPageCount) {
		this.userPageCount = userPageCount;
	}

	public void setUserQMD(String userQMD) {
		this.userQMD = userQMD;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	public void setUserSMD(String userSMD) {
		this.userSMD = userSMD;
	}

}
