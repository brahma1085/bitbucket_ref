package com.sssoft.isatt.ui2.controller;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.pojo.output.ApplicationFlow;
import com.sssoft.isatt.data.pojo.output.TestingFlow;
import com.sssoft.isatt.data.service.MainService;

/**
 * The Class TestingFlowController2.
 */
@Controller
public class TestingFlowController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestingFlowController2.class);

	/** The Constant TYPE_APP. */
	private static final String TYPE_APP = "Application";

	/** The Constant TYPE_PLAN. */
	private static final String TYPE_PLAN = "TestPlan";

	/** The Constant TYPE_SUITE. */
	private static final String TYPE_SUITE = "TestSuite";

	/** The Constant TYPE_SCENARIO. */
	private static final String TYPE_SCENARIO = "TestScenario";

	/** The Constant TYPE_CASE. */
	private static final String TYPE_CASE = "TestCase";

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Gets the testing flow.
	 * 
	 * @param appId
	 *            the app id
	 * @return the testing flow
	 */
	@RequestMapping(value = "/getTestingFlow", method = RequestMethod.POST)
	public @ResponseBody
	String getTestingFlow(@RequestBody int appId) {
		try {
			ApplicationFlow applicationFlow = getApplicationFlow(appId);
			Gson gson = new Gson();
			return gson.toJson(applicationFlow);
		} catch (Exception e) {
			LOG.error("Error Occured due to " + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Gets the application flow.
	 * 
	 * @param appId
	 *            the app id
	 * @return the application flow
	 * @throws ServiceException
	 *             the service exception
	 */
	public ApplicationFlow getApplicationFlow(int appId) throws ServiceException {
		ApplicationFlow appFlow = new ApplicationFlow();
		Application application = this.mainService.getApplicationForFlowChart(appId);
		int applicationId = application.getAppID();
		appFlow.setId(applicationId);
		appFlow.setName(application.getAppName());
		appFlow.setType(TYPE_APP);
		List<TestingFlow> tstFlow = new LinkedList<TestingFlow>();
		List<TestPlan> testPlans = application.getTestPlan();
		Iterator<TestPlan> planItr = testPlans.iterator();
		while (planItr.hasNext()) {
			TestingFlow tstPlanFlow = new TestingFlow();
			TestPlan testPlan = (TestPlan) planItr.next();
			int planId = testPlan.getTestPlanID();
			tstPlanFlow.setId(planId);
			tstPlanFlow.setName(testPlan.getPlanName());
			tstPlanFlow.setType(TYPE_PLAN);
			tstPlanFlow.setParentId(applicationId);
			tstFlow.add(tstPlanFlow);
			List<TestSuite> testSuites = testPlan.getTestSuite();
			Iterator<TestSuite> suiteItr = testSuites.iterator();
			while (suiteItr.hasNext()) {
				TestingFlow tstSuiteFlow = new TestingFlow();
				TestSuite testSuite = (TestSuite) suiteItr.next();
				int suiteId = testSuite.getTestSuiteID();
				tstSuiteFlow.setId(suiteId);
				tstSuiteFlow.setName(testSuite.getSuiteName());
				tstSuiteFlow.setType(TYPE_SUITE);
				tstSuiteFlow.setParentId(planId);
				tstFlow.add(tstSuiteFlow);
				List<TestScenario> testScenarios = testSuite.getTestScenario();
				Iterator<TestScenario> scenarioItr = testScenarios.iterator();
				while (scenarioItr.hasNext()) {
					TestingFlow tstScenarioFlow = new TestingFlow();
					TestScenario testScenario = (TestScenario) scenarioItr.next();
					int scenarioId = testScenario.getTestScenarioID();
					tstScenarioFlow.setId(scenarioId);
					tstScenarioFlow.setName(testScenario.getTestScenarioName());
					tstScenarioFlow.setType(TYPE_SCENARIO);
					tstScenarioFlow.setParentId(suiteId);
					tstFlow.add(tstScenarioFlow);
					List<TestCase> testCases = testScenario.getTestCase();
					Iterator<TestCase> caseItr = testCases.iterator();
					while (caseItr.hasNext()) {
						TestingFlow tstCaseFlow = new TestingFlow();
						TestCase testCase = (TestCase) caseItr.next();
						tstCaseFlow.setId(testCase.getTestCaseID());
						tstCaseFlow.setName(testCase.getCaseName());
						tstCaseFlow.setType(TYPE_CASE);
						tstCaseFlow.setParentId(scenarioId);
						tstFlow.add(tstCaseFlow);
					}
				}
			}
		}
		appFlow.setFlow(tstFlow);
		return appFlow;
	}
}
