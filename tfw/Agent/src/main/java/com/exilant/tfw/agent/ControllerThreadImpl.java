package com.exilant.tfw.agent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.exilant.tfw.agent.exception.AgentException;
import com.exilant.tfw.bean.MailingBean;
import com.exilant.tfw.bean.UtlConf;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.def.AgentRunResult;
import com.exilant.tfw.pojo.input.CaseStepMapping;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.ParamGroup;
import com.exilant.tfw.pojo.input.PlanSuiteMapping;
import com.exilant.tfw.pojo.input.ScenarioCaseMapping;
import com.exilant.tfw.pojo.input.SuiteScenarioMapping;
import com.exilant.tfw.pojo.input.TestCase;
import com.exilant.tfw.pojo.input.TestScenario;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.input.TestSuite;
import com.exilant.tfw.pojo.output.LaneResults;
import com.exilant.tfw.pojo.output.TaskResult;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestPlanResult;
import com.exilant.tfw.pojo.output.TestScenarioResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.service.OutputService;
import com.exilant.tfw.util.VideoRecording;
import com.exilant.tfw.util.mail.iTAPSSLMailingUtility;
import com.exilant.tfw.utils.DataConstants;

/**
 * The Class ControllerThreadImpl.
 */
public final class ControllerThreadImpl implements ControllerThread {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ControllerThreadImpl.class);
	
	/** The thread exe. */
	private static java.util.concurrent.ThreadPoolExecutor threadExe;
	
	/** The main service. */
	private MainService mainService;
	
	/** The output service. */
	private OutputService outputService;
	
	/** The scheduler. */
	private Scheduler scheduler;

	/**
	 * Sets the main service.
	 *
	 * @param mainService the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Sets the output service.
	 *
	 * @param outputService the new output service
	 */
	public void setOutputService(OutputService outputService) {
		this.outputService = outputService;
	}

	/* (non-Javadoc)
	 * @see com.exilant.tfw.agent.ControllerThread#initScheduler(int)
	 */
	public void initScheduler(int schedulerId) throws AgentException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initScheduler(int) - start"); //$NON-NLS-1$
		}

		try {
			scheduler = mainService.getSchedulerWithAllData(schedulerId);
		} catch (ServiceException e) {
			LOG.error("initScheduler(int)", e); //$NON-NLS-1$

			throw new AgentException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("initScheduler(int) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Constructor to re use other scheduler.
	 *
	 */

	public ControllerThreadImpl() {
		super();
	}

	/**
	 * In general use pools from utils instead. This class is called from lane
	 * process now for threading
	 *
	 * @return the thread exe
	 */
	public static ThreadPoolExecutor getThreadExe() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getThreadExe() - start"); //$NON-NLS-1$
		}

		if (threadExe == null) {
			LinkedBlockingQueue<Runnable> que = new LinkedBlockingQueue<Runnable>();
			threadExe = new ThreadPoolExecutor(1, 5, 100, TimeUnit.HOURS, que);
			LOG.info("Inside ThreadPoolExecutor");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getThreadExe() - end"); //$NON-NLS-1$
		}
		return threadExe;
	}

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public static String getInfo() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getInfo() - start"); //$NON-NLS-1$
		}

		ThreadPoolExecutor tpe = ControllerThreadImpl.getThreadExe();
		StringBuilder sb = new StringBuilder().append("Task Count :");
		sb.append(tpe.getTaskCount()).append(", completed count : ").append(tpe.getCompletedTaskCount()).append(", active :").append(tpe.getActiveCount());
		String returnString = sb.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getInfo() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Start.
	 *
	 * @param agntTh the agnt th
	 */
	public static void start(ControllerThread agntTh) {
		LOG.info("inside start ");
		getThreadExe().execute(agntTh);

		if (LOG.isDebugEnabled()) {
			LOG.debug("start(ControllerThread) - end"); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - start"); //$NON-NLS-1$
		}

		process();

		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Process.
	 *
	 * @return the map
	 */
	public Map<String, Object> process() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("process() - start"); //$NON-NLS-1$
		}

		Map<String, Object> rtn = new HashMap<String, Object>();
		LOG.info("inside Run");
		long testRunId = 0;
		Controller controller = new Controller();
		SchedulerRunDetails schedulerRunDetails = new SchedulerRunDetails();
		int schedulerID = scheduler.getScheduleID();
		schedulerRunDetails.setScheduleID(schedulerID);
		schedulerRunDetails.setAppID(scheduler.getAppID());
		schedulerRunDetails.setStartDateTime(new Timestamp(System.currentTimeMillis()));
		schedulerRunDetails.setTestPlanID(scheduler.getTestPlan().getTestPlanID());
		schedulerRunDetails.setTestDataID(scheduler.getTestData().getTestDataID());
		String toAddress = scheduler.getNotifications();

		try {
			synchronized (this) {
				testRunId = mainService.insertSchedulerRunDetailsGetKey(schedulerRunDetails);
			}
		} catch (ServiceException e) {
			LOG.error("Exception details " + e, e);
		}
		schedulerRunDetails.setTestRunID((int) testRunId);
		TaskResult taskResult = new TaskResult();
		taskResult.setStartTime(new java.sql.Date(System.currentTimeMillis()));
		LOG.info(" test Plan ID :" + scheduler.getTestPlan().getTestPlanID());

		controller.startTestPlan(scheduler, scheduler.getTestPlan());

		taskResult.setAgentOS(System.getProperty("os.name") + "-" + System.getProperty("os.version"));

		TestPlanResult planResult = new TestPlanResult();
		planResult.setTestPlanID(scheduler.getTestPlan().getTestPlanID());
		planResult.setStartDateTime(new Timestamp(System.currentTimeMillis()));

		List<PlanSuiteMapping> planSuiteMappingsList = scheduler.getTestPlan().getPlanSuiteMappings();
		Iterator<PlanSuiteMapping> iterPlanSuiteMap = planSuiteMappingsList.iterator();
		List<TestSuiteResult> testSuiteResults = new ArrayList<TestSuiteResult>();
		while (iterPlanSuiteMap.hasNext()) {
			PlanSuiteMapping planSuiteMapping = (PlanSuiteMapping) iterPlanSuiteMap.next();
			TestSuite testSuite = planSuiteMapping.getTestSuite();
			TestSuiteResult testSuiteResult = new TestSuiteResult();
			testSuiteResult.setTestSuiteID(testSuite.getTestSuiteID());
			testSuiteResult.setStartDateTime(new Timestamp(System.currentTimeMillis()));
			long beforeSuiteStart = System.currentTimeMillis();
			testSuiteResult.setTestSuite(testSuite);
			controller.startTestSuite(scheduler, scheduler.getTestPlan(), testSuite, testSuiteResult);

			List<SuiteScenarioMapping> suiteScenarioMappingList = testSuite.getSuiteScenarioMappings();
			Iterator<SuiteScenarioMapping> iterSuiteScenario = suiteScenarioMappingList.iterator();
			List<TestScenarioResult> testScenarioResults = new ArrayList<TestScenarioResult>();
			LOG.info("For Test Suites ++++");
			LOG.info("Suite Scenario size " + suiteScenarioMappingList.size());
			VideoRecording videoRecording = null;
			while (iterSuiteScenario.hasNext()) {

				SuiteScenarioMapping suiteScenarioMapping = iterSuiteScenario.next();

				TestScenario testScenario = suiteScenarioMapping.getTestScenario();
				if (UtlConf.getProperty("VIDEO_RECORDING_ENABLE") == "1") {
					videoRecording = new VideoRecording();
					videoRecording.startVideoCaptureServer();
					videoRecording.startVideoCapture(testScenario
							.getTestScenarioName());
				}
				TestScenarioResult testScenarioResult = new TestScenarioResult();
				testScenarioResult.setStartDateTime(new Timestamp(System.currentTimeMillis()));
				testScenarioResult.setTestScenarioID(testScenario.getTestScenarioID());
				int currToTest = 0;
				int tstCaseCntr = 0;
				int numberOfTestCases;
				List<ScenarioCaseMapping> scenarioCaseMappingList = testScenario.getScenarioCaseMappings();
				List<TestCaseResult> testCaseResults = new ArrayList<TestCaseResult>();
				Iterator<ScenarioCaseMapping> iterScenarioCase = scenarioCaseMappingList.iterator();

				while (iterScenarioCase.hasNext()) {
					ScenarioCaseMapping scenarioCaseMapping = (ScenarioCaseMapping) iterScenarioCase.next();
					TestScenario testScenario2 = scenarioCaseMapping.getTestScenario();
					TestCase tcase = scenarioCaseMapping.getTestCase();
					String runnerName = tcase.getRunner().getRunnerName();
					TestCaseResult testCaseResult = new TestCaseResult();
					testCaseResult.setStartDateTime(new Timestamp(System.currentTimeMillis()));
					testCaseResult.setTestCaseID(tcase.getTestCaseID());

					if (!("1".equalsIgnoreCase(tcase.getActive()) || "Y".equalsIgnoreCase(tcase.getActive()))) {
						testCaseResult.setResult(2);
						testCaseResult.setComment("Skipped");
						testCaseResults.add(testCaseResult);
						continue;
					} else {
						String stepErrorMsg = "";
						String stepSuccessMsg = "";
						int passCount = 0;
						int failCount = 0;
						int skipCount = 0;
						long beforeStart = System.currentTimeMillis();

						List<TestStepResult> testStepResults = new ArrayList<TestStepResult>();
						LOG.info("Test case name" + tcase.getCaseName());
						LOG.info("For TestSteps ++++");
						LOG.info("Test Steps size " + tcase.getTestStep().size());

						List<CaseStepMapping> caseStepMappingList = tcase.getCaseStepMappings();
						Iterator<CaseStepMapping> iterCaseStep = caseStepMappingList.iterator();
						int tstStepCtr = 0;
						boolean flag = false;
						while (iterCaseStep.hasNext()) {
							CaseStepMapping caseStepMapping = (CaseStepMapping) iterCaseStep.next();
							TestCase testCase = caseStepMapping.getTestCase();
							TestStep step = caseStepMapping.getTestStep();

							LOG.log(Level.INFO, "Case :" + tstCaseCntr + ", tst step :" + tstStepCtr + ", currToTest " + currToTest);
							try {
								TestStepResult testStepResult = new TestStepResult();
								if (!flag) {
									LOG.info("step >>>>" + step.getStepName());

									testStepResult.setTestStepID(step.getTestStepID());
									Timestamp stepStartTimestamp = new Timestamp(System.currentTimeMillis());
									testStepResult.setStartDateTime(stepStartTimestamp);
									if (step.getActions() != null) {
										LOG.log(Level.INFO, "getActionname :" + step.getActions().getActionName());

									} else {
										LOG.info("Action obj null");
									}
									List<ParamGroup> paramGroupList = step.getParamGroup();
									LOG.info(" ParamGroup Size " + paramGroupList.size());
									if (paramGroupList.size() > 0) {
										Iterator<ParamGroup> iterParamGroup = paramGroupList.iterator();
										while (iterParamGroup.hasNext()) {
											ParamGroup paramGroup = (ParamGroup) iterParamGroup.next();
											List<Param> paramsList = paramGroup.getParam();
											if (paramsList != null && paramsList.size() <= 0 && runnerName != null && "HTTP".equalsIgnoreCase(runnerName)) {
												controller.testStep(tcase, testCaseResult, step, testStepResult, null);
												LOG.info("Called the respective Runner & testStepResult is =" + testStepResult.getResult());
											} else if (runnerName != null && "JUNIT".equalsIgnoreCase(runnerName)) {
												controller.testStep(tcase, testCaseResult, step, testStepResult, null);
												LOG.info("Called the respective Runner & testStepResult is =" + testStepResult.getResult());
												break;
											}
											Iterator<Param> iterParam = paramsList.iterator();
											while (iterParam.hasNext()) {
												Param param = (Param) iterParam.next();
												controller.testStep(tcase, testCaseResult, step, testStepResult, param);
												LOG.info("Called the respective Runner & testStepResult is =" + testStepResult.getResult());
											}
										}
									}
									Timestamp stepEndTimestamp = new Timestamp(System.currentTimeMillis());
									testStepResult.setEndDateTime(stepEndTimestamp);
									long stepDuration = stepEndTimestamp.getTime() - stepStartTimestamp.getTime();
									testStepResult.setDuration((int) stepDuration);
									if (testStepResult.getComment() != null && testStepResult.getComment().length() >= 4000) {
										testStepResult.setComment(testStepResult.getComment().substring(0, 3999));
									}
									if (testStepResult.getException() != null && testStepResult.getException().length() >= 4000) {
										testStepResult.setException(testStepResult.getException().substring(0, 3999));
									}

									if (testStepResult.getResult() == 0) {
										stepErrorMsg = testStepResult.getComment();
										flag = true;
									} else {
										stepSuccessMsg = testStepResult.getComment();
									}
								} else {
									testStepResult.setTestStepID(caseStepMapping.getTestStepID());
									testStepResult.setStartDateTime(new Timestamp(System.currentTimeMillis()));
									testStepResult.setEndDateTime(new Timestamp(System.currentTimeMillis()));
									testStepResult.setResult(2);
									testStepResult.setComment("Skipping step as predecssar failed");
									testStepResult.setDuration(0);
									LOG.info("Skipping step as predecssar failed");
								}
								tstStepCtr++;
								testStepResults.add(testStepResult);
							} catch (Exception e) {
								LOG.warn("step error " + e, e);
							}
						}
						if (stepErrorMsg != null && !"".equals(stepErrorMsg)) {
							testCaseResult.setComment(stepErrorMsg);
							testCaseResult.setException(stepErrorMsg);

						} else {
							testCaseResult.setComment(stepSuccessMsg);

						}
						testCaseResult.setTestStepResultsList(testStepResults);
						Iterator<TestStepResult> itr1 = testStepResults.iterator();
						while (itr1.hasNext()) {
							TestStepResult testStepRslt = itr1.next();
							if (testStepRslt.getResult() == 1) {
								passCount++;
							} else if (testStepRslt.getResult() == 0) {
								failCount++;
							} else {
								skipCount++;

							}
						}

						if (testStepResults.size() == passCount) {
							testCaseResult.setRunStatus(DataConstants.PASSED_AFTER_RUN);
							testCaseResult.setResult(1);

						} else if (failCount > 0) {
							testCaseResult.setRunStatus(DataConstants.FAILED_AFTER_RUN);
							testCaseResult.setResult(0);
						}

						testCaseResult.setTotalCount(testStepResults.size());
						testCaseResult.setPercentagePassCount(passCount);
						testCaseResult.setPercentageFailCount(failCount);
						testCaseResult.setPercentageSkipCount(skipCount);

						Date eDt = new Date();
						long afterEnd = System.currentTimeMillis();
						double d = afterEnd - beforeStart;
						testCaseResult.setTimeDuration(d);

						controller.endTestCase(scheduler, testSuite, tcase, null, null);
						testCaseResult.setEndDateTime(new Timestamp(System.currentTimeMillis()));

					}
					try {
						testCaseResult.setTestRunID((int) testRunId);
						testCaseResult.setTestScenarioID(testScenario.getTestScenarioID());
						outputService.insertTestCaseResultGetKey(testCaseResult);
					} catch (ServiceException e) {
						LOG.error("Exception details " + e, e);
					}
					List<TestStepResult> testStepResultsToDB = testCaseResult.getTestStepResultsList();
					Iterator<TestStepResult> iterStepRes = testStepResultsToDB.iterator();
					while (iterStepRes.hasNext()) {
						TestStepResult testStepResult = (TestStepResult) iterStepRes.next();
						testStepResult.setTestRunID((int) testRunId);
						testStepResult.setTestCaseID(tcase.getTestCaseID());
						try {
							outputService.insertTestStepResultGetKey(testStepResult);
						} catch (ServiceException e) {
							LOG.error("Exception details " + e, e);
						}

					}
					testCaseResults.add(testCaseResult);

					testScenarioResult.setCaseResultsList(testCaseResults);
					List<TestCaseResult> testCaseRslt = testScenarioResult.getCaseResultsList();
					Iterator<TestCaseResult> itr2 = testCaseRslt.iterator();
					int passCaseCnt = 0;
					int failCaseCnt = 0;
					int skipCaseCnt = 0;
					while (itr2.hasNext()) {
						TestCaseResult testCasRsl = itr2.next();

						if (testCasRsl.getRunStatus() == 0) {
							skipCaseCnt++;
						} else if (testCasRsl.getRunStatus() == 1) {
							passCaseCnt++;
						} else if (testCasRsl.getRunStatus() == 2) {
							failCaseCnt++;
						}
					}
					testScenarioResult.setPercentagePassCount(passCaseCnt);
					testScenarioResult.setPercentageFailCount(failCaseCnt);
					testScenarioResult.setPercentageSkipCount(skipCaseCnt);
					testScenarioResult.setResult(false);
					if (testScenarioResult.getPercentagePassCount() == scenarioCaseMappingList.size() && tcase != null) {
						testScenarioResult.setResult(true);
					} else if (((testScenarioResult.getPercentagePassCount()) + (testScenarioResult.getPercentageSkipCount())) == testScenarioResult
							.getCaseResultsList().size()) {
						testScenarioResult.setResult(true);
					}
					tstCaseCntr++;
					if (!testScenarioResult.isResult()) {
						MailingBean mail = new MailingBean();
						mail.setMailSubject("iTAP - Scheduler Run Failed");
						StringBuffer buffer = new StringBuffer();
						buffer.append("iTAP - Scheduler Run Failed whose Schedule Id is :" + schedulerID);
						buffer.append("\n" + "And the Scheduler Run ID is : " + testRunId);
						buffer.append("\n" + "The following is the Exception Message : ");
						buffer.append("" + testCaseResult.getException());
						mail.setMailText(buffer.toString());
						mail.setToAddress(toAddress);
						iTAPSSLMailingUtility.sendMail(mail, false);
					}
				}
				testScenarioResult.setEndDateTime(new Timestamp(System.currentTimeMillis()));
				try {
					testScenarioResult.setTestRunID((int) testRunId);
					testScenarioResult.setTestSuiteID(testSuite.getTestSuiteID());
					outputService.insertTestScenarioResultGetKey(testScenarioResult);
				} catch (ServiceException e) {
					LOG.error("Exception details " + e, e);
				}
				if (videoRecording != null) {
					videoRecording.stopVideoCapture();
					videoRecording.stopVideoCaptureServer();
				}
				testScenarioResults.add(testScenarioResult);
				testSuiteResult.setTestScenarioResults(testScenarioResults);
				List<TestScenarioResult> testScenarioResultsToDb = testSuiteResult.getTestScenarioResults();
				Iterator<TestScenarioResult> itrScenarioResult = testScenarioResultsToDb.iterator();
				int passScenarioCnt = 0;
				int failScenarioCnt = 0;
				while (itrScenarioResult.hasNext()) {
					TestScenarioResult scenarioResult = itrScenarioResult.next();
					if (scenarioResult.isResult()) {
						passScenarioCnt++;
					} else {
						failScenarioCnt++;
					}
				}
				long afterSuiteEnd = System.currentTimeMillis();
				double d1 = afterSuiteEnd - beforeSuiteStart;
				testSuiteResult.setDuration(d1);
				testSuiteResult.setTotalCount(testScenarioResults.size());
				testSuiteResult.setPercentagePassCount(passScenarioCnt);
				testSuiteResult.setPercentageFailCount(failScenarioCnt);
				testSuiteResult.setResult(false);
				if (testSuiteResult.getPercentagePassCount() == testSuiteResult.getTestScenarioResults().size()) {
					testSuiteResult.setResult(true);
				}
				testSuiteResult.setEndDateTime(new Timestamp(System.currentTimeMillis()));
				try {
					testSuiteResult.setTestRunID((int) testRunId);
					testSuiteResult.setTestPlanID(scheduler.getTestPlan().getTestPlanID());
					outputService.insertTestSuiteResultGetKey(testSuiteResult);
				} catch (ServiceException e) {
					LOG.error("Exception details " + e, e);
				}
				testSuiteResults.add(testSuiteResult);

			}
			planResult.setTestSuiteResultList(testSuiteResults);
			int passSuiteCount = 0;
			int failSuiteCount = 0;

			List<TestSuiteResult> testSuiteResultsList = planResult.getTestSuiteResultList();
			Iterator<TestSuiteResult> itrTestSuite = testSuiteResultsList.iterator();
			while (itrTestSuite.hasNext()) {
				TestSuiteResult suiteResult = itrTestSuite.next();
				if (suiteResult.isResult()) {
					passSuiteCount++;
				} else {
					failSuiteCount++;
				}
			}

			planResult.setResult(false);
			if (passSuiteCount == planResult.getTestSuiteResultList().size()) {
				planResult.setResult(true);
			}
			planResult.setPercentagePassCount(passSuiteCount);
			planResult.setPercentageFailCount(failSuiteCount);
			planResult.setTestSuiteResultList(testSuiteResults);
			planResult.setTestPlanID(scheduler.getTestPlanID());
			planResult.setTestPlanRunName(scheduler.getTestPlan().getPlanName());
			planResult.setEndDateTime(new Timestamp(System.currentTimeMillis()));
		}
		try {
			planResult.setTestRunID((int) testRunId);
			outputService.insertTestPlanResultGetKey(planResult);
		} catch (ServiceException e) {
			LOG.error("Exception details " + e, e);
		}
		taskResult.setResult(planResult.isResult());
		taskResult.setEndTime(new java.sql.Date(System.currentTimeMillis()));
		taskResult.setTaskResultID(planResult.getTestPlanID());
		taskResult.setTestPlan(scheduler.getTestPlan());

		String agentRunnerId = System.getProperty("Agent.self.id", "1");
		taskResult.setAgentID(new Integer(agentRunnerId));
		String nm = "agent" + agentRunnerId + "name";
		String agentname = UtlConf.getProperty(nm, "tushar");
		taskResult.setAgentName(agentname);
		nm = "agent" + agentRunnerId + "ip";
		taskResult.setIp(UtlConf.getProperty(nm, "localhost"));
		nm = "agent" + agentRunnerId + "port";
		taskResult.setPort(Integer.parseInt(UtlConf.getProperty(nm, "8180")));

		LOG.info("After setting the agent Name :" + taskResult.getAgentName() + "; ip :" + taskResult.getIp() + ", port :" + taskResult.getPort() + ".");

		/**
		 * Need two path 1 for main Report & 2nd for TestCase so removing the
		 * 2nd parameter from File
		 */
		StringBuilder sb = new StringBuilder().append("Completed Lane ");
		LaneResults laneResult = new LaneResults();
		// laneResult.setTaskResult(taskResult);
		laneResult.setResult(taskResult.isResult());
		long schedulerEndTime = System.currentTimeMillis();
		schedulerRunDetails.setTestPlanResult(planResult);
		schedulerRunDetails.setResult(planResult.isResult());
		schedulerRunDetails.setPercentagePassCount(planResult.getPercentagePassCount());
		schedulerRunDetails.setPercentageFailCount(planResult.getPercentageFailCount());
		schedulerRunDetails.setEndDateTime(new Timestamp(System.currentTimeMillis()));
		try {
			mainService.updateSchedulerRunDetails(schedulerRunDetails);
		} catch (ServiceException e) {
			LOG.error("Exception details " + e, e);
		}
		LOG.info("done  task (AgentThread process)");
		return rtn;
	}

	/**
	 * Gets the agent.
	 *
	 * @param runReslt the run reslt
	 * @return the agent
	 */
	public AgentRunResult getAgent(AgentRunResult runReslt) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgent(AgentRunResult) - start"); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgent(AgentRunResult) - end"); //$NON-NLS-1$
		}
		return runReslt;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static final String getVersion() {
		return "1.4.1.1";
	}

}
