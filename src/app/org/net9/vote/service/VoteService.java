package org.net9.vote.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.core.service.BaseService;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.ctba.VoteAnswer;
import org.net9.domain.model.ctba.VoteComment;

import com.google.inject.servlet.SessionScoped;

/**
 * 
 * 投票服务
 * 
 * @author gladstone
 * @since 2008/06/21
 */
@SessionScoped
public class VoteService extends BaseService {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(VoteService.class);

	/**
	 * 删除投票
	 * 
	 * @param model
	 */
	public void delVote(Vote model) {
		voteDAO.remove(model);
	}

	/**
	 * 删除投票答案
	 * 
	 * @param model
	 */
	public void delVoteAnswer(VoteAnswer model) {
		voteAnswerDAO.remove(model);
	}

	public void delVoteComment(VoteComment model) {
		voteCommentDAO.remove(model);
	}

	@SuppressWarnings("unchecked")
	public List<Vote> findHotVotes(Integer boardId, Integer type, String date,
			Integer start, Integer limit) {
		String jpql = "select model from Vote model" + " where model.id>0 ";
		if (boardId != null) {
			jpql += " and  model.boardId =" + boardId;
		}
		if (type != null) {
			jpql += " and  model.type =" + type;
		}
		if (CommonUtils.isNotEmpty(date)) {
			jpql += " and  model.overTime >='" + date + "' ";
		}

		jpql += " order by model.hits desc";

		return voteDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到过期的投票
	 * 
	 * @param dateStart
	 *            dateEnd，规定时间范围
	 * @param dateEnd
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Vote> findExpiredVotes(String dateStart, String dateEnd,
			Integer start, Integer limit) {
		String jpql = "select model from Vote model" + " where model.id>0 ";
		jpql += " and  model.overTime <'" + dateEnd + "' ";
		jpql += " and  model.overTime >='" + dateStart + "' ";
		jpql += " order by model.id desc";
		return voteDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 投票答案列表
	 * 
	 * @param voteId
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VoteAnswer> findVoteAnswers(Integer voteId, String username,
			Integer start, Integer limit) {
		String jpql = "select model from VoteAnswer model"
				+ " where model.voteId =" + voteId;
		if (CommonUtils.isNotEmpty(username)) {
			jpql += " and  model.username ='" + username + "' ";
		}

		jpql += " order by model.id desc";
		return voteAnswerDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public List<VoteComment> findVoteComments(Integer voteId, String username,
			Integer start, Integer limit) {
		String jpql = "select model from VoteComment model"
				+ " where model.vote.id =" + voteId;
		if (CommonUtils.isNotEmpty(username)) {
			jpql += " and  model.username ='" + username + "' ";
		}

		jpql += " order by model.id asc";
		return voteCommentDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 得到投票列表
	 * 
	 * @param boardId
	 *            could be null
	 * @param username
	 *            could be null
	 * @param type
	 *            could be null
	 * @param date
	 *            到当天为止还有效 could be null
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Vote> findVotes(Integer boardId, String username, Integer type,
			String date, Integer start, Integer limit) {

		String jpql = "select model from Vote model" + " where model.id>0 ";
		if (boardId != null) {
			jpql += " and  model.boardId =" + boardId;
		}
		if (CommonUtils.isNotEmpty(username)) {
			jpql += " and  model.username ='" + username + "' ";
		}
		if (type != null) {
			jpql += " and  model.type =" + type;
		}
		if (CommonUtils.isNotEmpty(date)) {
			jpql += " and  model.overTime >='" + date + "' ";
		}

		jpql += " order by model.id desc";

		return voteDAO.findByStatement(jpql, start, limit);
	}

	@SuppressWarnings("unchecked")
	public Vote getLastVote(Integer boardId) {
		String jpql = "select model from Vote model ";
		if (boardId != null) {
			jpql += " where model.boardId =" + boardId;
		}
		jpql += " order by model.id desc ";
		List<Vote> list = voteDAO.findByStatement(jpql, 0, 1);
		log.debug("last: " + list.size());
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 根据ID得到投票
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Vote getVote(Integer id) {
		String jpql = "select model from Vote model" + " where model.id =" + id;
		List<Vote> list = voteDAO.findByStatement(jpql, 0, 1);
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 根据ID得到答案
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VoteAnswer getVoteAnswer(Integer id) {
		String jpql = "select model from VoteAnswer model"
				+ " where model.id =" + id;
		List<VoteAnswer> list = voteAnswerDAO.findByStatement(jpql, 0, 1);
		return list.size() == 0 ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	public VoteComment getVoteComment(Integer id) {
		String jpql = "select model from VoteComment model"
				+ " where model.id =" + id;
		List<VoteComment> list = voteDAO.findByStatement(jpql, 0, 1);
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 得到投票答案数
	 * 
	 * @param voteId
	 *            投票ID
	 * @param username
	 *            投票者
	 * @return
	 */
	public Integer getVotesAnswerCnt(Integer voteId, String username) {
		String jpql = "select count(model) from VoteAnswer model"
				+ " where  model.voteId =" + voteId;
		if (CommonUtils.isNotEmpty(username)) {
			jpql += " and  model.username ='" + username + "' ";
		}
		return voteAnswerDAO.getCntByStatement(jpql);
	}

	public Integer getVotesAnswerCntByUsername(String username) {
		String jpql = "select count(model) from VoteAnswer model"
				+ " where model.username ='" + username + "' ";
		return voteAnswerDAO.getCntByStatement(jpql);
	}

	/**
	 * 投票数目
	 * 
	 * @param boardId
	 *            版面ID
	 * @param username
	 *            创建者
	 * @param type
	 *            类型
	 * @return
	 */
	public Integer getVotesCnt(Integer boardId, String username, Integer type) {
		String jpql = "select count(model) from Vote model"
				+ " where model.id>0 ";
		if (boardId != null) {
			jpql += " and  model.boardId =" + boardId;
		}
		if (CommonUtils.isNotEmpty(username)) {
			jpql += " and  model.username ='" + username + "' ";
		}
		if (type != null) {
			jpql += " and  model.type =" + type;
		}
		return voteDAO.getCntByStatement(jpql);
	}

	public Integer getVotesCommentCnt(Integer voteId, String username) {
		String jpql = "select count(model) from VoteComment model"
				+ " where  model.vote.id =" + voteId;
		if (CommonUtils.isNotEmpty(username)) {
			jpql += " and  model.username ='" + username + "' ";
		}
		return voteCommentDAO.getCntByStatement(jpql);
	}

	/**
	 * 保存投票
	 * 
	 * @param model
	 * @param update
	 */
	public void saveVote(Vote model) {
		if (model.getId() != null) {
			voteDAO.update(model);
		} else {
			voteDAO.save(model);
		}
	}

	/**
	 * 保存答案
	 * 
	 * @param model
	 */
	public void saveVoteAnswer(VoteAnswer model) {
		if (model.getId() != null) {
			voteAnswerDAO.update(model);
		} else {
			voteAnswerDAO.save(model);
		}
	}

	public void saveVoteComment(VoteComment model) {
		if (model.getId() != null) {
			voteCommentDAO.update(model);
		} else {
			voteCommentDAO.save(model);
		}
	}
}
