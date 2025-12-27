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
import com.sssoft.isatt.data.dao.AppFunctionalityDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QAppfunctionality;
import com.sssoft.isatt.data.pojo.AppFunctionality;

/**
 * The Class AppFunctionalityDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for AppFunctionalityDao
 * interface
 */
public class AppFunctionalityDaoImpl implements AppFunctionalityDao {

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

	/** The qapp functionality. */
	private QAppfunctionality qappFunctionality = QAppfunctionality.appfunctionality;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AppFunctionalityDaoImpl.class);

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
	 * The Class MappingAppFunctionalityProjection.
	 */
	public class MappingAppFunctionalityProjection extends MappingProjection<AppFunctionality> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping app functionality projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingAppFunctionalityProjection(Expression<?>... args) {
			super(AppFunctionality.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected AppFunctionality map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			AppFunctionality appFunctionality = new AppFunctionality();
			appFunctionality.setFunctionalID(tuple.get(qappFunctionality.functionalID));
			appFunctionality.setFunctionalName(tuple.get(qappFunctionality.functionalName));
			appFunctionality.setDescription(tuple.get(qappFunctionality.description));
			appFunctionality.setAppID(tuple.get(qappFunctionality.appID));
			appFunctionality.setCreatedBy(tuple.get(qappFunctionality.createdBy));
			appFunctionality.setCreatedDateTime(tuple.get(qappFunctionality.createdDateTime));
			appFunctionality.setUpdatedBy(tuple.get(qappFunctionality.updatedBy));
			appFunctionality.setUpdatedDateTime(tuple.get(qappFunctionality.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Actions object : " + appFunctionality);
			}
			return appFunctionality;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertAppFunctionality(final AppFunctionality appFunctionality) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionality(AppFunctionality) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in AppFunctionality: " + appFunctionality);
		try {
			result = template.insert(qappFunctionality, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qappFunctionality.functionalID, qappFunctionality.functionalName, qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy, qappFunctionality.createdDateTime, qappFunctionality.updatedBy, qappFunctionality.updatedDateTime).values(appFunctionality.getFunctionalID(), appFunctionality.getFunctionalName(), appFunctionality.getDescription(), appFunctionality.getAppID(), appFunctionality.getCreatedBy(), appFunctionality.getCreatedDateTime(), appFunctionality.getUpdatedBy(), appFunctionality.getUpdatedDateTime()).execute();
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
			LOG.debug("insertAppFunctionality(AppFunctionality) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param appFunctionality
	 *            the app functionality
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateAppFunctionality(final AppFunctionality appFunctionality) throws DataAccessException {
		LOG.info("Started updating data in AppFunctionality: " + appFunctionality);
		try {
			long returnlong = template.update(qappFunctionality, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qappFunctionality.functionalID.eq(appFunctionality.getFunctionalID())).set(qappFunctionality.description, appFunctionality.getDescription()).set(qappFunctionality.appID, appFunctionality.getAppID()).set(qappFunctionality.updatedBy, appFunctionality.getUpdatedBy()).set(qappFunctionality.updatedDateTime, appFunctionality.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFunctionality(AppFunctionality) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionality()
	 */
	@Override
	public List<AppFunctionality> getAppFunctionality() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionality() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality);
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAppFunctionalityProjection(qappFunctionality.functionalID, qappFunctionality.functionalName,
					qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy, qappFunctionality.createdDateTime,
					qappFunctionality.updatedBy, qappFunctionality.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionalityIdByNameAndAppID
	 * (com.sssoft.isatt.data.pojo.AppFunctionality)
	 */
	public int getAppFunctionalityIdByNameAndAppID(AppFunctionality appFunctionality) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityIdByNameAndAppID(AppFunctionality) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qappFunctionality)
					.where((qappFunctionality.appID.eq(appFunctionality.getAppID())).and(qappFunctionality.functionalName.eq(appFunctionality
							.getFunctionalName())));
			Integer functionalId = template.queryForObject(sqlQuery, qappFunctionality.functionalID);
			if (functionalId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppFunctionalityIdByNameAndAppID(AppFunctionality) - end"); //$NON-NLS-1$
				}
				return functionalId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityIdByNameAndAppID(AppFunctionality) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionalityById(int)
	 */
	@Override
	public AppFunctionality getAppFunctionalityById(int functionalId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality).where(qappFunctionality.functionalID.eq(functionalId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingAppFunctionalityProjection(qappFunctionality.functionalID,
					qappFunctionality.functionalName, qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy,
					qappFunctionality.createdDateTime, qappFunctionality.updatedBy, qappFunctionality.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionalityByAppId(int)
	 */
	@Override
	public List<AppFunctionality> getAppFunctionalityByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality).where(qappFunctionality.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAppFunctionalityProjection(qappFunctionality.functionalID, qappFunctionality.functionalName,
					qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy, qappFunctionality.createdDateTime,
					qappFunctionality.updatedBy, qappFunctionality.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionalityFilterByAppId
	 * (int)
	 */
	@Override
	public List<AppFunctionality> getAppFunctionalityFilterByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality).where(qappFunctionality.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAppFunctionalityProjection(qappFunctionality.functionalID, qappFunctionality.functionalName,
					qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy, qappFunctionality.createdDateTime,
					qappFunctionality.updatedBy, qappFunctionality.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#insertAppFunctionalityGetKey(
	 * com.sssoft.isatt.data.pojo.AppFunctionality)
	 */
	@Override
	public int insertAppFunctionalityGetKey(final AppFunctionality appFunctionality) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionalityGetKey(AppFunctionality) - start"); //$NON-NLS-1$
		}

		int appFunctionalityId = 0;
		try {
			appFunctionalityId = template.insertWithKey(qappFunctionality, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qappFunctionality.functionalID, qappFunctionality.functionalName, qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy, qappFunctionality.createdDateTime, qappFunctionality.updatedBy, qappFunctionality.updatedDateTime).values(appFunctionality.getFunctionalID(), appFunctionality.getFunctionalName(), appFunctionality.getDescription(), appFunctionality.getAppID(), appFunctionality.getCreatedBy(), appFunctionality.getCreatedDateTime(), appFunctionality.getUpdatedBy(), appFunctionality.getUpdatedDateTime()).executeWithKey(qappFunctionality.functionalID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated appFunctionalityId : " + appFunctionalityId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFunctionalityGetKey(AppFunctionality) - end"); //$NON-NLS-1$
		}
		return appFunctionalityId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionalityIdByName(int,
	 * java.lang.String)
	 */
	@Override
	public int getAppFunctionalityIdByName(int appId, String functionalName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityIdByName(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality)
					.where((qappFunctionality.appID.eq(appId).and(qappFunctionality.functionalName.eq(functionalName))));
			Integer functionalId = template.queryForObject(sqlQuery, qappFunctionality.functionalID);
			if (functionalId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppFunctionalityIdByName(int, String) - end"); //$NON-NLS-1$
				}
				return functionalId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityIdByName(int, String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionNamesByAppFunctionId
	 * (int)
	 */
	@Override
	public List<AppFunctionality> getAppFunctionNamesByAppFunctionId(int appFunctionId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionNamesByAppFunctionId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality).where(qappFunctionality.functionalID.eq(appFunctionId));
			;
			List<AppFunctionality> returnList = template.query(sqlQuery, new MappingAppFunctionalityProjection(qappFunctionality.functionalName));
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFunctionNamesByAppFunctionId(int) - end"); //$NON-NLS-1$
			}
			return returnList;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAllFunctionNamesByTestCaseId
	 * (int)
	 */
	@Override
	public List<AppFunctionality> getAllFunctionNamesByTestCaseId(int appFunctionId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFunctionNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFunctionality> returnList = this.getAppFunctionNamesByAppFunctionId(appFunctionId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllFunctionNamesByTestCaseId(int) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#updateAppFunctionalityData(com
	 * .exilant.tfw.pojo.AppFunctionality)
	 */
	@Override
	public long updateAppFunctionalityData(final AppFunctionality appFunctionality) throws DataAccessException {
		LOG.info("Started updating data in AppFunctionality: " + appFunctionality);
		try {
			long returnlong = template.update(qappFunctionality, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qappFunctionality.functionalID.eq(appFunctionality.getFunctionalID())).set(qappFunctionality.functionalName, appFunctionality.getFunctionalName()).set(qappFunctionality.description, appFunctionality.getDescription()).set(qappFunctionality.updatedBy, appFunctionality.getUpdatedBy()).set(qappFunctionality.updatedDateTime, appFunctionality.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFunctionalityData(AppFunctionality) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.AppFunctionalityDao#getAppFunctionalityObjByAppId
	 * (int)
	 */
	@Override
	public List<AppFunctionality> getAppFunctionalityObjByAppId(int appID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalityObjByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qappFunctionality).where(qappFunctionality.appID.eq(appID));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAppFunctionalityProjection(qappFunctionality.functionalID, qappFunctionality.functionalName,
					qappFunctionality.description, qappFunctionality.appID, qappFunctionality.createdBy, qappFunctionality.createdDateTime,
					qappFunctionality.updatedBy, qappFunctionality.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
}