package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ConditionGroup.
 *
 * @author mohammedfirdos
 */
public class ConditionGroup implements Serializable {

	/** ConditionGroup Entity. */
	private static final long serialVersionUID = 1L;

	/** The condition group id. */
	private int conditionGroupID;
	
	/** The condition group name. */
	private String conditionGroupName;
	
	/** The description. */
	private String description;
	
	/** The app id. */
	private int appID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The conditions. */
	private List<Conditions> conditions = new ArrayList<Conditions>(0);
	
	/** The test case. */
	private List<TestCase> testCase = new ArrayList<TestCase>(0);
	
	/** The test condition data. */
	private List<TestConditionData> testConditionData = new ArrayList<TestConditionData>(0);
	
	/** The test plan. */
	private List<TestPlan> testPlan = new ArrayList<TestPlan>(0);

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
	 * Gets the condition group name.
	 *
	 * @return the conditionGroupName
	 */
	public String getConditionGroupName() {
		return conditionGroupName;
	}

	/**
	 * Sets the condition group name.
	 *
	 * @param conditionGroupName the conditionGroupName to set
	 */
	public void setConditionGroupName(String conditionGroupName) {
		this.conditionGroupName = conditionGroupName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	public List<Conditions> getConditions() {
		return conditions;
	}

	/**
	 * Sets the conditions.
	 *
	 * @param conditions the conditions to set
	 */
	public void setConditions(List<Conditions> conditions) {
		this.conditions = conditions;
	}

	/**
	 * Gets the test case.
	 *
	 * @return the testCase
	 */
	public List<TestCase> getTestCase() {
		return testCase;
	}

	/**
	 * Sets the test case.
	 *
	 * @param testCase the testCase to set
	 */
	public void setTestCase(List<TestCase> testCase) {
		this.testCase = testCase;
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
	 * Gets the test plan.
	 *
	 * @return the testPlan
	 */
	public List<TestPlan> getTestPlan() {
		return testPlan;
	}

	/**
	 * Sets the test plan.
	 *
	 * @param testPlan the testPlan to set
	 */
	public void setTestPlan(List<TestPlan> testPlan) {
		this.testPlan = testPlan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConditionGroup [ConditionGroupID=" + conditionGroupID + ", ConditionGroupName=" + conditionGroupName + ", Description=" + description
				+ ", AppID=" + appID + "]";
	}

}
