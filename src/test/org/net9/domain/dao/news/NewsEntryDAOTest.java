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
import org.net9.domain.dao.news.NewsEntryDAO;
import org.net9.domain.model.news.NewsEntry;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class NewsEntryDAOTest extends DAOTestBase {

	NewsEntryDAO instance;

	@Before
	public void setUp() {
		instance = (NewsEntryDAO) Guice.createInjector(new ServiceModule())
				.getInstance(NewsEntryDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<NewsEntry> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<NewsEntry> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsEntry model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<NewsEntry> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsEntry model = new NewsEntry();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<NewsEntry> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<NewsEntry> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsEntry model = list.get(0);
		model.setContent("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getContent().equals(model.getContent()));
	}

}