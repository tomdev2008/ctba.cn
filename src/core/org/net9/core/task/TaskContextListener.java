package org.net9.core.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.core.service.ServiceModule;

import com.google.inject.Guice;

/**
 * 系统自动任务监听器
 * 
 * @author gladstone
 * @since 2008/06/21
 */
public class TaskContextListener implements ServletContextListener {

	static Log log = LogFactory.getLog(TaskContextListener.class);
	private Timer timer = null;
	private TimerTask onlineTask;
	private TimerTask hitRefreshTimerTask;
	private TimerTask dailyExpireCheckTask;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer(true);

		onlineTask = new OnlineTimerTask();
		hitRefreshTimerTask = new HitRefreshTimerTask();
		dailyExpireCheckTask = new DailyExpireCheckTask();

		Guice.createInjector(new ServiceModule()).injectMembers(onlineTask);
		Guice.createInjector(new ServiceModule()).injectMembers(
				hitRefreshTimerTask);
		Guice.createInjector(new ServiceModule()).injectMembers(
				dailyExpireCheckTask);

		// 在线扫描时间设置到1小时
		timer.schedule(onlineTask, 0, 60 * 60 * 1000);

		Integer hitRefreshDur = 60 * 60 * 1000;

		timer.schedule(hitRefreshTimerTask, hitRefreshDur, hitRefreshDur);

		Calendar c = Calendar.getInstance();

		// 每天凌晨1点
		c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date firstdailyExpireCheckTime = c.getTime();
		timer.schedule(dailyExpireCheckTask, firstdailyExpireCheckTime,
				24 * 60 * 60 * 1000);
	}
}
