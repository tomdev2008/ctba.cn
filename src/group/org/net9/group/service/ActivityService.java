package org.net9.group.service;

import java.util.List;

import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.model.group.ActivityComment;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.ActivityUser;

/**
 * group activity service
 * 
 * @author gladstone
 * 
 */
public class ActivityService extends BaseService {

	private static final long serialVersionUID = 9076602864005546576L;

	/**
	 * delete ActivityModel
	 * 
	 * @param model
	 */
	public void delActivity(ActivityModel model) {
		activityModelDAO.remove(model);
	}

	/**
	 * 删除活动评论
	 * 
	 * @param model
	 */
	public void delActivityComment(ActivityComment model) {
		activityCommentDAO.remove(model);
	}

	/**
	 * delete ActivityUser
	 * 
	 * @param model
	 */
	public void delActivityUser(ActivityUser model) {
		activityUserDAO.remove(model);
	}

	/**
	 * get a list of ActivityModel
	 * 
	 * @param groupId
	 * @param title
	 * @param username
	 * @param type
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityModel> findActivities(Integer groupId, String title,
			String username, Integer type, Integer start, Integer limit) {
		String jpql = "select model from ActivityModel model where model.id>0 ";
		if (StringUtils.isNotEmpty(title)) {
			jpql += " and model.title like '%" + title + "%'";
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		jpql += " order by model.id desc";
		return activityModelDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到已经过期的活动
	 * 
	 * @param checkTimeStart
	 *            和checkTimeEnd配对，规定时间范围
	 * @param checkTimeEnd
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityModel> findExpiredActivities(String checkTimeStart,
			String checkTimeEnd, Integer start, Integer limit) {
		String jpql = "select model from ActivityModel model where model.id>0 ";
		jpql += "and model.endTime<'" + checkTimeEnd + "' and model.endTime>='"
				+ checkTimeStart + "' order by model.id desc";
		return activityModelDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<ActivityComment> findActivityComments(Integer activityId,
			String username, Integer start, Integer limit) {
		String jpql = "select model from ActivityComment model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.author ='" + username + "'";
		}
		if (activityId != null) {
			jpql += " and model.activity.id=" + activityId;
		}
		// jpql += " order by model.id desc";
		return activityCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * get a list of ActivityUser
	 * 
	 * @param atcivityId
	 * @param username
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityUser> findActivityUsers(Integer atcivityId,
			String username, Integer[] roles, Integer start, Integer limit) {
		String jpql = "select model from ActivityUser model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (atcivityId != null) {
			jpql += " and model.activityModel.id=" + atcivityId;
		}
		if (roles.length > 0) {
			jpql += " and model.role in (";
			for (Integer role : roles) {
				jpql += role + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += ")";
		}
		jpql += " order by model.id desc";
		return activityUserDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * get the count of ActivityModels
	 * 
	 * @param groupId
	 * @param title
	 * @param username
	 * @param type
	 * @return
	 */
	public Integer getActivitiesCnt(Integer groupId, String title,
			String username, Integer type) {
		String jpql = "select count( model) from ActivityModel model where model.id>0 ";
		if (StringUtils.isNotEmpty(title)) {
			jpql += " and model.title like '%" + title + "%'";
		}
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		return activityModelDAO.getCntByStatement(jpql);
	}

	/**
	 * get a ActivityModel
	 * 
	 * @param id
	 *            如果为null,返回最新的一个
	 * @return
	 */
	public ActivityModel getActivity(Integer id) {
		if (id == null) {
			// 返回最新的一个
			List<ActivityModel> aList = findActivities(null, null, null, null,
					0, 1);
			if (aList.size() > 0) {
				return aList.get(0);
			}
		}
		return (ActivityModel) activityModelDAO.findById(id);
	}

	public ActivityComment getActivityComment(Integer id) {
		return (ActivityComment) activityCommentDAO.findById(id);
	}

	public Integer getActivityCommentsCnt(Integer activityId, String username) {
		String jpql = "select count( model) from ActivityComment model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.author ='" + username + "'";
		}
		if (activityId != null) {
			jpql += " and model.activity.id=" + activityId;
		}
		return activityCommentDAO.getCntByStatement(jpql);
	}

	/**
	 * get a ActivityUser
	 * 
	 * @param id
	 * @return
	 */
	public ActivityUser getActivityUser(Integer id) {
		return (ActivityUser) activityUserDAO.findById(id);
	}

	public ActivityUser getActivityUser(Integer atcivityId, String username,
			Integer role) {
		List<ActivityUser> list = findActivityUsers(atcivityId, username,
				new Integer[] { role }, 0, 1);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * get the count of ActivityUsers
	 * 
	 * #883 (活动改进，报名增加人员限制和外挂)
	 * 
	 * @param atcivityId
	 * @param username
	 * @param role
	 * @return
	 */
	public Integer getActivityUsersCnt(Integer atcivityId, String username,
			Integer[] roles) {
		String jpql = "select sum(model.joinCnt) from ActivityUser model where model.id>0 ";
		if (StringUtils.isNotEmpty(username)) {
			jpql += " and model.username ='" + username + "'";
		}
		if (atcivityId != null) {
			jpql += " and model.activityModel.id=" + atcivityId;
		}
		if (roles.length > 0) {
			jpql += " and  model.role in (";
			for (Integer role : roles) {
				jpql += role + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += ")";
		}
		return activityModelDAO.getCntByStatement(jpql);
	}

	/**
	 * save activity
	 * 
	 * @param model
	 */
	public void saveActivity(ActivityModel model) {
		if (model.getId() == null) {
			activityModelDAO.save(model);
		} else {
			activityModelDAO.update(model);
		}
	}

	/**
	 * 保存活动评论
	 * 
	 * @param model
	 */
	public void saveActivityComment(ActivityComment model) {
		if (model.getId() == null) {
			activityCommentDAO.save(model);
		} else {
			activityCommentDAO.update(model);
		}
	}

	/**
	 * save ActivityUser
	 * 
	 * @param model
	 */
	public void saveActivityUser(ActivityUser model) {
		if (model.getId() == null) {
			activityUserDAO.save(model);
		} else {
			activityUserDAO.update(model);
		}
	}
}
