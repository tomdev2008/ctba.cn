/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.blog.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.blog.BlogAddress;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class AddressServiceTest extends TestBase {

	AddressService instance;

	@Before
	public void setUp() throws Exception {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				AddressService.class);
	}

	/**
	 * Test of deleteAddress method, of class AddressService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteAddress() {
		System.out.println("deleteAddress");
		List<BlogAddress> list = instance.findAddress(0, 1);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
		Integer aid = list.get(0).getId();
		instance.deleteAddress(aid);
		Assert.assertTrue(instance.getAddress(aid) == null);
	}

	/**
	 * Test of findAddress method, of class AddressService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAddress() {
		System.out.println("findAddress");
		Integer start = 0;
		Integer limit = 10;
		List<BlogAddress> result = instance.findAddress(start, limit);
		System.out.println(result.size());
		System.out.println(result.get(0).getUsername());
	}

	/**
	 * Test of findAddressByUser method, of class AddressService.
	 */
	@Test
	public void testFindAddressByUser() {
		System.out.println("findAddressByUser");
		List<BlogAddress> result = instance.findAddressByUser("smth");
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	/**
	 * Test of getAddress method, of class AddressService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAddress() {
		System.out.println("getAddress");
		List<BlogAddress> list = instance.findAddress(0, 1);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
		BlogAddress result = instance.getAddress(list.get(0).getId());
		Assert.assertTrue(result != null);

	}

	/**
	 * Test of getAddressByUserAndUrl method, of class AddressService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAddressByUserAndUrl() {
		System.out.println("getAddressByUserAndUrl");
		List<BlogAddress> list = instance.findAddress(0, 1);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);

		BlogAddress expResult = list.get(0);
		BlogAddress result = instance.getAddressByUserAndUrl(expResult
				.getUsername(), expResult.getUrl());
		Assert.assertTrue(result != null);
	}

	/**
	 * Test of getAddressCnt method, of class AddressService.
	 */
	@Test
	public void testGetAddressCnt_0args() {
		System.out.println("getAddressCnt");
		Integer result = instance.getAddressCnt();
		System.out.println(result);
	}

	/**
	 * Test of getAddressCnt method, of class AddressService.
	 */
	@Test
	public void testGetAddressCnt_String() {
		System.out.println("getAddressCnt");
		String username = "";
		Integer result = instance.getAddressCnt(username);
		System.out.println(result);
	}

	/**
	 * Test of saveAddress method, of class AddressService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveAddress() {
		System.out.println("saveAddress");
		BlogAddress model = new BlogAddress();
		List<BlogAddress> list = instance.findAddress(0, 1);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
		model.setCat(list.get(0).getCat());
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setGotEntriesCnt(0);
		model.setUrl("url");
		model.setUsername("gladstone");
		instance.saveAddress(model);
	}

}