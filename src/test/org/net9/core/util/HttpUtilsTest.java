package org.net9.core.util;

import org.junit.Test;
import org.net9.core.util.HttpUtils;

/**
 * HttpUtils's Test
 * 
 * @author gladstone
 */
public class HttpUtilsTest {

	@Test
	public void testGetSearchKeyInRefererURL() {
		System.out.println("==testGetSearchKeyInRefererURL==");
		// baidu
		String url = "http://www.baidu.com/s?wd=%B3%B6%CC%B8%C9%E7";
		String searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("baidu searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));

		// google
		url = "http://www.google.cn/search?hl=zh-CN&q=%E6%89%AF%E8%B0%88%E7%A4%BE&meta=&aq=f&oq=";
		searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("google searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));

		// yahoo
		url = "http://search.yahoo.com/search?p=%E6%89%AF%E8%B0%88%E7%A4%BE&vc=&fr=yfp-t-501&toggle=1&cop=mss&ei=UTF-8&fp_ip=CN";
		searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("yahoo searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));

		// live
		url = "http://cnweb.search.live.com/results.aspx?q=%E6%89%AF%E8%B0%88%E7%A4%BE&form=QBRE";
		searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("live searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));

		// 114
		url = "http://search.114.vnet.cn/search_web.html?id=1&fm=index&bt=s&st=web&kw=%B3%B6%CC%B8%C9%E7";
		searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("114 searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));

		// sogou
		url = "http://www.sogou.com/web?query=%B3%B6%CC%B8%C9%E7&_asf=www.sogou.com&_ast=1237430670&w=01019900&p=40040100";
		searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("sogou searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));
		
		// youdao
		url = "http://www.youdao.com/search?q=%E6%89%AF%E8%B0%88%E7%A4%BE&btnIndex=%E6%90%9C+%E7%B4%A2&ue=utf8&keyfrom=web.index";
		searchKey = HttpUtils.getSearchKeyInRefererURL(url);
		System.out.println("youdao searchKey:" + searchKey);
		org.junit.Assert.assertTrue("扯谈社".equals(searchKey));
	}

	@Test
	public void testGetParameterFromUrl() {
		System.out.println("==testGetParameterFromUrl==");
		String url = "http://www.baidu.com/s?ie=gb2312&bs=%C9%A8%C3%E8%B0%FC%CF%C2%C3%E6%B5%C4%C0%E0&sr=&z=&cl=3&f=8&wd=java+%C9%A8%C3%E8%B0%FC%CF%C2%C3%E6%B5%C4%C0%E0&ct=0";
		String searchKey = HttpUtils.getParameterFromUrl("wd", url);
		System.out.println(searchKey);
		org.junit.Assert
				.assertTrue("java+%C9%A8%C3%E8%B0%FC%CF%C2%C3%E6%B5%C4%C0%E0"
						.equals(searchKey));
		url = "http://www.google.com/search?q=%E6%88%91%E6%93%8D&sourceid=ie7&rls=com.microsoft:en-US&ie=utf8&oe=utf8";
		searchKey = HttpUtils.getParameterFromUrl("q", url);
		System.out.println(searchKey);
		org.junit.Assert.assertTrue("%E6%88%91%E6%93%8D".equals(searchKey));
	}
}
