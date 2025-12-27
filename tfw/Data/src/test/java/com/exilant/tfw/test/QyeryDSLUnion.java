package com.exilant.tfw.test;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.pojo.def.ScheduledJobs;
import com.exilant.tfw.service.MainService;

public class QyeryDSLUnion {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(QyeryDSLUnion.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });
		MainService mainService = (MainService) context.getBean("mainService");
		List<ScheduledJobs> jobs = mainService.getScheduledCompletedJobsForApp(2);
		Iterator<ScheduledJobs> iterator = jobs.iterator();
		while (iterator.hasNext()) {
			ScheduledJobs scheduledJobs = (ScheduledJobs) iterator.next();
			LOG.info(scheduledJobs.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
