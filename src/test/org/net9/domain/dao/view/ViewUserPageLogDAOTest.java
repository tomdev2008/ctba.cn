package org.net9.domain.dao.view;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.view.ViewUserPageLogDAO;
import org.net9.domain.model.view.ViewUserPageLog;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

public class ViewUserPageLogDAOTest extends DAOTestBase {

	ViewUserPageLogDAO instance;

	@Before
	public void setUp() {
		instance = (ViewUserPageLogDAO) Guice.createInjector(
				new ServiceModule()).getInstance(ViewUserPageLogDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ViewUserPageLog> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

}
