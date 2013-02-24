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
import org.net9.domain.model.blog.BlogLink;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class BlogLinkDAOTest extends DAOTestBase {

	BlogLinkDAO instance;

	@Before
	public void setUp() {
		instance = (BlogLinkDAO) Guice.createInjector(new ServiceModule())
				.getInstance(BlogLinkDAO.class);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<BlogLink> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindById() {
		System.out.println("findById");
		Integer id = 0;
		BlogLink result = instance.findById(id.intValue());
		assertEquals(null, result);

		List<BlogLink> list = instance.findAll();
		result = instance.findById(list.get(0).getId());
		assertTrue(result != null);

	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<BlogLink> list = instance.findAll();
		assertTrue(list.size() > 0);
		BlogLink model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<BlogLink> list = instance.findAll();
		assertTrue(list.size() > 0);
		BlogLink model = new BlogLink();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<BlogLink> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<BlogLink> list = instance.findAll();
		assertTrue(list.size() > 0);
		BlogLink model = list.get(0);
		model.setImageUrl("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getImageUrl().equals(model.getImageUrl()));
	}

}