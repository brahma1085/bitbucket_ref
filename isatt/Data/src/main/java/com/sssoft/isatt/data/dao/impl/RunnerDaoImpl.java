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
import com.sssoft.isatt.data.dao.RunnerDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QRunner;
import com.sssoft.isatt.data.pojo.Runner;

/**
 * The Class RunnerDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for RunnerDao interface
 */
public class RunnerDaoImpl implements RunnerDao {

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

	/** The q runner. */
	private QRunner qRunner = QRunner.runner;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(RunnerDaoImpl.class);

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
	 * The Class MappingRunnerProjection.
	 */
	public class MappingRunnerProjection extends MappingProjection<Runner> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping runner projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingRunnerProjection(Expression<?>... args) {
			super(Runner.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Runner map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Runner runner = new Runner();
			runner.setRunnerID(tuple.get(qRunner.runnerID));
			runner.setRunnerName(tuple.get(qRunner.runnerName));
			runner.setDescription(tuple.get(qRunner.description));
			runner.setCreatedBy(tuple.get(qRunner.createdBy));
			runner.setCreatedDateTime(tuple.get(qRunner.createdDateTime));
			runner.setUpdatedBy(tuple.get(qRunner.updatedBy));
			runner.setUpdatedDateTime(tuple.get(qRunner.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Runner object : " + runner);
			}
			return runner;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param runner
	 *            the runner
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertRunner(final Runner runner) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRunner(Runner) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Runner: " + runner);
		try {
			result = template.insert(qRunner, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qRunner.runnerID, qRunner.runnerName, qRunner.description, qRunner.createdBy, qRunner.createdDateTime, qRunner.updatedBy, qRunner.updatedDateTime).values(runner.getRunnerID(), runner.getRunnerName(), runner.getDescription(), runner.getCreatedBy(), runner.getCreatedDateTime(), runner.getUpdatedBy(), runner.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Runner");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRunner(Runner) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param runner
	 *            the runner
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateRunner(final Runner runner) throws DataAccessException {
		LOG.info("Started updating data in Runner: " + runner);
		try {
			long returnlong = template.update(qRunner, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qRunner.runnerID.eq(runner.getRunnerID())).set(qRunner.description, runner.getDescription()).set(qRunner.updatedBy, runner.getUpdatedBy()).set(qRunner.updatedDateTime, runner.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateRunner(Runner) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.RunnerDao#getRunner()
	 */
	@Override
	public List<Runner> getRunner() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunner() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qRunner);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingRunnerProjection(qRunner.runnerID, qRunner.runnerName, qRunner.description, qRunner.createdBy,
					qRunner.createdDateTime, qRunner.updatedBy, qRunner.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.RunnerDao#getRunnerById(int)
	 */
	@Override
	public Runner getRunnerById(int runnerId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunnerById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qRunner).where(qRunner.runnerID.eq(runnerId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingRunnerProjection(qRunner.runnerID, qRunner.runnerName, qRunner.description,
					qRunner.createdBy, qRunner.createdDateTime, qRunner.updatedBy, qRunner.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.RunnerDao#insertRunnerGetKey(com.sssoft.isatt.data.pojo
	 * .Runner)
	 */
	@Override
	public int insertRunnerGetKey(final Runner runner) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRunnerGetKey(Runner) - start"); //$NON-NLS-1$
		}

		int runnerId = 0;
		try {
			runnerId = template.insertWithKey(qRunner, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qRunner.runnerID, qRunner.runnerName, qRunner.description, qRunner.createdBy, qRunner.createdDateTime, qRunner.updatedBy, qRunner.updatedDateTime).values(runner.getRunnerID(), runner.getRunnerName(), runner.getDescription(), runner.getCreatedBy(), runner.getCreatedDateTime(), runner.getUpdatedBy(), runner.getUpdatedDateTime()).executeWithKey(qRunner.runnerID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated runnerId : " + runnerId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertRunnerGetKey(Runner) - end"); //$NON-NLS-1$
		}
		return runnerId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.RunnerDao#getRunnerIDFromRunnerName(java.lang.String)
	 */
	@Override
	public int getRunnerIDFromRunnerName(String runnerName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunnerIDFromRunnerName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qRunner).where(qRunner.runnerName.eq(runnerName));
			Integer runnerId = template.queryForObject(sqlQuery, qRunner.runnerID);
			if (runnerId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getRunnerIDFromRunnerName(String) - end"); //$NON-NLS-1$
				}
				return runnerId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getRunnerIDFromRunnerName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}
}