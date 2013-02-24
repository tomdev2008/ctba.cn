package org.net9.search.web.servlet;

import com.j2bb.common.search.search.SearchRequest;

/**
 * 为了集群，增加序列化
 * 
 * @author gladstone
 * @since May 9, 2009
 */
public class RichSearchRequest extends SearchRequest implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

}
