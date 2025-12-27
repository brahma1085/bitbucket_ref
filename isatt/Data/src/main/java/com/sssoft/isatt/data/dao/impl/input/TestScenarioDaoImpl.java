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
import com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao;
import com.sssoft.isatt.data.dao.input.SuiteScenarioMappingDao;
import com.sssoft.isatt.data.dao.input.TestScenarioDao;
import com.sssoft.isatt.data.dao.input.TestSuiteDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QSuitescenariomapping;
import com.sssoft.isatt.data.generated.pojo.QTestscenario;
import com.sssoft.isatt.data.pojo.def.TestScenarioUI;
import com.sssoft.isatt.data.pojo.input.TestScenario;

/**
 * The Class TestScenarioDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestScenarioDao
 * interface
 */
public class TestScenarioDaoImpl implements TestScenarioDao {

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

	/** The q test scenario. */
	private QTestscenario qTestScenario = QTestscenario.testscenario;

	private QSuitescenariomapping qSuiteScenarioMapping = QSuitescenariomapping.suitescenariomapping;

	/**
	 * Sets the test suite dao.
	 * 
	 * @param testSuiteDao
	 *            the new test suite dao
	 */
	public void setTestSuiteDao(TestSuiteDao testSuiteDao) {
		this.testSuiteDao = testSuiteDao;
	}

	/** The test suite dao. */
	private TestSuiteDao testSuiteDao;

	private ScenarioCaseMappingDao scenarioCaseMappingDao;

	private SuiteScenarioMappingDao suiteScenarioMappingDao;

	public void setScenarioCaseMappingDao(ScenarioCaseMappingDao scenarioCaseMappingDao) {
		this.scenarioCaseMappingDao = scenarioCaseMappingDao;
	}

	public void setSuiteScenarioMappingDao(SuiteScenarioMappingDao suiteScenarioMappingDao) {
		this.suiteScenarioMappingDao = suiteScenarioMappingDao;
	}

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestScenarioDaoImpl.class);

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
	 * The Class MappingTestScenarioProjection.
	 */
	public class MappingTestScenarioProjection extends MappingProjection<TestScenario> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test scenario projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestScenarioProjection(Expression<?>... args) {
			super(TestScenario.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestScenario map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestScenario testScenario = new TestScenario();
			testScenario.setTestScenarioID(tuple.get(qTestScenario.testScenarioID));
			testScenario.setTestScenarioName(tuple.get(qTestScenario.testScenarioName));
			testScenario.setDescription(tuple.get(qTestScenario.description));
			testScenario.setAppID(tuple.get(qTestScenario.appID));
			testScenario.setSortOrder(tuple.get(qTestScenario.sortOrder));
			testScenario.setCreatedBy(tuple.get(qTestScenario.createdBy));
			testScenario.setCreatedDateTime(tuple.get(qTestScenario.createdDateTime));
			testScenario.setUpdatedBy(tuple.get(qTestScenario.updatedBy));
			testScenario.setUpdatedDateTime(tuple.get(qTestScenario.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestScenario object : " + testScenario);
			}
			return testScenario;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestScenario(final TestScenario testScenario) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenario(TestScenario) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestScenario: " + testScenario);
		try {
			result = template.insert(qTestScenario, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qTestScenario.testScenarioID, qTestScenario.testScenarioName, qTestScenario.description, qTestScenario.appID,
									qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime, qTestScenario.updatedBy,
									qTestScenario.updatedDateTime)
							.values(testScenario.getTestScenarioID(), testScenario.getTestScenarioName(), testScenario.getDescription(),
									testScenario.getAppID(), testScenario.getSortOrder(), testScenario.getCreatedBy(), testScenario.getCreatedDateTime(),
									testScenario.getUpdatedBy(), testScenario.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Test Scenario");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenario(TestScenario) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testScenario
	 *            the test scenario
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestScenario(final TestScenario testScenario) throws DataAccessException {
		LOG.info("Started updating data in TestScenario: " + testScenario);
		try {
			long returnlong = template.update(qTestScenario, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestScenario.testScenarioID.eq(testScenario.getTestScenarioID()))
							.set(qTestScenario.description, testScenario.getDescription()).set(qTestScenario.sortOrder, testScenario.getSortOrder())
							.set(qTestScenario.updatedBy, testScenario.getUpdatedBy())
							.set(qTestScenario.updatedDateTime, testScenario.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestScenario(TestScenario) - end"); //$NON-NLS-1$
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
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#updateTestScenarioData(com.
	 * exilant.tfw.pojo.input.TestScenario)
	 */
	@Override
	public long updateTestScenarioData(final TestScenario testScenario) throws DataAccessException {
		LOG.info("Started updating data in TestScenario: " + testScenario);
		try {
			long returnlong = template.update(qTestScenario, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestScenario.testScenarioID.eq(testScenario.getTestScenarioID()))
							.set(qTestScenario.description, testScenario.getDescription())
							.set(qTestScenario.testScenarioName, testScenario.getTestScenarioName())
							.set(qTestScenario.sortOrder, testScenario.getSortOrder()).set(qTestScenario.updatedBy, testScenario.getUpdatedBy())
							.set(qTestScenario.updatedDateTime, testScenario.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestScenarioData(TestScenario) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenario()
	 */
	@Override
	public List<TestScenario> getTestScenario() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenario() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID, qTestScenario.testScenarioName,
					qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime,
					qTestScenario.updatedBy, qTestScenario.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenarioById(int)
	 */
	@Override
	public TestScenario getTestScenarioById(int testScenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario).where(qTestScenario.testScenarioID.eq(testScenarioId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID, qTestScenario.testScenarioName,
					qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime,
					qTestScenario.updatedBy, qTestScenario.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public TestScenario getTestScenarioByName(String testScenarioName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByName(string) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario).where(qTestScenario.testScenarioName.eq(testScenarioName));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID, qTestScenario.testScenarioName,
					qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime,
					qTestScenario.updatedBy, qTestScenario.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<TestScenarioUI> getTestScenarioUIByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioUIByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestScenarioUI> testScenarioUIs = new ArrayList<TestScenarioUI>();
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario).where(qTestScenario.appID.eq(appId));
			LOG.info("Generated Query : " + sqlQuery);
			List<TestScenario> testScenarios = template.query(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID,
					qTestScenario.testScenarioName, qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy,
					qTestScenario.createdDateTime, qTestScenario.updatedBy, qTestScenario.updatedDateTime));
			Iterator<TestScenario> iterator = testScenarios.iterator();
			while (iterator.hasNext()) {
				TestScenario testScenario = (TestScenario) iterator.next();
				TestScenarioUI testScenarioUI = new TestScenarioUI();
				int scenarioId = testScenario.getTestScenarioID();
				String scenarioName = testScenario.getTestScenarioName();
				testScenarioUI.setTestScenarioID(scenarioId);
				testScenarioUI.setTestScenarioName(scenarioName);
				testScenarioUI.setDescription(testScenario.getDescription());
				long testCasesCount = this.scenarioCaseMappingDao.getScenarioCaseMappingsByScenarioIdCount(scenarioId);
				testScenarioUI.setTestCasesCount((int) testCasesCount);
				long testSuiteCount = this.suiteScenarioMappingDao.getSuiteScenarioMappingsByScenarioIdCount(scenarioId);
				testScenarioUI.setTestSuiteCount((int) testSuiteCount);
				testScenarioUIs.add(testScenarioUI);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestScenarioUIByAppID(int) - end"); //$NON-NLS-1$
			}
			return testScenarioUIs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenarioBySuiteId(int)
	 */
	@Override
	public List<TestScenario> getTestScenarioBySuiteId(int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioBySuiteId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario);
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID, qTestScenario.testScenarioName,
					qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime,
					qTestScenario.updatedBy, qTestScenario.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenarioId(int,
	 * java.lang.String)
	 */
	@Override
	public int getTestScenarioId(int appID, String testScenarioName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioId(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario)
					.where((qTestScenario.appID.eq(appID)).and(qTestScenario.testScenarioName.eq(testScenarioName)));
			Integer testScenarioID = template.queryForObject(sqlQuery, qTestScenario.testScenarioID);
			if (testScenarioID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestScenarioId(int, String) - end"); //$NON-NLS-1$
				}
				return testScenarioID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioId(int, String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestSuiteFilterBytestScenarioId
	 * (int)
	 */
	@Override
	public List<String> getTestSuiteFilterBytestScenarioId(int testScenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteFilterBytestScenarioId(int) - start"); //$NON-NLS-1$
		}

		try {
			TestScenario testScenario = this.getTestScenarioById(testScenarioId);
			List<String> returnList = this.testSuiteDao.getTestSuiteFilterByAppId(testScenario.getAppID());
			if (LOG.isDebugEnabled()) {
				LOG.debug("getTestSuiteFilterBytestScenarioId(int) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#insertTestScenarioGetKey(com
	 * .exilant.tfw.pojo.input.TestScenario)
	 */
	@Override
	public int insertTestScenarioGetKey(final TestScenario testScenario) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioGetKey(TestScenario) - start"); //$NON-NLS-1$
		}

		int testScenarioId = 0;
		try {
			testScenarioId = template.insertWithKey(qTestScenario, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qTestScenario.testScenarioID, qTestScenario.testScenarioName, qTestScenario.description, qTestScenario.appID,
									qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime, qTestScenario.updatedBy,
									qTestScenario.updatedDateTime)
							.values(testScenario.getTestScenarioID(), testScenario.getTestScenarioName(), testScenario.getDescription(),
									testScenario.getAppID(), testScenario.getSortOrder(), testScenario.getCreatedBy(), testScenario.getCreatedDateTime(),
									testScenario.getUpdatedBy(), testScenario.getUpdatedDateTime()).executeWithKey(qTestScenario.testScenarioID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testScenarioId : " + testScenarioId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioGetKey(TestScenario) - end"); //$NON-NLS-1$
		}
		return testScenarioId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenarioIdByNameAndSuiteID
	 * (java.lang.String, int)
	 */
	@Override
	public int getTestScenarioIdByNameAndSuiteID(String scenarioName, int testSuiteID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioIdByNameAndSuiteID(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario).where(qTestScenario.testScenarioName.eq(scenarioName));
			Integer testScenarioID = template.queryForObject(sqlQuery, qTestScenario.testScenarioID);
			if (testScenarioID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestScenarioIdByNameAndSuiteID(String, int) - end"); //$NON-NLS-1$
				}
				return testScenarioID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioIdByNameAndSuiteID(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenarioByAppId(int)
	 */
	@Override
	public List<TestScenario> getTestScenarioByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario).where(qTestScenario.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID, qTestScenario.testScenarioName,
					qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime,
					qTestScenario.updatedBy, qTestScenario.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestScenarioDao#getTestScenarioIdByName(java
	 * .lang.String)
	 */
	@Override
	public int getTestScenarioIdByName(String scenarioName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioIdByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestScenario).where(qTestScenario.testScenarioName.eq(scenarioName));
			Integer testSuiteID = template.queryForObject(sqlQuery, qTestScenario.testScenarioID);
			if (testSuiteID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestScenarioIdByName(String) - end"); //$NON-NLS-1$
				}
				return testSuiteID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioIdByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	public List<TestScenario> getTestScenariosForFlowChart(int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenariosForFlowChart(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qSuiteScenarioMapping).from(qTestScenario)
				.where(qSuiteScenarioMapping.testSuiteID.eq(suiteId).and(qSuiteScenarioMapping.testScenarioID.eq(qTestScenario.testScenarioID)));
		List<TestScenario> returnList = template.query(sqlQuery, new MappingTestScenarioProjection(qTestScenario.testScenarioID,
				qTestScenario.testScenarioName, qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy,
				qTestScenario.createdDateTime, qTestScenario.updatedBy, qTestScenario.updatedDateTime));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenariosForFlowChart(int) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

}