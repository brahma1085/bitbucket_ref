package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class CaseStepMapping.
 *
 * @author mohammedfirdos
 */
public class CaseStepMapping implements Serializable {

	/** CaseStepMapping Entity. */
	private static final long serialVersionUID = 1L;

	/** The case step mapping id. */
	private int caseStepMappingID;
	
	/** The test case id. */
	private int testCaseID;
	
	/** The test step id. */
	private int testStepID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;

	/** The test case. */
	private TestCase testCase;
	
	/** The test step. */
	private TestStep testStep;

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
	 * Gets the test step.
	 *
	 * @return the test step
	 */
	public TestStep getTestStep() {
		return testStep;
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
	 * Gets the case step mapping id.
	 *
	 * @return the caseStepMappingID
	 */
	public int getCaseStepMappingID() {
		return caseStepMappingID;
	}

	/**
	 * Sets the case step mapping id.
	 *
	 * @param caseStepMappingID the caseStepMappingID to set
	 */
	public void setCaseStepMappingID(int caseStepMappingID) {
		this.caseStepMappingID = caseStepMappingID;
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
		return "CaseStepMapping [CaseStepMappingID=" + caseStepMappingID + ", TestCaseID=" + testCaseID + ", TestStepID=" + testStepID + ", CreatedBy="
				+ createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy + ", UpdatedDateTime=" + updatedDateTime + "]";
	}

}