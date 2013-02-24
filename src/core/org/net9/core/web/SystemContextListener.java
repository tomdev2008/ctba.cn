package org.net9.core.web;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.WebPageVarConstant;
import org.net9.core.service.ServiceModule;
import org.net9.core.util.HttpUtils;
import org.net9.domain.model.core.Online;
import org.net9.domain.model.news.NewsCategory;
import org.net9.news.service.NewsService;
import org.net9.subject.service.SubjectTypeHelper;

import com.google.inject.Guice;
import com.google.inject.Inject;

/**
 * 系统上下文监听器
 * 
 * @author gladstone
 * 
 */
public class SystemContextListener implements HttpSessionListener,
		ServletRequestListener, ServletContextListener {

	private static Log log = LogFactory.getLog(SystemContextListener.class);
	public static final Pattern ROBOT_PATTERN = Pattern
			.compile(".*(slurp|bot|java).*");
	private HttpServletRequest request;
	@Inject
	NewsService newsService;

	public SystemContextListener() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("ctba community context shuting down...");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("ctba community context starting...");
		try {
			WebModuleManager.scanWebModules();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		// 在系统里存入专题类型和新闻类型
		servletContextEvent.getServletContext().setAttribute(
				WebPageVarConstant.APP_SUBJECT_TYPE_LIST,
				SubjectTypeHelper.typeList);

		List<NewsCategory> cats = newsService.findCats(true, 0,
				BusinessConstants.MAX_INT);
		servletContextEvent.getServletContext().setAttribute(
				WebPageVarConstant.APP_NEWS_TYPE_LIST, cats);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		request = (HttpServletRequest) sre.getServletRequest();
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		request = (HttpServletRequest) sre.getServletRequest();

	}

	@Override
	public void sessionCreated(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		String src = session.getServletContext().getServletContextName();
		if (request != null) {
			src = "" + HttpUtils.getIpAddr(request);
		}
		String ip = HttpUtils.getIpAddr(request);
		Online online = new Online();

		String agent = request.getHeader("User-Agent");
		if (agent != null && agent.length() > 0
				&& ROBOT_PATTERN.matcher(agent).matches()) {
			online.setAgent(WebPageVarConstant.COMMON_IS_ROBOT);
		} else {
			online.setAgent(agent);
		}

		online.setInstanceCnt(1);
		online.setUpdateTime(StringUtils.getTimeStrByNow());
		online.setIp(ip);
		OnlineInfoKeeper.getInstance().setOnline(online);

		log.info("Session from " + src + ",agent:" + agent + ", online "
				+ OnlineInfoKeeper.getInstance().getOnLineCount());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		// remove the ip
		OnlineInfoKeeper.getInstance().removeOnline(
				HttpUtils.getIpAddr(request));
		String src = session.getServletContext().getServletContextName();
		if (request != null) {
			src = "" + HttpUtils.getIpAddr(request);
		}
		if (session.getAttribute(BusinessConstants.USER_NAME) != null
				&& !session.getAttribute(BusinessConstants.USER_NAME)
						.equals("")) {
			log.info(session.getAttribute(BusinessConstants.ADMIN_NAME)
					+ " session off>> " + src + ", online "
					+ OnlineInfoKeeper.getInstance().getOnLineCount());
		} else {
			log.info("user session off>> " + src + ", online "
					+ OnlineInfoKeeper.getInstance().getOnLineCount());
		}
	}
}
