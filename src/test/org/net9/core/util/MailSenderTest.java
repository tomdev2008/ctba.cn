package org.net9.core.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.net9.bbs.service.TopicService;
import org.net9.blog.service.EntryService;
import org.net9.common.util.CommonUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.mail.MailSender;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.core.types.NewsStateType;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;
import org.net9.domain.model.news.NewsEntry;
import org.net9.news.service.NewsService;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class MailSenderTest extends TestBase {

//	 @Test
	public void testSendHtmlMail() {
		String title = "_test_html_email_";
		String content = "<h2>_test_email</h2>hello,this is gladstone from ctba ,<a href='http://ctba.cn'>click this</a><br>";
		String email = "gladstone9@foxmail.com";
		MailSender m = MailSender.getInstance();
		try {
			m.sendMail(title, content, new String[] { email },
					MailSender.EMAIL_TYPE_HTML);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	@Test
	public void testSendRemindMail() {
		MainUser user = new MainUser();
		user.setEmail("gladstone9@gmail.com");
		user.setUsername("雷锋");
		MailSender m = MailSender.getInstance();
		try {
			m.sendLoginRemindEmail(user, null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testDummy() {
		System.out.println("testDummy");
	}

	// @Test
	public void testSendInviteMail() {
		MailSender m = MailSender.getInstance();
		try {
			m.sendInviteMail("gladstone", new String[] { "gladstone@ctba.cn",
					"trac@ctba.cn" });
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	// @Test
	public void testSendMsgRemindMail() {
		MailSender m = MailSender.getInstance();
		MainUser user = new MainUser();
		user.setEmail("gladstone9@gmail.com");
		user.setUsername("雷锋");
		try {
			m.sendMsgRemindEmail(user, 2);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	// @Test
	public void testSendMsgRemindMailSingle() {
		MailSender m = MailSender.getInstance();
		MainUser user = new MainUser();
		user.setEmail("gladstone9@foxmail.com");
		user.setUsername("雷锋");

		MainUser userFrom = new MainUser();
		userFrom.setEmail("gladstone9@foxmail.com");
		userFrom.setUsername("石头");
		try {
			m.sendMsgRemindEmail(user, userFrom, ">_<");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	// @Test
	public void testSendRegMail() {
		MailSender m = MailSender.getInstance();
		MainUser user = new MainUser();
		user.setEmail("gladstone@ctba.cn");
		user.setUsername("雷锋");
		try {
			m.sendRegMail(user);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	// @Test
	public void testSendSimpleMail() {
		String title = "_test_simple_email_";
		String content = "_test_email";
		String email = "gladstone@ctba.cn";
		MailSender m = MailSender.getInstance();
		try {
			m.sendMail(title, content, new String[] { email },
					MailSender.EMAIL_TYPE_SIMPLE);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	// @Test
	public void testSendTimelineMail() {
		// UserService userService = (UserService)
		// ServiceFactory.getService(UserService.class);
		UserService userService = Guice.createInjector(new ServiceModule())
				.getInstance((UserService.class));

		List<MainUser> userList = userService.findUsersWithEmail(0,
				BusinessConstants.MAX_INT);

		for (MainUser user : userList) {
			if (user.getEmailSettingTimeline().intValue() == 1) {
				// 用戶的好友
				List<String> userAndFriends = new ArrayList<String>();
				List<Friend> friendList = userService.findFriends(user
						.getUsername(), null, 0, 25);
				for (Friend friend : friendList) {
					User friendUser = userService.getUser(null, friend
							.getFrdUserYou());
					if (friendUser == null
							|| userAndFriends
									.contains(friendUser.getUserName())) {
						continue;
					}
					userAndFriends.add(friendUser.getUserName());
				}
				List<UserLog> userLogs = userService.findUserlogs(
						userAndFriends, 0, 20,
						UserEventWrapper.DEFAULT_WANTED_TYPES);

				if (userLogs.size() > 0) {
					System.out.println("__" + user.getUsername());
				}
			}
		}

		MailSender m = MailSender.getInstance();
		MainUser user = new MainUser();
		user.setEmail("gladstone9@gmail.com");
		user.setUsername("雷锋");
		try {
			// 用戶的好友
			List friends = new ArrayList();
			List<String> userAndFriends = new ArrayList<String>();
			List<Friend> friendList = userService.findFriends(user
					.getUsername(), null, 0, 25);
			for (Friend friend : friendList) {
				User friendUser = userService.getUser(null, friend
						.getFrdUserYou());
				if (friendUser == null)
					continue;
				Map map = new HashMap();
				map.put("user", friendUser);
				friends.add(map);
				userAndFriends.add(friendUser.getUserName());
			}
			List<UserLog> userLogs = userService.findUserlogs(userAndFriends,
					0, 20, UserEventWrapper.DEFAULT_WANTED_TYPES);

			m.sendDailyTimelineEmail(user, ListWrapper.getInstance()
					.formatUserLogList(userLogs));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	// @Test
	public void testSendTopicMail() {
		// 每天发送
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date firstEmailTime = c.getTime();

		System.out.println(firstEmailTime);
		TopicService topicService = Guice.createInjector(new ServiceModule())
				.getInstance(TopicService.class);
		List<Topic> topicList = topicService.findNewTopicsByTime(null, 0, 0,
				BusinessConstants.MAX_INT);

		if (topicList.size() == 0) {
			topicList = topicService.findNewTopicsForIndex(0, 10);
		}
		NewsService newsService = Guice.createInjector(new ServiceModule())
				.getInstance(NewsService.class);

		// 好评新闻
		List<NewsEntry> goodNewses = newsService.findNewsesOrderByDigg(true,
				NewsStateType.OK.getValue(), null, CommonUtils
						.getDateFromTodayOn(-7), 0, 8);
		// 确认列表不为空
		if (goodNewses.size() < 1) {
			goodNewses = newsService.findNewsesOrderByDigg(true,
					NewsStateType.OK.getValue(), null, null, 0, 8);
		}

		EntryService entryService = Guice.createInjector(new ServiceModule())
				.getInstance(EntryService.class);

		List<BlogEntry> blogList = entryService.findEntries(null, null, null,
				0, 10);

		List<Object> allList = new ArrayList<Object>();
		allList.addAll(goodNewses);
		allList.addAll(topicList);
		allList.addAll(blogList);
		MailSender m = MailSender.getInstance();
		MainUser user = new MainUser();
		user.setEmail("gladstone9@gmail.com");
		user.setUsername("雷锋");

		MainUser user2 = new MainUser();
		user2.setEmail("mockee@gmail.com");
		user2.setUsername("百万");
		try {
			m.sendContentEmail(user, allList);
			m.sendContentEmail(user2, allList);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

}
