package com.tfw.exilant.ITAP;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.AgentDetails;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerLaneDetails;
import com.exilant.tfw.pojo.def.ScheduledJobs;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class TestExecutionController.
 */
@Controller
public class TestExecutionController {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestExecutionController.class);

	/** The str buf. */
	static StringBuffer strBuf = new StringBuffer();

	/** The date. */
	java.util.Date date = new java.util.Date();

	/** The sql today. */
	java.sql.Date sqlToday = new java.sql.Date(date.getTime());

	/** The main service. */
	private MainService mainService;

	/** The input service. */
	private InputService inputService;

	/**
	 * Gets the input service.
	 * 
	 * @return the input service
	 */
	public InputService getInputService() {
		return inputService;
	}

	/**
	 * Sets the input service.
	 * 
	 * @param inputService
	 *            the new input service
	 */
	public void setInputService(InputService inputService) {
		this.inputService = inputService;
	}

	/**
	 * Gets the main service.
	 * 
	 * @return the main service
	 */
	public MainService getMainService() {
		return mainService;
	}

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/** The json object. */
	JSONObject jsonObject = new JSONObject(strBuf);

	/**
	 * Adds the test execution.
	 * 
	 * @param testExeData
	 *            the test exe data
	 */
	@RequestMapping(value = "/addTestExection", method = RequestMethod.POST)
	public @ResponseBody
	void addTestExecution(@RequestBody Map<String, String> testExeData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestExecution(Map<String,String>) - start"); //$NON-NLS-1$
		}

		LOG.info("Entering addTestExecution controller !!!");
		try {

			LOG.info("Printing data recieved from client :: " + testExeData);
			JSONObject jsonObjectAddTest = new JSONObject(testExeData);

			int appId = (Integer) jsonObjectAddTest.get("appID");
			String testSchedulerName = (String) jsonObjectAddTest.get("schedulerName");
			int planId = (Integer) jsonObjectAddTest.get("testPlanID");
			int dataId = (Integer) jsonObjectAddTest.get("testDataID");
			int agentID = (Integer) jsonObjectAddTest.get("agentID");
			String testScheduleTime = (String) jsonObjectAddTest.get("scheduleTime");
			String testFrequency = (String) jsonObjectAddTest.get("frequency");
			String testNotifications = (String) jsonObjectAddTest.get("notifications");
			Boolean testMultiLanes = (Boolean) jsonObjectAddTest.get("multiLanes");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			java.util.Date parsedDate = dateFormat.parse(testScheduleTime);
			java.sql.Timestamp schedulertimestamp = new java.sql.Timestamp(parsedDate.getTime());
			LOG.info("ScheduleTime" + schedulertimestamp);

			Timestamp timestamp = Timestamp.valueOf(testScheduleTime);
			LOG.info("testScheduleTime" + timestamp);

			Scheduler scheduler = new Scheduler();

			scheduler.setAppID(appId);
			scheduler.setSchedulerName(testSchedulerName);
			scheduler.setTestPlanID(planId);
			scheduler.setTestDataID(dataId);
			scheduler.setNotifications(testNotifications);
			scheduler.setFrequency(testFrequency);
			scheduler.setScheduleTime(schedulertimestamp);
			scheduler.setStatus("N");
			scheduler.setMultiLanes(testMultiLanes);
			scheduler.setCreatedBy(DataConstants.DEFAULT_USER);
			scheduler.setCreatedDateTime(sqlToday);
			scheduler.setUpdatedBy(DataConstants.DEFAULT_USER);
			scheduler.setUpdatedDateTime(sqlToday);
			scheduler.setAgentID(agentID);

			LOG.info("Scheduler Pojo String ::" + scheduler.toString());
			long schedulerId = mainService.insertSchedulerGetKey(scheduler);
			LOG.info("Scheduler ID ::" + schedulerId);

			if (testMultiLanes) {
				@SuppressWarnings("unchecked")
				List<ArrayList<String>> multiLanedata = (List<ArrayList<String>>) jsonObjectAddTest.get("mainArr");
				SchedulerLaneDetails schedulerLaneDetails = new SchedulerLaneDetails();
				LOG.info("multiLanedata" + multiLanedata.toString());

				for (int i = 0; i < multiLanedata.size(); i++) {
					List<String> laneData = (List<String>) multiLanedata.get(i);
					LOG.info("laneData" + laneData.toString());
					String parts = laneData.toString();
					String[] laneDetails = parts.split(",");

					int testScheduleId = (int) schedulerId;
					int agentIDs = Integer.parseInt(laneDetails[2].trim());
					int clones = Integer.parseInt(laneDetails[3].trim());
					int iterations = Integer.parseInt(laneDetails[4].trim());
					int rampUp = Integer.parseInt(laneDetails[5].trim());
					int duration = Integer.parseInt(laneDetails[6].replace("]", "").trim());

					schedulerLaneDetails.setScheduleID(testScheduleId);
					schedulerLaneDetails.setLaneUserName(laneDetails[0].replace("[", "").trim());
					schedulerLaneDetails.setLaneType(laneDetails[1].trim());
					schedulerLaneDetails.setAgentID(agentIDs);
					schedulerLaneDetails.setClones(clones);
					schedulerLaneDetails.setIterations(iterations);
					schedulerLaneDetails.setRampUpDelay(rampUp);
					schedulerLaneDetails.setDuration(duration);
					schedulerLaneDetails.setCreatedBy(DataConstants.DEFAULT_USER);
					schedulerLaneDetails.setCreatedDateTime(sqlToday);
					schedulerLaneDetails.setUpdatedBy(DataConstants.DEFAULT_USER);
					schedulerLaneDetails.setUpdatedDateTime(sqlToday);
					LOG.info("MultiLane Pojo String ::" + schedulerLaneDetails.toString());
					mainService.insertSchedulerLaneDetails(schedulerLaneDetails);
				}
			}

		} catch (Exception e) {
			LOG.error("addTestExecution(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestExecution(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the scheduler.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the scheduler
	 */
	@RequestMapping(value = "/getTestExectionPlanId", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getScheduler(@RequestBody String appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduler(String) - start"); //$NON-NLS-1$
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int appID = Integer.parseInt(appId);
			Object planName = inputService.getTestPlanNameFilterByAppId(appID);
			Object dataName = inputService.getTestDataDescriptionFilterByAppId(appID);
			map.put("plan", planName);
			map.put("data", dataName);
			LOG.info("Map data=" + map.toString());
		} catch (Exception e) {
			LOG.error("Error occured ", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduler(String) - end"); //$NON-NLS-1$
		}
		return map;
	}

	/**
	 * Gets the test exection lane run type.
	 * 
	 * @return the test exection lane run type
	 */
	@RequestMapping(value = "/getTestExectionRunId", method = RequestMethod.GET)
	public @ResponseBody
	List<com.exilant.tfw.pojo.Runner> getTestExectionLaneRunType() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestExectionLaneRunType() - start"); //$NON-NLS-1$
		}
		List<com.exilant.tfw.pojo.Runner> returnList = null;
		try {
			returnList = mainService.getRunner();
		} catch (Exception e) {
			LOG.error("Error Occured ", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestExectionLaneRunType() - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/**
	 * Gets the agent data.
	 * 
	 * @return the agent data
	 */
	@RequestMapping(value = "/getAgentData", method = RequestMethod.GET)
	public @ResponseBody
	List<AgentDetails> getAgentData() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentData() - start"); //$NON-NLS-1$
		}
		List<AgentDetails> returnList = null;
		try {
			returnList = mainService.getAgentDetails();
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentData() - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/**
	 * Test execution run suite.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the list
	 */
	@RequestMapping(value = "/getTestExecutionRunSuites", method = RequestMethod.POST)
	public @ResponseBody
	List<ScheduledJobs> testExecutionRunSuite(@RequestBody String appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("TestExecutionRunSuite(String) - start"); //$NON-NLS-1$
		}
		int appID = Integer.parseInt(appId);
		List<ScheduledJobs> jobs = new ArrayList<ScheduledJobs>();
		try {
			jobs = mainService.getScheduledJobsForApp(appID);
			if (!(jobs.isEmpty())) {
				for (int i = 0; i < jobs.size(); i++) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss");
					if (jobs.get(i).getSchedulerStartTime() != null) {
						jobs.get(i).setSchedulerStartDate(simpleDateFormat.format(jobs.get(i).getSchedulerStartTime()));
					}
					if (jobs.get(i).getScheduleEndTime() != null) {
						jobs.get(i).setSchedulerEndDate(simpleDateFormat.format(jobs.get(i).getScheduleEndTime()));
					}
					if (jobs.get(i).getNextScheduleTime() != null) {
						jobs.get(i).setNextScheduledDate(simpleDateFormat.format(jobs.get(i).getNextScheduleTime()));
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error Occured ", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("TestExecutionRunSuite(String) - end"); //$NON-NLS-1$
		}
		return jobs;
	}

	/**
	 * Gets the all running jobs for app.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the all running jobs for app
	 */
	@RequestMapping(value = "/getAllRunningJobsiTAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ScheduledJobs> getAllRunningJobsForApp(@RequestBody String appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRunningJobsForApp(String) - start"); //$NON-NLS-1$
		}
		List<ScheduledJobs> jobs = new ArrayList<ScheduledJobs>();
		try {
			int appID = Integer.parseInt(appId);
			jobs = mainService.getScheduledRunningJobsForApp(appID);
			LOG.info("Running Jobs:: " + jobs.size());
			if (!(jobs.isEmpty())) {
				for (int i = 0; i < jobs.size(); i++) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss");
					if (jobs.get(i).getSchedulerStartTime() != null) {
						jobs.get(i).setSchedulerStartDate(simpleDateFormat.format(jobs.get(i).getSchedulerStartTime()));
					}
					if (jobs.get(i).getNextScheduleTime() != null) {
						jobs.get(i).setNextScheduledDate(simpleDateFormat.format(jobs.get(i).getNextScheduleTime()));
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error Occured ", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllRunningJobsForApp(String) - end"); //$NON-NLS-1$
		}
		return jobs;
	}

	/**
	 * Gets the all schedules jobs for app.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the all schedules jobs for app
	 */
	@RequestMapping(value = "/getAllScheduledJobsiTAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ScheduledJobs> getAllSchedulesJobsForApp(@RequestBody String appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllSchedulesJobsForApp(String) - start"); //$NON-NLS-1$
		}
		int appID = Integer.parseInt(appId);
		List<ScheduledJobs> jobs = new ArrayList<ScheduledJobs>();
		try {
			jobs = mainService.getScheduledNotRunJobsForApp(appID);
		} catch (ServiceException se) {
			LOG.error("getAllSchedulesJobsForApp(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllSchedulesJobsForApp(String) - end"); //$NON-NLS-1$
		}
		return jobs;
	}

	/**
	 * Gets the all completed jobsi tap.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the all completed jobsi tap
	 */
	@RequestMapping(value = "/getAllCompletedJobsiTAP", method = RequestMethod.POST)
	public @ResponseBody
	List<ScheduledJobs> getAllCompletedJobsiTAP(@RequestBody String appId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllCompletedJobsiTAP(String) - start"); //$NON-NLS-1$
		}
		List<ScheduledJobs> jobs = new ArrayList<ScheduledJobs>();
		try {
			int appID = Integer.parseInt(appId);
			jobs = mainService.getScheduledCompletedJobsForApp(appID);
			LOG.info("Completed Jobs:: " + jobs.size());
			if (!(jobs.isEmpty())) {
				for (int i = 0; i < jobs.size(); i++) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss");
					if (jobs.get(i).getSchedulerStartTime() != null) {
						jobs.get(i).setSchedulerStartDate(simpleDateFormat.format(jobs.get(i).getSchedulerStartTime()));
					}
					if (jobs.get(i).getScheduleEndTime() != null) {
						jobs.get(i).setSchedulerEndDate(simpleDateFormat.format(jobs.get(i).getScheduleEndTime()));
					}
					if (jobs.get(i).getNextScheduleTime() != null) {
						jobs.get(i).setNextScheduledDate(simpleDateFormat.format(jobs.get(i).getNextScheduleTime()));
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error Occured ", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllCompletedJobsiTAP(String) - end"); //$NON-NLS-1$
		}
		return jobs;
	}

	/**
	 * Update scheduler time.
	 * 
	 * @param scheduleId
	 *            the schedule id
	 */
	@RequestMapping(value = "/updateSchedulerTime", method = RequestMethod.POST)
	public @ResponseBody
	void updateSchedulerTime(@RequestBody String scheduleId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerTime(String) - start"); //$NON-NLS-1$
		}
		try {
			int scheduleIds = Integer.parseInt(scheduleId);
			Scheduler schedulerList = mainService.getSchedulerById(scheduleIds);
			Scheduler schedulerData = new Scheduler();
			schedulerData.setAgentID(schedulerList.getAgentID());
			schedulerData.setAppID(schedulerList.getAppID());
			schedulerData.setFailureCount(schedulerList.getFailureCount());
			schedulerData.setFrequency(schedulerList.getFrequency());
			schedulerData.setMultiLanes(schedulerList.isMultiLanes());
			schedulerData.setNotifications(schedulerList.getNotifications());
			schedulerData.setFrequency(schedulerList.getFrequency());
			schedulerData.setStatus(schedulerList.getStatus());
			Date date1 = new Date();
			Timestamp timestamp = new Timestamp(date1.getTime());
			LOG.info("latest Scheduler time=" + timestamp);
			schedulerData.setScheduleTime(timestamp);
			schedulerData.setTestDataID(schedulerList.getTestDataID());
			schedulerData.setTestPlanID(schedulerList.getTestPlanID());
			schedulerData.setSchedulerName(schedulerList.getSchedulerName());
			schedulerData.setCreatedBy(DataConstants.DEFAULT_USER);
			schedulerData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			schedulerData.setUpdatedBy(DataConstants.DEFAULT_USER);
			schedulerData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			mainService.insertScheduler(schedulerData);
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerTime(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the scheduler lane details by schedule id.
	 * 
	 * @param scheduleId
	 *            the schedule id
	 * @return the scheduler lane details by schedule id
	 */
	@RequestMapping(value = "/getSchedulerLaneDetailsByScheduleId", method = RequestMethod.POST)
	public @ResponseBody
	List<SchedulerLaneDetails> getSchedulerLaneDetailsByScheduleId(@RequestBody String scheduleId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsByScheduleId(String) - start"); //$NON-NLS-1$
		}
		int scheduleIds = Integer.parseInt(scheduleId);
		List<SchedulerLaneDetails> schedulerLaneDetails = new ArrayList<SchedulerLaneDetails>();
		try {
			schedulerLaneDetails = mainService.getSchedulerLaneDetailsByScheduleId(scheduleIds);
			LOG.info("SchedulerLaneDetails list ::" + schedulerLaneDetails.toString());
		} catch (ServiceException se) {
			LOG.error("getSchedulerLaneDetailsByScheduleId(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsByScheduleId(String) - end"); //$NON-NLS-1$
		}
		return schedulerLaneDetails;
	}

	/**
	 * Edits the scheduler.
	 * 
	 * @param scheduleId
	 *            the schedule id
	 * @return the map< string,? extends object>
	 */
	@RequestMapping(value = "/editScheduler", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> editScheduler(@RequestBody String scheduleId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editScheduler(String) - start"); //$NON-NLS-1$
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int scheduleID = Integer.parseInt(scheduleId);
			Scheduler schedulerDetails = mainService.getSchedulerById(scheduleID);
			if (schedulerDetails != null) {
				Object schedulerLaneDetails = mainService.getSchedulerLaneDetailsByScheduleId(scheduleID);
				Timestamp stamp = schedulerDetails.getScheduleTime();
				String scheduleTime = stamp.toString();
				String[] dateTime = null;
				dateTime = scheduleTime.split(" ");
				String dateStr = dateTime[0];
				String timeStr = dateTime[1];
				String[] scehTime = null;
				scehTime = timeStr.split(":");
				String hr = scehTime[0];
				String min = scehTime[1];
				String sec = scehTime[2];
				String secs = sec.split("\\.")[0];
				LOG.info("scheduleDate: " + dateStr);
				map.put("schedulerDetails", schedulerDetails);
				map.put("scheduleTime", scheduleTime);
				map.put("scheduleDate", dateStr);
				map.put("scheduleTimes", timeStr);
				map.put("scheduleHr", hr);
				map.put("scheduleMin", min);
				map.put("scheduleSec", secs);
				map.put("schedulerLaneDetails", schedulerLaneDetails);
				LOG.info("schedulerDetails data=" + schedulerDetails.toString());
			}
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		LOG.info(" Scheduler Map " + map);
		if (LOG.isDebugEnabled()) {
			LOG.debug("editScheduler(String) - end"); //$NON-NLS-1$
		}
		return map;
	}

	/**
	 * Update test exection.
	 * 
	 * @param testExeData
	 *            the test exe data
	 */
	@RequestMapping(value = "/updateTestExection", method = RequestMethod.POST)
	public @ResponseBody
	void updateTestExection(@RequestBody Map<String, String> testExeData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestExection(Map<String,String>) - start"); //$NON-NLS-1$
		}

		LOG.info("Entering updateTestExection controller !!!");
		try {

			LOG.info("Printing data recieved from client :: " + testExeData);
			JSONObject jsonObjectTestExe = new JSONObject(testExeData);

			int scheduleId = (Integer) jsonObjectTestExe.get("scheduleID");
			String testSchedulerName = (String) jsonObjectTestExe.get("schedulerName");
			int planId = (Integer) jsonObjectTestExe.get("testExePlanID");
			int dataId = (Integer) jsonObjectTestExe.get("testDataID");
			String testScheduleTime = (String) jsonObjectTestExe.get("scheduleTime");
			String testFrequency = (String) jsonObjectTestExe.get("frequency");
			String testNotifications = (String) jsonObjectTestExe.get("notifications");
			Boolean testMultiLanes = (Boolean) jsonObjectTestExe.get("multiLanes");
			int agentIDs = (Integer) jsonObjectTestExe.get("agentID");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			java.util.Date parsedDate = dateFormat.parse(testScheduleTime);
			java.sql.Timestamp schedulertimestamp = new java.sql.Timestamp(parsedDate.getTime());
			LOG.info("ScheduleTime" + schedulertimestamp);

			Timestamp timestamp = Timestamp.valueOf(testScheduleTime);
			LOG.info("testScheduleTime" + timestamp);

			Scheduler schedulerData = new Scheduler();

			schedulerData.setScheduleID(scheduleId);
			schedulerData.setSchedulerName(testSchedulerName);
			schedulerData.setTestPlanID(planId);
			schedulerData.setTestDataID(dataId);
			schedulerData.setNotifications(testNotifications);
			schedulerData.setFrequency(testFrequency);
			schedulerData.setScheduleTime(schedulertimestamp);
			schedulerData.setStatus("N");
			schedulerData.setMultiLanes(testMultiLanes);
			schedulerData.setAgentID(agentIDs);
			schedulerData.setUpdatedBy(DataConstants.DEFAULT_USER);
			schedulerData.setUpdatedDateTime(sqlToday);

			LOG.info("Update Scheduler Pojo String ::" + schedulerData.toString());
			mainService.updateScheduler(schedulerData);

			if (testMultiLanes) {
				@SuppressWarnings("unchecked")
				List<ArrayList<String>> multiLanedata = (List<ArrayList<String>>) jsonObjectTestExe.get("mainArrUpdate");
				SchedulerLaneDetails laneDetailsData = new SchedulerLaneDetails();
				SchedulerLaneDetails laneDetailsDatas = new SchedulerLaneDetails();
				LOG.info("multiLanedata" + multiLanedata.toString());

				for (int i = 0; i < multiLanedata.size(); i++) {
					List<String> laneData = (List<String>) multiLanedata.get(i);
					LOG.info("laneData" + laneData.toString());

					int laneDataSize = laneData.size();
					if (laneDataSize == 8) {

						String parts = laneData.toString();
						String[] laneDetails = parts.split(",");

						int testScheduleLaneIds = Integer.parseInt(laneDetails[6].trim());
						int agentIds = Integer.parseInt(laneDetails[7].replace("]", "").trim());
						int clones = Integer.parseInt(laneDetails[2].trim());
						int iterations = Integer.parseInt(laneDetails[3].trim());
						int rampUp = Integer.parseInt(laneDetails[4].trim());
						int duration = Integer.parseInt(laneDetails[5].trim());

						laneDetailsData.setScheduleLaneID(testScheduleLaneIds);
						laneDetailsData.setLaneUserName(laneDetails[0].replace("[", "").trim());
						laneDetailsData.setLaneType(laneDetails[1].trim());
						laneDetailsData.setAgentID(agentIds);
						laneDetailsData.setClones(clones);
						laneDetailsData.setIterations(iterations);
						laneDetailsData.setRampUpDelay(rampUp);
						laneDetailsData.setDuration(duration);
						laneDetailsData.setUpdatedBy(DataConstants.DEFAULT_USER);
						laneDetailsData.setUpdatedDateTime(sqlToday);
						LOG.info("MultiLane Pojo String ::" + laneDetailsData.toString());
						mainService.updateSchedulerLaneDetails(laneDetailsData);

					} else {

						String parts = laneData.toString();
						String[] laneDetails = parts.split(",");

						int testScheduleId = (int) scheduleId;
						int agentIds = Integer.parseInt(laneDetails[8].trim());
						int clones = Integer.parseInt(laneDetails[9].trim());
						int iterations = Integer.parseInt(laneDetails[10].trim());
						int rampUp = Integer.parseInt(laneDetails[11].trim());
						int duration = Integer.parseInt(laneDetails[12].replace("]", "").trim());

						laneDetailsDatas.setScheduleID(testScheduleId);
						laneDetailsDatas.setLaneUserName(laneDetails[6].trim());
						laneDetailsDatas.setLaneType(laneDetails[7].trim());
						laneDetailsDatas.setAgentID(agentIds);
						laneDetailsDatas.setClones(clones);
						laneDetailsDatas.setIterations(iterations);
						laneDetailsDatas.setRampUpDelay(rampUp);
						laneDetailsDatas.setDuration(duration);
						laneDetailsDatas.setCreatedBy(DataConstants.DEFAULT_USER);
						laneDetailsDatas.setCreatedDateTime(sqlToday);
						laneDetailsDatas.setUpdatedBy(DataConstants.DEFAULT_USER);
						laneDetailsDatas.setUpdatedDateTime(sqlToday);
						LOG.info("MultiLane Pojo String ::" + laneDetailsDatas.toString());
						mainService.insertSchedulerLaneDetails(laneDetailsDatas);
					}
				}
			}

		} catch (Exception e) {
			LOG.error("updateTestExection(Map<String,String>)", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestExection(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

}
