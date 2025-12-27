package com.sssoft.isatt.data.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.sssoft.isatt.data.dao.AppFunctionalityDao;
import com.sssoft.isatt.data.dao.input.CaseStepMappingDao;
import com.sssoft.isatt.data.dao.input.ConditionGroupDao;
import com.sssoft.isatt.data.dao.input.ConditionsDao;
import com.sssoft.isatt.data.dao.input.IdentifierTypeDao;
import com.sssoft.isatt.data.dao.input.ObjectGroupDao;
import com.sssoft.isatt.data.dao.input.ObjectTypeDao;
import com.sssoft.isatt.data.dao.input.ObjectsDao;
import com.sssoft.isatt.data.dao.input.ParamDao;
import com.sssoft.isatt.data.dao.input.ParamGroupDao;
import com.sssoft.isatt.data.dao.input.PlanSuiteMappingDao;
import com.sssoft.isatt.data.dao.input.ReplacementStringsDao;
import com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao;
import com.sssoft.isatt.data.dao.input.SuiteScenarioMappingDao;
import com.sssoft.isatt.data.dao.input.TaskDao;
import com.sssoft.isatt.data.dao.input.TestCaseDao;
import com.sssoft.isatt.data.dao.input.TestConditionDataDao;
import com.sssoft.isatt.data.dao.input.TestDataDao;
import com.sssoft.isatt.data.dao.input.TestParamDataDao;
import com.sssoft.isatt.data.dao.input.TestPlanDao;
import com.sssoft.isatt.data.dao.input.TestScenarioDao;
import com.sssoft.isatt.data.dao.input.TestStepDao;
import com.sssoft.isatt.data.dao.input.TestSuiteDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.def.TestCaseUI;
import com.sssoft.isatt.data.pojo.def.TestScenarioUI;
import com.sssoft.isatt.data.pojo.def.TestSuiteUI;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;
import com.sssoft.isatt.data.pojo.input.ConditionGroup;
import com.sssoft.isatt.data.pojo.input.Conditions;
import com.sssoft.isatt.data.pojo.input.IdentifierType;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;
import com.sssoft.isatt.data.pojo.input.ObjectType;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.input.ParamGroup;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.ReplacementStrings;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;
import com.sssoft.isatt.data.pojo.input.Task;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestConditionData;
import com.sssoft.isatt.data.pojo.input.TestData;
import com.sssoft.isatt.data.pojo.input.TestParamData;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

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
 * This implementation class provides implementation for InputService interface
 */
public class InputServiceImpl implements InputService {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(InputServiceImpl.class);

	/** The condition group dao. */
	private ConditionGroupDao conditionGroupDao;

	/** The conditions dao. */
	private ConditionsDao conditionsDao;

	/** The identifier type dao. */
	private IdentifierTypeDao identifierTypeDao;

	/** The object group dao. */
	private ObjectGroupDao objectGroupDao;

	/** The objects dao. */
	private ObjectsDao objectsDao;

	/** The object type dao. */
	private ObjectTypeDao objectTypeDao;

	/** The param dao. */
	private ParamDao paramDao;

	/** The param group dao. */
	private ParamGroupDao paramGroupDao;

	/** The replacement_ strings dao. */
	private ReplacementStringsDao replacement_StringsDao;

	/** The task dao. */
	private TaskDao taskDao;

	/** The test case dao. */
	private TestCaseDao testCaseDao;

	/** The test condition data dao. */
	private TestConditionDataDao testConditionDataDao;

	/** The test data dao. */
	private TestDataDao testDataDao;

	/** The test param data dao. */
	private TestParamDataDao testParamDataDao;

	/** The test plan dao. */
	private TestPlanDao testPlanDao;

	/** The test scenario dao. */
	private TestScenarioDao testScenarioDao;

	/** The test step dao. */
	private TestStepDao testStepDao;

	/** The test suite dao. */
	private TestSuiteDao testSuiteDao;

	/** The app function dao. */
	private AppFunctionalityDao appFunctionDao;

	/** The suite scenario mapping dao. */
	private SuiteScenarioMappingDao suiteScenarioMappingDao;

	/** The scenario case mapping dao. */
	private ScenarioCaseMappingDao scenarioCaseMappingDao;

	/** The case step mapping dao. */
	private CaseStepMappingDao caseStepMappingDao;

	/** The plan suite mapping dao. */
	private PlanSuiteMappingDao planSuiteMappingDao;

	/**
	 * @param conditionGroupDao
	 *            the conditionGroupDao to set
	 */
	public void setConditionGroupDao(ConditionGroupDao conditionGroupDao) {
		this.conditionGroupDao = conditionGroupDao;
	}

	/**
	 * Sets the conditions dao.
	 * 
	 * @param conditionsDao
	 *            the conditionsDao to set
	 */
	public void setConditionsDao(ConditionsDao conditionsDao) {
		this.conditionsDao = conditionsDao;
	}

	/**
	 * Sets the identifier type dao.
	 * 
	 * @param identifierTypeDao
	 *            the identifierTypeDao to set
	 */
	public void setIdentifierTypeDao(IdentifierTypeDao identifierTypeDao) {
		this.identifierTypeDao = identifierTypeDao;
	}

	/**
	 * Sets the object group dao.
	 * 
	 * @param objectGroupDao
	 *            the objectGroupDao to set
	 */
	public void setObjectGroupDao(ObjectGroupDao objectGroupDao) {
		this.objectGroupDao = objectGroupDao;
	}

	/**
	 * Sets the objects dao.
	 * 
	 * @param objectsDao
	 *            the objectsDao to set
	 */
	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}

	/**
	 * Sets the object type dao.
	 * 
	 * @param objectTypeDao
	 *            the objectTypeDao to set
	 */
	public void setObjectTypeDao(ObjectTypeDao objectTypeDao) {
		this.objectTypeDao = objectTypeDao;
	}

	/**
	 * Sets the param dao.
	 * 
	 * @param paramDao
	 *            the paramDao to set
	 */
	public void setParamDao(ParamDao paramDao) {
		this.paramDao = paramDao;
	}

	/**
	 * Sets the param group dao.
	 * 
	 * @param paramGroupDao
	 *            the paramGroupDao to set
	 */
	public void setParamGroupDao(ParamGroupDao paramGroupDao) {
		this.paramGroupDao = paramGroupDao;
	}

	/**
	 * Sets the replacement_ strings dao.
	 * 
	 * @param replacement_StringsDao
	 *            the replacement_StringsDao to set
	 */
	public void setReplacement_StringsDao(ReplacementStringsDao replacement_StringsDao) {
		this.replacement_StringsDao = replacement_StringsDao;
	}

	/**
	 * Sets the task dao.
	 * 
	 * @param taskDao
	 *            the taskDao to set
	 */
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	/**
	 * Sets the test case dao.
	 * 
	 * @param testCaseDao
	 *            the testCaseDao to set
	 */
	public void setTestCaseDao(TestCaseDao testCaseDao) {
		this.testCaseDao = testCaseDao;
	}

	/**
	 * Sets the test condition data dao.
	 * 
	 * @param testConditionDataDao
	 *            the testConditionDataDao to set
	 */
	public void setTestConditionDataDao(TestConditionDataDao testConditionDataDao) {
		this.testConditionDataDao = testConditionDataDao;
	}

	/**
	 * Sets the test data dao.
	 * 
	 * @param testDataDao
	 *            the testDataDao to set
	 */
	public void setTestDataDao(TestDataDao testDataDao) {
		this.testDataDao = testDataDao;
	}

	/**
	 * Sets the test param data dao.
	 * 
	 * @param testParamDataDao
	 *            the testParamDataDao to set
	 */
	public void setTestParamDataDao(TestParamDataDao testParamDataDao) {
		this.testParamDataDao = testParamDataDao;
	}

	/**
	 * Sets the test plan dao.
	 * 
	 * @param testPlanDao
	 *            the testPlanDao to set
	 */
	public void setTestPlanDao(TestPlanDao testPlanDao) {
		this.testPlanDao = testPlanDao;
	}

	/**
	 * Sets the test scenario dao.
	 * 
	 * @param testScenarioDao
	 *            the testScenarioDao to set
	 */
	public void setTestScenarioDao(TestScenarioDao testScenarioDao) {
		this.testScenarioDao = testScenarioDao;
	}

	/**
	 * Sets the test step dao.
	 * 
	 * @param testStepDao
	 *            the testStepDao to set
	 */
	public void setTestStepDao(TestStepDao testStepDao) {
		this.testStepDao = testStepDao;
	}

	/**
	 * Sets the test suite dao.
	 * 
	 * @param testSuiteDao
	 *            the testSuiteDao to set
	 */
	public void setTestSuiteDao(TestSuiteDao testSuiteDao) {
		this.testSuiteDao = testSuiteDao;
	}

	/*
	 * Service Layer Methods Implementing DataModule MainService Interface
	 */

	/**
	 * Sets the suite scenario mapping dao.
	 * 
	 * @param suiteScenarioMappingDao
	 *            the suiteScenarioMappingDao to set
	 */
	public void setSuiteScenarioMappingDao(SuiteScenarioMappingDao suiteScenarioMappingDao) {
		this.suiteScenarioMappingDao = suiteScenarioMappingDao;
	}

	/**
	 * Sets the scenario case mapping dao.
	 * 
	 * @param scenarioCaseMappingDao
	 *            the new scenario case mapping dao
	 */
	public void setScenarioCaseMappingDao(ScenarioCaseMappingDao scenarioCaseMappingDao) {
		this.scenarioCaseMappingDao = scenarioCaseMappingDao;
	}

	/**
	 * Sets the case step mapping dao.
	 * 
	 * @param caseStepMappingDao
	 *            the caseStepMappingDao to set
	 */
	public void setCaseStepMappingDao(CaseStepMappingDao caseStepMappingDao) {
		this.caseStepMappingDao = caseStepMappingDao;
	}

	/**
	 * Sets the plan suite mapping dao.
	 * 
	 * @param planSuiteMappingDao
	 *            the planSuiteMappingDao to set
	 */
	public void setPlanSuiteMappingDao(PlanSuiteMappingDao planSuiteMappingDao) {
		this.planSuiteMappingDao = planSuiteMappingDao;
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
	 * @see com.sssoft.isatt.data.service.InputService#getConditionGroup()
	 */
	@Override
	public List<ConditionGroup> getConditionGroup() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroup() - start"); //$NON-NLS-1$
		}

		try {
			List<ConditionGroup> returnList = this.conditionGroupDao.getConditionGroup();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getConditionGroup() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getConditionGroup()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getConditions()
	 */
	@Override
	public List<Conditions> getConditions() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditions() - start"); //$NON-NLS-1$
		}

		try {
			List<Conditions> returnList = this.conditionsDao.getConditions();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getConditions() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getConditions()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getIdentifierType()
	 */
	@Override
	public List<IdentifierType> getIdentifierType() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierType() - start"); //$NON-NLS-1$
		}

		try {
			List<IdentifierType> returnList = this.identifierTypeDao.getIdentifierType();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getIdentifierType() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getIdentifierType()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjectGroup()
	 */
	@Override
	public List<ObjectGroup> getObjectGroup() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroup() - start"); //$NON-NLS-1$
		}

		try {
			List<ObjectGroup> returnList = this.objectGroupDao.getObjectGroup();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectGroup() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getObjectGroup()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjects()
	 */
	@Override
	public List<Objects> getObjects() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjects() - start"); //$NON-NLS-1$
		}

		try {
			List<Objects> returnList = this.objectsDao.getObjects();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjects() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getObjects()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjectType()
	 */
	@Override
	public List<ObjectType> getObjectType() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectType() - start"); //$NON-NLS-1$
		}

		try {
			List<ObjectType> returnList = this.objectTypeDao.getObjectType();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectType() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getObjectType()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParam()
	 */
	@Override
	public List<Param> getParam() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParam() - start"); //$NON-NLS-1$
		}

		try {
			List<Param> returnList = this.paramDao.getParam();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParam() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParam()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamGroup()
	 */
	@Override
	public List<ParamGroup> getParamGroup() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroup() - start"); //$NON-NLS-1$
		}

		try {
			List<ParamGroup> returnList = this.paramGroupDao.getParamGroup();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamGroup() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParamGroup()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getReplacementStrings()
	 */
	@Override
	public List<ReplacementStrings> getReplacementStrings() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStrings() - start"); //$NON-NLS-1$
		}

		try {
			List<ReplacementStrings> returnList = this.replacement_StringsDao.getReplacementStrings();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getReplacementStrings() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getReplacementStrings()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTask()
	 */
	@Override
	public List<Task> getTask() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTask() - start"); //$NON-NLS-1$
		}

		try {
			List<Task> returnList = this.taskDao.getTask();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTask() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTask()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestCase(com.sssoft.isatt.data.pojo
	 * .input.TestCase)
	 */
	@Override
	public long insertTestCase(TestCase testCases) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCase(TestCase) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testCaseDao.insertTestCase(testCases);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestCase(TestCase) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestCase(TestCase)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestCase()
	 */
	@Override
	public List<TestCase> getTestCase() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCase() - start"); //$NON-NLS-1$
		}

		try {
			List<TestCase> returnList = this.testCaseDao.getTestCase();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCase() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestCase()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestConditionData()
	 */
	@Override
	public List<TestConditionData> getTestConditionData() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestConditionData() - start"); //$NON-NLS-1$
		}

		try {
			List<TestConditionData> returnList = this.testConditionDataDao.getTestConditionData();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestConditionData() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestConditionData()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestData()
	 */
	@Override
	public List<TestData> getTestData() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestData() - start"); //$NON-NLS-1$
		}

		try {
			List<TestData> returnList = this.testDataDao.getTestData();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestData() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestData()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestParamData()
	 */
	@Override
	public List<TestParamData> getTestParamData() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamData() - start"); //$NON-NLS-1$
		}

		try {
			List<TestParamData> returnList = this.testParamDataDao.getTestParamData();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestParamData() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestParamData()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestPlan()
	 */
	@Override
	public List<TestPlan> getTestPlan() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlan() - start"); //$NON-NLS-1$
		}

		try {
			List<TestPlan> returnList = this.testPlanDao.getTestPlan();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestPlan() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestPlan()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestScenario(com.exilant.tfw
	 * .pojo.input.TestScenario)
	 */
	@Override
	public long insertTestScenario(TestScenario testScenario) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenario(TestScenario) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioDao.insertTestScenario(testScenario);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestScenario(TestScenario) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestScenario(TestScenario)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestScenario()
	 */
	@Override
	public List<TestScenario> getTestScenario() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenario() - start"); //$NON-NLS-1$
		}

		try {
			List<TestScenario> returnList = this.testScenarioDao.getTestScenario();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestScenario() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestScenario()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestStep()
	 */
	@Override
	public List<TestStep> getTestStep() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStep() - start"); //$NON-NLS-1$
		}

		try {
			List<TestStep> returnList = this.testStepDao.getTestStep();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestStep() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestStep()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestSuite()
	 */
	@Override
	public List<TestSuite> getTestSuite() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuite() - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuite> returnList = this.testSuiteDao.getTestSuite();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuite() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestSuite()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestPlan(com.sssoft.isatt.data.pojo
	 * .input.TestPlan)
	 */
	@Override
	public long insertTestPlan(TestPlan testPlan) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlan(TestPlan) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testPlanDao.insertTestPlan(testPlan);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestPlan(TestPlan) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestPlan(TestPlan)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestPlan(com.sssoft.isatt.data.pojo
	 * .input.TestPlan)
	 */
	@Override
	public long updateTestPlan(TestPlan testPlan) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestPlan(TestPlan) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testPlanDao.updateTestPlan(testPlan);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestPlan(TestPlan) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestPlan(TestPlan)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestScenario(com.exilant.tfw
	 * .pojo.input.TestScenario)
	 */
	@Override
	public long updateTestScenario(TestScenario testScenario) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenario(TestScenario) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioDao.updateTestScenario(testScenario);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestScenario(TestScenario) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestScenario(TestScenario)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestSuiteGetKey(com.exilant
	 * .tfw.pojo.input.TestSuite)
	 */
	@Override
	public long insertTestSuiteGetKey(TestSuite testSuite) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteGetKey(TestSuite) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testSuiteDao.insertTestSuiteGetKey(testSuite);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestSuiteGetKey(TestSuite) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestSuiteGetKey(TestSuite)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getTestSuiteFilterBytestScenarioId
	 * (int)
	 */
	@Override
	public List<String> getTestSuiteFilterBytestScenarioId(int testScenarioId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteFilterBytestScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<String> returnList = this.testScenarioDao.getTestSuiteFilterBytestScenarioId(testScenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteFilterBytestScenarioId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestSuiteFilterBytestScenarioId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestPlanById(int)
	 */
	public TestPlan getTestPlanById(int testPlanId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestPlan returnTestPlan = this.testPlanDao.getTestPlanById(testPlanId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestPlanById(int) - end"); //$NON-NLS-1$
			}
			return returnTestPlan;
		} catch (DataAccessException e) {
			LOG.error("getTestPlanById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	public TestPlan getTestPlanByName(String testPlanName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByName(String) - start"); //$NON-NLS-1$
		}

		try {
			TestPlan returnTestPlan = this.testPlanDao.getTestPlanByName(testPlanName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestPlanByName(String) - end"); //$NON-NLS-1$
			}
			return returnTestPlan;
		} catch (DataAccessException e) {
			LOG.error("getTestPlanByName(String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestScenarioById(int)
	 */
	public TestScenario getTestScenarioById(int testScenarioId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestScenario returnTestScenario = this.testScenarioDao.getTestScenarioById(testScenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestScenarioById(int) - end"); //$NON-NLS-1$
			}
			return returnTestScenario;
		} catch (DataAccessException e) {
			LOG.error("getTestScenarioById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	public TestScenario getTestScenarioByName(String testScenarioName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestScenario returnTestScenario = this.testScenarioDao.getTestScenarioByName(testScenarioName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestScenarioById(int) - end"); //$NON-NLS-1$
			}
			return returnTestScenario;
		} catch (DataAccessException e) {
			LOG.error("getTestScenarioById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertPlanSuiteMapping(com.exilant
	 * .tfw.pojo.input.PlanSuiteMapping)
	 */
	@Override
	public long insertPlanSuiteMapping(PlanSuiteMapping planSuiteMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertPlanSuiteMapping(PlanSuiteMapping) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.planSuiteMappingDao.insertPlanSuiteMappingDaoGetKey(planSuiteMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertPlanSuiteMapping(PlanSuiteMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertPlanSuiteMapping(PlanSuiteMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	public long deletePlanSuiteMappingById(final int planId) throws ServiceException{
		if (LOG.isDebugEnabled()) {
			LOG.debug("deletePlanSuiteMappingById(planId,suiteId) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.planSuiteMappingDao.deletePlanSuiteMappingById( planId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deletePlanSuiteMappingById(planId,suiteId) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("deletePlanSuiteMappingById(planId,suiteId)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	
	public long deletePlanSuiteMappingBySuiteId(final int suiteId) throws ServiceException{
		if (LOG.isDebugEnabled()) {
			LOG.debug("deletePlanSuiteMappingBySuiteId(suiteId) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.planSuiteMappingDao.deletePlanSuiteMappingBySuiteId(suiteId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deletePlanSuiteMappingBySuiteId(suiteId) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("deletePlanSuiteMappingBySuiteId(suiteId)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertSuiteScenarioMapping(com.exilant
	 * .tfw.pojo.input.SuiteScenarioMapping)
	 */
	@Override
	public long insertSuiteScenarioMapping(SuiteScenarioMapping suiteScenarioMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMapping(SuiteScenarioMapping) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.suiteScenarioMappingDao.insertSuiteScenarioMappingDao(suiteScenarioMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSuiteScenarioMapping(SuiteScenarioMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertSuiteScenarioMapping(SuiteScenarioMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertScenarioCaseMapping(com.exilant
	 * .tfw.pojo.input.ScenarioCaseMapping)
	 */
	@Override
	public long insertScenarioCaseMapping(ScenarioCaseMapping scenarioCaseMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMapping(ScenarioCaseMapping) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.scenarioCaseMappingDao.insertScenarioCaseMappingDao(scenarioCaseMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertScenarioCaseMapping(ScenarioCaseMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertScenarioCaseMapping(ScenarioCaseMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertCaseStepMapping(com.exilant
	 * .tfw.pojo.input.CaseStepMapping)
	 */
	@Override
	public long insertCaseStepMapping(CaseStepMapping caseStepMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMapping(CaseStepMapping) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.caseStepMappingDao.insertCaseStepMappingDao(caseStepMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertCaseStepMapping(CaseStepMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertCaseStepMapping(CaseStepMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertParam(com.sssoft.isatt.data.pojo
	 * .input.Param)
	 */
	@Override
	public long insertParam(Param param) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParam(Param) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.paramDao.insertParam(param);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertParam(Param) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertParam(Param)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertAppFunctionalityGetKey(com
	 * .exilant.tfw.pojo.AppFunctionality)
	 */
	@Override
	public int insertAppFunctionalityGetKey(AppFunctionality appFunctionality) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionalityGetKey(AppFunctionality) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.appFunctionDao.insertAppFunctionalityGetKey(appFunctionality);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertAppFunctionalityGetKey(AppFunctionality) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertAppFunctionalityGetKey(AppFunctionality)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getAllTestPlans()
	 */
	@Override
	public List<TestPlan> getAllTestPlans() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestPlans() - start"); //$NON-NLS-1$
		}

		try {
			List<TestPlan> returnList = this.testPlanDao.getAllTestPlans();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestPlans() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException dae) {
			LOG.error("getAllTestPlans()", dae); //$NON-NLS-1$

			throw new ServiceException(dae.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getAllTestSuite()
	 */
	@Override
	public List<TestSuite> getAllTestSuite() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestSuite() - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuite> returnList = this.testSuiteDao.getAllTestSuites();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestSuite() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException dae) {
			LOG.error("getAllTestSuite()", dae); //$NON-NLS-1$

			throw new ServiceException(dae.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertParamGroupGetKey(com.exilant
	 * .tfw.pojo.input.ParamGroup)
	 */
	@Override
	public int insertParamGroupGetKey(ParamGroup paramGroup) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroupGetKey(ParamGroup) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.paramGroupDao.insertParamGroupGetKey(paramGroup);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertParamGroupGetKey(ParamGroup) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertParamGroupGetKey(ParamGroup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestParamDataGetKey(com.exilant
	 * .tfw.pojo.input.TestParamData)
	 */
	public int insertTestParamDataGetKey(TestParamData testParamData) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamDataGetKey(TestParamData) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.testParamDataDao.insertTestParamDataGetKey(testParamData);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestParamDataGetKey(TestParamData) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertTestParamDataGetKey(TestParamData)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertParamGetKey(com.exilant.tfw
	 * .pojo.input.Param)
	 */
	@Override
	public int insertParamGetKey(Param param) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGetKey(Param) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.paramDao.insertParamGetKey(param);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertParamGetKey(Param) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertParamGetKey(Param)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getTestPlanNameFilterByAppId(int)
	 */
	@Override
	public List<TestPlan> getTestPlanNameFilterByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanNameFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestPlan> returnList = this.testPlanDao.getTestPlanNameFilterByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestPlanNameFilterByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestPlanNameFilterByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getTestDataDescriptionFilterByAppId
	 * (int)
	 */
	@Override
	public List<TestData> getTestDataDescriptionFilterByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDescriptionFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestData> returnList = this.testDataDao.getTestDataDescriptionFilterByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestDataDescriptionFilterByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestDataDescriptionFilterByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertObjectGroupGetKey(com.exilant
	 * .tfw.pojo.input.ObjectGroup)
	 */
	@Override
	public int insertObjectGroupGetKey(ObjectGroup objGrp) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroupGetKey(ObjectGroup) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.objectGroupDao.insertObjectGroupGetKey(objGrp);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertObjectGroupGetKey(ObjectGroup) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertObjectGroupGetKey(ObjectGroup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestStep(com.sssoft.isatt.data.pojo
	 * .input.TestStep)
	 */
	@Override
	public long insertTestStep(TestStep testStep) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStep(TestStep) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testStepDao.insertTestStep(testStep);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestStep(TestStep) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestStep(TestStep)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestStepGetKey(com.exilant
	 * .tfw.pojo.input.TestStep)
	 */
	public int insertTestStepGetKey(TestStep testStep) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepGetKey(TestStep) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.testStepDao.insertTestStepGetKey(testStep);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestStepGetKey(TestStep) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertTestStepGetKey(TestStep)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestDataGetKey(com.exilant
	 * .tfw.pojo.input.TestData)
	 */
	@Override
	public int insertTestDataGetKey(final TestData testData) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestDataGetKey(TestData) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.testDataDao.insertTestDataGetKey(testData);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestDataGetKey(TestData) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertTestDataGetKey(TestData)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestParamData(com.exilant.
	 * tfw.pojo.input.TestParamData)
	 */
	@Override
	public long insertTestParamData(TestParamData testParamData) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamData(TestParamData) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testParamDataDao.insertTestParamData(testParamData);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestParamData(TestParamData) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestParamData(TestParamData)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestDataById(int)
	 */
	@Override
	public TestData getTestDataById(int testDataId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestData returnTestData = this.testDataDao.getTestDataById(testDataId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestDataById(int) - end"); //$NON-NLS-1$
			}
			return returnTestData;
		} catch (DataAccessException e) {
			LOG.error("getTestDataById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestData(com.sssoft.isatt.data.pojo
	 * .input.TestData)
	 */
	@Override
	public long updateTestData(final TestData testData) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestData(TestData) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testDataDao.updateTestData(testData);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestData(TestData) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestData(TestData)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getTestParamDataByTestDataId(int)
	 */
	@Override
	public List<TestParamData> getTestParamDataByTestDataId(int testDataId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataByTestDataId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestParamData> returnList = this.testParamDataDao.getTestParamDataByTestDataId(testDataId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestParamDataByTestDataId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestParamDataByTestDataId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getAllTestSuites()
	 */
	@Override
	public List<TestSuite> getAllTestSuites() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestSuites() - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuite> returnList = this.testSuiteDao.getAllTestSuites();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestSuites() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllTestSuites()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestSuiteById(int)
	 */
	@Override
	public TestSuite getTestSuiteById(int testSuiteId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestSuite returnTestSuite = this.testSuiteDao.getTestSuiteById(testSuiteId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteById(int) - end"); //$NON-NLS-1$
			}
			return returnTestSuite;
		} catch (DataAccessException e) {
			LOG.error("getTestSuiteById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public TestSuite getTestSuiteByName(String testSuiteName) throws ServiceException{
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteIdByName(int) - start"); //$NON-NLS-1$
		}

		try {
			TestSuite returnTestSuite = this.testSuiteDao.getTestSuiteByName(testSuiteName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteById(int) - end"); //$NON-NLS-1$
			}
			return returnTestSuite;
		} catch (DataAccessException e) {
			LOG.error("getTestSuiteById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertObjectTypeGetKey(com.exilant
	 * .tfw.pojo.input.ObjectType)
	 */
	@Override
	public int insertObjectTypeGetKey(ObjectType objType) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectTypeGetKey(ObjectType) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.objectTypeDao.insertObjectTypeGetKey(objType);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertObjectTypeGetKey(ObjectType) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertObjectTypeGetKey(ObjectType)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getAllObjGrps()
	 */
	@Override
	public List<ObjectGroup> getAllObjGrps() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllObjGrps() - start"); //$NON-NLS-1$
		}

		try {
			List<ObjectGroup> returnList = this.objectGroupDao.getAllObjectGroups();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllObjGrps() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllObjGrps()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getAllObjTypes()
	 */
	@Override
	public List<ObjectType> getAllObjTypes() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllObjTypes() - start"); //$NON-NLS-1$
		}

		try {
			List<ObjectType> returnList = this.objectTypeDao.getAllObjectTypes();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllObjTypes() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllObjTypes()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getAllIdentifierTypes()
	 */
	@Override
	public List<IdentifierType> getAllIdentifierTypes() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllIdentifierTypes() - start"); //$NON-NLS-1$
		}

		try {
			List<IdentifierType> returnList = this.identifierTypeDao.getAllIdentifierTypes();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllIdentifierTypes() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllIdentifierTypes()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertObjectsGetKey(com.exilant.
	 * tfw.pojo.input.Objects)
	 */
	@Override
	public int insertObjectsGetKey(Objects obj) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectsGetKey(Objects) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.objectsDao.insertObjectsGetKey(obj);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertObjectsGetKey(Objects) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertObjectsGetKey(Objects)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjectGroupById(int)
	 */
	@Override
	public ObjectGroup getObjectGroupById(int objGrpId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupById(int) - start"); //$NON-NLS-1$
		}

		try {
			ObjectGroup returnObjectGroup = this.objectGroupDao.getObjectGroupById(objGrpId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectGroupById(int) - end"); //$NON-NLS-1$
			}
			return returnObjectGroup;
		} catch (DataAccessException e) {
			LOG.error("getObjectGroupById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestPlanData(com.exilant.tfw
	 * .pojo.input.TestPlan)
	 */
	@Override
	public long updateTestPlanData(TestPlan testPlan) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestPlanData(TestPlan) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testPlanDao.updateTestPlanData(testPlan);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestPlanData(TestPlan) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestPlanData(TestPlan)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestSuiteData(com.exilant.
	 * tfw.pojo.input.TestSuite)
	 */
	@Override
	public long updateTestSuiteData(TestSuite testSuite) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSuiteData(TestSuite) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testSuiteDao.updateTestSuiteData(testSuite);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestSuiteData(TestSuite) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestSuiteData(TestSuite)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateObjectGroupData(com.exilant
	 * .tfw.pojo.input.ObjectGroup)
	 */
	@Override
	public long updateObjectGroupData(ObjectGroup objGrp) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjectGroupData(ObjectGroup) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.objectGroupDao.updateObjectGroupData(objGrp);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjectGroupData(ObjectGroup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateObjectGroupData(ObjectGroup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestScenarioData(com.exilant
	 * .tfw.pojo.input.TestScenario)
	 */
	@Override
	public long updateTestScenarioData(TestScenario testScenario) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarioData(TestScenario) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioDao.updateTestScenarioData(testScenario);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestScenarioData(TestScenario) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestScenarioData(TestScenario)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertCaseStepMappingGetKey(com.
	 * exilant.tfw.pojo.input.CaseStepMapping)
	 */
	@Override
	public int insertCaseStepMappingGetKey(CaseStepMapping caseStepMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMappingGetKey(CaseStepMapping) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.caseStepMappingDao.insertCaseStepMappingGetKey(caseStepMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertCaseStepMappingGetKey(CaseStepMapping) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertCaseStepMappingGetKey(CaseStepMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getAllFunctionNamesByTestCaseId(int)
	 */
	@Override
	public List<AppFunctionality> getAllFunctionNamesByTestCaseId(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFunctionNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.testCaseDao.getAllFunctionNamesByTestCaseId(testCaseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllFunctionNamesByTestCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllFunctionNamesByTestCaseId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getAllFeatureNamesByTestCaseId(int)
	 */
	@Override
	public List<AppFeature> getAllFeatureNamesByTestCaseId(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFeatureNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFeature> returnList = this.testCaseDao.getAllFeatureNamesByTestCaseId(testCaseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllFeatureNamesByTestCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllFeatureNamesByTestCaseId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamGroupNamesByAppId(int)
	 */
	@Override
	public List<ParamGroup> getParamGroupNamesByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupNamesByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ParamGroup> returnList = this.paramGroupDao.getParamGroupNamesByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamGroupNamesByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParamGroupNamesByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getConditionGroupNamesByAppId(int)
	 */
	@Override
	public List<ConditionGroup> getConditionGroupNamesByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupNamesByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ConditionGroup> returnList = this.conditionGroupDao.getConditionGroupNamesByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getConditionGroupNamesByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getConditionGroupNamesByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getAllTestCasesByTestScenarioId(int)
	 */
	@Override
	public List<TestCase> getAllTestCasesByTestScenarioId(int testScenarioId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestCasesByTestScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestCase> returnList = this.testCaseDao.getAllTestCasesByTestScenarioId(testScenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestCasesByTestScenarioId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllTestCasesByTestScenarioId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getAllTestStepsByTestCaseId(int)
	 */
	@Override
	public List<TestStep> getAllTestStepsByTestCaseId(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestStepsByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestStep> returnList = this.testStepDao.getAllTestStepsByTestCaseId(testCaseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestStepsByTestCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllTestStepsByTestCaseId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestScenarioByAppId(int)
	 */
	@Override
	public List<TestScenario> getTestScenarioByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestScenario> returnList = this.testScenarioDao.getTestScenarioByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestScenarioByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestScenarioByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestSuiteByAppId(int)
	 */
	@Override
	public List<TestSuite> getTestSuiteByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuite> returnList = this.testSuiteDao.getTestSuiteByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestSuiteByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertScenarioAndSuiteScenarioGetKey
	 * (com.sssoft.isatt.data.pojo.input.TestScenario, java.lang.String)
	 */
	public int insertScenarioAndSuiteScenarioGetKey(TestScenario testScenario, String suiteName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioAndSuiteScenarioGetKey(TestScenario, String) - start"); //$NON-NLS-1$
		}

		try {
			int scenarioId = this.testScenarioDao.insertTestScenarioGetKey(testScenario);
			if (scenarioId != 0) {
				int suiteId = this.testSuiteDao.getTestSuiteIdByNameByAppId(testScenario.getAppID(), suiteName);
				if (suiteId != 0) {
					SuiteScenarioMapping ssMapping = new SuiteScenarioMapping();
					ssMapping.setTestScenarioID(scenarioId);
					ssMapping.setTestSuiteID(suiteId);
					ssMapping.setCreatedBy(DataConstants.DEFAULT_USER);
					ssMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					ssMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
					ssMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					int returnint = this.suiteScenarioMappingDao.insertSuiteScenarioMappingGetKey(ssMapping);
					if (LOG.isDebugEnabled()) {
						LOG.debug("insertScenarioAndSuiteScenarioGetKey(TestScenario, String) - end"); //$NON-NLS-1$
					}
					return returnint;
				} else {
					throw new ServiceException("Respective suite id not available");
				}
			} else {
				throw new ServiceException("Scenario is not created");
			}
		} catch (DataAccessException e) {
			LOG.error("insertScenarioAndSuiteScenarioGetKey(TestScenario, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertCaseAndScenarioCaseGetKey(
	 * com.sssoft.isatt.data.pojo.input.TestCase, java.lang.String)
	 */
	@Override
	public int insertCaseAndScenarioCaseGetKey(TestCase testCase, String scenarioName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseAndScenarioCaseGetKey(TestCase, String) - start"); //$NON-NLS-1$
		}

		try {
			int caseId = this.testCaseDao.insertTestCaseGetKey(testCase);
			if (caseId != 0) {
				int scenarioId = this.testScenarioDao.getTestScenarioIdByName(scenarioName);
				if (scenarioId != 0) {
					ScenarioCaseMapping scMapping = new ScenarioCaseMapping();
					scMapping.setTestCaseID(caseId);
					scMapping.setTestScenarioID(scenarioId);
					scMapping.setCreatedBy(DataConstants.DEFAULT_USER);
					scMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					scMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
					scMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					int returnint = this.scenarioCaseMappingDao.insertScenarioCaseMappingGetKey(scMapping);
					if (LOG.isDebugEnabled()) {
						LOG.debug("insertCaseAndScenarioCaseGetKey(TestCase, String) - end"); //$NON-NLS-1$
					}
					return returnint;
				} else {
					throw new ServiceException("Respective suite id not available");
				}
			} else {
				throw new ServiceException("Scenario is not created");
			}
		} catch (DataAccessException e) {
			LOG.error("insertCaseAndScenarioCaseGetKey(TestCase, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestDatabyAppId(int)
	 */
	@Override
	public List<TestData> getTestDatabyAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDatabyAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestData> returnList = this.testDataDao.getTestDatabyAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestDatabyAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestDatabyAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamByAppId(int)
	 */
	@Override
	public List<Param> getParamByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Param> returnList = this.paramDao.getParamByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParamByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamGroupByAppId(int)
	 */
	@Override
	public List<ParamGroup> getParamGroupByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ParamGroup> returnList = this.paramGroupDao.getParamGroupByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamGroupByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParamGroupByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestCaseGetKey(com.exilant
	 * .tfw.pojo.input.TestCase)
	 */
	@Override
	public int insertTestCaseGetKey(TestCase testCase) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseGetKey(TestCase) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.testCaseDao.insertTestCaseGetKey(testCase);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestCaseGetKey(TestCase) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertTestCaseGetKey(TestCase)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertScenarioCaseMappingGetKey(
	 * com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping)
	 */
	@Override
	public long insertScenarioCaseMappingGetKey(ScenarioCaseMapping scenarioCaseMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingGetKey(ScenarioCaseMapping) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.scenarioCaseMappingDao.insertScenarioCaseMappingGetKey(scenarioCaseMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertScenarioCaseMappingGetKey(ScenarioCaseMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertScenarioCaseMappingGetKey(ScenarioCaseMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestScenarioGetKey(com.exilant
	 * .tfw.pojo.input.TestScenario)
	 */
	@Override
	public long insertTestScenarioGetKey(TestScenario testScenario) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioGetKey(TestScenario) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testScenarioDao.insertTestScenarioGetKey(testScenario);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestScenarioGetKey(TestScenario) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertTestScenarioGetKey(TestScenario)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertSuiteScenarioMappingGetKey
	 * (com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping)
	 */
	@Override
	public long insertSuiteScenarioMappingGetKey(SuiteScenarioMapping suiteScenarioMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMappingGetKey(SuiteScenarioMapping) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.suiteScenarioMappingDao.insertSuiteScenarioMappingGetKey(suiteScenarioMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSuiteScenarioMappingGetKey(SuiteScenarioMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertSuiteScenarioMappingGetKey(SuiteScenarioMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertIdentifierTypeGetKey(com.exilant
	 * .tfw.pojo.input.IdentifierType)
	 */
	@Override
	public int insertIdentifierTypeGetKey(IdentifierType identifierType) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertIdentifierTypeGetKey(IdentifierType) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.identifierTypeDao.insertIdentifierTypeGetKey(identifierType);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertIdentifierTypeGetKey(IdentifierType) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertIdentifierTypeGetKey(IdentifierType)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestCaseById(int)
	 */
	@Override
	public TestCase getTestCaseById(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestCase returnTestCase = this.testCaseDao.getTestCaseById(testCaseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseById(int) - end"); //$NON-NLS-1$
			}
			return returnTestCase;
		} catch (DataAccessException e) {
			LOG.error("getTestCaseById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public TestCase getTestCaseByName(String testCaseName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByName(String) - start"); //$NON-NLS-1$
		}

		try {
			TestCase returnTestCase = this.testCaseDao.getTestCaseByName(testCaseName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseByName(String) - end"); //$NON-NLS-1$
			}
			return returnTestCase;
		} catch (DataAccessException e) {
			LOG.error("getTestCaseByName(String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestCase(com.sssoft.isatt.data.pojo
	 * .input.TestCase)
	 */
	@Override
	public long updateTestCase(TestCase testCase) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestCase(TestCase) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testCaseDao.updateTestCase(testCase);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestCase(TestCase) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestCase(TestCase)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestStepById(int)
	 */
	@Override
	public TestStep getTestStepById(int testStepId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestStep returnTestStep = this.testStepDao.getTestStepById(testStepId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestStepById(int) - end"); //$NON-NLS-1$
			}
			return returnTestStep;
		} catch (DataAccessException e) {
			LOG.error("getTestStepById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestStep(com.sssoft.isatt.data.pojo
	 * .input.TestStep)
	 */
	@Override
	public long updateTestStep(TestStep testStep) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestStep(TestStep) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testStepDao.updateTestStep(testStep);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestStep(TestStep) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestStep(TestStep)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertTestPlanGetKey(com.exilant
	 * .tfw.pojo.input.TestPlan)
	 */
	@Override
	public int insertTestPlanGetKey(TestPlan testPlan) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanGetKey(TestPlan) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.testPlanDao.insertTestPlanGetKey(testPlan);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertTestPlanGetKey(TestPlan) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertTestPlanGetKey(TestPlan)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjGrpsByScreenID(int)
	 */
	@Override
	public List<ObjectGroup> getObjGrpsByScreenID(int screenId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpsByScreenID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ObjectGroup> returnList = this.objectGroupDao.getObjGrpsByScreenID(screenId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjGrpsByScreenID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getObjGrpsByScreenID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjGrpsByAppID(int)
	 */
	@Override
	public List<ObjectGroup> getObjGrpsByAppID(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpsByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ObjectGroup> returnList = this.objectGroupDao.getObjGrpsByAppID(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjGrpsByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getObjGrpsByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjectsForObjGrp(int)
	 */
	@Override
	public List<Objects> getObjectsForObjGrp(int objGrpId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsForObjGrp(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Objects> returnList = this.objectsDao.getObjectsByGroupId(objGrpId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectsForObjGrp(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getObjectsForObjGrp(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getIdentifierTypeByAppID(int)
	 */
	@Override
	public List<IdentifierType> getIdentifierTypeByAppID(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<IdentifierType> returnList = this.identifierTypeDao.getIdentifierTypeByAppID(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getIdentifierTypeByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getIdentifierTypeByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getObjectsById(int)
	 */
	@Override
	public Objects getObjectsById(int objId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsById(int) - start"); //$NON-NLS-1$
		}

		try {
			Objects returnObjects = this.objectsDao.getObjectsById(objId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectsById(int) - end"); //$NON-NLS-1$
			}
			return returnObjects;
		} catch (DataAccessException e) {
			LOG.error("getObjectsById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateObjectsDetails(com.exilant
	 * .tfw.pojo.input.Objects)
	 */
	@Override
	public long updateObjectsDetails(Objects objects) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjectsDetails(Objects) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.objectsDao.updateObjectsDetails(objects);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjectsDetails(Objects) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateObjectsDetails(Objects)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestPlanObjByAppId(int)
	 */
	@Override
	public List<TestPlan> getTestPlanObjByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanObjByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestPlan> returnList = this.testPlanDao.getTestPlanObjByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestPlanObjByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestPlanObjByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getPlanSuiteMappingByPlanId(int)
	 */
	@Override
	public List<PlanSuiteMapping> getPlanSuiteMappingByPlanId(int testPlanId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanSuiteMappingByPlanId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<PlanSuiteMapping> returnList = this.planSuiteMappingDao.getPlanSuiteMappingByPlanId(testPlanId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPlanSuiteMappingByPlanId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getPlanSuiteMappingByPlanId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getTestParamDataById(int)
	 */
	@Override
	public TestParamData getTestParamDataById(int testParamDataId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataById(int) - start"); //$NON-NLS-1$
		}

		try {
			TestParamData returnTestParamData = this.testParamDataDao.getTestParamDataById(testParamDataId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestParamDataById(int) - end"); //$NON-NLS-1$
			}
			return returnTestParamData;
		} catch (DataAccessException e) {
			LOG.error("getTestParamDataById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateTestParamData(com.exilant.
	 * tfw.pojo.input.TestParamData)
	 */
	@Override
	public long updateTestParamData(TestParamData testParamData) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestParamData(TestParamData) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.testParamDataDao.updateTestParamData(testParamData);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestParamData(TestParamData) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateTestParamData(TestParamData)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertConditionGroupGetKey(com.exilant
	 * .tfw.pojo.input.ConditionGroup)
	 */
	@Override
	public long insertConditionGroupGetKey(ConditionGroup conditionGrp) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionGroupGetKey(ConditionGroup) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.conditionGroupDao.insertConditionGroupGetKey(conditionGrp);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertConditionGroupGetKey(ConditionGroup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertConditionGroupGetKey(ConditionGroup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getConditionGroupById(int)
	 */
	@Override
	public ConditionGroup getConditionGroupById(int id) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupById(int) - start"); //$NON-NLS-1$
		}

		try {
			ConditionGroup returnConditionGroup = this.conditionGroupDao.getConditionGroupById(id);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getConditionGroupById(int) - end"); //$NON-NLS-1$
			}
			return returnConditionGroup;
		} catch (DataAccessException e) {
			LOG.error("getConditionGroupById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateConditionGroupData(com.exilant
	 * .tfw.pojo.input.ConditionGroup)
	 */
	public long updateConditionGroupData(ConditionGroup conGroup) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionGroupData(ConditionGroup) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.conditionGroupDao.updateConditionGroupData(conGroup);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateConditionGroupData(ConditionGroup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateConditionGroupData(ConditionGroup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getConditionByConditionGroupId(int)
	 */
	@Override
	public List<Conditions> getConditionByConditionGroupId(int conditionGroupId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionByConditionGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Conditions> returnList = this.conditionsDao.getConditionByConditionGroupId(conditionGroupId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getConditionByConditionGroupId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getConditionByConditionGroupId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertConditionsGetKey(com.exilant
	 * .tfw.pojo.input.Conditions)
	 */
	@Override
	public int insertConditionsGetKey(Conditions conditions) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionsGetKey(Conditions) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.conditionsDao.insertConditionsGetKey(conditions);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertConditionsGetKey(Conditions) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertConditionsGetKey(Conditions)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getConditionsById(int)
	 */
	@Override
	public Conditions getConditionsById(int conditionsId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsById(int) - start"); //$NON-NLS-1$
		}

		try {
			Conditions returnConditions = this.conditionsDao.getConditionsById(conditionsId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getConditionsById(int) - end"); //$NON-NLS-1$
			}
			return returnConditions;
		} catch (DataAccessException e) {
			LOG.error("getConditionsById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateConditionsData(com.exilant
	 * .tfw.pojo.input.Conditions)
	 */
	@Override
	public long updateConditionsData(Conditions conditions) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateConditionsData(Conditions) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.conditionsDao.updateConditionsData(conditions);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateConditionsData(Conditions) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateConditionsData(Conditions)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertReplacementStringsGetKey(com
	 * .exilant.tfw.pojo.input.ReplacementStrings)
	 */
	@Override
	public int insertReplacementStringsGetKey(ReplacementStrings replacementStrings) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReplacementStringsGetKey(ReplacementStrings) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.replacement_StringsDao.insertReplacementStringsGetKey(replacementStrings);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertReplacementStringsGetKey(ReplacementStrings) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertReplacementStringsGetKey(ReplacementStrings)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#insertReplacementStrings(com.exilant
	 * .tfw.pojo.input.ReplacementStrings)
	 */
	@Override
	public long insertReplacementStrings(ReplacementStrings replacementStrings) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReplacementStrings(ReplacementStrings) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.replacement_StringsDao.insertReplacementStrings(replacementStrings);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertReplacementStrings(ReplacementStrings) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertReplacementStrings(ReplacementStrings)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getIdentifierTypeById(int)
	 */
	@Override
	public IdentifierType getIdentifierTypeById(int identifierTypeId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeById(int) - start"); //$NON-NLS-1$
		}

		try {
			IdentifierType returnIdentifierType = this.identifierTypeDao.getIdentifierTypeById(identifierTypeId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getIdentifierTypeById(int) - end"); //$NON-NLS-1$
			}
			return returnIdentifierType;
		} catch (DataAccessException e) {
			LOG.error("getIdentifierTypeById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateIdentifierType(com.exilant
	 * .tfw.pojo.input.IdentifierType)
	 */
	@Override
	public long updateIdentifierType(IdentifierType identifierType) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateIdentifierType(IdentifierType) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.identifierTypeDao.updateIdentifierType(identifierType);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateIdentifierType(IdentifierType) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateIdentifierType(IdentifierType)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamByParamGrpId(int)
	 */
	@Override
	public List<Param> getParamByParamGrpId(int paramGrpId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamByParamGrpId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Param> returnList = this.paramDao.getParamByParamGroupId(paramGrpId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamByParamGrpId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParamByParamGrpId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamGrpDetailsById(int)
	 */
	@Override
	public ParamGroup getParamGrpDetailsById(int paramGrpId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGrpDetailsById(int) - start"); //$NON-NLS-1$
		}

		try {
			ParamGroup returnParamGroup = this.paramGroupDao.getParamGroupById(paramGrpId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamGrpDetailsById(int) - end"); //$NON-NLS-1$
			}
			return returnParamGroup;
		} catch (DataAccessException e) {
			LOG.error("getParamGrpDetailsById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateParamGroupData(com.exilant
	 * .tfw.pojo.input.ParamGroup)
	 */
	@Override
	public long updateParamGroupData(ParamGroup paramGroup) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParamGroupData(ParamGroup) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.paramGroupDao.updateParamGroupData(paramGroup);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateParamGroupData(ParamGroup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateParamGroupData(ParamGroup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamById(int)
	 */
	@Override
	public Param getParamById(int paramId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamById(int) - start"); //$NON-NLS-1$
		}

		try {
			Param returnParam = this.paramDao.getParamById(paramId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamById(int) - end"); //$NON-NLS-1$
			}
			return returnParam;
		} catch (DataAccessException e) {
			LOG.error("getParamById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateParamDetails(com.exilant.tfw
	 * .pojo.input.Param)
	 */
	@Override
	public long updateParamDetails(Param param) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParamDetails(Param) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.paramDao.updateParamDetails(param);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateParamDetails(Param) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateParamDetails(Param)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getReplacementStringsById(int)
	 */
	@Override
	public ReplacementStrings getReplacementStringsById(int replacementId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStringsById(int) - start"); //$NON-NLS-1$
		}

		try {
			ReplacementStrings returnReplacementStrings = this.replacement_StringsDao.getReplacementStringsById(replacementId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getReplacementStringsById(int) - end"); //$NON-NLS-1$
			}
			return returnReplacementStrings;
		} catch (DataAccessException e) {
			LOG.error("getReplacementStringsById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#updateReplacementStrings(com.exilant
	 * .tfw.pojo.input.ReplacementStrings)
	 */
	@Override
	public long updateReplacementStrings(ReplacementStrings replacementStrings) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateReplacementStrings(ReplacementStrings) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.replacement_StringsDao.updateReplacementStrings(replacementStrings);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateReplacementStrings(ReplacementStrings) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateReplacementStrings(ReplacementStrings)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.InputService#getReplacementStringsByAppID(int)
	 */
	@Override
	public List<ReplacementStrings> getReplacementStringsByAppID(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementStringsByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ReplacementStrings> returnList = this.replacement_StringsDao.getReplacementStringsByAppID(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getReplacementStringsByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getReplacementStringsByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamByParamGroupId(int)
	 */
	@Override
	public List<Param> getParamByParamGroupId(int paramGroupId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamByParamGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Param> returnList = this.paramDao.getParamByParamGroupId(paramGroupId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamByParamGroupId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getParamByParamGroupId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.InputService#getParamGroupById(int)
	 */
	@Override
	public ParamGroup getParamGroupById(int paramGroupId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupById(int) - start"); //$NON-NLS-1$
		}

		try {
			ParamGroup returnParamGroup = this.paramGroupDao.getParamGroupById(paramGroupId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getParamGroupById(int) - end"); //$NON-NLS-1$
			}
			return returnParamGroup;
		} catch (DataAccessException e) {
			LOG.error("getParamGroupById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<TestScenarioUI> getTestScenarioUIByAppID(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioUIByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestScenarioUI> returnList = this.testScenarioDao.getTestScenarioUIByAppID(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestScenarioUIByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestScenarioUIByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<TestSuiteUI> getTestSuiteUIByAppID(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteUIByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuiteUI> returnList = this.testSuiteDao.getTestSuiteUIByAppID(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteUIByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestSuiteUIByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	
	@Override
	public List<ScenarioCaseMapping> getScenarioCaseMappingsByScenarioId(int testScenarioId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			LOG.info("value in input service : "+testScenarioId);
			List<ScenarioCaseMapping> returnList = this.scenarioCaseMappingDao.getScenarioCaseMappingsByScenarioId(testScenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScenarioCaseMappingsByScenarioId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScenarioCaseMappingsByScenarioId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<ScenarioCaseMapping> getScenarioCaseMappingsByCaseId(int caseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			LOG.info("value in input service : "+caseId);
			List<ScenarioCaseMapping> returnList = this.scenarioCaseMappingDao.getScenarioCaseMappingsByCaseId(caseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScenarioCaseMappingsByCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScenarioCaseMappingsByCaseId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<CaseStepMapping> getCaseStepMappingsByCaseId(int caseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingsByCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			LOG.info("value in input service : "+caseId);
			List<CaseStepMapping> returnList = this.caseStepMappingDao.getCaseStepMappingsByCaseId(caseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getCaseStepMappingsByCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getCaseStepMappingsByCaseId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<TestCaseUI> getTestCaseUIByAppID(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseUIByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestCaseUI> returnList = this.testCaseDao.getTestCaseUIByAppID(testCaseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseUIByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestCaseUIByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<PlanSuiteMapping> getPlanSuiteMappingBySuiteId(int testSuiteId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanSuiteMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

			try {
			List<PlanSuiteMapping> returnList = this.planSuiteMappingDao.getPlanSuiteMappingBySuiteId(testSuiteId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPlanSuiteMappingBySuiteId(int) - end"); //$NON-NLS-1$
			}
				return returnList;
			} catch (DataAccessException e) {
			LOG.error("getPlanSuiteMappingBySuiteId(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}

	@Override
	public List<SuiteScenarioMapping> getSuiteScenarioMappingBySuiteId(
			int suiteId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

		 try {
			List<SuiteScenarioMapping> returnList = this.suiteScenarioMappingDao.getSuiteScenarioMappingBySuiteId(suiteId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSuiteScenarioMappingBySuiteId(int) - end"); //$NON-NLS-1$
			}
				 return
				  returnList;
				 } catch (DataAccessException e) {
			LOG.error("getSuiteScenarioMappingBySuiteId(int)", e); //$NON-NLS-1$

				 throw new ServiceException(e.getMessage());
				 }
	}
	
	@Override
	public List<SuiteScenarioMapping> getSuiteScenarioMappingByPlanId(int planId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingByPlanId(int) - start"); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingByPlanId(int) - end"); //$NON-NLS-1$
		}
		return null;
	}
	
	@Override
	public List<SuiteScenarioMapping> getSuiteScenarioMappingByScenarioId(int scenarioId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingByScenarioId(int) - start"); //$NON-NLS-1$
		}

			try {
			List<SuiteScenarioMapping> returnList = this.suiteScenarioMappingDao.getSuiteScenarioMappingByScenarioId(scenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSuiteScenarioMappingByScenarioId(int) - end"); //$NON-NLS-1$
			}
				return returnList;
			} catch (DataAccessException e) {
			LOG.error("getSuiteScenarioMappingByScenarioId(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public List<Objects> getObjectsByGroupId(int objectGroupId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsByGroupId(int) - start"); //$NON-NLS-1$
		}

			try {
			List<Objects> returnList = this.objectsDao.getObjectsByGroupId(objectGroupId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectsByGroupId(int) - end"); //$NON-NLS-1$
			}
				return returnList;
			} catch (DataAccessException e) {
			LOG.error("getObjectsByGroupId(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public long updateObjects(final Objects objects)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateObjects(Objects) - start"); //$NON-NLS-1$
		}

			try {
			long returnlong = this.objectsDao.updateObjects(objects);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjects(Objects) - end"); //$NON-NLS-1$
			}
				return returnlong;
			} catch (DataAccessException e) {
			LOG.error("updateObjects(Objects)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public List<TestStep> getTestStepByStepsId(int testStepId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepByStepsId(int) - start"); //$NON-NLS-1$
		}

			try {
			List<TestStep> returnList = this.testStepDao.getTestStepByStepsId(testStepId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestStepByStepsId(int) - end"); //$NON-NLS-1$
			}
				return returnList;
			} catch (DataAccessException e) {
			LOG.error("getTestStepByStepsId(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public long deleteScenarioCaseMappingById(final int scenarioId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteScenarioCaseMappingById(int) - start"); //$NON-NLS-1$
		}

			try {
			long returnlong = this.scenarioCaseMappingDao.deleteScenarioCaseMappingById(scenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deleteScenarioCaseMappingById(int) - end"); //$NON-NLS-1$
			}
				return returnlong;
			} catch (DataAccessException e) {
			LOG.error("deleteScenarioCaseMappingById(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public long deleteSuiteScenarioMappingById(final int scenarioId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSuiteScenarioMappingById(int) - start"); //$NON-NLS-1$
		}

			try {
			long returnlong = this.suiteScenarioMappingDao.deleteSuiteScenarioMappingById(scenarioId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deleteSuiteScenarioMappingById(int) - end"); //$NON-NLS-1$
			}
				return returnlong;
			} catch (DataAccessException e) {
			LOG.error("deleteSuiteScenarioMappingById(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public long deleteSuiteScenarioMappingBySuiteId(final int suiteId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSuiteScenarioMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

			try {
			long returnlong = this.suiteScenarioMappingDao.deleteSuiteScenarioMappingBySuiteId(suiteId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deleteSuiteScenarioMappingBySuiteId(int) - end"); //$NON-NLS-1$
			}
				return returnlong;
			} catch (DataAccessException e) {
			LOG.error("deleteSuiteScenarioMappingBySuiteId(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public List<Integer> getTestCaseIdByName(String testCaseName)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByName(String) - start"); //$NON-NLS-1$
		}

			try {
			List<Integer> returnList = this.testCaseDao.getTestCaseIdByName(testCaseName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseIdByName(String) - end"); //$NON-NLS-1$
			}
				return returnList;
			} catch (DataAccessException e) {
			LOG.error("getTestCaseIdByName(String)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
	
	@Override
	public long deleteScenarioCaseMappingByCaseId(final int caseId)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteScenarioCaseMappingByCaseId(int) - start"); //$NON-NLS-1$
		}

			try {
			long returnlong = this.scenarioCaseMappingDao.deleteScenarioCaseMappingByCaseId(caseId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deleteScenarioCaseMappingByCaseId(int) - end"); //$NON-NLS-1$
			}
				return returnlong;
			} catch (DataAccessException e) {
			LOG.error("deleteScenarioCaseMappingByCaseId(int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
	}
}