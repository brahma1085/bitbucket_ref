package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.Runner;
import com.sssoft.isatt.data.pojo.output.TestCaseResult;

/**
 * The Class TestCase.
 *
 * @author mohammedfirdos
 */
public class TestCase implements Serializable {

	/** TestCase Entity. */
	private static final long serialVersionUID = 1L;

	/** The test case id. */
	private int testCaseID;
	
	/** The classification tag. */
	private String classificationTag;
	
	/** The case name. */
	private String caseName;
	
	/** The runner id. */
	private int runnerID;
	
	/** The description. */
	private String description;
	
	/** The functional id. */
	private int functionalID;
	
	/** The feature id. */
	private int featureID;
	
	/** The active. */
	private String active;
	
	/** The positive. */
	private String positive;
	
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
	
	/** The condition group id. */
	private int conditionGroupID;
	
	/** The param group. */
	private List<ParamGroup> paramGroup = new ArrayList<ParamGroup>(0);
	
	/** The app functionality. */
	private AppFunctionality appFunctionality;

	/**
	 * Gets the app feature.
	 *
	 * @return the app feature
	 */
	public AppFeature getAppFeature() {
		return appFeature;
	}

	/**
	 * Sets the app feature.
	 *
	 * @param appFeature the new app feature
	 */
	public void setAppFeature(AppFeature appFeature) {
		this.appFeature = appFeature;
	}

	/** The test case result. */
	private List<TestCaseResult> testCaseResult = new ArrayList<TestCaseResult>(0);
	
	/** The test step. */
	private List<TestStep> testStep = new ArrayList<TestStep>(0);
	
	/** The runner. */
	private Runner runner = new Runner();

	/** The condition group. */
	private ConditionGroup conditionGroup;
	
	/** The test step obj. */
	private TestStep testStepObj;
	
	/** The app feature. */
	private AppFeature appFeature;

	/** The scenario case mappings. */
	private List<ScenarioCaseMapping> scenarioCaseMappings = new ArrayList<ScenarioCaseMapping>(0);
	
	/** The case step mappings. */
	private List<CaseStepMapping> caseStepMappings = new ArrayList<CaseStepMapping>(0);

	/**
	 * Gets the case step mappings.
	 *
	 * @return the case step mappings
	 */
	public List<CaseStepMapping> getCaseStepMappings() {
		return caseStepMappings;
	}

	/**
	 * Sets the case step mappings.
	 *
	 * @param caseStepMappings the new case step mappings
	 */
	public void setCaseStepMappings(List<CaseStepMapping> caseStepMappings) {
		this.caseStepMappings = caseStepMappings;
	}

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
	 * Gets the test step obj.
	 *
	 * @return the test step obj
	 */
	public TestStep getTestStepObj() {
		return testStepObj;
	}

	/**
	 * Sets the test step obj.
	 *
	 * @param testStepObj the new test step obj
	 */
	public void setTestStepObj(TestStep testStepObj) {
		this.testStepObj = testStepObj;
	}

	/**
	 * Gets the condition group.
	 *
	 * @return the condition group
	 */
	public ConditionGroup getConditionGroup() {
		return conditionGroup;
	}

	/**
	 * Sets the condition group.
	 *
	 * @param conditionGroup the new condition group
	 */
	public void setConditionGroup(ConditionGroup conditionGroup) {
		this.conditionGroup = conditionGroup;
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
	 * Gets the classification tag.
	 *
	 * @return the classificationTag
	 */
	public String getClassificationTag() {
		return classificationTag;
	}

	/**
	 * Sets the classification tag.
	 *
	 * @param classificationTag the classificationTag to set
	 */
	public void setClassificationTag(String classificationTag) {
		this.classificationTag = classificationTag;
	}

	/**
	 * Gets the case name.
	 *
	 * @return the caseName
	 */
	public String getCaseName() {
		return caseName;
	}

	/**
	 * Sets the case name.
	 *
	 * @param caseName the caseName to set
	 */
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	/**
	 * Gets the runner id.
	 *
	 * @return the runnerID
	 */
	public int getRunnerID() {
		return runnerID;
	}

	/**
	 * Sets the runner id.
	 *
	 * @param runnerID the runnerID to set
	 */
	public void setRunnerID(int runnerID) {
		this.runnerID = runnerID;
	}

	/**
	 * Gets the functional id.
	 *
	 * @return the functionalID
	 */
	public int getFunctionalID() {
		return functionalID;
	}

	/**
	 * Sets the functional id.
	 *
	 * @param functionalID the functionalID to set
	 */
	public void setFunctionalID(int functionalID) {
		this.functionalID = functionalID;
	}

	/**
	 * Gets the feature id.
	 *
	 * @return the featureID
	 */
	public int getFeatureID() {
		return featureID;
	}

	/**
	 * Sets the feature id.
	 *
	 * @param featureID the featureID to set
	 */
	public void setFeatureID(int featureID) {
		this.featureID = featureID;
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
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the positive.
	 *
	 * @return the positive
	 */
	public String getPositive() {
		return positive;
	}

	/**
	 * Sets the positive.
	 *
	 * @param positive the positive to set
	 */
	public void setPositive(String positive) {
		this.positive = positive;
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
	 * Gets the test case result.
	 *
	 * @return the testCaseResult
	 */
	public List<TestCaseResult> getTestCaseResult() {
		return testCaseResult;
	}

	/**
	 * Sets the test case result.
	 *
	 * @param testCaseResult the testCaseResult to set
	 */
	public void setTestCaseResult(List<TestCaseResult> testCaseResult) {
		this.testCaseResult = testCaseResult;
	}

	/**
	 * Gets the test step.
	 *
	 * @return the testStep
	 */
	public List<TestStep> getTestStep() {
		return testStep;
	}

	/**
	 * Sets the test step.
	 *
	 * @param testStep the testStep to set
	 */
	public void setTestStep(List<TestStep> testStep) {
		this.testStep = testStep;
	}

	/**
	 * Gets the runner.
	 *
	 * @return the runner
	 */
	public Runner getRunner() {
		return runner;
	}

	/**
	 * Sets the runner.
	 *
	 * @param runner the runner to set
	 */
	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	/**
	 * Gets the app functionality.
	 *
	 * @return the app functionality
	 */
	public AppFunctionality getAppFunctionality() {
		return appFunctionality;
	}

	/**
	 * Sets the app functionality.
	 *
	 * @param appFunctionality the new app functionality
	 */
	public void setAppFunctionality(AppFunctionality appFunctionality) {
		this.appFunctionality = appFunctionality;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCase [TestCaseID=" + testCaseID + ", ClassificationTag=" + classificationTag + ", CaseName=" + caseName + ", RunnerID=" + runnerID
				+ ", Description=" + description + ", Active=" + active + ", Positive=" + positive + ", SortOrder=" + sortOrder + ", ConditionGroupID="
				+ conditionGroupID + "]";
	}

}
