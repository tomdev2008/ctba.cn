package org.net9.core.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.exception.InvalidAuthorSecurityException;
import org.net9.common.util.StringUtils;
import org.net9.common.web.annotation.ReturnUrl;
import org.net9.common.web.annotation.SecurityRule;
import org.net9.common.web.annotation.WebModule;
import org.net9.core.constant.UrlConstants;
import org.net9.core.types.UserSecurityType;
import org.net9.core.util.HttpUtils;
import org.net9.core.util.UserHelper;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.blog.BlogEntry;
import org.net9.domain.model.core.Share;
import org.net9.domain.model.core.SysFeedback;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.gallery.ImageModel;
import org.net9.domain.model.group.GroupTopic;
import org.net9.domain.model.news.NewsEntry;

/**
 * 用户反馈
 * 
 * @author gladstone
 * 
 */
@WebModule("feedback")
@SuppressWarnings("serial")
@SecurityRule(level = UserSecurityType.OPTION_LEVEL_USER)
public class FeedbackServlet extends BusinessCommonServlet {

	private static Log log = LogFactory.getLog(FeedbackServlet.class);

	/**
	 * 表单
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws InvalidAuthorSecurityException
	 */
	@ReturnUrl(rederect = false, url = "/core/feedbackForm.jsp")
	public void form(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException,
			InvalidAuthorSecurityException {
		String refUrl = HttpUtils.getReferer(request);
		request.setAttribute("__request_refurl", refUrl);
		String id = request.getParameter("id");
		String typeStr = request.getParameter("type");
		String label = "";
		String url = "";
		if ("topic".equalsIgnoreCase(typeStr)) {
			Topic topic = topicService.getTopic(Integer.valueOf(id));
			label = topic.getTopicTitle();
			url = UrlConstants.TOPIC + id;
		} else if ("blog".equalsIgnoreCase(typeStr)) {
			BlogEntry e = entryService.getEntry(Integer.valueOf(id));
			label = e.getTitle();
			url = UrlConstants.BLOG_ENRTY + id;
		} else if ("group".equalsIgnoreCase(typeStr)) {
			GroupTopic t = groupTopicService.getTopic(Integer.valueOf(id));
			label = t.getTitle();
			url = UrlConstants.GROUP_TOPIC + id;
		} else if ("news".equalsIgnoreCase(typeStr)) {
			NewsEntry e = newsService.getNews(Integer.valueOf(id));
			label = e.getTitle();
			url = UrlConstants.NEWS_ENRTY + id;
		} else if ("vote".equalsIgnoreCase(typeStr)) {
			Vote vote = voteService.getVote(Integer.valueOf(id));
			label = vote.getQuestion();
			url = UrlConstants.VOTE + id;
		} else if ("image".equalsIgnoreCase(typeStr)) {
			ImageModel imageModel = this.imageService.getImage(Integer
					.valueOf(id));
			label = imageModel.getName();
			url = UrlConstants.GALLERY_PHOTO + id;
		} else if ("share".equalsIgnoreCase(typeStr)) {
			Share shareModel = this.shareService.getShare(Integer.valueOf(id));
			label = shareModel.getLabel();
			url = UrlConstants.SHARE_COMMENT + id;
		}
		request.setAttribute("__request_label", label);
		request.setAttribute("__request_url", url);
	}

	@ReturnUrl(rederect = false, url = "/core/feedbackDone.jsp")
	public void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = UserHelper.getuserFromCookie(request);
		String description = request.getParameter("description");
		log.debug(description);
		String url = request.getParameter("refurl");
		String typeStr = request.getParameter("type");
		SysFeedback model = new SysFeedback();
		model.setCreateTime(StringUtils.getTimeStrByNow());
		model.setUsername(username);
		model.setDescription(description);
		model.setScoreAdded(0);
		model.setType(Integer.valueOf(typeStr));
		model.setUrl(url);
		this.commonService.saveFeedback(model);
		request.setAttribute("__request_refurl", url);
	}

}
