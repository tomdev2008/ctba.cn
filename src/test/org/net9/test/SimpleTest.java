package org.net9.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.core.constant.SystemConfigVars;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.domain.model.core.UserLog;

import com.google.inject.Guice;

public class SimpleTest extends TestBase {

	UserService userService;

	@Before
	public void setUp() {
		userService = Guice.createInjector(new ServiceModule()).getInstance(
				UserService.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		List<UserLog> l = userService
				.findUserlogs(null, null, null, 0, 1, null);
		System.out.println(l.size());
		System.out.println(userService.getUserCnt(null, null));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test_1() {
		System.out.println(SystemConfigVars.ATTACH_VIEW_ALLOW_GUEST);

	}

}
