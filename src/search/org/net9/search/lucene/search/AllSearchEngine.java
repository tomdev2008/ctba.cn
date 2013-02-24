package org.net9.search.lucene.search;

import java.util.ArrayList;
import java.util.List;

import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;

import com.j2bb.common.search.search.AbstractSearchEngine;
import com.j2bb.common.search.search.SearchResponse;

public class AllSearchEngine extends AbstractSearchEngine implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public AllSearchEngine(String indexPath) {
		super(indexPath);
	}

	@SuppressWarnings("unchecked")
	@Override
	public com.j2bb.common.search.search.SearchResponse search(
			com.j2bb.common.search.search.SearchRequest searchRequest) {
		SearchResponse allSearchResponse = new SearchResponse();
		SearchResponse bbsSearchResponse = this.serach(searchRequest,
				Topic.class);
		SearchResponse groupSearchResponse = this.serach(searchRequest,
				GroupTopic.class);
		SearchResponse blogSearchResponse = this.serach(searchRequest,
				BlogEntry.class);
		SearchResponse newsSearchResponse = this.serach(searchRequest,
				NewsEntry.class);

		Integer totalCnt = bbsSearchResponse.getTotalCnt()
				+ groupSearchResponse.getTotalCnt()
				+ blogSearchResponse.getTotalCnt()
				+ newsSearchResponse.getTotalCnt();

		List allList = new ArrayList();
		allList.addAll(bbsSearchResponse.getResults());
		allList.addAll(groupSearchResponse.getResults());
		allList.addAll(blogSearchResponse.getResults());
		allList.addAll(newsSearchResponse.getResults());
		allSearchResponse.setTotalCnt(totalCnt);
		return allSearchResponse;
	}
}
