package org.net9.domain.dao.bbs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.domain.dao.CachedJpaDAO;
import org.net9.domain.dao.annotation.EntityClass;
import org.net9.domain.model.bbs.Topic;

/**
 * topic DAO
 * 
 * @author gladstone
 * 
 */
@EntityClass(value = Topic.class)
@SuppressWarnings("unchecked")
public class TopicsDAO extends CachedJpaDAO {

	static Log log = LogFactory.getLog(TopicsDAO.class);

	/**
	 * @param topicState
	 * @return
	 */
	public static String[] decodeTopicState(int topicState) {
		String[] reval = new String[3];
		for (int i = 0; i <= 2; i++)
			reval[i] = "";
		int test = 0;
		test = topicState & 1;
		if (test != 0) {
			reval[0] = "on";
		}
		test = topicState & 2;
		if (test != 0) {
			reval[1] = "on";
		}
		test = topicState & 4;
		if (test != 0) {
			reval[2] = "on";
		}
		return reval;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public static int encodeTopicState(String[] values) {
		if (values.length < 3)
			return 0;
		String top10 = values[0];
		String show = values[1];
		String post = values[2];
		int topicState = 0;
		if (top10 != null && top10.equals("on"))
			topicState += 1;
		if (show != null && show.equals("on"))
			topicState += 2;
		if (post != null && post.equals("on"))
			topicState += 4;
		return topicState;
	}

	/**
	 * 切换精华
	 * 
	 * @param topicId
	 * @return
	 */
	public void dealPrimer(long topicId) {
		Topic topic = this.getTopic(topicId);
		int topicPrimer = topic.getTopicPrime();
		if (topicPrimer == 0) {
			topicPrimer = 1;
		} else {
			topicPrimer = 0;
		}
		topic.setTopicPrime(topicPrimer);
		update(topic);
	}

	/**
	 * 切换不可回复
	 * 
	 * @param topicId
	 * @return
	 */
	public void dealRe(long topicId) {
		Topic topic = this.getTopic(topicId);
		int topicState = topic.getTopicState();
		String[] states = decodeTopicState(topicState);
		if (states[1].equals("")) {
			states[1] = "on";
		} else {
			states[1] = "";
		}
		topicState = encodeTopicState(states);
		topic.setTopicState(topicState);
		update(topic);
	}

	/**
	 * 切换提醒
	 * 
	 * @param topicId
	 * @return
	 */
	public void dealRemind(long topicId) {
		Topic topic = this.getTopic(topicId);
		int topicState = topic.getTopicState();
		String[] states = decodeTopicState(topicState);
		if (states[2].equals("")) {
			states[2] = "on";
		} else {
			states[2] = "";
		}
		topicState = encodeTopicState(states);
		topic.setTopicState(topicState);
		update(topic);
	}

	/**
	 * 切换置顶
	 * 
	 * @param topicId
	 * @return
	 */
	public void dealTop(long topicId) {
		Topic topic = this.getTopic(topicId);
		int topicTop = topic.getTopicTop();
		if (topicTop == 0) {
			topicTop = 1;
		} else {
			topicTop = 0;
		}
		topic.setTopicTop(topicTop);
		update(topic);
	}

	/**
	 * seletct a topic
	 * 
	 * @param topicId
	 * @return
	 */
	private Topic getTopic(long topicId) {
		return (Topic) this.getByPrimaryKey(Topic.class, new Integer(
				(int) topicId));
	}

}
