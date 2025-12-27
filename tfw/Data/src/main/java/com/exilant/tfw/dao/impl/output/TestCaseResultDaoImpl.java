package com.exilant.tfw.dao.impl.output;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.output.TestCaseResultDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QTestcase;
import com.exilant.tfw.generated.pojo.QTestcaseresult;
import com.exilant.tfw.pojo.output.TestCaseResult;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestCaseResultDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestCaseResultDao
 * interface
 */
public class TestCaseResultDaoImpl implements TestCaseResultDao {

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

	/** The q test case result. */
	private QTestcaseresult qTestCaseResult = QTestcaseresult.testcaseresult;

	/** The q test case. */
	private QTestcase qTestCase = QTestcase.testcase;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestCaseResultDaoImpl.class);

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
	 * The Class MappingTestCaseResultProjection.
	 */
	public class MappingTestCaseResultProjection extends MappingProjection<TestCaseResult> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test case result projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestCaseResultProjection(Expression<?>... args) {
			super(TestCaseResult.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestCaseResult map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestCaseResult testCaseResult = new TestCaseResult();
			testCaseResult.setTestCaseResultID(tuple.get(qTestCaseResult.testCaseResultID));
			testCaseResult.setTestCaseID(tuple.get(qTestCaseResult.testCaseID));
			testCaseResult.setTestScenarioID(tuple.get(qTestCaseResult.testScenarioID));
			testCaseResult.setResult((tuple.get(qTestCaseResult.result)));
			testCaseResult.setStartDateTime(tuple.get(qTestCaseResult.startDateTime));
			testCaseResult.setEndDateTime(tuple.get(qTestCaseResult.endDateTime));
			testCaseResult.setComment(tuple.get(qTestCaseResult.comment));
			testCaseResult.setException(tuple.get(qTestCaseResult.exception));
			testCaseResult.setRequest(tuple.get(qTestCaseResult.request));
			testCaseResult.setResponse(tuple.get(qTestCaseResult.response));
			testCaseResult.setPercentageFailCount(tuple.get(qTestCaseResult.percentageFailCount));
			testCaseResult.setPercentagePassCount(tuple.get(qTestCaseResult.percentagePassCount));
			testCaseResult.setPercentageSkipCount(tuple.get(qTestCaseResult.percentageSkipCount));
			testCaseResult.setTestRunID(tuple.get(qTestCaseResult.testRunID));

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestCaseResult object : " + testCaseResult);
			}
			return testCaseResult;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestCaseResult(final TestCaseResult testCaseResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseResult(TestCaseResult) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestCaseResult: " + testCaseResult);
		try {
			result = template.insert(qTestCaseResult, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestCaseResult.testCaseResultID, qTestCaseResult.testCaseID, qTestCaseResult.testScenarioID, qTestCaseResult.result, qTestCaseResult.startDateTime, qTestCaseResult.endDateTime, qTestCaseResult.comment, qTestCaseResult.exception, qTestCaseResult.request, qTestCaseResult.response, qTestCaseResult.testRunID, qTestCaseResult.percentageFailCount, qTestCaseResult.percentagePassCount, qTestCaseResult.percentageSkipCount).values(testCaseResult.getTestCaseResultID(), testCaseResult.getTestCaseID(), testCaseResult.getTestScenarioID(), testCaseResult.getResult(), testCaseResult.getStartDateTime(), testCaseResult.getEndDateTime(), testCaseResult.getComment(), testCaseResult.getException(), testCaseResult.getRequest(), testCaseResult.getResponse(), testCaseResult.getTestRunID(), testCaseResult.getPercentageFailCount(), testCaseResult.getPercentagePassCount(), testCaseResult.getPercentageSkipCount()).execute();
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
			LOG.debug("insertTestCaseResult(TestCaseResult) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testCaseResult
	 *            the test case result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestCaseResult(final TestCaseResult testCaseResult) throws DataAccessException {
		LOG.info("Started updating data in TestCaseResult: " + testCaseResult);
		try {
			long returnlong = template.update(qTestCaseResult, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestCaseResult.testCaseResultID.eq(testCaseResult.getTestCaseResultID())).set(qTestCaseResult.result, testCaseResult.getResult()).set(qTestCaseResult.endDateTime, testCaseResult.getEndDateTime()).set(qTestCaseResult.comment, testCaseResult.getComment()).set(qTestCaseResult.exception, testCaseResult.getException()).set(qTestCaseResult.request, testCaseResult.getRequest()).set(qTestCaseResult.response, testCaseResult.getResponse()).set(qTestCaseResult.percentageFailCount, testCaseResult.getPercentageFailCount()).set(qTestCaseResult.percentagePassCount, testCaseResult.getPercentagePassCount()).set(qTestCaseResult.percentageSkipCount, testCaseResult.getPercentageSkipCount()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestCaseResult(TestCaseResult) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.output.TestCaseResultDao#getTestCaseResult()
	 */
	@Override
	public List<TestCaseResult> getTestCaseResult() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseResult() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCaseResult).where(qTestCaseResult.testCaseID.eq(qTestCase.testCaseID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestCaseResultProjection(qTestCaseResult.testCaseResultID, qTestCaseResult.testCaseID,
					qTestCaseResult.testScenarioID, qTestCaseResult.result, qTestCaseResult.startDateTime, qTestCaseResult.endDateTime,
					qTestCaseResult.comment, qTestCaseResult.exception, qTestCaseResult.request, qTestCaseResult.response, qTestCaseResult.testRunID,
					qTestCaseResult.percentageFailCount, qTestCaseResult.percentagePassCount, qTestCaseResult.percentageSkipCount));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestCaseResultDao#getTestCaseResultById(int)
	 */
	@Override
	public TestCaseResult getTestCaseResultById(int testCaseResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestCaseResultById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestCaseResult).where(qTestCaseResult.testCaseResultID.eq(testCaseResultId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestCaseResultProjection(qTestCaseResult.testCaseResultID, qTestCaseResult.testCaseID,
					qTestCaseResult.testScenarioID, qTestCaseResult.result, qTestCaseResult.startDateTime, qTestCaseResult.endDateTime,
					qTestCaseResult.comment, qTestCaseResult.exception, qTestCaseResult.request, qTestCaseResult.response, qTestCaseResult.testRunID,
					qTestCaseResult.percentageFailCount, qTestCaseResult.percentagePassCount, qTestCaseResult.percentageSkipCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestCaseResultDao#insertTestCaseResultGetKey
	 * (com.exilant.tfw.pojo.output.TestCaseResult)
	 */
	@Override
	public int insertTestCaseResultGetKey(final TestCaseResult testCaseResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseResultGetKey(TestCaseResult) - start"); //$NON-NLS-1$
		}

		int testCaseResultId = 0;
		try {
			testCaseResultId = template.insertWithKey(qTestCaseResult, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestCaseResult.testCaseResultID, qTestCaseResult.testCaseID, qTestCaseResult.result, qTestCaseResult.startDateTime, qTestCaseResult.endDateTime, qTestCaseResult.comment, qTestCaseResult.exception, qTestCaseResult.request, qTestCaseResult.response, qTestCaseResult.testRunID, qTestCaseResult.testScenarioID, qTestCaseResult.percentagePassCount, qTestCaseResult.percentageFailCount, qTestCaseResult.percentageSkipCount).values(testCaseResult.getTestCaseResultID(), testCaseResult.getTestCaseID(), testCaseResult.getResult(), testCaseResult.getStartDateTime(), testCaseResult.getEndDateTime(), testCaseResult.getComment(), testCaseResult.getException(), testCaseResult.getRequest(), testCaseResult.getResponse(), testCaseResult.getTestRunID(), testCaseResult.getTestScenarioID(), testCaseResult.getPercentagePassCount(), testCaseResult.getPercentageFailCount(), testCaseResult.getPercentageSkipCount()).executeWithKey(qTestCaseResult.testCaseResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testCaseResultId : " + testCaseResultId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestCaseResultGetKey(TestCaseResult) - end"); //$NON-NLS-1$
		}
		return testCaseResultId;
	}

}