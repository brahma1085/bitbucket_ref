package com.exilant.tfw.dao.impl.output;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.output.TestScenarioResultDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QTestscenarioresult;
import com.exilant.tfw.pojo.output.TestScenarioResult;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestScenarioResultDaoImpl.
 */
public class TestScenarioResultDaoImpl implements TestScenarioResultDao {

	/** The template. */
	private QueryDslJdbcTemplate template;

	/** The q test scenario result. */
	private QTestscenarioresult qTestScenarioResult = QTestscenarioresult.testscenarioresult;

	/** The Constant logger. */
	private static final Logger LOG = Logger.getLogger(TestSuiteResultDaoImpl.class);

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
	 * The Class ProjectTestScenarioResult.
	 */
	public class ProjectTestScenarioResult extends MappingProjection<TestScenarioResult> {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new project test scenario result.
		 * 
		 * @param args
		 *            the args
		 */
		public ProjectTestScenarioResult(Expression<?>... args) {
			super(TestScenarioResult.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestScenarioResult map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestScenarioResult testScenarioResult = new TestScenarioResult();
			testScenarioResult.setTestScenarioID(tuple.get(qTestScenarioResult.testScenarioID));
			testScenarioResult.setTestSuiteID(tuple.get(qTestScenarioResult.testSuiteID));
			testScenarioResult.setTestRunID(tuple.get(qTestScenarioResult.testRunID));
			testScenarioResult.setStartDateTime(tuple.get(qTestScenarioResult.startDateTime));
			testScenarioResult.setEndDateTime(tuple.get(qTestScenarioResult.endDateTime));
			testScenarioResult.setResult(tuple.get(qTestScenarioResult.result));
			testScenarioResult.setPercentageFailCount(tuple.get(qTestScenarioResult.percentageFailCount));
			testScenarioResult.setPercentagePassCount(tuple.get(qTestScenarioResult.percentagePassCount));
			testScenarioResult.setPercentageSkipCount(tuple.get(qTestScenarioResult.percentageSkipCount));
			LOG.debug("Returning TestScenario Result Object:" + testScenarioResult);
			return testScenarioResult;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestScenarioResultDao#insertTestScenarioResult
	 * (com.exilant.tfw.pojo.output.TestScenarioResult)
	 */
	@Override
	public long insertTestScenarioResult(final TestScenarioResult testScenarioResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioResult(TestScenarioResult) - start"); //$NON-NLS-1$
		}

		long testScenarioResultId;
		try {
			testScenarioResultId = template.insert(qTestScenarioResult, new SqlInsertCallback() {
				@Override
				public long doInSqlInsertClause(SQLInsertClause insert) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = insert.columns(qTestScenarioResult.testScenarioID, qTestScenarioResult.testSuiteID, qTestScenarioResult.testRunID, qTestScenarioResult.startDateTime, qTestScenarioResult.endDateTime, qTestScenarioResult.result, qTestScenarioResult.percentageFailCount, qTestScenarioResult.percentagePassCount, qTestScenarioResult.percentageSkipCount).values(testScenarioResult.getTestScenarioID(), testScenarioResult.getTestSuiteID(), testScenarioResult.getTestRunID(), testScenarioResult.getStartDateTime(), testScenarioResult.getEndDateTime(), testScenarioResult.isResult(), testScenarioResult.getPercentageFailCount(), testScenarioResult.getPercentagePassCount(), testScenarioResult.getPercentageSkipCount()).executeWithKey(qTestScenarioResult.testScenarioResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info("One record got inserted in TestScenarioResult table, with TestScenarioResultId :" + testScenarioResultId);
		} catch (Exception e) {
			LOG.error("Error occured while insertion of testScenarioResult : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioResult(TestScenarioResult) - end"); //$NON-NLS-1$
		}
		return testScenarioResultId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.output.TestScenarioResultDao#updateTestScenarioResult
	 * (com.exilant.tfw.pojo.output.TestScenarioResult)
	 */
	@Override
	public long updateTestScenarioResult(final TestScenarioResult testScenarioResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarioResult(TestScenarioResult) - start"); //$NON-NLS-1$
		}

		long noOfRowsAffected;
		try {
			noOfRowsAffected = template.update(qTestScenarioResult, new SqlUpdateCallback() {

				@Override
				public long doInSqlUpdateClause(SQLUpdateClause update) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong = update.set(qTestScenarioResult.testScenarioID, testScenarioResult.getTestScenarioID()).set(qTestScenarioResult.testSuiteID, testScenarioResult.getTestSuiteID()).set(qTestScenarioResult.testRunID, testScenarioResult.getTestRunID()).set(qTestScenarioResult.startDateTime, testScenarioResult.getStartDateTime()).set(qTestScenarioResult.endDateTime, testScenarioResult.getEndDateTime()).set(qTestScenarioResult.result, testScenarioResult.isResult()).set(qTestScenarioResult.percentageFailCount, testScenarioResult.getPercentageFailCount()).set(qTestScenarioResult.percentagePassCount, testScenarioResult.getPercentagePassCount()).set(qTestScenarioResult.percentageSkipCount, testScenarioResult.getPercentageSkipCount()).where(qTestScenarioResult.testScenarioResultID.eq(testScenarioResult.getTestScenarioResultID())).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(noOfRowsAffected + " row(s) got updated in TestScenarioResult table, with TestScenarioResultId :"
					+ testScenarioResult.getTestScenarioResultID());
		} catch (Exception e) {
			LOG.error("Error occured while updation of testScenarioResult : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestScenarioResult(TestScenarioResult) - end"); //$NON-NLS-1$
		}
		return noOfRowsAffected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.output.TestScenarioResultDao#
	 * getTestScenarioResultBySuiteResultId(int)
	 */
	@Override
	public List<TestScenarioResult> getTestScenarioResultBySuiteResultId(int testSuiteResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioResultBySuiteResultId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery query = template.newSqlQuery().from(qTestScenarioResult).where(qTestScenarioResult.testSuiteID.eq(testSuiteResultId));
			LOG.info("Generated TestScenarioResult Select query for given TestSuiteResultId " + testSuiteResultId + " is:" + query);
			return template.query(query, new ProjectTestScenarioResult(qTestScenarioResult.testScenarioResultID, qTestScenarioResult.testScenarioID,
					qTestScenarioResult.testSuiteID, qTestScenarioResult.testRunID, qTestScenarioResult.startDateTime, qTestScenarioResult.endDateTime,
					qTestScenarioResult.result, qTestScenarioResult.percentageFailCount, qTestScenarioResult.percentagePassCount,
					qTestScenarioResult.percentageSkipCount));
		} catch (Exception e) {
			LOG.error("Error occured while selection of testScenarioResult for testSuiteResultId " + testSuiteResultId + " :" + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.output.TestScenarioResultDao#
	 * getTestScenarioResultBySchedulerResultId(int)
	 */
	@Override
	public List<TestScenarioResult> getTestScenarioResultBySchedulerResultId(int schedulerResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestScenarioResultBySchedulerResultId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery query = template.newSqlQuery().from(qTestScenarioResult).where(qTestScenarioResult.testScenarioResultID.eq(schedulerResultId));
			LOG.info("Generated TestScenarioResult select query for given SchedulerResultId :" + schedulerResultId + " is:" + query);
			return template.query(query, new ProjectTestScenarioResult(qTestScenarioResult.testScenarioResultID, qTestScenarioResult.testScenarioID,
					qTestScenarioResult.testSuiteID, qTestScenarioResult.testRunID, qTestScenarioResult.startDateTime, qTestScenarioResult.endDateTime,
					qTestScenarioResult.result, qTestScenarioResult.percentageFailCount, qTestScenarioResult.percentagePassCount,
					qTestScenarioResult.percentageSkipCount));

		} catch (Exception e) {
			LOG.error("Error occured while selection of testScenarioResult for schedulerResultID " + schedulerResultId + " :" + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.output.TestScenarioResultDao#
	 * insertTestScenarioResultGetKey
	 * (com.exilant.tfw.pojo.output.TestScenarioResult)
	 */
	@Override
	public int insertTestScenarioResultGetKey(final TestScenarioResult testScenarioResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioResultGetKey(TestScenarioResult) - start"); //$NON-NLS-1$
		}

		int testScenarioResultId = 0;
		try {
			testScenarioResultId = template.insertWithKey(qTestScenarioResult, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestScenarioResult.testScenarioID, qTestScenarioResult.testSuiteID, qTestScenarioResult.testRunID, qTestScenarioResult.result, qTestScenarioResult.startDateTime, qTestScenarioResult.endDateTime, qTestScenarioResult.testScenarioResultID, qTestScenarioResult.percentagePassCount, qTestScenarioResult.percentageFailCount, qTestScenarioResult.percentageSkipCount).values(testScenarioResult.getTestScenarioID(), testScenarioResult.getTestSuiteID(), testScenarioResult.getTestRunID(), testScenarioResult.isResult(), testScenarioResult.getStartDateTime(), testScenarioResult.getEndDateTime(), testScenarioResult.getTestScenarioResultID(), testScenarioResult.getPercentagePassCount(), testScenarioResult.getPercentageFailCount(), testScenarioResult.getPercentageSkipCount()).executeWithKey(qTestScenarioResult.testScenarioResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testScenarioResultId : " + testScenarioResultId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestScenarioResultGetKey(TestScenarioResult) - end"); //$NON-NLS-1$
		}
		return testScenarioResultId;
	}

}
