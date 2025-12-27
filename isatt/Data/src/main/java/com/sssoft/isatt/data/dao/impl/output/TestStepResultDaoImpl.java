package com.sssoft.isatt.data.dao.impl.output;

import java.sql.SQLException;
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
import com.sssoft.isatt.data.dao.output.TestStepResultDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QTeststep;
import com.sssoft.isatt.data.generated.pojo.QTeststepresult;
import com.sssoft.isatt.data.pojo.output.TestStepResult;

/**
 * The Class TestStepResultDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestStepResultDao
 * interface
 */
public class TestStepResultDaoImpl implements TestStepResultDao {

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

	/** The q test step result. */
	private QTeststepresult qTestStepResult = QTeststepresult.teststepresult;

	/** The q test step. */
	private QTeststep qTestStep = QTeststep.teststep;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestStepResultDaoImpl.class);

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
	 * The Class MappingTestStepResultProjection.
	 */
	public class MappingTestStepResultProjection extends MappingProjection<TestStepResult> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test step result projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestStepResultProjection(Expression<?>... args) {
			super(TestStepResult.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestStepResult map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestStepResult testStepResult = new TestStepResult();
			testStepResult.setTestStepResultID(tuple.get(qTestStepResult.testStepResultID));
			testStepResult.setTestStepID(tuple.get(qTestStepResult.testStepID));
			testStepResult.setTestCaseID(tuple.get(qTestStepResult.testCaseID));
			testStepResult.setResult(tuple.get(qTestStepResult.result));
			testStepResult.setException(tuple.get(qTestStepResult.exception));
			testStepResult.setRequest(tuple.get(qTestStepResult.request));
			testStepResult.setResponse(tuple.get(qTestStepResult.response));
			testStepResult.setStartDateTime(tuple.get(qTestStepResult.startDateTime));
			testStepResult.setEndDateTime(tuple.get(qTestStepResult.endDateTime));
			testStepResult.setTestRunID(tuple.get(qTestStepResult.testRunID));
			testStepResult.setDuration(tuple.get(qTestStepResult.duration));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestStepResult object : " + testStepResult);
			}
			return testStepResult;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestStepResult(final TestStepResult testStepResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepResult(TestStepResult) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestStepResult: " + testStepResult);
		try {
			result = template.insert(qTestStepResult, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestStepResult.testStepResultID, qTestStepResult.testStepID, qTestStepResult.testCaseID, qTestStepResult.result, qTestStepResult.comment, qTestStepResult.exception, qTestStepResult.request, qTestStepResult.response, qTestStepResult.startDateTime, qTestStepResult.endDateTime, qTestStepResult.testRunID, qTestStepResult.duration).values(testStepResult.getTestStepResultID(), testStepResult.getTestStepID(), testStepResult.getTestCaseID(), testStepResult.getResult(), testStepResult.getComment(), testStepResult.getException(), testStepResult.getRequest(), testStepResult.getResponse(), testStepResult.getStartDateTime(), testStepResult.getEndDateTime(), testStepResult.getTestRunID(), testStepResult.getDuration()).execute();
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
			LOG.debug("insertTestStepResult(TestStepResult) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testStepResult
	 *            the test step result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestStepResult(final TestStepResult testStepResult) throws DataAccessException {
		LOG.info("Started updating data in TestStepResult: " + testStepResult);
		try {
			long returnlong = template.update(qTestStepResult, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestStepResult.testStepResultID.eq(testStepResult.getTestStepResultID())).set(qTestStepResult.result, testStepResult.getResult()).set(qTestStepResult.comment, testStepResult.getComment()).set(qTestStepResult.exception, testStepResult.getException()).set(qTestStepResult.endDateTime, testStepResult.getEndDateTime()).set(qTestStepResult.request, testStepResult.getRequest()).set(qTestStepResult.response, testStepResult.getResponse()).set(qTestStepResult.duration, testStepResult.getDuration()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestStepResult(TestStepResult) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.output.TestStepResultDao#getTestStepResult()
	 */
	@Override
	public List<TestStepResult> getTestStepResult() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepResult() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStepResult).where(qTestStepResult.testStepID.eq(qTestStep.testStepID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestStepResultProjection(qTestStepResult.testStepResultID, qTestStepResult.testStepID,
					qTestStepResult.testCaseID, qTestStepResult.result, qTestStepResult.comment, qTestStepResult.exception, qTestStepResult.request,
					qTestStepResult.response, qTestStepResult.startDateTime, qTestStepResult.endDateTime, qTestStepResult.duration));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.output.TestStepResultDao#getTestStepResultById(int)
	 */
	@Override
	public TestStepResult getTestStepResultById(int testStepResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepResultById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStepResult).where(qTestStepResult.testStepResultID.eq(testStepResultId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestStepResultProjection(qTestStepResult.testStepResultID, qTestStepResult.testStepID,
					qTestStepResult.testCaseID, qTestStepResult.result, qTestStepResult.comment, qTestStepResult.exception, qTestStepResult.request,
					qTestStepResult.response, qTestStepResult.startDateTime, qTestStepResult.endDateTime, qTestStepResult.duration));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.output.TestStepResultDao#insertTestStepResultGetKey
	 * (com.sssoft.isatt.data.pojo.output.TestStepResult)
	 */
	@Override
	public int insertTestStepResultGetKey(final TestStepResult testStepResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepResultGetKey(TestStepResult) - start"); //$NON-NLS-1$
		}

		int testStepResultId = 0;
		try {
			testStepResultId = template.insertWithKey(qTestStepResult, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestStepResult.testStepResultID, qTestStepResult.testStepID, qTestStepResult.testCaseID, qTestStepResult.result, qTestStepResult.startDateTime, qTestStepResult.endDateTime, qTestStepResult.comment, qTestStepResult.exception, qTestStepResult.request, qTestStepResult.response, qTestStepResult.testRunID, qTestStepResult.duration).values(testStepResult.getTestStepResultID(), testStepResult.getTestStepID(), testStepResult.getTestCaseID(), testStepResult.getResult(), testStepResult.getStartDateTime(), testStepResult.getEndDateTime(), testStepResult.getComment(), testStepResult.getException(), testStepResult.getRequest(), testStepResult.getResponse(), testStepResult.getTestRunID(), testStepResult.getDuration()).executeWithKey(qTestStepResult.testStepResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testStepResultId : " + testStepResultId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepResultGetKey(TestStepResult) - end"); //$NON-NLS-1$
		}
		return testStepResultId;
	}

}