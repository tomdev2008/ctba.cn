package org.net9.core.task;

import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.common.util.CommonUtils;
import org.net9.common.util.I18nMsgUtils;
import org.net9.core.constant.BusinessConstants;
import org.net9.core.service.UserService;
import org.net9.core.types.NoticeType;
import org.net9.core.wrapper.SimplePojoWrapper;
import org.net9.domain.model.ctba.Vote;
import org.net9.domain.model.ctba.VoteAnswer;
import org.net9.domain.model.group.ActivityModel;
import org.net9.domain.model.group.ActivityUser;
import org.net9.group.service.ActivityService;
import org.net9.vote.service.VoteService;

import com.google.inject.Inject;

/**
 * 检查活动和投票是否过期
 * 
 * 性能可能不是很好，以后可以考虑用sp来代替
 * 
 * @author gladstone
 * @since Feb 26, 2009
 */
public class DailyExpireCheckTask extends TimerTask {

    private static Log log = LogFactory.getLog(DailyExpireCheckTask.class);
    @Inject
    private UserService userService;
    @Inject
    private ActivityService activityService;
    @Inject
    private VoteService voteService;

    @Override
    public void run() {

        log.info("Daily Expire Check Task Running...");

        //从昨天到今天这段时间中过期的活动
        List<ActivityModel> activityList = activityService.findExpiredActivities(CommonUtils.getDateFromTodayOn(-1), CommonUtils.getDateFromTodayOn(0), 0,
                BusinessConstants.MAX_INT);
        log.debug("activityList size: " + activityList.size());
        for (ActivityModel a : activityList) {
            // 向所有参与活动的人发送系统提醒
            List<ActivityUser> userList = this.activityService.findActivityUsers(a.getId(), null, new Integer[]{}, 0,
                    BusinessConstants.MAX_INT);
            for (ActivityUser u : userList) {
                String msg = I18nMsgUtils.getInstance().createMessage(
                        "notice.expired.activity",
                        new Object[]{SimplePojoWrapper.wrapActivity(a)});
                userService.trigeNotice(u.getUsername(), "dummy", msg,
                        null, NoticeType.COMMON);
            }
        }


        //从昨天到今天这段时间中过期的投票
        List<Vote> voteList = voteService.findExpiredVotes(CommonUtils.getDateFromTodayOn(-1), CommonUtils.getDateFromTodayOn(0), 0, BusinessConstants.MAX_INT);
        log.debug("voteList size: " + voteList.size());
        for (Vote v : voteList) {
            // 向所有参与投票的人发送系统提醒
            List<VoteAnswer> answerList = this.voteService.findVoteAnswers(v.getId(), null, 0, BusinessConstants.MAX_INT);
            for (VoteAnswer va : answerList) {
                String msg = I18nMsgUtils.getInstance().createMessage(
                        "notice.expired.vote",
                        new Object[]{SimplePojoWrapper.wrapVote(v)});
                userService.trigeNotice(va.getUsername(), "dummy", msg,
                        null, NoticeType.COMMON);
            }
        }
    }
}
