package org.net9.domain.dao.core;


import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.core.Notice;

import com.google.inject.Guice;

public class NoticeDAOTest {


	NoticeDAO instance;

	@Before
	public void setUp() {
		instance = (NoticeDAO) Guice.createInjector(new ServiceModule())
				.getInstance(NoticeDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<Notice> result = instance.findAll();
		System.out.println(result.size());
//		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {

        Notice model = new Notice();
		model.setTitle("t");
		model.setExpired(1);
		model.setSource("gladstone");
		model.setType(1);
		model.setUsername("gladstone");
		model.setUpdateTime(StringUtils.getTimeStrByNow());
//		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);

		List<Notice> list = instance.findAll();
		assertTrue(list.size() > 0);
		model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<Notice> list = instance.findAll();
//		assertTrue(list.size() > 0);
		Notice model = new Notice();
		model.setTitle("t");
		model.setExpired(1);
		model.setSource("gladstone");
		model.setType(1);
		model.setUsername("gladstone");
		model.setUpdateTime(StringUtils.getTimeStrByNow());
//		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<Notice> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<Notice> list = instance.findAll();
		assertTrue(list.size() > 0);
		Notice model = list.get(0);
		model.setTitle("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getTitle().equals(model.getTitle()));
	}
}
