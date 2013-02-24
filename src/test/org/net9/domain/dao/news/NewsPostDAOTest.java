package org.net9.domain.dao.news;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.news.NewsPost;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class NewsPostDAOTest extends DAOTestBase {

	NewsPostDAO instance;

	@Before
	public void setUp() {
		instance = (NewsPostDAO) Guice.createInjector(new ServiceModule())
				.getInstance(NewsPostDAO.class);
	}

	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<NewsPost> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@Test
	public void testRemove() {
		List<NewsPost> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsPost model = list.get(0);
		this.instance.remove(model);
	}

	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<NewsPost> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsPost model = new NewsPost();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<NewsPost> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@Test
	public void testUpdate() {
		List<NewsPost> list = instance.findAll();
		assertTrue(list.size() > 0);
		NewsPost model = list.get(0);
		model.setContent("test_urltest_urltest_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getContent().equals(model.getContent()));
	}

}