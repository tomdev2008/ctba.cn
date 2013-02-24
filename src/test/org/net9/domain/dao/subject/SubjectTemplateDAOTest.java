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
import org.net9.domain.model.subject.SubjectTemplate;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

public class SubjectTemplateDAOTest extends DAOTestBase {

	SubjectTemplateDAO instance;

	@Before
	public void setUp() {
		instance = (SubjectTemplateDAO) Guice.createInjector(
				new ServiceModule()).getInstance(SubjectTemplateDAO.class);
	}

	@After
	public void tearDown() {
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<SubjectTemplate> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<SubjectTemplate> list = instance.findAll();
		assertTrue(list.size() > 0);
		SubjectTemplate model = list.get(0);
		this.instance.remove(model);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<SubjectTemplate> list = instance.findAll();
		assertTrue(list.size() > 0);
		SubjectTemplate model = new SubjectTemplate();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		this.instance.save(model);

		List<SubjectTemplate> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<SubjectTemplate> list = instance.findAll();
		assertTrue(list.size() > 0);
		SubjectTemplate model = list.get(0);
		model.setContent("dummyContent" + StringUtils.getTimeStrByNow());
		model.setTitle("dummyTitle" + StringUtils.getTimeStrByNow());
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getContent().equals(model.getContent()));
		assertTrue(list.get(0).getTitle().equals(model.getTitle()));

	}
}
