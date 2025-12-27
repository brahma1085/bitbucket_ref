package com.sssoft.isatt.data.pojo.def;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sssoft.isatt.data.pojo.input.TestSuite;

public class TestPlanUI implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	private int testPlanId;
	private String testPlanName;
	private String testPlanDesc;
	private int testSuiteCount;
	
	private List<TestSuite> suiteList; 
	
	public List<TestSuite> getSuiteList() {
		return suiteList;
	}
	public void setSuiteList(List<TestSuite> suiteList) {
		this.suiteList = suiteList;
	}
	public int getTestPlanId() {
		return testPlanId;
	}
	public void setTestPlanId(int testPlanId) {
		this.testPlanId = testPlanId;
	}
	public String getTestPlanName() {
		return testPlanName;
	}
	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}
	public String getTestPlanDesc() {
		return testPlanDesc;
	}
	public void setTestPlanDesc(String testPlanDesc) {
		this.testPlanDesc = testPlanDesc;
	}
	public int getTestSuiteCount() {
		return testSuiteCount;
	}
	public void setTestSuiteCount(int testSuiteCount) {
		this.testSuiteCount = testSuiteCount;
	}
	

	
	@Override
	public String toString() {
		return "TestPlanUI [testPlanId=" + testPlanId + ", testPlanName=" + testPlanName + ", testPlanDesc=" + testPlanDesc
				+ ", testSuiteCount=" + testSuiteCount + "]";
	}
}
