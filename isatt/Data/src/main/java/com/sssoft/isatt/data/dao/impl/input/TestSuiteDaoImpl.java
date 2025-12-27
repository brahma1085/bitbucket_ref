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
import com.sssoft.isatt.data.dao.input.SuiteScenarioMappingDao;
import com.sssoft.isatt.data.dao.input.TestSuiteDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QPlansuitemapping;
import com.sssoft.isatt.data.generated.pojo.QTestsuite;
import com.sssoft.isatt.data.pojo.def.TestSuiteUI;
import com.sssoft.isatt.data.pojo.input.TestSuite;

/**
 * The Class TestSuiteDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestSuiteDao interface
 */
public class TestSuiteDaoImpl implements TestSuiteDao {

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
	private SuiteScenarioMappingDao suiteScenarioMappingDao;

	public void setSuiteScenarioMappingDao(SuiteScenarioMappingDao suiteScenarioMappingDao) {
		this.suiteScenarioMappingDao = suiteScenarioMappingDao;
	}

	/** The q test suite. */
	private QTestsuite qTestSuite = QTestsuite.testsuite;

	private QPlansuitemapping qPlanSuiteMapping = QPlansuitemapping.plansuitemapping;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestSuiteDaoImpl.class);

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
	 * The Class MappingTestSuiteProjection.
	 */
	public class MappingTestSuiteProjection extends MappingProjection<TestSuite> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test suite projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestSuiteProjection(Expression<?>... args) {
			super(TestSuite.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestSuite map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestSuite testSuite = new TestSuite();
			testSuite.setTestSuiteID(tuple.get(qTestSuite.testSuiteID));
			testSuite.setSuiteName(tuple.get(qTestSuite.suiteName));
			testSuite.setDescription(tuple.get(qTestSuite.description));
			testSuite.setAppID(tuple.get(qTestSuite.appID));
			// testSuite.setFunctionalID(tuple.get(qTestSuite.functionalID));
			// testSuite.setFeatureID(tuple.get(qTestSuite.featureID));
			testSuite.setSortOrder(tuple.get(qTestSuite.sortOrder));
			testSuite.setCreatedBy(tuple.get(qTestSuite.createdBy));
			testSuite.setCreatedDateTime(tuple.get(qTestSuite.createdDateTime));
			testSuite.setUpdatedBy(tuple.get(qTestSuite.updatedBy));
			testSuite.setUpdatedDateTime(tuple.get(qTestSuite.updatedDateTime));
			// testSuite.setTestPlanID(tuple.get(qTestSuite.testPlanID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestSuite object : " + testSuite);
			}
			return testSuite;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestSuite(final TestSuite testSuite) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuite(TestSuite) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestSuite: " + testSuite);
		try {
			result = template.insert(qTestSuite, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.sortOrder,
									qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime)
							.values(testSuite.getTestSuiteID(), testSuite.getSuiteName(), testSuite.getDescription(), testSuite.getAppID(),
									testSuite.getSortOrder(), testSuite.getCreatedBy(), testSuite.getCreatedDateTime(), testSuite.getUpdatedBy(),
									testSuite.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in TestSuite");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuite(TestSuite) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testSuite
	 *            the test suite
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestSuite(final TestSuite testSuite) throws DataAccessException {
		LOG.info("Started updating data in TestSuite: " + testSuite);
		try {
			long returnlong = template.update(qTestSuite, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestSuite.testSuiteID.eq(testSuite.getTestSuiteID()))
							.set(qTestSuite.description, testSuite.getDescription()).set(qTestSuite.sortOrder, testSuite.getSortOrder())
							.set(qTestSuite.updatedBy, testSuite.getUpdatedBy()).set(qTestSuite.updatedDateTime, testSuite.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestSuite(TestSuite) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuite()
	 */
	@Override
	public List<TestSuite> getTestSuite() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuite() - start"); //$NON-NLS-1$
		}

		LOG.info("Inside getTestSuitesDaoImpl");
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite);
			List<TestSuite> returnList = template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName,
					qTestSuite.description, qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy,
					qTestSuite.updatedDateTime, qTestSuite.sortOrder));
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuite() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuiteById(int)
	 */
	@Override
	public TestSuite getTestSuiteById(int testSuiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where(qTestSuite.testSuiteID.eq(testSuiteId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description,
					qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime,
					qTestSuite.sortOrder));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public TestSuite getTestSuiteByName(String testSuiteName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where(qTestSuite.suiteName.eq(testSuiteName));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description,
					qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime,
					qTestSuite.sortOrder));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/**
	 * Gets the test suite by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the test suite by query
	 */
	private List<TestSuite> getTestSuiteByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<TestSuite> returnList = template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName,
				qTestSuite.description, qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy,
				qTestSuite.updatedDateTime, qTestSuite.sortOrder));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuiteIdByNameByAppId(int,
	 * java.lang.String)
	 */
	@Override
	public int getTestSuiteIdByNameByAppId(int appID, String suiteName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteIdByNameByAppId(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where((qTestSuite.appID.eq(appID)).and(qTestSuite.suiteName.eq(suiteName)));
			Integer testSuiteID = template.queryForObject(sqlQuery, qTestSuite.testSuiteID);
			if (testSuiteID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestSuiteIdByNameByAppId(int, String) - end"); //$NON-NLS-1$
				}
				return testSuiteID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteIdByNameByAppId(int, String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuitesByPlanId(int)
	 */
	@Override
	public List<TestSuite> getTestSuitesByPlanId(int testPlanId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuitesByPlanId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite);
		LOG.info("generated query : " + sqlQuery);
		return getTestSuiteByQuery(sqlQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuiteFilterByAppId(int)
	 */
	@Override
	public List<String> getTestSuiteFilterByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where(qTestSuite.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, qTestSuite.suiteName);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestSuiteDao#insertTestSuiteGetKey(com.exilant
	 * .tfw.pojo.input.TestSuite)
	 */
	@Override
	public int insertTestSuiteGetKey(final TestSuite testSuite) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteGetKey(TestSuite) - start"); //$NON-NLS-1$
		}

		int testSuiteId = 0;
		try {
			testSuiteId = template.insertWithKey(qTestSuite, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.createdBy,
									qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime, qTestSuite.sortOrder)
							.values(testSuite.getTestSuiteID(), testSuite.getSuiteName(), testSuite.getDescription(), testSuite.getAppID(),
									testSuite.getCreatedBy(), testSuite.getCreatedDateTime(), testSuite.getUpdatedBy(), testSuite.getUpdatedDateTime(),
									testSuite.getSortOrder()).executeWithKey(qTestSuite.testSuiteID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testSuiteId : " + testSuiteId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteGetKey(TestSuite) - end"); //$NON-NLS-1$
		}
		return testSuiteId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestSuiteDao#getAllTestSuites()
	 */
	@Override
	public List<TestSuite> getAllTestSuites() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestSuites() - start"); //$NON-NLS-1$
		}

		LOG.info("Inside getTestSuitesDaoImpl");
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite);
			List<TestSuite> returnList = template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName,
					qTestSuite.description, qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy,
					qTestSuite.updatedDateTime, qTestSuite.sortOrder));
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestSuites() - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuiteNamesByTestSuiteId
	 * (int)
	 */
	@Override
	public List<TestSuite> getTestSuiteNamesByTestSuiteId(int testSuiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteNamesByTestSuiteId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where(qTestSuite.testSuiteID.eq(testSuiteId));
			;
			List<TestSuite> returnList = template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.suiteName));
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteNamesByTestSuiteId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestSuiteDao#updateTestSuiteData(com.exilant
	 * .tfw.pojo.input.TestSuite)
	 */
	@Override
	public long updateTestSuiteData(final TestSuite testSuite) throws DataAccessException {
		LOG.info("Started updating data in TestSuite: " + testSuite);
		try {
			long returnlong = template.update(qTestSuite, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestSuite.testSuiteID.eq(testSuite.getTestSuiteID()))
							.set(qTestSuite.suiteName, testSuite.getSuiteName()).set(qTestSuite.description, testSuite.getDescription())
							.set(qTestSuite.sortOrder, testSuite.getSortOrder()).set(qTestSuite.updatedBy, testSuite.getUpdatedBy())
							.set(qTestSuite.updatedDateTime, testSuite.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestSuiteData(TestSuite) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.TestSuiteDao#getTestSuiteByAppId(int)
	 */
	@Override
	public List<TestSuite> getTestSuiteByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where(qTestSuite.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description,
					qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime,
					qTestSuite.sortOrder));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<TestSuiteUI> getTestSuiteUIByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteUIByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestSuiteUI> testSuiteUIs = new ArrayList<TestSuiteUI>();
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuite).where(qTestSuite.appID.eq(appId));
			LOG.info("Generated Query : " + sqlQuery);
			List<TestSuite> testSuites = template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName,
					qTestSuite.description, qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy,
					qTestSuite.updatedDateTime, qTestSuite.sortOrder));
			Iterator<TestSuite> iterator = testSuites.iterator();
			while (iterator.hasNext()) {
				TestSuite testSuite = (TestSuite) iterator.next();
				TestSuiteUI testSuiteUI = new TestSuiteUI();
				int testSuiteID = testSuite.getTestSuiteID();
				String suiteName = testSuite.getSuiteName();
				testSuiteUI.setTestSuiteId(testSuiteID);
				testSuiteUI.setTestSuiteName(suiteName);
				testSuiteUI.setTestSuiteDesc(testSuite.getDescription());
				long testScenariosCount = this.suiteScenarioMappingDao.getSuiteScenarioMappingsBySuiteIdCount(testSuiteID);
				testSuiteUI.setTestScenariosCount((int) testScenariosCount);
				SQLQuery sqlQueryName = template.newSqlQuery().from(qTestSuite).where(qTestSuite.suiteName.eq(suiteName).and(qTestSuite.appID.eq(appId)));
				long planCount = template.count(sqlQueryName);
				testSuiteUI.setTestPlanCount((int) planCount);
				testSuiteUIs.add(testSuiteUI);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteUIByAppID(int) - end"); //$NON-NLS-1$
			}
			return testSuiteUIs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	public List<TestSuite> getTestSuitesForFlowChart(int planId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuitesForFlowChart(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qPlanSuiteMapping).from(qTestSuite)
				.where(qPlanSuiteMapping.testPlanID.eq(planId).and(qPlanSuiteMapping.testSuiteID.eq(qTestSuite.testSuiteID)));
		List<TestSuite> returnList = template.query(sqlQuery, new MappingTestSuiteProjection(qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime, qTestSuite.sortOrder));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuitesForFlowChart(int) - end"); //$NON-NLS-1$
		}
		return returnList;
	}
}