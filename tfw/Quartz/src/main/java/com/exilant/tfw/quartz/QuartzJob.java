package com.exilant.tfw.quartz;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionException;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.scheduler.SchedulerExecution;
import com.exilant.tfw.service.MainService;

/**
 * The Class QuartzJob.
 */
public class QuartzJob {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(QuartzJob.class);

	/** The main service. */
	private MainService mainService;
	
	/** The scheduler execution. */
	private SchedulerExecution schedulerExecution;

	/**
	 * Instantiates a new quartz job.
	 */
	public QuartzJob() {
		super();
	}

	/**
	 * Sets the main service.
	 *
	 * @param mainService the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Sets the scheduler execution.
	 *
	 * @param schedulerExecution the new scheduler execution
	 */
	public void setSchedulerExecution(SchedulerExecution schedulerExecution) {
		this.schedulerExecution = schedulerExecution;
	}

	/**
	 * Initialize schedulers.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<Scheduler> initializeSchedulers() throws ServiceException {
		List<Scheduler> returnList = this.mainService.fetchSchedulerByTime(new Timestamp(System.currentTimeMillis()));
		if (LOG.isDebugEnabled()) {
			LOG.debug("initializeSchedulers() - end");
		}
		return returnList;
	}

	/**
	 * Execute scheduler.
	 *
	 * @throws JobExecutionException the job execution exception
	 */
	public void executeScheduler() throws JobExecutionException {
		try {
			LOG.info("SimpleJob executing at : " + new Timestamp(System.currentTimeMillis()));
			List<Scheduler> schedulers = initializeSchedulers();
			List<Integer> schedulerIds = new ArrayList<Integer>();
			Iterator<Scheduler> iterator = schedulers.iterator();
			while (iterator.hasNext()) {
				Scheduler scheduler = (Scheduler) iterator.next();
				schedulerIds.add(scheduler.getScheduleID());
			}
			schedulerExecution.laneExecution(schedulerIds);
		} catch (Exception e) {
			LOG.error("error in executing the quartz job : " + e, e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("executeScheduler() - end");
		}
	}
}
