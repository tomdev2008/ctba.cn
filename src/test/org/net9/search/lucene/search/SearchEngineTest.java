package org.net9.search.lucene.search;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.net9.core.constant.WebConstants;
import org.net9.domain.model.bbs.Topic;
import org.net9.search.lucene.index.DummyTopicIndexBuilder;
import org.net9.test.TestBase;

import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

public class SearchEngineTest extends TestBase {



	@Before
	public void setUp() throws Exception {
		DummyTopicIndexBuilder indexBuilder = new DummyTopicIndexBuilder(new File(
		"TestRoot/index/"));
		indexBuilder.createIndex();
	}

	@SuppressWarnings("unchecked")
	 @Test
	public void testFindAllDocuments() {
		List list = new BbsSearchEngine("TestRoot/index/").find (Topic.class,
				0, 100);
		System.out.println(list.size());
		Assert.assertTrue(list.size() > 0);

	}

	 @Test
	public void testSearch() {
		SearchRequest searchRequest = new SearchRequest();
		// 得到参数，并设置查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("topicTitle", "test1");
		// searchMap.put("topicContent", query);
		searchRequest.setSearchContext(searchMap);
		searchRequest.setStart(0);
		searchRequest.setLimit(WebConstants.PAGE_SIZE_30);
		SearchResponse response = new BbsSearchEngine("TestRoot/index/")
				.search(searchRequest);
		System.out.println(response.getTotalCnt());
		Assert.assertTrue(response.getResults().size() > 0);

		// ---------------------------
		searchRequest = new SearchRequest();
		// 得到参数，并设置查询条件
		searchMap = new HashMap<String, Object>();
		// TODO:自动绑定字段？
		searchMap.put("topicTitle", "站内");
		searchMap.put("topicContent", "工口相谈21");
		searchRequest.setSearchContext(searchMap);
		searchRequest.setStart(0);
		searchRequest.setLimit(WebConstants.PAGE_SIZE_30);
		response = new BbsSearchEngine("TestRoot/index/").search(searchRequest);
		System.out.println(response.getTotalCnt());
		Assert.assertTrue(response.getResults().size() > 0);
	}

}
