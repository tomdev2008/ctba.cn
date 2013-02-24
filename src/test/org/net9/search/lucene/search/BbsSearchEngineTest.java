package org.net9.search.lucene.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.net9.domain.model.bbs.Topic;
import org.net9.search.lucene.index.IndexBuilderFactory;
import org.net9.test.TestBase;

import com.j2bb.common.search.search.SearchEngine;
import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

public class BbsSearchEngineTest extends TestBase {

	 @Before
	public void setUp() throws Exception {
		IndexBuilderFactory.createBbsIndexBuilder(new File("TestRoot/test_index/bbs/"))
				.createIndex();
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
				.createBbsSearchEngine("TestRoot/test_index/bbs/");
		SearchResponse result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		assertTrue(result.getTotalCnt() > 0);
		m.put("topicAuthor", "gladstone");
		request.setSearchContext(m);
		result = engine.search(request);
		System.out.println(result.getTotalCnt());
		System.out.println(result.getResults().size());
		assertTrue(result.getTotalCnt() >= result.getResults().size());
		for (Object o : result.getResults()) {
			Topic e = (Topic) o;
			System.out.println(e.getTopicAuthor());
			assertEquals("gladstone", e.getTopicAuthor());
		}
	}

}
