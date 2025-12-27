package com.sssoft.isatt.data.dao.impl;

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
import com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QScheduler;
import com.sssoft.isatt.data.generated.pojo.QSchedulerlanedetails;
import com.sssoft.isatt.data.pojo.SchedulerLaneDetails;

/**
 * The Class SchedulerLaneDetailsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for SchedulerLaneDetailsDao
 * interface
 */
public class SchedulerLaneDetailsDaoImpl implements SchedulerLaneDetailsDao {

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

	/** The q scheduler lane details. */
	private QSchedulerlanedetails qSchedulerLaneDetails = QSchedulerlanedetails.schedulerlanedetails;

	/** The q scheduler. */
	private QScheduler qScheduler = QScheduler.scheduler;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SchedulerLaneDetailsDaoImpl.class);

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
	 * The Class MappingSchedulerLaneDetailsProjection.
	 */
	public class MappingSchedulerLaneDetailsProjection extends MappingProjection<SchedulerLaneDetails> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping scheduler lane details projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingSchedulerLaneDetailsProjection(Expression<?>... args) {
			super(SchedulerLaneDetails.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected SchedulerLaneDetails map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			SchedulerLaneDetails schedulerLaneDetails = new SchedulerLaneDetails();
			schedulerLaneDetails.setScheduleID(tuple.get(qSchedulerLaneDetails.scheduleID));
			schedulerLaneDetails.setScheduleLaneID(tuple.get(qSchedulerLaneDetails.scheduleLaneID));
			schedulerLaneDetails.setLaneType(tuple.get(qSchedulerLaneDetails.laneType));
			schedulerLaneDetails.setLaneUserName(tuple.get(qSchedulerLaneDetails.laneUserName));
			schedulerLaneDetails.setRunnerType(tuple.get(qSchedulerLaneDetails.runnerType));
			schedulerLaneDetails.setClones(tuple.get(qSchedulerLaneDetails.clones));
			schedulerLaneDetails.setIterations(tuple.get(qSchedulerLaneDetails.iterations));
			schedulerLaneDetails.setRampUpDelay(tuple.get(qSchedulerLaneDetails.rampUpDelay));
			schedulerLaneDetails.setDuration(tuple.get(qSchedulerLaneDetails.duration));
			schedulerLaneDetails.setAgentID(tuple.get(qSchedulerLaneDetails.agentID));
			schedulerLaneDetails.setCreatedBy(tuple.get(qSchedulerLaneDetails.createdBy));
			schedulerLaneDetails.setCreatedDateTime(tuple.get(qSchedulerLaneDetails.createdDateTime));
			schedulerLaneDetails.setUpdatedBy(tuple.get(qSchedulerLaneDetails.updatedBy));
			schedulerLaneDetails.setUpdatedDateTime(tuple.get(qSchedulerLaneDetails.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SchedulerLaneDetails object : " + schedulerLaneDetails);
			}
			return schedulerLaneDetails;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param schedulerLaneDetails
	 *            the scheduler lane details
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertSchedulerLaneDetails(final SchedulerLaneDetails schedulerLaneDetails) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerLaneDetails(SchedulerLaneDetails) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in SchedulerLaneDetails: " + schedulerLaneDetails);
		try {
			result = template.insert(qSchedulerLaneDetails, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qSchedulerLaneDetails.scheduleID, qSchedulerLaneDetails.scheduleLaneID, qSchedulerLaneDetails.laneType, qSchedulerLaneDetails.laneUserName, qSchedulerLaneDetails.runnerType, qSchedulerLaneDetails.clones, qSchedulerLaneDetails.iterations, qSchedulerLaneDetails.rampUpDelay, qSchedulerLaneDetails.duration, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.createdBy, qSchedulerLaneDetails.createdDateTime, qSchedulerLaneDetails.updatedBy, qSchedulerLaneDetails.updatedDateTime).values(schedulerLaneDetails.getScheduleID(), schedulerLaneDetails.getScheduleLaneID(), schedulerLaneDetails.getLaneType(), schedulerLaneDetails.getLaneUserName(), schedulerLaneDetails.getRunnerType(), schedulerLaneDetails.getClones(), schedulerLaneDetails.getIterations(), schedulerLaneDetails.getRampUpDelay(), schedulerLaneDetails.getDuration(), schedulerLaneDetails.getAgentID(), schedulerLaneDetails.getCreatedBy(), schedulerLaneDetails.getCreatedDateTime(), schedulerLaneDetails.getUpdatedBy(), schedulerLaneDetails.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Actions");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerLaneDetails(SchedulerLaneDetails) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param schedulerLaneDetails
	 *            the scheduler lane details
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateSchedulerLaneDetails(final SchedulerLaneDetails schedulerLaneDetails) throws DataAccessException {
		LOG.info("Started updating data in SchedulerLaneDetails: " + schedulerLaneDetails);
		try {
			long returnlong = template.update(qSchedulerLaneDetails, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qSchedulerLaneDetails.scheduleLaneID.eq(schedulerLaneDetails.getScheduleLaneID())).set(qSchedulerLaneDetails.laneType, schedulerLaneDetails.getLaneType()).set(qSchedulerLaneDetails.laneUserName, schedulerLaneDetails.getLaneUserName()).set(qSchedulerLaneDetails.runnerType, schedulerLaneDetails.getRunnerType()).set(qSchedulerLaneDetails.clones, schedulerLaneDetails.getClones()).set(qSchedulerLaneDetails.iterations, schedulerLaneDetails.getIterations()).set(qSchedulerLaneDetails.rampUpDelay, schedulerLaneDetails.getRampUpDelay()).set(qSchedulerLaneDetails.duration, schedulerLaneDetails.getDuration()).set(qSchedulerLaneDetails.agentID, schedulerLaneDetails.getAgentID()).set(qSchedulerLaneDetails.updatedBy, schedulerLaneDetails.getUpdatedBy()).set(qSchedulerLaneDetails.updatedDateTime, schedulerLaneDetails.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerLaneDetails(SchedulerLaneDetails) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao#getSchedulerLaneDetails()
	 */
	@Override
	public List<SchedulerLaneDetails> getSchedulerLaneDetails() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetails() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerLaneDetails).where(qSchedulerLaneDetails.scheduleID.eq(qScheduler.scheduleID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerLaneDetailsProjection(qSchedulerLaneDetails.scheduleID,
					qSchedulerLaneDetails.scheduleLaneID, qSchedulerLaneDetails.laneType, qSchedulerLaneDetails.laneUserName,
					qSchedulerLaneDetails.runnerType, qSchedulerLaneDetails.clones, qSchedulerLaneDetails.iterations, qSchedulerLaneDetails.rampUpDelay,
					qSchedulerLaneDetails.duration, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.createdBy, qSchedulerLaneDetails.createdDateTime,
					qSchedulerLaneDetails.updatedBy, qSchedulerLaneDetails.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao#getSchedulerLaneDetailsById
	 * (int)
	 */
	@Override
	public SchedulerLaneDetails getSchedulerLaneDetailsById(int schedulerLaneId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerLaneDetails).where(qSchedulerLaneDetails.scheduleLaneID.eq(schedulerLaneId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingSchedulerLaneDetailsProjection(qSchedulerLaneDetails.scheduleID,
					qSchedulerLaneDetails.scheduleLaneID, qSchedulerLaneDetails.laneType, qSchedulerLaneDetails.laneUserName,
					qSchedulerLaneDetails.runnerType, qSchedulerLaneDetails.clones, qSchedulerLaneDetails.iterations, qSchedulerLaneDetails.rampUpDelay,
					qSchedulerLaneDetails.duration, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.createdBy, qSchedulerLaneDetails.createdDateTime,
					qSchedulerLaneDetails.updatedBy, qSchedulerLaneDetails.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao#insertSchedulerLaneDetailsGetKey
	 * (com.sssoft.isatt.data.pojo.SchedulerLaneDetails)
	 */
	@Override
	public int insertSchedulerLaneDetailsGetKey(final SchedulerLaneDetails schedulerLaneDetails) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerLaneDetailsGetKey(SchedulerLaneDetails) - start"); //$NON-NLS-1$
		}

		int schedulerLaneDetailsId = 0;
		try {
			schedulerLaneDetailsId = template.insertWithKey(qSchedulerLaneDetails, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qSchedulerLaneDetails.scheduleLaneID, qSchedulerLaneDetails.scheduleID, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.laneType, qSchedulerLaneDetails.laneUserName, qSchedulerLaneDetails.runnerType, qSchedulerLaneDetails.clones, qSchedulerLaneDetails.iterations, qSchedulerLaneDetails.rampUpDelay, qSchedulerLaneDetails.duration, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.createdBy, qSchedulerLaneDetails.createdDateTime, qSchedulerLaneDetails.updatedBy, qSchedulerLaneDetails.updatedDateTime).values(schedulerLaneDetails.getScheduleLaneID(), schedulerLaneDetails.getScheduleID(), schedulerLaneDetails.getAgentID(), schedulerLaneDetails.getLaneType(), schedulerLaneDetails.getLaneUserName(), schedulerLaneDetails.getRunnerType(), schedulerLaneDetails.getClones(), schedulerLaneDetails.getIterations(), schedulerLaneDetails.getRampUpDelay(), schedulerLaneDetails.getDuration(), schedulerLaneDetails.getAgentID(), schedulerLaneDetails.getCreatedBy(), schedulerLaneDetails.getCreatedDateTime(), schedulerLaneDetails.getUpdatedBy(), schedulerLaneDetails.getUpdatedDateTime()).executeWithKey(qSchedulerLaneDetails.scheduleLaneID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated schedulerLaneDetailsId : " + schedulerLaneDetailsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerLaneDetailsGetKey(SchedulerLaneDetails) - end"); //$NON-NLS-1$
		}
		return schedulerLaneDetailsId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao#
	 * getSchedulerLaneDetailsByScheduleId(int)
	 */
	@Override
	public List<SchedulerLaneDetails> getSchedulerLaneDetailsByScheduleId(int scheduleId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsByScheduleId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerLaneDetails).where(qSchedulerLaneDetails.scheduleID.eq(scheduleId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerLaneDetailsProjection(qSchedulerLaneDetails.scheduleID,
					qSchedulerLaneDetails.scheduleLaneID, qSchedulerLaneDetails.laneType, qSchedulerLaneDetails.laneUserName,
					qSchedulerLaneDetails.runnerType, qSchedulerLaneDetails.clones, qSchedulerLaneDetails.iterations, qSchedulerLaneDetails.rampUpDelay,
					qSchedulerLaneDetails.duration, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.createdBy, qSchedulerLaneDetails.createdDateTime,
					qSchedulerLaneDetails.updatedBy, qSchedulerLaneDetails.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerLaneDetailsDao#getSchedulerLaneDetailsByAppId
	 * (int)
	 */
	@Override
	public List<SchedulerLaneDetails> getSchedulerLaneDetailsByAppId(int AppId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerLaneDetailsByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerLaneDetails);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerLaneDetailsProjection(qSchedulerLaneDetails.scheduleID,
					qSchedulerLaneDetails.scheduleLaneID, qSchedulerLaneDetails.laneType, qSchedulerLaneDetails.laneUserName,
					qSchedulerLaneDetails.runnerType, qSchedulerLaneDetails.clones, qSchedulerLaneDetails.iterations, qSchedulerLaneDetails.rampUpDelay,
					qSchedulerLaneDetails.duration, qSchedulerLaneDetails.agentID, qSchedulerLaneDetails.createdBy, qSchedulerLaneDetails.createdDateTime,
					qSchedulerLaneDetails.updatedBy, qSchedulerLaneDetails.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}