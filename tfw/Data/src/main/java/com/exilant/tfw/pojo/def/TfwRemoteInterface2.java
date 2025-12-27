package com.exilant.tfw.pojo.def;

import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;


public interface TfwRemoteInterface2 {
    void startTestPlan(TestPlan testPlan);

    void startTestSuite(TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult);

    void startTestCase(TestPlan testPlan, TestSuite testSuite, TestSuiteResult testSuiteResult, TestCase testCase);

    void testStep(TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult, TestStep testStep, TestStepResult stepResult,
	    TestSuiteResult testSuiteResult);

    void endTestCase(TestPlan testPlan, TestSuite testSuite, TestCase testCase, TestCaseResult testCaseResult, TestStepResult testStepResult);

    void endTestSuite(TestPlan plan, TestSuite suite, TestSuiteResult testSuiteResult);

    // TestSuiteResultSummary suiteSumamryResult

    String getConfigPath();

    void setConfigPath(String configPath);

}

