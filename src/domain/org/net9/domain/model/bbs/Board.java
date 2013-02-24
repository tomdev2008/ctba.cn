package org.net9.domain.model.bbs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.net9.domain.model.BaseModel;

/**
 * 板块信息
 * 
 * @author gladstone
 * 
 */
@Entity
@Table(name = "bbs_boards", uniqueConstraints = {})
@SuppressWarnings("serial")
public class Board extends BaseModel {

	@Id
	Integer boardId;// 索引

	Integer boardLevel;// 板块层次

	Integer boardParent;// 父板块

	String boardName;// 名字

	String boardInfo;// 简介

	String boardMaster1;// 版大

	String boardMaster2;// 版二

	String boardMaster3;// 版三

	Integer boardTodayNum;// 今天主题数

	Long boardNewTopic;// 最新主题

	Integer boardState = 127;// 状态 1记入十大 2可见 4可发文 8可推荐文章 16可投票 32只读 64可贴附件

	Integer boardTopicNum;// 主题数

	Integer boardReNum;// 回复数

	Integer boardOption;// 需要的权限

	Integer boardRecom;// 推荐主题

	/** 板块的图片路径 add at 2007.6.24 by gladstone */
	String boardFace;

	Integer boardType;// 板块的类型

	String boardCode;

	public String getBoardCode() {
		return boardCode;
	}

	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}


	public Integer getBoardType() {
		return boardType;
	}

	public void setBoardType(Integer boardType) {
		this.boardType = boardType;
	}

	public Integer getBoardId() {
		return boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public String getBoardInfo() {
		return boardInfo;
	}

	public void setBoardInfo(String boardInfo) {
		this.boardInfo = boardInfo;
	}

	public Integer getBoardLevel() {
		return boardLevel;
	}

	public void setBoardLevel(Integer boardLevel) {
		this.boardLevel = boardLevel;
	}

	public String getBoardMaster1() {
		return boardMaster1;
	}

	public void setBoardMaster1(String boardMaster1) {
		this.boardMaster1 = boardMaster1;
	}

	public String getBoardMaster2() {
		return boardMaster2;
	}

	public void setBoardMaster2(String boardMaster2) {
		this.boardMaster2 = boardMaster2;
	}

	public String getBoardMaster3() {
		return boardMaster3;
	}

	public void setBoardMaster3(String boardMaster3) {
		this.boardMaster3 = boardMaster3;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public Long getBoardNewTopic() {
		return boardNewTopic;
	}

	public void setBoardNewTopic(Long boardNewTopic) {
		this.boardNewTopic = boardNewTopic;
	}

	public Integer getBoardOption() {
		return boardOption;
	}

	public void setBoardOption(Integer boardOption) {
		this.boardOption = boardOption;
	}

	public Integer getBoardParent() {
		return boardParent;
	}

	public void setBoardParent(Integer boardParent) {
		this.boardParent = boardParent;
	}

	public Integer getBoardRecom() {
		return boardRecom;
	}

	public void setBoardRecom(Integer boardRecom) {
		this.boardRecom = boardRecom;
	}

	public Integer getBoardReNum() {
		return boardReNum;
	}

	public void setBoardReNum(Integer boardReNum) {
		this.boardReNum = boardReNum;
	}

	public Integer getBoardState() {
		return boardState;
	}

	public void setBoardState(Integer boardState) {
		this.boardState = boardState;
	}

	public Integer getBoardTodayNum() {
		return boardTodayNum;
	}

	public void setBoardTodayNum(Integer boardTodayNum) {
		this.boardTodayNum = boardTodayNum;
	}

	public Integer getBoardTopicNum() {
		return boardTopicNum;
	}

	public void setBoardTopicNum(Integer boardTopicNum) {
		this.boardTopicNum = boardTopicNum;
	}

	public String getBoardFace() {
		return boardFace;
	}

	public void setBoardFace(String boardFace) {
		this.boardFace = boardFace;
	}

}
