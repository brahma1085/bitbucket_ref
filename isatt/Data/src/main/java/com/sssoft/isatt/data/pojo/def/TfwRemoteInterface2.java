package com.sssoft.isatt.data.pojo.def;

import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.pojo.output.TestCaseResult;
import com.sssoft.isatt.data.pojo.output.TestStepResult;
import com.sssoft.isatt.data.pojo.output.TestSuiteResult;


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

