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
import org.net9.domain.model.ctba.EquipmentScore;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EquipmentScoreDAOTest extends DAOTestBase {

	EquipmentScoreDAO instance;

	@Before
	public void setUp() {
		instance = (EquipmentScoreDAO) Guice
				.createInjector(new ServiceModule()).getInstance(
						EquipmentScoreDAO.class);
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<EquipmentScore> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		EquipmentScore result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<EquipmentScore> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testRemove() {
		List<EquipmentScore> list = instance.findAll();
		assertTrue(list.size() > 0);
		EquipmentScore model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<EquipmentScore> list = instance.findAll();
		assertTrue(list.size() > 0);
		EquipmentScore model = new EquipmentScore();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<EquipmentScore> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	
	@Test
	public void testUpdate() {
		List<EquipmentScore> list = instance.findAll();
		assertTrue(list.size() > 0);
		EquipmentScore model = list.get(0);
		model.setValue(10000);
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getValue().equals(model.getValue()));
	}

}