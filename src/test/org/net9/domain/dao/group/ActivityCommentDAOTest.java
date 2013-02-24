/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.group.ActivityComment;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class ActivityCommentDAOTest extends DAOTestBase {

	ActivityCommentDAO instance;

	@Before
	public void setUp() {
		instance = (ActivityCommentDAO) Guice.createInjector(
				new ServiceModule()).getInstance(ActivityCommentDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ActivityComment> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		ActivityComment result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<ActivityComment> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}

	@Test
	public void testRemove() {
		List<ActivityComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		ActivityComment model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testUpdate() {
		List<ActivityComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		ActivityComment model = list.get(0);
		model.setBody("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getBody().equals(model.getBody()));
	}

}