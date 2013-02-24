package org.net9.core.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.EmailService;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.core.SysEmail;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class EmailServiceTest extends TestBase {

	EmailService instance;

	@Before
	public void setUp() {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				EmailService.class);
	}

	/**
	 * Test of deleteEmail method, of class EmailService.
	 */
	@Test
	public void testDeleteEmail() {
		System.out.println("deleteEmail");

		Integer start = 0;
		Integer limit = 1;
		List<SysEmail> list = instance.findEmails("", start, limit);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
		SysEmail model = list.get(0);
		instance.deleteEmail(model);

	}

	/**
	 * Test of findEmails method, of class EmailService.
	 */
	@Test
	public void testFindEmails() {
		System.out.println("findEmails");
		String username = "";
		Integer start = 0;
		Integer limit = 1;
		List<SysEmail> result = instance.findEmails(username, start, limit);
		System.out.println(result.size());
		Assert.assertTrue(result.size() > 0);

	}

	/**
	 * Test of getEmail method, of class EmailService.
	 */
	@Test
	public void testGetEmail() {
		System.out.println("getEmail");
		Integer start = 0;
		Integer limit = 1;
		List<SysEmail> list = instance.findEmails("", start, limit);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
		SysEmail result = instance.getEmail(list.get(0).getId());
		Assert.assertNotNull(result);

	}

	/**
	 * Test of getEmailsCnt method, of class EmailService.
	 */
	@Test
	public void testGetEmailsCnt() {
		System.out.println("getEmailsCnt");
		String username = "";
		Integer result = instance.getEmailsCnt(username);
		System.out.println(result);

	}

	/**
	 * Test of saveEmail method, of class EmailService.
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Test
	public void testSaveEmail() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		System.out.println("saveEmail");
		SysEmail model = new SysEmail();

		Integer start = 0;
		Integer limit = 1;
		List<SysEmail> list = instance.findEmails("", start, limit);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);
		PropertyUtil.copyProperties(model, list.get(0));
		model.setId(null);
		instance.saveEmail(model);
	}

}