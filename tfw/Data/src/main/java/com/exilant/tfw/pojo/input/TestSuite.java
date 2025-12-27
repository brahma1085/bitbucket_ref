package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.Application;
import com.exilant.tfw.pojo.output.TestSuiteResult;

/**
 * The Class TestSuite.
 *
 * @author mohammedfirdos
 */
public class TestSuite implements Serializable {

	/** TestSuite Entity. */
	private static final long serialVersionUID = 1L;

	/** The test suite id. */
	private int testSuiteID;
	
	/** The suite name. */
	private String suiteName;
	
	/** The description. */
	private String description;
	
	/** The app id. */
	private int appID;
	
	/** The sort order. */
	private int sortOrder;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The test suite result. */
	private List<TestSuiteResult> testSuiteResult = new ArrayList<TestSuiteResult>(0);
	
	/** The test scenario. */
	private List<TestScenario> testScenario = new ArrayList<TestScenario>(0);
	
	/** The suite scenario mappings. */
	private List<SuiteScenarioMapping> suiteScenarioMappings = new ArrayList<SuiteScenarioMapping>(0);

	/**
	 * Gets the test scenario.
	 *
	 * @return the test scenario
	 */
	public List<TestScenario> getTestScenario() {
		return testScenario;
	}

	/**
	 * Sets the test scenario.
	 *
	 * @param testScenario the new test scenario
	 */
	public void setTestScenario(List<TestScenario> testScenario) {
		this.testScenario = testScenario;
	}

	/** The application. */
	private Application application;

	/**
	 * Gets the suite scenario mappings.
	 *
	 * @return the suite scenario mappings
	 */
	public List<SuiteScenarioMapping> getSuiteScenarioMappings() {
		return suiteScenarioMappings;
	}

	/**
	 * Sets the suite scenario mappings.
	 *
	 * @param suiteScenarioMappings the new suite scenario mappings
	 */
	public void setSuiteScenarioMappings(List<SuiteScenarioMapping> suiteScenarioMappings) {
		this.suiteScenarioMappings = suiteScenarioMappings;
	}

	/**
	 * Gets the application.
	 *
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Sets the application.
	 *
	 * @param application the new application
	 */
	public void setApplication(Application application) {
		this.application = application;
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
	 * Gets the suite name.
	 *
	 * @return the suiteName
	 */
	public String getSuiteName() {
		return suiteName;
	}

	/**
	 * Sets the suite name.
	 *
	 * @param suiteName the suiteName to set
	 */
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
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
	 * Gets the sort order.
	 *
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * Sets the sort order.
	 *
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	 * Gets the test suite result.
	 *
	 * @return the testSuiteResult
	 */
	public List<TestSuiteResult> getTestSuiteResult() {
		return testSuiteResult;
	}

	/**
	 * Sets the test suite result.
	 *
	 * @param testSuiteResult the testSuiteResult to set
	 */
	public void setTestSuiteResult(List<TestSuiteResult> testSuiteResult) {
		this.testSuiteResult = testSuiteResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestSuite [TestSuiteID=" + testSuiteID + ", SuiteName=" + suiteName + ", Description=" + description + ", AppID=" + appID + ", SortOrder="
				+ sortOrder + "]";
	}

}
