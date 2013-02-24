package org.net9.domain.dao.core;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.core.ShareCommentDAO;
import org.net9.domain.model.core.ShareComment;

import com.google.inject.Guice;

public class ShareCommentDAOTest {

	ShareCommentDAO instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				ShareCommentDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ShareComment> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<ShareComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		ShareComment model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<ShareComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		ShareComment model = new ShareComment();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);
		List<ShareComment> newList = instance.findAll();
		assertTrue(list.size() + 1 == newList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<ShareComment> list = instance.findAll();
		assertTrue(list.size() > 0);
		ShareComment model = list.get(0);
		model.setBody("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getBody().equals(model.getBody()));
	}

}
