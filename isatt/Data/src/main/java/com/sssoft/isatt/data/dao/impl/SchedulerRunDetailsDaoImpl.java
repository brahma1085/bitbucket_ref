package com.sssoft.isatt.data.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
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
import com.sssoft.isatt.data.dao.SchedulerRunDetailsDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QScheduler;
import com.sssoft.isatt.data.generated.pojo.QSchedulerrundetails;
import com.sssoft.isatt.data.generated.pojo.QTestdata;
import com.sssoft.isatt.data.generated.pojo.QTestplan;
import com.sssoft.isatt.data.pojo.SchedulerRunDetails;

/**
 * The Class SchedulerRunDetailsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for SchedulerRunDetailsDao
 * interface
 */
public class SchedulerRunDetailsDaoImpl implements SchedulerRunDetailsDao {

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

	/** The q scheduler run details. */
	private QSchedulerrundetails qSchedulerRunDetails = QSchedulerrundetails.schedulerrundetails;

	/** The q test plan. */
	private QTestplan qTestPlan = QTestplan.testplan;

	/** The q test data. */
	private QTestdata qTestData = QTestdata.testdata;

	/** The q scheduler. */
	private QScheduler qScheduler = QScheduler.scheduler;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SchedulerRunDetailsDaoImpl.class);

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
	 * The Class MappingSchedulerRunDetailsProjection.
	 */
	public class MappingSchedulerRunDetailsProjection extends MappingProjection<SchedulerRunDetails> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping scheduler run details projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingSchedulerRunDetailsProjection(Expression<?>... args) {
			super(SchedulerRunDetails.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected SchedulerRunDetails map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			SchedulerRunDetails schedulerRunDetails = new SchedulerRunDetails();
			schedulerRunDetails.setTestRunID(tuple.get(qSchedulerRunDetails.testRunID));
			schedulerRunDetails.setTestPlanID(tuple.get(qSchedulerRunDetails.testPlanID));
			schedulerRunDetails.setTestDataID(tuple.get(qSchedulerRunDetails.testDataID));
			schedulerRunDetails.setScheduleID(tuple.get(qSchedulerRunDetails.scheduleID));
			schedulerRunDetails.setResult(tuple.get(qSchedulerRunDetails.result));
			schedulerRunDetails.setRunTime(tuple.get(qSchedulerRunDetails.runTime));
			schedulerRunDetails.setPercentagePassCount(tuple.get(qSchedulerRunDetails.percentagePassCount));
			schedulerRunDetails.setPercentageFailCount(tuple.get(qSchedulerRunDetails.percentageFailCount));
			schedulerRunDetails.setStartDateTime(tuple.get(qSchedulerRunDetails.startDateTime));
			schedulerRunDetails.setEndDateTime(tuple.get(qSchedulerRunDetails.endDateTime));
			schedulerRunDetails.setAppID(tuple.get(qSchedulerRunDetails.appID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SchedulerRunDetails object : " + schedulerRunDetails);
			}
			return schedulerRunDetails;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param schedulerRunDetails
	 *            the scheduler run details
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertSchedulerRunDetails(final SchedulerRunDetails schedulerRunDetails) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerRunDetails(SchedulerRunDetails) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in SchedulerRunDetails: " + schedulerRunDetails);
		try {
			result = template.insert(qSchedulerRunDetails, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qSchedulerRunDetails.testRunID, qSchedulerRunDetails.testPlanID, qSchedulerRunDetails.testDataID, qSchedulerRunDetails.scheduleID, qSchedulerRunDetails.result, qSchedulerRunDetails.runTime, qSchedulerRunDetails.percentagePassCount, qSchedulerRunDetails.percentageFailCount, qSchedulerRunDetails.startDateTime, qSchedulerRunDetails.endDateTime, qSchedulerRunDetails.appID).values(schedulerRunDetails.getTestRunID(), schedulerRunDetails.getTestPlanID(), schedulerRunDetails.getTestDataID(), schedulerRunDetails.getScheduleID(), schedulerRunDetails.isResult(), schedulerRunDetails.getRunTime(), schedulerRunDetails.getPercentagePassCount(), schedulerRunDetails.getPercentageFailCount(), schedulerRunDetails.getStartDateTime(), schedulerRunDetails.getEndDateTime(), schedulerRunDetails.getAppID()).execute();
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
			LOG.debug("insertSchedulerRunDetails(SchedulerRunDetails) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param schedulerRunDetails
	 *            the scheduler run details
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateSchedulerRunDetails(final SchedulerRunDetails schedulerRunDetails) throws DataAccessException {
		LOG.info("Started updating data in SchedulerRunDetails: " + schedulerRunDetails);
		try {
			long returnlong = template.update(qSchedulerRunDetails, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qSchedulerRunDetails.testRunID.eq(schedulerRunDetails.getTestRunID())).set(qSchedulerRunDetails.result, schedulerRunDetails.isResult()).set(qSchedulerRunDetails.runTime, schedulerRunDetails.getRunTime()).set(qSchedulerRunDetails.endDateTime, schedulerRunDetails.getEndDateTime()).set(qSchedulerRunDetails.percentagePassCount, schedulerRunDetails.getPercentagePassCount()).set(qSchedulerRunDetails.percentageFailCount, schedulerRunDetails.getPercentageFailCount()).set(qSchedulerRunDetails.appID, schedulerRunDetails.getAppID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateSchedulerRunDetails(SchedulerRunDetails) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.SchedulerRunDetailsDao#getSchedulerRunDetails()
	 */
	@Override
	public List<SchedulerRunDetails> getSchedulerRunDetails() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerRunDetails() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qSchedulerRunDetails)
					.where(qSchedulerRunDetails.testPlanID.eq(qTestPlan.testPlanID).and(
							qSchedulerRunDetails.testDataID.eq(qTestData.testDataID).and(qSchedulerRunDetails.scheduleID.eq(qScheduler.scheduleID))));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerRunDetailsProjection(qSchedulerRunDetails.testRunID, qSchedulerRunDetails.testPlanID,
					qSchedulerRunDetails.testDataID, qSchedulerRunDetails.scheduleID, qSchedulerRunDetails.result, qSchedulerRunDetails.runTime,
					qSchedulerRunDetails.percentagePassCount, qSchedulerRunDetails.percentageFailCount, qSchedulerRunDetails.startDateTime,
					qSchedulerRunDetails.endDateTime, qSchedulerRunDetails.appID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerRunDetailsDao#getSchedulerRunDetailsById
	 * (int)
	 */
	@Override
	public List<SchedulerRunDetails> getSchedulerRunDetailsById(int testAppId,String type) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerRunDetailsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery=template.newSqlQuery().from(qSchedulerRunDetails).where(qSchedulerRunDetails.appID.eq(testAppId))
					.orderBy(qSchedulerRunDetails.testRunID.desc()).limit(15);
			if("All".equalsIgnoreCase(type))
				sqlQuery = template.newSqlQuery().from(qSchedulerRunDetails).where(qSchedulerRunDetails.appID.eq(testAppId));
			else if("week".equalsIgnoreCase(type)) {
				Calendar cal = Calendar.getInstance();
				Timestamp today = new Timestamp(cal.getTimeInMillis()); //Current Time
				cal.set(Calendar.HOUR_OF_DAY, 0); 
				cal.clear(Calendar.MINUTE);
				cal.clear(Calendar.SECOND);
				cal.clear(Calendar.MILLISECOND);
				cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
				Timestamp weekStart = new Timestamp(cal.getTimeInMillis()); // Week Start Date with time 00:00:00
				sqlQuery = template.newSqlQuery().from(qSchedulerRunDetails).where(qSchedulerRunDetails.appID.eq(testAppId),qSchedulerRunDetails.startDateTime.between(weekStart,today));
			} else if(type.toLowerCase().startsWith("time")) {
				String[] time=type.split(":");
				Timestamp start= new Timestamp(Long.parseLong(time[1]));
				Timestamp end= new Timestamp(Long.parseLong(time[2]));
				sqlQuery = template.newSqlQuery().from(qSchedulerRunDetails).where(qSchedulerRunDetails.appID.eq(testAppId),qSchedulerRunDetails.startDateTime.between(start,end));
			}
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerRunDetailsProjection(qSchedulerRunDetails.testRunID, qSchedulerRunDetails.testPlanID,
					qSchedulerRunDetails.testDataID, qSchedulerRunDetails.scheduleID, qSchedulerRunDetails.result, qSchedulerRunDetails.runTime,
					qSchedulerRunDetails.percentagePassCount, qSchedulerRunDetails.percentageFailCount, qSchedulerRunDetails.startDateTime,
					qSchedulerRunDetails.endDateTime, qSchedulerRunDetails.appID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerRunDetailsDao#getAllSchedulerRunDetails()
	 */
	@Override
	public List<SchedulerRunDetails> getAllSchedulerRunDetails() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllSchedulerRunDetails() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qSchedulerRunDetails);
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingSchedulerRunDetailsProjection(qSchedulerRunDetails.testRunID, qSchedulerRunDetails.testPlanID,
					qSchedulerRunDetails.testDataID, qSchedulerRunDetails.scheduleID, qSchedulerRunDetails.result, qSchedulerRunDetails.runTime,
					qSchedulerRunDetails.percentagePassCount, qSchedulerRunDetails.percentageFailCount, qSchedulerRunDetails.startDateTime,
					qSchedulerRunDetails.endDateTime, qSchedulerRunDetails.appID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.SchedulerRunDetailsDao#insertSchedulerRunDetailsGetKey
	 * (com.sssoft.isatt.data.pojo.SchedulerRunDetails)
	 */
	@Override
	public int insertSchedulerRunDetailsGetKey(final SchedulerRunDetails schedulerRunDetails) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerRunDetailsGetKey(SchedulerRunDetails) - start"); //$NON-NLS-1$
		}

		int schedulerRunDetailsId = 0;
		try {
			schedulerRunDetailsId = template.insertWithKey(qSchedulerRunDetails, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qSchedulerRunDetails.testRunID, qSchedulerRunDetails.testPlanID, qSchedulerRunDetails.testDataID, qSchedulerRunDetails.scheduleID, qSchedulerRunDetails.runTime, qSchedulerRunDetails.result, qSchedulerRunDetails.startDateTime, qSchedulerRunDetails.endDateTime, qSchedulerRunDetails.percentagePassCount, qSchedulerRunDetails.percentageFailCount, qSchedulerRunDetails.appID).values(schedulerRunDetails.getTestRunID(), schedulerRunDetails.getTestPlanID(), schedulerRunDetails.getTestDataID(), schedulerRunDetails.getScheduleID(), schedulerRunDetails.getRunTime(), schedulerRunDetails.isResult(), schedulerRunDetails.getStartDateTime(), schedulerRunDetails.getEndDateTime(), schedulerRunDetails.getPercentagePassCount(), schedulerRunDetails.getPercentageFailCount(), schedulerRunDetails.getAppID()).executeWithKey(qSchedulerRunDetails.testRunID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Generated schedulerRunDetailsId : " + schedulerRunDetailsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertSchedulerRunDetailsGetKey(SchedulerRunDetails) - end"); //$NON-NLS-1$
		}
		return schedulerRunDetailsId;
	}

}