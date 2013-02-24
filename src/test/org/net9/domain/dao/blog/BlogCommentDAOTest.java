/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.blog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.blog.BlogComment;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class BlogCommentDAOTest extends DAOTestBase {

	BlogCommentDAO instance;

	@Before
	public void setUp() {
		instance = (BlogCommentDAO) Guice.createInjector(new ServiceModule())
				.getInstance(BlogCommentDAO.class);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<BlogComment> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		BlogComment result = instance.findById((long) id.intValue());
		assertEquals(null, result);

		List<BlogComment> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<BlogComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		BlogComment model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<BlogComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		BlogComment model = new BlogComment();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<BlogComment> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<BlogComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		BlogComment model = list.get(0);
		model.setBody("testContent");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getBody().equals(model.getBody()));
	}

}