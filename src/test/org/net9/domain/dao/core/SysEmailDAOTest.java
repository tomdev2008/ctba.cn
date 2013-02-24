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
import org.net9.domain.dao.core.SysEmailDAO;
import org.net9.domain.model.core.SysEmail;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class SysEmailDAOTest extends DAOTestBase {

	SysEmailDAO instance;

	@Before
	public void setUp() {
		instance = (SysEmailDAO) Guice.createInjector(new ServiceModule())
				.getInstance(SysEmailDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<SysEmail> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<SysEmail> list = instance.findAll();
		assertTrue(list.size() > 0);
		SysEmail model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<SysEmail> list = instance.findAll();
		assertTrue(list.size() > 0);
		SysEmail model = new SysEmail();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<SysEmail> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<SysEmail> list = instance.findAll();
		assertTrue(list.size() > 0);
		SysEmail model = list.get(0);
		model.setCc("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getCc().equals(model.getCc()));
	}

}