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
import com.sssoft.isatt.data.dao.output.TaskResultDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QTask;
import com.sssoft.isatt.data.generated.pojo.QTaskresult;
import com.sssoft.isatt.data.pojo.output.TaskResult;

/**
 * The Class TaskResultDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TaskResultDao interface
 */
public class TaskResultDaoImpl implements TaskResultDao {

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

	/** The q task result. */
	private QTaskresult qTaskResult = QTaskresult.taskresult;

	/** The q task. */
	private QTask qTask = QTask.task;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TaskResultDaoImpl.class);

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
	 * The Class MappingTaskResultProjection.
	 */
	public class MappingTaskResultProjection extends MappingProjection<TaskResult> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping task result projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTaskResultProjection(Expression<?>... args) {
			super(TaskResult.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TaskResult map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TaskResult taskResult = new TaskResult();
			taskResult.setTaskResultID(tuple.get(qTaskResult.taskResultID));
			taskResult.setTaskID(tuple.get(qTaskResult.taskID));
			taskResult.setLaneID(tuple.get(qTaskResult.laneID));
			taskResult.setRunResultID(tuple.get(qTaskResult.runResultID));
			taskResult.setReportFilePath(tuple.get(qTaskResult.reportFilePath));
			taskResult.setStartDateTime(tuple.get(qTaskResult.startDateTime));
			taskResult.setEndDateTime(tuple.get(qTaskResult.endDateTime));
			taskResult.setPercentagePassCount(tuple.get(qTaskResult.percentagePassCount));
			taskResult.setPercentageFailCount(tuple.get(qTaskResult.percentageFailCount));
			taskResult.setResult(tuple.get(qTaskResult.result));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TaskResult object : " + taskResult);
			}
			return taskResult;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param taskResult
	 *            the task result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTaskResult(final TaskResult taskResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTaskResult(TaskResult) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TaskResult: " + taskResult);
		try {
			result = template.insert(qTaskResult, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTaskResult.taskResultID, qTaskResult.taskID, qTaskResult.laneID, qTaskResult.runResultID, qTaskResult.reportFilePath, qTaskResult.result, qTaskResult.percentagePassCount, qTaskResult.percentageFailCount, qTaskResult.startDateTime, qTaskResult.endDateTime).values(taskResult.getTaskResultID(), taskResult.getTaskID(), taskResult.getLaneID(), taskResult.getRunResultID(), taskResult.getReportFilePath(), taskResult.isResult(), taskResult.getPercentagePassCount(), taskResult.getPercentageFailCount(), taskResult.getStartDateTime(), taskResult.getEndDateTime()).execute();
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
			LOG.debug("insertTaskResult(TaskResult) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param taskResult
	 *            the task result
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTaskResult(final TaskResult taskResult) throws DataAccessException {
		LOG.info("Started updating data in TaskResult: " + taskResult);
		try {
			long returnlong = template.update(qTaskResult, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTaskResult.taskResultID.eq(taskResult.getTaskResultID())).set(qTaskResult.runResultID, taskResult.getRunResultID()).set(qTaskResult.result, taskResult.isResult()).set(qTaskResult.percentagePassCount, taskResult.getPercentagePassCount()).set(qTaskResult.endDateTime, taskResult.getEndDateTime()).set(qTaskResult.percentageFailCount, taskResult.getPercentageFailCount()).set(qTaskResult.reportFilePath, taskResult.getReportFilePath()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTaskResult(TaskResult) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.output.TaskResultDao#getTaskResult()
	 */
	@Override
	public List<TaskResult> getTaskResult() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTaskResult() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTaskResult).where(qTaskResult.taskID.eq(qTask.taskID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTaskResultProjection(qTaskResult.taskResultID, qTaskResult.taskID, qTaskResult.laneID,
					qTaskResult.runResultID, qTaskResult.reportFilePath, qTaskResult.result, qTaskResult.percentagePassCount,
					qTaskResult.percentageFailCount, qTaskResult.startDateTime, qTaskResult.endDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.output.TaskResultDao#getTaskResultById(int)
	 */
	@Override
	public TaskResult getTaskResultById(int taskResultId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTaskResultById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTaskResult).where(qTaskResult.taskResultID.eq(taskResultId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTaskResultProjection(qTaskResult.taskResultID, qTaskResult.taskID, qTaskResult.laneID,
					qTaskResult.runResultID, qTaskResult.reportFilePath, qTaskResult.result, qTaskResult.percentagePassCount,
					qTaskResult.percentageFailCount, qTaskResult.startDateTime, qTaskResult.endDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.output.TaskResultDao#insertTaskResultGetKey(com.exilant
	 * .tfw.pojo.output.TaskResult)
	 */
	@Override
	public int insertTaskResultGetKey(final TaskResult taskResult) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTaskResultGetKey(TaskResult) - start"); //$NON-NLS-1$
		}

		int taskResultId = 0;
		try {
			taskResultId = template.insertWithKey(qTaskResult, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTaskResult.taskResultID, qTaskResult.taskID, qTaskResult.laneID, qTaskResult.runResultID, qTaskResult.reportFilePath, qTaskResult.testRunID, qTaskResult.result, qTaskResult.startDateTime, qTaskResult.endDateTime, qTaskResult.percentagePassCount, qTaskResult.percentageFailCount).values(taskResult.getTaskResultID(), taskResult.getTaskID(), taskResult.getLaneID(), taskResult.getRunResultID(), taskResult.getReportFilePath(), taskResult.getTestRunID(), taskResult.isResult(), taskResult.getStartDateTime(), taskResult.getEndDateTime(), taskResult.getPercentagePassCount(), taskResult.getPercentageFailCount()).executeWithKey(qTaskResult.taskResultID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated taskResultId : " + taskResultId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTaskResultGetKey(TaskResult) - end"); //$NON-NLS-1$
		}
		return taskResultId;
	}

}