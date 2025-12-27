package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerRunDetails;

/**
 * The Class TestData.
 *
 * @author mohammedfirdos
 */
public class TestData implements Serializable {

	/** TestData Entity. */
	private static final long serialVersionUID = 1L;

	/** The test data id. */
	private int testDataID;
	
	/** The app id. */
	private int appID;
	
	/** The test data description. */
	private String testDataDescription;
	
	/** The status. */
	private String status;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The scheduler. */
	private List<Scheduler> scheduler = new ArrayList<Scheduler>(0);
	
	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The test condition data. */
	private List<TestConditionData> testConditionData = new ArrayList<TestConditionData>(0);
	
	/** The test param data. */
	private List<TestParamData> testParamData = new ArrayList<TestParamData>(0);

	/** The param count. */
	private int paramCount;

	/**
	 * Gets the param count.
	 *
	 * @return the param count
	 */
	public int getParamCount() {
		return paramCount;
	}

	/**
	 * Sets the param count.
	 *
	 * @param paramCount the new param count
	 */
	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
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
	 * @param testDataID the testDataID to set
	 */
	public void setTestDataID(int testDataID) {
		this.testDataID = testDataID;
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
	 * @param appID the appID to set
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the test data description.
	 *
	 * @return the testDataDescription
	 */
	public String getTestDataDescription() {
		return testDataDescription;
	}

	/**
	 * Sets the test data description.
	 *
	 * @param testDataDescription the testDataDescription to set
	 */
	public void setTestDataDescription(String testDataDescription) {
		this.testDataDescription = testDataDescription;
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
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @param createdBy the createdBy to set
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
	 * @param createdDateTime the createdDateTime to set
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
	 * @param updatedBy the updatedBy to set
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
	 * @param updatedDateTime the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the scheduler.
	 *
	 * @return the scheduler
	 */
	public List<Scheduler> getScheduler() {
		return scheduler;
	}

	/**
	 * Sets the scheduler.
	 *
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(List<Scheduler> scheduler) {
		this.scheduler = scheduler;
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
	 * Gets the test condition data.
	 *
	 * @return the testConditionData
	 */
	public List<TestConditionData> getTestConditionData() {
		return testConditionData;
	}

	/**
	 * Sets the test condition data.
	 *
	 * @param testConditionData the testConditionData to set
	 */
	public void setTestConditionData(List<TestConditionData> testConditionData) {
		this.testConditionData = testConditionData;
	}

	/**
	 * Gets the test param data.
	 *
	 * @return the testParamData
	 */
	public List<TestParamData> getTestParamData() {
		return testParamData;
	}

	/**
	 * Sets the test param data.
	 *
	 * @param testParamData the testParamData to set
	 */
	public void setTestParamData(List<TestParamData> testParamData) {
		this.testParamData = testParamData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestData [TestDataID=" + testDataID + ", AppID=" + appID + ", TestDataDescription=" + testDataDescription + ", Status=" + status + "]";
	}

}
