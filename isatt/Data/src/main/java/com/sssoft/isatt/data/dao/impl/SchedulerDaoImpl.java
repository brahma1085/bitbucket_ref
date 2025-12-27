package com.sssoft.isatt.data.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
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
import com.sssoft.isatt.data.dao.SchedulerDao;
import com.sssoft.isatt.data.dao.input.TestDataDao;
import com.sssoft.isatt.data.dao.input.TestPlanDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QScheduler;
import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.def.DataSets;
import com.sssoft.isatt.data.pojo.def.ScheduledJobs;
import com.sssoft.isatt.data.pojo.input.TestPlan;

/**
 * The Class SchedulerDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for SchedulerDao interface
 */
public class SchedulerDaoImpl implements SchedulerDao {

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

	/** The q scheduler. */
	private QScheduler qScheduler = QScheduler.scheduler;

	/** The test plan dao. */
	private TestPlanDao testPlanDao;

	/** The test data dao. */
	private TestDataDao testDataDao;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SchedulerDaoImpl.class);

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
	 * Sets the test plan dao.
	 * 
	 * @param testPlanDao
	 *            the testPlanDao to set
	 */
	public void setTestPlanDao(TestPlanDao testPlanDao) {
		this.testPlanDao = testPlanDao;
	}

	/**
	 * Sets the test data dao.
	 * 
	 * @param testDataDao
	 *            the new test data dao
	 */
	public void setTestDataDao(TestDataDao testDataDao) {
		this.testDataDao = testDataDao;
	}

	/**
	 * The Class MappingSchedulerProjection.
	 */
	public class MappingSchedulerProjection extends MappingProjection<Scheduler> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping scheduler projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingSchedulerProjection(Expression<?>... args) {
			super(Scheduler.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Scheduler map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Scheduler scheduler = new Scheduler();
			scheduler.setScheduleID(tuple.get(qScheduler.scheduleID));
			scheduler.setSchedulerName(tuple.get(qScheduler.schedulerName));
			scheduler.setTestPlanID(tuple.get(qScheduler.testPlanID));
			scheduler.setTestDataID(tuple.get(qScheduler.testDataID));
			scheduler.setAgentID(tuple.get(qScheduler.agentID));
			scheduler.setScheduleTime(new Timestamp(tuple.get(qScheduler.scheduleTime).getTime()));
			scheduler.setStatus(String.valueOf(tuple.get(qScheduler.status)));
			scheduler.setFrequency(tuple.get(qScheduler.frequency));
			scheduler.setNotifications(tuple.get(qScheduler.notifications));
			scheduler.setMultiLanes(Boolean.parseBoolean(tuple.get(qScheduler.multiLanes)));
			scheduler.setCreatedBy(tuple.get(qScheduler.createdBy));
			scheduler.setCreatedDateTime(tuple.get(qScheduler.createdDateTime));
			scheduler.setUpdatedBy(tuple.get(qScheduler.updatedBy));
			scheduler.setUpdatedDateTime(tuple.get(qScheduler.updatedDateTime));
			scheduler.setAppID(tuple.get(qScheduler.appID));
			scheduler.setFailureCount(tuple.get(qScheduler.failureCount));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Actions object : " + scheduler);
			}
			return scheduler;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertScheduler(final Scheduler scheduler) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScheduler(Scheduler) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Scheduler: " + scheduler);
		try {
			result = template.insert(qScheduler, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qScheduler.scheduleID, qScheduler.schedulerName, qScheduler.testPlanID, qScheduler.testDataID, qScheduler.agentID,
									qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency, qScheduler.notifications, qScheduler.multiLanes,
									qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy, qScheduler.updatedDateTime, qScheduler.appID,
									qScheduler.failureCount)
							.values(scheduler.getScheduleID(), scheduler.getSchedulerName(), scheduler.getTestPlanID(), scheduler.getTestDataID(),
									scheduler.getAgentID(), scheduler.getScheduleTime(), scheduler.getStatus(), scheduler.getFrequency(),
									scheduler.getNotifications(), scheduler.isMultiLanes(), scheduler.getCreatedBy(), scheduler.getCreatedDateTime(),
									scheduler.getUpdatedBy(), scheduler.getUpdatedDateTime(), scheduler.getAppID(), scheduler.getFailureCount()).execute();
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

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScheduler(Scheduler) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param scheduler
	 *            the scheduler
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateScheduler(final Scheduler scheduler) throws DataAccessException {
		LOG.info("Started updating data in Scheduler: " + scheduler);
		try {
			long returnlong = template.update(qScheduler, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qScheduler.scheduleID.eq(scheduler.getScheduleID()))
							.set(qScheduler.schedulerName, scheduler.getSchedulerName()).set(qScheduler.testPlanID, scheduler.getTestPlanID())
							.set(qScheduler.testDataID, scheduler.getTestDataID()).set(qScheduler.scheduleTime,scheduler.getScheduleTime())
							.set(qScheduler.status, scheduler.getStatus()).set(qScheduler.agentID, scheduler.getAgentID())
							.set(qScheduler.frequency, scheduler.getFrequency()).set(qScheduler.notifications, scheduler.getNotifications())
							.set(qScheduler.multiLanes, String.valueOf(scheduler.isMultiLanes())).set(qScheduler.updatedBy, scheduler.getUpdatedBy())
							.set(qScheduler.updatedDateTime, scheduler.getUpdatedDateTime()).set(qScheduler.failureCount, scheduler.getFailureCount())
							.execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScheduler(Scheduler) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#getScheduler()
	 */
	@Override
	public List<Scheduler> getScheduler() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduler() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName, qScheduler.testPlanID,
					qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency, qScheduler.notifications,
					qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy, qScheduler.updatedDateTime,
					qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#getSchedulerById(int)
	 */
	@Override
	public Scheduler getSchedulerById(int schedulerId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler).where(qScheduler.scheduleID.eq(schedulerId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName,
					qScheduler.testPlanID, qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency,
					qScheduler.notifications, qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy,
					qScheduler.updatedDateTime, qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerDao#getSchedulerByIdStatusFailCount(int)
	 */
	@Override
	public Scheduler getSchedulerByIdStatusFailCount(int schedulerId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerByIdStatusFailCount(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler)
					.where(qScheduler.scheduleID.eq(schedulerId).and(qScheduler.status.eq("N")).and(qScheduler.failureCount.eq(0)));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName,
					qScheduler.testPlanID, qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency,
					qScheduler.notifications, qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy,
					qScheduler.updatedDateTime, qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerDao#insertSchedulerGetKey(com.exilant.tfw
	 * .pojo.Scheduler)
	 */
	@Override
	public int insertSchedulerGetKey(final Scheduler scheduler) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerGetKey(Scheduler) - start"); //$NON-NLS-1$
		}

		int schedulerId = 0;
		try {
			schedulerId = template.insertWithKey(qScheduler, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qScheduler.scheduleID, qScheduler.schedulerName, qScheduler.testPlanID, qScheduler.testDataID, qScheduler.agentID,
									qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency, qScheduler.notifications, qScheduler.multiLanes,
									qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy, qScheduler.updatedDateTime, qScheduler.appID,
									qScheduler.failureCount)
							.values(scheduler.getScheduleID(), scheduler.getSchedulerName(), scheduler.getTestPlanID(), scheduler.getTestDataID(),
									scheduler.getAgentID(), scheduler.getScheduleTime(), scheduler.getStatus(), scheduler.getFrequency(),
									scheduler.getNotifications(), scheduler.isMultiLanes(), scheduler.getCreatedBy(), scheduler.getCreatedDateTime(),
									scheduler.getUpdatedBy(), scheduler.getUpdatedDateTime(), scheduler.getAppID(), scheduler.getFailureCount())
							.executeWithKey(qScheduler.scheduleID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated schedulerId : " + schedulerId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerGetKey(Scheduler) - end"); //$NON-NLS-1$
		}
		return schedulerId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerDao#updateSchedulerTime(com.exilant.tfw.
	 * pojo.Scheduler)
	 */
	@Override
	public long updateSchedulerTime(final Scheduler scheduler) throws DataAccessException {

		LOG.info("Started updating Scheduler Time: " + scheduler.getScheduleTime());
		try {
			long returnlong = template.update(qScheduler, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qScheduler.scheduleID.eq(scheduler.getScheduleID()))
							.set(qScheduler.scheduleTime, scheduler.getScheduleTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerTime(Scheduler) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.SchedulerDao#updateSchedulerFailureCountWithTime(
	 * com.sssoft.isatt.data.pojo.Scheduler)
	 */
	@Override
	public long updateSchedulerFailureCountWithTime(final Scheduler scheduler) throws DataAccessException {

		LOG.info("Started updating Scheduler Time: " + scheduler.getScheduleTime());
		try {
			long returnlong = template.update(qScheduler, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qScheduler.scheduleID.eq(scheduler.getScheduleID()))
							.set(qScheduler.failureCount, scheduler.getFailureCount()).set(qScheduler.scheduleTime, scheduler.getScheduleTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerFailureCountWithTime(Scheduler) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.SchedulerDao#updateSchedulerStatus(com.exilant.tfw
	 * .pojo.Scheduler)
	 */
	@Override
	public long updateSchedulerStatus(final Scheduler scheduler) throws DataAccessException {

		LOG.info("Started updating Scheduler Time: " + scheduler.getScheduleTime());
		try {
			long returnlong = template.update(qScheduler, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qScheduler.scheduleID.eq(scheduler.getScheduleID()))
							.set(qScheduler.status, scheduler.getStatus()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerStatus(Scheduler) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.SchedulerDao#fetchSchedulerByTime(java.sql.Timestamp)
	 */
	@Override
	public List<Scheduler> fetchSchedulerByTime(Timestamp scheduledTime) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchSchedulerByTime(Timestamp) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler).where(qScheduler.scheduleTime.loe(scheduledTime).and(qScheduler.status.eq("N")))
					.orderBy(qScheduler.scheduleID.asc());
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName, qScheduler.testPlanID,
					qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency, qScheduler.notifications,
					qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy, qScheduler.updatedDateTime,
					qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#getSchedulerByAppId(int)
	 */
	@Override
	public Scheduler getSchedulerByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler).where(qScheduler.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName,
					qScheduler.testPlanID, qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency,
					qScheduler.notifications, qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy,
					qScheduler.updatedDateTime, qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#getSchedulerListByAppId(int)
	 */
	@Override
	public List<TestPlan> getSchedulerListByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerListByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			Scheduler scheduler = this.getSchedulerByAppId(appId);
			List<TestPlan> returnList = this.testPlanDao.getTestPlanNamesByTestPlanId(scheduler.getTestPlanID());
			if (LOG.isDebugEnabled()) {
				LOG.debug("getSchedulerListByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerDao#getDataSetsBySchedulerIdByAppId(int)
	 */
	public List<DataSets> getDataSetsBySchedulerIdByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataSetsBySchedulerIdByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler).where(qScheduler.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			List<Integer> scheduleIds = template.query(sqlQuery, qScheduler.scheduleID);
			List<DataSets> returnList = getPlanNamesByScheduleId(scheduleIds);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getDataSetsBySchedulerIdByAppId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerDao#getPlanNamesByScheduleId(java.util.List)
	 */
	public List<DataSets> getPlanNamesByScheduleId(List<Integer> scheduleIds) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanNamesByScheduleId(List<Integer>) - start"); //$NON-NLS-1$
		}

		List<DataSets> resultDataSets = new ArrayList<DataSets>();
		Iterator<Integer> iterator = scheduleIds.iterator();
		while (iterator.hasNext()) {
			Integer schedulerId = (Integer) iterator.next();
			Scheduler scheduler = this.getSchedulerByIdStatusFailCount(schedulerId);
			if (scheduler != null) {
				DataSets dataSets = new DataSets();
				String planName = this.testPlanDao.getPlanNameById(scheduler.getTestPlanID());
				dataSets.setPlanName(planName);
				String dataDescription = this.testDataDao.getDataDescriptionById(scheduler.getTestDataID());
				dataSets.setDataDescription(dataDescription);
				dataSets.setCreatedBy(scheduler.getCreatedBy());
				dataSets.setCreatedDate(scheduler.getCreatedDateTime());
				dataSets.setSchedulerId(scheduler.getScheduleID());
				dataSets.setSchedulerName(scheduler.getSchedulerName());
				dataSets.setPlanId(scheduler.getTestPlanID());
				dataSets.setDataId(scheduler.getTestDataID());
				dataSets.setNotification(scheduler.getNotifications());
				dataSets.setMultiLanes(scheduler.isMultiLanes());
				dataSets.setAgentId(scheduler.getAgentID());
				dataSets.setFrequency(scheduler.getFrequency());
				resultDataSets.add(dataSets);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanNamesByScheduleId(List<Integer>) - end"); //$NON-NLS-1$
		}
		return resultDataSets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#getSchedulerFilterByAppID(int)
	 */
	@Override
	public List<Scheduler> getSchedulerFilterByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerFilterByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler).where(qScheduler.appID.eq(appId));
			;
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName, qScheduler.testPlanID,
					qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency, qScheduler.notifications,
					qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy, qScheduler.updatedDateTime,
					qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#getSchedulerByStatus()
	 */
	@Override
	public List<Scheduler> getSchedulerByStatus() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerByStatus() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScheduler)
					.where(qScheduler.status.eq("Y").or(qScheduler.status.eq("F").and(qScheduler.failureCount.gt(0))));

			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerProjection(qScheduler.scheduleID, qScheduler.schedulerName, qScheduler.testPlanID,
					qScheduler.testDataID, qScheduler.agentID, qScheduler.scheduleTime, qScheduler.status, qScheduler.frequency, qScheduler.notifications,
					qScheduler.multiLanes, qScheduler.createdBy, qScheduler.createdDateTime, qScheduler.updatedBy, qScheduler.updatedDateTime,
					qScheduler.appID, qScheduler.failureCount));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.SchedulerDao#deleteSchedulerById(int)
	 */
	@Override
	public long deleteSchedulerById(final int schedulerId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSchedulerById(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("deleting the records where Scheduler id : " + schedulerId);
		try {
			result = template.delete(qScheduler, new SqlDeleteCallback() {

				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qScheduler.scheduleID.eq(schedulerId)).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info("number of rows deleted : " + result);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteSchedulerById(int) - end"); //$NON-NLS-1$
		}
		return result;
	}

	@Override
	public List<ScheduledJobs> getScheduledJobsForApp(final int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledJobsForApp(int) - start"); //$NON-NLS-1$
		}
		try {
			String query = "call scheduler_details(" + appId + ")";
			List<Map<String, Object>> sqlQuery = template.getJdbcOperations().queryForList(query);
			Iterator<Map<String, Object>> iterator = sqlQuery.iterator();
			List<ScheduledJobs> scheduledJobs = new ArrayList<ScheduledJobs>();
			while (iterator.hasNext()) {
				ScheduledJobs scheduledJobs2 = new ScheduledJobs();
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				scheduledJobs2.setSchedulerName((String) map.get("SchedulerName"));
				scheduledJobs2.setStatus((String) map.get("Status"));
				scheduledJobs2.setPlanName((String) map.get("PlanName"));
				scheduledJobs2.setAgentName((String) map.get("AgentName"));
				scheduledJobs2.setTestDataDescription((String) map.get("TestDataDescription"));
				scheduledJobs2.setSchedulerId(MapUtils.getIntValue(map, "ScheduleID"));
				scheduledJobs2.setFrequency(MapUtils.getIntValue(map, "Frequency"));
				scheduledJobs2.setAppId(MapUtils.getIntValue(map, "AppID"));
				scheduledJobs2.setFailureCount(MapUtils.getIntValue(map, "FailureCount"));
				scheduledJobs2.setDurationInSeconds(MapUtils.getIntValue(map, "DurationInSec"));
				scheduledJobs2.setTestPlanId(MapUtils.getIntValue(map, "TestPlanID"));
				scheduledJobs2.setTestDataId(MapUtils.getIntValue(map, "TestDataID"));
				scheduledJobs2.setAgentId(MapUtils.getIntValue(map, "AgentID"));
				scheduledJobs2.setResult(MapUtils.getBooleanValue(map, "Result"));
				scheduledJobs2.setSchedulerStartTime((Timestamp) MapUtils.getObject(map, "StartDateTime"));
				scheduledJobs2.setScheduleEndTime((Timestamp) MapUtils.getObject(map, "EndDateTime"));
				Object object = map.get("ScheduleTime");
				if (object != null) {
					if (object instanceof Timestamp) {
						scheduledJobs2.setNextScheduleTime((Timestamp) object);
					} else {
						String time = new String((byte[]) object);
						scheduledJobs2.setNextScheduleTime(Timestamp.valueOf(time));
					}
				}
				scheduledJobs.add(scheduledJobs2);
			}
			LOG.info("Returning the results : " + sqlQuery);
			return scheduledJobs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<ScheduledJobs> getScheduledRunningJobsForApp(final int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledJobsForApp(int) - start"); //$NON-NLS-1$
		}
		try {
			String query = "call scheduler_details_running(" + appId + ");";
			List<Map<String, Object>> sqlQuery = template.getJdbcOperations().queryForList(query);
			Iterator<Map<String, Object>> iterator = sqlQuery.iterator();
			List<ScheduledJobs> scheduledJobs = new ArrayList<ScheduledJobs>();
			while (iterator.hasNext()) {
				ScheduledJobs scheduledJobs2 = new ScheduledJobs();
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				scheduledJobs2.setSchedulerName((String) map.get("SchedulerName"));
				scheduledJobs2.setStatus((String) map.get("Status"));
				scheduledJobs2.setPlanName((String) map.get("PlanName"));
				scheduledJobs2.setAgentName((String) map.get("AgentName"));
				scheduledJobs2.setTestDataDescription((String) map.get("TestDataDescription"));
				scheduledJobs2.setSchedulerId(MapUtils.getIntValue(map, "ScheduleID"));
				scheduledJobs2.setFrequency(MapUtils.getIntValue(map, "Frequency"));
				scheduledJobs2.setAppId(MapUtils.getIntValue(map, "AppID"));
				scheduledJobs2.setFailureCount(MapUtils.getIntValue(map, "FailureCount"));
				scheduledJobs2.setTestPlanId(MapUtils.getIntValue(map, "TestPlanID"));
				scheduledJobs2.setTestDataId(MapUtils.getIntValue(map, "TestDataID"));
				scheduledJobs2.setAgentId(MapUtils.getIntValue(map, "AgentID"));
				scheduledJobs2.setSchedulerStartTime((Timestamp) MapUtils.getObject(map, "StartDateTime"));
				Object object = map.get("ScheduleTime");
				if (object != null) {
					if (object instanceof Timestamp) {
						scheduledJobs2.setNextScheduleTime((Timestamp) object);
					} else {
						String time = new String((byte[]) object);
						scheduledJobs2.setNextScheduleTime(Timestamp.valueOf(time));
					}
				}
				scheduledJobs.add(scheduledJobs2);
			}
			LOG.info("Returning the results : " + sqlQuery);
			return scheduledJobs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<ScheduledJobs> getScheduledNotRunJobsForApp(final int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledJobsForApp(int) - start"); //$NON-NLS-1$
		}
		try {
			String query = "call scheduler_details_scheduled(" + appId + ");";
			List<Map<String, Object>> sqlQuery = template.getJdbcOperations().queryForList(query);
			Iterator<Map<String, Object>> iterator = sqlQuery.iterator();
			List<ScheduledJobs> scheduledJobs = new ArrayList<ScheduledJobs>();
			while (iterator.hasNext()) {
				ScheduledJobs scheduledJobs2 = new ScheduledJobs();
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				scheduledJobs2.setSchedulerName((String) map.get("SchedulerName"));
				scheduledJobs2.setStatus((String) map.get("Status"));
				scheduledJobs2.setPlanName((String) map.get("PlanName"));
				scheduledJobs2.setAgentName((String) map.get("AgentName"));
				scheduledJobs2.setTestDataDescription((String) map.get("TestDataDescription"));
				scheduledJobs2.setSchedulerId(MapUtils.getIntValue(map, "ScheduleID"));
				scheduledJobs2.setFrequency(MapUtils.getIntValue(map, "Frequency"));
				scheduledJobs2.setAppId(MapUtils.getIntValue(map, "AppID"));
				scheduledJobs2.setFailureCount(MapUtils.getIntValue(map, "FailureCount"));
				scheduledJobs2.setTestPlanId(MapUtils.getIntValue(map, "TestPlanID"));
				scheduledJobs2.setTestDataId(MapUtils.getIntValue(map, "TestDataID"));
				scheduledJobs2.setAgentId(MapUtils.getIntValue(map, "AgentID"));
				Object object = map.get("ScheduleTime");
				if (object != null) {
					if (object instanceof Timestamp) {
						scheduledJobs2.setNextScheduleTime((Timestamp) object);
					} else {
						String time = new String((byte[]) object);
						scheduledJobs2.setNextScheduleTime(Timestamp.valueOf(time));
					}
				}
				scheduledJobs.add(scheduledJobs2);
			}
			LOG.info("Returning the results : " + sqlQuery);
			return scheduledJobs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<ScheduledJobs> getScheduledCompletedJobsForApp(final int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScheduledJobsForApp(int) - start"); //$NON-NLS-1$
		}
		try {
			String query = "call scheduler_details_completed(" + appId + ");";
			List<Map<String, Object>> sqlQuery = template.getJdbcOperations().queryForList(query);
			Iterator<Map<String, Object>> iterator = sqlQuery.iterator();
			List<ScheduledJobs> scheduledJobs = new ArrayList<ScheduledJobs>();
			while (iterator.hasNext()) {
				ScheduledJobs scheduledJobs2 = new ScheduledJobs();
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				scheduledJobs2.setSchedulerName((String) map.get("SchedulerName"));
				scheduledJobs2.setStatus((String) map.get("Status"));
				scheduledJobs2.setPlanName((String) map.get("PlanName"));
				scheduledJobs2.setAgentName((String) map.get("AgentName"));
				scheduledJobs2.setTestDataDescription((String) map.get("TestDataDescription"));
				scheduledJobs2.setSchedulerId(MapUtils.getIntValue(map, "ScheduleID"));
				scheduledJobs2.setFrequency(MapUtils.getIntValue(map, "Frequency"));
				scheduledJobs2.setAppId(MapUtils.getIntValue(map, "AppID"));
				scheduledJobs2.setFailureCount(MapUtils.getIntValue(map, "FailureCount"));
				scheduledJobs2.setDurationInSeconds(MapUtils.getIntValue(map, "DurationInSec"));
				scheduledJobs2.setTestPlanId(MapUtils.getIntValue(map, "TestPlanID"));
				scheduledJobs2.setTestDataId(MapUtils.getIntValue(map, "TestDataID"));
				scheduledJobs2.setAgentId(MapUtils.getIntValue(map, "AgentID"));
				scheduledJobs2.setResult(MapUtils.getBooleanValue(map, "Result"));
				scheduledJobs2.setSchedulerStartTime((Timestamp) MapUtils.getObject(map, "StartDateTime"));
				scheduledJobs2.setScheduleEndTime((Timestamp) MapUtils.getObject(map, "EndDateTime"));
				Object object = map.get("ScheduleTime");
				if (object != null) {
					if (object instanceof Timestamp) {
						scheduledJobs2.setNextScheduleTime((Timestamp) object);
					} else {
						String time = new String((byte[]) object);
						scheduledJobs2.setNextScheduleTime(Timestamp.valueOf(time));
					}
				}
				scheduledJobs.add(scheduledJobs2);
			}
			LOG.info("Returning the results : " + sqlQuery);
			return scheduledJobs;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
}