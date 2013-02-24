package org.net9.core.task.deprecated;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.SystemConfigUtils;

/**
 * topic index time job
 * 
 * @author gladstone
 * @deprecated
 */
public class IndexTimerTask extends TimerTask {
	static Log log = LogFactory.getLog(IndexTimerTask.class);

	@Override
	public void run() {

		if ("true".equals(SystemConfigUtils.getProperty("topic.index.task.auto"))) {
			log.debug("index timer task running...");
//			File indexDir = new File(SearchConst.TOPICINDEXPATH);
//			IndexBuilder.createTopicIndex(indexDir);
//			SearchCaches.refreshCache();
		}
		log.debug("index timer task is not in use...");

	}

}
