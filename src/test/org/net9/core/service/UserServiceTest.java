/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.core.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.types.MessageBoxType;
import org.net9.core.types.UserEventType;
import org.net9.domain.model.bbs.Userboard;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Message;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.view.ViewUserPageLog;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class UserServiceTest extends TestBase {
	private UserService userService;

	@Before
	public void setUp() {
		userService = Guice.createInjector(new ServiceModule()).getInstance(
				UserService.class);
	}

	/**
	 * Test of deleteMessage method, of class UserService.
	 */
	@Test
	public void testDeleteMessage() {
		System.out.println("deleteMessage");

		Message model = new Message();
		model.setMsgFrom("gladstone");
		model.setMsgTo("gladstone");
		model.setMsgRead(0);
		model.setMsgTitle("dummy_title");
		model.setMsgContent("dummy_content");
		model.setMsgTime("2009-00-11");
		this.userService.saveMessage(model);

		List<Message> list = this.userService.findMsgsByUser("gladstone", 0,
				false, null, 0, 1000000);
		Assert.assertTrue(list.size() > 0);
		Integer messageId = list.get(0).getMsgId();
		this.userService.deleteMessage(messageId);
	}

	/**
	 * Test of delFriend method, of class UserService.
	 */
	@Test
	public void testDelFriend() {
		System.out.println("delFriend");

		Friend model = new Friend();
		model.setFrdMemo("dummy_memo");
		model.setFrdTag("dummy_tag");
		model.setFrdUserMe("gladstone");
		model.setFrdUserYou("gladstone");
		userService.saveFriend(model, false);

		List<Friend> list = this.userService.findFriends("gladstone", null, 0,
				1000000);
		Assert.assertTrue(list.size() > 0);
		Integer id = list.get(0).getFrdId();
		this.userService.deleteFriend(id);
	}

	/**
	 * Test of findFriends method, of class UserService.
	 */
	@Test
	public void testFindFriends() {
		System.out.println("findFriends");
		String username = "gladstone";
		int start = 0;
		int limit = 1000;
		List<Friend> result = this.userService.findFriends(username, null,
				start, limit);
		System.out.println(result);
		// Assert.assertTrue(result.size() > 0);
	}

	/**
	 * Test of findMsgsByUser method, of class UserService.
	 */
	@Test
	public void testFindMsgsByUser() {
		System.out.println("findMsgsByUser");
		String username = "gladstone";
		int type = 0;
		boolean read = false;
		Boolean star = false;
		Integer start = 0;
		Integer limit = 10;
		List<Message> result = this.userService.findMsgsByUser(username, type,
				read, star, start, limit);
		System.out.println(result.size());
	}

	@Test
	public void testFindMsgsByUser_1() {
		List<Message> list = userService.findMsgsByUser("gladstone",
				MessageBoxType.MSG_TYPE_ALL.getValue(), true, null, 0, 10);
		System.out.println("size: " + list.size());
		for (Message m : list) {
			System.out.println(m.getMsgId() + "__" + m.getMsgTime() + "_"
					+ m.getMsgFrom());
		}
		Message model = userService.getMessage(27067);
		System.out.println(model == null);
	}

	/**
	 * Test of findMsgsGroupByUser method, of class UserService.
	 */
	@Test
	public void testFindMsgsGroupByUser() {
		List<Message> list = userService.findMsgsGroupByUser(true, 0, 10);
		System.out.println(list.size());
		for (Message m : list) {
			System.out.println(m.getMsgId() + "__" + m.getMsgTime() + "_"
					+ m.getMsgFrom());
		}
	}

	/**
	 * Test of findUserlogs method, of class UserService.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testFindUserlogs_4args() {
		System.out.println("findUserlogs");
		List<String> usernameArray = new ArrayList<String>();
		usernameArray.add("gladstone");
		Integer start = 0;
		Integer limit = 12;
		Integer[] types = null;
		List<UserLog> result = userService.findUserlogs(usernameArray, start,
				limit, types);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	// public void testFindTimeline() {
	// System.out.println("testFindTimeline");
	// List<String> usernameArray = new ArrayList<String>();
	// usernameArray.add("gladstone");
	// Integer start = 0;
	// Integer limit = 12;
	// Integer[] types = null;
	// List<ViewTimeline> result = userService.findViewTimeline(usernameArray,
	// start, limit, types);
	// System.out.println(result.size());
	// Assert.assertTrue(result.size() > 0);
	// }

	/**
	 * Test of findUserlogs method, of class UserService.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testFindUserlogs_6args() {
		System.out.println("findUserlogs");
		String username = "gladstone";
		String target = "";
		String date = "";
		Integer start = 0;
		Integer limit = 12;
		Integer[] types = null;
		List<UserLog> result = userService.findUserlogs(username, target, date,
				start, limit, types);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	// public void testFindTimeline_6args() {
	// System.out.println("testFindTimeline_6args");
	// String username = "gladstone";
	// String target = "";
	// String date = "";
	// Integer start = 0;
	// Integer limit = 12;
	// Integer[] types = null;
	// List<ViewTimeline> result = userService.findViewTimeline(username,
	// target, date, start, limit, types);
	// System.out.println(result.size());
	// Assert.assertTrue(result.size() > 0);
	// }

	@Test
	public void testFindUserPageLogs() {
		List<ViewUserPageLog> result = userService.findUserPageLogs(
				"gladstone", 0, 12);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	/**
	 * Test of findUsers method, of class UserService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindUsers_4args() {
		System.out.println("findUsers");
		Integer option = null;
		String key = "";
		Integer start = 0;
		Integer limit = 12;
		List result = userService.findUsers(option, key, start, limit);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFindUsersNotLogined4aWhile() {
		System.out.println("findUsersNotLogined4aWhile");
		Integer start = 0;
		Integer limit = 12;
		List<MainUser> result = userService.findUsersNotLogined4aWhile(30,
				start, limit);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
		for (MainUser u : result) {
			System.out.println(u.getUsername() + " " + u.getEmail() + " "
					+ u.getLoginTime());
		}
	}

	/**
	 * Test of findUsers method, of class UserService.
	 */
	@Test
	public void testFindUsers_Integer_Integer() {
		System.out.println("findUsers");
		List<User> result = userService.findUsers(0, 10);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);
	}

	/**
	 * Test of findUsersByEmail method, of class UserService.
	 */
	@Test
	public void testFindUsersByEmail() {
		System.out.println("findUsersByEmail");
		String email = "gladstone9@gmail.com";
		List<MainUser> result = this.userService.findUsersByEmail(email);
		Assert.assertNotNull(result);
	}

	/**
	 * Test of findUsersByEmailList method, of class UserService.
	 */
	@Test
	public void testFindUsersByEmailList() {
		System.out.println("findUsersByEmailList");
		List<String> emailList = new ArrayList<String>();
		emailList.add("gladstone9@gmail.com");
		List<MainUser> result = userService.findUsersByEmailList(emailList);
		Assert.assertNotNull(result);
	}

	/**
	 * Test of findUsersWithEmail method, of class UserService.
	 */
	@Test
	public void testFindUsersWithEmail() {
		System.out.println("findUsersWithEmail");
		List<MainUser> result = userService.findUsersWithEmail(0, 111);
		Assert.assertNotNull(result);
	}

	/**
	 * Test of findUsersWithQQ method, of class UserService.
	 */
	@Test
	public void testFindUsersWithQQ() {
		System.out.println("findUsersWithQQ");
		Integer start = 0;
		Integer limit = 12;
		List<MainUser> result = userService.findUsersWithQQ(start, limit);
		Assert.assertNotNull(result);
	}

	/**
	 * Test of getBmsCnt method, of class UserService.
	 */
	@Test
	public void testGetBmsCnt() {
		System.out.println("getBmsCnt");
		Integer boardId = null;
		Integer result = userService.getBmsCnt(boardId);
		System.out.println(result);
	}

	/**
	 * Test of getFriendById method, of class UserService.
	 */
	@Test
	public void testGetFriendById() {
		System.out.println("getFriendById");
		Integer frdId = 12;
		Friend result = userService.getFriendById(frdId);
		System.out.println(result);
	}

	/**
	 * Test of getFriendsCnt method, of class UserService.
	 */
	@Test
	public void testGetFriendsCnt() {
		System.out.println("getFriendsCnt");
		String username = "gladstone";
		String tag = "";
		Integer result = userService.getFriendsCnt(username, tag);
		System.out.println(result);
	}

	/**
	 * Test of getMessage method, of class UserService.
	 */
	@Test
	public void testGetMessage() {
		System.out.println("getMessage");
		Integer messageId = 1222;
		Message result = userService.getMessage(messageId);
		System.out.println(result);
	}

	/**
	 * Test of getMsgsCntByUser method, of class UserService.
	 */
	@Test
	public void testGetMsgsCntByUser() {
		System.out.println("getMsgsCntByUser");
		String username = "";
		Integer type = MessageBoxType.MSG_TYPE_RECEIVE.getValue().intValue();
		boolean read = false;
		Boolean star = false;
		int result = userService.getMsgsCntByUser(username, type, read, star);
		System.out.println(result);
	}

	@Test
	public void testGetMsgsCntByUser_1() {
		int i = userService.getMsgsCntByUser("gladstone",
				MessageBoxType.MSG_TYPE_RECEIVE.getValue(), true, null);
		System.out.println(i);
	}

	/**
	 * Test of getNextMessage method, of class UserService.
	 */
	@Test
	public void testGetNextMessage() {
		System.out.println("getNextMessage");
		Integer messageId = 1000;
		Boolean read = false;
		String username = "gladstone";
		Message result = userService.getNextMessage(messageId, read, username);
		System.out.println(result);
	}

	/**
	 * Test of getPrevMessage method, of class UserService.
	 */
	@Test
	public void testGetPrevMessage() {
		System.out.println("getPrevMessage");
		Integer messageId = 1000;
		Boolean read = false;
		String username = "gladstone";

		Message result = userService.getPrevMessage(messageId, read, username);
		System.out.println(result);
	}

	/**
	 * Test of getUser method, of class UserService.
	 */
	@Test
	public void testGetUser_Integer_String() {
		System.out.println("getUser");
		Integer userId = null;
		String username = "gladstone";
		User result = userService.getUser(userId, username);
		System.out.println(result);
	}

	/**
	 * Test of getUser method, of class UserService.
	 */
	@Test
	public void testGetUser_String() {
		System.out.println("getUser");
		String username = "";

		MainUser result = userService.getUser(username);
		System.out.println(result);
	}

	/**
	 * Test of getUserCnt method, of class UserService.
	 */
	@Test
	public void testGetUserCnt() {
		System.out.println("getUserCnt");
		Integer option = null;
		String key = "";
		Integer result = userService.getUserCnt(option, key);
		System.out.println(result);
	}

	/**
	 * Test of getUserInfoCompletePercent method, of class UserService.
	 */
	@Test
	public void testGetUserInfoCompletePercent() {
		System.out.println("getUserInfoCompletePercent");
		String username = "gladstone";
		String result = userService.getUserInfoCompletePercent(username);
		System.out.println(result);
	}

	/**
	 * Test of getUserlogCnt method, of class UserService.
	 */
	@Test
	public void testGetUserlogCnt() {
		System.out.println("getUserlogCnt");
		String username = "";
		String target = "";
		Integer type = null;
		Integer result = userService.getUserlogCnt(username, target, type);
		System.out.println(result);
	}

	/**
	 * Test of getUserlogsCnt method, of class UserService.
	 */
	@Test
	public void testGetUserlogsCnt_4args() {
		System.out.println("getUserlogsCnt");
		String username = "";
		String target = "";
		String date = "";
		Integer[] types = null;

		Integer result = userService.getUserlogsCnt(username, target, date,
				types);
		System.out.println(result);
	}

	/**
	 * Test of getUserlogsCnt method, of class UserService.
	 */
	@Test
	public void testGetUserlogsCnt_StringArr_IntegerArr() {
		System.out.println("getUserlogsCnt");
		String[] usernameArray = new String[] { "gladstone", "gg" };
		Integer[] types = null;
		Integer result = userService.getUserlogsCnt(usernameArray, types);
		System.out.println(result);
	}

	/**
	 * Test of getUserlogsCntBe4SomeDay method, of class UserService.
	 */
	@Test
	public void testGetUserlogsCntBe4SomeDay() {
		System.out.println("getUserlogsCntBe4SomeDay");
		String username = "";
		String target = "";
		Integer[] types = null;
		String date = "";
		Integer result = userService.getUserlogsCntBe4SomeDay(username, target,
				types, date);
		System.out.println(result);
	}

	/**
	 * Test of isFriend method, of class UserService.
	 */
	@Test
	public void testIsFriend() {
		System.out.println("isFriend");
		String frdUserMe = "gladstone";
		String frdUserYou = "gladstone";
		boolean result = userService.isFriend(frdUserMe, frdUserYou);
		System.out.println(result);
	}

	/**
	 * Test of listBms method, of class UserService.
	 */
	@Test
	public void testListBms() {
		System.out.println("listBms");
		Integer boardId = 5;
		Integer start = 6;
		Integer limit = 12;
		List<Userboard> result = userService.listBms(boardId, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of saveFriend method, of class UserService.
	 */
	@Test
	public void testSaveFriend() {
		System.out.println("saveFriend");
		Friend model = new Friend();
		model.setFrdMemo("dummy_memo");
		model.setFrdTag("dummy_tag");
		model.setFrdUserMe("gladstone");
		model.setFrdUserYou("gladstone");
		boolean update = false;
		userService.saveFriend(model, update);
	}

	/**
	 * Test of saveMainUser method, of class UserService.
	 */
	@Test
	public void testSaveMainUser_MainUser() {
		System.out.println("saveMainUser");
		MainUser model = new MainUser();
		model.setEmail("gladstone9@gmail.com");
		model.setEmailFlag(0);
		model.setEmailSettingMsg(0);
		model.setEmailSettingTimeline(0);
		model.setEmailSettingTopic(0);
		model.setEnable(1);
		model.setRegTime("2009-01-01");
		model.setUsername("gladstone_" + StringUtils.getTimeStrByNow());
		model.setState("dummy");
		model.setPassword("g");
		MainUser result = userService.saveMainUser(model);
		System.out.println(result);
	}

	/**
	 * Test of saveMessage method, of class UserService.
	 */
	@Test
	public void testSaveMessage() {
		System.out.println("saveMessage");
		Message model = new Message();
		model.setMsgFrom("gladstone");
		model.setMsgTo("gladstone");
		model.setMsgRead(0);
		model.setMsgTitle("dummy_title");
		model.setMsgContent("dummy_content");
		model.setMsgTime("2009-00-11");
		this.userService.saveMessage(model);
	}

	/**
	 * Test of saveUser method, of class UserService.
	 */
	@Test
	public void testSaveUser_3args() {
		System.out.println("saveUser");
		User model = new User();
		model.setUserName("g_" + StringUtils.getTimeStrByNow());
		model.setUserScore(0);
		model.setUserOption(1);
		boolean update = false;
		boolean withKey = false;
		userService.saveUser(model, update, withKey);
	}

	/**
	 * Test of saveUser method, of class UserService.
	 */
	@Test
	public void testSaveUser() {
		System.out.println("testSaveUser");
		User model = this.userService.getUser(null, "gladstone");
		System.out.println(model.getUserFace());
		model.setUserScore(0);
		model.setUserOption(1);
		model.setUserFace("dummy____dddd");
		boolean update = true;
		userService.saveUser(model, update);
		model = this.userService.getUser(null, "gladstone");
		System.out.println(model.getUserFace());
		Assert.assertTrue(model.getUserFace().equals("dummy____dddd"));
	}

	/**
	 * Test of trigeEvent method, of class UserService.
	 */
	@Test
	public void testTrigeEvent() {
		System.out.println("trigeEvent");
		String username = "gladstone";
		String target = "gladstone";
		userService.trigeEvent(this.userService.getUser(username), target,
				UserEventType.ADD_FRIEND);
	}

}