package org.net9.core.task.deprecated;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.net9.bbs.service.BoardService;
import org.net9.core.constant.BusinessConstants;
import org.net9.domain.model.bbs.Forbidden;

import com.google.inject.Inject;

/**
 * check the forbidens and delete them when the time have passed
 * 
 * @author gladstone
 * 
 */
public class ForbiddenTimerTask extends TimerTask {
	static Log log = LogFactory.getLog(ForbiddenTimerTask.class);

	@Inject
	BoardService boardService;

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("check forbiddens in 5 minutes...");

		List forbiddens = boardService
				.findForbiddens(null, 0, BusinessConstants.MAX_INT);
		if (forbiddens == null) {
			return;
		}
		for (int i = 0; i < forbiddens.size(); i++) {
			Forbidden f = (Forbidden) forbiddens.get(i);
			try {
				Date fbnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(f.getFbnTime());
				Calendar now = Calendar.getInstance();
				now
						.add(Calendar.DAY_OF_YEAR, new Integer("-"
								+ f.getFbnDays()));
				if (now.getTime().compareTo(fbnDate) >= 0) {
					log.info("delete the forbidden whose id is " + f.getFbnId()
							+ " and user is" + f.getFbnUser());
					boardService.delForbedden(f.getFbnId().intValue());
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

}
