package org.net9.bbs.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.core.types.TopicType;
import org.net9.domain.model.bbs.Topic;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class TopicServiceTest extends TestBase {

	TopicService instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				TopicService.class);
	}

	/**
	 * Test of dealPrimer method, of class TopicService.
	 */
	@Test
	public void testDealPrimer() {
		System.out.println("dealPrimer");
		Integer topicId = null;
		instance.dealPrimer(topicId);
	}

	/**
	 * Test of dealRe method, of class TopicService.
	 */
	@Test
	public void testDealRe() {
		System.out.println("dealRe");
		Integer topicId = null;
		instance.dealRe(topicId);

	}

	/**
	 * Test of dealRemind method, of class TopicService.
	 */
	@Test
	public void testDealRemind() {
		System.out.println("dealRemind");
		Integer topicId = null;
		instance.dealRemind(topicId);

	}

	/**
	 * Test of dealTop method, of class TopicService.
	 */
	@Test
	public void testDealTop() {
		System.out.println("dealTop");
		List<Topic> list = instance.findTopics(null, 0, 1, TopicType.NORMAL
				.getValue());
		Assert.assertTrue(list.size() > 0);
		Integer topicId = list.get(0).getTopicId();
		instance.dealTop(topicId);
	}

	/**
	 * Test of delTopic method, of class TopicService.
	 */
	@Test
	public void testDelTopic() {
		System.out.println("delTopic");
		List<Topic> list = instance.findTopics(null, 0, 1, TopicType.NORMAL
				.getValue());
		Assert.assertTrue(list.size() > 0);
		Topic model = list.get(0);
		instance.deleteTopic(model);

	}

	/**
	 * Test of findHotTopics method, of class TopicService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindHotTopics() {
		System.out.println("findHotTopics");
		Integer boardId = 5;
		Integer start = 0;
		Integer limit = 10;
		List result = instance.findHotTopics(boardId, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of findHotTopicsByTime method, of class TopicService.
	 */
	@Test
	public void testFindHotTopicsByTime() {
		System.out.println("findHotTopicsByTime");
		Integer boardId = 5;
		Integer dayCnt = 1;
		Integer start = 0;
		Integer limit = 10;
		List<Topic> result = instance.findHotTopicsByTime(boardId, dayCnt,
				start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findHotTopicsForIndex method, of class TopicService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindHotTopicsForIndex() {
		System.out.println("findHotTopicsForIndex");
		List result = instance.findHotTopicsForIndex();
		System.out.println(result);
	}

	/**
	 * Test of findNewTopicsByTime method, of class TopicService.
	 */
	@Test
	public void testFindNewTopicsByTime() {
		System.out.println("findNewTopicsByTime");
		Integer boardId = 5;
		Integer dayCnt = 2;
		Integer start = 0;
		Integer limit = 12;
		List<Topic> result = instance.findNewTopicsByTime(boardId, dayCnt,
				start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findNewTopicsForIndex method, of class TopicService.
	 */
	@Test
	public void testFindNewTopicsForIndex() {
		System.out.println("findNewTopicsForIndex");
		Integer start = 0;
		Integer limit = 11;
		List<Topic> result = instance.findNewTopicsForIndex(start, limit);
		System.out.println(result);
	}

	/**
	 * Test of findTopics method, of class TopicService.
	 */
	@Test
	public void testFindTopics() {
		System.out.println("findTopics");
		Integer boardId = null;
		Integer start = 0;
		Integer limit = 12;
		Integer type = null;
		List<Topic> result = instance.findTopics(boardId, start, limit, type);
		System.out.println(result);
	}

	/**
	 * Test of findTopicsByUser method, of class TopicService.
	 */
	@Test
	public void testFindTopicsByUser() {
		System.out.println("findTopicsByUser");
		String username = "";
		Integer start = 0;
		Integer limit = 10;

		List<Topic> result = instance.findTopicsByUser(username, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of getNewestTopic method, of class TopicService.
	 */
	@Test
	public void testGetNewestTopic() {
		System.out.println("getNewestTopic");
		Integer boardId = 5;
		Topic result = instance.getNewestTopic(boardId, TopicType.NORMAL
				.getValue());
		System.out.println(result);
	}

	/**
	 * Test of getNextTopic method, of class TopicService.
	 */
	@Test
	public void testGetNextTopic() {
		System.out.println("getNextTopic");
		Integer topicId = 12;
		Integer bid = 5;
		Topic result = instance.getNextTopic(topicId, bid);
		System.out.println(result);
	}

	/**
	 * Test of getPrevTopic method, of class TopicService.
	 */
	@Test
	public void testGetPrevTopic() {
		System.out.println("getPrevTopic");
		Integer topicId = 12;
		Integer bid = 5;
		Topic result = instance.getPrevTopic(topicId, bid);
		System.out.println(result);
	}

	/**
	 * Test of getTopic method, of class TopicService.
	 */
	@Test
	public void testGetTopic() {
		System.out.println("getTopic");
		Integer topicId = null;
		Topic result = instance.getTopic(topicId);
		System.out.println(result);
	}

	/**
	 * Test of getTopicCnt method, of class TopicService.
	 */
	@Test
	public void testGetTopicCnt() {
		System.out.println("getTopicCnt");
		Integer boardId = null;
		Integer type = null;
		int result = instance.getTopicCnt(boardId, type);
		System.out.println(result);
	}

	/**
	 * Test of getTopicsCntByUser method, of class TopicService.
	 */
	@Test
	public void testGetTopicsCntByUser() {
		System.out.println("getTopicsCntByUser");
		String username = "";
		Integer result = instance.getTopicsCntByUser(username);
		System.out.println(result);
	}

	/**
	 * Test of getTopicTagClass method, of class TopicService.
	 */
	@Test
	public void testGetTopicTagClass() {
		System.out.println("getTopicTagClass");
		List<Topic> list = instance.findTopics(null, 0, 1, TopicType.NORMAL
				.getValue());
		Assert.assertTrue(list.size() > 0);
		Topic model = list.get(0);
		Integer topicId = model.getTopicId();
		String result = instance.getTopicTagClass(topicId);
		System.out.println(result);

	}

	/**
	 * Test of saveTopic method, of class TopicService.
	 */
	@Test
	public void testSaveTopic_3args() {
		System.out.println("saveTopic");
		List<Topic> list = instance.findTopics(null, 0, 1, TopicType.NORMAL
				.getValue());
		Assert.assertTrue(list.size() > 0);
		Topic model = list.get(0);
		boolean update = true;
		boolean withKey = false;
		model.setTopicAttachName("dummy_attach");
		instance.saveTopic(model, update, withKey);

	}

	/**
	 * Test of saveTopic method, of class TopicService.
	 */
	@Test
	public void testSaveTopic_Topic_boolean() {
		System.out.println("saveTopic");
		List<Topic> list = instance.findTopics(null, 0, 1, TopicType.NORMAL
				.getValue());
		Assert.assertTrue(list.size() > 0);
		Topic model = list.get(0);
		model.setTopicHits(10000);
		boolean update = true;
		instance.saveTopic(model, update);

	}

	@Test
	public void testUpdateTopicHit() {
		System.out.println("testUpdateTopicHit");
		List<Topic> list = instance.findTopics(null, 0, 1, TopicType.NORMAL
				.getValue());
		Assert.assertTrue(list.size() > 0);
		Topic model = list.get(0);
		System.out.println("before:" + model.getTopicHits());
		instance.updateTopicHit(model.getTopicId(), model.getTopicHits() + 1);

		instance.flushTopicCache();
		list = instance.findTopics(null, 0, 1, TopicType.NORMAL.getValue());
		model = list.get(0);
		System.out.println("after:" + model.getTopicHits());

	}

}