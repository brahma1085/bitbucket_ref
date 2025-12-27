package com.exilant.tfw.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.AppFeatureDao;
import com.exilant.tfw.dao.AppFunctionalityDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QAppfeature;
import com.exilant.tfw.pojo.AppFeature;
import com.exilant.tfw.pojo.AppFunctionality;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class AppFeatureDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for AppFeatureDao interface
 */
public class AppFeatureDaoImpl implements AppFeatureDao {

	/**
	 * Sets the app functionality dao.
	 * 
	 * @param appFunctionalityDao
	 *            the new app functionality dao
	 */
	public void setAppFunctionalityDao(AppFunctionalityDao appFunctionalityDao) {
		this.appFunctionalityDao = appFunctionalityDao;
	}

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

	/** The q app feature. */
	private QAppfeature qAppFeature = QAppfeature.appfeature;

	/** The app functionality dao. */
	@Autowired(required = true)
	private AppFunctionalityDao appFunctionalityDao;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AppFeatureDaoImpl.class);

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
	 * The Class MappingAppFeatureProjection.
	 */
	public class MappingAppFeatureProjection extends MappingProjection<AppFeature> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping app feature projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingAppFeatureProjection(Expression<?>... args) {
			super(AppFeature.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected AppFeature map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			AppFeature appFeature = new AppFeature();
			appFeature.setFeatureID(tuple.get(qAppFeature.featureID));
			appFeature.setFeatureName(tuple.get(qAppFeature.featureName));
			appFeature.setDescription(tuple.get(qAppFeature.description));
			appFeature.setFunctionalID(tuple.get(qAppFeature.functionalID));
			appFeature.setCreatedBy(tuple.get(qAppFeature.createdBy));
			appFeature.setCreatedDateTime(tuple.get(qAppFeature.createdDateTime));
			appFeature.setUpdatedBy(tuple.get(qAppFeature.updatedBy));
			appFeature.setUpdatedDateTime(tuple.get(qAppFeature.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning AppFeature object : " + appFeature);
			}
			return appFeature;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertAppFeature(final AppFeature appFeature) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFeature(AppFeature) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in AppFeature: " + appFeature);
		try {
			result = template.insert(qAppFeature, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qAppFeature.featureID, qAppFeature.featureName, qAppFeature.description, qAppFeature.functionalID, qAppFeature.createdBy, qAppFeature.createdDateTime, qAppFeature.updatedBy, qAppFeature.updatedDateTime).values(appFeature.getFeatureID(), appFeature.getFeatureName(), appFeature.getDescription(), appFeature.getFunctionalID(), appFeature.getCreatedBy(), appFeature.getCreatedDateTime(), appFeature.getUpdatedBy(), appFeature.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in AppFeature");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertAppFeature(AppFeature) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param appFeature
	 *            the app feature
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateAppFeature(final AppFeature appFeature) throws DataAccessException {
		LOG.info("Started updating data in AppFeature: " + appFeature);
		try {
			long returnlong = template.update(qAppFeature, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qAppFeature.featureID.eq(appFeature.getFeatureID())).set(qAppFeature.description, appFeature.getDescription()).set(qAppFeature.updatedBy, appFeature.getUpdatedBy()).set(qAppFeature.updatedDateTime, appFeature.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFeature(AppFeature) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.AppFeatureDao#getAppFeature()
	 */
	@Override
	public List<AppFeature> getAppFeature() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeature() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAppFeatureProjection(qAppFeature.featureID, qAppFeature.featureName, qAppFeature.description,
					qAppFeature.functionalID, qAppFeature.createdBy, qAppFeature.createdDateTime, qAppFeature.updatedBy, qAppFeature.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.AppFeatureDao#getAppFeatureById(int)
	 */
	@Override
	public AppFeature getAppFeatureById(int appFeatureId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature).where(qAppFeature.featureID.eq(appFeatureId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingAppFeatureProjection(qAppFeature.featureID, qAppFeature.featureName,
					qAppFeature.description, qAppFeature.functionalID, qAppFeature.createdBy, qAppFeature.createdDateTime, qAppFeature.updatedBy,
					qAppFeature.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.AppFeatureDao#getAppFeatureByFunctionalId(int)
	 */
	@Override
	public List<AppFeature> getAppFeatureByFunctionalId(int functionalId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureByFunctionalId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature).where(qAppFeature.functionalID.eq(functionalId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingAppFeatureProjection(qAppFeature.featureID, qAppFeature.featureName, qAppFeature.description,
					qAppFeature.functionalID, qAppFeature.createdBy, qAppFeature.createdDateTime, qAppFeature.updatedBy, qAppFeature.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.AppFeatureDao#getAppFeatureIdByName(java.lang.String,
	 * int)
	 */
	@Override
	public int getAppFeatureIdByName(String featureName, int functionalId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureIdByName(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature)
					.where((qAppFeature.functionalID.eq(functionalId)).and(qAppFeature.featureName.eq(featureName)));
			Integer featureId = template.queryForObject(sqlQuery, qAppFeature.featureID);
			if (featureId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppFeatureIdByName(String, int) - end"); //$NON-NLS-1$
				}
				return featureId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureIdByName(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.AppFeatureDao#getAppFeatureFilterByAppFunctionalityID
	 * (int)
	 */
	@Override
	public List<AppFunctionality> getAppFeatureFilterByAppFunctionalityID(int appFeatureID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureFilterByAppFunctionalityID(int) - start"); //$NON-NLS-1$
		}

		try {
			AppFeature appFeature = this.getAppFeatureById(appFeatureID);
			List<AppFunctionality> returnList = this.appFunctionalityDao.getAppFunctionalityFilterByAppId(appFeature.getAppID());
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeatureFilterByAppFunctionalityID(int) - end"); //$NON-NLS-1$
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
	 * com.exilant.tfw.dao.AppFeatureDao#getAppFeatureIDByName(java.lang.String)
	 */
	@Override
	public int getAppFeatureIDByName(String screenName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureIDByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature).where((qAppFeature.featureName.eq(screenName)));
			Integer featureId = template.queryForObject(sqlQuery, qAppFeature.featureID);
			if (featureId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppFeatureIDByName(String) - end"); //$NON-NLS-1$
				}
				return featureId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureIDByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.AppFeatureDao#getAppFeatureIDByNameAndFunctionalID
	 * (com.exilant.tfw.pojo.AppFeature)
	 */
	public int getAppFeatureIDByNameAndFunctionalID(AppFeature appFeature) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureIDByNameAndFunctionalID(AppFeature) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature)
					.where((qAppFeature.functionalID.eq(appFeature.getFunctionalID())).and(qAppFeature.featureName.eq(appFeature.getFeatureName())));
			Integer featureId = template.queryForObject(sqlQuery, qAppFeature.featureID);
			if (featureId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppFeatureIDByNameAndFunctionalID(AppFeature) - end"); //$NON-NLS-1$
				}
				return featureId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureIDByNameAndFunctionalID(AppFeature) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.AppFeatureDao#insertFeatureGetKey(com.exilant.tfw
	 * .pojo.AppFeature)
	 */
	@Override
	public int insertFeatureGetKey(final AppFeature appFeature) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertFeatureGetKey(AppFeature) - start"); //$NON-NLS-1$
		}

		int appFeatureId = 0;
		try {
			appFeatureId = template.insertWithKey(qAppFeature, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qAppFeature.featureID, qAppFeature.featureName, qAppFeature.description, qAppFeature.functionalID, qAppFeature.createdBy, qAppFeature.createdDateTime, qAppFeature.updatedBy, qAppFeature.updatedDateTime).values(appFeature.getFeatureID(), appFeature.getFeatureName(), appFeature.getDescription(), appFeature.getFunctionalID(), appFeature.getCreatedBy(), appFeature.getCreatedDateTime(), appFeature.getUpdatedBy(), appFeature.getUpdatedDateTime()).executeWithKey(qAppFeature.featureID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated appFeatureId : " + appFeatureId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertFeatureGetKey(AppFeature) - end"); //$NON-NLS-1$
		}
		return appFeatureId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.AppFeatureDao#getAppFunctionalIDByFeatureID(int)
	 */
	@Override
	public int getAppFunctionalIDByFeatureID(int featureID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalIDByFeatureID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature).where((qAppFeature.featureID.eq(featureID)));
			Integer functionalID = template.queryForObject(sqlQuery, qAppFeature.functionalID);
			if (functionalID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppFunctionalIDByFeatureID(int) - end"); //$NON-NLS-1$
				}
				return functionalID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFunctionalIDByFeatureID(int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.AppFeatureDao#getAllAppFeaturesByFunctionalId(int)
	 */
	@Override
	public List<AppFeature> getAllAppFeaturesByFunctionalId(int appFunctionalityID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllAppFeaturesByFunctionalId(int) - start"); //$NON-NLS-1$
		}

		LOG.info("Feature DAO Implementation");
		try {
			List<AppFeature> returnList = this.getAppFeatureByFunctionalId(appFunctionalityID);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllAppFeaturesByFunctionalId(int) - end"); //$NON-NLS-1$
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
	 * com.exilant.tfw.dao.AppFeatureDao#getAppFeatureNamesByAppFeatureId(int)
	 */
	@Override
	public List<AppFeature> getAppFeatureNamesByAppFeatureId(int appFeatureId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppFeatureNamesByAppFeatureId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qAppFeature).where(qAppFeature.featureID.eq(appFeatureId));
			;
			List<AppFeature> returnList = template.query(sqlQuery, new MappingAppFeatureProjection(qAppFeature.featureName));
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAppFeatureNamesByAppFeatureId(int) - end"); //$NON-NLS-1$
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
	 * com.exilant.tfw.dao.AppFeatureDao#getAllFeatureNamesByTestCaseId(int)
	 */
	@Override
	public List<AppFeature> getAllFeatureNamesByTestCaseId(int appFeatureId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllFeatureNamesByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<AppFeature> returnList = this.getAppFeatureNamesByAppFeatureId(appFeatureId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllFeatureNamesByTestCaseId(int) - end"); //$NON-NLS-1$
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
	 * com.exilant.tfw.dao.AppFeatureDao#updateAppFeatureData(com.exilant.tfw
	 * .pojo.AppFeature)
	 */
	@Override
	public long updateAppFeatureData(final AppFeature appFeature) throws DataAccessException {
		LOG.info("Started updating data in AppFeature: " + appFeature);
		try {
			long returnlong = template.update(qAppFeature, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qAppFeature.featureID.eq(appFeature.getFeatureID())).set(qAppFeature.featureName, appFeature.getFeatureName()).set(qAppFeature.functionalID, appFeature.getFunctionalID()).set(qAppFeature.description, appFeature.getDescription()).set(qAppFeature.updatedBy, appFeature.getUpdatedBy()).set(qAppFeature.updatedDateTime, appFeature.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateAppFeatureData(AppFeature) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

}