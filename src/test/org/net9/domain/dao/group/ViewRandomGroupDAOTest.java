package org.net9.domain.dao.group;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.view.ViewRandomGroupDAO;
import org.net9.domain.model.view.ViewRandomGroup;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

public class ViewRandomGroupDAOTest extends DAOTestBase {

	ViewRandomGroupDAO viewRandomGroupDAO;

	@Before
	public void setUp() throws Exception {
		viewRandomGroupDAO = (ViewRandomGroupDAO) Guice.createInjector(
				new ServiceModule()).getInstance(ViewRandomGroupDAO.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() throws Exception {
		List<ViewRandomGroup> list = viewRandomGroupDAO
				.findByStatement("select model from ViewRandomGroup model");
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
	}
}
