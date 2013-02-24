package org.net9.search.lucene.search;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.search.lucene.index.IndexBuilderFactory;
import org.net9.test.TestBase;

import com.j2bb.common.search.search.SearchEngine;
import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

public class BlogSearchEngineTest extends TestBase {

	@Before
	public void setUp() throws Exception {
		IndexBuilderFactory.createBlogIndexBuilder(
				new File("TestRoot/test_index/blog/")).createIndex();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSerachSearchRequest() {
		Map m = new HashMap();
		SearchRequest request = new SearchRequest();
		request.setLimit(100);
		request.setReverse(false);
		request.setSearchContext(m);
		request.setStart(0);
		SearchEngine engine = SearchEngineFactory
				.createBlogSearchEngine("TestRoot/test_index/blog/");
		SearchResponse result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		assertTrue(result.getTotalCnt() > 0);
		m.put("title", "PC贴");
		request.setSearchContext(m);
		result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		for (Object o : result.getResults()) {
			BlogEntry e = (BlogEntry) o;
			System.out.println(e.getTitle());
		}

	}

	/**
	 * #721 ([相关文章的改善]lucene有查询关键字解析错误)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFiltering() {
		Map m = new HashMap();
		SearchRequest request = new SearchRequest();
		request.setLimit(100);
		request.setReverse(false);
		request.setSearchContext(m);
		request.setStart(0);
		SearchEngine engine = SearchEngineFactory
				.createBlogSearchEngine("TestRoot/test_index/blog/");
		SearchResponse result = engine.search(request);

		m.put("title", "[讨论]PC贴ANTZ筒子请注意一下群里的发言！");
		request.setSearchContext(m);
		result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		for (Object o : result.getResults()) {
			BlogEntry e = (BlogEntry) o;
			System.out.println(e.getTitle());
		}

	}

}
