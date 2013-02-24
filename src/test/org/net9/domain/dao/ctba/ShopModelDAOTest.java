package org.net9.domain.dao.ctba;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.ShopModel;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class ShopModelDAOTest extends DAOTestBase {

	ShopModelDAO instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				ShopModelDAO.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<ShopModel> result = instance.findAll();
		System.out.println(result.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<ShopModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		ShopModel model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		ShopModel model = new ShopModel();
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setDescription("setDescription");
		model.setHits(0);
		model.setId(null);
		model.setEquipmentCnt(0);
		model.setMainBiz("badminton");
		model.setUsername("gladstone");
		model.setName("natalia");
		model.setType(0);
		this.instance.save(model);
		List<ShopModel> newList = instance.findAll();
		assertTrue(newList.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<ShopModel> list = instance.findAll();
		assertTrue(list.size() > 0);
		ShopModel model = list.get(0);
		model.setDescription("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getDescription().equals(model.getDescription()));
	}

}