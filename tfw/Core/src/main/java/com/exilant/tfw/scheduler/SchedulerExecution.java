package com.exilant.tfw.scheduler;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.exilant.tfw.bean.UtlConf;
import com.exilant.tfw.core.exception.CoreException;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.AgentDetails;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerBackup;
import com.exilant.tfw.pojo.SchedulerLaneDetails;
import com.exilant.tfw.pojo.def.LaneListener;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.util.LangUtils;
import com.exilant.tfw.util.http.HttpData;
import com.exilant.tfw.util.http.NetSend;

/**
 * The Class SchedulerExecution.
 */
public class SchedulerExecution {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(SchedulerExecution.class);

	/** The run mode single vm. */
	private static boolean runModeSingleVm = false;

	/** The lane listener. */
	private static LaneListener laneListener;

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Initialize scheduler lane details.
	 * 
	 * @param schedulerId
	 *            the scheduler id
	 * @return the list
	 * @throws CoreException
	 *             the core exception
	 */
	private List<SchedulerLaneDetails> initializeSchedulerLaneDetails(int schedulerId) throws CoreException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initializeSchedulerLaneDetails(int) - start"); //$NON-NLS-1$
		}

		try {
			List<SchedulerLaneDetails> returnList = mainService.getSchedulerLaneDetailsByScheduleId(schedulerId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("initializeSchedulerLaneDetails(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (ServiceException e) {
			LOG.error("initializeSchedulerLaneDetails(int)", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		}
	}

	/**
	 * Checks if is run mode single vm.
	 * 
	 * @return true, if is run mode single vm
	 */
	public static boolean isRunModeSingleVm() {
		return runModeSingleVm;
	}

	/**
	 * Sets the run mode single vm.
	 * 
	 * @param runModeSingleVm
	 *            the new run mode single vm
	 */
	public static void setRunModeSingleVm(boolean runModeSingleVm) {
		SchedulerExecution.runModeSingleVm = runModeSingleVm;
	}

	/**
	 * Delete dirty schedulers.
	 * 
	 * @throws CoreException
	 *             the core exception
	 */
	private void deleteDirtySchedulers() throws CoreException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteDirtySchedulers() - start"); //$NON-NLS-1$
		}

		try {
			List<Scheduler> schedulers = this.mainService.getSchedulerByStatus();
			Iterator<Scheduler> iterator = schedulers.iterator();
			while (iterator.hasNext()) {
				Scheduler scheduler = (Scheduler) iterator.next();
				SchedulerBackup schedulerBackup = this.mainService.getSchedulerBackupById(scheduler.getScheduleID());
				if (schedulerBackup != null) {
					this.mainService.deleteSchedulerById(scheduler.getScheduleID());
				} else {
					SchedulerBackup backup = new SchedulerBackup();
					BeanUtils.copyProperties(backup, scheduler);
					int id = this.mainService.insertSchedulerGetKey(backup);
					if (id != 0) {
						this.mainService.deleteSchedulerById(scheduler.getScheduleID());
					}
				}
			}
		} catch (ServiceException e) {
			LOG.error("deleteDirtySchedulers()", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		} catch (IllegalAccessException e) {
			LOG.error("deleteDirtySchedulers()", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		} catch (InvocationTargetException e) {
			LOG.error("deleteDirtySchedulers()", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteDirtySchedulers() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Lane execution.
	 * 
	 * @param schedulerIds
	 *            the scheduler ids
	 * @throws CoreException
	 *             the core exception
	 */
	public void laneExecution(List<Integer> schedulerIds) throws CoreException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("laneExecution(List<Integer>) - start"); //$NON-NLS-1$
		}

		Iterator<Integer> iterator = schedulerIds.iterator();
		while (iterator.hasNext()) {
			Integer schedulerId = (Integer) iterator.next();
			Scheduler scheduler = null;
			try {
				scheduler = mainService.getSchedulerById(schedulerId);
				deleteDirtySchedulers();
			} catch (ServiceException e1) {
				LOG.error("laneExecution(List<Integer>)", e1); //$NON-NLS-1$

				throw new CoreException(e1.getMessage());
			}
			if (scheduler != null) {
				Timestamp oldTimestamp = scheduler.getScheduleTime();
				int scheduleId = scheduler.getScheduleID();
				if (scheduler.getFrequency() != null && Integer.parseInt(scheduler.getFrequency()) != 0 && scheduler.getFailureCount() == 0) {
					String frequencyValue = scheduler.getFrequency();
					long frequencyInMin = Long.parseLong(frequencyValue);
					long newTimeStamp = (oldTimestamp.getTime()) + (frequencyInMin * 60000);
					scheduler.setScheduleTime(new Timestamp(newTimeStamp));
					try {
						scheduler.setScheduleID(0);
						mainService.insertSchedulerGetKey(scheduler);
					} catch (ServiceException e1) {
						LOG.error("laneExecution(List<Integer>)", e1); //$NON-NLS-1$

						throw new CoreException(e1.getMessage());
					}
				}
				scheduler.setScheduleID(scheduleId);
				if (scheduler.isMultiLanes()) {
					List<SchedulerLaneDetails> schedulerLaneDetailsList = initializeSchedulerLaneDetails(schedulerId);
					Iterator<SchedulerLaneDetails> laneIterator = schedulerLaneDetailsList.iterator();
					while (laneIterator.hasNext()) {
						SchedulerLaneDetails schedulerLaneDetails = (SchedulerLaneDetails) laneIterator.next();
						if (isRunModeSingleVm() && laneListener != null) {
							laneListener.process(schedulerLaneDetails);
						} else {
							AgentDetails agentDetails = null;
							try {
								agentDetails = this.mainService.getAgentDetailsById(schedulerLaneDetails.getAgentID());
							} catch (ServiceException e) {
								LOG.error("laneExecution(List<Integer>)", e); //$NON-NLS-1$

								throw new CoreException(e.getMessage());
							}
							if (agentDetails == null) {
								LOG.error("agent details are not available, without agent where can i run? please make an entry in Agent Details");
								throw new CoreException(
										"agent details are not available, without agent where can i run? please make an entry in Agent Details");
							} else {
								String honourAgentStatus = UtlConf.getProperty("honourAgentStatus", "1");
								boolean hnrStat = LangUtils.isTrue(honourAgentStatus, true);
								if (!agentDetails.isStatus() && hnrStat) {
									LOG.warn("Agent not active and honour status is true");
									throw new CoreException("Agent not active and honour status is true");
								}
								String protocol = agentDetails.getProtocol();
								if ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol)) {
									String domain = protocol + "://" + agentDetails.getIP() + ":" + agentDetails.getPort();
									String url = domain + "/Agent/iTAPMulti";
									HttpData httpData = new HttpData(url, 0, null);
									try {
										// httpData.setTimeoutMilli(60000);
										LOG.info("URL : " + url);
										LOG.info("SchedulerLaneDetails => number of clones : " + schedulerLaneDetails.getClones()
												+ ", RampUpDelayTime in seconds : " + schedulerLaneDetails.getRampUpDelay() + ", number of iterations : "
												+ schedulerLaneDetails.getIterations());
										NetSend.sendObjects(schedulerLaneDetails.getLaneType(), SchedulerExecution.runModeSingleVm, httpData,
												(Serializable) schedulerLaneDetails);
										scheduler.setStatus("Y");
										long updateCount = mainService.updateSchedulerStatus(scheduler);
										if ((int) updateCount != 0) {
											insertSchedulerBackUpData(scheduler);
										}
									} catch (Exception e) {
										LOG.error("laneExecution(List<Integer>)", e); //$NON-NLS-1$

										int count = checkFrequency(scheduler, oldTimestamp);
										if (count != 0) {
											insertSchedulerBackUpData(scheduler);
										}
									}
								}
							}
						}
					}
				} else {
					AgentDetails agentDetails = null;
					try {
						agentDetails = this.mainService.getAgentDetailsById(scheduler.getAgentID());
					} catch (ServiceException e) {
						LOG.error("laneExecution(List<Integer>)", e); //$NON-NLS-1$

						throw new CoreException(e.getMessage());
					}
					if (agentDetails == null) {
						LOG.error("agent details are not available, without agent where can i run? please make an entry in Agent Details");
						throw new CoreException("agent details are not available, without agent where can i run? please make an entry in Agent Details");
					} else {
						String honourAgentStatus = UtlConf.getProperty("honourAgentStatus", "1");
						boolean hnrStat = LangUtils.isTrue(honourAgentStatus, true);
						if (!agentDetails.isStatus() && hnrStat) {
							LOG.warn("Agent not active and honour status is true");
							throw new CoreException("Agent not active and honour status is true");
						}
						String protocol = agentDetails.getProtocol();
						if ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol)) {
							String domain = protocol + "://" + agentDetails.getIP() + ":" + agentDetails.getPort();
							String url = domain + "/Agent/iTAP";
							HttpData httpData = new HttpData(url, 0, null);
							try {
								// httpData.setTimeoutMilli(60000);
								LOG.info("URL : " + url);
								LOG.info("Scheduler with out lanes => " + scheduler.getSchedulerName());
								NetSend.sendObjects(scheduler.getSchedulerName(), SchedulerExecution.runModeSingleVm, httpData, (Serializable) scheduler);
								scheduler.setStatus("Y");
								long updateCount = mainService.updateSchedulerStatus(scheduler);
								if ((int) updateCount != 0) {
									insertSchedulerBackUpData(scheduler);
								}
							} catch (Exception e) {
								LOG.error("laneExecution(List<Integer>)", e); //$NON-NLS-1$

								int count = checkFrequency(scheduler, oldTimestamp);
								if (count != 0) {
									insertSchedulerBackUpData(scheduler);
								}
							}
						}
					}
				}
			} else {
				throw new CoreException("Scheduler Details are not found..!!!");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("laneExecution(List<Integer>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Check frequency.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @param timestamp
	 *            the timestamp
	 * @return the int
	 * @throws CoreException
	 *             the core exception
	 */
	private int checkFrequency(Scheduler scheduler, Timestamp timestamp) throws CoreException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkFrequency(Scheduler, Timestamp) - start"); //$NON-NLS-1$
		}

		long count = 0L;
		String frequencyValue = scheduler.getFrequency();
		long frequencyInMin = Long.parseLong(frequencyValue);
		String retry = UtlConf.getProperty("frequency.retryCount", "3");
		int retryCount = Integer.parseInt(retry);
		long frquencyDivisionMilli = ((Math.round(frequencyInMin / retryCount)) * 60000);
		int failureCount = scheduler.getFailureCount();
		scheduler.setFailureCount(failureCount + 1);
		long oldTime = timestamp.getTime();
		long newTime = oldTime + frquencyDivisionMilli;
		scheduler.setScheduleTime(new Timestamp(newTime));
		try {
			mainService.updateSchedulerFailureCountWithTime(scheduler);
			if (failureCount >= retryCount - 1) {
				scheduler.setStatus("F");
				count = mainService.updateSchedulerStatus(scheduler);
			}
			int returnint = (int) count;
			if (LOG.isDebugEnabled()) {
				LOG.debug("checkFrequency(Scheduler, Timestamp) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (ServiceException e1) {
			LOG.error("checkFrequency(Scheduler, Timestamp)", e1); //$NON-NLS-1$

			throw new CoreException(e1.getMessage());
		}
	}

	/**
	 * Insert scheduler back up data.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @throws CoreException
	 *             the core exception
	 */
	private void insertSchedulerBackUpData(Scheduler scheduler) throws CoreException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerBackUpData(Scheduler) - start"); //$NON-NLS-1$
		}

		SchedulerBackup backup = new SchedulerBackup();
		try {
			BeanUtils.copyProperties(backup, scheduler);
			mainService.insertSchedulerGetKey(backup);
		} catch (IllegalAccessException e) {
			LOG.error("insertSchedulerBackUpData(Scheduler)", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		} catch (InvocationTargetException e) {
			LOG.error("insertSchedulerBackUpData(Scheduler)", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		} catch (ServiceException e) {
			LOG.error("insertSchedulerBackUpData(Scheduler)", e); //$NON-NLS-1$

			throw new CoreException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerBackUpData(Scheduler) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the lane listener.
	 * 
	 * @return the lane listener
	 */
	public static final LaneListener getLaneListener() {
		return laneListener;
	}

	/**
	 * Sets the lane listener.
	 * 
	 * @param laneListener
	 *            the new lane listener
	 */
	public static final void setLaneListener(LaneListener laneListener) {
		SchedulerExecution.laneListener = laneListener;
	}

}
