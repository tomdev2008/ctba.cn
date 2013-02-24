package org.net9.core.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.service.CommonService;
import org.net9.core.service.UserService;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Online;

import com.google.inject.Inject;

/**
 * check the onlines , delete the passed item in the database
 * 
 * @author gladstone
 * 
 */
public class OnlineTimerTask extends TimerTask {
	static Log log = LogFactory.getLog(OnlineTimerTask.class);

	@Inject
	CommonService commonService;

	@Inject
	UserService userService;

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("Check online in 5 minutes...");

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, -10);

		List onlines = commonService.findOnlines(0, BusinessConstants.MAX_INT);

		if (onlines != null) {
			for (int i = 0; i < onlines.size(); i++) {
				Online model = (Online) onlines.get(i);
				try {
					Date updateTime = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(model.getUpdateTime());
					if (now.getTime().compareTo(updateTime) >= 0) {
						log.info("Delete the online whose ip is "
								+ model.getIp() + " and user is "
								+ model.getUsername());
						commonService.deleteOnline(model);
						// 设置用户的最后活动时间
						MainUser user = userService
								.getUser(model.getUsername());
						if (user != null) {
							user.setLoginTime(model.getUpdateTime());
							userService.saveMainUser(user);
						}
					} else {
						log.debug("Keep the online whose ip is "
								+ model.getIp() + " and user is "
								+ model.getUsername());
					}
				} catch (ParseException e) {
					log.error(e.getMessage());
					// e.printStackTrace();
				}
			}
		} else {
			log.info("No online found");
		}
	}

}
