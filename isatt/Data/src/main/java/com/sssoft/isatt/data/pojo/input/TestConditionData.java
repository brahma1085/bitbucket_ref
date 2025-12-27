package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class TestConditionData.
 *
 * @author mohammedfirdos
 */
public class TestConditionData implements Serializable {

	/** TestConditionData Entity. */
	private static final long serialVersionUID = 1L;

	/** The test condition data id. */
	private int testConditionDataID;
	
	/** The test data id. */
	private int testDataID;
	
	/** The condition group id. */
	private int conditionGroupID;
	
	/** The condition id. */
	private int conditionID;
	
	/** The condition value. */
	private String conditionValue;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The test data. */
	private TestData testData;

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
	 * @param testData the new test data
	 */
	public void setTestData(TestData testData) {
		this.testData = testData;
	}

	/**
	 * Gets the test condition data id.
	 *
	 * @return the testConditionDataID
	 */
	public int getTestConditionDataID() {
		return testConditionDataID;
	}

	/**
	 * Sets the test condition data id.
	 *
	 * @param testConditionDataID the testConditionDataID to set
	 */
	public void setTestConditionDataID(int testConditionDataID) {
		this.testConditionDataID = testConditionDataID;
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
	 * Gets the condition group id.
	 *
	 * @return the conditionGroupID
	 */
	public int getConditionGroupID() {
		return conditionGroupID;
	}

	/**
	 * Sets the condition group id.
	 *
	 * @param conditionGroupID the conditionGroupID to set
	 */
	public void setConditionGroupID(int conditionGroupID) {
		this.conditionGroupID = conditionGroupID;
	}

	/**
	 * Gets the condition id.
	 *
	 * @return the conditionID
	 */
	public int getConditionID() {
		return conditionID;
	}

	/**
	 * Sets the condition id.
	 *
	 * @param conditionID the conditionID to set
	 */
	public void setConditionID(int conditionID) {
		this.conditionID = conditionID;
	}

	/**
	 * Gets the condition value.
	 *
	 * @return the conditionValue
	 */
	public String getConditionValue() {
		return conditionValue;
	}

	/**
	 * Sets the condition value.
	 *
	 * @param conditionValue the conditionValue to set
	 */
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestConditionData [TestConditionDataID=" + testConditionDataID + ", TestDataID=" + testDataID + ", ConditionGroupID=" + conditionGroupID
				+ ", ConditionID=" + conditionID + ", ConditionValue=" + conditionValue + "]";
	}

}
