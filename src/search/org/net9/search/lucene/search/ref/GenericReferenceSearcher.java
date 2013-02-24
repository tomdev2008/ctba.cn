package org.net9.search.lucene.search.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.net9.common.cache.CacheManager;
import org.net9.common.cache.provider.CacheProviderFactory;
import org.net9.common.exception.CacheException;
import org.net9.core.constant.SystemConfigVars;

import com.j2bb.common.search.search.AbstractSearchEngine;
import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

public abstract class GenericReferenceSearcher extends AbstractSearchEngine
		implements ReferenceSearcher, java.io.Serializable {

	public static final int MAX_COUNT = 15;

	private static Log log = LogFactory.getLog(GenericReferenceSearcher.class);

	private boolean usePaoding = true;

	public GenericReferenceSearcher(String indexPath) {
		super(indexPath);
	}

	abstract public String getCacheKey();

	@Override
	public SearchResponse search(SearchRequest searchRequest) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> searchByKey(String searchKey, String ignore) {
		searchKey = com.j2bb.common.search.util.StringUtils
				.filteringLuceneSearchStr(searchKey);
		if (usePaoding) {
			return searchByKeyWithPaoding(searchKey, ignore);
		} else {
			return searchByKeyWithLucene(searchKey, ignore);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchByKeyWithLucene(String searchKey,
			String ignore) {
		List<Map<String, String>> list = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance(getCacheKey());
			if (!SystemConfigVars.DB_CACHE_USE_MEMCACHE) {
				cacheManager.setCacheProvider(CacheProviderFactory
						.getWhirlycacheProvider());
			}
			list = (List<Map<String, String>>) cacheManager.get(searchKey);
		} catch (CacheException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
			List<String> tIndex = new ArrayList<String>();
			list.addAll(this.searchBySingleWord(searchKey, tIndex, ignore));
			try {
				CacheManager.getInstance(getCacheKey()).put(searchKey, list);
			} catch (CacheException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> searchByKeyWithPaoding(String searchKey,
			String ignore) {
		List<Map<String, String>> list = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance(getCacheKey());
			if (!SystemConfigVars.DB_CACHE_USE_MEMCACHE) {
				cacheManager.setCacheProvider(CacheProviderFactory
						.getWhirlycacheProvider());
			}
			list = (List<Map<String, String>>) cacheManager.get(searchKey);
		} catch (CacheException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
			Analyzer analyzer = new PaodingAnalyzer();
			QueryParser parser = new QueryParser("", analyzer);
			List<String> tIndex = new ArrayList<String>();
			Query query;
			try {
				query = parser.parse(searchKey);
				if (query.toString().indexOf(" ") > 0) {
					String words[] = query.toString().substring(1,
							query.toString().length() - 1).split(" ");
					log.debug("got words: " + words.length);
					for (String w : words) {
						log.debug(w);
						list.addAll(this.searchBySingleWord(w, tIndex, ignore));
						if (list.size() > MAX_COUNT) {
							break;
						}
					}
				} else {
					log.debug(query);
					list.addAll(this.searchBySingleWord(query.toString(), null,
							ignore));
				}
				while (list.size() > MAX_COUNT) {
					list.remove(list.size() - 1);
				}
				CacheManager.getInstance(getCacheKey()).put(searchKey, list);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return list;
	}

	abstract protected List<Map<String, String>> searchBySingleWord(
			String searchKey, List<String> currTopicIndex, String ignore);

	public void setUsePaoding(boolean usePaoding) {
		this.usePaoding = usePaoding;
	}
}
