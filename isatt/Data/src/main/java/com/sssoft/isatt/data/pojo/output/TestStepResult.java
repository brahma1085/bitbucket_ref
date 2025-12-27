package com.sssoft.isatt.data.pojo.output;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.SchedulerRunDetails;
import com.sssoft.isatt.data.pojo.input.TestStep;

/**
 * The Class TestStepResult.
 *
 * @author mohammedfirdos
 */
public class TestStepResult implements Serializable {

	/** TestStepResult Entity. */
	private static final long serialVersionUID = 1L;

	/** The test step result id. */
	private int testStepResultID;
	
	/** The test step id. */
	private int testStepID;
	
	/** The test case id. */
	private int testCaseID;
	
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
	
	/** The result. */
	private int result;
	
	/** The start date time. */
	private Timestamp startDateTime;
	
	/** The end date time. */
	private Timestamp endDateTime;
	
	/** The duration. */
	private int duration;
	
	/** The test step. */
	private TestStep testStep = new TestStep();
	
	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The snap shot. */
	private String snapShot;

	/**
	 * Gets the test step.
	 *
	 * @return the test step
	 */
	public TestStep getTestStep() {
		return testStep;
	}

	/** The time taken. */
	private long timeTaken;

	/**
	 * Gets the time taken.
	 *
	 * @return the time taken
	 */
	public long getTimeTaken() {
		return timeTaken;
	}

	/**
	 * Sets the time taken.
	 *
	 * @param timeTaken the new time taken
	 */
	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * Sets the test step.
	 *
	 * @param testStep the new test step
	 */
	public void setTestStep(TestStep testStep) {
		this.testStep = testStep;
	}

	/**
	 * Gets the snap shot.
	 *
	 * @return the snap shot
	 */
	public String getSnapShot() {
		return snapShot;
	}

	/**
	 * Sets the snap shot.
	 *
	 * @param snapShot the new snap shot
	 */
	public void setSnapShot(String snapShot) {
		this.snapShot = snapShot;
	}

	/**
	 * Gets the test step result id.
	 *
	 * @return the testStepResultID
	 */
	public int getTestStepResultID() {
		return testStepResultID;
	}

	/**
	 * Sets the test step result id.
	 *
	 * @param testStepResultID the testStepResultID to set
	 */
	public void setTestStepResultID(int testStepResultID) {
		this.testStepResultID = testStepResultID;
	}

	/**
	 * Gets the test step id.
	 *
	 * @return the testStepID
	 */
	public int getTestStepID() {
		return testStepID;
	}

	/**
	 * Sets the test step id.
	 *
	 * @param testStepID the testStepID to set
	 */
	public void setTestStepID(int testStepID) {
		this.testStepID = testStepID;
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
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
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
		return "TestStepResult [TestStepResultID=" + testStepResultID + ", TestStepID=" + testStepID + ", TestCaseID=" + testCaseID + ", Result=" + result
				+ ", StartDateTime=" + startDateTime + ", EndDateTime=" + endDateTime + ", Comment=" + comment + ", Exception=" + exception + ", Request="
				+ request + ", Response=" + response + ", TestRunID=" + testRunID + "]";
	}

}
