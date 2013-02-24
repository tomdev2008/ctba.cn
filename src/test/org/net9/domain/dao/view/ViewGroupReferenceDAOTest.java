package org.net9.domain.dao.view;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.view.ViewGroupReference;

import com.google.inject.Guice;

public class ViewGroupReferenceDAOTest {

	ViewGroupReferenceDAO instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				ViewGroupReferenceDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ViewGroupReference> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

}
