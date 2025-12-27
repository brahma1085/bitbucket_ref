package com.exilant.tfw.dao.impl.output;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.output.TestPlanResultDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QTestplan;
import com.exilant.tfw.generated.pojo.QTestplanresult;
import com.exilant.tfw.pojo.output.TestPlanResult;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestPlanResultDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestPlanResultDao
 * interface
 */
public class TestPlanResultDaoImpl implements TestPlanResultDao {

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

	/** The q test plan result. */
	private QTestplanresult qTestPlanResult = QTestplanresult.testplanresult;

	/** The q test plan. */
	private QTestplan qTestPlan = QTestplan.testplan;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestPlanResultDaoImpl.class);

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
	 * The Class MappingTestPlanResultProjection.
	 */
	public class MappingTestPlanResultProjection extends MappingProjection<TestPlanResult> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test plan result projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestPlanResultProjection(Expression<?>... args) {
			super(TestPlanResult.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestPlanResult map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestPlanResult testPlanResult = new TestPlanResult();
			testPlanResult.setTestPlanResultID(tuple.get(qTestPlanResult.testPlanResultID));
			testPlanResult.setTestPlanID(tuple.get(qTestPlanResult.testPlanID));
			testPlanResult.setTestPlanRunName(tuple.get(qTestPlanResult.testPlanRunName));
			testPlanResult.setStartDateTime(tuple.get(qTestPlanResult.startDateTime));
			testPlanResult.setEndDateTime(tuple.get(qTestPlanResult.endDateTime));
			testPlanResult.setTestRunID(tuple.get(qTestPlanResult.testRunID));
			testPlanResult.setResult(tuple.get(qTestPlanResult.result));
			testPlanResult.setPercentageFailCount(tuple.get(qTestPlanResult.percentageFailCount));
			testPlanResult.setPercentagePassCount(tuple.get(qTestPlanResult.percentagePassCount));
			testPlanResult.setTaskID(tuple.get(qTestPlanResult.taskID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestPlanResult object : " + testPlanResult);
			}
			return testPlanResult;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestPlanResult(final TestPlanResult testPlanResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanResult(TestPlanResult) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestPlanResult: " + testPlanResult);
		try {
			result = template.insert(qTestPlanResult, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestPlanResult.testPlanResultID, qTestPlanResult.testPlanID, qTestPlanResult.testPlanRunName, qTestPlanResult.startDateTime, qTestPlanResult.endDateTime, qTestPlanResult.testRunID, qTestPlanResult.result, qTestPlanResult.percentageFailCount, qTestPlanResult.percentagePassCount).values(testPlanResult.getTestPlanResultID(), testPlanResult.getTestPlanID(), testPlanResult.getTestPlanRunName(), testPlanResult.getStartDateTime(), testPlanResult.getEndDateTime(), testPlanResult.getTestRunID(), testPlanResult.isResult(), testPlanResult.getPercentageFailCount(), testPlanResult.getPercentagePassCount()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in TestPlanResult");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanResult(TestPlanResult) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testPlanResult
	 *            the test plan result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestPlanResult(final TestPlanResult testPlanResult) throws DataAccessException {
		LOG.info("Started updating data in TestPlanResult: " + testPlanResult);
		try {
			long returnlong = template.update(qTestPlanResult, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestPlanResult.testPlanResultID.eq(testPlanResult.getTestPlanResultID())).set(qTestPlanResult.endDateTime, testPlanResult.getEndDateTime()).set(qTestPlanResult.result, testPlanResult.isResult()).set(qTestPlanResult.percentageFailCount, testPlanResult.getPercentageFailCount()).set(qTestPlanResult.percentagePassCount, testPlanResult.getPercentagePassCount()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestPlanResult(TestPlanResult) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.output.TestPlanResultDao#getTestPlanResult()
	 */
	@Override
	public List<TestPlanResult> getTestPlanResult() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanResult() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlanResult).where(qTestPlanResult.testPlanID.eq(qTestPlan.testPlanID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestPlanResultProjection(qTestPlanResult.testPlanResultID, qTestPlanResult.testPlanID,
					qTestPlanResult.testPlanRunName, qTestPlanResult.startDateTime, qTestPlanResult.endDateTime, qTestPlanResult.testRunID,
					qTestPlanResult.result, qTestPlanResult.percentageFailCount, qTestPlanResult.percentagePassCount));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestPlanResultDao#getTestPlanResultById(int)
	 */
	@Override
	public TestPlanResult getTestPlanResultById(int testPlanResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanResultById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlanResult).where(qTestPlanResult.testPlanResultID.eq(testPlanResultId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestPlanResultProjection(qTestPlanResult.testPlanResultID, qTestPlanResult.testPlanID,
					qTestPlanResult.testPlanRunName, qTestPlanResult.startDateTime, qTestPlanResult.endDateTime, qTestPlanResult.testRunID,
					qTestPlanResult.result, qTestPlanResult.percentageFailCount, qTestPlanResult.percentagePassCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestPlanResultDao#insertTestPlanResultGetKey
	 * (com.exilant.tfw.pojo.output.TestPlanResult)
	 */
	@Override
	public int insertTestPlanResultGetKey(final TestPlanResult testPlanResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanResultGetKey(TestPlanResult) - start"); //$NON-NLS-1$
		}

		int testPlanResultId = 0;
		try {
			testPlanResultId = template.insertWithKey(qTestPlanResult, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestPlanResult.testPlanResultID, qTestPlanResult.testPlanID, qTestPlanResult.testPlanRunName, qTestPlanResult.startDateTime, qTestPlanResult.endDateTime, qTestPlanResult.testRunID, qTestPlanResult.result, qTestPlanResult.percentagePassCount, qTestPlanResult.percentageFailCount, qTestPlanResult.taskID).values(testPlanResult.getTestPlanResultID(), testPlanResult.getTestPlanID(), testPlanResult.getTestPlanRunName(), testPlanResult.getStartDateTime(), testPlanResult.getEndDateTime(), testPlanResult.getTestRunID(), testPlanResult.isResult(), testPlanResult.getPercentagePassCount(), testPlanResult.getPercentageFailCount(), testPlanResult.getTaskID()).executeWithKey(qTestPlanResult.testPlanResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testPlanResultId : " + testPlanResultId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanResultGetKey(TestPlanResult) - end"); //$NON-NLS-1$
		}
		return testPlanResultId;
	}

}