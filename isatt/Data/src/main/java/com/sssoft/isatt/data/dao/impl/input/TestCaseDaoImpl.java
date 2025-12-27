package com.sssoft.isatt.data.dao.impl.input;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;
import com.sssoft.isatt.data.dao.AppFeatureDao;
import com.sssoft.isatt.data.dao.AppFunctionalityDao;
import com.sssoft.isatt.data.dao.input.CaseStepMappingDao;
import com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao;
import com.sssoft.isatt.data.dao.input.TestCaseDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QScenariocasemapping;
import com.sssoft.isatt.data.generated.pojo.QTestcase;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.def.TestCaseUI;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;

/**
 * The Class TestCaseDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestCaseDao interface
 */
public class TestCaseDaoImpl implements TestCaseDao {

	/*
	 * The central class in the Querydsl support is the QueryDslJdbcTemplate.
	 * Just like the NamedParameterJdbcTemplate it wraps a regular JdbcTemplate
	 * that you can get access to by calling the getJdbcOperations method. One
	 * thing to note is that when you use the QueryDslJdbcTemplate, there is no
	 * need to specify the SQL dialect to be used since the template will
	 * auto-detect this when it is created.
	 */
	/** The template. */
	private QueryDslJdbcTemplate template;

	/** The q test case. */
	private QTestcase qTestCase = QTestcase.testcase;

	private QScenariocasemapping qScenarioCaseMapping = QScenariocasemapping.scenariocasemapping;

	/** The app functionality dao. */
	private AppFunctionalityDao appFunctionalityDao;

	/** The app feature dao. */
	private AppFeatureDao appFeatureDao;

	/** The scenario case mapping dao. */
	private ScenarioCaseMappingDao scenarioCaseMappingDao;
	private CaseStepMappingDao caseStepMappingDao;

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
	 * Sets the app functionality dao.
	 * 
	 * @param appFunctionalityDao
	 *            the appFunctionalityDao to set
	 */
	public void setAppFunctionalityDao(AppFunctionalityDao appFunctionalityDao) {
		this.appFunctionalityDao = appFunctionalityDao;
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

	public void setCaseStepMappingDao(CaseStepMappingDao caseStepMappingDao) {
		this.caseStepMappingDao = caseStepMappingDao;
	}

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestCaseDaoImpl.class);

	/*
	 * You can create a QueryDslJdbcTemplate by passing in a JdbcTemplate or a
	 * DataSource in the constructor.
	 */
	/**
	 * Sets the data source.
	 * 
	 * @param dataSource
	 *            the new data source
	 */
	public void setDataSource(DataSource dataSource) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setDataSource(DataSource) - start"); //$NON-NLS-1$
		}

		this.template = new QueryDslJdbcTemplate(dataSource);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setDataSource(DataSource) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * The Class MappingTestCaseProjection.
	 */
	public class MappingTestCaseProjection extends MappingProjection<TestCase> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test case projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestCaseProjection(Expression<?>... args) {
			super(TestCase.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestCase map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestCase testCase = new TestCase();
			testCase.setTestCaseID(tuple.get(qTestCase.testCaseID));
			testCase.setClassificationTag(tuple.get(qTestCase.classificationTag));
			testCase.setRunnerID(tuple.get(qTestCase.runnerID));
			testCase.setDescription(tuple.get(qTestCase.description));
			testCase.setActive(tuple.get(qTestCase.active));
			testCase.setPositive(tuple.get(qTestCase.positive));
			testCase.setSortOrder(tuple.get(qTestCase.sortOrder));
			testCase.setFunctionalID(tuple.get(qTestCase.functionalID));
			testCase.setFeatureID(tuple.get(qTestCase.featureID));
			testCase.setCreatedBy(tuple.get(qTestCase.createdBy));
			testCase.setCreatedDateTime(tuple.get(qTestCase.createdDateTime));
			testCase.setUpdatedBy(tuple.get(qTestCase.updatedBy));
			testCase.setUpdatedDateTime(tuple.get(qTestCase.updatedDateTime));
			testCase.setConditionGroupID(tuple.get(qTestCase.conditionGroupID));
			testCase.setCaseName(tuple.get(qTestCase.caseName));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestCase object : " + testCase);
			}
			return testCase;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestCase(final TestCase testCase) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCase(TestCase) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestCase: " + testCase);
		try {
			result = template.insert(qTestCase, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName, qTestCase.runnerID, qTestCase.description,
									qTestCase.active, qTestCase.positive, qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime,
									qTestCase.updatedBy, qTestCase.updatedDateTime, qTestCase.conditionGroupID)
							.values(testCase.getTestCaseID(), testCase.getClassificationTag(), testCase.getCaseName(), testCase.getRunnerID(),
									testCase.getDescription(), testCase.getActive(), testCase.getPositive(), testCase.getSortOrder(),
									testCase.getCreatedBy(), testCase.getCreatedDateTime(), testCase.getUpdatedBy(), testCase.getUpdatedDateTime(),
									testCase.getConditionGroupID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in TestCase");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCase(TestCase) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testCase
	 *            the test case
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestCase(final TestCase testCase) throws DataAccessException {
		LOG.info("Started updating data in TestCase: " + testCase);
		try {
			long returnlong = template.update(qTestCase, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestCase.testCaseID.eq(testCase.getTestCaseID()))
							.set(qTestCase.functionalID, testCase.getFunctionalID()).set(qTestCase.featureID, testCase.getFeatureID())
							.set(qTestCase.caseName, testCase.getCaseName()).set(qTestCase.classificationTag, testCase.getClassificationTag())
							.set(qTestCase.runnerID, testCase.getRunnerID()).set(qTestCase.description, testCase.getDescription())
							.set(qTestCase.active, String.valueOf(testCase.getActive())).set(qTestCase.positive, String.valueOf(testCase.getPositive()))
							.set(qTestCase.sortOrder, testCase.getSortOrder()).set(qTestCase.conditionGroupID, testCase.getConditionGroupID())
							.set(qTestCase.updatedBy, testCase.getUpdatedBy()).set(qTestCase.updatedDateTime, testCase.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestCase(TestCase) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCase()
	 */
	@Override
	public List<TestCase> getTestCase() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCase() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestCaseProjection(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName,
					qTestCase.runnerID, qTestCase.description, qTestCase.functionalID, qTestCase.featureID, qTestCase.active, qTestCase.positive,
					qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy, qTestCase.updatedDateTime,
					qTestCase.conditionGroupID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCaseById(int)
	 */
	@Override
	public TestCase getTestCaseById(int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where(qTestCase.testCaseID.eq(testCaseId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestCaseProjection(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName,
					qTestCase.runnerID, qTestCase.description, qTestCase.active, qTestCase.positive, qTestCase.functionalID, qTestCase.featureID,
					qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy, qTestCase.updatedDateTime,
					qTestCase.conditionGroupID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public TestCase getTestCaseByName(String testCaseName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByName(String) - start"); //$NON-NLS-1$
		}
		
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where(qTestCase.caseName.eq(testCaseName));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestCaseProjection(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName,
					qTestCase.runnerID, qTestCase.description, qTestCase.active, qTestCase.positive, qTestCase.functionalID, qTestCase.featureID,
					qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy, qTestCase.updatedDateTime,
					qTestCase.conditionGroupID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<Integer> getTestCaseIdByName(String testCaseName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByName(String) - start"); //$NON-NLS-1$
		}
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where(qTestCase.caseName.eq(testCaseName));
			List<Integer> testCaseID = template.query(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestCaseIdByName(String) - end"); //$NON-NLS-1$
				}
				return testCaseID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByName(String) - end"); //$NON-NLS-1$
		}
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCaseByTestScenarioId(int)
	 */
	@Override
	public List<TestCase> getTestCaseByTestScenarioId(int testScenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByTestScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase);
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestCaseProjection(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName,
					qTestCase.runnerID, qTestCase.description, qTestCase.functionalID, qTestCase.featureID, qTestCase.active, qTestCase.positive,
					qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy, qTestCase.updatedDateTime,
					qTestCase.conditionGroupID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCaseIdByTestScenarioId(int)
	 */
	@Override
	public int getTestCaseIdByTestScenarioId(int testScenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByTestScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase);
			LOG.info("generated query : " + sqlQuery);
			Integer testCaseID = template.queryForObject(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestCaseIdByTestScenarioId(int) - end"); //$NON-NLS-1$
				}
				return testCaseID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByTestScenarioId(int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCaseByIdByName(java.lang
	 * .String, int)
	 */
	@Override
	public List<Integer> getTestCaseByIdByName(String caseName, int testScenarioID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByIdByName(String, int) - start"); //$NON-NLS-1$
		}

		List<Integer> testCaseIDList = new ArrayList<Integer>();
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where(qTestCase.caseName.eq(caseName));
			LOG.info("generated query : " + sqlQuery);
			Integer testCaseID = template.queryForObject(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				testCaseIDList.add(testCaseID);
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByIdByName(String, int) - end"); //$NON-NLS-1$
		}
		return testCaseIDList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCaseByIdByName(java.lang
	 * .String)
	 */
	@Override
	public int getTestCaseByIdByName(String caseName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByIdByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where((qTestCase.caseName.eq(caseName)));
			Integer testCaseID = template.queryForObject(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestCaseByIdByName(String) - end"); //$NON-NLS-1$
				}
				return testCaseID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByIdByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestCaseDao#
	 * getTestCaseIdByNameByFeatureIdByClassificationTag
	 * (com.sssoft.isatt.data.pojo.input.TestCase)
	 */
	@Override
	public int getTestCaseIdByNameByFeatureIdByClassificationTag(TestCase testCase) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByNameByFeatureIdByClassificationTag(TestCase) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qTestCase)
					.where(qTestCase.caseName.eq(testCase.getCaseName()).and(
							qTestCase.featureID.eq(testCase.getFeatureID()).and(qTestCase.classificationTag.eq(testCase.getClassificationTag()))));
			Integer testCaseID = template.queryForObject(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestCaseIdByNameByFeatureIdByClassificationTag(TestCase) - end"); //$NON-NLS-1$
				}
				return testCaseID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByNameByFeatureIdByClassificationTag(TestCase) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestCaseDao#
	 * getTestCaseIdByNameByFeatureIdByClassificationTagByCgId
	 * (com.sssoft.isatt.data.pojo.input.TestCase)
	 */
	@Override
	public int getTestCaseIdByNameByFeatureIdByClassificationTagByCgId(TestCase testCase) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByNameByFeatureIdByClassificationTagByCgId(TestCase) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qTestCase)
					.where(qTestCase.caseName.eq(testCase.getCaseName()).and(
							qTestCase.featureID.eq(testCase.getFeatureID()).and(
									qTestCase.classificationTag.eq(testCase.getClassificationTag()).and(
											qTestCase.conditionGroupID.eq(testCase.getConditionGroupID())))));
			Integer testCaseID = template.queryForObject(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestCaseIdByNameByFeatureIdByClassificationTagByCgId(TestCase) - end"); //$NON-NLS-1$
				}
				return testCaseID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseIdByNameByFeatureIdByClassificationTagByCgId(TestCase) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#insertTestCaseGetKey(com.exilant
	 * .tfw.pojo.input.TestCase)
	 */
	@Override
	public int insertTestCaseGetKey(final TestCase testCase) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseGetKey(TestCase) - start"); //$NON-NLS-1$
		}

		int testCaseId = 0;
		try {
			testCaseId = template.insertWithKey(qTestCase, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName, qTestCase.runnerID, qTestCase.description,
									qTestCase.functionalID, qTestCase.featureID, qTestCase.active, qTestCase.positive, qTestCase.sortOrder,
									qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy, qTestCase.updatedDateTime,
									qTestCase.conditionGroupID)
							.values(testCase.getTestCaseID(), testCase.getClassificationTag(), testCase.getCaseName(), testCase.getRunnerID(),
									testCase.getDescription(), testCase.getFunctionalID(), testCase.getFeatureID(), testCase.getActive(),
									testCase.getPositive(), testCase.getSortOrder(), testCase.getCreatedBy(), testCase.getCreatedDateTime(),
									testCase.getUpdatedBy(), testCase.getUpdatedDateTime(), testCase.getConditionGroupID())
							.executeWithKey(qTestCase.testCaseID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testCaseId : " + testCaseId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseGetKey(TestCase) - end"); //$NON-NLS-1$
		}
		return testCaseId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getTestCaseByIdByNameAndScenarioID
	 * (java.lang.String, int)
	 */
	@Override
	public int getTestCaseByIdByNameAndScenarioID(String caseName, int testScenarioID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByIdByNameAndScenarioID(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where(qTestCase.caseName.eq(caseName));
			Integer testCaseID = template.queryForObject(sqlQuery, qTestCase.testCaseID);
			if (testCaseID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestCaseByIdByNameAndScenarioID(String, int) - end"); //$NON-NLS-1$
				}
				return testCaseID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseByIdByNameAndScenarioID(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getAllFunctionNamesByTestCaseId
	 * (int)
	 */
	@Override
	public List<AppFunctionality> getAllFunctionNamesByTestCaseId(int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFunctionNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			TestCase testCase = this.getTestCaseById(testCaseId);
			List<AppFunctionality> returnList = this.appFunctionalityDao.getAllFunctionNamesByTestCaseId(testCase.getFunctionalID());
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllFunctionNamesByTestCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getAllFeatureNamesByTestCaseId(int)
	 */
	@Override
	public List<AppFeature> getAllFeatureNamesByTestCaseId(int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFeatureNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			TestCase testCase = this.getTestCaseById(testCaseId);
			List<AppFeature> returnList = this.appFeatureDao.getAllFeatureNamesByTestCaseId(testCase.getFeatureID());
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllFeatureNamesByTestCaseId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestCaseDao#getAllTestCasesByTestScenarioId
	 * (int)
	 */
	@Override
	public List<TestCase> getAllTestCasesByTestScenarioId(int testScenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestCasesByTestScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestCase> resultTestCases = new ArrayList<TestCase>();
			List<ScenarioCaseMapping> scenarioCaseMappings = this.scenarioCaseMappingDao.getScenarioCaseMappingsByScenarioId(testScenarioId);
			Iterator<ScenarioCaseMapping> iterator = scenarioCaseMappings.iterator();
			while (iterator.hasNext()) {
				ScenarioCaseMapping scenarioCaseMapping2 = (ScenarioCaseMapping) iterator.next();
				resultTestCases.add(this.getTestCaseById(scenarioCaseMapping2.getTestCaseID()));
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestCasesByTestScenarioId(int) - end"); //$NON-NLS-1$
			}
			return resultTestCases;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	}

	@Override
	public List<TestCaseUI> getTestCaseUIByAppID(int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseUIByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestCaseUI> testCaseUIs = new ArrayList<TestCaseUI>();
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCase).where(qTestCase.testCaseID.eq(testCaseId));
			LOG.info("Generated Query : " + sqlQuery);
			List<TestCase> testCases = template.query(sqlQuery, new MappingTestCaseProjection(qTestCase.testCaseID, qTestCase.classificationTag,
					qTestCase.caseName, qTestCase.runnerID, qTestCase.description, qTestCase.functionalID, qTestCase.featureID, qTestCase.active,
					qTestCase.positive, qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy,
					qTestCase.updatedDateTime, qTestCase.conditionGroupID));
			Iterator<TestCase> iterator = testCases.iterator();
			while (iterator.hasNext()) {
				TestCase testCase = (TestCase) iterator.next();
				TestCaseUI testCaseUI = new TestCaseUI();
				int caseId = testCase.getTestCaseID();
				String caseName = testCase.getCaseName();
				testCaseUI.setTestCaseID(caseId);
				testCaseUI.setCaseName(caseName);
				testCaseUI.setDescription(testCase.getDescription());
				long testStepsCount = this.caseStepMappingDao.getCaseStepMappingsByCaseIdCount(caseId);
				testCaseUI.setTestStepsCount((int) testStepsCount);
				long testScenarioCount = this.scenarioCaseMappingDao.getScenarioCaseMappingsByCaseIdCount(caseId);
				testCaseUI.setTestScenariosCount((int) testScenarioCount);
				testCaseUIs.add(testCaseUI);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestCaseUIByAppID(int) - end"); //$NON-NLS-1$
			}
			return testCaseUIs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	public List<TestCase> getTestCasesForFlowChart(int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCasesForFlowChart(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qScenarioCaseMapping).from(qTestCase)
				.where(qScenarioCaseMapping.testScenarioID.eq(scenarioId).and(qScenarioCaseMapping.testCaseID.eq(qTestCase.testCaseID)));
		List<TestCase> returnList = template.query(sqlQuery, new MappingTestCaseProjection(qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName, qTestCase.runnerID, qTestCase.description, qTestCase.functionalID, qTestCase.featureID, qTestCase.active, qTestCase.positive, qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime, qTestCase.updatedBy, qTestCase.updatedDateTime, qTestCase.conditionGroupID));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCasesForFlowChart(int) - end"); //$NON-NLS-1$
		}
		return returnList;
	}
}