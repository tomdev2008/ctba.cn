/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.news;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.news.NewsCategoryDAO;
import org.net9.domain.model.news.NewsCategory;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class NewsCategoryDAOTest extends DAOTestBase {

	NewsCategoryDAO instance;

	@Before
	public void setUp() {
		instance = (NewsCategoryDAO) Guice.createInjector(new ServiceModule())
				.getInstance(NewsCategoryDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<NewsCategory> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<NewsCategory> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsCategory model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<NewsCategory> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsCategory model = new NewsCategory();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<NewsCategory> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<NewsCategory> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsCategory model = list.get(0);
		model.setName("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getName().equals(model.getName()));
	}

}