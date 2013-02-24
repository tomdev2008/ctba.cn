package org.net9.bbs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.service.BaseService;
import org.net9.domain.model.bbs.Reply;

import com.google.inject.servlet.SessionScoped;

/**
 * Reply service
 * 
 * @author gladstone
 * 
 */
@SessionScoped
public class ReplyService extends BaseService {
	private static final long serialVersionUID = 1L;
	static Log log = LogFactory.getLog(ReplyService.class);

	/**
	 * get a Reply model
	 * 
	 * @param ReplyId
	 * @return
	 */
	public Reply getReply(Integer replyId) {
		return (Reply) replyDAO.getByPrimaryKey(Reply.class, replyId);
	}

	/**
	 * save a Reply
	 * 
	 * @param model
	 */
	public void saveReply(Reply model) {
		if (model.getTopicId() != null)
			replyDAO.update(model);
		else
			replyDAO.save(model);
	}

	/**
	 * for data transe
	 * 
	 * @param model
	 * @param withKey
	 */
	public void saveReply(Reply model, boolean withKey) {
		replyDAO.save(model);
	}

	/**
	 * delete a Reply
	 * 
	 * @param model
	 */
	public void delReply(Reply model) {
		replyDAO.remove(model);
	}

	/**
	 * get Replys by a topic
	 * 
	 * @param topicId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reply> findReplys(Integer topicId, Integer start, Integer limit) {
		return replyDAO.findByStatement(
				"select model from Reply model where model.topicOriginId="
						+ topicId + " order by model.topicId ", start, limit);
	}

	/**
	 * get count of re by a topic
	 * 
	 * @param topicId
	 * @return
	 */
	public Integer getRepliesCntByTopic(Integer topicId) {
		return replyDAO
				.getCntByStatement("select count(model) from Reply model where model.topicOriginId="
						+ topicId);
	}

	/**
	 * get the user's replies
	 * 
	 * @param userId
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reply> findRepliesByUser(String username, Integer start,
			Integer limit) {
		return replyDAO.findByStatement(
				"select model from Reply model where model.topicAuthor='"
						+ username + "' order by model.topicId desc", start,
				limit);
	}

	/**
	 * get count of re by a user
	 * 
	 * @param username
	 * @return
	 */
	public Integer getRepliesCntByUser(String username) {
		return replyDAO
				.getCntByStatement("select count(model) from Reply model where model.topicAuthor='"
						+ username + "'");
	}

	/**
	 * get replies cnt
	 * 
	 * @return
	 */
	public Integer getRepliesCnt() {
		return replyDAO
				.getCntByStatement("select count(model) from Reply model ");
	}

	/**
	 * get all replies
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reply> findAllReplys(Integer start, Integer limit) {
		return replyDAO.findByStatement(
				"select model from Reply model order by model.topicId desc ",
				start, limit);
	}

}
