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
import org.net9.domain.dao.core.MainPlaceDAO;
import org.net9.domain.model.core.MainPlace;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class MainPlaceDAOTest extends DAOTestBase {

	MainPlaceDAO instance;

	@Before
	public void setUp() {
		instance = (MainPlaceDAO) Guice.createInjector(new ServiceModule())
				.getInstance(MainPlaceDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<MainPlace> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<MainPlace> list = instance.findAll();
		assertTrue(list.size() > 0);
		MainPlace model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<MainPlace> list = instance.findAll();
		assertTrue(list.size() > 0);
		MainPlace model = new MainPlace();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<MainPlace> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<MainPlace> list = instance.findAll();
		assertTrue(list.size() > 0);
		MainPlace model = list.get(0);
		model.setDescription("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getDescription().equals(model.getDescription()));
	}

}