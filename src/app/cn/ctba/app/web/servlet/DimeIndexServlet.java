package cn.ctba.app.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.net9.common.util.CommonUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.types.NewsStateType;
import org.net9.core.util.HttpUtils;
import org.net9.core.web.servlet.BusinessCommonServlet;
import org.net9.domain.model.news.NewsCategory;
import org.net9.domain.model.news.NewsComment;
import org.net9.domain.model.news.NewsEntry;

import cn.ctba.app.service.DimeNewsService;

import com.google.inject.Inject;

@WebModule("dimeMagPlugin")
@SuppressWarnings("serial")
public class DimeIndexServlet extends BusinessCommonServlet {

	@Inject
	protected DimeNewsService newsService;

	/**
	 * 新闻首页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ReturnUrl(rederect = false, url = "/apps/dime_mag/indexPage.jsp")
	public void indexPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int start = HttpUtils.getStartParameter(request);
		int limit = WebConstants.PAGE_SIZE_15;
		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		request.setAttribute("cats", cats);

		// 主要新闻列表
		List<NewsEntry> models = newsService.findNewses(true, NewsStateType.OK
				.getValue(), null, start, limit);
		List<Map<Object, Object>> newsList = new ArrayList<Map<Object, Object>>();
		for (NewsEntry e : models) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("entry", e);
			m.put("user", this.userService.getUser(null, e.getAuthor()));
			m.put("commentCnt", newsService.getCommentsCnt(e.getId()));
			newsList.add(m);
		}
		request.setAttribute("newsList", newsList);
		request.setAttribute("models", models);
		Integer count = newsService.getNewsCnt(true, NewsStateType.OK
				.getValue(), null);
		request.setAttribute("count", count);

		// 新闻评论
		List<NewsComment> newComments = newsService.findComments(null, true, 0,
				30);
		request.setAttribute("newComments", newComments);

		// 好评新闻
		List<NewsEntry> goodNewses = newsService.findNewsesOrderByDigg(true,
				NewsStateType.OK.getValue(), null, CommonUtils
						.getDateFromTodayOn(-7), 0, 15);
		// 确认列表不为空
		if (goodNewses.size() < 1) {
			goodNewses = newsService.findNewsesOrderByDigg(true,
					NewsStateType.OK.getValue(), null, null, 0, 15);
		}
		request.setAttribute("goodNewses", goodNewses);

		// 点击量最大新闻
		List<NewsEntry> hotNewses = newsService.findHotNewses(true, 0, 15);

		request.setAttribute("hotNewses", hotNewses);

	}
}
