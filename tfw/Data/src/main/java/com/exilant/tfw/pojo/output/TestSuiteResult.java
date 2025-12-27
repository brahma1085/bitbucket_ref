package com.exilant.tfw.pojo.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.input.TestSuite;

/**
 * The Class TestSuiteResult.
 *
 * @author mohammedfirdos
 */
public class TestSuiteResult implements Serializable {

	/** TestSuiteResult Entity. */
	private static final long serialVersionUID = 1L;

	/** The test suite result id. */
	private int testSuiteResultID;
	
	/** The test suite id. */
	private int testSuiteID;
	
	/** The test plan id. */
	private int testPlanID;
	
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
	
	/** The duration. */
	private double duration;
	
	/** The test suite. */
	private TestSuite testSuite;

	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);

	/** The test scenario results. */
	private List<TestScenarioResult> testScenarioResults = new ArrayList<TestScenarioResult>(0);
	
	/** The perc not executed. */
	private int percNotExecuted;

	/** The not executed. */
	private int notExecuted;
	
	/** The total count. */
	private int totalCount;
	
	/** The suit satus. */
	private boolean suitSatus;

	/**
	 * Gets the test scenario results.
	 *
	 * @return the test scenario results
	 */
	public List<TestScenarioResult> getTestScenarioResults() {
		return testScenarioResults;
	}

	/**
	 * Sets the test scenario results.
	 *
	 * @param testScenarioResults the new test scenario results
	 */
	public void setTestScenarioResults(List<TestScenarioResult> testScenarioResults) {
		this.testScenarioResults = testScenarioResults;
	}

	/**
	 * Gets the perc not executed.
	 *
	 * @return the perc not executed
	 */
	public int getPercNotExecuted() {
		return percNotExecuted;
	}

	/**
	 * Sets the perc not executed.
	 *
	 * @param percNotExecuted the new perc not executed
	 */
	public void setPercNotExecuted(int percNotExecuted) {
		this.percNotExecuted = percNotExecuted;
	}

	/**
	 * Gets the test suite.
	 *
	 * @return the test suite
	 */
	public TestSuite getTestSuite() {
		return testSuite;
	}

	/**
	 * Sets the test suite.
	 *
	 * @param testSuite the new test suite
	 */
	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	public int getTotalCount() {
		return totalCount = this.percentagePassCount + this.percentageFailCount;
	}

	/**
	 * Sets the total count.
	 *
	 * @param totalCount the new total count
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Checks if is suit satus.
	 *
	 * @return true, if is suit satus
	 */
	public boolean isSuitSatus() {
		return suitSatus;
	}

	/**
	 * Sets the suit satus.
	 *
	 * @param suitSatus the new suit satus
	 */
	public void setSuitSatus(boolean suitSatus) {
		this.suitSatus = suitSatus;
	}

	/**
	 * Gets the not executed.
	 *
	 * @return the not executed
	 */
	public int getNotExecuted() {
		return notExecuted;
	}

	/**
	 * Sets the not executed.
	 *
	 * @param notExecuted the new not executed
	 */
	public void setNotExecuted(int notExecuted) {
		this.notExecuted = notExecuted;
	}

	/**
	 * Gets the test suite result id.
	 *
	 * @return the testSuiteResultID
	 */
	public int getTestSuiteResultID() {
		return testSuiteResultID;
	}

	/**
	 * Sets the test suite result id.
	 *
	 * @param testSuiteResultID the testSuiteResultID to set
	 */
	public void setTestSuiteResultID(int testSuiteResultID) {
		this.testSuiteResultID = testSuiteResultID;
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

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestSuiteResult [TestSuiteResultID=" + testSuiteResultID + ", TestSuiteID=" + testSuiteID + ", TestPlanID=" + testPlanID + ", Result="
				+ result + ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime + ", PercentagePassCount=" + percentagePassCount
				+ ", PercentageFailCount=" + percentageFailCount + ", TestRunID=" + testRunID + "]";
	}

}