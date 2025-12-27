package com.exilant.tfw.test;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.service.MainService;

public class FetchAppData {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(FetchAppData.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });

		InputService inputService = (InputService) context.getBean("inputService");
		MainService mainService = (MainService) context.getBean("mainService");

		Param param = getParamData();
		long paramId = inputService.insertParam(param);
		LOG.info("param Id : " + paramId);

		Application application = getApplicationData();
		long appId = mainService.insertApplication(application);
		LOG.info("appId : " + appId);

		SchedulerRunDetails schedulerRunDetails = getSchedulerRunDetails();
		long testRunId = mainService.insertSchedulerRunDetails(schedulerRunDetails);
		LOG.info("testRunId : " + testRunId);
	}

	private static Param getParamData() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamData() - start"); //$NON-NLS-1$
		}

		Param param = new Param();
		param.setCreatedBy(System.getProperty("user"));
		param.setCreatedDateTime(new Date(System.currentTimeMillis()));
		param.setUpdatedBy(System.getProperty("user"));
		param.setUpdatedDateTime(new Date(System.currentTimeMillis()));
		param.setDescription("testing from Data module");
		param.setObjectID(0);
		param.setParamGroupID(0);
		param.setParamName("testing fromswer Data module");
		param.setSortOrder(1);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamData() - end"); //$NON-NLS-1$
		}
		return param;
	}

	private static Application getApplicationData() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationData() - start"); //$NON-NLS-1$
		}

		Application application = new Application();
		application.setAppName("testinf");
		application.setDescription("dskfhjlsd");
		application.setCreatedBy(System.getProperty("user"));
		application.setUpdatedBy(System.getProperty("user"));
		application.setCreatedDateTime(new Date(System.currentTimeMillis()));
		application.setUpdatedDateTime(new Date(System.currentTimeMillis()));

		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationData() - end"); //$NON-NLS-1$
		}
		return application;
	}

	private static SchedulerRunDetails getSchedulerRunDetails() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerRunDetails() - start"); //$NON-NLS-1$
		}

		SchedulerRunDetails schedulerRunDetails = new SchedulerRunDetails();
		schedulerRunDetails.setPercentageFailCount(10);
		schedulerRunDetails.setPercentagePassCount(10);
		schedulerRunDetails.setResult(false);
		schedulerRunDetails.setTestDataID(1);
		schedulerRunDetails.setTestPlanID(1);
		schedulerRunDetails.setScheduleID(1);
		schedulerRunDetails.setRunTime(new Date(System.currentTimeMillis()));
		schedulerRunDetails.setStartDateTime(new Timestamp(System.currentTimeMillis()));
		schedulerRunDetails.setEndDateTime(new Timestamp(System.currentTimeMillis()));

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerRunDetails() - end"); //$NON-NLS-1$
		}
		return schedulerRunDetails;
	}
}
