package com.sssoft.isatt.agent.lanes;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.sssoft.isatt.agent.ControllerThread;
import com.sssoft.isatt.agent.exception.AgentException;
import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.def.TaskListener;
import com.sssoft.isatt.utils.bean.UtlConf;
import com.sssoft.isatt.utils.util.LangUtils;
import com.sssoft.isatt.utils.util.threads.PoolOptions;
import com.sssoft.isatt.utils.util.threads.TfwPools;

/**
 * The Class SchedulerProcess.
 */
public class SchedulerProcess {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SchedulerLaneProcess.class);

	/** The task listener. */
	private TaskListener taskListener;
	
	/** The controller thread. */
	private ControllerThread controllerThread;
	
	/** The Constant AGENTS_POOL. */
	public static final String AGENTS_POOL = "agents";

	/**
	 * Gets the pool name.
	 *
	 * @param scheduler the scheduler
	 * @return the pool name
	 */
	private static String getPoolName(Scheduler scheduler) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPoolName(Scheduler) - start"); //$NON-NLS-1$
		}

		String returnString = AGENTS_POOL + scheduler.getSchedulerName() + scheduler.getScheduleTime() + scheduler.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPoolName(Scheduler) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Inits the pool.
	 *
	 * @param scheduler the scheduler
	 */
	private void initPool(Scheduler scheduler) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initPool(Scheduler) - start"); //$NON-NLS-1$
		}

		final String poolName = getPoolName(scheduler);
		LOG.trace("pool :" + poolName);
		String smax = UtlConf.getProperty("agent.PoolSize", "5");
		smax = UtlConf.getProperty("agent.PoolSize.maxSize", smax);
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
			LOG.debug("initPool(Scheduler) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Process.
	 *
	 * @param scheduler the scheduler
	 * @param taskListner the task listner
	 * @param controllerThread the controller thread
	 * @throws AgentException the agent exception
	 */
	public void process(Scheduler scheduler, TaskListener taskListner, ControllerThread controllerThread) throws AgentException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("process(Scheduler, TaskListener, ControllerThread) - start"); //$NON-NLS-1$
		}

		initPool(scheduler);
		this.controllerThread = controllerThread;
		this.taskListener = taskListner;
		final String poolName = getPoolName(scheduler);
		TfwPools.offer(poolName, this.controllerThread);

		if (LOG.isDebugEnabled()) {
			LOG.debug("process(Scheduler, TaskListener, ControllerThread) - end"); //$NON-NLS-1$
		}
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
	 * Scheduler force stop.
	 *
	 * @param scheduler the scheduler
	 * @param timeout the timeout
	 * @param forceStop the force stop
	 */
	public void schedulerForceStop(Scheduler scheduler, int timeout, boolean forceStop) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("schedulerForceStop(Scheduler, int, boolean) - start"); //$NON-NLS-1$
		}

		final String poolName = getPoolName(scheduler);
		ThreadPoolExecutor threadPoolExecutor = TfwPools.getPool(poolName);
		threadPoolExecutor.shutdown();
		if (forceStop) {
			LangUtils.sleep(200);
			threadPoolExecutor.shutdownNow();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("schedulerForceStop(Scheduler, int, boolean) - end"); //$NON-NLS-1$
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

}
