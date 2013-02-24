/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.core.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.net9.core.service.DefaultFileAdminService;
import org.net9.test.TestBase;

/**
 * 
 * @author ChenChangRen
 */
public class DefaultFileAdminServiceTest extends TestBase {

	/**
	 * Test of authAdmin method, of class DefaultFileAdminService.
	 */
	@Test
	public void testAuthAdmin() {
		System.out.println("authAdmin");
		String username = "";
		String password = "";
		DefaultFileAdminService instance = DefaultFileAdminService
				.getInstance();
		boolean expResult = false;
		boolean result = instance.authAdmin(username, password);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getAdminRole method, of class DefaultFileAdminService.
	 */
	@Test
	public void testGetAdminRole() {
		System.out.println("getAdminRole");
		String username = "gladstone";
		DefaultFileAdminService instance = DefaultFileAdminService
				.getInstance();
		String result = instance.getAdminRole(username);
		System.out.println(result);
		Assert.assertTrue(result.equals("admin"));

	}

	/**
	 * Test of getInstance method, of class DefaultFileAdminService.
	 */
	@Test
	public void testGetInstance() {
		System.out.println("getInstance");
		DefaultFileAdminService result = DefaultFileAdminService.getInstance();
		Assert.assertTrue(result != null);
	}

	/**
	 * Test of listAdmins method, of class DefaultFileAdminService.
	 */
	@Test
	public void testListAdmins() {
		System.out.println("listAdmins");
		Integer start = 0;
		Integer limit = 10;
		DefaultFileAdminService instance = DefaultFileAdminService
				.getInstance();
		List<String> result = instance.listAdmins(start, limit);
		System.out.println(result.size());
	}

}