package com.exilant.tfw.pojo.def;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;

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
