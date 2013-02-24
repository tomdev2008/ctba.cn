package org.net9.blog.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.service.ServiceModule;
import org.net9.domain.model.blog.Blog;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.view.BlogEntryMonthly;
import org.net9.test.TestBase;

import com.google.inject.Guice;

public class EntryServiceTest extends TestBase {

	EntryService entryService;
	BlogService blogService;

	@Before
	public void setUp() throws Exception {
		entryService = Guice.createInjector(new ServiceModule()).getInstance(
				EntryService.class);
		blogService = Guice.createInjector(new ServiceModule()).getInstance(
						BlogService.class);
	}

	/**
	 * Test of delEntry method, of class EntryService.
	 */
	@Test
	public void testDelEntry() {
		System.out.println("delEntry");
		List<BlogEntry> list = entryService.findNewEntries(null, null, 0,
				1);
		Assert.assertTrue(list.size()>0);
		BlogEntry entry = list.get(0);
		entryService.delEntry(entry);

	}

	/**
	 * Test of findEntries method, of class EntryService.
	 */
	@Test
	public void testFindEntries() {
		System.out.println("findEntries");
		Integer blogId = 9;
		Integer categoryId = null;
		Integer type = null;
		Integer start = 0;
		Integer limit = 12;

		List<BlogEntry> result = entryService.findEntries(blogId, categoryId,
				type, start, limit);
		System.out.println(result);
	}

	@Test
	public void testFindEntriesByMonth() {
		List<BlogEntry> list = entryService.findEntriesByMonth(9, "2008-10", 1,
				1000);
		for (BlogEntry e : list) {
			System.out.println(">" + e.getTitle());
		}
	}

	/**
	 * Test of findEntriesByMonth method, of class EntryService.
	 */
	@Test
	public void testFindEntriesByMonth_4args() {
		System.out.println("findEntriesByMonth");
		Integer blogId = 9;
		String month = "";
		Integer start = 0;
		Integer limit = 12;

		List<BlogEntry> result = entryService.findEntriesByMonth(blogId, month,
				start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findHotEntries method, of class EntryService.
	 */
	@Test
	public void testFindHotEntries() {
		System.out.println("findHotEntries");
		String key = "";
		Integer start = 0;
		Integer limit = 12;
		List<BlogEntry> result = entryService.findHotEntries(key, null, start,
				limit);
		System.out.println(result);
	}

	@Test
	public void testFindMonthlyEntries() {
		List<BlogEntryMonthly> list = entryService.findMonthlyEntries(null, 9,
				1, 1000);
		for (BlogEntryMonthly e : list) {
			System.out.println(">" + e.getMonth() + ":" + e.getCnt());
		}
	}

	/**
	 * Test of findMonthlyEntries method, of class EntryService.
	 */
	@Test
	public void testFindMonthlyEntries_4args() {
		System.out.println("findMonthlyEntries");
		String beforeMonth = "";
		Integer blogId = 9;
		Integer start = 0;
		Integer limit = 12;
		List<BlogEntryMonthly> result = entryService.findMonthlyEntries(
				beforeMonth, blogId, start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findMostCommentedEntries method, of class EntryService.
	 */
	@Test
	public void testFindMostCommentedEntries() {
		System.out.println("findMostCommentedEntries");
		Integer type = null;
		Integer start = 0;
		Integer limit = 12;

		List<BlogEntry> result = entryService.findMostCommentedEntries(type,
				start, limit);
		System.out.println(result);

	}

	/**
	 * Test of findNewEntries method, of class EntryService.
	 */
	@Test
	public void testFindNewEntries() {
		System.out.println("findNewEntries");
		String key = "";
		Integer type = null;
		Integer start = 0;
		Integer limit = 12;
		List<BlogEntry> result = entryService.findNewEntries(key, type, start,
				limit);
		System.out.println(result);
	}

	/**
	 * Test of getEntriesCnt method, of class EntryService.
	 */
	@Test
	public void testGetEntriesCnt() {
		System.out.println("getEntriesCnt");
		Integer blogId = null;
		Integer categoryId = null;
		Integer type = null;

		Integer result = entryService.getEntriesCnt(blogId, categoryId, type);
		System.out.println(result);

	}

	/**
	 * Test of getEntriesCntByMonth method, of class EntryService.
	 */
	@Test
	public void testGetEntriesCntByMonth() {
		System.out.println("getEntriesCntByMonth");
		Integer blogId = null;
		String month = "";

		Integer result = entryService.getEntriesCntByMonth(blogId, month);
		System.out.println(result);

	}

	/**
	 * Test of getEntriesCntWithTitle method, of class EntryService.
	 */
	@Test
	public void testGetEntriesCntWithTitle() {
		System.out.println("getEntriesCntWithTitle");
		String title = "";
		String body = "";
		Integer type = null;
		Integer result = entryService.getEntriesCntWithTitle(title, body, type);
		System.out.println(result);

	}

	/**
	 * Test of getEntry method, of class EntryService.
	 */
	@Test
	public void testGetEntry() {
		System.out.println("getEntry");
		Integer id = 111;
		BlogEntry result = entryService.getEntry(id);
	System.out.println(result);

	}

	/**
	 * Test of getEntryByPermalink method, of class EntryService.
	 */
	@Test
	public void testGetEntryByPermalink() {
		System.out.println("getEntryByPermalink");
		String permalink = "test_url";
		BlogEntry result = entryService.getEntryByPermalink(permalink);
		System.out.println(result);
	}

	/**
	 * Test of getEntryByTitle method, of class EntryService.
	 */
	@Test
	public void testGetEntryByTitle() {
		System.out.println("getEntryByTitle");
		String title = "gladstone";
		BlogEntry result = entryService.getEntryByTitle(title);
		System.out.println(result);

	}

	/**
	 * Test of getNewestEntry method, of class EntryService.
	 */
	@Test
	public void testGetNewestEntry() {
		System.out.println("getNewestEntry");
		Integer bid = null;
		Integer type = null;
		BlogEntry result = entryService.getNewestEntry(bid, type);
		System.out.println(result);

	}

	/**
	 * Test of getNextEntry method, of class EntryService.
	 */
	@Test
	public void testGetNextEntry() {
		System.out.println("getNextEntry");
		List<BlogEntry> list = entryService.findNewEntries(null, null, 0,
				1);
		Assert.assertTrue(list.size()>0);
		BlogEntry entry = list.get(0);
		BlogEntry result = entryService.getNextEntry(entry);
		System.out.println(result);
	}

	/**
	 * Test of getPrevEntry method, of class EntryService.
	 */
	@Test
	public void testGetPrevEntry() {
		System.out.println("getPrevEntry");
		List<BlogEntry> list = entryService.findNewEntries(null, null, 0,
				1);
		Assert.assertTrue(list.size()>0);
		BlogEntry entry = list.get(0);
		BlogEntry result = entryService.getPrevEntry(entry);
		System.out.println(result);
	}

	/**
	 * Test of newEntry method, of class EntryService.
	 */
	@Test
	public void testNewEntry() {
		System.out.println("newEntry");
		Blog b = this.blogService.findBlogs(0, 1).get(0);
		BlogEntry model = new BlogEntry();
		model.setAuthor("gladstone");
		model.setBlogBlog(this.blogService.getBlog(9));
		model.setBody("test_body");
		model.setCategoryId(100);
		model.setCommentCnt(0);
		model.setCommentsEnabled(Short.valueOf("0"));
		model.setDate("2009-09-09");
		model.setHits(0);
		model.setTitle("title");
		model.setType(0);
		model.setSubtitle("sub");
		model.setState(0);
		model.setBlogBlog(b);
		entryService.newEntry(model);
	}

	/**
	 * Test of updateEntry method, of class EntryService.
	 */
	@Test
	public void testUpdateEntry() {
		System.out.println("updateEntry");
		List<BlogEntry> list = entryService.findNewEntries(null, null, 0,
				1);
		Assert.assertTrue(list.size()>0);
		BlogEntry model = list.get(0);
		model.setAuthor("gladstone");
		entryService.updateEntry(model);

	}

}
