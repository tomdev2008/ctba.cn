/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.ctba;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.ShopCommend;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class ShopCommendDAOTest extends DAOTestBase {

	ShopCommendDAO instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				ShopCommendDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ShopCommend> result = instance.findAll();
		System.out.println(result.size());
	}
}