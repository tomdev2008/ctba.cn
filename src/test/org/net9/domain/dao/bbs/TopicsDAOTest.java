/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.bbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.bbs.TopicsDAO;
import org.net9.domain.model.bbs.Topic;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class TopicsDAOTest extends DAOTestBase {

	TopicsDAO instance;

	@Before
	public void setUp() {
		instance = (TopicsDAO) Guice.createInjector(new ServiceModule())
				.getInstance(TopicsDAO.class);
	}

	/**
	 * Test of dealPrimer method, of class TopicsDAO.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDealPrimer() {
		System.out.println("dealPrimer");
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = list.get(0);
		instance.dealPrimer(model.getTopicId());
	}

	/**
	 * Test of dealRe method, of class TopicsDAO.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDealRe() {
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = list.get(0);
		instance.dealRe(model.getTopicId());
	}

	/**
	 * Test of dealRemind method, of class TopicsDAO.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDealRemind() {
		System.out.println("dealRemind");
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = list.get(0);
		instance.dealRemind(model.getTopicId());
	}

	/**
	 * Test of dealTop method, of class TopicsDAO.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDealTop() {
		System.out.println("dealTop");
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = list.get(0);
		instance.dealTop(model.getTopicId());
	}

	/**
	 * Test of decodeTopicState method, of class TopicsDAO.
	 */
	@Test
	public void testDecodeTopicState() {
		System.out.println("decodeTopicState");
		int topicState = 7;
		String[] expResult = { "on", "on", "on" };
		String[] result = TopicsDAO.decodeTopicState(topicState);
		for (String s : result) {
			System.out.println(s);
		}
		assertEquals(expResult, result);
	}

	/**
	 * Test of encodeTopicState method, of class TopicsDAO.
	 */
	@Test
	public void testEncodeTopicState() {
		System.out.println("encodeTopicState");
		String[] values = { "on", "on", "on" };
		int expResult = 7;
		int result = TopicsDAO.encodeTopicState(values);
		assertEquals(expResult, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<Topic> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = new Topic();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setTopicId(null);
		this.instance.save(model);
		List<Topic> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<Topic> list = instance.findAll();
		assertTrue(list.size() > 0);
		Topic model = list.get(0);
		model.setTopicAttachName("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getTopicAttachName().equals(
				model.getTopicAttachName()));
	}

}