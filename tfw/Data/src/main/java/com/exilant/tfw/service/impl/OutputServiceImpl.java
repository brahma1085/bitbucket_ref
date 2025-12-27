package com.exilant.tfw.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.exilant.tfw.dao.SchedulerDao;
import com.exilant.tfw.dao.SchedulerRunDetailsDao;
import com.exilant.tfw.dao.output.LaneResultsDao;
import com.exilant.tfw.dao.output.TaskResultDao;
import com.exilant.tfw.dao.output.TestCaseResultDao;
import com.exilant.tfw.dao.output.TestPlanResultDao;
import com.exilant.tfw.dao.output.TestScenarioResultDao;
import com.exilant.tfw.dao.output.TestStepResultDao;
import com.exilant.tfw.dao.output.TestSuiteResultDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.pojo.input.TestPlan;
import com.exilant.tfw.pojo.output.LaneResults;
import com.exilant.tfw.pojo.output.TaskResult;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.exilant.tfw.pojo.output.TestPlanResult;
import com.exilant.tfw.pojo.output.TestScenarioResult;
import com.exilant.tfw.pojo.output.TestStepResult;
import com.exilant.tfw.pojo.output.TestSuiteResult;
import com.exilant.tfw.service.OutputService;

/**
 * Data Module primary business object.
 * 
 * <p>
 * This object makes use of DAO objects, decoupling it from the details of
 * working with persistence APIs. So although this application uses Spring Data
 * for data access, a different persistence tool could be dropped in without
 * breaking this class.
 * 
 * <p>
 * The DAOs are made available to the instance of this object using Dependency
 * Injection. (The DAOs are in turn configured using Dependency Injection
 * themselves.) We use Setter Injection here, exposing JavaBean setter methods
 * for each DAO. This means there is a JavaBean property for each DAO. In the
 * present case, the properties are write-only: there are no corresponding
 * getter methods. Getter methods for configuration properties are optional:
 * Implement them only if you want to expose those properties to other business
 * objects.
 * 
 * <p>
 * There is one instance of this class in the Data module application. In Spring
 * terminology, it is a "singleton", referring to a per-Application Context
 * singleton. The factory creates a single instance; there is no need for a
 * private constructor, static factory method etc as in the traditional
 * implementation of the Singleton Design Pattern.
 * 
 * <p>
 * This is a POJO. It does not depend on any Spring APIs. It's usable outside a
 * Spring container, and can be instantiated using new in a JUnit test. However,
 * we can still apply declarative transaction management to it using Spring AOP.
 * 
 * <p>
 * This class defines a default transaction annotation for all methods.
 * 
 */

/**
 * @author mohammedfirdos
 *
 */

/**
 * This implementation class provides implementation for OutputService interface
 */
public class OutputServiceImpl implements OutputService {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(OutputServiceImpl.class);

	/** The lane results dao. */
	private LaneResultsDao laneResultsDao;

	/** The task result dao. */
	private TaskResultDao taskResultDao;

	/** The test case result dao. */
	private TestCaseResultDao testCaseResultDao;

	/** The test plan result dao. */
	private TestPlanResultDao testPlanResultDao;

	/** The test step result dao. */
	private TestStepResultDao testStepResultDao;

	/** The test suite result dao. */
	private TestSuiteResultDao testSuiteResultDao;

	/** The test scenario result dao. */
	private TestScenarioResultDao testScenarioResultDao;

	/** The scheduler run details dao. */
	private SchedulerRunDetailsDao schedulerRunDetailsDao;

	/** The scheduler dao. */
	private SchedulerDao schedulerDao;

	/**
	 * Setter Injection Methods for Spring Injection of Dependencies.
	 * 
	 * @param laneResultsDao
	 *            the new lane results dao
	 */

	/**
	 * @param laneResultsDao
	 *            the laneResultsDao to set
	 */
	public void setLaneResultsDao(LaneResultsDao laneResultsDao) {
		this.laneResultsDao = laneResultsDao;
	}

	/**
	 * Sets the task result dao.
	 * 
	 * @param taskResultDao
	 *            the taskResultDao to set
	 */
	public void setTaskResultDao(TaskResultDao taskResultDao) {
		this.taskResultDao = taskResultDao;
	}

	/**
	 * Sets the test case result dao.
	 * 
	 * @param testCaseResultDao
	 *            the testCaseResultDao to set
	 */
	public void setTestCaseResultDao(TestCaseResultDao testCaseResultDao) {
		this.testCaseResultDao = testCaseResultDao;
	}

	/**
	 * Sets the test plan result dao.
	 * 
	 * @param testPlanResultDao
	 *            the testPlanResultDao to set
	 */
	public void setTestPlanResultDao(TestPlanResultDao testPlanResultDao) {
		this.testPlanResultDao = testPlanResultDao;
	}

	/**
	 * Sets the test step result dao.
	 * 
	 * @param testStepResultDao
	 *            the testStepResultDao to set
	 */
	public void setTestStepResultDao(TestStepResultDao testStepResultDao) {
		this.testStepResultDao = testStepResultDao;
	}

	/**
	 * Sets the test suite result dao.
	 * 
	 * @param testSuiteResultDao
	 *            the testSuiteResultDao to set
	 */
	public void setTestSuiteResultDao(TestSuiteResultDao testSuiteResultDao) {
		this.testSuiteResultDao = testSuiteResultDao;
	}

	/*
	 * Service Layer Methods Implementing DataModule OutputService Interface
	 */

	/**
	 * Sets the test scenario result dao.
	 * 
	 * @param testScenarioResultDao
	 *            the new test scenario result dao
	 */
	public void setTestScenarioResultDao(TestScenarioResultDao testScenarioResultDao) {
		this.testScenarioResultDao = testScenarioResultDao;
	}

	/*
	 * 
	 */
	/**
	 * Sets the scheduler run details dao.
	 * 
	 * @param schedulerRunDetailsDao
	 *            the new scheduler run details dao
	 */
	public void setSchedulerRunDetailsDao(SchedulerRunDetailsDao schedulerRunDetailsDao) {
		this.schedulerRunDetailsDao = schedulerRunDetailsDao;
	}

	/**
	 * Sets the scheduler dao.
	 * 
	 * @param schedulerDao
	 *            the schedulerDao to set
	 */
	public void setSchedulerDao(SchedulerDao schedulerDao) {
		this.schedulerDao = schedulerDao;
	}

	/**
	 * Gets the logger.
	 * 
	 * @return the logger
	 */
	public static Logger getLogger() {
		return LOG;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getLaneResults()
	 */
	@Override
	public List<LaneResults> getLaneResults() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getLaneResults() - start"); //$NON-NLS-1$
		}

		try {
			List<LaneResults> returnList = this.laneResultsDao.getLaneResults();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getLaneResults() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getLaneResults()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getTaskResult()
	 */
	@Override
	public List<TaskResult> getTaskResult() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTaskResult() - start"); //$NON-NLS-1$
		}

		try {
			List<TaskResult> returnList = this.taskResultDao.getTaskResult();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTaskResult() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTaskResult()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getTestCaseResult()
	 */
	@Override
	public List<TestCaseResult> getTestCaseResult() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseResult() - start"); //$NON-NLS-1$
		}

		try {
			List<TestCaseResult> returnList = this.testCaseResultDao.getTestCaseResult();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseResult() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestCaseResult()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getTestPlanResult()
	 */
	@Override
	public List<TestPlanResult> getTestPlanResult() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanResult() - start"); //$NON-NLS-1$
		}

		try {
			List<TestPlanResult> returnList = this.testPlanResultDao.getTestPlanResult();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestPlanResult() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestPlanResult()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getTestStepResult()
	 */
	@Override
	public List<TestStepResult> getTestStepResult() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepResult() - start"); //$NON-NLS-1$
		}

		try {
			List<TestStepResult> returnList = this.testStepResultDao.getTestStepResult();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestStepResult() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestStepResult()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getTestSuiteResult()
	 */
	@Override
	public List<TestSuiteResult> getTestSuiteResult() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteResult() - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuiteResult> returnList = this.testSuiteResultDao.getTestSuiteResult();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteResult() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestSuiteResult()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.service.OutputService#insertLaneResults(com.exilant.tfw
	 * .pojo.output.LaneResults)
	 */
	@Override
	public long insertLaneResults(LaneResults laneResults) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertLaneResults(LaneResults) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.laneResultsDao.insertLaneResults(laneResults);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertLaneResults(LaneResults) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertLaneResults(LaneResults)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.service.OutputService#insertTaskResult(com.exilant.tfw
	 * .pojo.output.TaskResult)
	 */
	@Override
	public long insertTaskResult(TaskResult taskResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTaskResult(TaskResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.taskResultDao.insertTaskResult(taskResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTaskResult(TaskResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTaskResult(TaskResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.service.OutputService#insertTestCaseResult(com.exilant
	 * .tfw.pojo.output.TestCaseResult)
	 */
	@Override
	public long insertTestCaseResult(TestCaseResult testCaseResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseResult(TestCaseResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testCaseResultDao.insertTestCaseResult(testCaseResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestCaseResult(TestCaseResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestCaseResult(TestCaseResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestPlanResult(TestPlanResult testPlanResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanResult(TestPlanResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testPlanResultDao.insertTestPlanResult(testPlanResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestPlanResult(TestPlanResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestPlanResult(TestPlanResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestStepResult(TestStepResult testStepResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepResult(TestStepResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testStepResultDao.insertTestStepResult(testStepResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestStepResult(TestStepResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestStepResult(TestStepResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestSuiteResult(TestSuiteResult testSuiteResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteResult(TestSuiteResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testSuiteResultDao.insertTestSuiteResult(testSuiteResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestSuiteResult(TestSuiteResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestSuiteResult(TestSuiteResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestScenarioResult(TestScenarioResult testScenarioResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioResult(TestScenarioResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioResultDao.insertTestScenarioResult(testScenarioResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestScenarioResult(TestScenarioResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestScenarioResult(TestScenarioResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long updateTestScenarioResult(TestScenarioResult testScenarioResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarioResult(TestScenarioResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioResultDao.updateTestScenarioResult(testScenarioResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestScenarioResult(TestScenarioResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestScenarioResult(TestScenarioResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestPlanResultGetKey(TestPlanResult testPlanResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanResultGetKey(TestPlanResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testPlanResultDao.insertTestPlanResultGetKey(testPlanResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestPlanResultGetKey(TestPlanResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestPlanResultGetKey(TestPlanResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestSuiteResultGetKey(TestSuiteResult testSuiteResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteResultGetKey(TestSuiteResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testSuiteResultDao.insertTestSuiteResultGetKey(testSuiteResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestSuiteResultGetKey(TestSuiteResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestSuiteResultGetKey(TestSuiteResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestScenarioResultGetKey(TestScenarioResult testScenarioResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioResultGetKey(TestScenarioResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioResultDao.insertTestScenarioResultGetKey(testScenarioResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestScenarioResultGetKey(TestScenarioResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestScenarioResultGetKey(TestScenarioResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestCaseResultGetKey(TestCaseResult testCaseResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseResultGetKey(TestCaseResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testCaseResultDao.insertTestCaseResultGetKey(testCaseResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestCaseResultGetKey(TestCaseResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestCaseResultGetKey(TestCaseResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertTestStepResultGetKey(TestStepResult testStepResult) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepResultGetKey(TestStepResult) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testStepResultDao.insertTestStepResultGetKey(testStepResult);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestStepResultGetKey(TestStepResult) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestStepResultGetKey(TestStepResult)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<SchedulerRunDetails> getSchedulerRunDetails(int testAppId,String type) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerRunDetails(int) - start"); //$NON-NLS-1$
		}

		try {
			//return this.schedulerRunDetailsDao.getSchedulerRunDetailsById(testAppId,type);
			List<SchedulerRunDetails> returnList = this.schedulerRunDetailsDao.getSchedulerRunDetailsById(testAppId,type);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerRunDetails(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("Error Occured while returning fetching the Scheduler Run Details " + e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getAllSchedulerRunDetails()
	 */
	@Override
	public List<SchedulerRunDetails> getAllSchedulerRunDetails() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllSchedulerRunDetails() - start"); //$NON-NLS-1$
		}

		try {
			List<SchedulerRunDetails> returnList = this.schedulerRunDetailsDao.getAllSchedulerRunDetails();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllSchedulerRunDetails() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("Error Occured while returning fetching the Scheduler Run Details " + e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.service.OutputService#getSchedulerListByAppId(int)
	 */
	@Override
	public List<TestPlan> getSchedulerListByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerListByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestPlan> returnList = this.schedulerDao.getSchedulerListByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerListByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("Error Occured while returning fetching the Scheduler Run Details " + e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}
}
