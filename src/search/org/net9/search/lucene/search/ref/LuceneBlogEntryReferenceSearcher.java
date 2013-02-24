package org.net9.search.lucene.search.ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.search.lucene.SearchConst;
import org.net9.search.web.servlet.SearchReponseWrapper;

import com.google.inject.servlet.SessionScoped;
import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

@SessionScoped
public class LuceneBlogEntryReferenceSearcher extends GenericReferenceSearcher {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory
			.getLog(LuceneBlogEntryReferenceSearcher.class);

	public LuceneBlogEntryReferenceSearcher() {
		super(SearchConst.INDEXPATH_BLOG);
	}

	public String getCacheKey() {
		return "__ref_cache_blog_entries__";
	}

	protected List<Map<String, String>> searchBySingleWord(String searchKey,
			List<String> currTopicIndex, String ignore) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			SearchRequest searchRequest = new SearchRequest();
			// 得到参数，并设置查询条件
			Map<String, Object> searchMap = new HashMap<String, Object>();
			// TODO:自动绑定字段？
			searchMap.put("title", searchKey);
			searchRequest.setSearchContext(searchMap);
			searchRequest.setLimit(MAX_COUNT);
			searchRequest.setSortField("id");
			searchRequest.setReverse(true);
			SearchResponse response = this.serach(searchRequest,
					BlogEntry.class);
			if (response.getResults() == null) {
				log.debug("no response ");
				return list;
			}
			log.debug("response size: " + response.getResults().size());
			for (Object object : response.getResults()) {
				try {
					Map<String, String> map = new HashMap<String, String>();
					BlogEntry topic = (BlogEntry) object;
					if (("blog/entry/" + topic.getId()).equals(ignore)) {
						continue;
					}
					if (currTopicIndex != null) {
						if (!currTopicIndex.contains("blog/entry/"
								+ topic.getId())) {
							map.put(SearchReponseWrapper.URL, "blog/entry/"
									+ topic.getId());
							map.put(SearchReponseWrapper.LABEL, StringUtils
									.getShorterStr(topic.getTitle(),
											SearchReponseWrapper.LABEL_LIMIT));
							list.add(map);
							currTopicIndex.add("blog/entry/" + topic.getId());
						}
					} else {
						map.put(SearchReponseWrapper.URL, "blog/entry/"
								+ topic.getId());
						map.put(SearchReponseWrapper.LABEL, StringUtils
								.getShorterStr(topic.getTitle(),
										SearchReponseWrapper.LABEL_LIMIT));
						list.add(map);
					}

				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			// e.printStackTrace();
		}
		return list;
	}
}
