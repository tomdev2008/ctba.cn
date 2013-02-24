/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.blog.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.blog.BlogComment;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class CommentServiceTest extends TestBase {

	CommentService instance;

	@Before
	public void setUp() throws Exception {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				CommentService.class);
	}

	/**
	 * Test of delComment method, of class CommentService.
	 */
	@Test
	public void testDelComment() {
		System.out.println("delComment");
		List<BlogComment> list = instance.findComments(null, null,
				0, 1);
		Assert.assertTrue(list.size()>0);
		BlogComment model = list.get(0);
		instance.delComment(model);

	}

	/**
	 * Test of getComment method, of class CommentService.
	 */
	@Test
	public void testGetComment() {
		System.out.println("getComment");
		Integer id = 1;
		BlogComment result = instance.getComment(id);
		System.out.println(result);
	}

	/**
	 * Test of getCommentsCnt method, of class CommentService.
	 */
	@Test
	public void testGetCommentsCnt() {
		System.out.println("getCommentsCnt");
		Integer blogId = 9;
		Integer entryId = 12;
		Integer result = instance.getCommentsCnt(blogId, entryId);
		System.out.println(result);

	}

	/**
	 * Test of listComments method, of class CommentService.
	 */
	@Test
	public void testListComments_3args() {
		System.out.println("listComments");
		Integer entryId = null;
		Integer start = 0;
		Integer limit = 10;
		List<BlogComment> result = instance.findComments(entryId, start, limit);
		System.out.println(result);
	}

	/**
	 * Test of listComments method, of class CommentService.
	 */
	@Test
	public void testListComments_4args() {
		System.out.println("listComments");
		Integer blogId = null;
		Integer entryId = null;
		Integer start = 0;
		Integer limit = 10;
		List<BlogComment> result = instance.findComments(blogId, entryId,
				start, limit);
		System.out.println(result);
	}

	/**
	 * Test of newComment method, of class CommentService.
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void testNewComment() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println("newComment");
		List<BlogComment> list = instance.findComments(null, null,
				0, 1);
		Assert.assertTrue(list.size()>0);
		BlogComment model = new BlogComment();
		PropertyUtils.copyProperties( model,list.get(0));
		model.setId(null);
		instance.newComment(model);

	}

	/**
	 * Test of updateComment method, of class CommentService.
	 */
	@Test
	public void testUpdateComment() {
		System.out.println("updateComment");
		List<BlogComment> list = instance.findComments(null, null,
				0, 1);
		Assert.assertTrue(list.size()>0);
		BlogComment model = list.get(0);
		model.setAuthenticated((short)0);
		instance.updateComment(model);
	}

}