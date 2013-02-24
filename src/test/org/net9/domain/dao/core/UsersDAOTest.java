/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.core.UsersDAO;
import org.net9.domain.model.core.User;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class UsersDAOTest extends DAOTestBase {

	UsersDAO instance;

	@Before
	public void setUp() {
		instance = (UsersDAO) Guice.createInjector(new ServiceModule())
				.getInstance(UsersDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<User> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	/**
	 * Test of getUser method, of class UsersDAO.
	 */
	@Test
	public void testGetUser() {
		System.out.println("getUser");
		String userName = "gladstone";
		User result = instance.getUser(userName);
		assertEquals("gladstone", result.getUserName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<User> list = instance.findAll();
		assertTrue(list.size() > 0);
		User model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<User> list = instance.findAll();
		assertTrue(list.size() > 0);
		User model = new User();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setUserId(null);
		this.instance.save(model);
		List<User> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<User> list = instance.findAll();
		assertTrue(list.size() > 0);
		User model = list.get(0);
		model.setUserFace("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getUserFace().equals(model.getUserFace()));
	}

}