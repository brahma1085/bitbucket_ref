package com.sssoft.isatt.data.pojo.def;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The Class ScheduledJobs.
 */
public class ScheduledJobs implements Serializable {

	/** Default Serial verison id. */
	private static final long serialVersionUID = 1L;

	/** The scheduler id. */
	private int schedulerId;

	/** The test plan id. */
	private int testPlanId;

	/** The test data id. */
	private int testDataId;

	/** The app id. */
	private int appId;

	/** The agent id. */
	private int agentId;

	/** The frequency. */
	private int frequency;

	/** The duration in seconds. */
	private int durationInSeconds;

	/** The failure count. */
	private int failureCount;

	/** The result. */
	private boolean result;

	/** The Status. */
	private String Status;

	/** The scheduler name. */
	private String schedulerName;

	/** The plan name. */
	private String planName;

	/** The agent name. */
	private String agentName;

	/** The test data description. */
	private String testDataDescription;

	/** The next schedule time. */
	private Timestamp nextScheduleTime;

	/** The scheduler start time. */
	private Timestamp schedulerStartTime;

	/** The schedule end time. */
	private Timestamp scheduleEndTime;
	
	/** The schedule start Date. */
	private String schedulerStartDate;
	
	/** The schedule end Date. */
	private String schedulerEndDate;
	
	/** The schedule end time. */
	private String nextScheduledDate;
	

	/**
	 * Gets the scheduler id.
	 * 
	 * @return the scheduler id
	 */
	public int getSchedulerId() {
		return schedulerId;
	}

	/**
	 * Sets the scheduler id.
	 * 
	 * @param schedulerId
	 *            the new scheduler id
	 */
	public void setSchedulerId(int schedulerId) {
		this.schedulerId = schedulerId;
	}

	/**
	 * Gets the test plan id.
	 * 
	 * @return the test plan id
	 */
	public int getTestPlanId() {
		return testPlanId;
	}

	/**
	 * Sets the test plan id.
	 * 
	 * @param testPlanId
	 *            the new test plan id
	 */
	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}

	/**
	 * Gets the test data id.
	 * 
	 * @return the test data id
	 */
	public int getTestDataId() {
		return testDataId;
	}

	/**
	 * Sets the test data id.
	 * 
	 * @param testDataId
	 *            the new test data id
	 */
	public void setTestDataId(int testDataId) {
		this.testDataId = testDataId;
	}

	/**
	 * Gets the app id.
	 * 
	 * @return the app id
	 */
	public int getAppId() {
		return appId;
	}

	/**
	 * Sets the app id.
	 * 
	 * @param appId
	 *            the new app id
	 */
	public void setAppId(int appId) {
		this.appId = appId;
	}

	/**
	 * Gets the agent id.
	 * 
	 * @return the agent id
	 */
	public int getAgentId() {
		return agentId;
	}

	/**
	 * Sets the agent id.
	 * 
	 * @param agentId
	 *            the new agent id
	 */
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	/**
	 * Gets the frequency.
	 * 
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Sets the frequency.
	 * 
	 * @param frequency
	 *            the new frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * Gets the duration in seconds.
	 * 
	 * @return the duration in seconds
	 */
	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	/**
	 * Sets the duration in seconds.
	 * 
	 * @param durationInSeconds
	 *            the new duration in seconds
	 */
	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	/**
	 * Gets the failure count.
	 * 
	 * @return the failure count
	 */
	public int getFailureCount() {
		return failureCount;
	}

	/**
	 * Sets the failure count.
	 * 
	 * @param failureCount
	 *            the new failure count
	 */
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	/**
	 * Checks if is result.
	 * 
	 * @return true, if is result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            the new result
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		Status = status;
	}

	/**
	 * Gets the scheduler name.
	 * 
	 * @return the scheduler name
	 */
	public String getSchedulerName() {
		return schedulerName;
	}

	/**
	 * Sets the scheduler name.
	 * 
	 * @param schedulerName
	 *            the new scheduler name
	 */
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	/**
	 * Gets the plan name.
	 * 
	 * @return the plan name
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * Sets the plan name.
	 * 
	 * @param planName
	 *            the new plan name
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * Gets the agent name.
	 * 
	 * @return the agent name
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Sets the agent name.
	 * 
	 * @param agentName
	 *            the new agent name
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * Gets the test data description.
	 * 
	 * @return the test data description
	 */
	public String getTestDataDescription() {
		return testDataDescription;
	}

	/**
	 * Sets the test data description.
	 * 
	 * @param testDataDescription
	 *            the new test data description
	 */
	public void setTestDataDescription(String testDataDescription) {
		this.testDataDescription = testDataDescription;
	}

	/**
	 * Gets the next schedule time.
	 * 
	 * @return the next schedule time
	 */
	public Timestamp getNextScheduleTime() {
		return nextScheduleTime;
	}

	/**
	 * Sets the next schedule time.
	 * 
	 * @param nextScheduleTime
	 *            the new next schedule time
	 */
	public void setNextScheduleTime(Timestamp nextScheduleTime) {
		this.nextScheduleTime = nextScheduleTime;
	}

	/**
	 * Gets the scheduler start time.
	 * 
	 * @return the scheduler start time
	 */
	public Timestamp getSchedulerStartTime() {
		return schedulerStartTime;
	}

	/**
	 * Sets the scheduler start time.
	 * 
	 * @param schedulerStartTime
	 *            the new scheduler start time
	 */
	public void setSchedulerStartTime(Timestamp schedulerStartTime) {
		this.schedulerStartTime = schedulerStartTime;
	}

	/**
	 * Gets the schedule end time.
	 * 
	 * @return the schedule end time
	 */
	public Timestamp getScheduleEndTime() {
		return scheduleEndTime;
	}

	/**
	 * Sets the schedule end time.
	 * 
	 * @param scheduleEndTime
	 *            the new schedule end time
	 */
	public void setScheduleEndTime(Timestamp scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	public String getSchedulerStartDate() {
		return schedulerStartDate;
	}

	public void setSchedulerStartDate(String schedulerStartDate) {
		this.schedulerStartDate = schedulerStartDate;
	}

	public String getSchedulerEndDate() {
		return schedulerEndDate;
	}

	public void setSchedulerEndDate(String schedulerEndDate) {
		this.schedulerEndDate = schedulerEndDate;
	}

	public String getNextScheduledDate() {
		return nextScheduledDate;
	}

	public void setNextScheduledDate(String nextScheduledDate) {
		this.nextScheduledDate = nextScheduledDate;
	}

	@Override
	public String toString() {
		return "ScheduledJobs [schedulerId=" + schedulerId + ", testPlanId=" + testPlanId + ", testDataId=" + testDataId + ", appId=" + appId
				+ ", agentId=" + agentId + ", frequency=" + frequency + ", durationInSeconds=" + durationInSeconds + ", failureCount=" + failureCount
				+ ", result=" + result + ", Status=" + Status + ", schedulerName=" + schedulerName + ", planName=" + planName + ", agentName=" + agentName
				+ ", testDataDescription=" + testDataDescription + ", nextScheduleTime=" + nextScheduleTime + ", schedulerStartTime=" + schedulerStartTime
				+ ", scheduleEndTime=" + scheduleEndTime + "]";
	}

}
