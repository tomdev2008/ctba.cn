package org.net9.core.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.domain.model.core.Online;

/**
 * 
 * online info keeper
 * 
 * @author gladstone
 * 
 */
public class OnlineInfoKeeper {

	private final static Log log = LogFactory.getLog(OnlineInfoKeeper.class);

	private static OnlineInfoKeeper instance = null;

	public static OnlineInfoKeeper getInstance() {
		if (instance == null)
			instance = new OnlineInfoKeeper();
		return instance;
	}

	/** 最後檢查的時間 */
	private Date lastCheckTime;

	/** online mapper */
	private Map<String, Online> onlineMapper;

	private OnlineInfoKeeper() {
		onlineMapper = new HashMap<String, Online>();
		lastCheckTime = Calendar.getInstance().getTime();
	}

	private void checkAndRemove() {
		Calendar now = Calendar.getInstance();
		// TODO: Check this
		// 设置间隔为2小时
		now.add(Calendar.MINUTE, -120);
		if (now.getTime().compareTo(lastCheckTime) >= 0) {
			lastCheckTime = Calendar.getInstance().getTime();
			removeByTime(Calendar.getInstance().getTime());
		}
	}

	/**
	 * 
	 * @param ip
	 * @return
	 */
	public Online getOnline(String ip) {
		return onlineMapper.get(ip);
	}

	public int getOnLineCount() {
		checkAndRemove();
		return onlineMapper.size();
	}

	public Map<String, Online> getOnlineMapper() {
		return onlineMapper;
	}

	private void removeByTime(Date time) {
		log.debug("Check all online at " + time);
		List<String> badIpList = new ArrayList<String>();
		for (String ip : onlineMapper.keySet()) {
			Online model = onlineMapper.get(ip);
			try {
				Date updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(model.getUpdateTime());
				if (time.compareTo(updateTime) >= 0) {
					badIpList.add(ip);
				} else {
					log.debug("Keep the online whose ip is " + model.getIp()
							+ " and user is " + model.getUsername());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		for (String badIp : badIpList) {
			log.debug("Delete the online whose ip is " + badIp);
			removeOnline(badIp);
		}
	}

	public void removeOnline(String ip) {
		// TODO: Check this
		synchronized (onlineMapper) {
			if (onlineMapper.containsKey(ip)) {
				onlineMapper.remove(ip);
			}
		}

	}

	public void setOnline(Online model) {
		onlineMapper.put(model.getIp(), model);
		log.info("Session from agent:" + model.getIp() + ", online "
				+ onlineMapper.size());
	}

}