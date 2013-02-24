package org.net9.group.service;

import java.util.List;

import org.net9.common.util.CommonUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.model.group.GroupTopic;

/**
 * 小组话题服务
 * 
 * @author gladstone
 * 
 */
public class GroupTopicService extends BaseService {

	private static final long serialVersionUID = 8512417950317530862L;

	public static Integer TOPIC_LEVEL_DEFAULT = 0;

	private static final Integer TOPIC_TITLE_LEN = 16;

	/**
	 * delete topic
	 * 
	 * @param model
	 */
	public void delTopic(GroupTopic model) {
		groupTopicDAO.remove(model);
	}

	/**
	 * find new topics for user
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupTopic> findNewTopicsForUser(String username,
			boolean containReplies, Integer start, Integer limit) {
		String jpql = "select model from GroupUser gu,GroupTopic model where model.id>0  and gu.username='"
				+ username + "'  and model.groupModel.id=gu.groupModel.id";
		if (!containReplies) {
			jpql += " and model.parent<=0 ";
		}
		jpql += " order by model.id desc, gu.id desc ";
		return groupTopicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 小组话题列表
	 * 
	 * @param groupId
	 * @param content
	 * @param containReplies
	 * @param sortColumn
	 *            默认是 updateTime
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupTopic> findTopics(Integer groupId, Boolean isTop,
			boolean containReplies, String sortColumn, Integer start,
			Integer limit) {
		String jpql = "select model from GroupTopic model where model.id>0 ";
		if (isTop != null) {
			if (isTop) {
				jpql += " and model.topState = 1 ";
			} else {
				jpql += " and model.topState = 0 ";
			}
		}
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		if (!containReplies) {
			jpql += " and model.parent<=0 ";
		}
		if (StringUtils.isNotEmpty(sortColumn)) {
			jpql += " order by model." + sortColumn + " desc";
		} else {
			jpql += " order by model.updateTime desc";
		}
		return groupTopicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到最热小组话题，不包括置顶和回复
	 * 
	 * @param groupId
	 * @param dayCnt
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupTopic> findHotTopicsByTime(Integer groupId,
			Integer dayCnt, Integer start, Integer limit) {
		String timeStr = CommonUtils.getDateFromTodayOn(0 - dayCnt);
		String jpql = "select model from GroupTopic model where  model.topState=0 ";
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		jpql += " and model.updateTime>='" + timeStr
				+ "' order by model.hits desc ";
		return groupTopicDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<GroupTopic> findTopicsByGroupIds(Integer[] groupIds,
			boolean containReplies, String sortColumn, Integer start,
			Integer limit) {
		String jpql = "select model from GroupTopic model where model.id>0 ";
		if (groupIds != null && groupIds.length >= 1) {
			jpql += " and model.groupModel.id in (";
			for (Integer gid : groupIds) {
				jpql += gid + ",";
			}
			jpql = jpql.substring(0, jpql.length() - 1);
			jpql += ") ";
		}
		if (!containReplies) {
			jpql += " and model.parent<=0 ";
		}
		if (StringUtils.isNotEmpty(sortColumn)) {
			jpql += " order by model." + sortColumn + " desc";

		} else {
			jpql += " order by model.updateTime desc";
		}
		return groupTopicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 根据话题找回复
	 * 
	 * @param parentId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupTopic> findTopicsByParent(Integer parentId, Integer start,
			Integer limit) {
		String jpql = "select model from GroupTopic model where model.id>0 and model.parent="
				+ parentId;
		jpql += " order by model.id asc";
		return groupTopicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * find topics by user
	 * 
	 * @param groupId
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupTopic> findTopicsByUser(Integer groupId, String username,
			Integer start, Integer limit) {
		String jpql = "select model from GroupTopic model where model.id>0  and model.author='"
				+ username + "' ";
		jpql += " and model.parent<=0 ";
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		jpql += " order by model.id desc";
		return groupTopicDAO.findByStatement(jpql, start, limit);
	}

	public Integer getTopicsCntByUser(Integer groupId, String username) {
		String jpql = "select count(model) from GroupTopic model where model.id>0  and model.author='"
				+ username + "' ";
		jpql += " and model.parent<=0 ";
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		return groupTopicDAO.getCntByStatement(jpql);
	}

	public void flushTopicCache() {
		this.groupTopicDAO.flushCache();
	}

	/**
	 * 小组话题的数目
	 * 
	 * @param groupId
	 * @param content
	 * @param containReplies
	 * @return
	 */
	public Integer getGroupTopicsCnt(Integer groupId, Boolean isTop,
			boolean containReplies) {
		String jpql = "select count(model) from GroupTopic model where model.id>0 ";
		if (isTop != null) {
			if (isTop) {
				jpql += " and model.topState = 1 ";
			} else {
				jpql += " and model.topState = 0 ";
			}
		}
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		if (!containReplies) {
			jpql += " and model.parent<=0 ";
		}
		return groupTopicDAO.getCntByStatement(jpql);
	}

	/**
	 * 根据话题找回复的数目
	 * 
	 * @param parentId
	 * @return
	 */
	public Integer getGroupTopicsCntByParent(Integer parentId) {
		String jpql = "select count(model) from GroupTopic model where model.id>0 and model.parent="
				+ parentId;
		return groupTopicDAO.getCntByStatement(jpql);
	}

	/**
	 * get the newest topic of a group
	 * 
	 * 只包含主题（没有回复）
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupTopic getNewestGroupTopic(Integer groupId, boolean containReply) {
		String jpql = "select model from GroupTopic model where model.id>0 ";
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		if (!containReply) {
			jpql += " and model.parent<=0 ";
		}

		jpql += " order by model.id desc";
		GroupTopic reval = null;
		List l = groupTopicDAO.findByStatement(jpql, 0, 1);
		if (l == null || l.size() < 1) {
			return null;
		} else {
			reval = (GroupTopic) l.get(0);
		}

		return reval;
	}

	/**
	 * get a topic
	 * 
	 * @param id
	 * @return
	 */
	public GroupTopic getTopic(Integer id) {
		return (GroupTopic) groupTopicDAO.findById(id);
	}

	/**
	 * save topic
	 * 
	 * @param model
	 */
	public void saveTopic(GroupTopic model) {
		// 这里，如果话题没有标题的话，自动把前16个字符作为标题
		if (StringUtils.isEmpty(model.getTitle())) {
			model
					.setTitle(StringUtils.getShorterStr(StringUtils
							.getStrWithoutHtmlTag(model.getContent()),
							TOPIC_TITLE_LEN));
		}
		if (model.getId() == null) {
			groupTopicDAO.save(model);
		} else {
			groupTopicDAO.update(model);
		}
	}

	public void updateTopicHit(Integer id, Integer hit) {
		groupTopicDAO.exquteStatement("update GroupTopic set hits=" + hit
				+ " where id=" + id);
	}
}
