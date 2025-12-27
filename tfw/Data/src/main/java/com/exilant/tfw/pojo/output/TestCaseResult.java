package com.exilant.tfw.pojo.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.input.TestCase;

/**
 * The Class TestCaseResult.
 *
 * @author mohammedfirdos
 */
public class TestCaseResult implements Serializable {

	/** TestCaseResult Entity. */
	private static final long serialVersionUID = 1L;

	/** The test case result id. */
	private int testCaseResultID;
	
	/** The test case id. */
	private int testCaseID;
	
	/** The test scenario id. */
	private int testScenarioID;
	
	/** The result. */
	private int result;
	
	/** The start date time. */
	private Timestamp startDateTime;
	
	/** The end date time. */
	private Timestamp endDateTime;
	
	/** The comment. */
	private String comment;
	
	/** The exception. */
	private String exception;
	
	/** The request. */
	private String request;
	
	/** The response. */
	private String response;
	
	/** The test run id. */
	private int testRunID;
	
	/** The percentage pass count. */
	private int percentagePassCount;
	
	/** The percentage fail count. */
	private int percentageFailCount;
	
	/** The percentage skip count. */
	private int percentageSkipCount;
	
	/** The run status. */
	private int runStatus;
	
	/** The total count. */
	private int totalCount;
	
	/** The pass count. */
	private int passCount;
	
	/** The fail count. */
	private int failCount;
	
	/** The time duration. */
	private Double timeDuration;
	
	/** The active. */
	private boolean active;
	
	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The test step results list. */
	private List<TestStepResult> testStepResultsList = new ArrayList<TestStepResult>(0);
	
	/** The test case. */
	private TestCase testCase;
	
	/** The duration. */
	private double duration;

	/**
	 * Gets the test case.
	 *
	 * @return the test case
	 */
	public TestCase getTestCase() {
		return testCase;
	}

	/**
	 * Sets the test case.
	 *
	 * @param testCase the new test case
	 */
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	/**
	 * Gets the run status.
	 *
	 * @return the run status
	 */
	public int getRunStatus() {
		return runStatus;
	}

	/**
	 * Sets the run status.
	 *
	 * @param runStatus the new run status
	 */
	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}

	/**
	 * Gets the total count.
	 *
	 * @return the total count
	 */
	public int getTotalCount() {
		return totalCount;
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
	 * Gets the pass count.
	 *
	 * @return the pass count
	 */
	public int getPassCount() {
		return passCount;
	}

	/**
	 * Sets the pass count.
	 *
	 * @param passCount the new pass count
	 */
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	/**
	 * Gets the fail count.
	 *
	 * @return the fail count
	 */
	public int getFailCount() {
		return failCount;
	}

	/**
	 * Sets the fail count.
	 *
	 * @param failCount the new fail count
	 */
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	/**
	 * Gets the time duration.
	 *
	 * @return the time duration
	 */
	public Double getTimeDuration() {
		return timeDuration;
	}

	/**
	 * Sets the time duration.
	 *
	 * @param timeDuration the new time duration
	 */
	public void setTimeDuration(Double timeDuration) {
		this.timeDuration = timeDuration;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public boolean getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the test step results list.
	 *
	 * @return the test step results list
	 */
	public List<TestStepResult> getTestStepResultsList() {
		return testStepResultsList;
	}

	/**
	 * Sets the test step results list.
	 *
	 * @param testStepResultsList the new test step results list
	 */
	public void setTestStepResultsList(List<TestStepResult> testStepResultsList) {
		this.testStepResultsList = testStepResultsList;
	}

	/**
	 * Gets the test case result id.
	 *
	 * @return the testCaseResultID
	 */
	public int getTestCaseResultID() {
		return testCaseResultID;
	}

	/**
	 * Sets the test case result id.
	 *
	 * @param testCaseResultID the testCaseResultID to set
	 */
	public void setTestCaseResultID(int testCaseResultID) {
		this.testCaseResultID = testCaseResultID;
	}

	/**
	 * Gets the test case id.
	 *
	 * @return the testCaseID
	 */
	public int getTestCaseID() {
		return testCaseID;
	}

	/**
	 * Sets the test case id.
	 *
	 * @param testCaseID the testCaseID to set
	 */
	public void setTestCaseID(int testCaseID) {
		this.testCaseID = testCaseID;
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
	 * Gets the result.
	 *
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the result to set
	 */
	public void setResult(int result) {
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
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * Sets the exception.
	 *
	 * @param exception the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
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
		return "TestCaseResult [TestCaseResultID=" + testCaseResultID + ", TestCaseID=" + testCaseID + ", TestScenarioID=" + testScenarioID + ", Result="
				+ result + ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime + ", Comment=" + comment + ", Exception=" + exception
				+ ", Request=" + request + ", Response=" + response + ", TestRunID=" + testRunID + ", PercentagePassCount=" + percentagePassCount
				+ ", PercentageFailCount=" + percentageFailCount + ", PercentageSkipCount=" + percentageSkipCount + "]";
	}

}