/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.core;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.core.UserlogDAO;
import org.net9.domain.model.core.UserLog;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class UserlogDAOTest extends DAOTestBase {

	UserlogDAO instance;

	@Before
	public void setUp() {
		instance = (UserlogDAO) Guice.createInjector(new ServiceModule())
				.getInstance(UserlogDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<UserLog> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<UserLog> list = instance.findAll();
		assertTrue(list.size() > 0);
		UserLog model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<UserLog> list = instance.findAll();
		assertTrue(list.size() > 0);
		UserLog model = new UserLog();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<UserLog> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<UserLog> list = instance.findAll();
		assertTrue(list.size() > 0);
		UserLog model = list.get(0);
		model.setTarget("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getTarget().equals(model.getTarget()));
	}
}