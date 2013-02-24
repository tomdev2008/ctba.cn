package org.net9.core.task.deprecated;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.mail.MailSender;
import org.net9.core.service.UserService;
import org.net9.core.wrapper.ListWrapper;
import org.net9.core.wrapper.UserEventWrapper;
import org.net9.domain.model.core.Friend;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.User;
import org.net9.domain.model.core.UserLog;

import com.google.inject.Inject;

/**
 * 每日发送站内新鲜事的任务
 * 
 * @author gladstone
 * @deprecated
 */
public class DailyTimelineTask extends TimerTask {

	private static Log log = LogFactory.getLog(DailyTimelineTask.class);

	@Inject
	private UserService userService ;

	@SuppressWarnings("unchecked")
	@Override
	public void run() {

		if (!SystemConfigUtils.getBoolean("email.timeline.auto")) {
			return;
		}
		log.debug("DailyTimelineTask running...");

		List<MainUser> userList = userService.findUsersWithEmail(0,
				BusinessConstants.MAX_INT);

		for (MainUser user : userList) {
			if (user.getEmailSettingTimeline().intValue() == 1) {
				// 用戶的好友
				List<String> userAndFriends = new ArrayList<String>();
				List<Friend> friendList = userService.findFriends(user
						.getUsername(), null, 0, 25);
				for (Friend friend : friendList) {
					User friendUser = userService.getUser(null, friend
							.getFrdUserYou());
					if (friendUser == null||userAndFriends.contains(friendUser.getUserName())){
						continue;
					}
					userAndFriends.add(friendUser.getUserName());
				}
				List<UserLog> userLogs = userService.findUserlogs(
						userAndFriends, 0, 20,
						UserEventWrapper.DEFAULT_WANTED_TYPES);

				if (userLogs.size() > 0) {
					MailSender.getInstance().sendDailyTimelineEmail(
							user,
							ListWrapper.getInstance()
									.formatUserLogList(userLogs));
				}
			}
		}
	}
}
