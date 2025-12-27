package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class TestScenario.
 *
 * @author mohammedfirdos
 */
public class TestScenario implements Serializable {

	/** TestScenario Entity. */
	private static final long serialVersionUID = 1L;

	/** The test scenario id. */
	private int testScenarioID;
	
	/** The test scenario name. */
	private String testScenarioName;
	
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
	
	/** The param group. */
	private List<ParamGroup> paramGroup = new ArrayList<ParamGroup>(0);
	
	/** The test case. */
	private List<TestCase> testCase = new ArrayList<TestCase>(0);

	/** The suite scenario mappings. */
	private List<SuiteScenarioMapping> suiteScenarioMappings = new ArrayList<SuiteScenarioMapping>(0);
	
	/** The scenario case mappings. */
	private List<ScenarioCaseMapping> scenarioCaseMappings = new ArrayList<ScenarioCaseMapping>(0);

	/**
	 * Gets the scenario case mappings.
	 *
	 * @return the scenario case mappings
	 */
	public List<ScenarioCaseMapping> getScenarioCaseMappings() {
		return scenarioCaseMappings;
	}

	/**
	 * Sets the scenario case mappings.
	 *
	 * @param scenarioCaseMappings the new scenario case mappings
	 */
	public void setScenarioCaseMappings(List<ScenarioCaseMapping> scenarioCaseMappings) {
		this.scenarioCaseMappings = scenarioCaseMappings;
	}

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
	 * Gets the test scenario name.
	 *
	 * @return the testScenarioName
	 */
	public String getTestScenarioName() {
		return testScenarioName;
	}

	/**
	 * Sets the test scenario name.
	 *
	 * @param testScenarioName the testScenarioName to set
	 */
	public void setTestScenarioName(String testScenarioName) {
		this.testScenarioName = testScenarioName;
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
	 * Gets the param group.
	 *
	 * @return the paramGroup
	 */
	public List<ParamGroup> getParamGroup() {
		return paramGroup;
	}

	/**
	 * Sets the param group.
	 *
	 * @param paramGroup the paramGroup to set
	 */
	public void setParamGroup(List<ParamGroup> paramGroup) {
		this.paramGroup = paramGroup;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestScenario [TestScenarioID=" + testScenarioID + ", TestScenarioName=" + testScenarioName + ", Description=" + description + ", AppID="
				+ appID + ", SortOrder=" + sortOrder + ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy
				+ ", UpdatedDateTime=" + updatedDateTime + "]";
	}

}
