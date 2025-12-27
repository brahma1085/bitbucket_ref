package com.sssoft.isatt.data.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.sssoft.isatt.data.dao.ActionsDao;
import com.sssoft.isatt.data.dao.AgentDetailsDao;
import com.sssoft.isatt.data.dao.AppFeatureDao;
import com.sssoft.isatt.data.dao.AppFunctionalityDao;
import com.sssoft.isatt.data.dao.ApplicationDao;
import com.sssoft.isatt.data.dao.GenericDataDao;
import com.sssoft.isatt.data.dao.RolesDao;
import com.sssoft.isatt.data.dao.RunnerDao;
import com.sssoft.isatt.data.dao.SchedulerBackupDao;
import com.sssoft.isatt.data.dao.SchedulerDao;
import com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao;
import com.sssoft.isatt.data.dao.SchedulerRunDetailsDao;
import com.sssoft.isatt.data.dao.ScreenDao;
import com.sssoft.isatt.data.dao.UserRolesDao;
import com.sssoft.isatt.data.dao.UsersApplicationMappingDao;
import com.sssoft.isatt.data.dao.UsersDao;
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
import com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao;
import com.sssoft.isatt.data.dao.input.SuiteScenarioMappingDao;
import com.sssoft.isatt.data.dao.input.TestCaseDao;
import com.sssoft.isatt.data.dao.input.TestConditionDataDao;
import com.sssoft.isatt.data.dao.input.TestDataDao;
import com.sssoft.isatt.data.dao.input.TestParamDataDao;
import com.sssoft.isatt.data.dao.input.TestPlanDao;
import com.sssoft.isatt.data.dao.input.TestScenarioDao;
import com.sssoft.isatt.data.dao.input.TestStepDao;
import com.sssoft.isatt.data.dao.input.TestSuiteDao;
import com.sssoft.isatt.data.excelreader.ReadPlan;
import com.sssoft.isatt.data.exception.CacheException;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ExcelUtilityException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Actions;
import com.sssoft.isatt.data.pojo.AgentDetails;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.ExcelImport;
import com.sssoft.isatt.data.pojo.GenericData;
import com.sssoft.isatt.data.pojo.Roles;
import com.sssoft.isatt.data.pojo.Runner;
import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.SchedulerBackup;
import com.sssoft.isatt.data.pojo.SchedulerLaneDetails;
import com.sssoft.isatt.data.pojo.SchedulerRunDetails;
import com.sssoft.isatt.data.pojo.Screen;
import com.sssoft.isatt.data.pojo.UserRoles;
import com.sssoft.isatt.data.pojo.Users;
import com.sssoft.isatt.data.pojo.UsersApplicationMapping;
import com.sssoft.isatt.data.pojo.def.DataSets;
import com.sssoft.isatt.data.pojo.def.ScheduledJobs;
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
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestConditionData;
import com.sssoft.isatt.data.pojo.input.TestData;
import com.sssoft.isatt.data.pojo.input.TestParamData;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.data.utils.DataConstants;
import com.sssoft.isatt.data.utils.MapCacheUtility;

/**
 * The Class MainServiceImpl.
 *
 * @author mohammedfirdos
 */

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
public class MainServiceImpl implements MainService {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(MainServiceImpl.class);

	/** The actions dao. */
	private ActionsDao actionsDao;

	/** The agent details dao. */
	private AgentDetailsDao agentDetailsDao;

	/** The app feature dao. */
	private AppFeatureDao appFeatureDao;

	/** The app functionality dao. */
	private AppFunctionalityDao appFunctionalityDao;

	/** The application dao. */
	private ApplicationDao applicationDao;

	/** The generic data dao. */
	private GenericDataDao genericDataDao;

	/** The runner dao. */
	private RunnerDao runnerDao;

	/** The scheduler dao. */
	private SchedulerDao schedulerDao;

	/** The scheduler lane details dao. */
	private SchedulerLaneDetailsDao schedulerLaneDetailsDao;

	/** The scheduler run details dao. */
	private SchedulerRunDetailsDao schedulerRunDetailsDao;

	/** The screen dao. */
	private ScreenDao screenDao;

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

	/** The test case dao. */
	private TestCaseDao testCaseDao;

	/** The test condition data dao. */
	private TestConditionDataDao testConditionDataDao;

	/** The test param data dao. */
	private TestParamDataDao testParamDataDao;

	/** The test scenario dao. */
	private TestScenarioDao testScenarioDao;

	/** The test step dao. */
	private TestStepDao testStepDao;

	/** The test suite dao. */
	private TestSuiteDao testSuiteDao;

	/** The test data dao. */
	private TestDataDao testDataDao;

	/** The suite scenario mapping dao. */
	private SuiteScenarioMappingDao suiteScenarioMappingDao;

	/** The scenario case mapping dao. */
	private ScenarioCaseMappingDao scenarioCaseMappingDao;

	/** The case step mapping dao. */
	private CaseStepMappingDao caseStepMappingDao;

	/** The plan suite mapping dao. */
	private PlanSuiteMappingDao planSuiteMappingDao;

	/** The scheduler backup dao. */
	private SchedulerBackupDao schedulerBackupDao;

	private RolesDao rolesDao;
	private UsersDao usersDao;
	private UsersApplicationMappingDao usersApplicationMappingDao;
	private UserRolesDao userRolesDao;
	
	/**
	 * Sets the scheduler backup dao.
	 * 
	 * @param schedulerBackupDao
	 *            the new scheduler backup dao
	 */
	public void setSchedulerBackupDao(SchedulerBackupDao schedulerBackupDao) {
		this.schedulerBackupDao = schedulerBackupDao;
	}

	/**
	 * Sets the plan suite mapping dao.
	 * 
	 * @param planSuiteMappingDao
	 *            the new plan suite mapping dao
	 */
	public void setPlanSuiteMappingDao(PlanSuiteMappingDao planSuiteMappingDao) {
		this.planSuiteMappingDao = planSuiteMappingDao;
	}

	/**
	 * Sets the suite scenario mapping dao.
	 * 
	 * @param suiteScenarioMappingDao
	 *            the new suite scenario mapping dao
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
	 *            the new case step mapping dao
	 */
	public void setCaseStepMappingDao(CaseStepMappingDao caseStepMappingDao) {
		this.caseStepMappingDao = caseStepMappingDao;
	}

	/**
	 * Sets the test data dao.
	 * 
	 * @param testDataDao
	 *            the new test data dao
	 */
	public void setTestDataDao(TestDataDao testDataDao) {
		this.testDataDao = testDataDao;
	}

	/** The test plan dao. */
	private TestPlanDao testPlanDao;

	/** The map cache utility. */
	private MapCacheUtility mapCacheUtility;

	/**
	 * Sets the map cache utility.
	 * 
	 * @param mapCacheUtility
	 *            the new map cache utility
	 */
	public void setMapCacheUtility(MapCacheUtility mapCacheUtility) {
		this.mapCacheUtility = mapCacheUtility;
	}

	/**
	 * Sets the test plan dao.
	 * 
	 * @param testPlanDao
	 *            the new test plan dao
	 */
	public void setTestPlanDao(TestPlanDao testPlanDao) {
		this.testPlanDao = testPlanDao;
	}

	/**
	 * Setter Injection Methods for Spring Injection of Dependencies.
	 * 
	 * @param conditionGroupDao
	 *            the new condition group dao
	 */
	public void setConditionGroupDao(ConditionGroupDao conditionGroupDao) {
		this.conditionGroupDao = conditionGroupDao;
	}

	/**
	 * Sets the actions dao.
	 * 
	 * @param actionsDao
	 *            the actionsDao to set
	 */
	public void setActionsDao(ActionsDao actionsDao) {
		this.actionsDao = actionsDao;
	}

	/**
	 * Sets the conditions dao.
	 * 
	 * @param conditionsDao
	 *            the new conditions dao
	 */
	public void setConditionsDao(ConditionsDao conditionsDao) {
		this.conditionsDao = conditionsDao;
	}

	/**
	 * Sets the identifier type dao.
	 * 
	 * @param identifierTypeDao
	 *            the new identifier type dao
	 */
	public void setIdentifierTypeDao(IdentifierTypeDao identifierTypeDao) {
		this.identifierTypeDao = identifierTypeDao;
	}

	/**
	 * Sets the object group dao.
	 * 
	 * @param objectGroupDao
	 *            the new object group dao
	 */
	public void setObjectGroupDao(ObjectGroupDao objectGroupDao) {
		this.objectGroupDao = objectGroupDao;
	}

	/**
	 * Sets the objects dao.
	 * 
	 * @param objectsDao
	 *            the new objects dao
	 */
	public void setObjectsDao(ObjectsDao objectsDao) {
		this.objectsDao = objectsDao;
	}

	/**
	 * Sets the object type dao.
	 * 
	 * @param objectTypeDao
	 *            the new object type dao
	 */
	public void setObjectTypeDao(ObjectTypeDao objectTypeDao) {
		this.objectTypeDao = objectTypeDao;
	}

	/**
	 * Sets the param dao.
	 * 
	 * @param paramDao
	 *            the new param dao
	 */
	public void setParamDao(ParamDao paramDao) {
		this.paramDao = paramDao;
	}

	/**
	 * Sets the param group dao.
	 * 
	 * @param paramGroupDao
	 *            the new param group dao
	 */
	public void setParamGroupDao(ParamGroupDao paramGroupDao) {
		this.paramGroupDao = paramGroupDao;
	}

	/**
	 * Sets the test case dao.
	 * 
	 * @param testCaseDao
	 *            the new test case dao
	 */
	public void setTestCaseDao(TestCaseDao testCaseDao) {
		this.testCaseDao = testCaseDao;
	}

	/**
	 * Sets the test condition data dao.
	 * 
	 * @param testConditionDataDao
	 *            the new test condition data dao
	 */
	public void setTestConditionDataDao(TestConditionDataDao testConditionDataDao) {
		this.testConditionDataDao = testConditionDataDao;
	}

	/**
	 * Sets the test param data dao.
	 * 
	 * @param testParamDataDao
	 *            the new test param data dao
	 */
	public void setTestParamDataDao(TestParamDataDao testParamDataDao) {
		this.testParamDataDao = testParamDataDao;
	}

	/**
	 * Sets the test scenario dao.
	 * 
	 * @param testScenarioDao
	 *            the new test scenario dao
	 */
	public void setTestScenarioDao(TestScenarioDao testScenarioDao) {
		this.testScenarioDao = testScenarioDao;
	}

	/**
	 * Sets the test step dao.
	 * 
	 * @param testStepDao
	 *            the new test step dao
	 */
	public void setTestStepDao(TestStepDao testStepDao) {
		this.testStepDao = testStepDao;
	}

	/**
	 * Sets the test suite dao.
	 * 
	 * @param testSuiteDao
	 *            the new test suite dao
	 */
	public void setTestSuiteDao(TestSuiteDao testSuiteDao) {
		this.testSuiteDao = testSuiteDao;
	}

	/**
	 * Sets the agent details dao.
	 * 
	 * @param agentDetailsDao
	 *            the agentDetailsDao to set
	 */
	public void setAgentDetailsDao(AgentDetailsDao agentDetailsDao) {
		this.agentDetailsDao = agentDetailsDao;
	}

	/**
	 * Sets the app feature dao.
	 * 
	 * @param appFeatureDao
	 *            the appFeatureDao to set
	 */
	public void setAppFeatureDao(AppFeatureDao appFeatureDao) {
		this.appFeatureDao = appFeatureDao;
	}

	/**
	 * Sets the app functionality dao.
	 * 
	 * @param appFunctionalityDao
	 *            the appFunctionalityDao to set
	 */
	public void setAppFunctionalityDao(AppFunctionalityDao appFunctionalityDao) {
		this.appFunctionalityDao = appFunctionalityDao;
	}

	/**
	 * Sets the application dao.
	 * 
	 * @param applicationDao
	 *            the applicationDao to set
	 */
	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	/**
	 * Sets the generic data dao.
	 * 
	 * @param genericDataDao
	 *            the genericDataDao to set
	 */
	public void setGenericDataDao(GenericDataDao genericDataDao) {
		this.genericDataDao = genericDataDao;
	}

	/**
	 * Sets the runner dao.
	 * 
	 * @param runnerDao
	 *            the runnerDao to set
	 */
	public void setRunnerDao(RunnerDao runnerDao) {
		this.runnerDao = runnerDao;
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
	 * Sets the scheduler lane details dao.
	 * 
	 * @param schedulerLaneDetailsDao
	 *            the schedulerLaneDetailsDao to set
	 */
	public void setSchedulerLaneDetailsDao(SchedulerLaneDetailsDao schedulerLaneDetailsDao) {
		this.schedulerLaneDetailsDao = schedulerLaneDetailsDao;
	}

	/**
	 * Sets the scheduler run details dao.
	 * 
	 * @param schedulerRunDetailsDao
	 *            the schedulerRunDetailsDao to set
	 */
	public void setSchedulerRunDetailsDao(SchedulerRunDetailsDao schedulerRunDetailsDao) {
		this.schedulerRunDetailsDao = schedulerRunDetailsDao;
	}

	/**
	 * Sets the screen dao.
	 * 
	 * @param screenDao
	 *            the screenDao to set
	 */
	public void setScreenDao(ScreenDao screenDao) {
		this.screenDao = screenDao;
	}

	/**
	 * @param rolesDao the rolesDao to set
	 */
	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	/**
	 * @param usersDao the usersDao to set
	 */
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	/**
	 * @param usersApplicationMappingDao the usersApplicationMappingDao to set
	 */
	public void setUsersApplicationMappingDao(
			UsersApplicationMappingDao usersApplicationMappingDao) {
		this.usersApplicationMappingDao = usersApplicationMappingDao;
	}

	/**
	 * @param userRolesDao the userRolesDao to set
	 */
	public void setUserRolesDao(UserRolesDao userRolesDao) {
		this.userRolesDao = userRolesDao;
	}
	
	/*
	 * Service Layer Methods Implementing DataModule MainService Interface
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getActions()
	 */
	@Override
	public List<Actions> getActions() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActions() - start"); //$NON-NLS-1$
		}

		try {
			List<Actions> returnList = this.actionsDao.getActions();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getActions() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getActions()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAgentDetails()
	 */
	@Override
	public List<AgentDetails> getAgentDetails() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentDetails() - start"); //$NON-NLS-1$
		}

		try {
			List<AgentDetails> returnList = this.agentDetailsDao.getAgentDetails();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAgentDetails() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAgentDetails()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAppFeature()
	 */
	@Override
	public List<AppFeature> getAppFeature() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeature() - start"); //$NON-NLS-1$
		}

		try {
			List<AppFeature> returnList = this.appFeatureDao.getAppFeature();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeature() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFeature()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAppFunctionality()
	 */
	@Override
	public List<AppFunctionality> getAppFunctionality() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionality() - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.appFunctionalityDao.getAppFunctionality();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFunctionality() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFunctionality()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertApplication(com.exilant.tfw
	 * .pojo.Application)
	 */
	@Override
	public long insertApplication(Application application) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplication(Application) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.applicationDao.insertApplication(application);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertApplication(Application) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertApplication(Application)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long insertRunner(Runner runner) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRunner(Runner) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.runnerDao.insertRunner(runner);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertRunner(Runner) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertRunner(Runner)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getApplication()
	 */
	@Override
	public List<Application> getApplication() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplication() - start"); //$NON-NLS-1$
		}

		try {
			List<Application> returnList = this.applicationDao.getApplication();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getApplication() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getApplication()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getGenericData()
	 */
	@Override
	public List<GenericData> getGenericData() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getGenericData() - start"); //$NON-NLS-1$
		}

		try {
			List<GenericData> returnList = this.genericDataDao.getGenericData();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getGenericData() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getGenericData()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getRunner()
	 */
	@Override
	public List<Runner> getRunner() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunner() - start"); //$NON-NLS-1$
		}

		try {
			List<Runner> returnList = this.runnerDao.getRunner();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getRunner() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getRunner()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getScheduler()
	 */
	@Override
	public List<Scheduler> getScheduler() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduler() - start"); //$NON-NLS-1$
		}

		try {
			List<Scheduler> returnList = this.schedulerDao.getScheduler();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScheduler() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScheduler()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerLaneDetails()
	 */
	@Override
	public List<SchedulerLaneDetails> getSchedulerLaneDetails() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetails() - start"); //$NON-NLS-1$
		}

		try {
			List<SchedulerLaneDetails> returnList = this.schedulerLaneDetailsDao.getSchedulerLaneDetails();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerLaneDetails() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerLaneDetails()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerRunDetails()
	 */
	@Override
	public List<SchedulerRunDetails> getSchedulerRunDetails() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerRunDetails() - start"); //$NON-NLS-1$
		}

		try {
			List<SchedulerRunDetails> returnList = this.schedulerRunDetailsDao.getSchedulerRunDetails();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerRunDetails() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerRunDetails()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getScreen()
	 */
	@Override
	public List<Screen> getScreen() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreen() - start"); //$NON-NLS-1$
		}

		try {
			List<Screen> returnList = this.screenDao.getScreen();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreen() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScreen()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAppFunctionalityIdByName(int,
	 * java.lang.String)
	 */
	@Override
	public int getAppFunctionalityIdByName(int appId, String functionalName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityIdByName(int, String) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.appFunctionalityDao.getAppFunctionalityIdByName(appId, functionalName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFunctionalityIdByName(int, String) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("getAppFunctionalityIdByName(int, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertReadPlanData(com.exilant.tfw
	 * .pojo.ExcelImport)
	 */
	@Override
	public void insertReadPlanData(ExcelImport excelImport) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReadPlanData(ExcelImport) - start"); //$NON-NLS-1$
		}

		try {
			ReadPlan readPlan = new ReadPlan();
			Application application = readPlan.readPlanObj(excelImport);
			LOG.info("application data from excel sheet : " + application);
			if (application != null) {
				application = insertApplicationDetails(application);
				application = insertAppFunctionalityDetails(application);
				application = insertIdentifierTypeDetails(application);
				application = insertTestDataDetails(application);
				application = insertTestPlanDetails(application);
			}
		} catch (DataAccessException e) {
			LOG.error("insertReadPlanData(ExcelImport)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		} catch (ExcelUtilityException e) {
			LOG.error("insertReadPlanData(ExcelImport)", e); //$NON-NLS-1$
			throw new ServiceException(e.getMessage());
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertReadPlanData(ExcelImport) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Insert test plan details.
	 * 
	 * @param application
	 *            the application
	 * @return the application
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Application insertTestPlanDetails(Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanDetails(Application) - start"); //$NON-NLS-1$
		}

		List<TestPlan> insertedPlans = new ArrayList<TestPlan>();
		List<TestPlan> testPlans = application.getTestPlan();
		Iterator<TestPlan> iterator = testPlans.iterator();
		while (iterator.hasNext()) {
			TestPlan testPlan = (TestPlan) iterator.next();
			int appId = application.getAppID();
			int testPlanId = this.testPlanDao.getTestPlanByNameAndAppId(testPlan.getPlanName(), appId);
			if (testPlanId != 0) {
				throw new DataAccessException("You are trying to insert the same excel sheet which may cause data redundancy");
			}
			testPlan.setAppID(appId);
			testPlan.setUpdatedBy(DataConstants.DEFAULT_USER);
			testPlan.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (testPlanId == 0) {
				testPlan.setCreatedBy(DataConstants.DEFAULT_USER);
				testPlan.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				testPlanId = this.testPlanDao.insertTestPlanGetKey(testPlan);
			} else {
				testPlan.setTestPlanID(testPlanId);
				this.testPlanDao.updateTestPlan(testPlan);
			}
			testPlan.setTestPlanID(testPlanId);
			testPlan = insertPlanSuiteMappingDetails(testPlan);
			insertedPlans.add(testPlan);
		}
		application.setTestPlan(insertedPlans);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanDetails(Application) - end"); //$NON-NLS-1$
		}
		return application;
	}

	/**
	 * Insert plan suite mapping details.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the test plan
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestPlan insertPlanSuiteMappingDetails(TestPlan testPlan) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertPlanSuiteMappingDetails(TestPlan) - start"); //$NON-NLS-1$
		}

		List<PlanSuiteMapping> insertedPSMappings = new ArrayList<PlanSuiteMapping>();
		List<PlanSuiteMapping> planSuiteMappings = testPlan.getPlanSuiteMappings();
		Iterator<PlanSuiteMapping> iterator = planSuiteMappings.iterator();
		while (iterator.hasNext()) {
			PlanSuiteMapping planSuiteMapping = (PlanSuiteMapping) iterator.next();
			int appId = testPlan.getAppID();
			planSuiteMapping = insertTestSuiteDetails(planSuiteMapping, appId);
			int suiteId = planSuiteMapping.getTestSuite().getTestSuiteID();
			int planId = testPlan.getTestPlanID();
			int planSuiteId = this.planSuiteMappingDao.getPlanSuiteIdByPlanIdBySuiteId(planId, suiteId);
			planSuiteMapping.setTestPlanID(planId);
			planSuiteMapping.setTestSuiteID(suiteId);
			planSuiteMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
			planSuiteMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (planSuiteId == 0) {
				planSuiteMapping.setCreatedBy(DataConstants.DEFAULT_USER);
				planSuiteMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				planSuiteId = this.planSuiteMappingDao.insertPlanSuiteMappingDaoGetKey(planSuiteMapping);
			} else {
				planSuiteMapping.setPlanSuiteMappingID(planSuiteId);
				this.planSuiteMappingDao.updatePlanSuiteMapping(planSuiteMapping);
			}
			planSuiteMapping.setPlanSuiteMappingID(planSuiteId);
			planSuiteMapping = insertSuiteScenarioMappingDetails(planSuiteMapping, appId);
			insertedPSMappings.add(planSuiteMapping);
		}
		testPlan.setPlanSuiteMappings(insertedPSMappings);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertPlanSuiteMappingDetails(TestPlan) - end"); //$NON-NLS-1$
		}
		return testPlan;
	}

	/**
	 * Insert suite scenario mapping details.
	 * 
	 * @param planSuiteMapping
	 *            the plan suite mapping
	 * @param appId
	 *            the app id
	 * @return the plan suite mapping
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private PlanSuiteMapping insertSuiteScenarioMappingDetails(PlanSuiteMapping planSuiteMapping, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMappingDetails(PlanSuiteMapping, int) - start"); //$NON-NLS-1$
		}

		List<SuiteScenarioMapping> insertedSSMappings = new ArrayList<SuiteScenarioMapping>();
		List<SuiteScenarioMapping> suiteScenarioMappings = planSuiteMapping.getTestSuite().getSuiteScenarioMappings();
		Iterator<SuiteScenarioMapping> iterator = suiteScenarioMappings.iterator();
		while (iterator.hasNext()) {
			SuiteScenarioMapping suiteScenarioMapping = (SuiteScenarioMapping) iterator.next();
			suiteScenarioMapping = insertTestScenarioDetails(suiteScenarioMapping, appId);
			int suiteId = planSuiteMapping.getTestSuite().getTestSuiteID();
			int scenarioId = suiteScenarioMapping.getTestScenario().getTestScenarioID();
			int suiteSceMappingId = this.suiteScenarioMappingDao.getSuiteScenarioMapByDependentIds(scenarioId, suiteId);
			suiteScenarioMapping.setTestScenarioID(scenarioId);
			suiteScenarioMapping.setTestSuiteID(suiteId);
			suiteScenarioMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
			suiteScenarioMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (suiteSceMappingId == 0) {
				suiteScenarioMapping.setCreatedBy(DataConstants.DEFAULT_USER);
				suiteScenarioMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				suiteSceMappingId = this.suiteScenarioMappingDao.insertSuiteScenarioMappingGetKey(suiteScenarioMapping);
			} else {
				suiteScenarioMapping.setSuiteScenarioMappingID(suiteSceMappingId);
				this.suiteScenarioMappingDao.updateSuiteScenarioMappingDao(suiteScenarioMapping);
			}
			suiteScenarioMapping.setSuiteScenarioMappingID(suiteSceMappingId);
			insertedSSMappings.add(suiteScenarioMapping);
		}
		planSuiteMapping.getTestSuite().setSuiteScenarioMappings(insertedSSMappings);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMappingDetails(PlanSuiteMapping, int) - end"); //$NON-NLS-1$
		}
		return planSuiteMapping;
	}

	/**
	 * Insert test scenario details.
	 * 
	 * @param ssMapping
	 *            the ss mapping
	 * @param appId
	 *            the app id
	 * @return the suite scenario mapping
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private SuiteScenarioMapping insertTestScenarioDetails(SuiteScenarioMapping ssMapping, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioDetails(SuiteScenarioMapping, int) - start"); //$NON-NLS-1$
		}

		TestScenario testScenario = ssMapping.getTestScenario();
		int testScenarioId = this.testScenarioDao.getTestScenarioId(appId, testScenario.getTestScenarioName());
		testScenario.setAppID(appId);
		testScenario.setUpdatedBy(DataConstants.DEFAULT_USER);
		testScenario.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (testScenarioId == 0) {
			testScenario.setCreatedBy(DataConstants.DEFAULT_USER);
			testScenario.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testScenarioId = this.testScenarioDao.insertTestScenarioGetKey(testScenario);
		} else {
			testScenario.setTestScenarioID(testScenarioId);
			this.testScenarioDao.updateTestScenario(testScenario);
		}
		testScenario.setTestScenarioID(testScenarioId);
		testScenario = insertScenarioCaseMappingDetails(testScenario, appId);
		ssMapping.setTestScenario(testScenario);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioDetails(SuiteScenarioMapping, int) - end"); //$NON-NLS-1$
		}
		return ssMapping;
	}

	/**
	 * Insert scenario case mapping details.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @param appId
	 *            the app id
	 * @return the test scenario
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestScenario insertScenarioCaseMappingDetails(TestScenario testScenario, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingDetails(TestScenario, int) - start"); //$NON-NLS-1$
		}

		List<ScenarioCaseMapping> insertedScMappings = new ArrayList<ScenarioCaseMapping>();
		List<ScenarioCaseMapping> scenarioCaseMappings = testScenario.getScenarioCaseMappings();
		Iterator<ScenarioCaseMapping> iterator = scenarioCaseMappings.iterator();
		while (iterator.hasNext()) {
			ScenarioCaseMapping scenarioCaseMapping = (ScenarioCaseMapping) iterator.next();
			scenarioCaseMapping = insertTestCaseDetails(scenarioCaseMapping, appId);
			int scenarioId = testScenario.getTestScenarioID();
			int caseId = scenarioCaseMapping.getTestCase().getTestCaseID();
			scenarioCaseMapping.setTestScenarioID(scenarioId);
			scenarioCaseMapping.setTestCaseID(caseId);
			int scenarioCaseMappingId = this.scenarioCaseMappingDao.getScenarioCaseMapByTestScenarioId(scenarioId, caseId);
			scenarioCaseMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
			scenarioCaseMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (scenarioCaseMappingId == 0) {
				scenarioCaseMapping.setCreatedBy(DataConstants.DEFAULT_USER);
				scenarioCaseMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				scenarioCaseMappingId = this.scenarioCaseMappingDao.insertScenarioCaseMappingGetKey(scenarioCaseMapping);
			} else {
				scenarioCaseMapping.setScenarioCaseMappingID(scenarioCaseMappingId);
				this.scenarioCaseMappingDao.updateScenarioCaseMappingDao(scenarioCaseMapping);
			}
			scenarioCaseMapping.setScenarioCaseMappingID(scenarioCaseMappingId);
			insertedScMappings.add(scenarioCaseMapping);
		}
		testScenario.setScenarioCaseMappings(insertedScMappings);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingDetails(TestScenario, int) - end"); //$NON-NLS-1$
		}
		return testScenario;
	}

	/**
	 * Insert test case details.
	 * 
	 * @param scMapping
	 *            the sc mapping
	 * @param appId
	 *            the app id
	 * @return the scenario case mapping
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private ScenarioCaseMapping insertTestCaseDetails(ScenarioCaseMapping scMapping, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseDetails(ScenarioCaseMapping, int) - start"); //$NON-NLS-1$
		}

		TestCase testCase = scMapping.getTestCase();
		testCase = insertConditionGroupDetails(testCase, appId);
		testCase = insertRunnerDetails(testCase);
		testCase = updateAppFeatureDetails(testCase, appId);
		int featureId = testCase.getAppFeature().getFeatureID();
		int functionalId = testCase.getAppFeature().getFunctionalID();
		int runnerId = testCase.getRunner().getRunnerID();
		testCase.setFeatureID(featureId);
		testCase.setFunctionalID(functionalId);
		testCase.setRunnerID(runnerId);
		int testCaseId = this.testCaseDao.getTestCaseIdByNameByFeatureIdByClassificationTag(testCase);
		testCase.setUpdatedBy(DataConstants.DEFAULT_USER);
		testCase.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (testCaseId == 0) {
			testCase.setCreatedBy(DataConstants.DEFAULT_USER);
			testCase.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testCaseId = this.testCaseDao.insertTestCaseGetKey(testCase);
		} else {
			testCase.setTestCaseID(testCaseId);
			this.testCaseDao.updateTestCase(testCase);
		}
		testCase.setTestCaseID(testCaseId);
		testCase = insertCaseStepMappingDetails(testCase, appId, featureId);
		scMapping.setTestCase(testCase);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseDetails(ScenarioCaseMapping, int) - end"); //$NON-NLS-1$
		}
		return scMapping;
	}

	/**
	 * Insert case step mapping details.
	 * 
	 * @param testCase
	 *            the test case
	 * @param appId
	 *            the app id
	 * @param featureId
	 *            the feature id
	 * @return the test case
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestCase insertCaseStepMappingDetails(TestCase testCase, int appId, int featureId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMappingDetails(TestCase, int, int) - start"); //$NON-NLS-1$
		}

		List<CaseStepMapping> insertedCSMappings = new ArrayList<CaseStepMapping>();
		List<CaseStepMapping> caseStepMappings = testCase.getCaseStepMappings();
		Iterator<CaseStepMapping> iterator = caseStepMappings.iterator();
		while (iterator.hasNext()) {
			CaseStepMapping caseStepMapping = (CaseStepMapping) iterator.next();
			caseStepMapping = insertTestStepDetails(caseStepMapping, appId, featureId, testCase.getConditionGroup().getConditionGroupID());
			int caseId = testCase.getTestCaseID();
			int stepId = caseStepMapping.getTestStep().getTestStepID();
			int caseStepMapId = this.caseStepMappingDao.getCaseStepMappingsByCaseIdByStepId(caseId, stepId);
			caseStepMapping.setTestCaseID(caseId);
			caseStepMapping.setTestStepID(stepId);
			caseStepMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
			caseStepMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (caseStepMapId == 0) {
				caseStepMapping.setCreatedBy(DataConstants.DEFAULT_USER);
				caseStepMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				caseStepMapId = this.caseStepMappingDao.insertCaseStepMappingGetKey(caseStepMapping);
			} else {
				caseStepMapping.setCaseStepMappingID(caseStepMapId);
				this.caseStepMappingDao.updateCaseStepMappingDao(caseStepMapping);
			}
			caseStepMapping.setCaseStepMappingID(caseStepMapId);
			insertedCSMappings.add(caseStepMapping);
		}
		testCase.setCaseStepMappings(insertedCSMappings);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMappingDetails(TestCase, int, int) - end"); //$NON-NLS-1$
		}
		return testCase;
	}

	/**
	 * Insert test step details.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @param appId
	 *            the app id
	 * @param featureId
	 *            the feature id
	 * @param conditionGrpId
	 *            the condition grp id
	 * @return the case step mapping
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private CaseStepMapping insertTestStepDetails(CaseStepMapping caseStepMapping, int appId, int featureId, int conditionGrpId)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepDetails(CaseStepMapping, int, int, int) - start"); //$NON-NLS-1$
		}

		TestStep testStep = caseStepMapping.getTestStep();
		testStep = insertActionsDetails(testStep);
		testStep.setActionID(testStep.getActions().getActionID());
		testStep.setPreConditionGroupID(conditionGrpId);
		testStep.setPostConditionGroupID(conditionGrpId);
		testStep.setInputParamGroupID(0);
		testStep.setOutputParamGroupID(0);
		String tsName = testStep.getStepName();
		if (tsName != null) {
			tsName = tsName + getRandom();
			testStep.setStepName(tsName);
		}
		testStep.setUpdatedBy(DataConstants.DEFAULT_USER);
		testStep.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		testStep.setCreatedBy(DataConstants.DEFAULT_USER);
		testStep.setCreatedDateTime(DataConstants.DEFAULT_DATE);
		int testStepId = this.testStepDao.insertTestStepGetKey(testStep);
		testStep.setTestStepID(testStepId);
		testStep = insertParamGroupDetails(testStep, appId, featureId);
		caseStepMapping.setTestStep(testStep);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepDetails(CaseStepMapping, int, int, int) - end"); //$NON-NLS-1$
		}
		return caseStepMapping;
	}

	/**
	 * Gets the random.
	 * 
	 * @return the random
	 */
	private static int getRandom() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRandom() - start"); //$NON-NLS-1$
		}

		int returnint = (int) (Math.random() * 10000);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRandom() - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * Insert param group details.
	 * 
	 * @param testStep
	 *            the test step
	 * @param appId
	 *            the app id
	 * @param featureId
	 *            the feature id
	 * @return the test step
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestStep insertParamGroupDetails(TestStep testStep, int appId, int featureId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroupDetails(TestStep, int, int) - start"); //$NON-NLS-1$
		}

		List<ParamGroup> insertedParamGrps = new ArrayList<ParamGroup>();
		List<ParamGroup> paramGroups = testStep.getParamGroup();
		Iterator<ParamGroup> iterator = paramGroups.iterator();
		while (iterator.hasNext()) {
			ParamGroup paramGroup = (ParamGroup) iterator.next();
			paramGroup.setAppID(appId);
			paramGroup = insertObjectGroupDetails(paramGroup, appId, featureId, testStep.getActions().getActionID());
			int objectGroupId = paramGroup.getObjectGroup().getObjectGroupID();
			int paramGroupId = 0;
			String pgName = paramGroup.getParamGroupName();
			if (pgName != null) {
				pgName = pgName + getRandom();
				paramGroup.setParamGroupName(pgName);
			}
			paramGroup.setObjectGroupID(objectGroupId);
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			paramGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			paramGroupId = this.paramGroupDao.insertParamGroupGetKey(paramGroup);
			paramGroup.setParamGroupID(paramGroupId);
			paramGroup = insertParamDetails(paramGroup, appId);
			testStep.setInputParamGroupID(paramGroupId);
			testStep.setOutputParamGroupID(paramGroupId);
			this.testStepDao.updateTestStep(testStep);
			insertedParamGrps.add(paramGroup);
		}
		testStep.setParamGroup(insertedParamGrps);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroupDetails(TestStep, int, int) - end"); //$NON-NLS-1$
		}
		return testStep;
	}

	/**
	 * Insert param details.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @param appId
	 *            the app id
	 * @return the param group
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private ParamGroup insertParamDetails(ParamGroup paramGroup, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamDetails(ParamGroup, int) - start"); //$NON-NLS-1$
		}

		List<Param> insertedParams = new ArrayList<Param>();
		List<Param> params = paramGroup.getParam();
		Iterator<Param> iterator = params.iterator();
		while (iterator.hasNext()) {
			Param param = (Param) iterator.next();
			int paramGroupId = paramGroup.getParamGroupID();
			param.setParamGroupID(paramGroupId);
			String paramName = param.getParamName();
			if (paramName != null && paramName.length() >= 500) {
				paramName = StringUtils.substring(paramName, -500);
			}
			String description = param.getDescription();
			if (description != null && description.length() >= 1000) {
				description = StringUtils.substring(description, -1000);
			}
			param.setDescription(description);
			param.setParamName(paramName);
			param.setParamGroup(paramGroup);
			int objectId = this.objectsDao.getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(paramGroup.getObjectGroup().getObjects().get(0));
			param.setObjectID(objectId);
			int paramId = this.paramDao.getParamIdByDependents(param);
			param.setUpdatedBy(DataConstants.DEFAULT_USER);
			param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (paramId == 0) {
				param.setCreatedBy(DataConstants.DEFAULT_USER);
				param.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				paramId = this.paramDao.insertParamGetKey(param);
			} else {
				param.setParamID(paramId);
				this.paramDao.updateParam(param);
			}
			param.setParamID(paramId);
			param = insertTestParamDataDetails(param, appId);
			insertedParams.add(param);
		}
		paramGroup.setParam(insertedParams);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamDetails(ParamGroup, int) - end"); //$NON-NLS-1$
		}
		return paramGroup;
	}

	/**
	 * Insert test param data details.
	 * 
	 * @param param
	 *            the param
	 * @param appId
	 *            the app id
	 * @return the param
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Param insertTestParamDataDetails(Param param, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamDataDetails(Param, int) - start"); //$NON-NLS-1$
		}

		TestParamData testParamData = param.getTestParamData();
		int paramId = param.getParamID();
		int paramGroupId = param.getParamGroupID();
		testParamData.setParamID(paramId);
		testParamData = updateTestData(testParamData, appId);
		int testDataId = testParamData.getTestData().getTestDataID();
		int testParamDataId = this.testParamDataDao.getTestParamDataIDByIDs(paramId, paramGroupId, testDataId);
		testParamData.setTestDataID(testDataId);
		testParamData.setUpdatedBy(DataConstants.DEFAULT_USER);
		testParamData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (testParamDataId == 0) {
			testParamData.setCreatedBy(DataConstants.DEFAULT_USER);
			testParamData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testParamDataId = this.testParamDataDao.insertTestParamDataGetKey(testParamData);
		} else {
			testParamData.setTestParamDataID(testParamDataId);
			this.testParamDataDao.updateTestParamData(testParamData);
		}
		testParamData.setTestParamDataID(testParamDataId);
		param.setTestParamData(testParamData);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamDataDetails(Param, int) - end"); //$NON-NLS-1$
		}
		return param;
	}

	/**
	 * Update test data.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @param appId
	 *            the app id
	 * @return the test param data
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestParamData updateTestData(TestParamData testParamData, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestData(TestParamData, int) - start"); //$NON-NLS-1$
		}

		TestData testData = testParamData.getTestData();
		int testDataId = this.testDataDao.getTestDataIdByAppIDAndDataSetDescription(appId, testData.getTestDataDescription());
		testData.setAppID(appId);
		testData.setUpdatedBy(DataConstants.DEFAULT_USER);
		testData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (testDataId == 0) {
			testData.setCreatedBy(DataConstants.DEFAULT_USER);
			testData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testDataId = this.testDataDao.insertTestDataGetKey(testData);
		} else {
			testData.setTestDataID(testDataId);
			this.testDataDao.updateTestData(testData);
		}
		testData.setTestDataID(testDataId);
		testParamData.setTestData(testData);

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestData(TestParamData, int) - end"); //$NON-NLS-1$
		}
		return testParamData;
	}

	/**
	 * Insert object group details.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @param appId
	 *            the app id
	 * @param featureId
	 *            the feature id
	 * @param actionsId
	 *            the actions id
	 * @return the param group
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private ParamGroup insertObjectGroupDetails(ParamGroup paramGroup, int appId, int featureId, int actionsId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroupDetails(ParamGroup, int, int, int) - start"); //$NON-NLS-1$
		}

		ObjectGroup objectGroup = paramGroup.getObjectGroup();
		objectGroup.setAppID(appId);
		String screenName = objectGroup.getScreenName();
		String objectGroupName = objectGroup.getObjectGroupName();
		if (screenName == null || screenName.isEmpty()) {
			screenName = "Sample_Screen";
		}
		if (objectGroupName == null || objectGroupName.isEmpty()) {
			objectGroupName = "Sample_Object_Group";
		}
		objectGroup.setObjectGroupName(objectGroupName);
		int screenId = this.screenDao.getScreenIDByNameAndFeatureID(screenName, featureId);
		if (screenId == 0) {
			Screen screen = new Screen();
			screen.setScreenName(screenName);
			screen.setFeatureID(featureId);
			screen.setAppID(appId);
			screen.setDescription(screenName + "_Description");
			screen.setCreatedBy(DataConstants.DEFAULT_USER);
			screen.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			screen.setUpdatedBy(DataConstants.DEFAULT_USER);
			screen.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			screenId = this.screenDao.insertScreenGetKey(screen);
		}
		int objectGroupId = this.objectGroupDao.getObjectGroupIDByNameAndScreenId(objectGroupName, screenId);
		objectGroup.setScreenID(screenId);
		objectGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
		objectGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (objectGroupId == 0) {
			objectGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			objectGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objectGroupId = this.objectGroupDao.insertObjectGroupGetKey(objectGroup);
		} else {
			objectGroup.setObjectGroupID(objectGroupId);
			this.objectGroupDao.updateObjectGroup(objectGroup);
		}
		objectGroup.setObjectGroupID(objectGroupId);
		objectGroup = insertObjectDetails(objectGroup, appId, actionsId);
		paramGroup.setObjectGroup(objectGroup);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroupDetails(ParamGroup, int, int, int) - end"); //$NON-NLS-1$
		}
		return paramGroup;
	}

	/**
	 * Insert object details.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @param appId
	 *            the app id
	 * @param actionsId
	 *            the actions id
	 * @return the object group
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private ObjectGroup insertObjectDetails(ObjectGroup objectGroup, int appId, int actionsId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectDetails(ObjectGroup, int, int) - start"); //$NON-NLS-1$
		}

		List<Objects> insertedObjects = new ArrayList<Objects>();
		List<Objects> objectsList = objectGroup.getObjects();
		Iterator<Objects> iterator = objectsList.iterator();
		while (iterator.hasNext()) {
			Objects objects = (Objects) iterator.next();
			objects = insertObjectTypeDetails(objects, actionsId);
			String identifierTypeName = objects.getIdentifierTypeValue();
			if (identifierTypeName == null) {
				identifierTypeName = "Sample_Identifier_Type_Name";
			}
			int identifierTypeId = this.identifierTypeDao.getIdentifierIdByNameAndAppId(identifierTypeName, appId);
			int objectTypeId = objects.getObjectType().getObjectTypeID();
			objects.setObjectTypeID(objectTypeId);
			objects.setAppID(appId);
			objects.setIdentifierTypeID(identifierTypeId);
			objects.setObjectGroupID(objectGroup.getObjectGroupID());
			int objectsId = this.objectsDao.getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(objects);
			objects.setUpdatedBy(DataConstants.DEFAULT_USER);
			objects.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (objectsId == 0) {
				objects.setCreatedBy(DataConstants.DEFAULT_USER);
				objects.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				objectsId = this.objectsDao.insertObjectsGetKey(objects);
			} else {
				objects.setObjectID(objectsId);
				this.objectsDao.updateObjects(objects);
			}
			objects.setObjectID(objectsId);
			insertedObjects.add(objects);
		}
		objectGroup.setObjects(insertedObjects);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectDetails(ObjectGroup, int, int) - end"); //$NON-NLS-1$
		}
		return objectGroup;
	}

	/**
	 * Insert object type details.
	 * 
	 * @param objects
	 *            the objects
	 * @param actionsId
	 *            the actions id
	 * @return the objects
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Objects insertObjectTypeDetails(Objects objects, int actionsId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectTypeDetails(Objects, int) - start"); //$NON-NLS-1$
		}

		ObjectType objectType = objects.getObjectType();
		int objectTypeId = this.objectTypeDao.getObjectTypeIdByNameAndActionId(actionsId, objectType.getObjectTypeName());
		objectType.setUpdatedBy(DataConstants.DEFAULT_USER);
		objectType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		objectType.setActionID(actionsId);
		if (objectTypeId == 0) {
			objectType.setCreatedBy(DataConstants.DEFAULT_USER);
			objectType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objectTypeId = this.objectTypeDao.insertObjectTypeGetKey(objectType);
		} else {
			objectType.setObjectTypeID(objectTypeId);
			this.objectTypeDao.updateObjectType(objectType);
		}
		objectType.setObjectTypeID(objectTypeId);
		objects.setObjectType(objectType);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectTypeDetails(Objects, int) - end"); //$NON-NLS-1$
		}
		return objects;
	}

	/**
	 * Insert actions details.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the test step
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestStep insertActionsDetails(TestStep testStep) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActionsDetails(TestStep) - start"); //$NON-NLS-1$
		}

		Actions actions = testStep.getActions();
		int actionsId = this.actionsDao.getActionsIdByName(actions.getActionName());
		actions.setUpdatedBy(DataConstants.DEFAULT_USER);
		actions.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (actionsId == 0) {
			actions.setCreatedBy(DataConstants.DEFAULT_USER);
			actions.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			actionsId = this.actionsDao.insertActionsGetKey(actions);
		} else {
			actions.setActionID(actionsId);
			this.actionsDao.updateActions(actions);
		}
		actions.setActionID(actionsId);
		testStep.setActions(actions);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActionsDetails(TestStep) - end"); //$NON-NLS-1$
		}
		return testStep;
	}

	/**
	 * Update app feature details.
	 * 
	 * @param testCase
	 *            the test case
	 * @param appId
	 *            the app id
	 * @return the test case
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestCase updateAppFeatureDetails(TestCase testCase, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFeatureDetails(TestCase, int) - start"); //$NON-NLS-1$
		}

		AppFeature appFeature = testCase.getAppFeature();
		AppFunctionality appFunctionality = testCase.getAppFunctionality();
		String feaName = appFeature.getFeatureName();
		String featureName = feaName;
		if (feaName != null && feaName.contains(":")) {
			featureName = StringUtils.substringAfter(feaName, ":");
		}
		LOG.info("feature name is : " + featureName);
		int funId = appFunctionality.getFunctionalID();
		int appFeatureId = this.appFeatureDao.getAppFeatureIdByName(featureName, funId);
		appFeature.setFunctionalID(funId);
		appFeature.setFeatureName(featureName);
		appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
		appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (appFeatureId == 0) {
			appFeature.setCreatedBy(DataConstants.DEFAULT_USER);
			appFeature.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			appFeatureId = this.appFeatureDao.insertFeatureGetKey(appFeature);
		} else {
			appFeature.setFeatureID(appFeatureId);
			this.appFeatureDao.updateAppFeature(appFeature);
		}
		appFeature.setFeatureID(appFeatureId);
		testCase.setAppFeature(appFeature);

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFeatureDetails(TestCase, int) - end"); //$NON-NLS-1$
		}
		return testCase;
	}

	/**
	 * Insert runner details.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the test case
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestCase insertRunnerDetails(TestCase testCase) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRunnerDetails(TestCase) - start"); //$NON-NLS-1$
		}

		Runner runner = testCase.getRunner();
		String runnerName = runner.getRunnerName();
		if (runnerName != null && runnerName.length() != 0) {
			int runnerId = this.runnerDao.getRunnerIDFromRunnerName(runnerName);
			runner.setUpdatedBy(DataConstants.DEFAULT_USER);
			runner.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (runnerId == 0) {
				runner.setCreatedBy(DataConstants.DEFAULT_USER);
				runner.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				runnerId = this.runnerDao.insertRunnerGetKey(runner);
			} else {
				runner.setRunnerID(runnerId);
				this.runnerDao.updateRunner(runner);
			}
			runner.setRunnerID(runnerId);
			testCase.setRunner(runner);

			if (LOG.isDebugEnabled()) {
				LOG.debug("insertRunnerDetails(TestCase) - end"); //$NON-NLS-1$
			}
			return testCase;
		} else {
			throw new DataAccessException("Runner name can not be null or empty. This may cause breaking the testcase from running through Runners");
		}
	}

	/**
	 * Insert condition group details.
	 * 
	 * @param testCase
	 *            the test case
	 * @param appId
	 *            the app id
	 * @return the test case
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestCase insertConditionGroupDetails(TestCase testCase, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionGroupDetails(TestCase, int) - start"); //$NON-NLS-1$
		}

		ConditionGroup conditionGroup = testCase.getConditionGroup();
		int conditionGroupId = this.conditionGroupDao.getConditionGroupIdByNameByAppId(appId, conditionGroup.getConditionGroupName());
		conditionGroup.setAppID(appId);
		conditionGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
		conditionGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (conditionGroupId == 0) {
			conditionGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			conditionGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			conditionGroupId = this.conditionGroupDao.insertConditionGroupGetKey(conditionGroup);
		} else {
			conditionGroup.setConditionGroupID(conditionGroupId);
			this.conditionGroupDao.updateConditionGroup(conditionGroup);
		}
		conditionGroup.setConditionGroupID(conditionGroupId);
		conditionGroup = insertConditionDetails(conditionGroup, appId);
		testCase.setConditionGroup(conditionGroup);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionGroupDetails(TestCase, int) - end"); //$NON-NLS-1$
		}
		return testCase;
	}

	/**
	 * Insert condition details.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @param appId
	 *            the app id
	 * @return the condition group
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private ConditionGroup insertConditionDetails(ConditionGroup conditionGroup, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionDetails(ConditionGroup, int) - start"); //$NON-NLS-1$
		}

		List<Conditions> insertedConditions = new ArrayList<Conditions>();
		List<Conditions> conditionsList = conditionGroup.getConditions();
		Iterator<Conditions> iterator = conditionsList.iterator();
		while (iterator.hasNext()) {
			Conditions conditions = (Conditions) iterator.next();
			int conditionGrpId = conditionGroup.getConditionGroupID();
			int conditionsId = this.conditionsDao.getConditionIdByNameByConditionGroupId(conditions.getConditionName(), conditionGrpId);
			conditions.setConditionGroupID(conditionGrpId);
			conditions.setUpdatedBy(DataConstants.DEFAULT_USER);
			conditions.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (conditionsId == 0) {
				conditions.setCreatedBy(DataConstants.DEFAULT_USER);
				conditions.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				conditionsId = this.conditionsDao.insertConditionsGetKey(conditions);
			} else {
				conditions.setConditionID(conditionsId);
				this.conditionsDao.updateConditions(conditions);
			}
			conditions.setConditionID(conditionsId);
			conditions = insertTestConditionDataDetails(conditions, appId);
			insertedConditions.add(conditions);
		}
		conditionGroup.setConditions(insertedConditions);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionDetails(ConditionGroup, int) - end"); //$NON-NLS-1$
		}
		return conditionGroup;
	}

	/**
	 * Insert test condition data details.
	 * 
	 * @param conditions
	 *            the conditions
	 * @param appId
	 *            the app id
	 * @return the conditions
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Conditions insertTestConditionDataDetails(Conditions conditions, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestConditionDataDetails(Conditions, int) - start"); //$NON-NLS-1$
		}

		List<TestConditionData> insertedConditionDatas = new ArrayList<TestConditionData>();
		List<TestConditionData> testConditionDatas = conditions.getTestConditionData();
		Iterator<TestConditionData> iterator = testConditionDatas.iterator();
		while (iterator.hasNext()) {
			TestConditionData testConditionData = (TestConditionData) iterator.next();
			testConditionData = updateTestConditionDataDetails(testConditionData, appId);
			int testDataId = testConditionData.getTestData().getTestDataID();
			int conditionId = conditions.getConditionID();
			int testConditionDataId = this.testConditionDataDao.getTestConditionIdByDependentIds(testDataId, conditionId);
			testConditionData.setConditionID(conditionId);
			testConditionData.setTestDataID(testDataId);
			testConditionData.setConditionGroupID(conditions.getConditionGroupID());
			testConditionData.setUpdatedBy(DataConstants.DEFAULT_USER);
			testConditionData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (testConditionDataId == 0) {
				testConditionData.setCreatedBy(DataConstants.DEFAULT_USER);
				testConditionData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				testConditionDataId = this.testConditionDataDao.insertTestConditionDataGetKey(testConditionData);
			} else {
				testConditionData.setTestConditionDataID(testConditionDataId);
				this.testConditionDataDao.updateTestConditionData(testConditionData);
			}
			testConditionData.setTestConditionDataID(testConditionDataId);
			insertedConditionDatas.add(testConditionData);
		}
		conditions.setTestConditionData(insertedConditionDatas);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestConditionDataDetails(Conditions, int) - end"); //$NON-NLS-1$
		}
		return conditions;
	}

	/**
	 * Update test condition data details.
	 * 
	 * @param testConditionData
	 *            the test condition data
	 * @param appId
	 *            the app id
	 * @return the test condition data
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestConditionData updateTestConditionDataDetails(TestConditionData testConditionData, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestConditionDataDetails(TestConditionData, int) - start"); //$NON-NLS-1$
		}

		TestData testData = testConditionData.getTestData();
		int testDataId = this.testDataDao.getTestDataIdByAppIDAndDataSetDescription(appId, testData.getTestDataDescription());
		testData.setAppID(appId);
		testData.setUpdatedBy(DataConstants.DEFAULT_USER);
		testData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (testDataId == 0) {
			testData.setCreatedBy(DataConstants.DEFAULT_USER);
			testData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testDataId = this.testDataDao.insertTestDataGetKey(testData);
		} else {
			testData.setTestDataID(testDataId);
			this.testDataDao.updateTestData(testData);
		}
		testData.setTestDataID(testDataId);
		testConditionData.setTestData(testData);

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestConditionDataDetails(TestConditionData, int) - end"); //$NON-NLS-1$
		}
		return testConditionData;
	}

	/**
	 * Insert test suite details.
	 * 
	 * @param psMapping
	 *            the ps mapping
	 * @param appId
	 *            the app id
	 * @return the plan suite mapping
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private PlanSuiteMapping insertTestSuiteDetails(PlanSuiteMapping psMapping, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteDetails(PlanSuiteMapping, int) - start"); //$NON-NLS-1$
		}

		TestSuite testSuite = psMapping.getTestSuite();
		int testSuiteId = this.testSuiteDao.getTestSuiteIdByNameByAppId(appId, testSuite.getSuiteName());
		testSuite.setAppID(appId);
		testSuite.setUpdatedBy(DataConstants.DEFAULT_USER);
		testSuite.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		if (testSuiteId == 0) {
			testSuite.setCreatedBy(DataConstants.DEFAULT_USER);
			testSuite.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testSuiteId = this.testSuiteDao.insertTestSuiteGetKey(testSuite);
		} else {
			testSuite.setTestSuiteID(testSuiteId);
			this.testSuiteDao.updateTestSuite(testSuite);
		}
		testSuite.setTestSuiteID(testSuiteId);
		psMapping.setTestSuite(testSuite);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteDetails(PlanSuiteMapping, int) - end"); //$NON-NLS-1$
		}
		return psMapping;
	}

	/**
	 * Insert test data details.
	 * 
	 * @param application
	 *            the application
	 * @return the application
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Application insertTestDataDetails(Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestDataDetails(Application) - start"); //$NON-NLS-1$
		}

		List<TestData> insertedDatas = new ArrayList<TestData>();
		List<TestData> testDatas = application.getTestData();
		Iterator<TestData> iterator = testDatas.iterator();
		while (iterator.hasNext()) {
			TestData testData = (TestData) iterator.next();
			int appId = application.getAppID();
			int testDataId = this.testDataDao.getTestDataIdByAppIDAndDataSetDescription(appId, testData.getTestDataDescription());
			testData.setAppID(appId);
			testData.setUpdatedBy(DataConstants.DEFAULT_USER);
			testData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (testDataId == 0) {
				testData.setCreatedBy(DataConstants.DEFAULT_USER);
				testData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				testDataId = this.testDataDao.insertTestDataGetKey(testData);
			} else {
				testData.setTestDataID(testDataId);
				this.testDataDao.updateTestData(testData);
			}
			testData.setTestDataID(testDataId);
			insertedDatas.add(testData);
		}
		application.setTestData(insertedDatas);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestDataDetails(Application) - end"); //$NON-NLS-1$
		}
		return application;
	}

	/**
	 * Insert identifier type details.
	 * 
	 * @param application
	 *            the application
	 * @return the application
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Application insertIdentifierTypeDetails(Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertIdentifierTypeDetails(Application) - start"); //$NON-NLS-1$
		}

		List<IdentifierType> insertedTypes = new ArrayList<IdentifierType>();
		List<IdentifierType> identifierTypes = application.getIdentifierType();
		Iterator<IdentifierType> iterator = identifierTypes.iterator();
		while (iterator.hasNext()) {
			IdentifierType identifierType = (IdentifierType) iterator.next();
			int appId = application.getAppID();
			int identifierTypeId = this.identifierTypeDao.getIdentifierIdByNameAndAppId(identifierType.getIdentifierTypeName(), appId);
			identifierType.setAppID(appId);
			identifierType.setUpdatedBy(DataConstants.DEFAULT_USER);
			identifierType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (identifierTypeId == 0) {
				identifierType.setCreatedBy(DataConstants.DEFAULT_USER);
				identifierType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				identifierTypeId = this.identifierTypeDao.insertIdentifierTypeGetKey(identifierType);
			} else {
				identifierType.setIdentifierTypeID(identifierTypeId);
				this.identifierTypeDao.updateIdentifierType(identifierType);
			}
			identifierType.setIdentifierTypeID(identifierTypeId);
			insertedTypes.add(identifierType);
		}
		application.setIdentifierType(insertedTypes);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertIdentifierTypeDetails(Application) - end"); //$NON-NLS-1$
		}
		return application;
	}

	/**
	 * Insert application details.
	 * 
	 * @param application
	 *            the application
	 * @return the application
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Application insertApplicationDetails(Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplicationDetails(Application) - start"); //$NON-NLS-1$
		}

		String appName = application.getAppName();
		String applicationName = appName;
		if (appName != null && appName.contains(":")) {
			applicationName = StringUtils.substringAfter(appName, ":");
		}
		LOG.info("application name is : " + applicationName);
		int appId = this.applicationDao.getAppIdByAppName(applicationName);
		application.setAppName(applicationName);
		application.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
		application.setUpdatedBy(DataConstants.DEFAULT_USER);
		if (appId == 0) {
			application.setCreatedBy(DataConstants.DEFAULT_USER);
			application.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			appId = this.applicationDao.insertApplicationGetKey(application);
			LOG.info("application details were inserted with id : " + appId);
		} else {
			application.setAppID(appId);
			this.applicationDao.updateApplication(application);
			LOG.info("application details were updated with id : " + appId);
		}
		application.setAppID(appId);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplicationDetails(Application) - end"); //$NON-NLS-1$
		}
		return application;
	}

	/**
	 * Insert app functionality details.
	 * 
	 * @param application
	 *            the application
	 * @return the application
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private Application insertAppFunctionalityDetails(Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionalityDetails(Application) - start"); //$NON-NLS-1$
		}

		List<AppFunctionality> insertedFunctionalities = new ArrayList<AppFunctionality>();
		List<AppFunctionality> appFunctionalities = application.getAppFunctionality();
		Iterator<AppFunctionality> iterator = appFunctionalities.iterator();
		while (iterator.hasNext()) {
			AppFunctionality appFunctionality = (AppFunctionality) iterator.next();
			String funName = appFunctionality.getFunctionalName();
			String functionName = funName;
			if (funName != null && funName.contains(":")) {
				functionName = StringUtils.substringAfter(funName, ":");
			}
			LOG.info("functional name is : " + functionName);
			int appId = application.getAppID();
			appFunctionality.setAppID(appId);
			int appFunctionalId = this.appFunctionalityDao.getAppFunctionalityIdByName(appId, functionName);
			appFunctionality.setFunctionalName(functionName);
			appFunctionality.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFunctionality.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (appFunctionalId == 0) {
				appFunctionality.setCreatedBy(DataConstants.DEFAULT_USER);
				appFunctionality.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				appFunctionalId = this.appFunctionalityDao.insertAppFunctionalityGetKey(appFunctionality);
				LOG.info("appFunctionality details were inserted with id : " + appFunctionalId);
			} else {
				appFunctionality.setFunctionalID(appFunctionalId);
				this.appFunctionalityDao.updateAppFunctionality(appFunctionality);
				LOG.info("appFunctionality details were updated with id : " + appFunctionalId);
			}
			appFunctionality.setFunctionalID(appFunctionalId);
			appFunctionality = insertAppFeatureDetails(appFunctionality, appId);
			insertedFunctionalities.add(appFunctionality);
		}
		application.setAppFunctionality(insertedFunctionalities);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionalityDetails(Application) - end"); //$NON-NLS-1$
		}
		return application;
	}

	/**
	 * Insert app feature details.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @param appId
	 *            the app id
	 * @return the app functionality
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private AppFunctionality insertAppFeatureDetails(AppFunctionality appFunctionality, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFeatureDetails(AppFunctionality, int) - start"); //$NON-NLS-1$
		}

		List<AppFeature> insertedFeatures = new ArrayList<AppFeature>();
		List<AppFeature> appFeatures = appFunctionality.getAppFeature();
		Iterator<AppFeature> iterator = appFeatures.iterator();
		while (iterator.hasNext()) {
			AppFeature appFeature = (AppFeature) iterator.next();
			String feaName = appFeature.getFeatureName();
			String featureName = feaName;
			if (feaName != null && feaName.contains(":")) {
				featureName = StringUtils.substringAfter(feaName, ":");
			}
			LOG.info("feature name is : " + featureName);
			int funId = appFunctionality.getFunctionalID();
			int appFeatureId = this.appFeatureDao.getAppFeatureIdByName(featureName, funId);
			appFeature.setFunctionalID(funId);
			appFeature.setFeatureName(featureName);
			appFeature.setUpdatedBy(DataConstants.DEFAULT_USER);
			appFeature.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (appFeatureId == 0) {
				appFeature.setCreatedBy(DataConstants.DEFAULT_USER);
				appFeature.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				appFeatureId = this.appFeatureDao.insertFeatureGetKey(appFeature);
			} else {
				appFeature.setFeatureID(appFeatureId);
				this.appFeatureDao.updateAppFeature(appFeature);
			}
			appFeature.setFeatureID(appFeatureId);
			appFeature = insertScreenDetails(appFeature, appId);
			insertedFeatures.add(appFeature);
		}
		appFunctionality.setAppFeature(insertedFeatures);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFeatureDetails(AppFunctionality, int) - end"); //$NON-NLS-1$
		}
		return appFunctionality;
	}

	/**
	 * Insert screen details.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @param appId
	 *            the app id
	 * @return the app feature
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private AppFeature insertScreenDetails(AppFeature appFeature, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreenDetails(AppFeature, int) - start"); //$NON-NLS-1$
		}

		List<Screen> insertedScreens = new ArrayList<Screen>();
		List<Screen> screens = appFeature.getScreen();
		Iterator<Screen> iterator = screens.iterator();
		while (iterator.hasNext()) {
			Screen screen = (Screen) iterator.next();
			int featureId = appFeature.getFeatureID();
			int screenId = this.screenDao.getScreenIDByNameAndFeatureID(screen.getScreenName(), featureId);
			screen.setAppID(appId);
			screen.setFeatureID(featureId);
			screen.setUpdatedBy(DataConstants.DEFAULT_USER);
			screen.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			if (screenId == 0) {
				screen.setCreatedBy(DataConstants.DEFAULT_USER);
				screen.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				screenId = this.screenDao.insertScreenGetKey(screen);
			} else {
				screen.setScreenID(screenId);
				this.screenDao.updateScreen(screen);
			}
			screen.setScreenID(screenId);
			insertedScreens.add(screen);
		}
		appFeature.setScreen(insertedScreens);

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreenDetails(AppFeature, int) - end"); //$NON-NLS-1$
		}
		return appFeature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerWithAllData(int)
	 */
	@Override
	public Scheduler getSchedulerWithAllData(int schedulerId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerWithAllData(int) - start"); //$NON-NLS-1$
		}

		Scheduler scheduler = null;
		try {
			scheduler = (Scheduler) mapCacheUtility.get(String.valueOf(schedulerId));
		} catch (CacheException e) {
			LOG.error("getSchedulerWithAllData(int)", e); //$NON-NLS-1$

			try {
				scheduler = this.schedulerDao.getSchedulerById(schedulerId);
				int testPlanId = scheduler.getTestPlanID();
				int testDataId = scheduler.getTestDataID();
				List<PlanSuiteMapping> planSuiteMappings = getTestPlanDependents(testPlanId, testDataId);
				TestPlan testPlan = this.testPlanDao.getTestPlanById(testPlanId);
				TestData testData = this.testDataDao.getTestDataById(testDataId);
				testPlan.setPlanSuiteMappings(planSuiteMappings);
				LOG.info("testPlan ==> " + testPlan);
				scheduler.setTestData(testData);
				scheduler.setTestPlan(testPlan);
				try {
					mapCacheUtility.add(String.valueOf(scheduler.getScheduleID()), scheduler);
				} catch (CacheException e1) {
					LOG.error("getSchedulerWithAllData(int)", e1); //$NON-NLS-1$

					throw new ServiceException(e1.getMessage());
				}
			} catch (DataAccessException e1) {
				LOG.error("getSchedulerWithAllData(int)", e1); //$NON-NLS-1$

				throw new ServiceException(e1.getMessage());
			}
		}
		LOG.info("scheduler ==> " + scheduler);
		return scheduler;
	}

	/**
	 * Gets the test plan dependents.
	 * 
	 * @param testPlanId
	 *            the test plan id
	 * @param testDataId
	 *            the test data id
	 * @return the test plan dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private List<PlanSuiteMapping> getTestPlanDependents(int testPlanId, int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanDependents(int, int) - start"); //$NON-NLS-1$
		}

		List<PlanSuiteMapping> resultPSMappings = new ArrayList<PlanSuiteMapping>();
		List<PlanSuiteMapping> planSuiteMappings = this.planSuiteMappingDao.getPlanSuiteMappingByPlanId(testPlanId);
		Iterator<PlanSuiteMapping> iterator = planSuiteMappings.iterator();
		while (iterator.hasNext()) {
			PlanSuiteMapping planSuiteMapping = (PlanSuiteMapping) iterator.next();
			TestSuite testSuite = this.testSuiteDao.getTestSuiteById(planSuiteMapping.getTestSuiteID());
			testSuite = getTestSuiteDependents(testSuite, testDataId);
			planSuiteMapping.setTestSuite(testSuite);
			resultPSMappings.add(planSuiteMapping);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanDependents(int, int) - end"); //$NON-NLS-1$
		}
		return resultPSMappings;
	}

	/**
	 * Gets the test suite dependents.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @param testDataId
	 *            the test data id
	 * @return the test suite dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private TestSuite getTestSuiteDependents(TestSuite testSuite, int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteDependents(TestSuite, int) - start"); //$NON-NLS-1$
		}

		List<SuiteScenarioMapping> resultSuiteScenarioMappings = new ArrayList<SuiteScenarioMapping>();
		List<SuiteScenarioMapping> suiteScenarioMappings = this.suiteScenarioMappingDao.getSuiteScenarioMappingBySuiteId(testSuite.getTestSuiteID());
		Iterator<SuiteScenarioMapping> ssMappingItr = suiteScenarioMappings.iterator();
		while (ssMappingItr.hasNext()) {
			SuiteScenarioMapping suiteScenarioMapping = (SuiteScenarioMapping) ssMappingItr.next();
			TestScenario testScenario = suiteScenarioMapping.getTestScenario();
			List<ScenarioCaseMapping> scenarioCaseMappings = getTestScenarioDependents(testScenario.getTestScenarioID(), testDataId);
			testScenario.setScenarioCaseMappings(scenarioCaseMappings);
			suiteScenarioMapping.setTestScenario(testScenario);
			resultSuiteScenarioMappings.add(suiteScenarioMapping);
		}
		LOG.info("resultSuiteScenarioMappings ==> " + resultSuiteScenarioMappings);
		testSuite.setSuiteScenarioMappings(resultSuiteScenarioMappings);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteDependents(TestSuite, int) - end"); //$NON-NLS-1$
		}
		return testSuite;
	}

	/**
	 * Gets the test scenario dependents.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @param testDataId
	 *            the test data id
	 * @return the test scenario dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@SuppressWarnings("unchecked")
	private List<ScenarioCaseMapping> getTestScenarioDependents(int testScenarioId, int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioDependents(int, int) - start"); //$NON-NLS-1$
		}

		List<ScenarioCaseMapping> resultScenarioCaseMappings = new ArrayList<ScenarioCaseMapping>();
		List<ScenarioCaseMapping> scenarioCaseMappings = this.scenarioCaseMappingDao.getScenarioCaseMappingsByScenarioId(testScenarioId);
		Iterator<ScenarioCaseMapping> scMappingItr = scenarioCaseMappings.iterator();
		while (scMappingItr.hasNext()) {
			ScenarioCaseMapping scenarioCaseMapping = (ScenarioCaseMapping) scMappingItr.next();
			TestCase testCase = scenarioCaseMapping.getTestCase();
			ConditionGroup conditionGroup = this.conditionGroupDao.getConditionGroupById(testCase.getConditionGroupID());
			if (conditionGroup != null) {
				int conditionGroupId = conditionGroup.getConditionGroupID();
				List<Conditions> conditions = null;
				try {
					conditions = (List<Conditions>) mapCacheUtility.get(String.valueOf(conditionGroupId));
				} catch (Exception e) {
					LOG.error("getTestScenarioDependents(int, int)", e); //$NON-NLS-1$

					try {
						conditions = this.conditionsDao.getConditionByConditionGroupId(conditionGroupId);
						mapCacheUtility.add(String.valueOf(conditionGroupId), conditions);
					} catch (CacheException e1) {
						LOG.error("getTestScenarioDependents(int, int)", e1); //$NON-NLS-1$

						throw new DataAccessException(e1.getMessage());
					}
				}
				conditionGroup.setConditions(conditions);
				List<TestConditionData> testConditionDatas = null;
				try {
					testConditionDatas = (List<TestConditionData>) mapCacheUtility.get(String.valueOf(conditionGroupId) + "TCD");
				} catch (Exception e) {
					LOG.error("getTestScenarioDependents(int, int)", e); //$NON-NLS-1$

					try {
						testConditionDatas = this.testConditionDataDao.getTestConditionDataByConditionGroupId(conditionGroupId);
						mapCacheUtility.add(String.valueOf(conditionGroupId) + "TCD", testConditionDatas);
					} catch (CacheException e1) {
						LOG.error("getTestScenarioDependents(int, int)", e1); //$NON-NLS-1$

						throw new DataAccessException(e1.getMessage());
					}
				}
				conditionGroup.setTestConditionData(testConditionDatas);
				testCase.setConditionGroup(conditionGroup);
			}
			Runner runner = this.runnerDao.getRunnerById(testCase.getRunnerID());
			testCase.setRunner(runner);
			List<CaseStepMapping> resultCaseStepMappings = new ArrayList<CaseStepMapping>();
			List<CaseStepMapping> caseStepMappings = this.caseStepMappingDao.getCaseStepMappingsByCaseId(testCase.getTestCaseID());
			Iterator<CaseStepMapping> csMappingItr = caseStepMappings.iterator();
			while (csMappingItr.hasNext()) {
				CaseStepMapping caseStepMapping = (CaseStepMapping) csMappingItr.next();
				TestStep testStep = caseStepMapping.getTestStep();
				Actions actions = this.actionsDao.getActionsById(testStep.getActionID());
				if (actions != null) {
					List<ObjectType> objectTypes = this.objectTypeDao.getObjectTypeByActionId(actions.getActionID());
					actions.setObjectType(objectTypes);
					testStep.setActions(actions);
				}
				List<ParamGroup> paramGrpupsToRun = getTestStepDependents(testStep, testDataId);
				testStep.setParamGroup(paramGrpupsToRun);
				LOG.info("testStep ==> " + testStep);
				caseStepMapping.setTestStep(testStep);
				resultCaseStepMappings.add(caseStepMapping);
			}
			testCase.setCaseStepMappings(resultCaseStepMappings);
			scenarioCaseMapping.setTestCase(testCase);
			resultScenarioCaseMappings.add(scenarioCaseMapping);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioDependents(int, int) - end"); //$NON-NLS-1$
		}
		return resultScenarioCaseMappings;
	}

	/**
	 * Gets the test step dependents.
	 * 
	 * @param testStep
	 *            the test step
	 * @param testDataId
	 *            the test data id
	 * @return the test step dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private List<ParamGroup> getTestStepDependents(TestStep testStep, int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepDependents(TestStep, int) - start"); //$NON-NLS-1$
		}

		List<TestParamData> testParamDatas = this.testParamDataDao.getTestParamDataByTestDataId(testDataId);
		Set<Integer> paramGroupIds = new HashSet<Integer>();
		List<ParamGroup> paramGroups = new ArrayList<ParamGroup>();
		Iterator<TestParamData> testParamDataItr = testParamDatas.iterator();
		while (testParamDataItr.hasNext()) {
			TestParamData testParamData = (TestParamData) testParamDataItr.next();
			int paramId = testParamData.getParamID();
			Param param = null;
			try {
				param = (Param) mapCacheUtility.get(String.valueOf(paramId));
			} catch (Exception e) {
				LOG.error("getTestStepDependents(TestStep, int)", e); //$NON-NLS-1$

				try {
					param = this.paramDao.getParamById(paramId);
					mapCacheUtility.add(String.valueOf(paramId), param);
				} catch (CacheException e1) {
					LOG.error("getTestStepDependents(TestStep, int)", e1); //$NON-NLS-1$

					throw new DataAccessException(e1.getMessage());
				}
			}
			int paramGroupId = param.getParamGroupID();
			if (!paramGroupIds.contains(paramGroupId)) {
				paramGroupIds.add(paramGroupId);
			}
		}
		int stepParamGroupId = testStep.getInputParamGroupID();
		if (paramGroupIds.contains(stepParamGroupId)) {
			paramGroups.add(this.paramGroupDao.getParamGroupById(stepParamGroupId));
		} else {
			paramGroups.add(this.paramGroupDao.getParamGroupById(stepParamGroupId));
		}
		LOG.info("paramGroups before union ==> " + paramGroups);
		return getParamGroupDependents(paramGroups, testDataId);
	}

	/**
	 * Gets the param group dependents.
	 * 
	 * @param uniqueParamGroups
	 *            the unique param groups
	 * @param testDataId
	 *            the test data id
	 * @return the param group dependents
	 * @throws DataAccessException
	 *             the data access exception
	 */
	private List<ParamGroup> getParamGroupDependents(List<ParamGroup> uniqueParamGroups, int testDataId) throws DataAccessException {
		LOG.info("received uniqueParamGroups ==> " + uniqueParamGroups);
		List<ParamGroup> paramGrpupsToRun = new ArrayList<ParamGroup>();
		Iterator<ParamGroup> uniquePgItr = uniqueParamGroups.iterator();
		while (uniquePgItr.hasNext()) {
			ParamGroup paramGroup = (ParamGroup) uniquePgItr.next();
			ObjectGroup objectGroup = this.objectGroupDao.getObjectGroupById(paramGroup.getObjectGroupID());
			List<Objects> objectsList = new ArrayList<Objects>();
			List<Objects> objects = this.objectsDao.getObjectsByGroupId(objectGroup.getObjectGroupID());
			LOG.info("objects ==> " + objects);
			Iterator<Objects> objIterator = objects.iterator();
			while (objIterator.hasNext()) {
				Objects objects2 = (Objects) objIterator.next();
				int identifierTypeId = objects2.getIdentifierTypeID();
				IdentifierType identifierType = null;
				try {
					identifierType = (IdentifierType) mapCacheUtility.get(String.valueOf(identifierTypeId));
				} catch (Exception e) {
					LOG.error("getParamGroupDependents(List<ParamGroup>, int)", e); //$NON-NLS-1$

					try {
						identifierType = this.identifierTypeDao.getIdentifierTypeById(identifierTypeId);
						mapCacheUtility.add(String.valueOf(identifierTypeId), identifierType);
					} catch (Exception e1) {
						LOG.error("getParamGroupDependents(List<ParamGroup>, int)", e1); //$NON-NLS-1$

						throw new DataAccessException(e1.getMessage());
					}
				}
				objects2.setIdentifierType(identifierType);
				objectsList.add(objects2);
			}
			objects.clear();
			objectGroup.setObjects(objectsList);
			paramGroup.setObjectGroup(objectGroup);
			List<Param> paramList = new ArrayList<Param>();
			List<Param> params = this.paramDao.getParamByParamGroupId(paramGroup.getParamGroupID());
			Iterator<Param> paramItr = params.iterator();
			while (paramItr.hasNext()) {
				Param param = (Param) paramItr.next();
				TestParamData testParamData = this.testParamDataDao
						.getTestParamDataByParamId(param.getParamID(), paramGroup.getParamGroupID(), testDataId);
				Objects objParam = this.objectsDao.getObjectsById(param.getObjectID());
				param.setObjects(objParam);
				param.setTestParamData(testParamData);
				paramList.add(param);
			}
			params.clear();
			LOG.info("params ==> " + paramList);
			paramGroup.setParam(paramList);
			LOG.info("paramGroup ==> " + paramGroup);
			paramGrpupsToRun.add(paramGroup);
		}
		LOG.info("paramGrpupsToRun ==> " + paramGrpupsToRun);
		return paramGrpupsToRun;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getAppFeatureFilterByAppFunctionalityID
	 * (int)
	 */
	public List<AppFunctionality> getAppFeatureFilterByAppFunctionalityID(int appFeatureID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureFilterByAppFunctionalityID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.appFeatureDao.getAppFeatureFilterByAppFunctionalityID(appFeatureID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeatureFilterByAppFunctionalityID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFeatureFilterByAppFunctionalityID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getAppFunctionalityFilterByAppId(int)
	 */
	public List<AppFunctionality> getAppFunctionalityFilterByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.appFunctionalityDao.getAppFunctionalityFilterByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFunctionalityFilterByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFunctionalityFilterByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getScreenFilterByAppFeatureId(int)
	 */
	public AppFeature getScreenFilterByAppFeatureId(int screenId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenFilterByAppFeatureId(int) - start"); //$NON-NLS-1$
		}

		try {
			AppFeature returnAppFeature = this.screenDao.getScreenFilterByAppFeatureId(screenId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreenFilterByAppFeatureId(int) - end"); //$NON-NLS-1$
			}
			return returnAppFeature;
		} catch (DataAccessException e) {
			LOG.error("getScreenFilterByAppFeatureId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertSchedulerRunDetails(com.exilant
	 * .tfw.pojo.SchedulerRunDetails)
	 */
	@Override
	public long insertSchedulerRunDetails(SchedulerRunDetails schedulerRunDetails) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerRunDetails(SchedulerRunDetails) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerRunDetailsDao.insertSchedulerRunDetails(schedulerRunDetails);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSchedulerRunDetails(SchedulerRunDetails) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertSchedulerRunDetails(SchedulerRunDetails)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateSchedulerRunDetails(com.exilant
	 * .tfw.pojo.SchedulerRunDetails)
	 */
	@Override
	public long updateSchedulerRunDetails(SchedulerRunDetails schedulerRunDetails) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerRunDetails(SchedulerRunDetails) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerRunDetailsDao.updateSchedulerRunDetails(schedulerRunDetails);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerRunDetails(SchedulerRunDetails) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateSchedulerRunDetails(SchedulerRunDetails)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertScheduler(com.sssoft.isatt.data.pojo
	 * .Scheduler)
	 */
	@Override
	public long insertScheduler(Scheduler scheduler) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScheduler(Scheduler) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = schedulerDao.insertScheduler(scheduler);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertScheduler(Scheduler) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertScheduler(Scheduler)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertSchedulerGetKey(com.exilant
	 * .tfw.pojo.Scheduler)
	 */
	public long insertSchedulerGetKey(Scheduler scheduler) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerGetKey(Scheduler) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = schedulerDao.insertSchedulerGetKey(scheduler);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSchedulerGetKey(Scheduler) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertSchedulerGetKey(Scheduler)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertSchedulerLaneDetails(com.exilant
	 * .tfw.pojo.SchedulerLaneDetails)
	 */
	@Override
	public long insertSchedulerLaneDetails(SchedulerLaneDetails schedulerLaneDetails) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerLaneDetails(SchedulerLaneDetails) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = schedulerLaneDetailsDao.insertSchedulerLaneDetails(schedulerLaneDetails);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSchedulerLaneDetails(SchedulerLaneDetails) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertSchedulerLaneDetails(SchedulerLaneDetails)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertSchedulerRunDetailsGetKey(com
	 * .exilant.tfw.pojo.SchedulerRunDetails)
	 */
	@Override
	public long insertSchedulerRunDetailsGetKey(SchedulerRunDetails schedulerRunDetails) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerRunDetailsGetKey(SchedulerRunDetails) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = schedulerRunDetailsDao.insertSchedulerRunDetailsGetKey(schedulerRunDetails);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSchedulerRunDetailsGetKey(SchedulerRunDetails) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("insertSchedulerRunDetailsGetKey(SchedulerRunDetails)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAllScreens()
	 */
	@Override
	public List<Screen> getAllScreens() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllScreens() - start"); //$NON-NLS-1$
		}

		try {
			List<Screen> returnList = this.screenDao.getAllScreens();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllScreens() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllScreens()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getScreensByAppIdAndFeatureID(int,
	 * int)
	 */
	@Override
	public List<Screen> getScreensByAppIdAndFeatureID(int appId, int featureId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreensByAppIdAndFeatureID(int, int) - start"); //$NON-NLS-1$
		}

		try {
			List<Screen> returnList = this.screenDao.getScreensByAppIdAndFeatureID(appId, featureId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreensByAppIdAndFeatureID(int, int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScreensByAppIdAndFeatureID(int, int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertApplicationGetKey(com.exilant
	 * .tfw.pojo.Application)
	 */
	@Override
	public int insertApplicationGetKey(Application application) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplicationGetKey(Application) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = applicationDao.insertApplicationGetKey(application);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertApplicationGetKey(Application) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertApplicationGetKey(Application)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * Insert screen.
	 * 
	 * @param screen
	 *            the screen
	 * @param featureId
	 *            the feature id
	 * @param appId
	 *            the app id
	 * @return the screen
	 * @throws DataAccessException
	 *             the data access exception
	 * @throws ServiceException
	 *             the service exception
	 */
	public Screen insertScreen(Screen screen, int featureId, int appId) throws DataAccessException, ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreen(Screen, int, int) - start"); //$NON-NLS-1$
		}

		String screenName = screen.getScreenName();
		if (screenName != null && screenName.contains(":")) {
			screenName = StringUtils.substringAfter(screenName, ":");
		}
		LOG.info("feature name is : " + screenName);
		int screenId = 0;
		try {
			screenId = this.screenDao.getScreenIDByNameAndFeatureID(screenName, featureId);
		} catch (DataAccessException e) {
			LOG.error("insertScreen(Screen, int, int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
		LOG.info("screenId ID is : " + screenId);

		screen.setScreenName(screenName);
		screen.setUpdatedBy(System.getenv(DataConstants.DEFAULT_USER));
		screen.setUpdatedDateTime(new Date(System.currentTimeMillis()));
		screen.setFeatureID(featureId);
		screen.setAppID(appId);
		screen.setDescription("Screen Test");
		if (screenId == 0) {
			screen.setCreatedBy(System.getenv(DataConstants.DEFAULT_USER));
			screen.setCreatedDateTime(new Date(System.currentTimeMillis()));
			try {
				screenId = (int) this.screenDao.insertScreenGetKey(screen);
			} catch (DataAccessException e) {
				LOG.error("insertScreen(Screen, int, int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
			LOG.info("Screen details were inserted with id : " + screenId);
		} else {
			try {
				this.screenDao.updateScreen(screen);
				LOG.info("Screen details were updated with id : " + screenId);
			} catch (DataAccessException e) {
				LOG.error("insertScreen(Screen, int, int)", e); //$NON-NLS-1$

				throw new ServiceException(e.getMessage());
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreen(Screen, int, int) - end"); //$NON-NLS-1$
		}
		return screen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getApplicationById(int)
	 */
	@Override
	public Application getApplicationById(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationById(int) - start"); //$NON-NLS-1$
		}

		try {
			Application returnApplication = applicationDao.getApplicationById(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getApplicationById(int) - end"); //$NON-NLS-1$
			}
			return returnApplication;
		} catch (DataAccessException e) {
			LOG.error("getApplicationById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateApplication(com.exilant.tfw
	 * .pojo.Application)
	 */
	@Override
	public long updateApplication(Application application) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateApplication(Application) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = applicationDao.updateApplication(application);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateApplication(Application) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateApplication(Application)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long updateRunner(Runner runner) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateRunner(Runner) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = runnerDao.updateRunner(runner);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateRunner(Runner) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateApplication(Application)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertAppFunctionalityGetKey(com.
	 * exilant.tfw.pojo.AppFunctionality)
	 */
	@Override
	public int insertAppFunctionalityGetKey(AppFunctionality appFunctionality) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionalityGetKey(AppFunctionality) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = appFunctionalityDao.insertAppFunctionalityGetKey(appFunctionality);
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
	 * @see com.sssoft.isatt.data.service.MainService#getAppFunctionalityById(int)
	 */
	@Override
	public AppFunctionality getAppFunctionalityById(int functionalId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityById(int) - start"); //$NON-NLS-1$
		}

		try {
			AppFunctionality returnAppFunctionality = appFunctionalityDao.getAppFunctionalityById(functionalId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFunctionalityById(int) - end"); //$NON-NLS-1$
			}
			return returnAppFunctionality;
		} catch (DataAccessException e) {
			LOG.error("getAppFunctionalityById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateAppFunctionality(com.exilant
	 * .tfw.pojo.AppFunctionality)
	 */
	@Override
	public long updateAppFunctionality(AppFunctionality appFunctionality) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFunctionality(AppFunctionality) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = appFunctionalityDao.updateAppFunctionality(appFunctionality);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFunctionality(AppFunctionality) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateAppFunctionality(AppFunctionality)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertAppFeatureGetKey(com.exilant
	 * .tfw.pojo.AppFeature)
	 */
	@Override
	public int insertAppFeatureGetKey(AppFeature appFeature) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFeatureGetKey(AppFeature) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = appFeatureDao.insertFeatureGetKey(appFeature);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertAppFeatureGetKey(AppFeature) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertAppFeatureGetKey(AppFeature)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#
	 * getAppFeatureFilterByAppFunctionalityIDITAP(int)
	 */
	public List<AppFeature> getAppFeatureFilterByAppFunctionalityIDITAP(int appFeatureID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureFilterByAppFunctionalityIDITAP(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFeature> returnList = this.appFeatureDao.getAppFeatureByFunctionalId(appFeatureID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeatureFilterByAppFunctionalityIDITAP(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFeatureFilterByAppFunctionalityIDITAP(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAppFeatureById(int)
	 */
	@Override
	public AppFeature getAppFeatureById(int appFeatureId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureById(int) - start"); //$NON-NLS-1$
		}

		try {
			AppFeature returnAppFeature = appFeatureDao.getAppFeatureById(appFeatureId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeatureById(int) - end"); //$NON-NLS-1$
			}
			return returnAppFeature;
		} catch (DataAccessException e) {
			LOG.error("getAppFeatureById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateAppFeature(com.exilant.tfw.
	 * pojo.AppFeature)
	 */
	@Override
	public long updateAppFeature(AppFeature appFeature) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFeature(AppFeature) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = appFeatureDao.updateAppFeature(appFeature);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFeature(AppFeature) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateAppFeature(AppFeature)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertScreenGetKey(com.exilant.tfw
	 * .pojo.Screen)
	 */
	@Override
	public int insertScreenGetKey(Screen screen) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreenGetKey(Screen) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = screenDao.insertScreenGetKey(screen);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertScreenGetKey(Screen) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertScreenGetKey(Screen)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getScreenById(int)
	 */
	@Override
	public Screen getScreenById(int screenId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenById(int) - start"); //$NON-NLS-1$
		}

		try {
			Screen returnScreen = screenDao.getScreenById(screenId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreenById(int) - end"); //$NON-NLS-1$
			}
			return returnScreen;
		} catch (DataAccessException e) {
			LOG.error("getScreenById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateScreen(com.sssoft.isatt.data.pojo
	 * .Screen)
	 */
	@Override
	public long updateScreen(Screen screen) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScreen(Screen) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = screenDao.updateScreen(screen);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScreen(Screen) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateScreen(Screen)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertActionsGetKey(com.exilant.tfw
	 * .pojo.Actions)
	 */
	@Override
	public int insertActionsGetKey(Actions actions) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertActionsGetKey(Actions) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = actionsDao.insertActionsGetKey(actions);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertActionsGetKey(Actions) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertActionsGetKey(Actions)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAllActions()
	 */
	@Override
	public List<Actions> getAllActions() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllActions() - start"); //$NON-NLS-1$
		}

		try {
			List<Actions> returnList = actionsDao.getActions();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllActions() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllActions()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateScreenData(com.exilant.tfw.
	 * pojo.Screen)
	 */
	@Override
	public long updateScreenData(Screen screen) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScreenData(Screen) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = screenDao.updateScreenData(screen);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScreenData(Screen) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateScreenData(Screen)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getAllAppFeaturesByFunctionalId(int)
	 */
	@Override
	public List<AppFeature> getAllAppFeaturesByFunctionalId(int appFunctionalityID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllAppFeaturesByFunctionalId(int) - start"); //$NON-NLS-1$
		}

		LOG.info("Main Service - Feature DAO Implementation");
		try {
			List<AppFeature> returnList = this.appFeatureDao.getAllAppFeaturesByFunctionalId(appFunctionalityID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllAppFeaturesByFunctionalId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllAppFeaturesByFunctionalId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateSchedulerTime(com.exilant.tfw
	 * .pojo.Scheduler)
	 */
	@Override
	public long updateSchedulerTime(Scheduler scheduler) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerTime(Scheduler) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerDao.updateSchedulerTime(scheduler);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerTime(Scheduler) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateSchedulerTime(Scheduler)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#fetchSchedulerByTime(java.sql.Timestamp
	 * )
	 */
	@Override
	public List<Scheduler> fetchSchedulerByTime(Timestamp scheduledTime) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchSchedulerByTime(Timestamp) - start"); //$NON-NLS-1$
		}

		try {
			List<Scheduler> returnList = this.schedulerDao.fetchSchedulerByTime(scheduledTime);
			if (LOG.isDebugEnabled()) {
				LOG.debug("fetchSchedulerByTime(Timestamp) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("fetchSchedulerByTime(Timestamp)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getAllFunctionNamesByTestCaseId(int)
	 */
	@Override
	public List<AppFunctionality> getAllFunctionNamesByTestCaseId(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFunctionNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.appFunctionalityDao.getAllFunctionNamesByTestCaseId(testCaseId);
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
	 * com.sssoft.isatt.data.service.MainService#getAllFeatureNamesByTestCaseId(int)
	 */
	@Override
	public List<AppFeature> getAllFeatureNamesByTestCaseId(int testCaseId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFeatureNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFeature> returnList = this.appFeatureDao.getAllFeatureNamesByTestCaseId(testCaseId);
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
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getSchedulerLaneDetailsByScheduleId
	 * (int)
	 */
	@Override
	public List<SchedulerLaneDetails> getSchedulerLaneDetailsByScheduleId(int scheduleId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsByScheduleId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<SchedulerLaneDetails> returnList = this.schedulerLaneDetailsDao.getSchedulerLaneDetailsByScheduleId(scheduleId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerLaneDetailsByScheduleId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerLaneDetailsByScheduleId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAgentDetailsById(int)
	 */
	@Override
	public AgentDetails getAgentDetailsById(int agentDetailsId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentDetailsById(int) - start"); //$NON-NLS-1$
		}

		try {
			AgentDetails returnAgentDetails = this.agentDetailsDao.getAgentDetailsById(agentDetailsId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAgentDetailsById(int) - end"); //$NON-NLS-1$
			}
			return returnAgentDetails;
		} catch (DataAccessException e) {
			LOG.error("getAgentDetailsById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getDataSetsBySchedulerIdByAppId(int)
	 */
	@Override
	public List<DataSets> getDataSetsBySchedulerIdByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataSetsBySchedulerIdByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<DataSets> returnList = this.schedulerDao.getDataSetsBySchedulerIdByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getDataSetsBySchedulerIdByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("getDataSetsBySchedulerIdByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerFilterByAppID(int)
	 */
	@Override
	public List<Scheduler> getSchedulerFilterByAppID(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerFilterByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Scheduler> returnList = this.schedulerDao.getSchedulerFilterByAppID(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerFilterByAppID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerFilterByAppID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getSchedulerLaneDetailsByAppId(int)
	 */
	@Override
	public List<SchedulerLaneDetails> getSchedulerLaneDetailsByAppId(int AppId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<SchedulerLaneDetails> returnList = this.schedulerLaneDetailsDao.getSchedulerLaneDetailsByAppId(AppId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerLaneDetailsByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerLaneDetailsByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateSchedulerStatus(com.exilant
	 * .tfw.pojo.Scheduler)
	 */
	@Override
	public long updateSchedulerStatus(Scheduler scheduler) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerStatus(Scheduler) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerDao.updateSchedulerStatus(scheduler);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerStatus(Scheduler) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateSchedulerStatus(Scheduler)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateSchedulerFailureCountWithTime
	 * (com.sssoft.isatt.data.pojo.Scheduler)
	 */
	@Override
	public long updateSchedulerFailureCountWithTime(Scheduler scheduler) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerFailureCountWithTime(Scheduler) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerDao.updateSchedulerFailureCountWithTime(scheduler);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerFailureCountWithTime(Scheduler) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateSchedulerFailureCountWithTime(Scheduler)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateAppFunctionalData(com.exilant
	 * .tfw.pojo.AppFunctionality)
	 */
	@Override
	public long updateAppFunctionalData(AppFunctionality appFunctionality) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFunctionalData(AppFunctionality) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = appFunctionalityDao.updateAppFunctionalityData(appFunctionality);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFunctionalData(AppFunctionality) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateAppFunctionalData(AppFunctionality)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getAppFunctionalityObjByAppId(int)
	 */
	@Override
	public List<AppFunctionality> getAppFunctionalityObjByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityObjByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.appFunctionalityDao.getAppFunctionalityObjByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFunctionalityObjByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFunctionalityObjByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getScreensByAppId(int)
	 */
	@Override
	public List<Screen> getScreensByAppId(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreensByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Screen> returnList = this.screenDao.getScreensByAppId(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreensByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScreensByAppId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateAppFeatureData(com.exilant.
	 * tfw.pojo.AppFeature)
	 */
	@Override
	public long updateAppFeatureData(final AppFeature appFeature) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateAppFeatureData(AppFeature) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = appFeatureDao.updateAppFeatureData(appFeature);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFeatureData(AppFeature) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateAppFeatureData(AppFeature)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getTestDataWithParamCount(int)
	 */
	@Override
	public List<TestData> getTestDataWithParamCount(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataWithParamCount(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestData> returnList = this.testDataDao.getTestDataWithParamCount(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestDataWithParamCount(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getTestDataWithParamCount(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getPlanNamesByScheduleId(java.util
	 * .List)
	 */
	@Override
	public List<DataSets> getPlanNamesByScheduleId(List<Integer> scheduleIds) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanNamesByScheduleId(List<Integer>) - start"); //$NON-NLS-1$
		}

		try {
			List<DataSets> returnList = this.schedulerDao.getPlanNamesByScheduleId(scheduleIds);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPlanNamesByScheduleId(List<Integer>) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getPlanNamesByScheduleId(List<Integer>)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerById(int)
	 */
	@Override
	public Scheduler getSchedulerById(int schedulerId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerById(int) - start"); //$NON-NLS-1$
		}

		try {
			Scheduler returnScheduler = this.schedulerDao.getSchedulerById(schedulerId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerById(int) - end"); //$NON-NLS-1$
			}
			return returnScheduler;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateScheduler(com.sssoft.isatt.data.pojo
	 * .Scheduler)
	 */
	@Override
	public long updateScheduler(final Scheduler scheduler) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScheduler(Scheduler) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerDao.updateScheduler(scheduler);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScheduler(Scheduler) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateScheduler(Scheduler)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateSchedulerLaneDetails(com.exilant
	 * .tfw.pojo.SchedulerLaneDetails)
	 */
	@Override
	public long updateSchedulerLaneDetails(final SchedulerLaneDetails schedulerLaneDetails) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateSchedulerLaneDetails(SchedulerLaneDetails) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerLaneDetailsDao.updateSchedulerLaneDetails(schedulerLaneDetails);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerLaneDetails(SchedulerLaneDetails) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateSchedulerLaneDetails(SchedulerLaneDetails)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getActionsById(int)
	 */
	@Override
	public Actions getActionsById(int actionsId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActionsById(int) - start"); //$NON-NLS-1$
		}

		try {
			Actions returnActions = this.actionsDao.getActionsById(actionsId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getActionsById(int) - end"); //$NON-NLS-1$
			}
			return returnActions;
		} catch (DataAccessException e) {
			LOG.error("getActionsById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getRunnerById(int)
	 */
	@Override
	public Runner getRunnerById(int runnerId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunnerById(int) - start"); //$NON-NLS-1$
		}

		try {
			Runner returnRunner = this.runnerDao.getRunnerById(runnerId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getRunnerById(int) - end"); //$NON-NLS-1$
			}
			return returnRunner;
		} catch (DataAccessException e) {
			LOG.error("getRunnerById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#insertSchedulerGetKey(com.exilant
	 * .tfw.pojo.SchedulerBackup)
	 */
	@Override
	public int insertSchedulerGetKey(SchedulerBackup schedulerBackup) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerGetKey(SchedulerBackup) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = this.schedulerBackupDao.insertSchedulerGetKey(schedulerBackup);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertSchedulerGetKey(SchedulerBackup) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertSchedulerGetKey(SchedulerBackup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#updateScheduler(com.sssoft.isatt.data.pojo
	 * .SchedulerBackup)
	 */
	@Override
	public long updateScheduler(SchedulerBackup schedulerBackup) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateScheduler(SchedulerBackup) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerBackupDao.updateScheduler(schedulerBackup);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScheduler(SchedulerBackup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateScheduler(SchedulerBackup)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#deleteSchedulerById(int)
	 */
	@Override
	public long deleteSchedulerById(int schedulerId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSchedulerById(int) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = this.schedulerDao.deleteSchedulerById(schedulerId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("deleteSchedulerById(int) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("deleteSchedulerById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerByStatus()
	 */
	@Override
	public List<Scheduler> getSchedulerByStatus() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerByStatus() - start"); //$NON-NLS-1$
		}

		try {
			List<Scheduler> returnList = this.schedulerDao.getSchedulerByStatus();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerByStatus() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerByStatus()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getSchedulerBackupById(int)
	 */
	@Override
	public SchedulerBackup getSchedulerBackupById(int schedulerBackupId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerBackupById(int) - start"); //$NON-NLS-1$
		}

		try {
			SchedulerBackup returnSchedulerBackup = this.schedulerBackupDao.getSchedulerBackupById(schedulerBackupId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerBackupById(int) - end"); //$NON-NLS-1$
			}
			return returnSchedulerBackup;
		} catch (DataAccessException e) {
			LOG.error("getSchedulerBackupById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getScreensFeatureID(int)
	 */
	@Override
	public List<Screen> getScreensFeatureID(int featureID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreensFeatureID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<Screen> returnList = this.screenDao.getScreensByFeatureId(featureID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreensFeatureID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScreensFeatureID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getAppFeatureByFunctioanlityID(int)
	 */
	@Override
	public List<AppFeature> getAppFeatureByFunctioanlityID(int functionalID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureByFunctioanlityID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFeature> returnList = this.appFeatureDao.getAppFeatureByFunctionalId(functionalID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeatureByFunctioanlityID(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAppFeatureByFunctioanlityID(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getAllApps()
	 */
	@Override
	public List<Application> getAllApps() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApps() - start"); //$NON-NLS-1$
		}

		try {
			List<Application> returnList = this.applicationDao.getAllApps();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllApps() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getAllApps()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.service.MainService#getScheduledJobsForApp(int)
	 */
	@Override
	public List<ScheduledJobs> getScheduledJobsForApp(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledJobsForApp(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ScheduledJobs> returnList = this.schedulerDao.getScheduledJobsForApp(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScheduledJobsForApp(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScheduledJobsForApp(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getScheduledRunningJobsForApp(int)
	 */
	@Override
	public List<ScheduledJobs> getScheduledRunningJobsForApp(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledRunningJobsForApp(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ScheduledJobs> returnList = this.schedulerDao.getScheduledRunningJobsForApp(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScheduledRunningJobsForApp(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScheduledRunningJobsForApp(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getScheduledNotRunJobsForApp(int)
	 */
	@Override
	public List<ScheduledJobs> getScheduledNotRunJobsForApp(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledNotRunJobsForApp(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ScheduledJobs> returnList = this.schedulerDao.getScheduledNotRunJobsForApp(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScheduledNotRunJobsForApp(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScheduledNotRunJobsForApp(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.service.MainService#getScheduledCompletedJobsForApp(int)
	 */
	@Override
	public List<ScheduledJobs> getScheduledCompletedJobsForApp(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledCompletedJobsForApp(int) - start"); //$NON-NLS-1$
		}

		try {
			List<ScheduledJobs> returnList = this.schedulerDao.getScheduledCompletedJobsForApp(appId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScheduledCompletedJobsForApp(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getScheduledCompletedJobsForApp(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Application getApplicationForFlowChart(int appId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationForFlowChart(int) - start"); //$NON-NLS-1$
		}

		Application application = null;
		try {
			application = (Application) mapCacheUtility.get(String.valueOf(appId));
		} catch (CacheException e1) {
			LOG.error("getApplicationForFlowChart(int)", e1); //$NON-NLS-1$
			try {
				application = this.applicationDao.getApplicationById(appId);
				List<TestPlan> testPlans = getTestPlanDependentsForFlowChart(application);
				application.setTestPlan(testPlans);
				try {
					mapCacheUtility.add(String.valueOf(appId), application);
				} catch (CacheException e) {
					LOG.error("getApplicationForFlowChart(int)", e); //$NON-NLS-1$
					throw new ServiceException(e.getMessage());
				}
			} catch (DataAccessException e) {
				LOG.error("getApplicationForFlowChart(int)", e); //$NON-NLS-1$
				throw new ServiceException(e.getMessage());
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationForFlowChart(int) - end"); //$NON-NLS-1$
		}
		return application;
	}

	private List<TestPlan> getTestPlanDependentsForFlowChart(Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanDependentsForFlowChart(Application) - start"); //$NON-NLS-1$
		}
		int appId = application.getAppID();
		List<TestPlan> retPlans = new ArrayList<TestPlan>();
		List<TestPlan> testPlans = this.testPlanDao.getTestPlanObjByAppId(appId);
		Iterator<TestPlan> iterator = testPlans.iterator();
		while (iterator.hasNext()) {
			TestPlan testPlan = (TestPlan) iterator.next();
			List<TestSuite> retSuites = new ArrayList<TestSuite>();
			List<TestSuite> testSuites = this.testSuiteDao.getTestSuitesForFlowChart(testPlan.getTestPlanID());
			Iterator<TestSuite> iterator2 = testSuites.iterator();
			while (iterator2.hasNext()) {
				TestSuite testSuite = (TestSuite) iterator2.next();
				List<TestScenario> retScenarios = new ArrayList<TestScenario>();
				List<TestScenario> scenarios = this.testScenarioDao.getTestScenariosForFlowChart(testSuite.getTestSuiteID());
				Iterator<TestScenario> iterator3 = scenarios.iterator();
				while (iterator3.hasNext()) {
					TestScenario testScenario = (TestScenario) iterator3.next();
					List<TestCase> cases = this.testCaseDao.getTestCasesForFlowChart(testScenario.getTestScenarioID());
					testScenario.setTestCase(cases);
					retScenarios.add(testScenario);
				}
				testSuite.setTestScenario(retScenarios);
				retSuites.add(testSuite);
			}
			testPlan.setTestSuite(retSuites);
			retPlans.add(testPlan);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanDependentsForFlowChart(Application) - end"); //$NON-NLS-1$
		}
		return retPlans;
	}

	@Override
	public List<Roles> getRoles() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRoles() - start"); //$NON-NLS-1$
		}

		try {
			List<Roles> returnList = this.rolesDao.getRoles();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getRoles() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getRoles()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int insertUsersGetKey(Users users) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertUsersGetKey(Users) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = usersDao.insertUsersGetKey(users);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertUsersGetKey(Users) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertUsersGetKey(Users)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int insertUsersApplicationMappingGetKey(UsersApplicationMapping usersApplicationMapping) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertUsersApplicationMappingGetKey(UsersApplicationMapping) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = usersApplicationMappingDao.insertUsersApplicationMappingGetKey(usersApplicationMapping);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertUsersApplicationMappingGetKey(UsersApplicationMapping) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertUsersApplicationMappingGetKey(UsersApplicationMapping)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public int insertUserRolesGetKey(UserRoles userRoles) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertUserRolesGetKey(UserRoles) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = userRolesDao.insertUserRolesGetKey(userRoles);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertUserRolesGetKey(UserRoles) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertUserRolesGetKey(UserRoles)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<UsersApplicationMapping> getApplicationByUserId(int userId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationByUserId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<UsersApplicationMapping> returnList = usersApplicationMappingDao.getApplicationByUserId(userId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getApplicationByUserId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (DataAccessException e) {
			LOG.error("getApplicationByUserId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public boolean checkAvailability(String username) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("checkAvailability(String) - start"); //$NON-NLS-1$
		}

		try {
			boolean returnboolean = usersDao.checkAvailability(username);
			if (LOG.isDebugEnabled()) {
				LOG.debug("checkAvailability(String) - end"); //$NON-NLS-1$
			}
			return returnboolean;
		} catch (DataAccessException e) {
			LOG.error("checkAvailability(String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int getUserIdByUserName(String userName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserIdByUserName(String) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = usersDao.getUserIdByUserName(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getUserIdByUserName(String) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("getUserIdByUserName(String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Users getUsersByName(String username) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUsersByName(String) - start"); //$NON-NLS-1$
		}

		try {
			Users returnUsers = this.usersDao.getUsersByName(username);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getUsersByName(String) - end"); //$NON-NLS-1$
			}
			return returnUsers;
		} catch (DataAccessException e) {
			LOG.error("getUsersByName(String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<String> getUserRoleById(int userId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleById(int) - start"); //$NON-NLS-1$
		}

		try {
			List<String> returnString = this.userRolesDao.getUserRoleById(userId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getUserRoleById(int) - end"); //$NON-NLS-1$
			}
			return returnString;
		} catch (DataAccessException e) {
			LOG.error("getUserRoleById(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public List<String> getUserRoleFilterByUserId(int userId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleFilterByUserId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<String> returnString = this.userRolesDao.getUserRolesFilterByUserId(userId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getUserRoleFilterByUserId(int) - end"); //$NON-NLS-1$
			}
			return returnString;
		} catch (DataAccessException e) {
			LOG.error("getUserRoleFilterByUserId(int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public String getPasswordByUsernameEmailID(String username, String emailID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleById(int) - start"); //$NON-NLS-1$
		}

		try {
			String password = this.usersDao.getPasswordByUsernameEmailID(username, emailID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPasswordByUsernameEmailID(String, String) - end"); //$NON-NLS-1$
			}
			return password;
		} catch (DataAccessException e) {
			LOG.error("getPasswordByUsernameEmailID(String, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int getIncorrectPasswordCount(String username) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleById(int) - start"); //$NON-NLS-1$
		}
		try {
			int IncorrectPasswordCount = this.usersDao.getIncorrectPasswordCount(username);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPasswordByUsernameEmailID(String, String) - end"); //$NON-NLS-1$
			}
			return IncorrectPasswordCount;
		} catch (DataAccessException e) {
			LOG.error("getPasswordByUsernameEmailID(String, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public long updateIncorrectPasswordCount(String username, int incorrectPasswordCount) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleById(int) - start"); //$NON-NLS-1$
		}
		try {
			long IncorrectPasswordCount = this.usersDao.updateIncorrectPasswordCount(username, incorrectPasswordCount);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPasswordByUsernameEmailID(String, String) - end"); //$NON-NLS-1$
			}
			return IncorrectPasswordCount;
		} catch (DataAccessException e) {
			LOG.error("getPasswordByUsernameEmailID(String, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public String getEmailByUsername(String username) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleById(int) - start"); //$NON-NLS-1$
		}
		try {
			String emailID = this.usersDao.getEmailByUsername(username);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPasswordByUsernameEmailID(String, String) - end"); //$NON-NLS-1$
			}
			return emailID;
		} catch (DataAccessException e) {
			LOG.error("getPasswordByUsernameEmailID(String, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public long updatePasswordByUsername(String username, String newPassword) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserRoleById(int) - start"); //$NON-NLS-1$
		}
		try {
			long passwordUpdated = this.usersDao.updatePasswordByUsername(username, newPassword);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getPasswordByUsernameEmailID(String, String) - end"); //$NON-NLS-1$
			}
			return passwordUpdated;
		} catch (DataAccessException e) {
			LOG.error("getPasswordByUsernameEmailID(String, String)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public int insertRolesGetKey(Roles roles) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRolesGetKey(Roles) - start"); //$NON-NLS-1$
		}

		try {
			int returnint = rolesDao.insertRolesGetKey(roles);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertRolesGetKey(Roles) - end"); //$NON-NLS-1$
			}
			return returnint;
		} catch (DataAccessException e) {
			LOG.error("insertRolesGetKey(Roles)", e); //$NON-NLS-1$
			throw new ServiceException(e.getMessage());
		}
	
	}

	@Override
	public Roles getRoleByID(int roleID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRoleByID(roleID) - start"); //$NON-NLS-1$
		}
		Roles role= null;
		try {
			role = rolesDao.getRoleByID(roleID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getRoleByID(roleID)- end"); //$NON-NLS-1$
			}
			
		} catch (DataAccessException e) {
			LOG.error("getRoleByID(roleID)", e); //$NON-NLS-1$
			throw new ServiceException(e.getMessage());
		}
		return role;
	}

	@Override
	public long updateRoles(Roles roles) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateRoles(Roles) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = rolesDao.updateRole(roles);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateRoles(Roles) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateRoles(Roles)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int getUserIdByRole(String roleName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserIdByRole(Users) - start"); //$NON-NLS-1$
		}

		try {
			int userId = userRolesDao.getUserIdByRole(roleName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("insertRolesGetKey(Roles) - end"); //$NON-NLS-1$
			}
			return userId;
		} catch (DataAccessException e) {
			LOG.error("getUserIdByRole(Roles)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	
	}

	@Override
	public List<Users> getAllUsers() throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUsers() - start"); //$NON-NLS-1$
		}

		try {
			List<Users> usersList = this.usersDao.getAllUsers();
			if (LOG.isDebugEnabled()) {
				LOG.debug("getRoles() - end"); //$NON-NLS-1$
			}
			return usersList;
		} catch (DataAccessException e) {
			LOG.error("getRoles()", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public Users getUserByUserId(int userId) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserByUserId(userId) - start"); //$NON-NLS-1$
		}
		Users users= null;
		try {
			users = usersDao.getUsersByUserID(userId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getUserByUserId(userId) - end"); //$NON-NLS-1$
			}
			
		} catch (DataAccessException e) {
			LOG.error("getUserByUserId(userId)", e); //$NON-NLS-1$
			throw new ServiceException(e.getMessage());
		}
		return users;
	}

	@Override
	public long updateUser(Users users) throws ServiceException {
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateUser(users) - start"); //$NON-NLS-1$
		}

		try {
			long returnlong = usersDao.updateUser(users);
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateUser(users) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (DataAccessException e) {
			LOG.error("updateUser(users)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public List<Integer> getApplicationsByRoleAndUserID(int userID, String role)
			throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationsByRoleAndUserID(int ,int) - start"); //$NON-NLS-1$
		}

		try {
			List<Integer> appIds = usersApplicationMappingDao.getApplicationsByRoleAndUserID(userID,role);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getApplicationsByRoleAndUserID(int ,int) - end"); //$NON-NLS-1$
			}
			return appIds;
		} catch (DataAccessException e) {
			LOG.error("getApplicationsByRoleAndUserID(int ,int)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int getApplicationIDByName(String appName) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationIDByName(appName) - start"); //$NON-NLS-1$
		}

		try {
			int appID = applicationDao.getAppIdByAppName(appName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getApplicationIDByName(appName) - end"); //$NON-NLS-1$
			}
			return appID;
		} catch (DataAccessException e) {
			LOG.error("getApplicationIDByName(appName)", e); //$NON-NLS-1$

			throw new ServiceException(e.getMessage());
		}
	}
	
}