package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class ScenarioCaseMapping.
 *
 * @author mohammedfirdos
 */
public class ScenarioCaseMapping implements Serializable {

	/** ScenarioCaseMapping Entity. */
	private static final long serialVersionUID = 1L;

	/** The scenario case mapping id. */
	private int scenarioCaseMappingID;
	
	/** The test scenario id. */
	private int testScenarioID;
	
	/** The test case id. */
	private int testCaseID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;

	/** The test scenario. */
	private TestScenario testScenario;
	
	/** The test case. */
	private TestCase testCase;

	/**
	 * Gets the test scenario.
	 *
	 * @return the test scenario
	 */
	public TestScenario getTestScenario() {
		return testScenario;
	}

	/**
	 * Sets the test scenario.
	 *
	 * @param testScenario the new test scenario
	 */
	public void setTestScenario(TestScenario testScenario) {
		this.testScenario = testScenario;
	}

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
	 * Gets the scenario case mapping id.
	 *
	 * @return the scenarioCaseMappingID
	 */
	public int getScenarioCaseMappingID() {
		return scenarioCaseMappingID;
	}

	/**
	 * Sets the scenario case mapping id.
	 *
	 * @param scenarioCaseMappingID the scenarioCaseMappingID to set
	 */
	public void setScenarioCaseMappingID(int scenarioCaseMappingID) {
		this.scenarioCaseMappingID = scenarioCaseMappingID;
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
		return "ScenarioCaseMapping [ScenarioCaseMappingID=" + scenarioCaseMappingID + ", TestScenarioID=" + testScenarioID + ", TestCaseID=" + testCaseID
				+ ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy + ", UpdatedDateTime="
				+ updatedDateTime + "]";
	}

}