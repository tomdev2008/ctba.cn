package org.net9.domain.dao.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.group.GroupTopic;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class GroupTopicDAOTest extends DAOTestBase {

	GroupTopicDAO instance;

	@Before
	public void setUp() {
		instance = (GroupTopicDAO) Guice.createInjector(new ServiceModule())
				.getInstance(GroupTopicDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<GroupTopic> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		GroupTopic result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<GroupTopic> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<GroupTopic> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupTopic model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<GroupTopic> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupTopic model = new GroupTopic();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<GroupTopic> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<GroupTopic> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupTopic model = list.get(0);
		model.setAttachment("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getAttachment().equals(model.getAttachment()));
	}

}