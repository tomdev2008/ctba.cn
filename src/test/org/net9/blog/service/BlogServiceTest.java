/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.blog.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogCategory;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class BlogServiceTest extends TestBase {

	BlogService instance;

	@Before
	public void setUp() throws Exception {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				BlogService.class);
	}

	/**
	 * Test of delBlog method, of class BlogService.
	 */
	@Test
	public void testDelBlog() {
		System.out.println("delBlog");
		Blog model = new Blog();
		instance.delBlog(model);

	}

	/**
	 * Test of delCategory method, of class BlogService.
	 */
	@Test
	public void testDelCategory() {
		System.out.println("delCategory");
		BlogCategory model = new BlogCategory();
		instance.delCategory(model);

	}

	/**
	 * Test of findBlogs method, of class BlogService.
	 */
	@Test
	public void testFindBlogs() {
		System.out.println("findBlogs");
		int start = 0;
		int limit = 10;
		List<Blog> result = instance.findBlogs(start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findCategories method, of class BlogService.
	 */
	@Test
	public void testFindCategories() {
		System.out.println("findCategories");
		Integer blogId = null;
		int start = 0;
		int limit = 0;
		List<BlogCategory> result = instance.findCategories(blogId, start,
				limit);
		System.out.println(result);

	}

	/**
	 * Test of findHotBlogs method, of class BlogService.
	 */
	@Test
	public void testFindHotBlogs() {
		System.out.println("findHotBlogs");
		Integer start = 0;
		Integer limit = 12;

		List<Blog> result = instance.findHotBlogs(start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findHotBlogsByHits method, of class BlogService.
	 */
	@Test
	public void testFindHotBlogsByHits() {
		System.out.println("findHotBlogsByHits");
		Integer start = 0;
		Integer limit = 12;
		List<Blog> result = instance.findHotBlogsByHits(start, limit);
		System.out.println(result);

	}

	/**
	 * Test of getBlog method, of class BlogService.
	 */
	@Test
	public void testGetBlog() {
		System.out.println("getBlog");
		Integer id = 9;

		Blog result = instance.getBlog(id);
		System.out.println(result);

	}

	/**
	 * Test of getBlogByUser method, of class BlogService.
	 */
	@Test
	public void testGetBlogByUser() {
		System.out.println("getBlogByUser");
		String author = "glad";
		Blog expResult = null;
		Blog result = instance.getBlogByUser(author);
		assertEquals(expResult, result);

	}

	/**
	 * Test of getBlogsCnt method, of class BlogService.
	 */
	@Test
	public void testGetBlogsCnt() {
		System.out.println("getBlogsCnt");
		Integer result = instance.getBlogsCnt();
		System.out.println(result);

	}

	/**
	 * Test of getCategoriesCnt method, of class BlogService.
	 */
	@Test
	public void testGetCategoriesCnt() {
		System.out.println("getCategoriesCnt");
		Integer blogId = null;
		Integer result = instance.getCategoriesCnt(blogId);
		System.out.println(result);

	}

	/**
	 * Test of getCategory method, of class BlogService.
	 */
	@Test
	public void testGetCategory() {
		System.out.println("getCategory");
		Integer id = 12;
		BlogCategory result = instance.getCategory(id);
		System.out.println(result);
	}

	/**
	 * Test of getMaxCategoryIdx method, of class BlogService.
	 */
	@Test
	public void testGetMaxCategoryIdx() {
		System.out.println("getMaxCategoryIdx");
		Blog blog = instance.getBlog(9);
		Integer result = instance.getMaxCategoryIdx(blog);
		System.out.println(result);

	}

	/**
	 * Test of getMiniCategory method, of class BlogService.
	 */
	@Test
	public void testGetMiniCategory() {
		System.out.println("getMiniCategory");
		Integer blogId = 9;
		BlogCategory result = instance.getMiniCategory(blogId);
		System.out.println(result);
	}

	/**
	 * Test of getNextCategoryIdx method, of class BlogService.
	 */
	@Test
	public void testGetNextCategoryIdx() {
		System.out.println("getNextCategoryIdx");
		List<BlogCategory> modelList = this.instance.findCategories(9, 0, 1);
		Assert.assertTrue(modelList.size()>0);
		BlogCategory c = modelList.get(0);
		BlogCategory result = instance.getNextCategoryIdx(c);
		System.out.println(result);
	}

	/**
	 * Test of getPrevCategoryIdx method, of class BlogService.
	 */
	@Test
	public void testGetPrevCategoryIdx() {
		System.out.println("getPrevCategoryIdx");
		List<BlogCategory> modelList = this.instance.findCategories(9, 0, 1);
		Assert.assertTrue(modelList.size()>0);
		BlogCategory c = modelList.get(0);
		BlogCategory result = instance.getPrevCategoryIdx(c);
		System.out.println(result);

	}

	/**
	 * Test of getRssCategory method, of class BlogService.
	 */
	@Test
	public void testGetRssCategory() {
		System.out.println("getRssCategory");
		Integer blogId = 9;
		BlogCategory result = instance.getRssCategory(blogId);
		System.out.println(result);
	}

	/**
	 * Test of newBlog method, of class BlogService.
	 */
	@Test
	public void testNewBlog() {
		System.out.println("newBlog");
		Blog model = new Blog();
		model.setAuthor("gladstone");
		model.setCreateTime("2009-00-00");
		model.setDescription("dummy");
		model.setEntryCnt(1);
		model.setEntryCnt(0);
		model.setHits(0);
		model.setListType(0);
		model.setUpdateTime("2009-00-00");
		model.setName("gladstone");
		instance.newBlog(model);

	}

	/**
	 * Test of newCategory method, of class BlogService.
	 */
	@Test
	public void testNewCategory() {
		System.out.println("newCategory");
		BlogCategory model = new BlogCategory();
		model.setBlogBlog(this.instance.getBlog(9));
		model.setIdx(0);
		model.setName("dummy");
		model.setTags("t");
		instance.newCategory(model);

	}

	/**
	 * Test of updateBlog method, of class BlogService.
	 */
	@Test
	public void testUpdateBlog() {
		System.out.println("updateBlog");
		Blog model = instance.getBlog(9);
		model.setEntryCnt(1222);
		instance.updateBlog(model);

	}

	/**
	 * Test of updateCategory method, of class BlogService.
	 */
	@Test
	public void testUpdateCategory() {
		System.out.println("updateCategory");
		List<BlogCategory> modelList = this.instance.findCategories(9, 0, 1);
		Assert.assertTrue(modelList.size()>0);
		BlogCategory c = modelList.get(0);
		c.setIdx(1000);
		instance.updateCategory(c);

	}

}