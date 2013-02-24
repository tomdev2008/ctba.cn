package org.net9.core.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.common.web.filter.BaseFilter;
import org.net9.core.constant.WebConstants;
import org.net9.core.service.ServiceModule;
import org.net9.core.util.HttpUtils;
import org.net9.search.lucene.search.ref.LuceneBlogEntryReferenceSearcher;
import org.net9.search.lucene.search.ref.LuceneGroupTopicReferenceSearcher;
import org.net9.search.lucene.search.ref.LuceneTopicReferenceSearcher;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * #727 针对搜索引擎来源用户的提示
 * 
 * @author gladstone
 * 
 */
public class SearchEngineHintFilter extends BaseFilter {

	public SearchEngineHintFilter() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	/** Logger */
	private static Log log = LogFactory.getLog(SearchEngineHintFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (!this.isStaticFileStr(HttpUtils.getURL(httpRequest))) {
			String searchKey = HttpUtils.getSearchKey(httpRequest);
			if (StringUtils.isNotEmpty(searchKey)) {
				log.info("Got searchkey: " + searchKey+" |"+HttpUtils.getURL(httpRequest));
				httpRequest.setAttribute(WebConstants.REQUEST_SEARCH_KEYWORD,
						searchKey);
				List<Map<String, String>> refTopics = doSearch(searchKey);
				httpRequest.setAttribute(WebConstants.REQUEST_SEARCH_RESULT,
						refTopics);
			}
		}

		try {
			chain.doFilter(request, response);
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (ServletException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/** 论坛文章搜索器 */
	@Inject
	private LuceneTopicReferenceSearcher topicSearcher;
	/** 小组文章搜索器 */
	@Inject
	private LuceneGroupTopicReferenceSearcher groupTopicSearcher;
	/** 博客文章搜索器 */
	@Inject
	private LuceneBlogEntryReferenceSearcher blogSearcher;

	/** 最大的结果条数 */
	private static final int RESULT_MAX_CNT = 6;

	/**
	 * 根据取得的关键词搜索相关文章，返回结果
	 * 
	 * @param searchKey
	 * @return
	 */
	public List<Map<String, String>> doSearch(String searchKey) {
		List<Map<String, String>> reval = new ArrayList<Map<String, String>>();

		try {
			
			List<Map<String, String>> groupTopics = groupTopicSearcher
			.searchByKey(searchKey, null);
			reval.addAll(groupTopics);
			log.debug("With groupTopics:"+reval.size());
			if (reval.size() < RESULT_MAX_CNT) {
				List<Map<String, String>> topics = topicSearcher.searchByKey(
						searchKey, null);
				reval.addAll(topics);
				log.debug("With topics:"+reval.size());
				
			}
			if (reval.size() < RESULT_MAX_CNT) {
				List<Map<String, String>> blogEntries = blogSearcher
						.searchByKey(searchKey, null);
				reval.addAll(blogEntries);
				log.debug("With blogEntries:"+reval.size());
			}
			while(reval.size()>RESULT_MAX_CNT){
				reval.remove(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return reval;
	}
}
