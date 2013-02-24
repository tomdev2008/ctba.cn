package org.net9.bbs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.core.service.BaseService;
import org.net9.core.types.TopicType;
import org.net9.domain.dao.bbs.TopicsDAO;
import org.net9.domain.model.bbs.Topic;

import com.google.inject.servlet.SessionScoped;

/**
 * 版面文章服务
 * 
 * 
 * @author gladstone
 * @since 2007/11/09
 */
@SessionScoped
public class TopicService extends BaseService {

	private static final long serialVersionUID = 1L;

	/** Logger */
	static Log log = LogFactory.getLog(TopicService.class);

	public final static int TOPIC_STATE_INDEX_RE = 1;

	public final static int TOPIC_STATE_INDEX_REMIND = 2;

	public void dealPrimer(Integer topicId) {
		Topic topic = getTopic(topicId);
		int topicPrimer = topic.getTopicPrime() == 0 ? 1 : 0;
		topic.setTopicPrime(topicPrimer);
		saveTopic(topic, true);
	}

	public void dealRe(Integer topicId) {
		Topic topic = getTopic(topicId);
		int topicState = topic.getTopicState();
		String[] states = TopicsDAO.decodeTopicState(topicState);
		states[TOPIC_STATE_INDEX_RE] = CommonUtils
				.isEmpty(states[TOPIC_STATE_INDEX_RE]) ? "on" : "";
		topicState = TopicsDAO.encodeTopicState(states);
		topic.setTopicState(topicState);
		saveTopic(topic, true);
	}

	public void dealRemind(Integer topicId) {
		Topic topic = getTopic(topicId);
		int topicState = topic.getTopicState();
		String[] states = TopicsDAO.decodeTopicState(topicState);
		states[TOPIC_STATE_INDEX_REMIND] = CommonUtils
				.isEmpty(states[TOPIC_STATE_INDEX_REMIND]) ? "on" : "";
		topicState = TopicsDAO.encodeTopicState(states);
		topic.setTopicState(topicState);
		saveTopic(topic, true);
	}

	public void dealTop(Integer topicId) {
		Topic topic = getTopic(topicId);
		int topicTop = topic.getTopicTop() == 0 ? 1 : 0;
		topic.setTopicTop(topicTop);
		saveTopic(topic, true);
	}

	/**
	 * 删除文章
	 * 
	 * @param model
	 */
	public void deleteTopic(Topic model) {
		topicDAO.remove(model);
	}

	/**
	 * 版面热点文章
	 * 
	 * @param boardId
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findHotTopics(Integer boardId, Integer start, Integer limit) {
		String jpql = "select model from Topic model where model.topicBoardId="
				+ boardId;
		jpql += " order by model.topicHits desc ";
		return topicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 版面热点文章,有时间限制
	 * 
	 * @param boardId
	 * @param dayCnt
	 *            hot topic in dayCnt days
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> findHotTopicsByTime(Integer boardId, Integer dayCnt,
			Integer start, Integer limit) {
		String timeStr = CommonUtils.getDateFromTodayOn(0 - dayCnt);
		String jpql = "select model from Topic model where model.topicTime>='"
				+ timeStr + "'";
		if (boardId != null) {
			jpql += " and model.topicBoardId=" + boardId;
		}
		jpql += " order by model.topicHits desc ";
		return topicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 热贴，首页使用
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findHotTopicsForIndex() {
		return topicDAO.findByStatement(
				"select model from Topic model order by model.topicHits desc",
				0, 20);
	}

	/**
	 * 得到一定时间内的新帖
	 * 
	 * @param boardId
	 * @param dayCnt
	 *            几天之内， 0是今天
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> findNewTopicsByTime(Integer boardId, Integer dayCnt,
			Integer start, Integer limit) {
		String timeStr = CommonUtils.getDateFromTodayOn(0 - dayCnt);
		String jpql = "select model from Topic model where model.topicTime>='"
				+ timeStr + "' ";
		if (boardId != null) {
			jpql += " and model.topicBoardId=" + boardId;
		}
		jpql += " order by model.topicId desc ";
		return topicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * 新帖，首页使用
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> findNewTopicsForIndex(Integer start, Integer limit) {
		return topicDAO.findByStatement(
				"select model from Topic model order by model.topicId desc",
				start, limit);
	}

	/**
	 * get topics by a board
	 * 
	 * @param boardId
	 *            版面ID
	 * @param start
	 *            开始记录
	 * @param limit
	 *            查询长度
	 * @param type
	 *            类型,See Constant,如Constant.TOPIC_TYPE_NORMAL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> findTopics(Integer boardId, Integer start,
			Integer limit, Integer type) {
		String jpql = "select model from Topic model where model.topicUpdateTime is not null ";
		if (boardId != null) {
			jpql += " and model.topicBoardId=" + boardId;
		}
		if (type != null) {
			if (type.equals(TopicType.NORMAL.getValue())) {
				jpql += "   and model.topicTop=0  ";
			} else if (type.equals(TopicType.PRIME.getValue())) {
				jpql += "  and model.topicPrime=1 and model.topicTop=0  ";
			} else if (type.equals(TopicType.TOP.getValue())) {
				jpql += "   and model.topicTop=1  ";
			}
		}
		jpql += " order by model.topicUpdateTime desc ";
		return topicDAO.findByStatement(jpql, start, limit);
	}

	/**
	 * get topics acording to userId or username
	 * 
	 * @param userId
	 * @param username
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Topic> findTopicsByUser(String username, Integer start,
			Integer limit) {
		String jpql = "select model from Topic model where model.topicAuthor='"
				+ username + "' order by model.topicId desc";
		return topicDAO.findByStatement(jpql, start, limit);
	}

	public void flushTopicCache() {
		this.topicDAO.flushCache();
	}

	/**
	 * 
	 * 得到最新的文章
	 * 
	 * @param boardId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Topic getNewestTopic(Integer boardId, Integer type) {
		String jpql = null;
		if (type.equals(TopicType.NORMAL.getValue())) {
			jpql = "select model from Topic model where model.topicTop=0";
		} else if (type.equals(TopicType.PRIME.getValue())) {
			jpql = "select model from Topic model where model.topicPrime=1 ";
		} else if (type.equals(TopicType.TOP.getValue())) {
			jpql = "select model from Topic model where model.topicTop=1 ";
		}
		if (boardId != null) {
			jpql += " and model.topicBoardId=" + boardId;
		}
		jpql += " order by model.topicId desc ";
		List<Topic> tList = topicDAO.findByStatement(jpql, 0, 1);
		return tList.size() > 0 ? tList.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public Topic getNextTopic(Integer topicId, Integer bid) {
		String jpql = "select model from Topic model where model.topicId> "
				+ topicId;
		if (bid != null) {
			jpql += " and model.topicBoardId= " + bid;
		}
		jpql += "  order by model.topicId";
		List<Topic> tList = topicDAO.findByStatement(jpql, 0, 1);
		return tList.size() > 0 ? tList.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public Topic getPrevTopic(Integer topicId, Integer bid) {
		String jpql = "select model from Topic model where model.topicId< "
				+ topicId;
		if (bid != null) {
			jpql += " and model.topicBoardId= " + bid;
		}
		jpql += " order by model.topicId desc";
		List<Topic> tList = topicDAO.findByStatement(jpql, 0, 1);
		return tList.size() > 0 ? tList.get(0) : null;
	}

	/**
	 * get a topic model
	 * 
	 * @param topicId
	 *            可以为空,空的返回最新的文章
	 * @return
	 */
	public Topic getTopic(Integer topicId) {
		if (topicId == null) {
			return getNewestTopic(null, TopicType.NORMAL.getValue());
		}
		return (Topic) topicDAO.getByPrimaryKey(Topic.class, topicId);
	}

	/**
	 * 得到主题数量
	 * 
	 * @param boardId
	 * @param type
	 * @return
	 */
	public int getTopicCnt(Integer boardId, Integer type) {
		String jpql = "select count(model) from Topic model where model.topicId>0 ";
		if (type != null) {
			if (type.equals(TopicType.NORMAL.getValue())) {
				jpql += "   and model.topicTop=0  ";
			} else if (type.equals(TopicType.PRIME.getValue())) {
				jpql += "  and model.topicPrime=1 and model.topicTop=0  ";
			} else if (type.equals(TopicType.TOP.getValue())) {
				jpql += "  and model.topicTop=1 ";
			}
		}
		if (boardId != null) {
			jpql += " and model.topicBoardId=" + boardId;
		}
		return topicDAO.getCntByStatement(jpql);
	}

	/**
	 * get count of topics by a username
	 * 
	 * @param username
	 * @return
	 */
	public Integer getTopicsCntByUser(String username) {
		String jpql = "select count(model) from Topic model where model.topicAuthor='"
				+ username + "'";
		return topicDAO.getCntByStatement(jpql);
	}

	/**
	 * 得到文章的标识
	 * 
	 * @param topicId
	 * @return
	 */
	public String getTopicTagClass(Integer topicId) {
		Topic model = getTopic(topicId);
		// #366 改变 topic tag 的显示方式
		// 之前是返回一个图片，现在是定义一个css样式,前缀 tag_
		String reval = "tag_";
		if (model == null) {
			return reval;
		}
		String topicAttach = model.getTopicAttachName();
		String topicAttachPath = model.getTopicAttachPath();
		if (topicAttach != null && !topicAttach.equals("")
				&& topicAttachPath != null && !topicAttachPath.equals("")) {
			// reval += "<img src='" + root + tagMap.get("attach") + "'
			// border=0/>";
			reval += "attach";
			return reval;
		}
		int topicPrime = model.getTopicPrime();
		int topicTop = model.getTopicTop();
		int topicState = model.getTopicState();
		// 3. 帖子列表中，包含多个属性的帖子（如同时是精华和固顶）要选择一个图标显示吧？
		// 可以这样：如果帖子属性中包含“固顶”，那么选择固顶图标显示；
		// 如果包含“禁止回复”和“精华”，那么选择“禁止回复”图标显示；
		// 如果包含“通告”和“禁止回复”，那么选择“通告”图标显示
		// top> remind>nore>prime
		if (topicPrime == 1) {
			reval += "prime";
			return reval;
			// reval = "<img src='" + root + tagMap.get("prime") + "'
			// border=0>";
		}
		String[] state = TopicsDAO.decodeTopicState(topicState);
		if (state[1].equals("on")) {
			reval += "nore";
			return reval;
			// reval = "<img src='" + root + tagMap.get("nore") + "' border=0>";
		}
		if (state[2].equals("on")) {
			reval += "remind";
			return reval;
			// reval = "<img src='" + root + tagMap.get("remind") + "'
			// border=0>";
		}
		if (topicTop == 1) {
			reval += "top";
			return reval;
			// reval = "<img src='" + root + tagMap.get("top") + "' border=0>";
		}
		// if ("tag_".equals(reval)) {
		reval += "none";
		// reval += "<img src='" + root + tagMap.get("none") + "'
		// border=0>";
		// }
		return reval;
	}

	/**
	 * 保存文章
	 * 
	 * @param model
	 * @param update
	 */
	public void saveTopic(Topic model, boolean update) {
		if (update) {
			topicDAO.update(model);
		} else {
			topicDAO.save(model);
		}
	}

	/**
	 * for data transe
	 * 
	 * @param model
	 * @param update
	 * @param withKey
	 */
	public void saveTopic(Topic model, boolean update, boolean withKey) {
		if (update) {
			topicDAO.update(model);
		} else {
			topicDAO.save(model);
		}
	}

	public void updateTopicHit(Integer id, Integer hit) {
		topicDAO.exquteStatement("update Topic set topicHits=" + hit
				+ " where topicId=" + id);
	}

}
