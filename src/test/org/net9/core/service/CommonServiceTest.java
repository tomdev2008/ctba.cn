package org.net9.core.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.core.MainCommend;
import org.net9.domain.model.core.MainPlace;
import org.net9.domain.model.core.Online;
import org.net9.domain.model.core.SysConfig;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class CommonServiceTest extends TestBase {

	private CommonService instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				CommonService.class);
	}

	/**
	 * Test of deleteMainCommend method, of class CommonService.
	 */
	@Test
	public void testDeleteMainCommend() {
		System.out.println("deleteMainCommend");
		List<MainCommend> modelList = this.instance.findMainCommends(0, 1);
		Assert.assertTrue(modelList.size()>0);
		instance.deleteMainCommend(modelList.get(0));

	}

	/**
	 * Test of findMainCommends method, of class CommonService.
	 */
	@Test
	public void testFindMainCommends() {
		System.out.println("findMainCommends");
		Integer start = 1;
		Integer limit = 12;
		List<MainCommend> result = instance.findMainCommends(start, limit);
		System.out.println(result.size());
	}

	/**
	 * Test of findMainCommendsByType method, of class CommonService.
	 */
	@Test
	public void testFindMainCommendsByType() {
		System.out.println("findMainCommendsByType");
		Integer type = 111111;
		Integer start = 0;
		Integer limit = 12;
		List<MainCommend> result = instance.findMainCommendsByType(type, start,
				limit);
		System.out.println(result.size());

	}

	/**
	 * Test of findOnlines method, of class CommonService.
	 */
	@Test
	public void testFindOnlines() {
		System.out.println("findOnlines");
		Integer start = 0;
		Integer limit = 12;
		List<Online> result = instance.findOnlines(start, limit);
		System.out.println(result.size());

	}

	/**
	 * Test of findPlaces method, of class CommonService.
	 */
	@Test
	public void testFindPlaces() {
		System.out.println("findPlaces");
		String name = "";
		Integer start = 0;
		Integer limit = 10;
		List<MainPlace> result = instance.findPlaces(name, start, limit);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);

	}

	/**
	 * Test of getConfig method, of class CommonService.
	 */
	@Test
	public void testGetConfig() {
		System.out.println("getConfig");
		SysConfig result = instance.getConfig();
		Assert.assertTrue(result != null);
	}

	/**
	 * Test of getForbiddenWordsAsList method, of class CommonService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetForbiddenWordsAsList() {
		System.out.println("getForbiddenWordsAsList");
		List result = instance.getForbiddenWordsAsList();
		Assert.assertTrue(result != null);
		Assert.assertTrue(result.size() > 0);

	}

	/**
	 * Test of getMainCommend method, of class CommonService.
	 */
	@Test
	public void testGetMainCommend() {
		System.out.println("getMainCommend");
		Integer id = 11;
		MainCommend result = instance.getMainCommend(id);
		System.out.println(result);
	}

	/**
	 * Test of getMainCommendCnt method, of class CommonService.
	 */
	@Test
	public void testGetMainCommendCnt() {
		System.out.println("getMainCommendCnt");
		int result = instance.getMainCommendCnt();
System.out.println(result);
	}

	/**
	 * Test of getMainCommendCntByType method, of class CommonService.
	 */
	@Test
	public void testGetMainCommendCntByType() {
		System.out.println("getMainCommendCntByType");
		Integer type = 1;
		int result = instance.getMainCommendCntByType(type);
		System.out.println(result);
	}

	/**
	 * Test of getOnlineByUsernameOrIp method, of class CommonService.
	 */
	@Test
	public void testGetOnlineByUsernameOrIp() {
		System.out.println("getOnlineByUsernameOrIp");
		String username = "gladstone";
		String ip = "";
		Online result = instance.getOnlineByUsernameOrIp(username, ip);
		System.out.println(result);
	}

	/**
	 * Test of getOnlineCnt method, of class CommonService.
	 */
	@Test
	public void testGetOnlineCnt() {
		System.out.println("getOnlineCnt");
		int result = instance.getOnlineCnt();
		System.out.println(result);
	}

	/**
	 * Test of getPlace method, of class CommonService.
	 */
	@Test
	public void testGetPlace() {
		System.out.println("getPlace");
		Integer id = 11;
		MainPlace result = instance.getPlace(id);
		System.out.println(result);
	}

	/**
	 * Test of getPlaceCnt method, of class CommonService.
	 */
	@Test
	public void testGetPlaceCnt() {
		System.out.println("getPlaceCnt");
		int result = instance.getPlaceCnt();
		System.out.println(result);
		Assert.assertTrue(result > 0);
	}

	/**
	 * Test of saveConfig method, of class CommonService.
	 */
	@Test
	public void testSaveConfig() {
		System.out.println("saveConfig");
		SysConfig model = new SysConfig();
		model.setDomainRoot("ttt");model.setForbiddenWords("fuck");
		model.setRssTitlePrefix("http://ctba.cn");
		model.setId(10);
		instance.saveConfig(model);
	}

	/**
	 * Test of saveMainCommend method, of class CommonService.
	 */
	@Test
	public void testSaveMainCommend() {
		System.out.println("saveMainCommend");
		MainCommend model = new MainCommend();
		model.setCreateTime("1009-00-00");
		model.setIdx(0);
		model.setImage("g");
		model.setLabel("gg");
		model.setType(1);
		model.setUpdateTime("2009-00-00");
		model.setLink("jj");
		instance.saveMainCommend(model);

	}

	/**
	 * Test of saveOnline method, of class CommonService.
	 */
	@Test
	public void testSaveOnline() {
		System.out.println("saveOnline");
		Online model = new Online();
model.setInstanceCnt(0);model.setAgent("ie");model.setIp("127.0.0.1");
model.setUsername("gladstone_"+StringUtils.getTimeStrByNow());model.setUpdateTime("2009-00-00");
		instance.saveOnline(model);

	}

	/**
	 * Test of savePlace method, of class CommonService.
	 */
	@Test
	public void testSavePlace() {
		System.out.println("savePlace");
		MainPlace model = new MainPlace();
		model.setName("ddd");model.setCreateTime("2009-00-00");
		model.setDescription("ddd");model.setParent(0);
		model.setUpdateTime("2009-00-00");
		List<MainPlace> result = instance.findPlaces(null, 0, 1);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
		model.setId(null);
		instance.savePlace(model);

	}

}