package org.net9.domain.dao.subject;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.subject.Subject;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

public class SubjectDAOTest extends DAOTestBase {

	SubjectDAO instance;

	@Before
	public void setUp() {
		instance = (SubjectDAO) Guice.createInjector(new ServiceModule())
				.getInstance(SubjectDAO.class);
	}

	@After
	public void tearDown() {
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<Subject> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<Subject> list = instance.findAll();
		assertTrue(list.size() > 0);
		Subject model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<Subject> list = instance.findAll();
		assertTrue(list.size() > 0);
		Subject model = new Subject();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);

		List<Subject> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<Subject> list = instance.findAll();
		assertTrue(list.size() > 0);
		Subject model = list.get(0);
		model.setTitle("dummyTitle" + StringUtils.getTimeStrByNow());
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getTitle().equals(model.getTitle()));

	}
}
