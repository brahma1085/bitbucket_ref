package com.exilant.tfw.test;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.pojo.AgentDetails;
import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.service.MainService;
import com.google.gson.Gson;

/**
 * The Class JsonTest.
 */
public class JsonTest {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(JsonTest.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });
		MainService mainService = (MainService) context.getBean("mainService");
		/*Application application = mainService.getApplicationForFlowChart(1);
		Gson gson = new Gson();
		String json = gson.toJson(application);
		LOG.info(json);

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}*/
		List<Scheduler> schedulers = mainService.fetchSchedulerByTime(new Timestamp(System.currentTimeMillis()));
		Iterator<Scheduler> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = (Scheduler) iterator.next();
			System.out.println(scheduler);
		}
	}
}