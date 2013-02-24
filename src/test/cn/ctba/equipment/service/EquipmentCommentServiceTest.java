/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.ctba.equipment.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.EquipmentComment;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EquipmentCommentServiceTest extends TestBase {

	EquipmentCommentService instance;

	@Before
	public void setUp() {
		instance = (EquipmentCommentService) Guice.createInjector(
				new ServiceModule()).getInstance(EquipmentCommentService.class);
	}

	/**
	 * Test of deleteGoodsComment method, of class GoodsCommentService.
	 */
	@Test
	public void testDeleteGoodsComment() {
		System.out.println("deleteGoodsComment");
		List<EquipmentComment> list = instance.findEquipmentComment(null,  null,
				0, 1);
		instance.deleteGoodsComment(list.get(0));
	}

	/**
	 * Test of findGoodsComment method, of class GoodsCommentService.
	 */
	@Test
	public void testFindGoodsComment() {
		System.out.println("findGoodsComment");
		List<EquipmentComment> list = instance.findEquipmentComment(null,  null,
				0, 1);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
	}

	/**
	 * Test of getGoodsComment method, of class GoodsCommentService.
	 */
	@Test
	public void testGetGoodsComment() {
		System.out.println("getGoodsComment");
		List<EquipmentComment> list = instance.findEquipmentComment(null,  null,
				0, 1);

		Assert.assertTrue(list.size() > 0);
		EquipmentComment g = instance.getEquipmentComment(list.get(0).getId());
		Assert.assertTrue(g.getId().equals(list.get(0).getId()));
		Assert.assertTrue(g.getContent().equals(list.get(0).getContent()));

	}

	/**
	 * Test of getGoodsCommentsCnt method, of class GoodsCommentService.
	 */
	@Test
	public void testGetGoodsCommentsCnt() {
		System.out.println("getGoodsCommentsCnt");
		Integer cnt = instance.getEquipmentCommentCnt(null,  null);
		Assert.assertTrue(cnt > 0);
	}

	/**
	 * Test of saveGoodsComment method, of class GoodsCommentService.
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSaveGoodsComment() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		System.out.println("saveGoodsComment");
		List<EquipmentComment> list = instance.findEquipmentComment( null, null,
				0, 10000);
		EquipmentComment model = new EquipmentComment();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		instance.saveEquipmentComment(model);
		List<EquipmentComment> list1 = instance.findEquipmentComment( null, null,
				0, 10000);
		Assert.assertTrue(list.size() + 1 == list1.size());
	}

}