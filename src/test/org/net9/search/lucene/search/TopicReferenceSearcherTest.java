package org.net9.search.lucene.search;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.net9.domain.model.bbs.Topic;
import org.net9.search.lucene.index.DummyTopicIndexBuilder;
import org.net9.search.lucene.search.ref.LuceneTopicReferenceSearcher;
import org.net9.test.TestBase;

public class TopicReferenceSearcherTest extends TestBase {

	LuceneTopicReferenceSearcher searcher = new LuceneTopicReferenceSearcher(
			"TestRoot/index/");

	DummyTopicIndexBuilder indexBuilder = new DummyTopicIndexBuilder(new File(
			"TestRoot/index/"));

	@Before
	public void setUp() throws Exception {
		indexBuilder.createIndex();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSearchByKey() {
		System.out.println(">>" + searcher.getCnt(Topic.class));
		List list = searcher.searchByKey("test*", null);
		System.out.println(">>" + list.size());
		// assertTrue(list.size() > 0);
		list = searcher.searchByKey("站内搜索", null);
		System.out.println(">>" + list.size());
		assertTrue(list.size() > 0);
		list = searcher.searchByKey("站内搜索", null);
		System.out.println(">>" + list.size());
		assertTrue(list.size() > 0);
	}
}
