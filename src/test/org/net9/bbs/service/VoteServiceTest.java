package org.net9.bbs.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.common.util.DateUtils;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.ctba.VoteAnswer;
import org.net9.domain.model.ctba.VoteComment;
import org.net9.test.TestBase;
import org.net9.vote.service.VoteService;

import com.google.inject.Guice;

public class VoteServiceTest extends TestBase {

	VoteService instance;

	@Before
	public void setUp() throws Exception {
		instance = Guice.createInjector(new ServiceModule()).getInstance(
				VoteService.class);
	}

	/**
	 * Test of delVote method, of class VoteService.
	 */
	@Test
	public void testDelVote() {
		System.out.println("delVote");
		Vote v = instance.getLastVote(null);
		instance.delVote(v);

	}

	/**
	 * Test of delVoteAnswer method, of class VoteService.
	 */
	@Test
	public void testDelVoteAnswer() {
		System.out.println("delVoteAnswer");
		Vote v = instance.getLastVote(null);
		VoteAnswer model = new VoteAnswer();
		model.setAnswers("ddd");
		model.setCreateTime("2009-00-00");
		model.setOtherText("test");
		model.setVoteId(v.getId());
		model.setUsername("gladstone");
		instance.saveVoteAnswer(model);
		List<VoteAnswer> modelList = instance.findVoteAnswers(v.getId(), null, 0, 1);
		instance.delVoteAnswer(modelList.get(0));

	}

	/**
	 * Test of delVoteComment method, of class VoteService.
	 */
	@Test
	public void testDelVoteComment() {
		System.out.println("delVoteComment");
		Vote v = instance.getLastVote(null);
		VoteComment comment = new VoteComment();
		comment.setBody("fuck");
		comment.setIp("192.168.0.0");
		comment.setUpdateTime(DateUtils.getNow());
		comment.setUsername("gladstone");
		comment.setVote(v);
		instance.saveVoteComment(comment);
		List<VoteComment> modelList = instance.findVoteComments(v.getId(), null, 0, 1);
		instance.delVoteComment(modelList.get(0));

	}

	/**
	 * Test of findHotVotes method, of class VoteService.
	 */
	@Test
	public void testFindHotVotes() {
		System.out.println("findHotVotes");
		Integer boardId = null;
		Integer type = null;
		String date = "";
		Integer start = 0;
		Integer limit = 11;
		List<Vote> result = instance.findHotVotes(boardId, type, date, start,
				limit);
		System.out.println(result);
	}

	/**
	 * Test of findVoteAnswers method, of class VoteService.
	 */
	@Test
	public void testFindVoteAnswers() {
		System.out.println("findVoteAnswers");
		Integer voteId = 11;
		String username = "";
		Integer start = 0;
		Integer limit = 11;
		List<VoteAnswer> result = instance.findVoteAnswers(voteId, username,
				start, limit);
		System.out.println(result);
	}

	@Test
	public void testFindVoteComments() {
		List<VoteAnswer> result = instance.findVoteAnswers(1, null, 0, 1);
		System.out.println(result);
	}

	/**
	 * Test of findVotes method, of class VoteService.
	 */
	@Test
	public void testFindVotes() {
		System.out.println("findVotes");
		Integer boardId = 5;
		String username = "";
		Integer type = null;
		String date = "";
		Integer start = 0;
		Integer limit = 11;
		List<Vote> result = instance.findVotes(boardId, username, type, date,
				start, limit);
		System.out.println(result);
	}

	/**
	 * Test of getLastVote method, of class VoteService.
	 */
	@Test
	public void testGetLastVote() {
		System.out.println("getLastVote");
		Integer boardId = 5;
		Vote result = instance.getLastVote(boardId);
		System.out.println(result);
	}

	/**
	 * Test of getVote method, of class VoteService.
	 */
	@Test
	public void testGetVote() {
		System.out.println("getVote");
		Integer id = 11;
		Vote result = instance.getVote(id);
		System.out.println(result);
	}

	/**
	 * Test of getVoteAnswer method, of class VoteService.
	 */
	@Test
	public void testGetVoteAnswer() {
		System.out.println("getVoteAnswer");
		Integer id = 111;
		VoteAnswer result = instance.getVoteAnswer(id);
		System.out.println(result);
	}

	/**
	 * Test of getVoteComment method, of class VoteService.
	 */
	@Test
	public void testGetVoteComment() {
		System.out.println("getVoteComment");
		Integer id = 111;
		VoteComment result = instance.getVoteComment(id);
		System.out.println(result);
	}

	/**
	 * Test of getVotesAnswerCnt method, of class VoteService.
	 */
	@Test
	public void testGetVotesAnswerCnt() {
		System.out.println("getVotesAnswerCnt");
		Integer voteId = 111;
		String username = "gladstone";
		Integer result = instance.getVotesAnswerCnt(voteId, username);
		System.out.println(result);

	}

	/**
	 * Test of getVotesCnt method, of class VoteService.
	 */
	@Test
	public void testGetVotesCnt() {
		System.out.println("getVotesCnt");
		Integer boardId = null;
		String username = "";
		Integer type = null;
		Integer result = instance.getVotesCnt(boardId, username, type);
		System.out.println(result);
	}

	/**
	 * Test of getVotesCommentCnt method, of class VoteService.
	 */
	@Test
	public void testGetVotesCommentCnt() {
		System.out.println("getVotesCommentCnt");
		Integer voteId = 11;
		String username = "";
		Integer result = instance.getVotesCommentCnt(voteId, username);
		System.out.println(result);
	}

	/**
	 * Test of saveVote method, of class VoteService.
	 */
	@Test
	public void testSaveVote() {
		System.out.println("saveVote");
		Vote model = new Vote();
		model.setAnswers("tst");
		model.setBoardId(5);
		model.setCanBeOther(1);
		model.setHits(0);
		model.setMaxNum(2);
		model.setMemo("test");
		model.setOverTime("2009-00-00");
		model.setUsername("gladstone");
		model.setUpdateTime("2009-00-00");
		model.setType(1);
		model.setQuestion("test_question");
		instance.saveVote(model);

	}

	/**
	 * Test of saveVoteAnswer method, of class VoteService.
	 */
	@Test
	public void testSaveVoteAnswer() {
		System.out.println("saveVoteAnswer");
		Vote v = instance.getLastVote(null);
		if (v != null) {
			VoteAnswer model = new VoteAnswer();
			model.setAnswers("ddd");
			model.setCreateTime("2009-00-00");
			model.setOtherText("test");
			model.setVoteId(v.getId());
			model.setUsername("gladstone");
			instance.saveVoteAnswer(model);
		}

	}

	@Test
	public void testSaveVoteComment() {
		Vote v = instance.getLastVote(null);
		if (v != null) {
			VoteComment comment = new VoteComment();
			comment.setBody("fuck");
			comment.setIp("192.168.0.0");
			comment.setUpdateTime(DateUtils.getNow());
			comment.setUsername("gladstone");
			comment.setVote(v);
			instance.saveVoteComment(comment);
			List<VoteComment> cList = instance.findVoteComments(v.getId(),
					null, 0, 1);
			Assert.assertTrue(cList.size() > 0);
			instance.delVoteComment(cList.get(0));
		}
	}

}
