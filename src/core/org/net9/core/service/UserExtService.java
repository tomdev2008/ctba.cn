package org.net9.core.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.domain.dao.view.ViewTimelineFriendDAO;
import org.net9.domain.dao.view.ViewUserScoreDAO;
import org.net9.domain.model.core.Notice;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.core.UserNote;
import org.net9.domain.model.view.ViewTimelineFriend;

import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

/**
 * user extention services
 * 
 * @author gladstone
 * @since 2008-10-12
 */
@SessionScoped
public class UserExtService extends BaseService {

	private static final long serialVersionUID = -373987247360966733L;
	static Log log = LogFactory.getLog(UserExtService.class);

	@Inject
	ViewUserScoreDAO viewUserScoreDAO;

	/**
	 * delete an userNote
	 * 
	 * @param model
	 */
	public void deleteUserNote(UserNote model) {
		userNoteDAO.remove(model);
	}

	/**
	 * list userNotes of a user
	 * 
	 * @param userId
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findUserNotes(Integer userId, Integer type, Integer start,
			Integer limit) {
		String jpql = "select model from UserNote model  where model.id>0 ";
		jpql += " and model.user_id =" + userId;
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		jpql += " order by model.id desc ";
		return userNoteDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * selete a userNote
	 * 
	 * @param eid
	 * @return
	 */
	public UserNote getUserNote(Integer eid) {
		return (UserNote) userNoteDAO.getByPrimaryKey(UserNote.class, eid);
	}

	/**
	 * get the count of the notes
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public int getUserNoteCnt(Integer userId, Integer type) {
		String jpql = "select count(model) from  UserNote model where model.id>0 ";
		jpql += " and model.user_id =" + userId;
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		return userNoteDAO.getCntByStatement(jpql);
	}

	/**
	 * save an userNote
	 * 
	 * @param model
	 * @param update
	 */
	public void saveUserNote(UserNote model, boolean update) {
		if (update) {
			userNoteDAO.update(model);
		} else {
			userNoteDAO.save(model);
		}
	}

	/**
	 * 删除系统通知
	 * 
	 * @param model
	 */
	public void deleteNotice(Notice model) {
		noticeDAO.remove(model);
	}

	/**
	 * 得到系统通知列表
	 * 
	 * @param username
	 * @param expired
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findNotices(String username, Integer expired, Integer type,
			Integer start, Integer limit) {
		String jpql = "select model from Notice model  where model.id>0 ";
		jpql += " and model.username ='" + username + "'";
		if (expired != null) {
			jpql += " and model.expired =" + expired;
		}
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		jpql += " order by model.id desc ";
		return noticeDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 使得某个用户的通知过期
	 * 
	 * @param username
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	public void expireNotices(String username, Integer type) {
		String jpql = "update Notice model set model.expired=1 where model.id>0 ";
		jpql += " and model.username ='" + username + "'";
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		noticeDAO.exquteStatement(jpql);
		// List<Notice> list = this.findNotices(username, 0, type, 0,
		// BusinessConstants.MAX_INT);
		// for (Notice n : list) {
		// n.setExpired(1);
		// this.noticeDAO.update(n);
		// }
		noticeDAO.flushCache();
	}

	/**
	 * 得到一个通知
	 * 
	 * @param eid
	 * @return
	 */
	public Notice getNotice(Integer id) {
		return (Notice) this.noticeDAO.getByPrimaryKey(Notice.class, id);
	}

	/**
	 * 得到系统通知的数量
	 * 
	 * @param username
	 * @param expired
	 * @param type
	 * @return
	 */
	public int getNoticeCnt(String username, Integer expired, Integer type) {
		String jpql = "select count(model) from Notice model  where model.id>0 ";
		jpql += " and model.username ='" + username + "'";
		if (expired != null) {
			jpql += " and model.expired =" + expired;
		}
		if (type != null) {
			jpql += " and model.type =" + type;
		}
		return noticeDAO.getCntByStatement(jpql);
	}

	/**
	 * 保存通知
	 * 
	 * @param model
	 * @param update
	 */
	public void saveNotice(Notice model) {
		if (model.getId() != null && model.getId() > 0) {
			userNoteDAO.update(model);
		} else {
			userNoteDAO.save(model);
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserLog> findFriendsUserlogs(String username, String date,
			Integer start, Integer limit, Integer[] types) {
		// #804 (timeline 相关)
		// 通过委托方法来查询视图
		List<ViewTimelineFriend> timelineModelList = this.findViewTimeline(
				username, date, start, limit, types);
		return this.convertTimeline(timelineModelList);
	}

	/**
	 * 
	 * #804 (timeline 相关)
	 * 
	 * 视图的转换
	 * 
	 * @param timelineModelList
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<UserLog> convertTimeline(
			List<ViewTimelineFriend> timelineModelList) {
		List<UserLog> list = new ArrayList<UserLog>();
		for (ViewTimelineFriend model : timelineModelList) {
			UserLog l = new UserLog();
			l.setHide(model.getHide());
			l.setId(model.getId());
			l.setTarget(model.getTarget());
			l.setType(model.getType());
			l.setUpdateTime(model.getUpdateTime());
			l.setUsername(model.getUsername());
			list.add(l);
		}
		return list;
	}

	/**
	 * #804 (timeline 相关)
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
	private List<ViewTimelineFriend> findViewTimeline(String username,
			String date, Integer start, Integer limit, Integer[] types) {
		String jpql = "select model from ViewTimelineFriend model where model.id>0  and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.currUsername='" + username + "'";
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
		return viewTimelineFriendDAO.findByStatement(jpql, start, limit);
	}

	public Integer getFriendsUserlogsCnt(String username, String date,
			Integer[] types) {
		String jpql = "select count(model) from ViewTimelineFriend model where model.id>0  and model.hide=0";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.currUsername='" + username + "'";
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
		return viewTimelineFriendDAO.getCntByStatement(jpql);
	}

	@Inject
	ViewTimelineFriendDAO viewTimelineFriendDAO;

}
