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
import com.sssoft.isatt.data.dao.output.LaneResultsDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QAgentdetails;
import com.sssoft.isatt.data.generated.pojo.QLaneresults;
import com.sssoft.isatt.data.generated.pojo.QSchedulerlanedetails;
import com.sssoft.isatt.data.pojo.output.LaneResults;

/**
 * The Class LaneResultsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for LaneResultsDao
 * interface
 */
public class LaneResultsDaoImpl implements LaneResultsDao {

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

	/** The q lane results. */
	private QLaneresults qLaneResults = QLaneresults.laneresults;

	/** The q scheduler lane details. */
	private QSchedulerlanedetails qSchedulerLaneDetails = QSchedulerlanedetails.schedulerlanedetails;

	/** The q agent details. */
	private QAgentdetails qAgentDetails = QAgentdetails.agentdetails;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(LaneResultsDaoImpl.class);

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
	 * The Class MappingLaneResultsProjection.
	 */
	public class MappingLaneResultsProjection extends MappingProjection<LaneResults> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping lane results projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingLaneResultsProjection(Expression<?>... args) {
			super(LaneResults.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected LaneResults map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			LaneResults laneResults = new LaneResults();
			laneResults.setAgentID(tuple.get(qLaneResults.testRunID));
			laneResults.setScheduleLaneID(tuple.get(qLaneResults.scheduleLaneID));
			laneResults.setAgentID(tuple.get(qLaneResults.agentID));
			laneResults.setBuildVersion(tuple.get(qLaneResults.buildVersion));
			laneResults.setOS(tuple.get(qLaneResults.os));
			laneResults.setResult(tuple.get(qLaneResults.result));
			laneResults.setFailureDetails(tuple.get(qLaneResults.failureDetails));
			laneResults.setPercentagePassCount(tuple.get(qLaneResults.percentagePassCount));
			laneResults.setPercentageFailCount(tuple.get(qLaneResults.percentageFailCount));
			laneResults.setStartDateTime(tuple.get(qLaneResults.startDateTime));
			laneResults.setEndDateTime(tuple.get(qLaneResults.endDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning LaneResults object : " + laneResults);
			}
			return laneResults;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param laneResults
	 *            the lane results
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertLaneResults(final LaneResults laneResults) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertLaneResults(LaneResults) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in LaneResults: " + laneResults);
		try {
			result = template.insert(qLaneResults, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qLaneResults.testRunID, qLaneResults.scheduleLaneID, qLaneResults.agentID, qLaneResults.buildVersion, qLaneResults.os, qLaneResults.result, qLaneResults.failureDetails, qLaneResults.percentagePassCount, qLaneResults.percentageFailCount, qLaneResults.startDateTime, qLaneResults.endDateTime).values(laneResults.getTestRunID(), laneResults.getScheduleLaneID(), laneResults.getAgentID(), laneResults.getBuildVersion(), laneResults.getOS(), laneResults.isResult(), laneResults.getFailureDetails(), laneResults.getPercentagePassCount(), laneResults.getPercentageFailCount(), laneResults.getStartDateTime(), laneResults.getEndDateTime()).execute();
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
			LOG.debug("insertLaneResults(LaneResults) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param laneResults
	 *            the lane results
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateLaneResults(final LaneResults laneResults) throws DataAccessException {
		LOG.info("Started updating data in LaneResults: " + laneResults);
		try {
			long returnlong = template.update(qLaneResults, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qLaneResults.scheduleLaneID.eq(laneResults.getScheduleLaneID())).set(qLaneResults.result, laneResults.isResult()).set(qLaneResults.endDateTime, laneResults.getEndDateTime()).set(qLaneResults.percentagePassCount, laneResults.getPercentagePassCount()).set(qLaneResults.percentageFailCount, laneResults.getPercentageFailCount()).set(qLaneResults.failureDetails, laneResults.getFailureDetails()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateLaneResults(LaneResults) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.output.LaneResultsDao#getLaneResults()
	 */
	@Override
	public List<LaneResults> getLaneResults() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getLaneResults() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qLaneResults)
					.where(qLaneResults.scheduleLaneID.eq(qSchedulerLaneDetails.scheduleLaneID).and(qLaneResults.agentID.eq(qAgentDetails.agentID)));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingLaneResultsProjection(qLaneResults.testRunID, qLaneResults.scheduleLaneID, qLaneResults.agentID,
					qLaneResults.buildVersion, qLaneResults.os, qLaneResults.result, qLaneResults.failureDetails, qLaneResults.percentagePassCount,
					qLaneResults.percentageFailCount, qLaneResults.startDateTime, qLaneResults.endDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.output.LaneResultsDao#getLaneResultsById(int)
	 */
	@Override
	public LaneResults getLaneResultsById(int laneResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getLaneResultsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qLaneResults).where(qLaneResults.testRunID.eq(laneResultId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingLaneResultsProjection(qLaneResults.testRunID, qLaneResults.scheduleLaneID,
					qLaneResults.agentID, qLaneResults.buildVersion, qLaneResults.os, qLaneResults.result, qLaneResults.failureDetails,
					qLaneResults.percentagePassCount, qLaneResults.percentageFailCount, qLaneResults.startDateTime, qLaneResults.endDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.output.LaneResultsDao#insertLaneResultsGetKey(com
	 * .exilant.tfw.pojo.output.LaneResults)
	 */
	@Override
	public int insertLaneResultsGetKey(final LaneResults laneResults) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertLaneResultsGetKey(LaneResults) - start"); //$NON-NLS-1$
		}

		int laneResultsId = 0;
		try {
			laneResultsId = template.insertWithKey(qLaneResults, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qLaneResults.laneResultsID, qLaneResults.scheduleLaneID, qLaneResults.agentID, qLaneResults.buildVersion, qLaneResults.os, qLaneResults.failureDetails, qLaneResults.testRunID, qLaneResults.result, qLaneResults.startDateTime, qLaneResults.endDateTime, qLaneResults.percentagePassCount, qLaneResults.percentageFailCount).values(laneResults.getLaneResultsID(), laneResults.getScheduleLaneID(), laneResults.getAgentID(), laneResults.getBuildVersion(), laneResults.getFailureDetails(), laneResults.getTestRunID(), laneResults.isResult(), laneResults.getStartDateTime(), laneResults.getEndDateTime(), laneResults.getPercentagePassCount(), laneResults.getPercentageFailCount()).executeWithKey(qLaneResults.laneResultsID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated laneResultsId : " + laneResultsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertLaneResultsGetKey(LaneResults) - end"); //$NON-NLS-1$
		}
		return laneResultsId;
	}

}