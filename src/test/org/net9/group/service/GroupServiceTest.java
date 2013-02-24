package org.net9.group.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.domain.model.group.GroupModel;
import org.net9.domain.model.view.ViewGroupReference;
import org.net9.domain.model.view.ViewRandomGroup;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class GroupServiceTest extends TestBase {

	GroupService groupService;

	@Before
	public void setUp() throws Exception {
		groupService = Guice.createInjector(
				new org.net9.core.service.ServiceModule()).getInstance(
				GroupService.class);
	}

	@Test
	public void testFindRandomGroups() {
		List<ViewRandomGroup> gList = groupService.findRandomGroups(0, 10);
		for (ViewRandomGroup g : gList) {
			System.out.println(g.getName());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindReferenceGroups() {
		List<GroupModel> gList = this.groupService.findGroups(null, null, 0, 1);
		System.out.println(gList.size());
		Assert.assertTrue(gList.size() > 0);
		List<ViewGroupReference> result = groupService.findReferenceGroups(
				gList.get(0).getId(), 0, 10);
		for (ViewGroupReference g : result) {
			System.out.println(g.getRefid());
		}
	}

	/**
	 * Test of deleteGroupUser method, of class GroupService.
	 */
	@Test
	public void testDeleteGroupUser() {
	}

	/**
	 * Test of delGroup method, of class GroupService.
	 */
	@Test
	public void testDelGroup() {
	}

	/**
	 * Test of findGroups method, of class GroupService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindGroups() {
		List<GroupModel> gList = this.groupService.findGroups(null, null, 0,
				100);
		System.out.println(gList.size());
	}

	/**
	 * Test of findGroupsByTag method, of class GroupService.
	 */
	@Test
	public void testFindGroupsByTag() {
	}

	/**
	 * Test of findGroupsByUsername method, of class GroupService.
	 */
	@Test
	public void testFindGroupsByUsername() {
	}

	/**
	 * Test of findGroupUsers method, of class GroupService.
	 */
	@Test
	public void testFindGroupUsers() {
	}

	/**
	 * Test of findHotGroups method, of class GroupService.
	 */
	@Test
	public void testFindHotGroups() {
	}

	/**
	 * Test of findNewGroups method, of class GroupService.
	 */
	@Test
	public void testFindNewGroups() {
	}

	/**
	 * Test of findNewGroupUsers method, of class GroupService.
	 */
	@Test
	public void testFindNewGroupUsers() {
	}

	/**
	 * Test of findRandomGroups method, of class GroupService.
	 */
	@Test
	public void testFindRandomGroups_Integer_Integer() {
	}

	/**
	 * Test of getGroup method, of class GroupService.
	 */
	@Test
	public void testGetGroup() {
	}

	/**
	 * Test of getGroupByName method, of class GroupService.
	 */
	@Test
	public void testGetGroupByName() {
	}

	/**
	 * Test of getGroupsCnt method, of class GroupService.
	 */
	@Test
	public void testGetGroupsCnt() {
	}

	/**
	 * Test of getGroupsCntByTag method, of class GroupService.
	 */
	@Test
	public void testGetGroupsCntByTag() {
	}

	/**
	 * Test of getGroupsCntByUsername method, of class GroupService.
	 */
	@Test
	public void testGetGroupsCntByUsername() {
	}

	/**
	 * Test of getGroupUser method, of class GroupService.
	 */
	@Test
	public void testGetGroupUser_Integer() {
	}

	/**
	 * Test of getGroupUser method, of class GroupService.
	 */
	@Test
	public void testGetGroupUser_Integer_String() {
	}

	/**
	 * Test of getGroupUsersCnt method, of class GroupService.
	 */
	@Test
	public void testGetGroupUsersCnt() {
	}

	/**
	 * Test of getLatestGroupByManager method, of class GroupService.
	 */
	@Test
	public void testGetLatestGroupByManager() {
	}

	/**
	 * Test of saveGroup method, of class GroupService.
	 */
	@Test
	public void testSaveGroup() {
	}

	/**
	 * Test of saveGroupUser method, of class GroupService.
	 */
	@Test
	public void testSaveGroupUser() {
	}

}
