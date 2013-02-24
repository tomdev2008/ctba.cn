package org.net9.group.service;

import java.util.List;

import org.net9.core.service.BaseService;
import org.net9.domain.dao.group.GroupLinkDAO;
import org.net9.domain.dao.view.ViewGroupRankDAO;
import org.net9.domain.model.group.GroupLink;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.view.ViewGroupRank;

import com.google.inject.Inject;

/**
 * 小组扩展服务
 * 
 * @author gladstone
 * 
 */
public class GroupExtService extends BaseService {

	private static final long serialVersionUID = -2305729329791397468L;
	@Inject
	private GroupLinkDAO groupLinkDAO;
	@Inject
	private ViewGroupRankDAO viewGroupRankDAO;

	/**
	 * 删除友情链接
	 * 
	 * @param model
	 */
	public void delGroupLink(GroupLink model) {
		groupLinkDAO.remove(model);
	}

	/**
	 * 查询小组友情链接列表
	 * 
	 * @param groupId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findGroupLinks(Integer groupId, Integer start, Integer limit) {
		String jpql = "select model from GroupLink model where model.id>0 ";
		if (groupId != null) {
			jpql += " and model.groupModel.id=" + groupId;
		}
		jpql += " order by model.idx desc";
		return groupLinkDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 清空缓存
	 */
	public void flushGroupLinkCache() {
		groupLinkDAO.flushCache();
	}

	/**
	 * 根据id得到友情链接
	 * 
	 * @param id
	 * @return
	 */
	public GroupLink getGroupLink(Integer id) {
		return (GroupLink) groupLinkDAO.findById(id);
	}

	/**
	 * 得到小组排名
	 * 
	 * #818 (小组改进- 个人小组话题和小组排位)请添加说明。
	 * 
	 * @param groupId
	 * @return
	 */
	public Integer getGroupRank(Integer groupId) {
		ViewGroupRank viewGroupRank = (ViewGroupRank) this.viewGroupRankDAO
				.getByPrimaryKey(ViewGroupRank.class, groupId);
		String jpql = "select count(model.rankRate) from ViewGroupRank model where model.rankRate>"
				+ viewGroupRank.getRankRate();
		return viewGroupRankDAO.getCntByStatement(jpql) + 1;
	}

	@SuppressWarnings("unchecked")
	public List<ViewGroupRank> findGroupsByRank(Integer start, Integer limit) {
		String jpql = "select model from ViewGroupRank model order by model.rankRate desc";
		return viewGroupRankDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到当前最高索引
	 * 
	 * @param group
	 * @return
	 */
	public Integer getMaxGroupLinkIdx(GroupModel group) {
		String jpql = "select  max(model.idx)  from GroupLink model where model.groupModel.id="
				+ group.getId();
		return groupLinkDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到排名比自己高的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupLink getNextGroupLinkIdx(GroupLink model) {
		String jpql = "select  model  from GroupLink model where model.groupModel.id="
				+ model.getGroupModel().getId()
				+ " and model.idx>"
				+ model.getIdx() + " order by model.idx asc";
		List l = groupLinkDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (GroupLink) l.get(0) : null;
	}

	/**
	 * 得到排名比自己低的
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupLink getPrevGroupLinkIdx(GroupLink model) {
		String jpql = "select  model  from GroupLink model where model.groupModel.id="
				+ model.getGroupModel().getId()
				+ " and model.idx<"
				+ model.getIdx() + " order by model.idx desc";
		List l = groupLinkDAO.findByStatement(jpql, 0, 1);
		return l.size() > 0 ? (GroupLink) l.get(0) : null;
	}

	/**
	 * 保存友情链接
	 * 
	 * @param model
	 */
	public void saveLink(GroupLink model) {
		if (model.getId() == null) {
			groupLinkDAO.save(model);
		} else {
			groupLinkDAO.update(model);
		}
	}

}
