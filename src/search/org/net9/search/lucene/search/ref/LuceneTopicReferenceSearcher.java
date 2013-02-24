package org.net9.search.lucene.search.ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.domain.model.bbs.Topic;
import org.net9.search.lucene.SearchConst;
import org.net9.search.web.servlet.SearchReponseWrapper;

import com.google.inject.servlet.SessionScoped;
import com.j2bb.common.search.search.SearchRequest;
import com.j2bb.common.search.search.SearchResponse;

@SessionScoped
public class LuceneTopicReferenceSearcher extends GenericReferenceSearcher {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory
			.getLog(LuceneTopicReferenceSearcher.class);

	public LuceneTopicReferenceSearcher() {
		super(SearchConst.INDEXPATH_TOPIC);
	}

	public LuceneTopicReferenceSearcher(String indexPath) {
		super(indexPath);
	}

	public String getCacheKey() {
		return "__ref_cache_topics__";
	}

	protected List<Map<String, String>> searchBySingleWord(String searchKey,
			List<String> currTopicIndex, String ignore) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			SearchRequest searchRequest = new SearchRequest();
			// 得到参数，并设置查询条件
			Map<String, Object> searchMap = new HashMap<String, Object>();
			// TODO:自动绑定字段？
			searchMap.put("topicTitle", searchKey);
			searchRequest.setSearchContext(searchMap);
			searchRequest.setSortField("topicId");
			searchRequest.setReverse(true);
			searchRequest.setLimit(MAX_COUNT);
			SearchResponse response = this.serach(searchRequest, Topic.class);
			if (response.getResults() == null) {
				log.debug("no response ");
				return list;
			}
			log.debug("response size: " + response.getResults().size());
			for (Object object : response.getResults()) {
				try {
					Map<String, String> map = new HashMap<String, String>();

					Topic topic = (Topic) object;
					if (("topic/" + topic.getTopicId()).equals(ignore)) {
						continue;
					}
					if (currTopicIndex != null) {
						if (!currTopicIndex.contains("topic/"
								+ topic.getTopicId())) {
							map.put(SearchReponseWrapper.URL, "topic/"
									+ topic.getTopicId());
							map.put(SearchReponseWrapper.LABEL, StringUtils
									.getShorterStr(topic.getTopicTitle(),
											SearchReponseWrapper.LABEL_LIMIT));
							list.add(map);
							currTopicIndex.add("topic/" + topic.getTopicId());
						}
					} else {
						map.put(SearchReponseWrapper.URL, "topic/"
								+ topic.getTopicId());
						map.put(SearchReponseWrapper.LABEL, StringUtils
								.getShorterStr(topic.getTopicTitle(),
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
