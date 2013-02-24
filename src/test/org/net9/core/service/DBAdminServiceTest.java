/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.core.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.core.SysAdmin;
import org.net9.domain.model.core.SysAdminGroup;
import org.net9.domain.model.core.SysGroup;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class DBAdminServiceTest extends TestBase {
	DBAdminService instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				DBAdminService.class);
	}

	/**
	 * Test of deleteAdmin method, of class DBAdminService.
	 */
	@Test
	public void testDeleteAdmin() {
		System.out.println("deleteAdmin");
		String u = "gladstone_"+StringUtils.getTimeStrByNow()+"__0";
		SysAdmin model = new SysAdmin();
		model.setCreateTime("2009-00-00");
		model.setPassword("gladstone");
		model.setUsername(u);
		model.setUpdateTime("2009-00-00");
		this.instance.saveAdmin(model);
		model = instance.getAdmin(u);
		instance.deleteAdmin(model);
	}

	/**
	 * Test of deleteAdminGroup method, of class DBAdminService.
	 */
	@Test
	public void testDeleteAdminGroup() {
		System.out.println("deleteAdminGroup");
		Integer id = 12;
		instance.deleteAdminGroup(id);
	}

	/**
	 * Test of deleteGroup method, of class DBAdminService.
	 */
	@Test
	public void testDeleteGroup() {
		System.out.println("deleteGroup");
		List<SysGroup> list = instance.findGroups(0, 1);
		Assert.assertTrue(list.size()>0);
		SysGroup model = list.get(0);
		instance.deleteGroup(model);
	}

	/**
	 * Test of findAdminGroups method, of class DBAdminService.
	 */
	@Test
	public void testFindAdminGroups() {
		System.out.println("findAdminGroups");
		String username = "gladstone";
		List<SysAdminGroup> result = instance.findAdminGroups(username);
		System.out.println(result.size());
	}

	/**
	 * Test of findAdmins method, of class DBAdminService.
	 */
	@Test
	public void testFindAdmins() {
		System.out.println("findAdmins");
		Integer start = 0;
		Integer limit = 12;
		List<SysAdmin> result = instance.findAdmins(start, limit);
		System.out.println(result.size());
	}

	/**
	 * Test of findGroups method, of class DBAdminService.
	 */
	@Test
	public void testFindGroups() {
		System.out.println("findGroups");
		Integer start = 0;
		Integer limit = 1;
		List<SysGroup> result = instance.findGroups(start, limit);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	@Test
	public void testGetAdmin() {
		SysAdmin admin = instance.getAdmin("gladstone");
		System.out.println(admin);
	}

	/**
	 * Test of getAdminCnt method, of class DBAdminService.
	 */
	@Test
	public void testGetAdminCnt() {
		System.out.println("getAdminCnt");
		Integer result = instance.getAdminCnt();
		System.out.println(result);
	}

	/**
	 * Test of getAdminGroup method, of class DBAdminService.
	 */
	@Test
	public void testGetAdminGroup_Integer() {
		System.out.println("getAdminGroup");
		Integer id = 1000000;
		SysAdminGroup expResult = null;
		SysAdminGroup result = instance.getAdminGroup(id);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getAdminGroup method, of class DBAdminService.
	 */
	@Test
	public void testGetAdminGroup_String_Integer() {
		System.out.println("getAdminGroup");
		String username = "gladstone";
		Integer groupId = 100000;
		SysAdminGroup expResult = null;
		SysAdminGroup result = instance.getAdminGroup(username, groupId);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getGroup method, of class DBAdminService.
	 */
	@Test
	public void testGetGroup() {
		System.out.println("getGroup");
		Integer id = 1222;
		SysGroup expResult = null;
		SysGroup result = instance.getGroup(id);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getGroupCnt method, of class DBAdminService.
	 */
	@Test
	public void testGetGroupCnt() {
		System.out.println("getGroupCnt");
		Integer result = instance.getGroupCnt();
		System.out.println(result);
	}

	@Test
	public void testGetGroupCnt_1() {
		Integer cnt = instance.getGroupCnt();
		Assert.assertTrue(cnt != null);
	}

	/**
	 * Test of insertAdminGroup method, of class DBAdminService.
	 */
	@Test
	public void testInsertAdminGroup() {
		System.out.println("insertAdminGroup");
		SysAdmin model = new SysAdmin();
		model.setCreateTime("2009-00-00");
		model.setPassword("gladstone");
		model.setUsername("gladstone_"+StringUtils.getTimeStrByNow()+"__2");
		model.setUpdateTime("2009-00-00");
		instance.saveAdmin(model);
		SysGroup g = new SysGroup();
		g.setCreateTime("2009-00-00");
		g.setName("name");
		g.setOptionStr("R");
		g.setUpdateTime("2009-00-00");
		instance.saveGroup(g);
		List<SysGroup> gList= this.instance.findGroups(0, 1);
		String optionStr = "T";
		
		instance.insertAdminGroup(gList.get(0), model, optionStr);
	}

	/**
	 * Test of isSuperAdmin method, of class DBAdminService.
	 */
	@Test
	public void testIsSuperAdmin() {
		System.out.println("isSuperAdmin");
		String username = "gladstone_____";
		boolean expResult = false;
		boolean result = instance.isSuperAdmin(username);
		assertEquals(expResult, result);
	}

	/**
	 * Test of saveAdmin method, of class DBAdminService.
	 */
	@Test
	public void testSaveAdmin() {
		System.out.println("saveAdmin");
		SysAdmin model = new SysAdmin();
		model.setCreateTime("2009-00-00");
		model.setPassword("gladstone");
		model.setUsername("gladstone_"+StringUtils.getTimeStrByNow());
		model.setUpdateTime("2009-00-00");
		instance.saveAdmin(model);
	}

	/**
	 * Test of saveGroup method, of class DBAdminService.
	 */
	@Test
	public void testSaveGroup() {
		System.out.println("saveGroup");
		SysGroup model = new SysGroup();
		model.setCreateTime("2009-00-00");
		model.setName("name");
		model.setOptionStr("R");
		model.setUpdateTime("2009-00-00");
		instance.saveGroup(model);
	}

}