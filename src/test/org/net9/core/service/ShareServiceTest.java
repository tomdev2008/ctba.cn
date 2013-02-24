package org.net9.core.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.core.Share;
import org.net9.test.TestBase;

import cn.ctba.share.service.ShareService;

import com.google.inject.Guice;

public class ShareServiceTest extends TestBase {

	ShareService shareService;

	@Before
	public void setUp() throws Exception {
		shareService = Guice.createInjector(new ServiceModule()).getInstance(
				ShareService.class);
	}

	/**
	 * Test of deleteShare method, of class ShareService.
	 */
	@Test
	public void testDeleteShare_Share() {
		System.out.println("deleteShare");
		Share model = this.shareService.getNewestShareByUsername("gladstone");
		shareService.deleteShare(model);

	}

	/**
	 * Test of findAllShares method, of class ShareService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllShares() {
		System.out.println("findAllShares");
		Integer type = null;
		Integer start = 0;
		Integer limit = 1;
		List result = shareService.findAllShares(type, start, limit);
		System.out.println(result);

	}

	@Test
	public void testFindShares() {
	}

	/**
	 * Test of findShares method, of class ShareService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindShares_4args_1() {
		System.out.println("findShares");
		String username = "gladstone";
		Integer type = null;
		Integer start = 0;
		Integer limit = 12;
		List result = shareService.findShares(username, type, null,start, limit);
		System.out.println(result.size());
	}

	@Test
	public void testGetShare() {
	}

	/**
	 * Test of getShare method, of class ShareService.
	 */
	@Test
	public void testGetShare_Integer() {
		System.out.println("getShare");
		Integer sid = 1000000;
		Share result = shareService.getShare(sid);
		System.out.println(result );
	}

	@Test
	public void testGetShareCnt() {
	}

	/**
	 * Test of getShareCnt method, of class ShareService.
	 */
	@Test
	public void testGetShareCnt_String_Integer() {
		System.out.println("getShareCnt");
		String username = "gladstone999_dummy";
		Integer type = null;
		int expResult = 0;
		int result = shareService.getShareCnt(username, type,null);
		assertEquals(expResult, result);

	}

	@Test
	public void testSaveShare() {
		System.out.println("saveShare");
		Share model = new Share();
		model.setUrl("test");
		model.setUpdateTime(StringUtils.getTimeStrByNow());
		model.setType(2);
		model.setLabel("test_url");
		model.setUsername("gladstone");
		shareService.saveShare(model);
	}

}
