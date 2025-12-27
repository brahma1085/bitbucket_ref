package com.sssoft.isatt.data.pojo.def;

import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.pojo.output.TestCaseResult;
import com.sssoft.isatt.data.pojo.output.TestStepResult;
import com.sssoft.isatt.data.pojo.output.TestSuiteResult;

public interface TfwRemoteInterfaceScheduler {

	void startTestPlan(Scheduler scheduler);

	void startTestSuite(Scheduler scheduler, TestSuite testSuite, TestSuiteResult testSuiteResult);

	void startTestCase(Scheduler scheduler, TestSuite testSuite, TestSuiteResult testSuiteResult);

	void testStep(Scheduler scheduler, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult, TestStep testStep,
			TestStepResult stepResult, TestSuiteResult testSuiteResult);

	void endTestCase(Scheduler scheduler, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult, TestStepResult testStepResult);

	// suiteSumamryResult

	String getConfigPath();

	void setConfigPath(String configPath);

}
