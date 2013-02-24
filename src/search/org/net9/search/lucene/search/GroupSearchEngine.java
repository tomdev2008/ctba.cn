package org.net9.search.lucene.search;

import org.net9.domain.model.group.GroupTopic;

import com.j2bb.common.search.search.AbstractSearchEngine;

public class GroupSearchEngine extends AbstractSearchEngine implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public GroupSearchEngine(String indexPath) {
		super(indexPath);
	}

	@Override
	public com.j2bb.common.search.search.SearchResponse search(
			com.j2bb.common.search.search.SearchRequest searchRequest) {
		return this.serach(searchRequest, GroupTopic.class);
	}
}
