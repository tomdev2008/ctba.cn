package org.net9.search.lucene.search;

import com.j2bb.common.search.search.SearchEngine;

/**
 * 搜索引擎工厂，负责生成搜索引擎
 * 
 * @author gladstone
 * @since 2008-8-17
 */
public class SearchEngineFactory {
	public static SearchEngine createBbsSearchEngine(String indexDir) {
		return new BbsSearchEngine(indexDir);
	}

	public static SearchEngine createBlogSearchEngine(String indexDir) {
		return new BlogSearchEngine(indexDir);
	}

	public static SearchEngine createGroupSearchEngine(String indexDir) {
		return new GroupSearchEngine(indexDir);
	}

	public static SearchEngine createNewsSearchEngine(String indexDir) {
		return new NewsSearchEngine(indexDir);
	}
	public static SearchEngine createSubjectSearchEngine(String indexDir) {
		return new SubjectSearchEngine(indexDir);
	}
	
	
}
