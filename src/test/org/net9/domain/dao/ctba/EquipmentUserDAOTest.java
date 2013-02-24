/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.ctba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.EquipmentUser;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EquipmentUserDAOTest extends DAOTestBase {

	EquipmentUserDAO instance;

	@Before
	public void setUp() {
		instance = (EquipmentUserDAO) Guice.createInjector(new ServiceModule())
				.getInstance(EquipmentUserDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<EquipmentUser> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		EquipmentUser result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<EquipmentUser> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testRemove() {
		List<EquipmentUser> list = instance.findAll();
		assertTrue(list.size() > 0);
		EquipmentUser model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<EquipmentUser> list = instance.findAll();
		assertTrue(list.size() > 0);
		EquipmentUser model = new EquipmentUser();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<EquipmentUser> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testUpdate() {
		List<EquipmentUser> list = instance.findAll();
		assertTrue(list.size() > 0);
		EquipmentUser model = list.get(0);
		model.setMemo("test_memo");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getMemo().equals(model.getMemo()));
	}

}