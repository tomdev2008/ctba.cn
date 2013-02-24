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
import org.net9.core.types.MessageBoxType;
import org.net9.domain.model.core.MainUser;
import org.net9.domain.model.core.Message;

import com.google.inject.Inject;

/**
 * 每日发送短信提醒的任务
 * 
 * @author gladstone
 * @deprecated
 */
public class DailyMsgRemindTask extends TimerTask {
	
	private static Log log = LogFactory.getLog(DailyMsgRemindTask.class);
	@Inject
	private UserService userService ;
	
	@Override
	public void run() {

		if(!SystemConfigUtils.getBoolean("email.msg.auto")){
			return;
		}
		log.debug("Msg Remind Task running...");
		List<String > sentList = new ArrayList<String>();
		List<Message> mList = userService.findMsgsGroupByUser(false, 0, BusinessConstants.MAX_INT);
		for(Message m:mList){
			if(sentList.contains(m.getMsgTo())){
				continue;
			}else{
				sentList.add(m.getMsgTo());
			}
			MainUser mainUser= userService.getUser(m.getMsgTo());
			if(mainUser.getEmailSettingMsg().intValue()==1){
				Integer msgCnt = userService.getMsgsCntByUser(m.getMsgTo(), MessageBoxType.MSG_TYPE_RECEIVE.getValue(), true,null);
				MailSender.getInstance().sendMsgRemindEmail(mainUser, msgCnt);
			}
		}
	}

}
