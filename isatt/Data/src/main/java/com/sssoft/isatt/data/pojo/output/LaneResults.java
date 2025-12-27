package com.sssoft.isatt.data.pojo.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.SchedulerRunDetails;

/**
 * The Class LaneResults.
 *
 * @author mohammedfirdos
 */
public class LaneResults implements Serializable {

	/** LaneResults Entity. */
	private static final long serialVersionUID = 1L;

	/** The lane results id. */
	private int laneResultsID;
	
	/** The test run id. */
	private int testRunID;
	
	/** The schedule lane id. */
	private int scheduleLaneID;
	
	/** The agent id. */
	private int agentID;
	
	/** The build version. */
	private String buildVersion;
	
	/** The os. */
	private String os;
	
	/** The result. */
	private boolean result;
	
	/** The start date time. */
	private Timestamp startDateTime;
	
	/** The end date time. */
	private Timestamp endDateTime;
	
	/** The percentage pass count. */
	private int percentagePassCount;
	
	/** The percentage fail count. */
	private int percentageFailCount;

	/** The failure details. */
	private String failureDetails;
	
	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);

	/** The task results. */
	private List<TaskResult> taskResults = new ArrayList<TaskResult>(0);

	/**
	 * Gets the lane results id.
	 *
	 * @return the laneResultsID
	 */
	public int getLaneResultsID() {
		return laneResultsID;
	}

	/**
	 * Sets the lane results id.
	 *
	 * @param laneResultsID the laneResultsID to set
	 */
	public void setLaneResultsID(int laneResultsID) {
		this.laneResultsID = laneResultsID;
	}

	/**
	 * Gets the test run id.
	 *
	 * @return the testRunID
	 */
	public int getTestRunID() {
		return testRunID;
	}

	/**
	 * Gets the task results.
	 *
	 * @return the task results
	 */
	public List<TaskResult> getTaskResults() {
		return taskResults;
	}

	/**
	 * Sets the task results.
	 *
	 * @param taskResults the new task results
	 */
	public void setTaskResults(List<TaskResult> taskResults) {
		this.taskResults = taskResults;
	}

	/**
	 * Sets the test run id.
	 *
	 * @param testRunID the testRunID to set
	 */
	public void setTestRunID(int testRunID) {
		this.testRunID = testRunID;
	}

	/**
	 * Gets the schedule lane id.
	 *
	 * @return the scheduleLaneID
	 */
	public int getScheduleLaneID() {
		return scheduleLaneID;
	}

	/**
	 * Sets the schedule lane id.
	 *
	 * @param scheduleLaneID the scheduleLaneID to set
	 */
	public void setScheduleLaneID(int scheduleLaneID) {
		this.scheduleLaneID = scheduleLaneID;
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
	 * @param agentID the agentID to set
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * Gets the builds the version.
	 *
	 * @return the buildVersion
	 */
	public String getBuildVersion() {
		return buildVersion;
	}

	/**
	 * Sets the builds the version.
	 *
	 * @param buildVersion the buildVersion to set
	 */
	public void setBuildVersion(String buildVersion) {
		this.buildVersion = buildVersion;
	}

	/**
	 * Gets the os.
	 *
	 * @return the oS
	 */
	public String getOS() {
		return os;
	}

	/**
	 * Sets the os.
	 *
	 * @param oS the oS to set
	 */
	public void setOS(String oS) {
		this.os = oS;
	}

	/**
	 * Checks if is result.
	 *
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * Gets the start date time.
	 *
	 * @return the startDateTime
	 */
	public Timestamp getStartDateTime() {
		return startDateTime;
	}

	/**
	 * Sets the start date time.
	 *
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(Timestamp startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * Gets the end date time.
	 *
	 * @return the endDateTime
	 */
	public Timestamp getEndDateTime() {
		return endDateTime;
	}

	/**
	 * Sets the end date time.
	 *
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(Timestamp endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * Gets the percentage pass count.
	 *
	 * @return the percentagePassCount
	 */
	public int getPercentagePassCount() {
		return percentagePassCount;
	}

	/**
	 * Sets the percentage pass count.
	 *
	 * @param percentagePassCount the percentagePassCount to set
	 */
	public void setPercentagePassCount(int percentagePassCount) {
		this.percentagePassCount = percentagePassCount;
	}

	/**
	 * Gets the percentage fail count.
	 *
	 * @return the percentageFailCount
	 */
	public int getPercentageFailCount() {
		return percentageFailCount;
	}

	/**
	 * Sets the percentage fail count.
	 *
	 * @param percentageFailCount the percentageFailCount to set
	 */
	public void setPercentageFailCount(int percentageFailCount) {
		this.percentageFailCount = percentageFailCount;
	}

	/**
	 * Gets the failure details.
	 *
	 * @return the failureDetails
	 */
	public String getFailureDetails() {
		return failureDetails;
	}

	/**
	 * Sets the failure details.
	 *
	 * @param failureDetails the failureDetails to set
	 */
	public void setFailureDetails(String failureDetails) {
		this.failureDetails = failureDetails;
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
	 * @param schedulerRunDetails the schedulerRunDetails to set
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
		return "LaneResults [TestRunID=" + testRunID + ", ScheduleLaneID=" + scheduleLaneID + ", AgentID=" + agentID + ", BuildVersion=" + buildVersion
				+ ", OS=" + os + ", Result=" + result + ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime + ", PercentagePassCount="
				+ percentagePassCount + ", PercentageFailCount=" + percentageFailCount + "]";
	}

}
