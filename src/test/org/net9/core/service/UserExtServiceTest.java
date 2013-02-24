package org.net9.core.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserExtService;
import org.net9.core.service.UserService;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserNote;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class UserExtServiceTest extends TestBase {

	UserExtService instance;
	
	UserService userService;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				UserExtService.class);
		userService = Guice.createInjector(new ServiceModule()).getInstance(
				UserService.class);
	}

	/**
	 * Test of findUserNotes method, of class UserExtService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindUserNotes() {
		System.out.println("findUserNotes");
		User user = this.userService.getUser(null, "gladstone");
		Integer type = null;
		Integer start = 0;
		Integer limit = 10;
		List result = instance.findUserNotes(user.getUserId(), type, start,
				limit);
		System.out.println(result.size());
	}

	/**
	 * Test of getUserNote method, of class UserExtService.
	 */
	@Test
	public void testGetUserNote() {
		System.out.println("getUserNote");
		Integer eid = 10000;
		UserNote expResult = null;
		UserNote result = instance.getUserNote(eid);
		assertEquals(expResult, result);

	}

	/**
	 * Test of getUserNoteCnt method, of class UserExtService.
	 */
	@Test
	public void testGetUserNoteCnt() {
		System.out.println("getUserNoteCnt");
		Integer userId = 1000000;
		Integer type = null;
		int expResult = 0;
		int result = instance.getUserNoteCnt(userId, type);
		assertEquals(expResult, result);

	}

	/**
	 * Test of saveUserNote method, of class UserExtService.
	 */
	@Test
	public void testSaveUserNote() {
		System.out.println("saveUserNote");
		User user = this.userService.getUser(null, "gladstone");
		UserNote model = new UserNote();
		model.setAuthor_id(user.getUserId());
		model.setContent("test");
		model.setCreate_time(StringUtils.getTimeStrByNow());
		model.setIp("tst");
		model.setParent(0);
		model.setTitle("teset_title");
		model.setUser_id(user.getUserId());
		model.setType(0);
		boolean update = false;
		instance.saveUserNote(model, update);

	}

}