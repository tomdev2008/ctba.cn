/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.ctba.equipment.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.domain.model.core.User;
import org.net9.domain.model.ctba.Equipment;
import org.net9.domain.model.ctba.EquipmentScore;
import org.net9.domain.model.ctba.EquipmentUser;
import org.net9.test.TestBase;

import cn.ctba.equipment.EquipmentStateType;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EquipmentUserServiceTest extends TestBase {

	EquipmentUserService instance;
	UserService userService;
	EquipmentService goodsService;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				EquipmentUserService.class);

		goodsService = Guice.createInjector(new ServiceModule()).getInstance(
				EquipmentService.class);
		userService = Guice.createInjector(new ServiceModule()).getInstance(
				UserService.class);
	}

	/**
	 * Test of deleteGoodsWareUser method, of class GoodsUserService.
	 */
	@Test
	public void testDeleteGoodsWareUser() {
		System.out.println("deleteGoodsWareUser");

		// prepare
		List<Equipment> list = this.goodsService.findEquipments(
				EquipmentStateType.OK, null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer wareId = list.get(0).getId();
		User u = this.userService.getUser(null, "gladstone");

		EquipmentUser model = new EquipmentUser();
		model.setUsername("gladstone");
		model.setUid(u.getUserId());
		model.setWareId(wareId);
		model.setMemo("memo");
		model.setType(0);
		model.setUpdateTime("2009-00-12");
		this.instance.saveEquipmentUser(model);
		model = this.instance.getEquipmentUser(wareId, u.getUserId());
		instance.deleteEquipmentUser(model);
	}

	/**
	 * Test of findGoodsWareScores method, of class GoodsUserService.
	 */
	@Test
	public void testFindGoodsWareScores() {
		System.out.println("findGoodsWareScores");
		Integer start = 0;
		Integer limit = 1;
		List<EquipmentScore> result = instance
				.findEquipmentScores(start, limit);
		System.out.println(result.size());

	}

	/**
	 * Test of getGoodsWareUser method, of class GoodsUserService.
	 */
	@Test
	public void testGetGoodsWareUser() {
		System.out.println("getGoodsWareUser");
		List<Equipment> list = this.goodsService.findEquipments(
				EquipmentStateType.OK, null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer wareId = list.get(0).getId();
		EquipmentUser result = instance.getEquipmentUser(wareId, 9);
		System.out.println(result);
	}

	/**
	 * Test of getScoreSum method, of class GoodsUserService.
	 */
	@Test
	public void testGetScoreSum() {
		System.out.println("getScoreSum");
		List<Equipment> list = this.goodsService.findEquipments(
				EquipmentStateType.OK, null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer wareId = list.get(0).getId();
		Double result = instance.getScoreSum(wareId);
		System.out.println(result);
	}

	/**
	 * Test of saveGoodsWareScore method, of class GoodsUserService.
	 */
	@Test
	public void testSaveGoodsWareScore() {
		System.out.println("saveGoodsWareScore");
		// prepare
		List<Equipment> list = this.goodsService.findEquipments(
				EquipmentStateType.OK, null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer wareId = list.get(0).getId();
		User u = this.userService.getUser(null, "gladstone");

		EquipmentScore model = new EquipmentScore();
		model.setCreateTime("2009-00-00");
		model.setUid(u.getUserId());
		model.setUsername("gladstone");
		model.setValue(0);
		model.setWareId(wareId);
		instance.saveEquipmentScore(model);
	}

	/**
	 * Test of saveGoodsWareUser method, of class GoodsUserService.
	 */
	@Test
	public void testSaveGoodsWareUser() {
		System.out.println("saveGoodsWareUser");

		// prepare
		List<Equipment> list = this.goodsService.findEquipments(
				EquipmentStateType.OK, null, null, null, 0, 1);
		Assert.assertTrue(list.size() > 0);
		Integer wareId = list.get(0).getId();
		User u = this.userService.getUser(null, "gladstone");
		EquipmentUser model = new EquipmentUser();
		model.setUsername("gladstone");
		model.setWareId(wareId);
		model.setMemo("memo");
		model.setType(0);
		model.setUid(u.getUserId());
		model.setUpdateTime("2009-00-12");
		instance.saveEquipmentUser(model);

	}

}