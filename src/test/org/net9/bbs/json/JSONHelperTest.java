package org.net9.bbs.json;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.json.JSONHelper;
import org.net9.common.json.base.JSONException;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.domain.model.core.User;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class JSONHelperTest extends TestBase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBuildPOJOJsonFromStr() throws JSONException {
		String str = "{\"userId\":1000000000,\"userName\":\"gladstone\",\"userFace\":null}";
		JSONHelper helper = new JSONHelper();
		User user = (User) helper.buildPOJOFromJsonStr(str, User.class);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getUserId(), 1000000000);
		Assert.assertEquals(user.getUserName(), "gladstone");
	}

	@Test
	public void testGetPOJOJsontStr() throws JSONException {
		User user = new User();
		user.setUserFace("fff...");
		user.setUserId(100);
		user.setUserName("gladstone");
		JSONHelper helper = new JSONHelper();
		String str = helper.getPOJOJsonStr(user);
		System.out.println(str);
	}

	@Test
	public void testGetPOJOListJsontStr() throws JSONException {
		UserService userService = Guice.createInjector(new ServiceModule())
				.getInstance(UserService.class);
		JSONHelper helper = new JSONHelper();
		String str = helper.getPOJOListJsonStr(userService.findUsers(0, 20));
		System.out.println(str);
	}

}
