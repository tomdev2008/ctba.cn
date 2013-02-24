/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.domain.dao.bbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.PropertyUtil;
import org.net9.core.service.ServiceModule;
import org.net9.domain.dao.bbs.BoardsDAO;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.blog.BlogAddress;
import org.net9.test.dao.DAOTestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class BoardsDAOTest extends DAOTestBase {

	BoardsDAO instance;

	@Before
	public void setUp() {
		instance = (BoardsDAO) Guice.createInjector(new ServiceModule())
				.getInstance(BoardsDAO.class);
	}

	/**
	 * Test of decodeBoardState method, of class BoardsDAO.
	 */
	@Test
	public void testDecodeBoardState() {
		System.out.println("decodeBoardState");
		int boardState = 127;
		String[] expResult = { "on", "on", "on", "on", "on", "on", "on" };
		String[] result = BoardsDAO.decodeBoardState(boardState);
		for (String s : result) {
			System.out.println(s);
		}
		assertEquals(expResult, result);
	}

	/**
	 * Test of encodeBoardState method, of class BoardsDAO.
	 */
	@Test
	public void testEncodeBoardState() {
		System.out.println("encodeBoardState");
		String[] values = { "on", "on", "on", "on", "on", "on", "on" };
		int expResult = 127;
		int result = BoardsDAO.encodeBoardState(values);
		assertEquals(expResult, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		System.out.println("findAll");
		List<BlogAddress> result = instance.findAll();
		System.out.println(result.size());
		assertTrue(result.size() > 0);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testRemove() {
		List<Board> list = instance.findAll();
		assertTrue(list.size() > 0);
		Board model = list.get(0);
		this.instance.remove(model);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSave() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		List<Board> list = instance.findAll();
		assertTrue(list.size() > 0);
		Board model = new Board();
		PropertyUtil.copyProperties(model, list.get(0));
		model.setBoardId(null);
		this.instance.save(model);
		List<Board> newList = instance.findAll();

		assertTrue(list.size() + 1 == newList.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() {
		List<Board> list = instance.findAll();
		assertTrue(list.size() > 0);
		Board model = list.get(0);
		model.setBoardFace("test_url");
		this.instance.update(model);
		list = instance.findAll();
		assertTrue(list.get(0).getBoardFace().equals(model.getBoardFace()));
	}

}