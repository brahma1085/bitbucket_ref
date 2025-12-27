package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.ConditionGroup;
import com.exilant.tfw.pojo.input.IdentifierType;
import com.exilant.tfw.pojo.input.ObjectGroup;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.ParamGroup;
import com.exilant.tfw.pojo.input.ReplacementStrings;
import com.exilant.tfw.pojo.input.TestData;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestScenario;
import com.exilant.tfw.pojo.input.TestSuite;

/**
 * The Class Application.
 *
 * @author mohammedfirdos
 */
public class Application implements Serializable {

	/** Application Entity. */
	private static final long serialVersionUID = 1L;

	/** The app id. */
	private int appID;
	
	/** The app name. */
	private String appName;
	
	/** The description. */
	private String description;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The app functionality. */
	private List<AppFunctionality> appFunctionality = new ArrayList<AppFunctionality>(0);
	
	/** The condition group. */
	private List<ConditionGroup> conditionGroup = new ArrayList<ConditionGroup>(0);
	
	/** The generic data. */
	private List<GenericData> genericData = new ArrayList<GenericData>(0);
	
	/** The identifier type. */
	private List<IdentifierType> identifierType = new ArrayList<IdentifierType>(0);
	
	/** The object group. */
	private List<ObjectGroup> objectGroup = new ArrayList<ObjectGroup>(0);
	
	/** The param. */
	private List<Param> param = new ArrayList<Param>(0);
	
	/** The param group. */
	private List<ParamGroup> paramGroup = new ArrayList<ParamGroup>(0);
	
	/** The replacement strings. */
	private List<ReplacementStrings> replacementStrings = new ArrayList<ReplacementStrings>(0);
	
	/** The screen. */
	private List<Screen> screen = new ArrayList<Screen>(0);
	
	/** The test data. */
	private List<TestData> testData = new ArrayList<TestData>(0);
	
	/** The test plan. */
	private List<TestPlan> testPlan = new ArrayList<TestPlan>(0);
	
	/** The test scenario. */
	private List<TestScenario> testScenario = new ArrayList<TestScenario>(0);
	
	/** The test suite. */
	private List<TestSuite> testSuite = new ArrayList<TestSuite>(0);
	
	/** The identifier type id. */
	private int identifierTypeID = 0;

	/**
	 * Gets the identifier type id.
	 *
	 * @return the identifier type id
	 */
	public int getIdentifierTypeID() {
		return identifierTypeID;
	}

	/**
	 * Gets the app id.
	 *
	 * @return the app id
	 */
	public int getAppID() {
		return appID;
	}

	/**
	 * Sets the app id.
	 *
	 * @param appID the new app id
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the app name.
	 *
	 * @return the app name
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * Sets the app name.
	 *
	 * @param appName the new app name
	 */
	public void setAppName(String appName) {
		this.appName = appName;
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
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date time.
	 *
	 * @return the created date time
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Sets the created date time.
	 *
	 * @param createdDateTime the new created date time
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date time.
	 *
	 * @return the updated date time
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * Sets the updated date time.
	 *
	 * @param updatedDateTime the new updated date time
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the app functionality.
	 *
	 * @return the appFunctionality
	 */
	public List<AppFunctionality> getAppFunctionality() {
		return appFunctionality;
	}

	/**
	 * Sets the app functionality.
	 *
	 * @param appFunctionality the appFunctionality to set
	 */
	public void setAppFunctionality(List<AppFunctionality> appFunctionality) {
		this.appFunctionality = appFunctionality;
	}

	/**
	 * Gets the condition group.
	 *
	 * @return the conditionGroup
	 */
	public List<ConditionGroup> getConditionGroup() {
		return conditionGroup;
	}

	/**
	 * Sets the condition group.
	 *
	 * @param conditionGroup the conditionGroup to set
	 */
	public void setConditionGroup(List<ConditionGroup> conditionGroup) {
		this.conditionGroup = conditionGroup;
	}

	/**
	 * Gets the generic data.
	 *
	 * @return the genericData
	 */
	public List<GenericData> getGenericData() {
		return genericData;
	}

	/**
	 * Sets the generic data.
	 *
	 * @param genericData the genericData to set
	 */
	public void setGenericData(List<GenericData> genericData) {
		this.genericData = genericData;
	}

	/**
	 * Gets the identifier type.
	 *
	 * @return the identifierType
	 */
	public List<IdentifierType> getIdentifierType() {
		return identifierType;
	}

	/**
	 * Sets the identifier type.
	 *
	 * @param identifierType the identifierType to set
	 */
	public void setIdentifierType(List<IdentifierType> identifierType) {
		this.identifierType = identifierType;
	}

	/**
	 * Gets the object group.
	 *
	 * @return the objectGroup
	 */
	public List<ObjectGroup> getObjectGroup() {
		return objectGroup;
	}

	/**
	 * Sets the object group.
	 *
	 * @param objectGroup the objectGroup to set
	 */
	public void setObjectGroup(List<ObjectGroup> objectGroup) {
		this.objectGroup = objectGroup;
	}

	/**
	 * Gets the param.
	 *
	 * @return the param
	 */
	public List<Param> getParam() {
		return param;
	}

	/**
	 * Sets the param.
	 *
	 * @param param the param to set
	 */
	public void setParam(List<Param> param) {
		this.param = param;
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
	 * Gets the replacement strings.
	 *
	 * @return the replacement_Strings
	 */
	public List<ReplacementStrings> getReplacementStrings() {
		return replacementStrings;
	}

	/**
	 * Sets the replacement strings.
	 *
	 * @param replacementStrings the new replacement strings
	 */
	public void setReplacementStrings(List<ReplacementStrings> replacementStrings) {
		this.replacementStrings = replacementStrings;
	}

	/**
	 * Gets the screen.
	 *
	 * @return the screen
	 */
	public List<Screen> getScreen() {
		return screen;
	}

	/**
	 * Sets the screen.
	 *
	 * @param screen the screen to set
	 */
	public void setScreen(List<Screen> screen) {
		this.screen = screen;
	}

	/**
	 * Gets the test data.
	 *
	 * @return the testData
	 */
	public List<TestData> getTestData() {
		return testData;
	}

	/**
	 * Sets the test data.
	 *
	 * @param testData the testData to set
	 */
	public void setTestData(List<TestData> testData) {
		this.testData = testData;
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

	/**
	 * Gets the test scenario.
	 *
	 * @return the testScenario
	 */
	public List<TestScenario> getTestScenario() {
		return testScenario;
	}

	/**
	 * Sets the test scenario.
	 *
	 * @param testScenario the testScenario to set
	 */
	public void setTestScenario(List<TestScenario> testScenario) {
		this.testScenario = testScenario;
	}

	/**
	 * Gets the test suite.
	 *
	 * @return the testSuite
	 */
	public List<TestSuite> getTestSuite() {
		return testSuite;
	}

	/**
	 * Sets the test suite.
	 *
	 * @param testSuite the testSuite to set
	 */
	public void setTestSuite(List<TestSuite> testSuite) {
		this.testSuite = testSuite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Application [AppID=" + appID + ", AppName=" + appName + ", Description=" + description + "]";
	}

	/**
	 * Sets the identifier type id.
	 *
	 * @param identifierTypeID the new identifier type id
	 */
	public void setIdentifierTypeID(int identifierTypeID) {
		this.identifierTypeID = identifierTypeID;
	}

}
