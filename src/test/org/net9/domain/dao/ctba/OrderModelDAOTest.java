package org.net9.domain.dao.ctba;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.OrderModel;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class OrderModelDAOTest extends DAOTestBase {

	private OrderModelDAO instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				OrderModelDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<OrderModel> result = instance.findAll();
		System.out.println(result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<OrderModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		OrderModel model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		OrderModel model = new OrderModel();
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setDescription("setDescription");
		model.setId(null);
		model.setOwner("gladstone");
		model.setTitle("gladstone_title");
		model.setTransState(1);
		model.setPrice(1.00);
		model.setUsername("gladstone");
		model.setType(0);
		this.instance.save(model);
		List<OrderModel> newList = instance.findAll();
		assertTrue(newList.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<OrderModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		OrderModel model = list.get(0);
		model.setDescription("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getDescription().equals(model.getDescription()));
	}

}