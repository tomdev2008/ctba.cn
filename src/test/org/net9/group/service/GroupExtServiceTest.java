package org.net9.group.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.group.GroupModel;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class GroupExtServiceTest extends TestBase {

	GroupExtService groupExtService;
	GroupService groupService;

	@Before
	public void setUp() throws Exception {
		groupExtService = Guice.createInjector(new ServiceModule())
				.getInstance(GroupExtService.class);
		groupService = Guice.createInjector(new ServiceModule()).getInstance(
				GroupService.class);
	}

	/**
	 * Test of getLatestGroupByManager method, of class GroupService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetGroupRank() {
		List<GroupModel> gList = this.groupService.findGroups(null, null, 0, 1);
		System.out.println(gList.size());
		org.junit.Assert.assertTrue(gList.size() == 1);
		Integer rank = groupExtService.getGroupRank(gList.get(0).getId());
		System.out.println("rank: " + rank);
	}

}
