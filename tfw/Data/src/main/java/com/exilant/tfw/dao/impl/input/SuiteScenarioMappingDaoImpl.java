package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.SuiteScenarioMappingDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QSuitescenariomapping;
import com.exilant.tfw.generated.pojo.QTestscenario;
import com.exilant.tfw.generated.pojo.QTestsuite;
import com.exilant.tfw.pojo.input.SuiteScenarioMapping;
import com.exilant.tfw.pojo.input.TestScenario;
import com.exilant.tfw.pojo.input.TestSuite;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class SuiteScenarioMappingDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for
 * SuiteScenarioMappingDaoImpl interface
 */
public class SuiteScenarioMappingDaoImpl implements SuiteScenarioMappingDao {

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

	/** The q suite scenario mapping. */
	private QSuitescenariomapping qSuiteScenarioMapping = QSuitescenariomapping.suitescenariomapping;

	/** The q test suite. */
	private QTestsuite qTestSuite = QTestsuite.testsuite;

	/** The q test scenario. */
	private QTestscenario qTestScenario = QTestscenario.testscenario;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SuiteScenarioMappingDaoImpl.class);

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
	 * The Class SuiteScenarioMappingProjection.
	 */
	private class SuiteScenarioMappingProjection extends MappingProjection<SuiteScenarioMapping> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new suite scenario mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public SuiteScenarioMappingProjection(Expression<?>... args) {
			super(SuiteScenarioMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected SuiteScenarioMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			SuiteScenarioMapping suiteScenarioMapping = new SuiteScenarioMapping();
			suiteScenarioMapping.setSuiteScenarioMappingID(tuple.get(qSuiteScenarioMapping.suiteScenarioMappingID));
			suiteScenarioMapping.setTestSuiteID(tuple.get(qSuiteScenarioMapping.testSuiteID));
			suiteScenarioMapping.setTestScenarioID(tuple.get(qSuiteScenarioMapping.testScenarioID));
			suiteScenarioMapping.setCreatedBy(tuple.get(qSuiteScenarioMapping.createdBy));
			suiteScenarioMapping.setCreatedDateTime(tuple.get(qSuiteScenarioMapping.createdDateTime));
			suiteScenarioMapping.setUpdatedBy(tuple.get(qSuiteScenarioMapping.updatedBy));
			suiteScenarioMapping.setUpdatedDateTime(tuple.get(qSuiteScenarioMapping.updatedDateTime));

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
			suiteScenarioMapping.setTestScenario(testScenario);

			TestSuite testSuite = new TestSuite();
			testSuite.setTestSuiteID(tuple.get(qTestSuite.testSuiteID));
			testSuite.setSuiteName(tuple.get(qTestSuite.suiteName));
			testSuite.setDescription(tuple.get(qTestSuite.description));
			testSuite.setAppID(tuple.get(qTestSuite.appID));
			testSuite.setSortOrder(tuple.get(qTestSuite.sortOrder));
			testSuite.setCreatedBy(tuple.get(qTestSuite.createdBy));
			testSuite.setCreatedDateTime(tuple.get(qTestSuite.createdDateTime));
			testSuite.setUpdatedBy(tuple.get(qTestSuite.updatedBy));
			testSuite.setUpdatedDateTime(tuple.get(qTestSuite.updatedDateTime));
			suiteScenarioMapping.setTestSuite(testSuite);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SuiteScenarioMapping object : " + suiteScenarioMapping);
			}
			return suiteScenarioMapping;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.SuiteScenarioMappingDao#
	 * getSuiteScenarioMappingBySuiteId(int)
	 */
	@Override
	public List<SuiteScenarioMapping> getSuiteScenarioMappingBySuiteId(int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qSuiteScenarioMapping)
				.from(qTestScenario)
				.from(qTestSuite)
				.where(qSuiteScenarioMapping.testScenarioID.eq(qTestScenario.testScenarioID)
						.and(qSuiteScenarioMapping.testSuiteID.eq(qTestSuite.testSuiteID)).and(qSuiteScenarioMapping.testSuiteID.eq(suiteId)))
				.orderBy(qSuiteScenarioMapping.suiteScenarioMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new SuiteScenarioMappingProjection(qSuiteScenarioMapping.suiteScenarioMappingID,
				qSuiteScenarioMapping.testSuiteID, qSuiteScenarioMapping.testScenarioID, qSuiteScenarioMapping.createdBy,
				qSuiteScenarioMapping.createdDateTime, qSuiteScenarioMapping.updatedBy, qSuiteScenarioMapping.updatedDateTime,
				qTestScenario.testScenarioID, qTestScenario.testScenarioName, qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder,
				qTestScenario.createdBy, qTestScenario.createdDateTime, qTestScenario.updatedBy, qTestScenario.updatedDateTime, qTestSuite.testSuiteID,
				qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.sortOrder, qTestSuite.createdBy, qTestSuite.createdDateTime,
				qTestSuite.updatedBy, qTestSuite.updatedDateTime));
	}

	@Override
	public List<SuiteScenarioMapping> getSuiteScenarioMappingByScenarioId(int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingByScenarioId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qSuiteScenarioMapping)
				.from(qTestScenario)
				.from(qTestSuite)
				.where(qSuiteScenarioMapping.testScenarioID.eq(qTestScenario.testScenarioID)
						.and(qSuiteScenarioMapping.testSuiteID.eq(qTestSuite.testSuiteID)).and(qSuiteScenarioMapping.testScenarioID.eq(scenarioId)))
				.orderBy(qSuiteScenarioMapping.suiteScenarioMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new SuiteScenarioMappingProjection(qSuiteScenarioMapping.suiteScenarioMappingID,
				qSuiteScenarioMapping.testSuiteID, qSuiteScenarioMapping.testScenarioID, qSuiteScenarioMapping.createdBy,
				qSuiteScenarioMapping.createdDateTime, qSuiteScenarioMapping.updatedBy, qSuiteScenarioMapping.updatedDateTime,
				qTestScenario.testScenarioID, qTestScenario.testScenarioName, qTestScenario.description, qTestScenario.appID, qTestScenario.sortOrder,
				qTestScenario.createdBy, qTestScenario.createdDateTime, qTestScenario.updatedBy, qTestScenario.updatedDateTime, qTestSuite.testSuiteID,
				qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.sortOrder, qTestSuite.createdBy, qTestSuite.createdDateTime,
				qTestSuite.updatedBy, qTestSuite.updatedDateTime));
	}

	/**
	 * The Class MappingSuiteScenarioMappingProjection.
	 */
	public class MappingSuiteScenarioMappingProjection extends MappingProjection<SuiteScenarioMapping> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping suite scenario mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingSuiteScenarioMappingProjection(Expression<?>... args) {
			super(SuiteScenarioMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected SuiteScenarioMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			SuiteScenarioMapping suiteScenarioMapping = new SuiteScenarioMapping();
			suiteScenarioMapping.setSuiteScenarioMappingID(tuple.get(qSuiteScenarioMapping.suiteScenarioMappingID));
			suiteScenarioMapping.setTestSuiteID(tuple.get(qSuiteScenarioMapping.testSuiteID));
			suiteScenarioMapping.setTestScenarioID(tuple.get(qSuiteScenarioMapping.testScenarioID));
			suiteScenarioMapping.setCreatedBy(tuple.get(qSuiteScenarioMapping.createdBy));
			suiteScenarioMapping.setCreatedDateTime(tuple.get(qSuiteScenarioMapping.createdDateTime));
			suiteScenarioMapping.setUpdatedBy(tuple.get(qSuiteScenarioMapping.updatedBy));
			suiteScenarioMapping.setUpdatedDateTime(tuple.get(qSuiteScenarioMapping.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SuiteScenarioMapping object : " + suiteScenarioMapping);
			}
			return suiteScenarioMapping;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertSuiteScenarioMappingDao(final SuiteScenarioMapping suiteScenarioMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMappingDao(SuiteScenarioMapping) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in SuiteScenarioMapping: " + suiteScenarioMapping);
		try {
			result = template.insert(qSuiteScenarioMapping, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qSuiteScenarioMapping.suiteScenarioMappingID, qSuiteScenarioMapping.testSuiteID,
									qSuiteScenarioMapping.testScenarioID, qSuiteScenarioMapping.createdBy, qSuiteScenarioMapping.createdDateTime,
									qSuiteScenarioMapping.updatedBy, qSuiteScenarioMapping.updatedDateTime)
							.values(suiteScenarioMapping.getSuiteScenarioMappingID(), suiteScenarioMapping.getTestSuiteID(),
									suiteScenarioMapping.getTestScenarioID(), suiteScenarioMapping.getCreatedBy(),
									suiteScenarioMapping.getCreatedDateTime(), suiteScenarioMapping.getUpdatedBy(),
									suiteScenarioMapping.getUpdatedDateTime()).execute();
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
			LOG.debug("insertSuiteScenarioMappingDao(SuiteScenarioMapping) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateSuiteScenarioMappingDao(final SuiteScenarioMapping suiteScenarioMapping) throws DataAccessException {
		LOG.info("Started updating data in SuiteScenarioMapping: " + suiteScenarioMapping);
		try {
			long returnlong = template.update(qSuiteScenarioMapping, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause
							.where(qSuiteScenarioMapping.suiteScenarioMappingID.eq(suiteScenarioMapping.getSuiteScenarioMappingID()))
							.set(qSuiteScenarioMapping.updatedBy, suiteScenarioMapping.getUpdatedBy())
							.set(qSuiteScenarioMapping.updatedDateTime, suiteScenarioMapping.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSuiteScenarioMappingDao(SuiteScenarioMapping) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.input.SuiteScenarioMappingDao#
	 * insertSuiteScenarioMappingGetKey
	 * (com.exilant.tfw.pojo.input.SuiteScenarioMapping)
	 */
	@Override
	public int insertSuiteScenarioMappingGetKey(final SuiteScenarioMapping suiteScenarioMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMappingGetKey(SuiteScenarioMapping) - start"); //$NON-NLS-1$
		}

		int suiteScenarioMappingId = 0;
		try {
			suiteScenarioMappingId = template.insertWithKey(qSuiteScenarioMapping, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qSuiteScenarioMapping.suiteScenarioMappingID, qSuiteScenarioMapping.testSuiteID,
									qSuiteScenarioMapping.testScenarioID, qSuiteScenarioMapping.createdBy, qSuiteScenarioMapping.createdDateTime,
									qSuiteScenarioMapping.updatedBy, qSuiteScenarioMapping.updatedDateTime)
							.values(suiteScenarioMapping.getSuiteScenarioMappingID(), suiteScenarioMapping.getTestSuiteID(),
									suiteScenarioMapping.getTestScenarioID(), suiteScenarioMapping.getCreatedBy(),
									suiteScenarioMapping.getCreatedDateTime(), suiteScenarioMapping.getUpdatedBy(),
									suiteScenarioMapping.getUpdatedDateTime()).executeWithKey(qSuiteScenarioMapping.suiteScenarioMappingID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated suiteScenarioMappingId : " + suiteScenarioMappingId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSuiteScenarioMappingGetKey(SuiteScenarioMapping) - end"); //$NON-NLS-1$
		}
		return suiteScenarioMappingId;
	}

	/**
	 * Gets the suite scenario mappings by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the suite scenario mappings by query
	 */
	public List<SuiteScenarioMapping> getSuiteScenarioMappingsByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingsByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<SuiteScenarioMapping> returnList = template.query(sqlQuery, new MappingSuiteScenarioMappingProjection(
				qSuiteScenarioMapping.suiteScenarioMappingID, qSuiteScenarioMapping.testSuiteID, qSuiteScenarioMapping.testScenarioID,
				qSuiteScenarioMapping.createdBy, qSuiteScenarioMapping.createdDateTime, qSuiteScenarioMapping.updatedBy,
				qSuiteScenarioMapping.updatedDateTime));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingsByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.SuiteScenarioMappingDao#
	 * getSuiteScenarioMapByTestSuiteId(int, int)
	 */
	@Override
	public int getSuiteScenarioMapByTestSuiteId(int scenarioId, int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMapByTestSuiteId(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSuiteScenarioMapping)
					.where((qSuiteScenarioMapping.testScenarioID.eq(scenarioId)).and(qSuiteScenarioMapping.testSuiteID.eq(suiteId)));
			Integer suiteScenarioMapId = template.queryForObject(sqlQuery, qSuiteScenarioMapping.suiteScenarioMappingID);
			if (suiteScenarioMapId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getSuiteScenarioMapByTestSuiteId(int, int) - end"); //$NON-NLS-1$
				}
				return suiteScenarioMapId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMapByTestSuiteId(int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.SuiteScenarioMappingDao#
	 * getSuiteScenarioMapByDependentIds(int, int)
	 */
	@Override
	public int getSuiteScenarioMapByDependentIds(int scenarioId, int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMapByDependentIds(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSuiteScenarioMapping)
					.where(qSuiteScenarioMapping.testScenarioID.eq(scenarioId).and(qSuiteScenarioMapping.testSuiteID.eq(suiteId)));
			Integer suiteScenarioMapId = template.queryForObject(sqlQuery, qSuiteScenarioMapping.suiteScenarioMappingID);
			if (suiteScenarioMapId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getSuiteScenarioMapByDependentIds(int, int) - end"); //$NON-NLS-1$
				}
				return suiteScenarioMapId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMapByDependentIds(int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}
	
	@Override
	public long getSuiteScenarioMappingsBySuiteIdCount(int suiteId)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingsBySuiteIdCount(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qSuiteScenarioMapping)
				.from(qTestSuite)
				.from(qTestScenario)
				.where(qSuiteScenarioMapping.testSuiteID.eq(suiteId).and(qSuiteScenarioMapping.testSuiteID.eq(qTestSuite.testSuiteID))
						.and(qSuiteScenarioMapping.testScenarioID.eq(qTestScenario.testScenarioID))).orderBy(qSuiteScenarioMapping.suiteScenarioMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.count(sqlQuery);
	
	}
	
	@Override
	public long getSuiteScenarioMappingsByScenarioIdCount(int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSuiteScenarioMappingsByScenarioIdCount(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qSuiteScenarioMapping)
				.from(qTestSuite)
				.from(qTestScenario)
				.where(qSuiteScenarioMapping.testScenarioID.eq(scenarioId).and(qSuiteScenarioMapping.testSuiteID.eq(qTestSuite.testSuiteID))
						.and(qSuiteScenarioMapping.testScenarioID.eq(qTestScenario.testScenarioID))).orderBy(qSuiteScenarioMapping.suiteScenarioMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.count(sqlQuery);
	}

	@Override
	public long deleteSuiteScenarioMappingById(final int scenarioId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSuiteScenarioMappingById(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		try {
			result = template.delete(qSuiteScenarioMapping, new SqlDeleteCallback() {
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qSuiteScenarioMapping.testScenarioID.eq(scenarioId)).execute();

					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
		} catch (Exception e) {
			LOG.error("deleteSuiteScenarioMappingById(int)", e); //$NON-NLS-1$

			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSuiteScenarioMappingById(int) - end"); //$NON-NLS-1$
		}
		return result;
	}
	
	@Override
	public long deleteSuiteScenarioMappingBySuiteId(final int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSuiteScenarioMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		try {
			result = template.delete(qSuiteScenarioMapping, new SqlDeleteCallback() {
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qSuiteScenarioMapping.testSuiteID.eq(suiteId)).execute();

					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
		} catch (Exception e) {
			LOG.error("deleteSuiteScenarioMappingBySuiteId(int)", e); //$NON-NLS-1$

			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSuiteScenarioMappingBySuiteId(int) - end"); //$NON-NLS-1$
		}
		return result;
	}

}