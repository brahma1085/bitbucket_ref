package tfw.com.Core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.scheduler.SchedulerExecution;
import com.exilant.tfw.service.MainService;

public class SchedulerTest {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(SchedulerTest.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext-Core.xml", "applicationContext.xml",
				"dataAccessContext-local.xml" });
		SchedulerExecution schedulerExecution = (SchedulerExecution) context.getBean("schedulerExecution");
		MainService mainService = (MainService) context.getBean("mainService");
		List<Scheduler> schedulers = mainService.fetchSchedulerByTime(new Timestamp(System.currentTimeMillis()));
		List<Integer> schedulerIds = new ArrayList<Integer>();
		Iterator<Scheduler> iterator = schedulers.iterator();
		while (iterator.hasNext()) {
			Scheduler scheduler = (Scheduler) iterator.next();
			schedulerIds.add(scheduler.getScheduleID());
		}
		schedulerExecution.setMainService(mainService);
		schedulerExecution.laneExecution(schedulerIds);
		LOG.info("Scheduler executed with lanes");
	}
}
