package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.TestData;
import com.exilant.tfw.pojo.input.TestPlan;

/**
 * The Class Scheduler.
 * 
 * @author mohammedfirdos
 */
public class Scheduler implements Serializable {

	/** Scheduler Entity. */
	private static final long serialVersionUID = 1L;

	/** The schedule id. */
	private int scheduleID;

	/** The scheduler name. */
	private String schedulerName;

	/** The test plan id. */
	private int testPlanID;

	/** The test data id. */
	private int testDataID;

	/** The agent id. */
	private int agentID;

	/** The schedule time. */
	private Timestamp scheduleTime;

	/** The status. */
	private String status;

	/** The frequency. */
	private String frequency;

	/** The notifications. */
	private String notifications;

	/** The multi lanes. */
	private boolean multiLanes;

	/** The created by. */
	private String createdBy;

	/** The created date time. */
	private Date createdDateTime;

	/** The updated by. */
	private String updatedBy;

	/** The updated date time. */
	private Date updatedDateTime;

	/** The scheduler lane details. */
	private List<SchedulerLaneDetails> schedulerLaneDetails = new ArrayList<SchedulerLaneDetails>(0);

	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);

	/** The test plan. */
	private TestPlan testPlan;

	/** The test data. */
	private TestData testData;

	/** The app id. */
	private int appID;

	/** The failure count. */
	private int failureCount;

	/**
	 * Gets the test data.
	 * 
	 * @return the test data
	 */
	public TestData getTestData() {
		return testData;
	}

	/**
	 * Sets the test data.
	 * 
	 * @param testData
	 *            the new test data
	 */
	public void setTestData(TestData testData) {
		this.testData = testData;
	}

	/**
	 * Gets the test plan.
	 * 
	 * @return the test plan
	 */
	public TestPlan getTestPlan() {
		return testPlan;
	}

	/**
	 * Sets the test plan.
	 * 
	 * @param testPlan
	 *            the new test plan
	 */
	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	/**
	 * Gets the schedule id.
	 * 
	 * @return the scheduleID
	 */
	public int getScheduleID() {
		return scheduleID;
	}

	/**
	 * Sets the schedule id.
	 * 
	 * @param scheduleID
	 *            the scheduleID to set
	 */
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	/**
	 * Gets the scheduler name.
	 * 
	 * @return the schedulerName
	 */
	public String getSchedulerName() {
		return schedulerName;
	}

	/**
	 * Sets the scheduler name.
	 * 
	 * @param schedulerName
	 *            the schedulerName to set
	 */
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	/**
	 * Gets the test plan id.
	 * 
	 * @return the testPlanID
	 */
	public int getTestPlanID() {
		return testPlanID;
	}

	/**
	 * Sets the test plan id.
	 * 
	 * @param testPlanID
	 *            the testPlanID to set
	 */
	public void setTestPlanID(int testPlanID) {
		this.testPlanID = testPlanID;
	}

	/**
	 * Gets the test data id.
	 * 
	 * @return the testDataID
	 */
	public int getTestDataID() {
		return testDataID;
	}

	/**
	 * Sets the test data id.
	 * 
	 * @param testDataID
	 *            the testDataID to set
	 */
	public void setTestDataID(int testDataID) {
		this.testDataID = testDataID;
	}

	/**
	 * Gets the agent id.
	 * 
	 * @return the agentID
	 */
	public int getAgentID() {
		return agentID;
	}

	/**
	 * Sets the agent id.
	 * 
	 * @param agentID
	 *            the agentID to set
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * Gets the schedule time.
	 * 
	 * @return the scheduleTime
	 */
	public Timestamp getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * Sets the schedule time.
	 * 
	 * @param scheduleTime
	 *            the scheduleTime to set
	 */
	public void setScheduleTime(Timestamp scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the frequency.
	 * 
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * Sets the frequency.
	 * 
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * Gets the notifications.
	 * 
	 * @return the notifications
	 */
	public String getNotifications() {
		return notifications;
	}

	/**
	 * Sets the notifications.
	 * 
	 * @param notifications
	 *            the notifications to set
	 */
	public void setNotifications(String notifications) {
		this.notifications = notifications;
	}

	/**
	 * Checks if is multi lanes.
	 * 
	 * @return the multiLanes
	 */
	public boolean isMultiLanes() {
		return multiLanes;
	}

	/**
	 * Sets the multi lanes.
	 * 
	 * @param multiLanes
	 *            the multiLanes to set
	 */
	public void setMultiLanes(boolean multiLanes) {
		this.multiLanes = multiLanes;
	}

	/**
	 * Gets the created by.
	 * 
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 * 
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date time.
	 * 
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Sets the created date time.
	 * 
	 * @param createdDateTime
	 *            the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the updated by.
	 * 
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 * 
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date time.
	 * 
	 * @return the updatedDateTime
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * Sets the updated date time.
	 * 
	 * @param updatedDateTime
	 *            the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the app id.
	 * 
	 * @return the appID
	 */
	public int getAppID() {
		return appID;
	}

	/**
	 * Sets the app id.
	 * 
	 * @param appID
	 *            the appID to set
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the failure count.
	 * 
	 * @return the failureCount
	 */
	public int getFailureCount() {
		return failureCount;
	}

	/**
	 * Sets the failure count.
	 * 
	 * @param failureCount
	 *            the failureCount to set
	 */
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	/**
	 * Gets the scheduler lane details.
	 * 
	 * @return the schedulerLaneDetails
	 */
	public List<SchedulerLaneDetails> getSchedulerLaneDetails() {
		return schedulerLaneDetails;
	}

	/**
	 * Sets the scheduler lane details.
	 * 
	 * @param schedulerLaneDetails
	 *            the schedulerLaneDetails to set
	 */
	public void setSchedulerLaneDetails(List<SchedulerLaneDetails> schedulerLaneDetails) {
		this.schedulerLaneDetails = schedulerLaneDetails;
	}

	/**
	 * Gets the scheduler run details.
	 * 
	 * @return the schedulerRunDetails
	 */
	public List<SchedulerRunDetails> getSchedulerRunDetails() {
		return schedulerRunDetails;
	}

	/**
	 * Sets the scheduler run details.
	 * 
	 * @param schedulerRunDetails
	 *            the schedulerRunDetails to set
	 */
	public void setSchedulerRunDetails(List<SchedulerRunDetails> schedulerRunDetails) {
		this.schedulerRunDetails = schedulerRunDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Scheduler [ScheduleID=" + scheduleID + ", SchedulerName=" + schedulerName + ", TestPlanID=" + testPlanID + ", TestDataID=" + testDataID
				+ ", agentID=" + agentID + ", ScheduleTime=" + scheduleTime + ", Status=" + status + ", Frequency=" + frequency + ", Notifications="
				+ notifications + ", MultiLanes=" + multiLanes + ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy="
				+ updatedBy + ", UpdatedDateTime=" + updatedDateTime + ", schedulerLaneDetails=" + schedulerLaneDetails + ", schedulerRunDetails="
				+ schedulerRunDetails + ", testPlan=" + testPlan + ", testData=" + testData + ", appID=" + appID + "]";
	}

}
