package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class SuiteScenarioMapping.
 *
 * @author mohammedfirdos
 */
public class SuiteScenarioMapping implements Serializable {

	/** SuiteScenarioMapping Entity. */
	private static final long serialVersionUID = 1L;

	/** The suite scenario mapping id. */
	private int suiteScenarioMappingID;
	
	/** The test suite id. */
	private int testSuiteID;
	
	/** The test scenario id. */
	private int testScenarioID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The test suite. */
	private TestSuite testSuite;
	
	/** The test scenario. */
	private TestScenario testScenario;

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
	 * Gets the suite scenario mapping id.
	 *
	 * @return the suiteScenarioMappingID
	 */
	public int getSuiteScenarioMappingID() {
		return suiteScenarioMappingID;
	}

	/**
	 * Sets the suite scenario mapping id.
	 *
	 * @param suiteScenarioMappingID the suiteScenarioMappingID to set
	 */
	public void setSuiteScenarioMappingID(int suiteScenarioMappingID) {
		this.suiteScenarioMappingID = suiteScenarioMappingID;
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
		return "SuiteScenarioMapping [SuiteScenarioMappingID=" + suiteScenarioMappingID + ", TestSuiteID=" + testSuiteID + ", TestScenarioID="
				+ testScenarioID + ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy + ", UpdatedDateTime="
				+ updatedDateTime + "]";
	}

}
