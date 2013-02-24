package org.net9.core.rss.reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.net9.core.service.ServiceModule;

import com.google.inject.Guice;

/**
 * Reader 抽象类
 * 
 * @author gladstone
 * @since 2008/06/19
 */
public abstract class BaseRssReader implements RssReader, java.io.Serializable {

	public BaseRssReader() {
		Guice.createInjector(new ServiceModule()).injectMembers(this);
	}

	/**
	 * 设置代理信息
	 * 
	 * @param proxySet
	 * @param proxyHost
	 * @param proxyPort
	 */
	protected void setProxyInfo(String proxySet, String proxyHost,
			String proxyPort) {
		System.getProperties().put("proxySet", proxySet);
		System.getProperties().put("proxyHost", proxyHost);
		System.getProperties().put("proxyPort", proxyPort);
	}

	/**
	 * get HTML or XML content
	 * 
	 * @param strUrl
	 * @return
	 */
	protected String getURLContent(String strUrl) {
		try {
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url
					.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error open url" + strUrl;
		}

	}

}
