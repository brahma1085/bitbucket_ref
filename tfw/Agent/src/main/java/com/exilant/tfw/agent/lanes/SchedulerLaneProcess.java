package com.exilant.tfw.agent.lanes;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.exilant.tfw.agent.ControllerThread;
import com.exilant.tfw.agent.exception.AgentException;
import com.exilant.tfw.bean.UtlConf;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerLaneDetails;
import com.exilant.tfw.pojo.def.TaskListener;
import com.exilant.tfw.util.LangUtils;
import com.exilant.tfw.util.threads.PoolOptions;
import com.exilant.tfw.util.threads.TfwPools;

/**
 * The Class SchedulerLaneProcess.
 */
public class SchedulerLaneProcess implements Runnable {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SchedulerLaneProcess.class);

	/** The task listener. */
	private TaskListener taskListener;
	
	/** The mode one jvm. */
	private boolean modeOneJvm;
	
	/** The scheduler lane details. */
	private SchedulerLaneDetails schedulerLaneDetails;
	
	/** The controller thread. */
	private ControllerThread controllerThread;
	
	/** The schedule id. */
	private int scheduleId;
	
	/** The run once. */
	private static boolean runOnce = false;
	
	/** The Constant AGENT_LANES_POOL. */
	public static final String AGENT_LANES_POOL = "agentLanes";

	/**
	 * Gets the pool name.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 * @return the pool name
	 */
	private static String getPoolName(SchedulerLaneDetails schedulerLaneDetails) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPoolName(SchedulerLaneDetails) - start"); //$NON-NLS-1$
		}

		String returnString = AGENT_LANES_POOL + schedulerLaneDetails.getLaneUserName();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPoolName(SchedulerLaneDetails) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Inits the pool.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 */
	private void initPool(SchedulerLaneDetails schedulerLaneDetails) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initPool(SchedulerLaneDetails) - start"); //$NON-NLS-1$
		}

		final String poolName = getPoolName(schedulerLaneDetails);
		LOG.trace("pool :" + poolName);
		String smax = UtlConf.getProperty("agent.lanePoolSize", "16");
		smax = UtlConf.getProperty("agent.lanePoolSize.maxSize", smax);
		int max = 2;
		try {
			max = Integer.parseInt(smax);
		} catch (NumberFormatException e) {
			LOG.warn("reinitPool smax parse " + e + " for pool :" + poolName);
		}
		PoolOptions options = new PoolOptions();
		options.setMaxThreads(max);
		options.setCoreThreads((int) ((1 + max) / 2));
		TfwPools.initPool(poolName, options);

		if (LOG.isDebugEnabled()) {
			LOG.debug("initPool(SchedulerLaneDetails) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Process.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 * @param modeOneJvm the mode one jvm
	 * @param taskListner the task listner
	 * @param controllerThread the controller thread
	 * @param scheduleId the schedule id
	 * @throws AgentException the agent exception
	 */
	public void process(SchedulerLaneDetails schedulerLaneDetails, boolean modeOneJvm, TaskListener taskListner, ControllerThread controllerThread,
			int scheduleId) throws AgentException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("process(SchedulerLaneDetails, boolean, TaskListener, ControllerThread, int) - start"); //$NON-NLS-1$
		}

		final String poolName = getPoolName(schedulerLaneDetails);
		this.schedulerLaneDetails = schedulerLaneDetails;
		this.controllerThread = controllerThread;
		this.scheduleId = scheduleId;
		this.modeOneJvm = modeOneJvm;
		this.taskListener = taskListner;
		init(schedulerLaneDetails, modeOneJvm, taskListner);
		TfwPools.offer(poolName, this);

		if (LOG.isDebugEnabled()) {
			LOG.debug("process(SchedulerLaneDetails, boolean, TaskListener, ControllerThread, int) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - start"); //$NON-NLS-1$
		}

		for (int laneIteration = 0; laneIteration < schedulerLaneDetails.getIterations(); laneIteration++) {
			final String poolName = getPoolName(schedulerLaneDetails);
			int clones = schedulerLaneDetails.getClones();
			initPool(schedulerLaneDetails);
			if (clones == 0) {
				SchedulerLaneProcess.setRunOnce(true);
				clones = 1;
			}
			int rampUpDelay = schedulerLaneDetails.getRampUpDelay();
			int sleepTime = (rampUpDelay / clones) * 1000;
			for (int cloneId = 0; cloneId < clones; cloneId++) {
				SchedulerLaneThread schedulerLaneThreadToRun = new SchedulerLaneThread(scheduleId, controllerThread, schedulerLaneDetails, this);
				waitBeforeLaneStart(sleepTime);
				if (isRunOnce()) {
					schedulerLaneThreadToRun.run();
				} else {
					TfwPools.offer(poolName, schedulerLaneThreadToRun);
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Checks if is run once.
	 *
	 * @return true, if is run once
	 */
	public static boolean isRunOnce() {
		return runOnce;
	}

	/**
	 * Sets the run once.
	 *
	 * @param runOnce the new run once
	 */
	public static void setRunOnce(boolean runOnce) {
		SchedulerLaneProcess.runOnce = runOnce;
	}

	/**
	 * Wait before lane start.
	 *
	 * @param timeToSleep the time to sleep
	 */
	public void waitBeforeLaneStart(int timeToSleep) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("waitBeforeLaneStart(int) - start"); //$NON-NLS-1$
		}

		if (timeToSleep == 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("waitBeforeLaneStart(int) - end"); //$NON-NLS-1$
			}
			return;
		} else {
			LOG.info("sleep-time/ramp up delay in milli-seconds : " + timeToSleep);
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e) {
				LOG.error("error while thread sleep : " + e.getMessage(), e);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("waitBeforeLaneStart(int) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Inits the.
	 *
	 * @param schedulerLaneDetails the scheduler lane details
	 * @param modeOneJvm the mode one jvm
	 * @param taskListner the task listner
	 */
	public void init(SchedulerLaneDetails schedulerLaneDetails, boolean modeOneJvm, TaskListener taskListner) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("init(SchedulerLaneDetails, boolean, TaskListener) - start"); //$NON-NLS-1$
		}

		this.schedulerLaneDetails = schedulerLaneDetails;
		this.taskListener = taskListner;
		setModeOneJvm(modeOneJvm);

		if (LOG.isDebugEnabled()) {
			LOG.debug("init(SchedulerLaneDetails, boolean, TaskListener) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Stop a master plan, timeout is the max time to stop, if 0 then immediate,
	 * if less than 0 then its max int.
	 *
	 * @param scheduler the scheduler
	 * @param timeout the timeout
	 * @param forceStop the force stop
	 */
	public void masterPlanStop(Scheduler scheduler, int timeout, boolean forceStop) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("masterPlanStop(Scheduler, int, boolean) - start"); //$NON-NLS-1$
		}

		final String poolName = getPoolName(schedulerLaneDetails);
		ThreadPoolExecutor threadPoolExecutor = TfwPools.getPool(poolName);
		threadPoolExecutor.shutdown();
		if (forceStop) {
			LangUtils.sleep(200);
			threadPoolExecutor.shutdownNow();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("masterPlanStop(Scheduler, int, boolean) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the task listener.
	 *
	 * @return the task listener
	 */
	public TaskListener getTaskListener() {
		return taskListener;
	}

	/**
	 * Sets the task listener.
	 *
	 * @param taskListener the new task listener
	 */
	public void setTaskListener(TaskListener taskListener) {
		this.taskListener = taskListener;
	}

	/**
	 * Checks if is mode one jvm.
	 *
	 * @return true, if is mode one jvm
	 */
	public boolean isModeOneJvm() {
		return modeOneJvm;
	}

	/**
	 * Sets the mode one jvm.
	 *
	 * @param modeOneJvm the new mode one jvm
	 */
	public void setModeOneJvm(boolean modeOneJvm) {
		this.modeOneJvm = modeOneJvm;
	}

}
