package com.exilant.tfw.pojo.def;

import java.io.Serializable;
import java.util.List;

import com.exilant.tfw.pojo.input.TestScenario;
import com.exilant.tfw.pojo.input.TestStep;


public class TestCaseUI implements Serializable{
	
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private int testCaseID;
	private String caseName;
	private String description;
	private int testScenariosCount;
	private int testStepsCount;
	
	private List<TestScenario> scenarioList;
	private List<TestStep> stepList;
	
	private String classificationTag;
	private int runnerID;
	private int functionalID;
	private int featureID;
	private String active;
	private String positive;
	private int sortOrder;
	private int conditionGroupID;
	
	
	public int getTestCaseID() {
		return testCaseID;
	}

	public void setTestCaseID(int testCaseID) {
		this.testCaseID = testCaseID;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTestScenariosCount() {
		return testScenariosCount;
	}
	
	public void setTestScenariosCount(int testScenariosCount) {
		this.testScenariosCount = testScenariosCount;
	}
	
	public int getTestStepsCount() {
		return testStepsCount;
	}
	
	public void setTestStepsCount(int testStepsCount) {
		this.testStepsCount = testStepsCount;
	}

	public List<TestScenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(List<TestScenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public List<TestStep> getStepList() {
		return stepList;
	}

	public void setStepList(List<TestStep> stepList) {
		this.stepList = stepList;
	}

	
	public String getClassificationTag() {
		return classificationTag;
	}

	public void setClassificationTag(String classificationTag) {
		this.classificationTag = classificationTag;
	}

	public int getRunnerID() {
		return runnerID;
	}

	public void setRunnerID(int runnerID) {
		this.runnerID = runnerID;
	}

	public int getFunctionalID() {
		return functionalID;
	}

	public void setFunctionalID(int functionalID) {
		this.functionalID = functionalID;
	}

	public int getFeatureID() {
		return featureID;
	}

	public void setFeatureID(int featureID) {
		this.featureID = featureID;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPositive() {
		return positive;
	}

	public void setPositive(String positive) {
		this.positive = positive;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getConditionGroupID() {
		return conditionGroupID;
	}

	public void setConditionGroupID(int conditionGroupID) {
		this.conditionGroupID = conditionGroupID;
	}

	@Override
	public String toString() {
		return "TestCaseUI [testCaseID=" + testCaseID + ", caseName="
				+ caseName + ", description=" + description
				+ ", testScenariosCount=" + testScenariosCount
				+ ", testStepsCount=" + testStepsCount + ", scenarioList="
				+ scenarioList + ", stepList=" + stepList
				+ ", classificationTag=" + classificationTag + ", runnerID="
				+ runnerID + ", functionalID=" + functionalID + ", featureID="
				+ featureID + ", active=" + active + ", positive=" + positive
				+ ", sortOrder=" + sortOrder + ", conditionGroupID="
				+ conditionGroupID + "]";
	}

	
}
