package com.sssoft.isatt.data.pojo.def;

import java.io.Serializable;
import java.util.List;

import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;

public class TestSuiteUI implements Serializable {

	private static final long serialVersionUID = 1L;

	private int testSuiteId;
	private String testSuiteName;
	private String testSuiteDesc;
	private int sortOrder;
	private int testPlanCount;
	private int testScenariosCount;
	private List<TestPlan> plansList;
	private List<TestScenario> scenarioList;
	
	public List<TestPlan> getPlansList() {
		return plansList;
	}

	public void setPlansList(List<TestPlan> plansList) {
		this.plansList = plansList;
	}

	public List<TestScenario> getScenarioList() {
		return scenarioList;
	}

	public void setScenarioList(List<TestScenario> scenarioList) {
		this.scenarioList = scenarioList;
	}

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public String getTestSuiteDesc() {
		return testSuiteDesc;
	}

	public void setTestSuiteDesc(String testSuiteDesc) {
		this.testSuiteDesc = testSuiteDesc;
	}

	public int getTestPlanCount() {
		return testPlanCount;
	}

	public void setTestPlanCount(int testPlanCount) {
		this.testPlanCount = testPlanCount;
	}

	public int getTestScenariosCount() {
		return testScenariosCount;
	}

	public void setTestScenariosCount(int testScenariosCount) {
		this.testScenariosCount = testScenariosCount;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "TestSuiteUI [testSuiteId=" + testSuiteId + ", testSuiteName="
				+ testSuiteName + ", testSuiteDesc=" + testSuiteDesc
				+ ", sortOrder=" + sortOrder + ", testPlanCount="
				+ testPlanCount + ", testScenariosCount=" + testScenariosCount
				+ ", plansList=" + plansList + ", scenarioList=" + scenarioList
				+ "]";
	}


}
