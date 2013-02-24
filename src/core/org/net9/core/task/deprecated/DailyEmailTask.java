package org.net9.core.task.deprecated;

import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.service.TopicService;
import org.net9.common.util.SystemConfigUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.mail.MailSender;
import org.net9.core.service.UserService;
import org.net9.domain.model.bbs.Topic;
import org.net9.domain.model.core.MainUser;

import com.google.inject.Inject;

/**
 * 每日发送邮件的系统任务
 * 
 * @author gladstone
 * @deprecated
 */
public class DailyEmailTask extends TimerTask {
	
	private static Log log = LogFactory.getLog(DailyEmailTask.class);
	@Inject
	private TopicService topicService ;
	@Inject
	private UserService userService;
	
	
	@Override
	public void run() {

		if(!SystemConfigUtils.getBoolean("email.topic.auto")){
			return;
		}
		log.debug("DailyEmailTask running...");
		
		List<Topic> topicList= topicService.findNewTopicsByTime(null, 0, 0, BusinessConstants.MAX_INT);
		
		List<MainUser> userList = userService.findUsersWithEmail(0, BusinessConstants.MAX_INT);
		
		for(MainUser user:userList){
			if(user.getEmailSettingTopic().intValue()==1){
				MailSender.getInstance().sendContentEmail(user, topicList );
			}
		}

	}

}
