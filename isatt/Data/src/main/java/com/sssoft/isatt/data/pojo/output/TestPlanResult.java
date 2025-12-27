package com.sssoft.isatt.data.pojo.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.SchedulerRunDetails;

/**
 * The Class TestPlanResult.
 *
 * @author mohammedfirdos
 */
public class TestPlanResult implements Serializable {

	/** TestPlanResult Entity. */
	private static final long serialVersionUID = 1L;

	/** The test plan result id. */
	private int testPlanResultID;
	
	/** The test plan id. */
	private int testPlanID;
	
	/** The test plan run name. */
	private String testPlanRunName;
	
	/** The task id. */
	private int taskID;
	
	/** The test run id. */
	private int testRunID;
	
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

	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The test suite result list. */
	private List<TestSuiteResult> testSuiteResultList = new ArrayList<TestSuiteResult>();
	
	/** The functional list. */
	private List<AppFunctionality> functionalList = new ArrayList<AppFunctionality>(0);;

	/**
	 * Gets the functional list.
	 *
	 * @return the functional list
	 */
	public List<AppFunctionality> getFunctionalList() {
		return functionalList;
	}

	/**
	 * Sets the functional list.
	 *
	 * @param functionalList the new functional list
	 */
	public void setFunctionalList(List<AppFunctionality> functionalList) {
		this.functionalList = functionalList;
	}

	/**
	 * Gets the test suite result list.
	 *
	 * @return the test suite result list
	 */
	public List<TestSuiteResult> getTestSuiteResultList() {
		return testSuiteResultList;
	}

	/**
	 * Sets the test suite result list.
	 *
	 * @param testSuiteResultList the new test suite result list
	 */
	public void setTestSuiteResultList(List<TestSuiteResult> testSuiteResultList) {
		this.testSuiteResultList = testSuiteResultList;
	}

	/**
	 * Gets the test plan result id.
	 *
	 * @return the testPlanResultID
	 */
	public int getTestPlanResultID() {
		return testPlanResultID;
	}

	/**
	 * Sets the test plan result id.
	 *
	 * @param testPlanResultID the testPlanResultID to set
	 */
	public void setTestPlanResultID(int testPlanResultID) {
		this.testPlanResultID = testPlanResultID;
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
	 * @param testPlanID the testPlanID to set
	 */
	public void setTestPlanID(int testPlanID) {
		this.testPlanID = testPlanID;
	}

	/**
	 * Gets the test plan run name.
	 *
	 * @return the testPlanRunName
	 */
	public String getTestPlanRunName() {
		return testPlanRunName;
	}

	/**
	 * Sets the test plan run name.
	 *
	 * @param testPlanRunName the testPlanRunName to set
	 */
	public void setTestPlanRunName(String testPlanRunName) {
		this.testPlanRunName = testPlanRunName;
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
	 * @param testRunID the testRunID to set
	 */
	public void setTestRunID(int testRunID) {
		this.testRunID = testRunID;
	}

	/**
	 * Gets the task id.
	 *
	 * @return the taskID
	 */
	public int getTaskID() {
		return taskID;
	}

	/**
	 * Sets the task id.
	 *
	 * @param taskID the taskID to set
	 */
	public void setTaskID(int taskID) {
		this.taskID = taskID;
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
		return "TestPlanResult [TestPlanResultID=" + testPlanResultID + ", TestPlanID=" + testPlanID + ", TestPlanRunName=" + testPlanRunName
				+ ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime + ", Result=" + result + ", TaskID=" + taskID + ", TestRunID="
				+ testRunID + ", PercentagePassCount=" + percentagePassCount + ", PercentageFailCount=" + percentageFailCount + "]";
	}

}
