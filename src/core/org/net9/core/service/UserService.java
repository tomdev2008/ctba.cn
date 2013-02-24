package org.net9.core.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.DateUtils;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.common.webservice.annotation.WebServiceDelegated;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.types.MessageBoxType;
import org.net9.core.types.NoticeType;
import org.net9.core.types.UserEventType;
import org.net9.core.types.UserSecurityType;
import org.net9.domain.dao.view.ViewTimelineDAO;
import org.net9.domain.dao.view.ViewUserPageLogDAO;
import org.net9.domain.model.bbs.Userboard;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Message;
import org.net9.domain.model.core.Notice;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.view.ViewTimeline;
import org.net9.domain.model.view.ViewUserPageLog;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * 用户服务
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class UserService extends BaseService {

	private static final long serialVersionUID = -97839088743765629L;

	private static final Log log = LogFactory.getLog(UserService.class);

	@Inject
	ViewUserPageLogDAO viewUserPageLogDAO;

	@Inject
	ViewTimelineDAO viewTimelineDAO;

	/**
	 * 
	 * #795 (timeline 中，相同事件不同时间的处理)
	 * 
	 * 视图的转换
	 * 
	 * @param timelineModelList
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<UserLog> convertTimeline(List<ViewTimeline> timelineModelList) {
		List<UserLog> list = new ArrayList<UserLog>();
		for (ViewTimeline model : timelineModelList) {
			UserLog l = new UserLog();
			try {
				PropertyUtil.copyProperties(l, model);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			list.add(l);
		}
		return list;
	}

	/**
	 * 删除好友
	 * 
	 */
	public void deleteFriend(Integer id) {
		Friend model = this.getFriendById(id);
		friendDAO.remove(model);
	}

	/**
	 * 删除消息
	 * 
	 * @param messageId
	 */
	public void deleteMessage(Integer messageId) {
		Message model = getMessage(messageId);
		if (model != null) {
			messageDAO.remove(model);
		}
	}

	public void deleteUserLog(UserLog model) {
		if (model != null) {
			this.userlogDAO.remove(model);
		}
	}

	/**
	 * 查找用户事件,包括hide字段是1的
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @param types
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserLog> findAllUserlogs(String username, Integer start,
			Integer limit, Integer[] types) {

		String jpql = "select model from UserLog model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}

		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		jpql += " order by model.id desc";
		return userlogDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据标签得到用户的社区好友
	 * 
	 * @param username
	 * @param tag
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Friend> findFriends(String username, String tag, int start,
			int limit) {
		String jpql = "select model from Friend model where model.frdUserMe='"
				+ username + "' ";
		if (CommonUtils.isNotEmpty(tag)) {
			jpql += " and  model.frdTag='" + tag + "' ";
		}
		jpql += " order by model.frdId desc";
		return friendDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据用户名的到消息列表
	 * 
	 * @param username
	 * @param type
	 * @param read
	 *            是否已读
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> findMsgsByUser(String username, int type,
			boolean read, Boolean star, Integer start, Integer limit) {
		String jpql = "select model from Message model ";
		if (type == MessageBoxType.MSG_TYPE_RECEIVE.getValue().intValue()) {
			jpql += " where  model.msgTo='" + username + "'   ";
		} else if (type == MessageBoxType.MSG_TYPE_SEND.getValue().intValue()) {
			jpql += " where  model.msgFrom='" + username + "'   ";
		} else {
			jpql += " where  model.msgId>0 ";
		}
		if (read) {
			jpql += " and model.msgRead=0 ";
		}
		if (star != null && star) {
			jpql += " and model.msgStar>0 ";
		}
		if (read) {
			jpql += " and model.msgRead=0 ";
		}
		if (star != null && star) {
			jpql += " and model.msgStar>0 ";
		}
		jpql += " order by model.msgId desc";
		log.debug(jpql);
		return messageDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到有（未读）短信的用户列表 注意，使用jpa之后，这里还有问题
	 * 
	 * @param read
	 * @param start
	 * @param limit
	 * @return
	 */
	// TODO: 使用 Join 和 User 联合查询
	@SuppressWarnings("unchecked")
	public List<Message> findMsgsGroupByUser(boolean read, Integer start,
			Integer limit) {
		String jpql = "select distinct model from Message model  where model.msgId>0 ";
		if (!read) {
			jpql += " and model.msgRead=0 ";
		} else {
			jpql += " and model.msgRead=1 ";
		}
		jpql += "  order by model.msgId desc";// group by model.msgTo
		return messageDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根據用戶數組得到用戶事件
	 * 
	 * @param usernameArray
	 * @param start
	 * @param limit
	 * @param types
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserLog> findUserlogs(List<String> usernameArray,
			Integer start, Integer limit, Integer[] types) {
		// #795 (timeline 中，相同事件不同时间的处理)
		// 通过委托方法来查询视图,实现单一化

		String jpql = "select model from UserLog model where model.id>0 and model.hide=0 ";
		if (usernameArray.size() > 0) {
			jpql += " and model.username in ( ";
			for (String username : usernameArray) {
				jpql += "'" + username + "',";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		jpql += " order by model.id desc";
		return userlogDAO.findByStatement(jpql, start, limit);
		// List<ViewTimeline> timelineModelList = this.findViewTimeline(
		// usernameArray, start, limit, types);
		// return this.convertTimeline(timelineModelList);
	}

	/**
	 * 查找用戶事件
	 * 
	 * @param username
	 * @param target
	 * @param types
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserLog> findUserlogs(String username, String target,
			String date, Integer start, Integer limit, Integer[] types) {
		// #795 (timeline 中，相同事件不同时间的处理)
		// 通过委托方法来查询视图,实现单一化

		String jpql = "select model from UserLog model where model.id>0 and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (StringUtils.isNotEmpty(target)) {
			jpql += " and model.target='" + target + "'";
		}
		if (StringUtils.isNotEmpty(date)) {
			String nextDate = date;
			try {
				nextDate = CommonUtils.getDateFromDateOn(date, 1);
			} catch (ParseException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			jpql += " and model.updateTime>='" + date
					+ "' and model.updateTime<='" + nextDate + "' ";
		}

		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		jpql += " order by model.id desc";
		return userlogDAO.findByStatement(jpql, start, limit);
		// List<ViewTimeline> timelineModelList =
		// this.findViewTimeline(username,
		// target, date, start, limit, types);
		// return this.convertTimeline(timelineModelList);
	}

	/**
	 * 这个不管userlog.hide是否为1，都可见
	 * 
	 * @param target
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ViewUserPageLog> findUserPageLogs(String target, Integer start,
			Integer limit) {
		String jpql = "select model from ViewUserPageLog model where  model.target='"
				+ target + "'";
		return viewUserPageLogDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到用户列表
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@WebServiceDelegated(name = "findUsers")
	@SuppressWarnings("unchecked")
	public List<User> findUsers(Integer start, Integer limit) {
		String jpql = "select model from User model order by model.userId desc";
		return userDAO.findByStatement(jpql, start, limit);
	}

	@WebServiceDelegated(name = "findUsersByName")
	@SuppressWarnings("unchecked")
	public List findUsers(Integer option, String key, Integer start,
			Integer limit) {
		String jpql = "select model from User model where model.userId>0 ";
		if (CommonUtils.isNotEmpty(key)) {
			jpql += " and model.userName like '%" + key + "%' ";
		}
		if (option != null) {
			jpql += " and model.userOption=" + option;
		}
		jpql += " order by model.userId desc";
		return userDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<MainUser> findUsersByEmail(String email) {
		String jpql = "select model from MainUser model "
				+ " where model.email='" + email + "'";
		return mainUserDAO.findByStatement(jpql);

	}

	@SuppressWarnings("unchecked")
	public List<MainUser> findUsersByEmailList(List<String> emailList) {
		if (emailList.size() == 0) {
			return null;
		}
		String jpql = "SELECT m FROM MainUser m WHERE m.email IN(";
		for (String email : emailList) {
			jpql += "'" + email + "',";
		}
		jpql = jpql.substring(0, jpql.length() - 1) + ")";
		return mainUserDAO.findByStatement(jpql, 0, BusinessConstants.MAX_INT);
	}

	/**
	 * 得到diffDays天内没有登录的用户
	 * 
	 * @param diffDays
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MainUser> findUsersNotLogined4aWhile(Integer diffDays,
			Integer start, Integer limit) {
		String dateStr = CommonUtils.getDateFromTodayOn(-diffDays);
		String jpql = "select model from MainUser model where model.loginTime<'"
				+ dateStr + "' order by model.id desc";
		return mainUserDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 找出所有设置Email并且emailFlag大于0的用户
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MainUser> findUsersWithEmail(Integer start, Integer limit) {
		String jpql = "select m from MainUser m where NOT m.email IS NULL AND m.emailFlag>0 order by m.id desc";
		return mainUserDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 找出所有设置QQ的用户
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MainUser> findUsersWithQQ(Integer start, Integer limit) {
		String jpql = "select m from MainUser m where not m.qq is null order by m.id desc";
		return mainUserDAO.findByStatement(jpql, start, limit);
	}

	// /**
	// * 根据QQ得到用户列表
	// *
	// * @param qqList
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// public List<MainUser> findUsersByQQList(List<String> qqList) {
	// if (qqList.size() == 0) {
	// return null;
	// }
	// String jpql = "SELECT m FROM MainUser m WHERE m.qq IN(";
	// for (String email : qqList) {
	// jpql += "'" + email.substring(0, email.indexOf("(")) + "',";
	// }
	// jpql = jpql.substring(0, jpql.length() - 1) + ")";
	// return mainUserDAO.findByStatement(jpql, 0, BusinessConstants.MAX_INT);
	// }

	/**
	 * #795 (timeline 中，相同事件不同时间的处理)
	 * 
	 * @param usernameArray
	 * @param start
	 * @param limit
	 * @param types
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	private List<ViewTimeline> findViewTimeline(List<String> usernameArray,
			Integer start, Integer limit, Integer[] types) {
		String jpql = "select model from ViewTimeline model where model.id>0 and model.hide=0 ";
		if (usernameArray.size() > 0) {
			jpql += " and model.username in ( ";
			for (String username : usernameArray) {
				jpql += "'" + username + "',";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		jpql += " order by model.id desc";
		return viewTimelineDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * #795 (timeline 中，相同事件不同时间的处理)
	 * 
	 * @param username
	 * @param target
	 * @param date
	 * @param start
	 * @param limit
	 * @param types
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unused" })
	private List<ViewTimeline> findViewTimeline(String username, String target,
			String date, Integer start, Integer limit, Integer[] types) {
		String jpql = "select model from ViewTimeline model where model.id>0  and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (StringUtils.isNotEmpty(target)) {
			jpql += " and model.target='" + target + "'";
		}
		if (StringUtils.isNotEmpty(date)) {
			String nextDate = date;
			try {
				nextDate = CommonUtils.getDateFromDateOn(date, 1);
			} catch (ParseException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			jpql += " and model.updateTime>='" + date
					+ "' and model.updateTime<='" + nextDate + "' ";
		}

		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		jpql += " order by model.id desc";
		return viewTimelineDAO.findByStatement(jpql, start, limit);
	}

	public Integer getAllUserlogCnt(String username, Integer type) {
		String jpql = "select count(model) from UserLog model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (type != null) {
			jpql += " and model.type=" + type + "";
		}
		return userlogDAO.getCntByStatement(jpql);
	}

	/**
	 * get bm count
	 * 
	 * @param boardId
	 * @return
	 */
	public Integer getBmsCnt(Integer boardId) {
		String jpql = "select count(u) from User u, Userboard ub where ub.user_id=u.userId and ub.role>=2 and u.userOption >= "
				+ UserSecurityType.OPTION_LEVEL_MANAGE;
		if (boardId != null) {
			jpql += " and ub.board_id=" + boardId;
		}
		jpql += " order by u.userId desc";
		return userDAO.getCntByStatement(jpql);
	}

	/**
	 * get a friend model
	 * 
	 * @param username1
	 * @param username2
	 * @return
	 */
	private Friend getFriend(String frdUserMe, String frdUserYou) {
		String jpql = "select model from Friend model where model.frdUserMe='"
				+ frdUserMe + "' and model.frdUserYou='" + frdUserYou + "'";
		return (Friend) friendDAO.findSingleByStatement(jpql);
	}

	public Friend getFriendById(Integer frdId) {
		String jpql = "select model from Friend model where model.frdId="
				+ frdId;
		return (Friend) friendDAO.findSingleByStatement(jpql);
	}

	/**
	 * 得到好友的数目
	 * 
	 * @param username
	 * @param tag
	 * @return
	 */
	public Integer getFriendsCnt(String username, String tag) {
		String jpql = "select count(model) from Friend model where model.frdUserMe='"
				+ username + "' ";
		if (CommonUtils.isNotEmpty(tag)) {
			jpql += " and  model.frdTag='" + tag + "' ";
		}
		return friendDAO.getCntByStatement(jpql);
	}

	/**
	 * 根据id得到消息
	 * 
	 * @param messageId
	 * @return
	 */
	public Message getMessage(Integer messageId) {
		return (Message) messageDAO.getByPrimaryKey(Message.class, messageId);
	}

	/**
	 * 根据用户名得到消息数目
	 * 
	 * @param username
	 * @param type
	 * @param read
	 *            是否已读
	 * @return
	 */
	public int getMsgsCntByUser(String username, Integer type, boolean read,
			Boolean star) {
		String jpql = "select count(model) from Message model ";
		if (type == MessageBoxType.MSG_TYPE_RECEIVE.getValue().intValue()) {
			jpql += " where  model.msgTo='" + username + "'   ";
		} else if (type == MessageBoxType.MSG_TYPE_SEND.getValue().intValue()) {
			jpql += " where  model.msgFrom='" + username + "'   ";
		} else {
			jpql += " where  model.msgId>0 ";
		}
		if (read) {
			jpql += " and model.msgRead=0 ";
		}
		if (star != null && star) {
			jpql += " and model.msgStar>0 ";
		}
		return messageDAO.getCntByStatement(jpql);
	}

	// public int getMyBoardCnt(String username) {
	// User u = userDAO.getUser(username);
	// String jpql = "select count(model) Userboard model where model.user_id="
	// + u.getUserId();
	// return userboardDAO.getCntByStatement(jpql);
	// }

	/**
	 * 得到下一封消息
	 * 
	 * @param messageId
	 * @param read
	 * @param username
	 * @return
	 */
	public Message getNextMessage(Integer messageId, Boolean read,
			String username) {
		String jpql = "select model from Message model  where model.msgId> "
				+ messageId;
		if (read != null) {
			if (!read) {
				jpql += " and model.msgRead=0 ";
			} else {
				jpql += " and model.msgRead=1 ";
			}
		}
		jpql += " and model.msgTo='" + username + "'  order by model.msgId asc";
		log.debug(jpql);
		return (Message) messageDAO.findSingleByStatement(jpql);
	}

	/**
	 * 得到上一封消息
	 * 
	 * @param messageId
	 * @param read
	 * @param username
	 * @return
	 */
	public Message getPrevMessage(Integer messageId, Boolean read,
			String username) {
		String jpql = "select model from Message model  where model.msgId< "
				+ messageId;
		if (read != null) {
			if (!read) {
				jpql += " and model.msgRead=0 ";
			} else {
				jpql += " and model.msgRead=1 ";
			}
		}
		jpql += " and model.msgTo='" + username
				+ "'  order by model.msgId desc";
		log.debug(jpql);
		return (Message) messageDAO.findSingleByStatement(jpql);
	}

	@WebServiceDelegated(name = "getUser")
	public User getUser(Integer userId, String username) {
		User model = null;
		if (userId == null) {
			if (StringUtils.isEmpty(username)) {
				return null;
			}
			model = userDAO.getUser(username);
		} else {
			model = (User) userDAO.getByPrimaryKey(User.class, userId);
		}
		return model;
	}

	@WebServiceDelegated(name = "getMainUser")
	public MainUser getUser(String username) {
		String jpql = "select model from MainUser model where model.username='"
				+ username + "'";
		return (MainUser) mainUserDAO.findSingleByStatement(jpql);

	}

	public User getUserByNick(String usernick) {
		String jpql = "select model from  User model where model.userNick='"
				+ usernick + "'";
		return (User) userDAO.findSingleByStatement(jpql);

	}

	public Integer getUserCnt(Integer option, String key) {
		String jpql = "select count(model) from User model where model.userId>0 ";
		if (CommonUtils.isNotEmpty(key)) {
			jpql += " and model.userName like '%" + key + "%' ";
		}
		if (option != null) {
			jpql += " and model.userOption=" + option;
		}
		return userDAO.getCntByStatement(jpql);
	}

	/**
	 * 简单计算用户的资料完成度
	 * 
	 * @param username
	 * @return
	 */
	public String getUserInfoCompletePercent(String username) {
		MainUser mainUser = this.getUser(username);
		User user = this.getUser(null, username);

		// see #563
		// sex默认是0
		int done = 0;
		if (StringUtils.isNotEmpty(mainUser.getEmail())) {
			done++;
		}
		if (StringUtils.isNotEmpty(mainUser.getGtalk())) {
			done++;
		}
		if (StringUtils.isNotEmpty(mainUser.getMsn())) {
			done++;
		}
		if (StringUtils.isNotEmpty(mainUser.getQq())) {
			done++;
		}
		if (StringUtils.isNotEmpty(mainUser.getBirthday())) {
			done++;
		}
		if (StringUtils.isNotEmpty(user.getUserSMD())) {
			done++;
		}
		if (StringUtils.isNotEmpty(user.getUserQMD())) {
			done++;
		}
		if (StringUtils.isNotEmpty(user.getUserNick())) {
			done++;
		}
		if (StringUtils.isNotEmpty(user.getUserFace())) {
			done++;
		}
		int per = (int) (100 * ((double) (done + 1) / (double) 10));
		return String.valueOf(per);
	}

	public UserLog getUserLog(Integer id) {
		return (UserLog) this.userlogDAO.getByPrimaryKey(UserLog.class, id);
	}

	public Integer getUserlogCnt(String username, String target, Integer type) {
		String jpql = "select count(model) from UserLog model where model.id>0  and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (StringUtils.isNotEmpty(target)) {
			jpql += " and model.target='" + target + "'";
		}
		if (type != null) {
			jpql += " and model.type=" + type + "";
		}
		return userlogDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到用戶事件數量
	 * 
	 * @param username
	 * @param target
	 * @param types
	 * @return
	 */
	public Integer getUserlogsCnt(String username, String target, String date,
			Integer[] types) {
		String jpql = "select count(model) from UserLog model where model.id>0  and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (StringUtils.isNotEmpty(target)) {
			jpql += " and model.target='" + target + "'";
		}
		if (StringUtils.isNotEmpty(date)) {
			String nextDate = date;
			try {
				nextDate = CommonUtils.getDateFromDateOn(date, 1);
			} catch (ParseException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			jpql += " and model.updateTime>='" + date
					+ "' and model.updateTime<='" + nextDate + "' ";
		}
		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		return userlogDAO.getCntByStatement(jpql);
	}

	/**
	 * 根據用戶數組得到用戶事件數量
	 * 
	 * @param usernameArray
	 * @param types
	 * @return
	 */
	public Integer getUserlogsCnt(String[] usernameArray, Integer[] types) {
		String jpql = "select count(model) from UserLog model where model.id>0 ";
		if (usernameArray.length > 0) {
			jpql += " and model.username in ( ";
			for (String username : usernameArray) {
				jpql += "'" + username + "',";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		return userlogDAO.getCntByStatement(jpql);
	}

	public Integer getUserlogsCntBe4SomeDay(String username, String target,
			Integer[] types, String date) {
		String jpql = "select count(model) from UserLog model where model.id>0 and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username='" + username + "'";
		}
		if (StringUtils.isNotEmpty(target)) {
			jpql += " and model.target='" + target + "'";
		}
		if (StringUtils.isNotEmpty(date)) {
			jpql += " and model.updateTime>='" + date + "'";
		}
		if (types != null) {
			jpql += " and model.type in ( ";
			for (int type : types) {
				jpql += type + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += " )";
		}
		return userlogDAO.getCntByStatement(jpql);
	}

	public boolean isEditor(String username) {
		User u = this.getUser(null, username);
		if (u == null) {
			return false;
		}
		return u.getUserIsEditor() != null && u.getUserIsEditor() > 0;
	}

	/**
	 * 判断用户是否好友
	 * 
	 * @param username1
	 * @param username2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isFriend(String frdUserMe, String frdUserYou) {
		return getFriend(frdUserMe, frdUserYou) != null;
	}

	/**
	 * list the board managers
	 * 
	 * @param boardId
	 *            could be null
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Userboard> listBms(Integer boardId, Integer start, Integer limit) {
		String jpql = "select ub from User u, Userboard ub where ub.user_id=u.userId and ub.role>=2 and u.userOption >= "
				+ UserSecurityType.OPTION_LEVEL_MANAGE;
		if (boardId != null) {
			jpql += " and ub.board_id=" + boardId;
		}
		jpql += " order by u.userId desc";
		return userboardDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 增加用户积分
	 * 
	 * #852 (CTBA社区财富(CTB))
	 * 
	 * @param username
	 * @param score
	 */
	public void plusScore(String username, Integer score) {
		User u = this.getUser(null, username);
		u.setUserScore(score + u.getUserScore());
		this.saveUser(u, true);
	}

	/**
	 * 保存好友
	 * 
	 * @param model
	 * @param update
	 */
	public void saveFriend(Friend model, boolean update) {
		if (update) {
			friendDAO.update(model);
		} else {
			friendDAO.save(model);
		}

	}

	public MainUser saveMainUser(MainUser model) {
		if (model.getId() != null) {
			mainUserDAO.update(model);
		} else {
			mainUserDAO.save(model);
		}
		return getUser(model.getUsername());
	}

	public MainUser saveMainUser(MainUser model, boolean withKey) {
		mainUserDAO.save(model);
		return getUser(model.getUsername());
	}

	/**
	 * 新增（更新）消息
	 * 
	 * @param model
	 */
	public void saveMessage(Message model) {
		if (StringUtils.isEmpty(model.getMsgContent())) {
			log.error("set the message content plz!");
			return;
		}
		if (StringUtils.isEmpty(model.getMsgTitle())) {
			model.setMsgTitle(StringUtils.getShorterStr(model.getMsgContent(),
					30));
		}
		if (model.getMsgId() != null && model.getMsgId() > 0) {
			messageDAO.update(model);
		} else {
			messageDAO.save(model);
		}
	}

	/**
	 * save a user'data
	 * 
	 * @param model
	 * @param update
	 */
	public void saveUser(User model, boolean update) {
		if (update) {
			userDAO.update(model);
			userDAO.flushCache();
		} else {
			userDAO.save(model);
		}
	}

	public void saveUser(User model, boolean update, boolean withKey) {
		if (update) {
			userDAO.update(model);
		} else {
			userDAO.save(model);
		}
	}

	/**
	 * 触发用户事件
	 * 
	 * @param username
	 * @param target
	 * @param type
	 */
	public void trigeEvent(MainUser user, String target, UserEventType type) {
		UserLog log = new UserLog();
		log.setUsername(user.getUsername());
		log.setUpdateTime(DateUtils.getNow());
		log.setTarget(target);
		log.setType(type.getValue());

		if (user.getTimelineSetting() == null
				|| user.getTimelineSetting().intValue() == 0) {
			// 对外发布
			log.setHide(0);
		} else {
			log.setHide(1);
		}
		userlogDAO.save(log);
	}

	/**
	 * 触发用户通知
	 * 
	 * @param targetUsername
	 * @param source
	 * @param title
	 * @param refererURL
	 *            可以空
	 * @param type
	 */
	public void trigeNotice(String targetUsername, String source, String title,
			String refererURL, NoticeType type) {
		// 确认来源不是用户自己
		if (StringUtils.isNotEmpty(targetUsername)
				&& StringUtils.isNotEmpty(source)
				&& targetUsername.equals(source)) {
			log.warn("No need to notice him self");
			return;
		}
		Notice model = new Notice();
		model.setUsername(targetUsername);
		model.setUpdateTime(DateUtils.getNow());
		model.setTitle(title);
		model.setSource(source);
		if (type != null) {
			model.setType(type.getValue());
		}
		model.setRefererURL(refererURL);
		noticeDAO.save(model);
	}

}
