/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.net9.bbs.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.StringUtils;
import org.net9.core.service.ServiceModule;
import org.net9.core.service.UserService;
import org.net9.domain.model.bbs.Board;
import org.net9.domain.model.bbs.Forbidden;
import org.net9.domain.model.bbs.Userboard;
import org.net9.domain.model.core.User;
import org.net9.domain.model.view.ViewHotUser;
import org.net9.test.TestBase;

import com.google.inject.Guice;

/**
 * 
 * @author ChenChangRen
 */
public class BoardServiceTest extends TestBase {

	private BoardService boardService;
	private UserService userService;

	@Before
	public void setUp() {
		boardService = Guice.createInjector(new ServiceModule()).getInstance(
				BoardService.class);
		userService = Guice.createInjector(new ServiceModule()).getInstance(
				UserService.class);
	}

	/**
	 * Test of bmList2Str method, of class BoardService.
	 */
	@Test
	public void testBmList2Str() {
		System.out.println("bmList2Str");
		List<String> list = new ArrayList<String>();
		list.add("gladstone9@gmail.com");
		list.add("mockee@gmail.com");
		String expResult = "gladstone9@gmail.com,mockee@gmail.com";
		String result = BoardService.bmList2Str(list);
		assertEquals(expResult, result);
	}

	/**
	 * Test of bmStr2List method, of class BoardService.
	 */
	@Test
	public void testBmStr2List() {
		System.out.println("bmStr2List");
		String str = "gladstone9@gmail.com,mockee@gmail.com";
		List<String> result = BoardService.bmStr2List(str);
		List<String> list = new ArrayList<String>();
		list.add("gladstone9@gmail.com");
		list.add("mockee@gmail.com");
		assertEquals(list, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBoard() {
		List boards = boardService.findBoards(null, null, 0, 1000);
		System.out.println(boards.size());
		int i = boards.size();
		Assert.assertTrue(boards.size() > 0);
		Board b = new Board();
		b.setBoardName("dummy1");
		b.setBoardCode("dummy1");
		b.setBoardType(1);
		b.setBoardTodayNum(0);
		b.setBoardRecom(0);
		b.setBoardReNum(0);
		b.setBoardParent(0);
		b.setBoardOption(0);
		b.setBoardLevel(0);
		b.setBoardTopicNum(0);
		b.setBoardNewTopic(0L);
		boardService.saveBoard(b, false);
		boards = boardService.findBoards(null, null, 0, 1000);
		System.out.println(boards.size());
		Assert.assertTrue(boards.size() == i + 1);
		// Assert.assertTrue(((Board) boards.get(i)).getBoardCode().equals(
		// "dummy1"));
		// Assert.assertTrue(((Board) boards.get(i)).getBoardName().equals(
		// "dummy1"));
		b = new Board();
		b.setBoardName("dummy2");
		b.setBoardParent(0);
		b.setBoardRecom(0);
		b.setBoardOption(0);
		b.setBoardReNum(0);
		b.setBoardCode("dummy2");
		b.setBoardType(2);
		b.setBoardInfo("dd");
		b.setBoardTodayNum(0);
		b.setBoardTopicNum(0);
		b.setBoardLevel(0);
		b.setBoardNewTopic(0L);
		boardService.saveBoard(b, false);
		boards = boardService.findBoards(null, null, 0, 1000);
		System.out.println(boards.size());
		Assert.assertTrue(boards.size() == i + 2);
		// Assert.assertTrue(((Board) boards.get(i)).getBoardCode().equals(
		// "dummy1"));
		// Assert.assertTrue(((Board) boards.get(i)).getBoardName().equals(
		// "dummy1"));
		// Assert.assertTrue(((Board) boards.get(i + 1)).getBoardCode().equals(
		// "dummy2"));
		// Assert.assertTrue(((Board) boards.get(i + 1)).getBoardName().equals(
		// "dummy2"));

		Board updateBoard = boardService.getBoard(((Board) boards.get(0))
				.getBoardId());
		updateBoard.setBoardCode("dummy3");
		boardService.saveBoard(updateBoard, true);
		boards = boardService.findBoards(null, null, 0, 1000);
		System.out.println(boards.size());
		Assert.assertTrue(boards.size() == i + 2);
		// Assert.assertTrue(((Board) boards.get(1)).getBoardCode().equals(
		// "dummy3"));
		// Assert.assertTrue(((Board) boards.get(1)).getBoardName().equals(
		// "dummy1"));

	}

	/**
	 * Test of checkUserRole method, of class BoardService.
	 */
	@Test
	public void testCheckUserRole() {
		System.out.println("checkUserRole");
		int userId = 11331;
		int boardId = 5;
		Integer result = boardService.checkUserRole(userId, boardId);
		System.out.println(result);
	}

	/**
	 * Test of delBoradManager method, of class BoardService.
	 */
	@Test
	public void testDelBoradManager() {
		System.out.println("delBoradManager");

        Userboard model = new Userboard();
		User u = this.userService.getUser(null, "gladstone");
		model.setBoard_id(5);
		model.setRole(2);
		model.setTopic_cnt(10);
		model.setUser_id(u.getUserId());
		model.setCreate_time(StringUtils.getTimeStrByNow());
        boardService.saveUserBoard(model,false);

        
		String userName = "gladstone";
		int boardId = 5;
		boardService.delBoradManager(userName, boardId);
	}

	/**
	 * Test of deleteBoard method, of class BoardService.
	 */
	@Test
	public void testDeleteBoard() {
		System.out.println("deleteBoard");
		Integer boardId = 0;
		boardService.deleteBoard(boardId);
	}

	/**
	 * Test of delForbedden method, of class BoardService.
	 */
	@Test
	public void testDelForbedden() {
		System.out.println("delForbedden");
		Integer fbnId = 2;
		boardService.delForbedden(fbnId);
	}

	/**
	 * Test of findBoards method, of class BoardService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindBoards() {
		System.out.println("findBoards");
		Integer parentId = null;
		String key = "";
		Integer start = 0;
		Integer limit = 10;
		List result = boardService.findBoards(parentId, key, start, limit);
		System.out.println(result.size());
	}

	/**
	 * Test of findBottomBoards method, of class BoardService.
	 */
	@Test
	public void testFindBottomBoards() {
		System.out.println("findBottomBoards");
		Integer start = 0;
		Integer limit = 10;
		List<Board> result = boardService.findBottomBoards(start, limit);
		System.out.println(result.size());
	}

	/**
	 * Test of findForbiddens method, of class BoardService.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindForbiddens() {
		System.out.println("findForbiddens");
		Integer boardId = 5;
		Integer start = 0;
		Integer limit = 10;
		List result = boardService.findForbiddens(boardId, start, limit);
		System.out.println(result.size());
	}



	/**
	 * Test of findHotUsersForIndex method, of class BoardService.
	 */
	@Test
	public void testFindHotUsersForIndex() {
		System.out.println("findHotUsersForIndex");
		List<ViewHotUser> result = boardService.findHotUsersForIndex();
		System.out.println(result.size());
	}

	/**
	 * Test of findTopBoards method, of class BoardService.
	 */
	@Test
	public void testFindTopBoards() {
		System.out.println("findTopBoards");
		Integer start = 0;
		Integer limit = 10;
		List<Board> result = boardService.findTopBoards(start, limit);
		System.out.println(result.size());

	}

	/**
	 * Test of getBoard method, of class BoardService.
	 */
	@Test
	public void testGetBoard() {
		System.out.println("getBoard");
		Integer boardId = 5;
		Board result = boardService.getBoard(boardId);
		Assert.assertTrue(result != null);

	}

	/**
	 * Test of getBoardCnt method, of class BoardService.
	 */
	@Test
	public void testGetBoardCnt() {
		System.out.println("getBoardCnt");
		Integer parentId = null;
		String key = "";
		int result = boardService.getBoardCnt(parentId, key);
		System.out.println(result);

	}

	/**
	 * Test of getBoardsCntByParent method, of class BoardService.
	 */
	@Test
	public void testGetBoardsCntByParent() {
		System.out.println("getBoardsCntByParent");
		Integer boardParent = 5;
		int expResult = 0;
		int result = boardService.getBoardsCntByParent(boardParent);
		assertEquals(expResult, result);
	}

	/**
	 * Test of getForbidden method, of class BoardService.
	 */
	@Test
	public void testGetForbidden() {
		System.out.println("getForbidden");
		Integer fbnId = 1000;
		Forbidden expResult = null;
		Forbidden result = boardService.getForbidden(fbnId);
		assertEquals(expResult, result);

	}

	/**
	 * Test of getForbiddenCnt method, of class BoardService.
	 */
	@Test
	public void testGetForbiddenCnt() {
		System.out.println("getForbiddenCnt");
		Integer boardId = 5;
		int result = boardService.getForbiddenCnt(boardId);
		System.out.println(result);
	}



	/**
	 * Test of getTopBoardsCnt method, of class BoardService.
	 */
	@Test
	public void testGetTopBoardsCnt() {
		System.out.println("getTopBoardsCnt");
		Integer result = boardService.getTopBoardsCnt();
		System.out.println(result);
	}

	/**
	 * Test of getUserBoard method, of class BoardService.
	 */
	@Test
	public void testGetUserBoard_int() {
		System.out.println("getUserBoard");
		int id = 0;
		Userboard expResult = null;
		Userboard result = boardService.getUserBoard(id);
		assertEquals(expResult, result);

	}

	/**
	 * Test of getUserBoard method, of class BoardService.
	 */
	@Test
	public void testGetUserBoard_Integer_Integer() {
		System.out.println("getUserBoard");
		Integer userId = 111;
		Integer boardId = 5;
		Userboard expResult = null;
		Userboard result = boardService.getUserBoard(userId, boardId);
		assertEquals(expResult, result);
	}

	/**
	 * Test of isBM method, of class BoardService.
	 */
	@Test
	public void testIsBM() {
		System.out.println("isBM");
		String username = "gladstone999999_dummy";
		Board board = this.boardService.getBoard(5);
		boolean expResult = false;
		boolean result = boardService.isBM(username, board);
		assertEquals(expResult, result);
	}

	/**
	 * Test of isForbiddened method, of class BoardService.
	 */
	@Test
	public void testIsForbiddened() {
		System.out.println("isForbiddened");
		Integer boardId = null;
		Integer uid = null;
		String username = "";

		boolean expResult = false;
		boolean result = boardService.isForbiddened(boardId, uid, username);
		assertEquals(expResult, result);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testListBoards() {
		List boards = boardService.findBoards(null, null, 0, 1000);
		System.out.println(boards.size());
	}

	@Test
	public void testListTopBoards() {
		List<Board> boards = boardService.findTopBoards(0, 100);
		System.out.println(boards.size());
	}

	/**
	 * Test of newBoradManager method, of class BoardService.
	 */
	@Test
	public void testNewBoradManager() {
		System.out.println("newBoradManager");
		String userName = "gladstone";
		int boardId = 5;
		boardService.newBoradManager(userName, boardId);

	}

	/**
	 * Test of saveBoard method, of class BoardService.
	 */
	@Test
	public void testSaveBoard() {
		System.out.println("saveBoard");
		Board model = new Board();
		model.setBoardCode("d");
		model.setBoardInfo("test");
		model.setBoardLevel(2);
		model.setBoardMaster1("gladstone");
		model.setBoardName("test");
		model.setBoardTodayNum(0);
		model.setBoardTopicNum(0);
		model.setBoardType(1);
		model.setBoardState(227);
		model.setBoardReNum(0);
		model.setBoardNewTopic(0L);
		model.setBoardRecom(0);
		model.setBoardOption(0);
		model.setBoardParent(0);
		boolean update = false;
		boardService.saveBoard(model, update);

	}

	/**
	 * Test of saveForbedden method, of class BoardService.
	 */
	@Test
	public void testSaveForbedden() {
		System.out.println("saveForbedden");
		Forbidden f = new Forbidden();
		f.setFbnUser("gladstone");
		f.setFbnBm("gladstone");
		f.setFbnBoardId(5);
		f.setFbnDays(20);
		f.setFbnReason("fuck");
		this.boardService.saveForbedden(f);
	}

	/**
	 * Test of saveUserBoard method, of class BoardService.
	 */
	@Test
	public void testSaveUserBoard() {
		System.out.println("saveUserBoard");
		Userboard model = new Userboard();
		User u = this.userService.getUser(null, "gladstone");
		boolean update = false;
		model.setBoard_id(5);
		model.setRole(2);
		model.setTopic_cnt(10);
		model.setUser_id(u.getUserId());
		model.setCreate_time(StringUtils.getTimeStrByNow());
		boardService.saveUserBoard(model, update);
	}
}