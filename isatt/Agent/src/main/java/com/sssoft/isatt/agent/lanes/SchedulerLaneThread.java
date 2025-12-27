package com.sssoft.isatt.agent.lanes;

import org.apache.log4j.Logger;

import com.sssoft.isatt.agent.ControllerThread;
import com.sssoft.isatt.agent.ControllerThreadImpl;
import com.sssoft.isatt.data.pojo.SchedulerLaneDetails;
import com.sssoft.isatt.data.pojo.def.TaskListener;

/**
 * The Class SchedulerLaneThread.
 */
public class SchedulerLaneThread implements Runnable {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(SchedulerLaneThread.class);

	/** The schedule id. */
	private int scheduleId;
	
	/** The controller thread. */
	private ControllerThread controllerThread;
	
	/** The scheduler lane details. */
	private SchedulerLaneDetails schedulerLaneDetails;
	
	/** The scheduler lane process. */
	private SchedulerLaneProcess schedulerLaneProcess;

	/**
	 * Instantiates a new scheduler lane thread.
	 *
	 * @param scheduleId the schedule id
	 * @param controllerThread the controller thread
	 * @param schedulerLaneDetails the scheduler lane details
	 * @param schedulerLaneProcess the scheduler lane process
	 */
	public SchedulerLaneThread(int scheduleId, ControllerThread controllerThread, SchedulerLaneDetails schedulerLaneDetails,
			SchedulerLaneProcess schedulerLaneProcess) {
		super();
		this.scheduleId = scheduleId;
		this.controllerThread = controllerThread;
		this.schedulerLaneDetails = schedulerLaneDetails;
		this.schedulerLaneProcess = schedulerLaneProcess;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		LOG.info("SchedulerLaneDetails started executing with the details : " + schedulerLaneDetails.toString());
		try {
			controllerThread.initScheduler(scheduleId);
			ControllerThreadImpl.start(controllerThread);
		} catch (Exception e) {
			LOG.error("error while iterating the scheduler lane details : " + e.getMessage(), e);
		}
		TaskListener taskListener = schedulerLaneProcess.getTaskListener();
		if (taskListener != null) {
			taskListener.completed(schedulerLaneDetails);
		}
		LOG.info("SchedulerLaneDetails completed executing with the details : " + schedulerLaneDetails.toString());
	}

}
