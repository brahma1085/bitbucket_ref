package com.exilant.tfw.pojo.def;

import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;

public interface TfwRemoteInterface {

	void startTestPlan(Scheduler scheduler, TestPlan testPlan);

	void startTestSuite(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult);

	void startTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult);

	void testStep(TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult testStepResult, Param param);

	void endTestCase(Scheduler scheduler, TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult,
			TestStepResult testStepResult);

	String getConfigPath();

	void setConfigPath(String configPath);

}
