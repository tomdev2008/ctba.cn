/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.bbs.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.core.types.TopicType;
import org.net9.domain.model.bbs.Reply;
import org.net9.domain.model.bbs.Topic;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class ReplyServiceTest extends TestBase {

	ReplyService instance;

	TopicService topicService;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				ReplyService.class);
		topicService = Guice.createInjector(new ServiceModule()).getInstance(
						TopicService.class);
	}

	/**
	 * Test of delReply method, of class ReplyService.
	 */
	@Test
	public void testDelReply() {
		System.out.println("delReply");
		List<Reply> modelList = instance.findAllReplys(0, 1);
		Assert.assertTrue(modelList.size()>0);
		instance.delReply(modelList.get(0));

	}

	/**
	 * Test of findAllReplys method, of class ReplyService.
	 */
	@Test
	public void testFindAllReplys() {
		System.out.println("findAllReplys");
		Integer start = 0;
		Integer limit = 12;
		List<Reply> result = instance.findAllReplys(start, limit);
System.out.println(result);
	}

	/**
	 * Test of findRepliesByUser method, of class ReplyService.
	 */
	@Test
	public void testFindRepliesByUser() {
		System.out.println("findRepliesByUser");
		String username = "gladstone";
		Integer start = 0;
		Integer limit = 12;
		List<Reply> result = instance.findRepliesByUser(username, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of findReplys method, of class ReplyService.
	 */
	@Test
	public void testFindReplys() {
		System.out.println("findReplys");
		Integer topicId = 100;
		Integer start = 0;
		Integer limit = 12;
		List<Reply> result = instance.findReplys(topicId, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of getRepliesCnt method, of class ReplyService.
	 */
	@Test
	public void testGetRepliesCnt() {
		System.out.println("getRepliesCnt");
		Integer result = instance.getRepliesCnt();
		System.out.println(result);
	}

	/**
	 * Test of getRepliesCntByTopic method, of class ReplyService.
	 */
	@Test
	public void testGetRepliesCntByTopic() {
		System.out.println("getRepliesCntByTopic");
		Integer topicId = 12;
		Integer result = instance.getRepliesCntByTopic(topicId);
		System.out.println(result);
	}

	/**
	 * Test of getRepliesCntByUser method, of class ReplyService.
	 */
	@Test
	public void testGetRepliesCntByUser() {
		System.out.println("getRepliesCntByUser");
		String username = "gladstone";
		Integer result = instance.getRepliesCntByUser(username);
		System.out.println(result);
	}

	/**
	 * Test of getReply method, of class ReplyService.
	 */
	@Test
	public void testGetReply() {
		System.out.println("getReply");
		Integer replyId = 1000;
		Reply result = instance.getReply(replyId);
		System.out.println(result);
	}

	/**
	 * Test of saveReply method, of class ReplyService.
	 */
	@Test
	public void testSaveReply() {
		System.out.println("saveReply");
		
		Topic t = this.topicService.getNewestTopic(null, TopicType.NORMAL.getValue());
		Assert.assertTrue(t!=null);
		Reply model = new Reply();
		
		model.setTopicAuthor("gladstone");
		model.setTopicBadHits(0);
		model.setTopicBoardId(5);
		model.setTopicContent("testContent");
		model.setTopicTitle("gladstone");
		model.setTopicTime("2009-00-00 00:00:00");
		model.setTopicOriginId(t.getTopicId());
		model.setTopicHits(0);
		model.setTopicGoodHits(0);
		model.setTopicIP("127.0.0.1");
		instance.saveReply(model);

	}


}