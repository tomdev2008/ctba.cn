/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.ctba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.EquipmentComment;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EquipmentCommentDAOTest extends DAOTestBase {

	EquipmentCommentDAO instance;

	@Before
	public void setUp() {
		instance = (EquipmentCommentDAO) Guice.createInjector(
				new ServiceModule()).getInstance(EquipmentCommentDAO.class);
	}

	@After
	public void tearDown() {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<EquipmentComment> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		EquipmentComment result = instance.findById(id);
		assertEquals(null, result);

		List<EquipmentComment> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

}