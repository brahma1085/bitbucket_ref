package com.sssoft.isatt.data.pojo.def;

import java.io.Serializable;
import java.util.List;

import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestSuite;


public class TestScenarioUI implements Serializable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private int testScenarioID;
	private String testScenarioName;
	private String description;
	private int testSuiteCount;
	private int testCasesCount;
	private int sortOrder;
	
	private List<TestSuite> suiteList;
	private List<TestCase> caseList;

	

	public int getTestScenarioID() {
		return testScenarioID;
	}

	public void setTestScenarioID(int testScenarioID) {
		this.testScenarioID = testScenarioID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTestScenarioName() {
		return testScenarioName;
	}

	public void setTestScenarioName(String testScenarioName) {
		this.testScenarioName = testScenarioName;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getTestSuiteCount() {
		return testSuiteCount;
	}

	public void setTestSuiteCount(int testSuiteCount) {
		this.testSuiteCount = testSuiteCount;
	}

	public int getTestCasesCount() {
		return testCasesCount;
	}

	public void setTestCasesCount(int testCasesCount) {
		this.testCasesCount = testCasesCount;
	}
	
	

	public List<TestSuite> getSuiteList() {
		return suiteList;
	}

	public void setSuiteList(List<TestSuite> suiteList) {
		this.suiteList = suiteList;
	}

	public List<TestCase> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<TestCase> caseList) {
		this.caseList = caseList;
	}

	@Override
	public String toString() {
		return "TestScenarioUI [testScenarioID=" + testScenarioID
				+ ", testScenarioName=" + testScenarioName + ", description="
				+ description + ", testSuiteCount=" + testSuiteCount
				+ ", testCasesCount=" + testCasesCount + ", sortOrder="
				+ sortOrder + ", suiteList=" + suiteList + ", caseList="
				+ caseList + "]";
	}

	
}
