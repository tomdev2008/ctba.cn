/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.group.GroupModel;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class GroupModelDAOTest extends DAOTestBase {

	GroupModelDAO instance;

	@Before
	public void setUp() {
		instance = (GroupModelDAO) Guice.createInjector(new ServiceModule())
				.getInstance(GroupModelDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<GroupModel> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		GroupModel result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<GroupModel> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<GroupModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupModel model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<GroupModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupModel model = new GroupModel();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<GroupModel> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<GroupModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupModel model = list.get(0);
		model.setDiscription("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getDiscription().equals(model.getDiscription()));
	}

}