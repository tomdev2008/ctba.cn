package cn.ctba.equipment.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.Equipment;
import org.net9.test.TestBase;

import cn.ctba.equipment.EquipmentStateType;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EquipmentServiceTest extends TestBase {

	EquipmentService instance;

	@Before
	public void setUp() {
		instance = (EquipmentService) Guice.createInjector(new ServiceModule())
				.getInstance(EquipmentService.class);
	}

	/**
	 * Test of deleteGoodsWare method, of class GoodsService.
	 */
	@Test
	public void testDeleteGoodsWare() {
		System.out.println("deleteGoodsWare");
		List<Equipment> list = instance.findEquipments(EquipmentStateType.OK,
				null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Equipment model = list.get(0);
		instance.deleteEquipment(model);
	}

	/**
	 * Test of findGoodsWares method, of class GoodsService.
	 */
	@Test
	public void testFindGoodsWares() {
		System.out.println("findGoodsWares");
		String name = "";
		String username = "";
		Integer type = null;
		Integer start = 0;
		Integer limit = 12;

		List<Equipment> result = instance.findEquipments(EquipmentStateType.OK,
				name, username, type, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of findGoodsWaresByHits method, of class GoodsService.
	 */
	@Test
	public void testFindGoodsWaresByHits() {
		System.out.println("findGoodsWaresByHits");
		Integer type = null;
		Integer start = 0;
		Integer limit = 12;
		List<Equipment> result = instance.findEquipmentsByHits(
				EquipmentStateType.OK, null, type, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of getGoodsWare method, of class GoodsService.
	 */
	@Test
	public void testGetGoodsWare() {
		System.out.println("getGoodsWare");
		Integer id = 12;
		Equipment result = instance.getEquipment(id);
		System.out.println(result);
	}

	/**
	 * Test of getGoodsWaresCnt method, of class GoodsService.
	 */
	@Test
	public void testGetGoodsWaresCnt() {
		System.out.println("getGoodsWaresCnt");
		String name = "";
		String username = "";
		Integer uid = null;
		Integer type = null;

		Integer result = instance.getEquipmentCnt(EquipmentStateType.OK, name,
				username, uid, type);
		System.out.println(result);
	}

	/**
	 * Test of getNextGoodsWare method, of class GoodsService.
	 */
	@Test
	public void testGetNextGoodsWare() {
		System.out.println("getNextGoodsWare");
		Integer wid = 23;
		Integer type = 0;
		Equipment result = instance.getNextEquipment(EquipmentStateType.OK,
				wid, "gladstone", type);
		System.out.println(result);
	}

	/**
	 * Test of saveGoodsWare method, of class GoodsService.
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSaveGoodsWare() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		System.out.println("saveGoodsWare");
		Equipment model = new Equipment();
		List<Equipment> list = instance.findEquipments(EquipmentStateType.OK,
				null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		PropertyUtils.copyProperties(model, list.get(0));
		model.setId(null);
		instance.saveEquipment(model);
	}

	@Test
	public void testFindRefEquipmentsByUsername()
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		System.out.println("testFindRefEquipmentsByUsername");
		Integer type = 1;
		Integer start = 0;
		Integer limit = 12;
		List<Equipment> result = instance.findRefEquipmentsByUsername(
				"gladstone", type, start, limit);
		System.out.println(result);
	}

}