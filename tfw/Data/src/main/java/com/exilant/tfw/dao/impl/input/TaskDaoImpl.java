package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.TaskDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QSchedulerlanedetails;
import com.exilant.tfw.generated.pojo.QTask;
import com.exilant.tfw.pojo.input.Task;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TaskDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TaskDao interface
 */
public class TaskDaoImpl implements TaskDao {

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

	/** The q task. */
	private QTask qTask = QTask.task;

	/** The q scheduler lane details. */
	private QSchedulerlanedetails qSchedulerLaneDetails = QSchedulerlanedetails.schedulerlanedetails;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TaskDaoImpl.class);

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
	 * The Class MappingTaskProjection.
	 */
	public class MappingTaskProjection extends MappingProjection<Task> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping task projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTaskProjection(Expression<?>... args) {
			super(Task.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Task map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Task task = new Task();
			task.setTaskID(tuple.get(qTask.taskID));
			task.setLaneID(tuple.get(qTask.laneID));
			task.setTaskName(tuple.get(qTask.taskName));
			task.setTestPlanXlsPath(tuple.get(qTask.testPlanXlsPath));
			task.setDataSet(tuple.get(qTask.dataSet));
			task.setRepeatNo(tuple.get(qTask.repeatNo));
			task.setTagsToRun(tuple.get(qTask.tagsToRun));
			task.setCreatedBy(tuple.get(qTask.createdBy));
			task.setCreatedDateTime(tuple.get(qTask.createdDateTime));
			task.setUpdatedBy(tuple.get(qTask.updatedBy));
			task.setUpdatedDateTime(tuple.get(qTask.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Task object : " + task);
			}
			return task;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param task
	 *            the task
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTask(final Task task) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTask(Task) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Task: " + task);
		try {
			result = template.insert(qTask, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qTask.taskID, qTask.laneID, qTask.taskName, qTask.testPlanXlsPath, qTask.dataSet, qTask.repeatNo, qTask.tagsToRun,
									qTask.createdBy, qTask.createdDateTime, qTask.updatedBy, qTask.updatedDateTime)
							.values(task.getTaskID(), task.getLaneID(), task.getTaskName(), task.getTestPlanXlsPath(), task.getDataSet(),
									task.getRepeatNo(), task.getTagsToRun(), task.getCreatedBy(), task.getCreatedDateTime(), task.getUpdatedBy(),
									task.getUpdatedDateTime()).execute();
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
			LOG.debug("insertTask(Task) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param task
	 *            the task
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTask(final Task task) throws DataAccessException {
		LOG.info("Started updating data in Task: " + task);
		try {
			long returnlong = template.update(qTask, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTask.taskID.eq(task.getTaskID())).set(qTask.testPlanXlsPath, task.getTestPlanXlsPath())
							.set(qTask.dataSet, task.getDataSet()).set(qTask.repeatNo, task.getRepeatNo()).set(qTask.tagsToRun, task.getTagsToRun())
							.set(qTask.createdBy, task.getCreatedBy()).set(qTask.createdDateTime, task.getCreatedDateTime())
							.set(qTask.updatedBy, task.getUpdatedBy()).set(qTask.updatedDateTime, task.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTask(Task) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.input.TaskDao#getTask()
	 */
	@Override
	public List<Task> getTask() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTask() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTask).where(qTask.laneID.eq(qSchedulerLaneDetails.scheduleLaneID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTaskProjection(qTask.taskID, qTask.laneID, qTask.taskName, qTask.testPlanXlsPath, qTask.dataSet,
					qTask.repeatNo, qTask.tagsToRun, qTask.createdBy, qTask.createdDateTime, qTask.updatedBy, qTask.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TaskDao#getTaskById(int)
	 */
	@Override
	public Task getTaskById(int taskId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTaskById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTask).where(qTask.taskID.eq(taskId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTaskProjection(qTask.taskID, qTask.laneID, qTask.taskName, qTask.testPlanXlsPath,
					qTask.dataSet, qTask.repeatNo, qTask.tagsToRun, qTask.createdBy, qTask.createdDateTime, qTask.updatedBy, qTask.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TaskDao#insertTaskGetKey(com.exilant.tfw.pojo
	 * .input.Task)
	 */
	@Override
	public int insertTaskGetKey(final Task task) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTaskGetKey(Task) - start"); //$NON-NLS-1$
		}

		int taskId = 0;
		try {
			taskId = template.insertWithKey(qTask, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qTask.taskID, qTask.laneID, qTask.taskName, qTask.testPlanXlsPath, qTask.dataSet, qTask.repeatNo, qTask.tagsToRun,
									qTask.createdBy, qTask.createdDateTime, qTask.updatedBy, qTask.updatedDateTime)
							.values(task.getTaskID(), task.getLaneID(), task.getTaskName(), task.getTestPlanXlsPath(), task.getDataSet(),
									task.getRepeatNo(), task.getTagsToRun(), task.getCreatedBy(), task.getCreatedDateTime(), task.getUpdatedBy(),
									task.getUpdatedDateTime()).executeWithKey(qTask.taskID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated taskId : " + taskId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTaskGetKey(Task) - end"); //$NON-NLS-1$
		}
		return taskId;
	}

}