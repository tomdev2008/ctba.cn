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
import org.net9.domain.dao.group.GroupUserDAO;
import org.net9.domain.model.group.GroupUser;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class GroupUserDAOTest extends DAOTestBase {

	GroupUserDAO instance;

	@Before
	public void setUp() {
		instance = (GroupUserDAO) Guice.createInjector(new ServiceModule())
				.getInstance(GroupUserDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<GroupUser> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		GroupUser result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<GroupUser> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

	@Test
	public void testRemove() {
		List<GroupUser> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupUser model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<GroupUser> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupUser model = new GroupUser();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		model.setUsername("gladstone");
		this.instance.save(model);
		List<GroupUser> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<GroupUser> list = instance.findAll();
		assertTrue(list.size() > 0);
		GroupUser model = list.get(0);
		model.setRole(111);
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getRole().equals(model.getRole()));
	}

}