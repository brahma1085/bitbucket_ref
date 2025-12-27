package com.exilant.tfw.pojo.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.SchedulerRunDetails;

/**
 * The Class TestScenarioResult.
 *
 * @author mohammedfirdos
 */
public class TestScenarioResult implements Serializable {

	/** TestScenarioResult Entity. */
	private static final long serialVersionUID = 1L;

	/** The test scenario result id. */
	private int testScenarioResultID;
	
	/** The test scenario id. */
	private int testScenarioID;
	
	/** The test suite id. */
	private int testSuiteID;
	
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
	
	/** The percentage skip count. */
	private int percentageSkipCount;

	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The case results list. */
	private List<TestCaseResult> caseResultsList = new ArrayList<TestCaseResult>();

	/**
	 * Gets the case results list.
	 *
	 * @return the case results list
	 */
	public List<TestCaseResult> getCaseResultsList() {
		return caseResultsList;
	}

	/**
	 * Sets the case results list.
	 *
	 * @param caseResultsList the new case results list
	 */
	public void setCaseResultsList(List<TestCaseResult> caseResultsList) {
		this.caseResultsList = caseResultsList;
	}

	/**
	 * Gets the test scenario result id.
	 *
	 * @return the testScenarioResultID
	 */
	public int getTestScenarioResultID() {
		return testScenarioResultID;
	}

	/**
	 * Sets the test scenario result id.
	 *
	 * @param testScenarioResultID the testScenarioResultID to set
	 */
	public void setTestScenarioResultID(int testScenarioResultID) {
		this.testScenarioResultID = testScenarioResultID;
	}

	/**
	 * Gets the test scenario id.
	 *
	 * @return the testScenarioID
	 */
	public int getTestScenarioID() {
		return testScenarioID;
	}

	/**
	 * Sets the test scenario id.
	 *
	 * @param testScenarioID the testScenarioID to set
	 */
	public void setTestScenarioID(int testScenarioID) {
		this.testScenarioID = testScenarioID;
	}

	/**
	 * Gets the test suite id.
	 *
	 * @return the testSuiteID
	 */
	public int getTestSuiteID() {
		return testSuiteID;
	}

	/**
	 * Sets the test suite id.
	 *
	 * @param testSuiteID the testSuiteID to set
	 */
	public void setTestSuiteID(int testSuiteID) {
		this.testSuiteID = testSuiteID;
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
	 * Gets the percentage skip count.
	 *
	 * @return the percentageSkipCount
	 */
	public int getPercentageSkipCount() {
		return percentageSkipCount;
	}

	/**
	 * Sets the percentage skip count.
	 *
	 * @param percentageSkipCount the percentageSkipCount to set
	 */
	public void setPercentageSkipCount(int percentageSkipCount) {
		this.percentageSkipCount = percentageSkipCount;
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
		return "TestScenarioResult [TestScenarioResultID=" + testScenarioResultID + ", TestScenarioID=" + testScenarioID + ", TestSuiteID=" + testSuiteID
				+ ", TestRunID=" + testRunID + ", Result=" + result + ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime
				+ ", PercentagePassCount=" + percentagePassCount + ", PercentageFailCount=" + percentageFailCount + ", PercentageSkipCount="
				+ percentageSkipCount + "]";
	}

}