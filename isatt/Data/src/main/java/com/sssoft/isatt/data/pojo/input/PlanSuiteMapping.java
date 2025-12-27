package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class PlanSuiteMapping.
 *
 * @author mohammedfirdos
 */
public class PlanSuiteMapping implements Serializable {

	/** PlanSuiteMappingDao Entity. */
	private static final long serialVersionUID = 1L;

	/** The plan suite mapping id. */
	private int planSuiteMappingID;
	
	/** The test plan id. */
	private int testPlanID;
	
	/** The test suite id. */
	private int testSuiteID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;

	/** The test plan. */
	private TestPlan testPlan;
	
	/** The test suite. */
	private TestSuite testSuite;

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
	 * @param testPlan the new test plan
	 */
	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
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
	 * Gets the plan suite mapping id.
	 *
	 * @return the planSuiteMappingID
	 */
	public int getPlanSuiteMappingID() {
		return planSuiteMappingID;
	}

	/**
	 * Sets the plan suite mapping id.
	 *
	 * @param planSuiteMappingID the planSuiteMappingID to set
	 */
	public void setPlanSuiteMappingID(int planSuiteMappingID) {
		this.planSuiteMappingID = planSuiteMappingID;
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
		return "PlanSuiteMapping [PlanSuiteMappingID=" + planSuiteMappingID + ", TestPlanID=" + testPlanID + ", TestSuiteID=" + testSuiteID
				+ ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy + ", UpdatedDateTime="
				+ updatedDateTime + "]";
	}

}