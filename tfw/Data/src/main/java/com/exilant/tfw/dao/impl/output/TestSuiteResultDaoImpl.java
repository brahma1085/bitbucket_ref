package com.exilant.tfw.dao.impl.output;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.output.TestSuiteResultDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QTestsuite;
import com.exilant.tfw.generated.pojo.QTestsuiteresult;
import com.exilant.tfw.pojo.output.TestSuiteResult;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestSuiteResultDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestSuiteResultDao
 * interface
 */
public class TestSuiteResultDaoImpl implements TestSuiteResultDao {

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

	/** The q test suite result. */
	private QTestsuiteresult qTestSuiteResult = QTestsuiteresult.testsuiteresult;

	/** The q test suite. */
	private QTestsuite qTestSuite = QTestsuite.testsuite;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestSuiteResultDaoImpl.class);

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
	 * The Class MappingTestSuiteResultProjection.
	 */
	public class MappingTestSuiteResultProjection extends MappingProjection<TestSuiteResult> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test suite result projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestSuiteResultProjection(Expression<?>... args) {
			super(TestSuiteResult.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestSuiteResult map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestSuiteResult testSuiteResult = new TestSuiteResult();
			testSuiteResult.setTestSuiteResultID(tuple.get(qTestSuiteResult.testSuiteResultID));
			testSuiteResult.setTestSuiteID(tuple.get(qTestSuiteResult.testSuiteID));
			testSuiteResult.setTestPlanID(tuple.get(qTestSuiteResult.testPlanID));
			testSuiteResult.setResult(tuple.get(qTestSuiteResult.result));
			testSuiteResult.setStartDateTime(tuple.get(qTestSuiteResult.startDateTime));
			testSuiteResult.setEndDateTime(tuple.get(qTestSuiteResult.endDateTime));
			testSuiteResult.setTestRunID(tuple.get(qTestSuiteResult.testRunID));
			testSuiteResult.setPercentageFailCount(tuple.get(qTestSuiteResult.percentageFailCount));
			testSuiteResult.setPercentagePassCount(tuple.get(qTestSuiteResult.percentagePassCount));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestSuiteResult object : " + testSuiteResult);
			}
			return testSuiteResult;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestSuiteResult(final TestSuiteResult testSuiteResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteResult(TestSuiteResult) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestSuiteResult: " + testSuiteResult);
		try {
			result = template.insert(qTestSuiteResult, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestSuiteResult.testSuiteResultID, qTestSuiteResult.testSuiteID, qTestSuiteResult.testPlanID, qTestSuiteResult.result, qTestSuiteResult.startDateTime, qTestSuiteResult.endDateTime, qTestSuiteResult.percentageFailCount, qTestSuiteResult.percentagePassCount, qTestSuiteResult.testRunID).values(testSuiteResult.getTestSuiteResultID(), testSuiteResult.getTestSuiteID(), testSuiteResult.getTestPlanID(), testSuiteResult.isResult(), testSuiteResult.getStartDateTime(), testSuiteResult.getEndDateTime(), testSuiteResult.getPercentageFailCount(), testSuiteResult.getPercentagePassCount(), testSuiteResult.getTestRunID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in TestSuiteResult");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteResult(TestSuiteResult) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testSuiteResult
	 *            the test suite result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestSuiteResult(final TestSuiteResult testSuiteResult) throws DataAccessException {
		LOG.info("Started updating data in TestSuiteResult: " + testSuiteResult);
		try {
			long returnlong = template.update(qTestSuiteResult, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestSuiteResult.testSuiteResultID.eq(testSuiteResult.getTestSuiteResultID())).set(qTestSuiteResult.result, testSuiteResult.isResult()).set(qTestSuiteResult.percentageFailCount, testSuiteResult.getPercentageFailCount()).set(qTestSuiteResult.percentagePassCount, testSuiteResult.getPercentagePassCount()).set(qTestSuiteResult.endDateTime, testSuiteResult.getEndDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestSuiteResult(TestSuiteResult) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.output.TestSuiteResultDao#getTestSuiteResult()
	 */
	@Override
	public List<TestSuiteResult> getTestSuiteResult() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteResult() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuiteResult).where(qTestSuiteResult.testSuiteID.eq(qTestSuite.testSuiteID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestSuiteResultProjection(qTestSuiteResult.testSuiteResultID, qTestSuiteResult.testSuiteID,
					qTestSuiteResult.testPlanID, qTestSuiteResult.result, qTestSuiteResult.startDateTime, qTestSuiteResult.endDateTime,
					qTestSuiteResult.percentageFailCount, qTestSuiteResult.percentagePassCount, qTestSuiteResult.testRunID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestSuiteResultDao#getTestSuiteResultById(int)
	 */
	@Override
	public TestSuiteResult getTestSuiteResultById(int testSuiteResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestSuiteResultById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestSuiteResult).where(qTestSuiteResult.testSuiteResultID.eq(testSuiteResultId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestSuiteResultProjection(qTestSuiteResult.testSuiteResultID,
					qTestSuiteResult.testSuiteID, qTestSuiteResult.testPlanID, qTestSuiteResult.result, qTestSuiteResult.startDateTime,
					qTestSuiteResult.endDateTime, qTestSuiteResult.percentageFailCount, qTestSuiteResult.percentagePassCount, qTestSuiteResult.testRunID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestSuiteResultDao#insertTestSuiteResultGetKey
	 * (com.exilant.tfw.pojo.output.TestSuiteResult)
	 */
	@Override
	public int insertTestSuiteResultGetKey(final TestSuiteResult testSuiteResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteResultGetKey(TestSuiteResult) - start"); //$NON-NLS-1$
		}

		int testSuiteResultId = 0;
		try {
			testSuiteResultId = template.insertWithKey(qTestSuiteResult, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestSuiteResult.testSuiteResultID, qTestSuiteResult.testSuiteID, qTestSuiteResult.testPlanID, qTestSuiteResult.result, qTestSuiteResult.startDateTime, qTestSuiteResult.endDateTime, qTestSuiteResult.percentagePassCount, qTestSuiteResult.percentageFailCount, qTestSuiteResult.testRunID).values(testSuiteResult.getTestSuiteResultID(), testSuiteResult.getTestSuiteID(), testSuiteResult.getTestPlanID(), testSuiteResult.isResult(), testSuiteResult.getStartDateTime(), testSuiteResult.getEndDateTime(), testSuiteResult.getPercentagePassCount(), testSuiteResult.getPercentageFailCount(), testSuiteResult.getTestRunID()).executeWithKey(qTestSuiteResult.testSuiteResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testSuiteResultId : " + testSuiteResultId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestSuiteResultGetKey(TestSuiteResult) - end"); //$NON-NLS-1$
		}
		return testSuiteResultId;
	}

}