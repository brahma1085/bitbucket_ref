package com.exilant.tfw.test;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

public class FetchSchedulerData {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(FetchSchedulerData.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });

		MainService mainService = (MainService) context.getBean("mainService");
		List<Scheduler> schedulers = mainService.fetchSchedulerByTime(DataConstants.DEFAULT_TIMESTAMP);
		for (int i = 0; i < schedulers.size(); i++) {
			LOG.info("scheduler data fetched from DB successfully ==> " + mainService.getSchedulerWithAllData(schedulers.get(i).getScheduleID()));
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
