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
import org.net9.domain.dao.core.MainCommendDAO;
import org.net9.domain.model.core.MainCommend;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class MainCommendDAOTest extends DAOTestBase {

	MainCommendDAO instance;

	@Before
	public void setUp() {
		instance = (MainCommendDAO) Guice.createInjector(new ServiceModule())
				.getInstance(MainCommendDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<MainCommend> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<MainCommend> list = instance.findAll();
		assertTrue(list.size() > 0);
		MainCommend model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<MainCommend> list = instance.findAll();
		assertTrue(list.size() > 0);
		MainCommend model = new MainCommend();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<MainCommend> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<MainCommend> list = instance.findAll();
		assertTrue(list.size() > 0);
		MainCommend model = list.get(0);
		model.setLabel("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getLabel().equals(model.getLabel()));
	}

}