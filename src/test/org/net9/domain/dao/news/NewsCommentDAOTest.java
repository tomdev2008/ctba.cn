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
import org.net9.domain.dao.news.NewsCommentDAO;
import org.net9.domain.model.news.NewsComment;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class NewsCommentDAOTest extends DAOTestBase {

	NewsCommentDAO instance;

	@Before
	public void setUp() {
		instance = (NewsCommentDAO) Guice.createInjector(new ServiceModule())
				.getInstance(NewsCommentDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<NewsComment> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<NewsComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsComment model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<NewsComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsComment model = new NewsComment();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<NewsComment> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<NewsComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsComment model = list.get(0);
		model.setContent("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getContent().equals(model.getContent()));
	}
}