package org.net9.search.lucene.search.ref;

import java.util.List;
import java.util.Map;

/**
 * 关联搜索器
 * 
 * @author gladstone
 * @since 2008-10-27
 */
public interface ReferenceSearcher {

	public String getCacheKey();

	public List<Map<String, String>> searchByKey(String searchKey, String ignore);

}
