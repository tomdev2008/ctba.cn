package org.net9.core.task;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.hit.HitManager;

import com.google.inject.Inject;

/**
 * check the topic hits
 * 
 * @author gladstone
 * 
 */
public class HitRefreshTimerTask extends TimerTask {
	
	private static Log log = LogFactory.getLog(HitRefreshTimerTask.class);

	@Inject
	private HitManager hitManager;

	@Override
	public void run() {
		log.info("Run HitRefreshTimerTask ...");
		this.hitManager.refreshAll();
	}

}
