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
import org.net9.domain.dao.group.ActivityModelDAO;
import org.net9.domain.model.group.ActivityModel;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class ActivityModelDAOTest extends DAOTestBase {

	ActivityModelDAO instance;

	@Before
	public void setUp() {
		instance = (ActivityModelDAO) Guice.createInjector(new ServiceModule())
				.getInstance(ActivityModelDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ActivityModel> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		ActivityModel result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<ActivityModel> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

	@Test
	public void testRemove() {
		List<ActivityModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		ActivityModel model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<ActivityModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		ActivityModel model = new ActivityModel();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<ActivityModel> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<ActivityModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		ActivityModel model = list.get(0);
		model.setContent("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getContent().equals(model.getContent()));
	}

}