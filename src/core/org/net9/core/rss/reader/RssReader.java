package org.net9.core.rss.reader;

import java.util.Map;

/**
 * Reader 接口
 * 
 * @author gladstone
 * @since 2008/06/19
 */
public interface RssReader {

	public static final String SUFFIX_PROTOCAL = "http://";

	/**
	 * 从RSS源读取文章
	 * 
	 * @param m
	 *            Reader上下文,保存Reader的URL等信息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void buildRSSContent(Map m) throws Exception;

}
