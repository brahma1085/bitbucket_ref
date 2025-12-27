package com.sssoft.isatt.data.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;
import com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QScenariocasemapping;
import com.sssoft.isatt.data.generated.pojo.QTestcase;
import com.sssoft.isatt.data.generated.pojo.QTestscenario;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestScenario;

/**
 * The Class ScenarioCaseMappingDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TaskDao interface
 */
public class ScenarioCaseMappingDaoImpl implements ScenarioCaseMappingDao {

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

	/** The q scenario case mapping. */
	private QScenariocasemapping qScenarioCaseMapping = QScenariocasemapping.scenariocasemapping;

	/** The q test scenario. */
	private QTestscenario qTestScenario = QTestscenario.testscenario;

	/** The q test case. */
	private QTestcase qTestCase = QTestcase.testcase;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ScenarioCaseMappingDaoImpl.class);

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
	 * The Class ScenarioCaseMappingProjection.
	 */
	private class ScenarioCaseMappingProjection extends MappingProjection<ScenarioCaseMapping> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new scenario case mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public ScenarioCaseMappingProjection(Expression<?>... args) {
			super(ScenarioCaseMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ScenarioCaseMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			ScenarioCaseMapping scenarioCaseMapping = new ScenarioCaseMapping();
			scenarioCaseMapping.setScenarioCaseMappingID(tuple.get(qScenarioCaseMapping.scenarioCaseMappingID));
			scenarioCaseMapping.setTestScenarioID(tuple.get(qScenarioCaseMapping.testScenarioID));
			scenarioCaseMapping.setTestCaseID(tuple.get(qScenarioCaseMapping.testCaseID));
			scenarioCaseMapping.setCreatedBy(tuple.get(qScenarioCaseMapping.createdBy));
			scenarioCaseMapping.setCreatedDateTime(tuple.get(qScenarioCaseMapping.createdDateTime));
			scenarioCaseMapping.setUpdatedBy(tuple.get(qScenarioCaseMapping.updatedBy));
			scenarioCaseMapping.setUpdatedDateTime(tuple.get(qScenarioCaseMapping.updatedDateTime));

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
			scenarioCaseMapping.setTestScenario(testScenario);

			TestCase testCase = new TestCase();
			testCase.setTestCaseID(tuple.get(qTestCase.testCaseID));
			testCase.setClassificationTag(tuple.get(qTestCase.classificationTag));
			testCase.setRunnerID(tuple.get(qTestCase.runnerID));
			testCase.setDescription(tuple.get(qTestCase.description));
			testCase.setActive(tuple.get(qTestCase.active));
			testCase.setPositive(tuple.get(qTestCase.positive));
			testCase.setSortOrder(tuple.get(qTestCase.sortOrder));
			testCase.setCreatedBy(tuple.get(qTestCase.createdBy));
			testCase.setCreatedDateTime(tuple.get(qTestCase.createdDateTime));
			testCase.setUpdatedBy(tuple.get(qTestCase.updatedBy));
			testCase.setUpdatedDateTime(tuple.get(qTestCase.updatedDateTime));
			testCase.setConditionGroupID(tuple.get(qTestCase.conditionGroupID));
			testCase.setCaseName(tuple.get(qTestCase.caseName));
			scenarioCaseMapping.setTestCase(testCase);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning ScenarioCaseMapping object : " + scenarioCaseMapping);
			}
			return scenarioCaseMapping;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao#
	 * getScenarioCaseMappingsByScenarioId(int)
	 */
	@Override
	public List<ScenarioCaseMapping> getScenarioCaseMappingsByScenarioId(int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByScenarioId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qScenarioCaseMapping)
				.from(qTestScenario)
				.from(qTestCase)
				.where(qScenarioCaseMapping.testScenarioID.eq(scenarioId).and(qScenarioCaseMapping.testScenarioID.eq(qTestScenario.testScenarioID))
						.and(qScenarioCaseMapping.testCaseID.eq(qTestCase.testCaseID))).orderBy(qScenarioCaseMapping.scenarioCaseMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new ScenarioCaseMappingProjection(qScenarioCaseMapping.scenarioCaseMappingID, qScenarioCaseMapping.testScenarioID,
				qScenarioCaseMapping.testCaseID, qScenarioCaseMapping.createdBy, qScenarioCaseMapping.createdDateTime, qScenarioCaseMapping.updatedBy,
				qScenarioCaseMapping.updatedDateTime, qTestScenario.testScenarioID, qTestScenario.testScenarioName, qTestScenario.description,
				qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime, qTestScenario.updatedBy,
				qTestScenario.updatedDateTime, qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName, qTestCase.runnerID,
				qTestCase.description, qTestCase.active, qTestCase.positive, qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime,
				qTestCase.updatedBy, qTestCase.updatedDateTime, qTestCase.conditionGroupID));
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao#
	 * getScenarioCaseMappingsByCaseId(int)
	 */
	@Override
	public List<ScenarioCaseMapping> getScenarioCaseMappingsByCaseId(int caseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByCaseId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qScenarioCaseMapping)
				.from(qTestScenario)
				.from(qTestCase)
				.where(qScenarioCaseMapping.testCaseID.eq(caseId).and(qScenarioCaseMapping.testScenarioID.eq(qTestScenario.testScenarioID))
						.and(qScenarioCaseMapping.testCaseID.eq(qTestCase.testCaseID))).orderBy(qScenarioCaseMapping.scenarioCaseMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new ScenarioCaseMappingProjection(qScenarioCaseMapping.scenarioCaseMappingID, qScenarioCaseMapping.testScenarioID,
				qScenarioCaseMapping.testCaseID, qScenarioCaseMapping.createdBy, qScenarioCaseMapping.createdDateTime, qScenarioCaseMapping.updatedBy,
				qScenarioCaseMapping.updatedDateTime, qTestScenario.testScenarioID, qTestScenario.testScenarioName, qTestScenario.description,
				qTestScenario.appID, qTestScenario.sortOrder, qTestScenario.createdBy, qTestScenario.createdDateTime, qTestScenario.updatedBy,
				qTestScenario.updatedDateTime, qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName, qTestCase.runnerID,
				qTestCase.description, qTestCase.active, qTestCase.positive, qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime,
				qTestCase.updatedBy, qTestCase.updatedDateTime, qTestCase.conditionGroupID));
	}
	

	@Override
	public long getScenarioCaseMappingsByScenarioIdCount(int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByScenarioIdCount(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qScenarioCaseMapping)
				.from(qTestScenario)
				.from(qTestCase)
				.where(qScenarioCaseMapping.testScenarioID.eq(scenarioId).and(qScenarioCaseMapping.testScenarioID.eq(qTestScenario.testScenarioID))
						.and(qScenarioCaseMapping.testCaseID.eq(qTestCase.testCaseID))).orderBy(qScenarioCaseMapping.scenarioCaseMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.count(sqlQuery);
	}
	
	/**
	 * The Class MappingScenarioCaseMappingProjection.
	 */
	public class MappingScenarioCaseMappingProjection extends MappingProjection<ScenarioCaseMapping> {

		/** Logger for this class. */
		private final Logger LOG = Logger.getLogger(MappingScenarioCaseMappingProjection.class);

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping scenario case mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingScenarioCaseMappingProjection(Expression<?>... args) {
			super(ScenarioCaseMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ScenarioCaseMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}
			ScenarioCaseMapping scenarioCaseMapping = new ScenarioCaseMapping();
			scenarioCaseMapping.setScenarioCaseMappingID(tuple.get(qScenarioCaseMapping.scenarioCaseMappingID));
			scenarioCaseMapping.setTestScenarioID(tuple.get(qScenarioCaseMapping.scenarioCaseMappingID));
			scenarioCaseMapping.setTestCaseID(tuple.get(qScenarioCaseMapping.testCaseID));
			scenarioCaseMapping.setCreatedBy(tuple.get(qScenarioCaseMapping.createdBy));
			scenarioCaseMapping.setCreatedDateTime(tuple.get(qScenarioCaseMapping.createdDateTime));
			scenarioCaseMapping.setUpdatedBy(tuple.get(qScenarioCaseMapping.updatedBy));
			scenarioCaseMapping.setUpdatedDateTime(tuple.get(qScenarioCaseMapping.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning ScenarioCaseMapping object : " + scenarioCaseMapping);
			}
			return scenarioCaseMapping;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertScenarioCaseMappingDao(final ScenarioCaseMapping scenarioCaseMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingDao(ScenarioCaseMapping) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in ScenarioCaseMapping: " + scenarioCaseMapping);
		try {
			result = template.insert(qScenarioCaseMapping, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qScenarioCaseMapping.scenarioCaseMappingID, qScenarioCaseMapping.testScenarioID, qScenarioCaseMapping.testCaseID, qScenarioCaseMapping.createdBy, qScenarioCaseMapping.createdDateTime, qScenarioCaseMapping.updatedBy, qScenarioCaseMapping.updatedDateTime).values(scenarioCaseMapping.getScenarioCaseMappingID(), scenarioCaseMapping.getTestScenarioID(), scenarioCaseMapping.getTestCaseID(), scenarioCaseMapping.getCreatedBy(), scenarioCaseMapping.getCreatedDateTime(), scenarioCaseMapping.getUpdatedBy(), scenarioCaseMapping.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in ConditionGroup");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingDao(ScenarioCaseMapping) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateScenarioCaseMappingDao(final ScenarioCaseMapping scenarioCaseMapping) throws DataAccessException {
		LOG.info("Started updating data in ScenarioCaseMapping: " + scenarioCaseMapping);
		try {
			long returnlong = template.update(qScenarioCaseMapping, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qScenarioCaseMapping.scenarioCaseMappingID.eq(scenarioCaseMapping.getScenarioCaseMappingID())).set(qScenarioCaseMapping.updatedBy, scenarioCaseMapping.getUpdatedBy()).set(qScenarioCaseMapping.updatedDateTime, scenarioCaseMapping.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScenarioCaseMappingDao(ScenarioCaseMapping) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao#
	 * insertScenarioCaseMappingGetKey
	 * (com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping)
	 */
	@Override
	public int insertScenarioCaseMappingGetKey(final ScenarioCaseMapping scenarioCaseMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingGetKey(ScenarioCaseMapping) - start"); //$NON-NLS-1$
		}

		int scenarioCaseMappingId = 0;
		try {
			scenarioCaseMappingId = template.insertWithKey(qScenarioCaseMapping, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qScenarioCaseMapping.scenarioCaseMappingID, qScenarioCaseMapping.testScenarioID, qScenarioCaseMapping.testCaseID, qScenarioCaseMapping.createdBy, qScenarioCaseMapping.createdDateTime, qScenarioCaseMapping.updatedBy, qScenarioCaseMapping.updatedDateTime).values(scenarioCaseMapping.getScenarioCaseMappingID(), scenarioCaseMapping.getTestScenarioID(), scenarioCaseMapping.getTestCaseID(), scenarioCaseMapping.getCreatedBy(), scenarioCaseMapping.getCreatedDateTime(), scenarioCaseMapping.getUpdatedBy(), scenarioCaseMapping.getUpdatedDateTime()).executeWithKey(qScenarioCaseMapping.scenarioCaseMappingID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated scenarioCaseMappingId : " + scenarioCaseMappingId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScenarioCaseMappingGetKey(ScenarioCaseMapping) - end"); //$NON-NLS-1$
		}
		return scenarioCaseMappingId;
	}

	/**
	 * Gets the scenario case mappings by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the scenario case mappings by query
	 */
	public List<ScenarioCaseMapping> getScenarioCaseMappingsByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<ScenarioCaseMapping> returnList = template.query(sqlQuery, new MappingScenarioCaseMappingProjection(qScenarioCaseMapping.scenarioCaseMappingID, qScenarioCaseMapping.testScenarioID, qScenarioCaseMapping.testCaseID, qScenarioCaseMapping.createdBy, qScenarioCaseMapping.createdDateTime, qScenarioCaseMapping.updatedBy, qScenarioCaseMapping.updatedDateTime));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ScenarioCaseMappingDao#
	 * getScenarioCaseMapByTestScenarioId(int, int)
	 */
	@Override
	public int getScenarioCaseMapByTestScenarioId(int testScenarioId, int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMapByTestScenarioId(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScenarioCaseMapping)
					.where((qScenarioCaseMapping.testScenarioID.eq(testScenarioId)).and(qScenarioCaseMapping.testCaseID.eq(testCaseId)));
			Integer scenarioCaseMapId = template.queryForObject(sqlQuery, qScenarioCaseMapping.scenarioCaseMappingID);
			if (scenarioCaseMapId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getScenarioCaseMapByTestScenarioId(int, int) - end"); //$NON-NLS-1$
				}
				return scenarioCaseMapId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMapByTestScenarioId(int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}
	
	@Override
	public long getScenarioCaseMappingsByCaseIdCount(int caseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScenarioCaseMappingsByCaseIdCount(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qScenarioCaseMapping)
				.from(qTestScenario)
				.from(qTestCase)
				.where(qScenarioCaseMapping.testCaseID.eq(caseId).and(qScenarioCaseMapping.testScenarioID.eq(qTestScenario.testScenarioID))
						.and(qScenarioCaseMapping.testCaseID.eq(qTestCase.testCaseID))).orderBy(qScenarioCaseMapping.scenarioCaseMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.count(sqlQuery);
	}
	
	@Override
	public long deleteScenarioCaseMappingById(final int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteScenarioCaseMappingById(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		try {
			result = template.delete(qScenarioCaseMapping, new SqlDeleteCallback() {
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qScenarioCaseMapping.testScenarioID.eq(scenarioId)).execute();

					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
		} catch (Exception e) {
			LOG.error("deleteScenarioCaseMappingById(int)", e); //$NON-NLS-1$

			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteScenarioCaseMappingById(int) - end"); //$NON-NLS-1$
		}
		return result;
	}
	
	@Override
	public long deleteScenarioCaseMappingByCaseId(final int caseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteScenarioCaseMappingByCaseId(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		try {
			result = template.delete(qScenarioCaseMapping, new SqlDeleteCallback() {
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qScenarioCaseMapping.testCaseID.eq(caseId)).execute();

					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
		} catch (Exception e) {
			LOG.error("deleteScenarioCaseMappingByCaseId(int)", e); //$NON-NLS-1$

			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteScenarioCaseMappingByCaseId(int) - end"); //$NON-NLS-1$
		}
		return result;
	}


}