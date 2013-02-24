package org.net9.core.web.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebConstants;
import org.net9.core.rss.adaptor.BbsRssAdaptor;
import org.net9.core.rss.adaptor.BlogRssAdaptor;
import org.net9.core.rss.adaptor.GroupRssAdaptor;
import org.net9.core.rss.adaptor.NewsRssAdaptor;
import org.net9.core.rss.adaptor.RssAdaptor;
import org.net9.core.rss.adaptor.ShareRssAdaptor;
import org.net9.core.rss.adaptor.TimelineRssAdaptor;
import org.net9.domain.model.BaseModel;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * Rss代理，分发到各个适配器处理
 * 
 * @author gladstone
 * @since 2008/03/16
 */
public class RssProxyAction extends BusinessDispatchAction {

	private static Log log = LogFactory.getLog(RssProxyAction.class);

	private final static String DEFAULT_FEED_TYPE = "rss_2.0";

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String feedType = request.getParameter("feed_type");
		feedType = (feedType != null) ? feedType : DEFAULT_FEED_TYPE;
		String rootUrl;
		rootUrl = this.commonService.getConfig().getDomainRoot();

		String type = request.getParameter("type");
		String limitStr = request.getParameter("limit");
		Integer start = 0;
		Integer limit = WebConstants.PAGE_SIZE_30;
		if (StringUtils.isNotEmpty(limitStr)) {
			limit = new Integer(limitStr);
		}
		RssAdaptor adaptor;

		if ("group".equals(type)) {
			adaptor = new GroupRssAdaptor();
		} else if ("blog".equals(type)) {
			adaptor = new BlogRssAdaptor();
		} else if ("news".equals(type)) {
			adaptor = new NewsRssAdaptor();
		} else if ("bbs".equals(type)) {
			adaptor = new BbsRssAdaptor();
		} else if ("share".equals(type)) {
			adaptor = new ShareRssAdaptor();
		} else {
			adaptor = new TimelineRssAdaptor();
		}

		adaptor.setRootUrl(rootUrl);
		
		
		Map<String, Object> requestMap = new HashMap<String, Object>();
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
			String key = (String)en.nextElement();
			requestMap.put(key, request.getParameter(key));
		}

		List modelList = adaptor.findModels(start, limit, requestMap);
		try {
			List entries = new ArrayList();
			SyndFeed feed = new SyndFeedImpl();
			String titlePrefix = this.commonService.getConfig()
					.getRssTitlePrefix();
			String title = titlePrefix + " "
					+ adaptor.getRssTitle(requestMap);
			feed.setTitle(title);
			feed.setLink(rootUrl);
			feed.setDescription(title);
			Iterator it = modelList.iterator();
			while (it.hasNext()) {
				try{
					BaseModel model = (BaseModel) it.next();
					SyndEntry entry = adaptor.adaptEntry(model);
					entries.add(entry);
				}catch(Exception e){
					log.error(e.getMessage());
					continue;
				}
				
			}
			feed.setEntries(entries);
			feed.setFeedType(feedType);
			response.setContentType(BusinessConstants.MIME_TYPE_XML);
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, response.getWriter());
		} catch (FeedException ex) {
			log.debug(ex);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}