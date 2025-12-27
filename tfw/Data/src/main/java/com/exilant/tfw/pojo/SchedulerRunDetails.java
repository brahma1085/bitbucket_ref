package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.output.LaneResults;
import com.exilant.tfw.pojo.output.TestPlanResult;

/**
 * The Class SchedulerRunDetails.
 * 
 * @author mohammedfirdos
 */
public class SchedulerRunDetails implements Serializable {

	/** SchedulerRunDetails Entity. */
	private static final long serialVersionUID = 1L;

	/** The test run id. */
	private int testRunID;

	/** The test plan id. */
	private int testPlanID;

	/** The test data id. */
	private int testDataID;

	/** The schedule id. */
	private int scheduleID;

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

	/** The app id. */
	private int appID;

	/** The run time. */
	private Date runTime;

	/** The test plan. */
	private TestPlan testPlan;

	/** The test plan result. */
	private TestPlanResult testPlanResult;

	/** The lane results list. */
	private List<LaneResults> laneResultsList = new ArrayList<LaneResults>(0);

	/**
	 * Gets the test plan result.
	 * 
	 * @return the test plan result
	 */
	public TestPlanResult getTestPlanResult() {
		return testPlanResult;
	}

	/**
	 * Sets the test plan result.
	 * 
	 * @param testPlanResult
	 *            the new test plan result
	 */
	public void setTestPlanResult(TestPlanResult testPlanResult) {
		this.testPlanResult = testPlanResult;
	}

	/**
	 * Gets the lane results list.
	 * 
	 * @return the lane results list
	 */
	public List<LaneResults> getLaneResultsList() {
		return laneResultsList;
	}

	/**
	 * Sets the lane results list.
	 * 
	 * @param laneResultsList
	 *            the new lane results list
	 */
	public void setLaneResultsList(List<LaneResults> laneResultsList) {
		this.laneResultsList = laneResultsList;
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
	 * Gets the test run id.
	 * 
	 * @return the testRunID
	 */
	public int getTestRunID() {
		return testRunID;
	}

	/**
	 * Sets the test run id.
	 * 
	 * @param testRunID
	 *            the testRunID to set
	 */
	public void setTestRunID(int testRunID) {
		this.testRunID = testRunID;
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
	 * Gets the run time.
	 * 
	 * @return the runTime
	 */
	public Date getRunTime() {
		return runTime;
	}

	/**
	 * Sets the run time.
	 * 
	 * @param runTime
	 *            the runTime to set
	 */
	public void setRunTime(Date runTime) {
		this.runTime = runTime;
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
	 * @param result
	 *            the result to set
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
	 * @param startDateTime
	 *            the startDateTime to set
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
	 * @param endDateTime
	 *            the endDateTime to set
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
	 * @param percentagePassCount
	 *            the percentagePassCount to set
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
	 * @param percentageFailCount
	 *            the percentageFailCount to set
	 */
	public void setPercentageFailCount(int percentageFailCount) {
		this.percentageFailCount = percentageFailCount;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SchedulerRunDetails [TestRunID=" + testRunID + ", TestPlanID=" + testPlanID + ", TestDataID=" + testDataID + ", ScheduleID=" + scheduleID
				+ ", Result=" + result + ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime + ", PercentagePassCount="
				+ percentagePassCount + ", PercentageFailCount=" + percentageFailCount + ", AppID=" + appID + ", RunTime=" + runTime + "]";
	}

}
