/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.blog.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.blog.BlogLink;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class LinkServiceTest extends TestBase {

	LinkService linkService;

	@Before
	public void setUp() throws Exception {
		linkService = Guice.createInjector(new ServiceModule()).getInstance(
				LinkService.class);
	}

	/**
	 * Test of delLink method, of class LinkService.
	 */
	@Test
	public void testDelLink() {
		System.out.println("delLink");
		List<BlogLink> list = linkService.listLinks(null, 0, 1);
		Assert.assertTrue(list.size()>0);
		BlogLink model = list.get(0);
		linkService.delLink(model);

	}

	/**
	 * Test of getLink method, of class LinkService.
	 */
	@Test
	public void testGetLink() {
		System.out.println("getLink");
		Integer id = 100;
		BlogLink result = linkService.getLink(id);
		System.out.println(result);

	}

	/**
	 * Test of getLinksCnt method, of class LinkService.
	 */
	@Test
	public void testGetLinksCnt() {
		System.out.println("getLinksCnt");
		Integer blogId = 9;
		Integer result = linkService.getLinksCnt(blogId);
		System.out.println(result);

	}

	/**
	 * Test of getMaxLinksIdx method, of class LinkService.
	 */
	@Test
	public void testGetMaxLinksIdx() {
		System.out.println("getMaxLinksIdx");
		Integer blogId = 9;
		Integer result = linkService.getMaxLinksIdx(blogId);
		System.out.println(result);

	}

	/**
	 * Test of getMinLinksIdx method, of class LinkService.
	 */
	@Test
	public void testGetMinLinksIdx() {
		System.out.println("getMinLinksIdx");
		Integer blogId = 9;
		Integer result = linkService.getMinLinksIdx(blogId);
		System.out.println(result);

	}

	/**
	 * Test of getNextLinksIdx method, of class LinkService.
	 */
	@Test
	public void testGetNextLinksIdx() {
		System.out.println("getNextLinksIdx");
		List<BlogLink> list = linkService.listLinks(null, 0, 1);
		Assert.assertTrue(list.size()>0);
		BlogLink model = list.get(0);
		BlogLink result = linkService.getNextLinksIdx(model);
		System.out.println(result);

	}

	/**
	 * Test of getPrevLinksIdx method, of class LinkService.
	 */
	@Test
	public void testGetPrevLinksIdx() {
		System.out.println("getPrevLinksIdx");
		List<BlogLink> list = linkService.listLinks(null, 0, 1);
		Assert.assertTrue(list.size()>0);
		BlogLink model = list.get(0);
		BlogLink result = linkService.getPrevLinksIdx(model);
		System.out.println(result);
	}

	/**
	 * Test of listLinks method, of class LinkService.
	 */
	@Test
	public void testListLinks() {
		System.out.println("listLinks");
		Integer blogId = null;
		Integer start = 0;
		Integer limit = 12;
		List<BlogLink> result = linkService.listLinks(blogId, start, limit);
		System.out.println(result);

	}

	/**
	 * Test of newLink method, of class LinkService.
	 */
	@Test
	public void testNewLink() {
		System.out.println("newLink");
		BlogLink model = new BlogLink();
		model.setBlogId(9);
		model.setHit(0);
		model.setIdx(0);
		model.setImageUrl("uuuu");
		model.setTitle("dummy_title");
		model.setUpdateTime("2009-00-00");
		model.setUrl("dmmyurl");
		linkService.newLink(model);
	}

	/**
	 * Test of updateEntry method, of class LinkService.
	 */
	@Test
	public void testUpdateLink() {
		System.out.println("updateLink");
		List<BlogLink> list = linkService.listLinks(null, 0, 1);
		Assert.assertTrue(list.size()>0);
		BlogLink model = list.get(0);
		model.setHit(1000);
		linkService.updateLink(model);
	}

}