package org.net9.search.lucene.search;

import org.net9.domain.model.news.NewsEntry;

import com.j2bb.common.search.search.AbstractSearchEngine;

public class NewsSearchEngine extends AbstractSearchEngine implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public NewsSearchEngine(String indexPath) {
		super(indexPath);
	}

	@Override
	public com.j2bb.common.search.search.SearchResponse search(
			com.j2bb.common.search.search.SearchRequest searchRequest) {
		return this.serach(searchRequest, NewsEntry.class);
	}
}
