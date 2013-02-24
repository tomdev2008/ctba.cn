package org.net9.core.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.I18nMsgUtils;
import org.net9.common.util.StringUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.constant.UrlConstants;
import org.net9.core.constant.WebConstants;

/**
 * 和HttpRequest相关的通用工具方法
 * 
 * @author gladstone
 * @since Feb 26, 2009
 */
public abstract class HttpUtils {

	private final static Log log = LogFactory.getLog(HttpUtils.class);
	public final static String METHOD_GET = "get";
	public final static String METHOD_POST = "post";

	public static boolean isMethodPost(HttpServletRequest request) {
		return (request.getMethod().equalsIgnoreCase(METHOD_POST));
	}

	public static boolean isMethodGet(HttpServletRequest request) {
		return (request.getMethod().equalsIgnoreCase(METHOD_GET));
	}

	/**
	 * 从当前请求得到用户的真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 从当前请求得到pagger的请求参数，求的开始位置
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getStartParameter(HttpServletRequest request) {
		Integer start = 0;
		String offset = request.getParameter(BusinessConstants.PAGER_OFFSET);
		if (StringUtils.isNotEmpty(offset)) {
			start = new Integer(offset);
		}
		return start;
	}

	/**
	 * 判断当前请求是否处于管理后台
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isInManageScope(HttpServletRequest request) {
		String uri = getURL(request);
		return uri.indexOf("manage/") >= 0;
	}

	/**
	 * 从当前请求得到当前的url
	 * 
	 * @param request
	 * @return
	 */
	public static String getURL(HttpServletRequest request) {
		String uri = request.getRequestURI();
		// String url = httpRequest.getRequestURL().toString();
		// Enumeration enumeration = httpRequest.getParameterNames();
		// String paras = "?";
		// if (isAppFileStr(url)) {
		// while (enumeration.hasMoreElements()) {
		// String para = (String) enumeration.nextElement();
		// para += "=" + httpRequest.getParameter(para) + "&";
		// paras += para;
		// }
		// paras = paras.substring(0, paras.length() - 1);
		// }
		uri = uri.substring(request.getContextPath().length(), uri.length());
		uri = uri.substring(uri.indexOf("/") + 1, uri.length());
		return uri;
	}

	/**
	 * 从当前请求拼装参数字符串
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getParameters(HttpServletRequest request) {
		Enumeration enumeration = request.getParameterNames();
		String paras = "?";
		while (enumeration.hasMoreElements()) {
			String para = (String) enumeration.nextElement();
			para += "=" + request.getParameter(para) + "&";
			paras += para;
		}
		paras = paras.substring(0, paras.length() - 1);
		return paras;
	}

	/**
	 * 从当前请求得到请求来源
	 * 
	 * @param request
	 * @return
	 */
	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	/**
	 * #727 针对搜索引擎来源用户的提示
	 * 
	 * 从某个url里面得到特定参数的值
	 * 
	 * @param name
	 * @param url
	 * @return
	 */
	public static String getParameterFromUrl(String name, String url) {
		if (!url.contains("?")) {
			return "";
		}
		String reval = "";
		url = url.substring(url.indexOf("?") + 1);
		String[] pairs = url.split("&");
		for (String pair : pairs) {
			if (pair.contains("=")) {
				String pairName = pair.substring(0, pair.indexOf("="));
				if (name.equals(pairName)) {
					reval = pair.substring(pair.indexOf("=") + 1);
					return reval;
				}
			}
		}
		return reval;
	}

	/**
	 * #727 针对搜索引擎来源用户的提示
	 * 
	 * 取得搜索关键词
	 * 
	 * @param request
	 * @return
	 */
	public static String getSearchKey(HttpServletRequest request) {
		String refererURL = getReferer(request);
		return getSearchKeyInRefererURL(refererURL);
	}

	/**
	 * #727 针对搜索引擎来源用户的提示
	 * 
	 * 根据url取得搜索关键词
	 * 
	 * @param refererURL
	 *            request的来源url
	 * @return
	 */
	public static String getSearchKeyInRefererURL(String refererURL) {
		String searchKey = "";
		if (StringUtils.isNotEmpty(refererURL)) {
			try {
				if (refererURL.contains("baidu.com")) {
					// wd/word
					searchKey = getParameterFromUrl("wd", refererURL);
					if (StringUtils.isEmpty(searchKey)) {
						searchKey = getParameterFromUrl("word", refererURL);
					}
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey,
								StringUtils.ENCODE_GBK);
					}
				} else if (refererURL.contains("sogou.com")) {
					// query
					searchKey = getParameterFromUrl("query", refererURL);
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey,
								StringUtils.ENCODE_GBK);
					}
				} else if (refererURL.contains("search.114.vnet.cn")) {
					// kw
					searchKey = getParameterFromUrl("kw", refererURL);
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey,
								StringUtils.ENCODE_GBK);
					}
				} else if (refererURL.contains("search.msn.com")
						|| refererURL.contains("live.com")) {
					// q
					searchKey = getParameterFromUrl("q", refererURL);
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey, null);
					}
				} else if (refererURL.contains("yahoo.com")
						|| refererURL.contains("yahoo.cn")) {
					// p
					searchKey = getParameterFromUrl("p", refererURL);
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey, null);
					}
				} else if (refererURL.contains("www.google")) {
					// q
					searchKey = getParameterFromUrl("q", refererURL);
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey, null);
					}
				} else if (refererURL.contains("www.youdao")) {
					// q or lq
					searchKey = getParameterFromUrl("q", refererURL);
					if (StringUtils.isEmpty(searchKey)) {
						searchKey = getParameterFromUrl("lq", refererURL);
					}
					if (StringUtils.isNotEmpty(searchKey)) {
						searchKey = StringUtils.urlDecode(searchKey, null);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				searchKey = "";
			}
		}
		return searchKey;
	}

	/**
	 * 从当前请求得到被回复的用户名
	 * 
	 * @param request
	 * @return
	 */
	public static String getRepliedUsername(HttpServletRequest request) {
		String reval = request.getParameter(WebConstants.PARAMETER_REPLY_TO);
		log.debug("para-reply-to: " + reval);
		return reval;
	}

	/**
	 * 转向到系统的错误页面
	 * 
	 * @param request
	 * @param response
	 * @param errorKey
	 *            错误代码
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void sendError(HttpServletRequest request,
			HttpServletResponse response, String errorKey)
			throws ServletException, IOException {
		request.setAttribute(BusinessConstants.ERROR_KEY, I18nMsgUtils
				.getInstance().getMessage(errorKey));
		request.getRequestDispatcher(UrlConstants.ERROR_PAGE).forward(request,
				response);
	}
}
