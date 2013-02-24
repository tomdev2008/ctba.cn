package org.net9.search.lucene.search;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.net9.domain.model.news.NewsEntry;
import org.net9.search.lucene.index.IndexBuilderFactory;
import org.net9.test.TestBase;

import com.j2bb.common.search.search.SearchEngine;
import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

public class NewsSearchEngineTest extends TestBase {

	 @Before
	public void setUp() throws Exception {
		IndexBuilderFactory.createNewsIndexBuilder(new File("TestRoot/test_index/news/"))
				.createIndex();
	}

	@SuppressWarnings("unchecked")
	 @Test
	public void testMultiSearchRequest() {
		Map m = new HashMap();
		SearchRequest request = new SearchRequest();
		request.setLimit(100);
		request.setReverse(false);
		request.setStart(0);
		SearchEngine engine = SearchEngineFactory
				.createNewsSearchEngine("TestRoot/test_index/news/");
		m.put("hits", 49);
		m.put("author", "扯谈社");
		m.put("title", "小艾");
		request.setSearchContext(m);

		SearchResponse result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		for (Object o : result.getResults()) {
			NewsEntry e = (NewsEntry) o;
			System.out.println(e.getTitle());
		}
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
				.createNewsSearchEngine("TestRoot/test_index/news/");
		SearchResponse result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
//		assertTrue(result.getTotalCnt() > 0);
		m.put("hits", 25);
		request.setSearchContext(m);
		result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		for (Object o : result.getResults()) {
			NewsEntry e = (NewsEntry) o;
			System.out.println(e.getTitle());
		}
	}

}
