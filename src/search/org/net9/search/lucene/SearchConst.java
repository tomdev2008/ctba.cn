package org.net9.search.lucene;

import java.io.File;

import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.SystemConfigVars;

public class SearchConst {

	public static String INDEXPATH_TOPIC = SystemConfigVars.REAL_CONTEXT
			+ File.separator + "data" + File.separator + "index"
			+ File.separator + "topic";

	public static String INDEXPATH_BLOG = SystemConfigVars.REAL_CONTEXT
			+ File.separator + "data" + File.separator + "index"
			+ File.separator + "blog";

	public static String INDEXPATH_NEWS = SystemConfigVars.REAL_CONTEXT
			+ File.separator + "data" + File.separator + "index"
			+ File.separator + "news";

	public static String INDEXPATH_SUBJECT = SystemConfigVars.REAL_CONTEXT
			+ File.separator + "data" + File.separator + "index"
			+ File.separator + "subject";

	public static String INDEXPATH_GROUP = SystemConfigVars.REAL_CONTEXT
			+ File.separator + "data" + File.separator + "index"
			+ File.separator + "group";

	public static int INDEX_DURATION = SystemConfigUtils
			.getInteger("topic.index.duration.minute");

}
