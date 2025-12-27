package com.exilant.tfw.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.SchedulerBackupDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QSchedulerbackup;
import com.exilant.tfw.pojo.SchedulerBackup;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class SchedulerBackupDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for SchedulerBackupDao
 * interface
 */
public class SchedulerBackupDaoImpl implements SchedulerBackupDao {

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

	/** The q scheduler backup. */
	private QSchedulerbackup qSchedulerBackup = QSchedulerbackup.schedulerbackup;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SchedulerBackupDaoImpl.class);

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
	 * The Class MappingSchedulerBackupProjection.
	 */
	public class MappingSchedulerBackupProjection extends MappingProjection<SchedulerBackup> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping scheduler backup projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingSchedulerBackupProjection(Expression<?>... args) {
			super(SchedulerBackup.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected SchedulerBackup map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			SchedulerBackup schedulerBackup = new SchedulerBackup();
			schedulerBackup.setScheduleID(tuple.get(qSchedulerBackup.schedulerbackupID));
			schedulerBackup.setSchedulerName(tuple.get(qSchedulerBackup.schedulerName));
			schedulerBackup.setTestPlanID(tuple.get(qSchedulerBackup.testPlanID));
			schedulerBackup.setTestDataID(tuple.get(qSchedulerBackup.testDataID));
			schedulerBackup.setAgentID(tuple.get(qSchedulerBackup.agentID));
			schedulerBackup.setAppID(tuple.get(qSchedulerBackup.appID));
			schedulerBackup.setFailureCount(tuple.get(qSchedulerBackup.failureCount));
			schedulerBackup.setScheduleTime(new Timestamp(tuple.get(qSchedulerBackup.scheduleTime).getTime()));
			schedulerBackup.setStatus(String.valueOf(tuple.get(qSchedulerBackup.status)));
			schedulerBackup.setFrequency(tuple.get(qSchedulerBackup.frequency));
			schedulerBackup.setNotifications(tuple.get(qSchedulerBackup.frequency));
			schedulerBackup.setMultiLanes(Boolean.getBoolean(tuple.get(qSchedulerBackup.multiLanes)));
			schedulerBackup.setCreatedBy(tuple.get(qSchedulerBackup.createdBy));
			schedulerBackup.setCreatedDateTime(tuple.get(qSchedulerBackup.createdDateTime));
			schedulerBackup.setUpdatedBy(tuple.get(qSchedulerBackup.updatedBy));
			schedulerBackup.setUpdatedDateTime(tuple.get(qSchedulerBackup.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SchedulerBackup object : " + schedulerBackup);
			}
			return schedulerBackup;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param schedulerBackup
	 *            the scheduler backup
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public int insertSchedulerGetKey(final SchedulerBackup schedulerBackup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerGetKey(SchedulerBackup) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in SchedulerBackup: " + schedulerBackup);
		try {
			result = template.insert(qSchedulerBackup, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qSchedulerBackup.schedulerbackupID, qSchedulerBackup.schedulerName, qSchedulerBackup.testPlanID, qSchedulerBackup.testDataID, qSchedulerBackup.agentID, qSchedulerBackup.appID, qSchedulerBackup.failureCount, qSchedulerBackup.scheduleTime, qSchedulerBackup.status, qSchedulerBackup.frequency, qSchedulerBackup.notifications, qSchedulerBackup.multiLanes, qSchedulerBackup.createdBy, qSchedulerBackup.createdDateTime, qSchedulerBackup.updatedBy, qSchedulerBackup.updatedDateTime).values(schedulerBackup.getScheduleID(), schedulerBackup.getSchedulerName(), schedulerBackup.getTestPlanID(), schedulerBackup.getTestDataID(), schedulerBackup.getAgentID(), schedulerBackup.getAppID(), schedulerBackup.getFailureCount(), schedulerBackup.getScheduleTime(), schedulerBackup.getStatus(), schedulerBackup.getFrequency(), schedulerBackup.getNotifications(), schedulerBackup.isMultiLanes(), schedulerBackup.getCreatedBy(), schedulerBackup.getCreatedDateTime(), schedulerBackup.getUpdatedBy(), schedulerBackup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Scheduler");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
		int returnint = (int) result;
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerGetKey(SchedulerBackup) - end"); //$NON-NLS-1$
		}
		return returnint;

	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param schedulerBackup
	 *            the scheduler backup
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateScheduler(final SchedulerBackup schedulerBackup) throws DataAccessException {
		LOG.info("Started updating data in SchedulerBackup: " + schedulerBackup);
		try {
			long returnlong = template.update(qSchedulerBackup, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qSchedulerBackup.schedulerbackupID.eq(schedulerBackup.getScheduleID())).set(qSchedulerBackup.schedulerName, schedulerBackup.getSchedulerName()).set(qSchedulerBackup.testPlanID, schedulerBackup.getTestDataID()).set(qSchedulerBackup.agentID, schedulerBackup.getAgentID()).set(qSchedulerBackup.appID, schedulerBackup.getAppID()).set(qSchedulerBackup.failureCount, schedulerBackup.getFailureCount()).set(qSchedulerBackup.status, schedulerBackup.getStatus()).set(qSchedulerBackup.frequency, schedulerBackup.getFrequency()).set(qSchedulerBackup.notifications, schedulerBackup.getNotifications()).set(qSchedulerBackup.updatedBy, schedulerBackup.getUpdatedBy()).set(qSchedulerBackup.updatedDateTime, schedulerBackup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScheduler(SchedulerBackup) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.SchedulerBackupDao#getSchedulerBackup()
	 */
	@Override
	public List<SchedulerBackup> getSchedulerBackup() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerBackup() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerBackup);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerBackupProjection(qSchedulerBackup.schedulerbackupID, qSchedulerBackup.schedulerName,
					qSchedulerBackup.testPlanID, qSchedulerBackup.testDataID, qSchedulerBackup.agentID, qSchedulerBackup.appID,
					qSchedulerBackup.failureCount, qSchedulerBackup.scheduleTime, qSchedulerBackup.status, qSchedulerBackup.frequency,
					qSchedulerBackup.notifications, qSchedulerBackup.multiLanes, qSchedulerBackup.createdBy, qSchedulerBackup.createdDateTime,
					qSchedulerBackup.updatedBy, qSchedulerBackup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.SchedulerBackupDao#getSchedulerBackupById(int)
	 */
	@Override
	public SchedulerBackup getSchedulerBackupById(int schedulerBackupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerBackupById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerBackup).where(qSchedulerBackup.schedulerbackupID.eq(schedulerBackupId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingSchedulerBackupProjection(qSchedulerBackup.schedulerbackupID,
					qSchedulerBackup.schedulerName, qSchedulerBackup.testPlanID, qSchedulerBackup.testDataID, qSchedulerBackup.agentID,
					qSchedulerBackup.appID, qSchedulerBackup.failureCount, qSchedulerBackup.scheduleTime, qSchedulerBackup.status,
					qSchedulerBackup.frequency, qSchedulerBackup.notifications, qSchedulerBackup.multiLanes, qSchedulerBackup.createdBy,
					qSchedulerBackup.createdDateTime, qSchedulerBackup.updatedBy, qSchedulerBackup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}