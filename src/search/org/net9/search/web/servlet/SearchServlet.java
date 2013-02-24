package org.net9.search.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.WebConstants;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.search.lucene.SearchConst;
import org.net9.search.lucene.search.SearchEngineFactory;

import com.google.inject.Inject;
import com.j2bb.common.search.search.SearchEngine;
import com.j2bb.common.search.search.SearchRequest;

/**
 * 全站搜索，调用lucene搜索引擎
 * 
 * TODO: 搜索功能有待增强 TODO: 添加排序以及其他功能？
 * 
 * @author gladstone
 * @since 2008-8-17
 */
@SuppressWarnings("serial")
@WebModule("search")
@ReturnUrl(rederect = false, url = "/search.jsp")
public class SearchServlet extends BusinessCommonServlet {

	@Inject
	SearchReponseWrapper searchReponseWrapper;

	static Log log = LogFactory.getLog(SearchServlet.class);

	private final static int SEARCH_TYPE_BBS = 1;

	private final static int SEARCH_TYPE_BLOG = 2;

	private final static int SEARCH_TYPE_NEWS = 3;

	private final static int SEARCH_TYPE_GROUP = 4;

	private final static int SEARCH_TYPE_SUBJECT = 5;

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (HttpUtils.isMethodGet(request)) {
			SearchRequest searchRequest = (SearchRequest) request.getSession()
					.getAttribute("searchRequest");
			if (searchRequest != null) {
				log.debug("get query from session ");
				this.doSearch(request, (RichSearchRequest) searchRequest);
			}
		} else {
			this.doSearch(request, null);
		}

	}

	private void doSearch(HttpServletRequest request,
			RichSearchRequest searchRequest) throws ServletException,
			IOException {
		String searchType = request.getParameter("searchType");
		if (StringUtils.isEmpty(searchType) || !StringUtils.isNum(searchType)) {
			searchType = String.valueOf(SEARCH_TYPE_BBS);
		}
		log.info("searchType:" + searchType);
		if (searchRequest == null) {
			searchRequest = new RichSearchRequest();
			// 得到参数，并设置查询条件
			Map<String, Object> searchMap = new HashMap<String, Object>();
			// TODO:自动绑定字段？
			if (StringUtils.isNotEmpty(request.getParameter("title"))) {
				searchMap.put("title", request.getParameter("title"));
				searchMap.put("topicTitle", request.getParameter("title"));
			}
			if (StringUtils.isNotEmpty(request.getParameter("author"))) {
				searchMap.put("author", request.getParameter("author"));
				searchMap.put("topicAuthor", request.getParameter("author"));
			}

			searchRequest.setSearchContext(searchMap);
			searchRequest.setSortField(request.getParameter("sort"));
		}
		int start = HttpUtils.getStartParameter(request);

		searchRequest.setStart(start);
		searchRequest.setLimit(WebConstants.PAGE_SIZE_30);

		// 查询条件存入session
		request.getSession().setAttribute("searchRequest", searchRequest);

		SearchEngine searchEngine = null;
		switch (Integer.valueOf(searchType)) {
		case SEARCH_TYPE_BBS:
			searchEngine = SearchEngineFactory
					.createBbsSearchEngine(SearchConst.INDEXPATH_TOPIC);
			break;
		case SEARCH_TYPE_BLOG:
			searchEngine = SearchEngineFactory
					.createBlogSearchEngine(SearchConst.INDEXPATH_BLOG);
			break;
		case SEARCH_TYPE_GROUP:
			searchEngine = SearchEngineFactory
					.createGroupSearchEngine(SearchConst.INDEXPATH_GROUP);
			break;
		case SEARCH_TYPE_NEWS:
			searchEngine = SearchEngineFactory
					.createNewsSearchEngine(SearchConst.INDEXPATH_NEWS);
			break;
		case SEARCH_TYPE_SUBJECT:
			searchEngine = SearchEngineFactory
					.createSubjectSearchEngine(SearchConst.INDEXPATH_SUBJECT);
			break;
		}
		com.j2bb.common.search.search.SearchResponse searchResponse = searchEngine
				.search(searchRequest);
		request.setAttribute("models", searchReponseWrapper
				.wrapResponse(searchResponse));
		request.setAttribute("count", searchResponse.getTotalCnt());
		request.setAttribute("searchType", searchType);
		request.setAttribute("title", searchRequest.getSearchContext().get(
				"title"));
		request.setAttribute("author", searchRequest.getSearchContext().get(
				"author"));
	}
}
