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
import com.sssoft.isatt.data.dao.ApplicationDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.pojo.Application;

/**
 * The Class ApplicationDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ApplicationDao
 * interface
 */
public class ApplicationDaoImpl implements ApplicationDao {

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

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ApplicationDaoImpl.class);

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
	 * The Class MappingApplicationProjection.
	 */
	public class MappingApplicationProjection extends MappingProjection<Application> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping application projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingApplicationProjection(Expression<?>... args) {
			super(Application.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Application map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Application application = new Application();
			application.setAppID(tuple.get(qApplication.appID));
			application.setAppName(tuple.get(qApplication.appName));
			application.setDescription(tuple.get(qApplication.description));
			application.setCreatedBy(tuple.get(qApplication.createdBy));
			application.setCreatedDateTime(tuple.get(qApplication.createdDateTime));
			application.setUpdatedBy(tuple.get(qApplication.updatedBy));
			application.setUpdatedDateTime(tuple.get(qApplication.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Application object : " + application);
			}
			return application;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param application
	 *            the application
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertApplication(final Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplication(Application) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Application: " + application);
		try {
			result = template.insert(qApplication, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qApplication.appID, qApplication.appName, qApplication.description, qApplication.createdBy, qApplication.createdDateTime, qApplication.updatedBy, qApplication.updatedDateTime).values(application.getAppID(), application.getAppName(), application.getDescription(), application.getCreatedBy(), application.getCreatedDateTime(), application.getUpdatedBy(), application.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Application");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplication(Application) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param application
	 *            the application
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateApplication(final Application application) throws DataAccessException {
		LOG.info("Started updating data in Application: " + application);
		try {
			long returnlong = template.update(qApplication, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qApplication.appID.eq(application.getAppID())).set(qApplication.appName, application.getAppName()).set(qApplication.description, application.getDescription()).set(qApplication.updatedBy, application.getUpdatedBy()).set(qApplication.updatedDateTime, application.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateApplication(Application) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.ApplicationDao#getApplication()
	 */
	@Override
	public List<Application> getApplication() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplication() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qApplication);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingApplicationProjection(qApplication.appID, qApplication.appName, qApplication.description,
					qApplication.createdBy, qApplication.createdDateTime, qApplication.updatedBy, qApplication.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.ApplicationDao#getApplicationById(int)
	 */
	@Override
	public Application getApplicationById(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApplicationById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qApplication).where(qApplication.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingApplicationProjection(qApplication.appID, qApplication.appName, qApplication.description,
					qApplication.createdBy, qApplication.createdDateTime, qApplication.updatedBy, qApplication.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.ApplicationDao#getAppIdByAppName(java.lang.String)
	 */
	@Override
	public int getAppIdByAppName(String appName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppIdByAppName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qApplication).where(qApplication.appName.eq(appName));
			Integer appId = template.queryForObject(sqlQuery, qApplication.appID);
			if (appId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppIdByAppName(String) - end"); //$NON-NLS-1$
				}
				return appId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppIdByAppName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.ApplicationDao#insertApplicationGetKey(com.exilant
	 * .tfw.pojo.Application)
	 */
	@Override
	public int insertApplicationGetKey(final Application application) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplicationGetKey(Application) - start"); //$NON-NLS-1$
		}

		int applicationId = 0;
		try {
			applicationId = template.insertWithKey(qApplication, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qApplication.appID, qApplication.appName, qApplication.description, qApplication.createdBy, qApplication.createdDateTime, qApplication.updatedBy, qApplication.updatedDateTime).values(application.getAppID(), application.getAppName(), application.getDescription(), application.getCreatedBy(), application.getCreatedDateTime(), application.getUpdatedBy(), application.getUpdatedDateTime()).executeWithKey(qApplication.appID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated applicationId : " + applicationId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertApplicationGetKey(Application) - end"); //$NON-NLS-1$
		}
		return applicationId;
	}
	
	@Override
	public List<Application> getAllApps() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllApps() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qApplication);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingApplicationProjection(qApplication.appID, qApplication.appName, qApplication.description,
					qApplication.createdBy, qApplication.createdDateTime, qApplication.updatedBy, qApplication.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}