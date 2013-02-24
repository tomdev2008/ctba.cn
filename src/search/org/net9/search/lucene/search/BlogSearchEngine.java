package org.net9.search.lucene.search;

import org.net9.domain.model.blog.BlogEntry;

import com.j2bb.common.search.search.AbstractSearchEngine;

public class BlogSearchEngine extends AbstractSearchEngine implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public BlogSearchEngine(String indexPath) {
		super(indexPath);
	}

	@Override
	public com.j2bb.common.search.search.SearchResponse search(
			com.j2bb.common.search.search.SearchRequest searchRequest) {
		return this.serach(searchRequest, BlogEntry.class);
	}
}
