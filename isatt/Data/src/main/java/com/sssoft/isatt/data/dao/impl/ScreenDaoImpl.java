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
import com.sssoft.isatt.data.dao.AppFeatureDao;
import com.sssoft.isatt.data.dao.ScreenDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QAppfeature;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.generated.pojo.QScreen;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.Screen;

/**
 * The Class ScreenDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ScreenDao interface
 */
public class ScreenDaoImpl implements ScreenDao {

	/**
	 * Sets the app feature dao.
	 * 
	 * @param appFeatureDao
	 *            the new app feature dao
	 */
	public void setAppFeatureDao(AppFeatureDao appFeatureDao) {
		this.appFeatureDao = appFeatureDao;
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

	/** The q screen. */
	private QScreen qScreen = QScreen.screen;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** The q app feature. */
	private QAppfeature qAppFeature = QAppfeature.appfeature;

	/** The app feature dao. */
	private AppFeatureDao appFeatureDao;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ScreenDaoImpl.class);

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
	 * The Class MappingScreenProjection.
	 */
	public class MappingScreenProjection extends MappingProjection<Screen> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping screen projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingScreenProjection(Expression<?>... args) {
			super(Screen.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Screen map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Screen screen = new Screen();
			screen.setScreenID(tuple.get(qScreen.screenID));
			screen.setScreenName(tuple.get(qScreen.screenName));
			screen.setDescription(tuple.get(qScreen.description));
			screen.setAppID(tuple.get(qScreen.appID));
			screen.setCreatedBy(tuple.get(qScreen.createdBy));
			screen.setCreatedDateTime(tuple.get(qScreen.createdDateTime));
			screen.setUpdatedBy(tuple.get(qScreen.updatedBy));
			screen.setUpdatedDateTime(tuple.get(qScreen.updatedDateTime));
			screen.setFeatureID(tuple.get(qScreen.featureID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Actions object : " + screen);
			}
			return screen;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param screen
	 *            the screen
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertScreen(final Screen screen) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreen(Screen) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Screen: " + screen);
		try {
			result = template.insert(qScreen, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qScreen.screenID, qScreen.screenName,
									qScreen.description, qScreen.appID,
									qScreen.createdBy, qScreen.createdDateTime,
									qScreen.updatedBy, qScreen.updatedDateTime,
									qScreen.featureID)
							.values(screen.getScreenID(),
									screen.getScreenName(),
									screen.getDescription(), screen.getAppID(),
									screen.getCreatedBy(),
									screen.getCreatedDateTime(),
									screen.getUpdatedBy(),
									screen.getUpdatedDateTime(),
									screen.getFeatureID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Screen");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreen(Screen) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param screen
	 *            the screen
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateScreen(final Screen screen) throws DataAccessException {
		LOG.info("Started updating data in Screen: " + screen);
		try {
			long returnlong = template.update(qScreen, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause
							.where(qScreen.screenID.eq(screen.getScreenID()))
							.set(qScreen.description, screen.getDescription())
							.set(qScreen.updatedBy, screen.getUpdatedBy())
							.set(qScreen.updatedDateTime,
									screen.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScreen(Screen) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.ScreenDao#getScreen()
	 */
	@Override
	public List<Screen> getScreen() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreen() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qScreen)
					.where(qScreen.appID.eq(qApplication.appID).and(
							qScreen.featureID.eq(qAppFeature.featureID)));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingScreenProjection(
					qScreen.screenID, qScreen.screenName, qScreen.description,
					qScreen.appID, qScreen.createdBy, qScreen.createdDateTime,
					qScreen.updatedBy, qScreen.updatedDateTime,
					qScreen.featureID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.ScreenDao#getScreenById(int)
	 */
	@Override
	public Screen getScreenById(int screenId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScreen)
					.where(qScreen.screenID.eq(screenId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery,
					new MappingScreenProjection(qScreen.screenID,
							qScreen.screenName, qScreen.description,
							qScreen.appID, qScreen.createdBy,
							qScreen.createdDateTime, qScreen.updatedBy,
							qScreen.updatedDateTime, qScreen.featureID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.ScreenDao#getScreenFilterByAppFeatureId(int)
	 */
	@Override
	public AppFeature getScreenFilterByAppFeatureId(int screenId)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenFilterByAppFeatureId(int) - start"); //$NON-NLS-1$
		}

		try {
			Screen screen = this.getScreenById(screenId);
			AppFeature returnAppFeature = this.appFeatureDao
					.getAppFeatureById(screen.getFeatureID());
			if (LOG.isDebugEnabled()) {
				LOG.debug("getScreenFilterByAppFeatureId(int) - end"); //$NON-NLS-1$
			}
			return returnAppFeature;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.ScreenDao#getScreenIDByNameAndFeatureID(java.lang
	 * .String, int)
	 */
	@Override
	public int getScreenIDByNameAndFeatureID(String screenName, int featureId)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenIDByNameAndFeatureID(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qScreen)
					.where(qScreen.featureID.eq(featureId).and(
							qScreen.screenName.eq(screenName)));
			LOG.info("Screen query : " + sqlQuery);
			Integer screenID = template.queryForObject(sqlQuery,
					qScreen.screenID);
			if (screenID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getScreenIDByNameAndFeatureID(String, int) - end"); //$NON-NLS-1$
				}
				return screenID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenIDByNameAndFeatureID(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.ScreenDao#getScreenIDByName(java.lang.String)
	 */
	@Override
	public int getScreenIDByName(String name) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenIDByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScreen)
					.where(qScreen.screenName.eq(name));
			LOG.info("Screen query : " + sqlQuery);
			Integer screenID = template.queryForObject(sqlQuery,
					qScreen.screenID);
			if (screenID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getScreenIDByName(String) - end"); //$NON-NLS-1$
				}
				return screenID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreenIDByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.ScreenDao#insertScreenGetKey(com.sssoft.isatt.data.pojo
	 * .Screen)
	 */
	@Override
	public int insertScreenGetKey(final Screen screen)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreenGetKey(Screen) - start"); //$NON-NLS-1$
		}

		int screenId = 0;
		try {
			screenId = template.insertWithKey(qScreen,
					new SqlInsertWithKeyCallback<Integer>() {
						@Override
						public Integer doInSqlInsertWithKeyClause(
								SQLInsertClause insert) throws SQLException {
							if (LOG.isDebugEnabled()) {
								LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
							}

							Integer returnInteger = insert
									.columns(qScreen.screenID,
											qScreen.screenName,
											qScreen.description, qScreen.appID,
											qScreen.createdBy,
											qScreen.createdDateTime,
											qScreen.updatedBy,
											qScreen.updatedDateTime,
											qScreen.featureID)
									.values(screen.getScreenID(),
											screen.getScreenName(),
											screen.getDescription(),
											screen.getAppID(),
											screen.getCreatedBy(),
											screen.getCreatedDateTime(),
											screen.getUpdatedBy(),
											screen.getUpdatedDateTime(),
											screen.getFeatureID())
									.executeWithKey(qScreen.screenID);
							if (LOG.isDebugEnabled()) {
								LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
							}
							return returnInteger;
						}
					});
			LOG.info("Genrated screenId : " + screenId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertScreenGetKey(Screen) - end"); //$NON-NLS-1$
		}
		return screenId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.ScreenDao#getAllScreens()
	 */
	@Override
	public List<Screen> getAllScreens() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllScreens() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScreen);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingScreenProjection(
					qScreen.screenID, qScreen.screenName, qScreen.description,
					qScreen.appID, qScreen.createdBy, qScreen.createdDateTime,
					qScreen.updatedBy, qScreen.updatedDateTime,
					qScreen.featureID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.ScreenDao#updateScreenData(com.sssoft.isatt.data.pojo.Screen
	 * )
	 */
	@Override
	public long updateScreenData(final Screen screen)
			throws DataAccessException {
		LOG.info("Started updating data in Screen: " + screen);
		try {
			long returnlong = template.update(qScreen, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause
							.where(qScreen.screenID.eq(screen.getScreenID()))
							.set(qScreen.screenName, screen.getScreenName())
							.set(qScreen.description, screen.getDescription())
							.set(qScreen.updatedBy, screen.getUpdatedBy())
							.set(qScreen.updatedDateTime,
									screen.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateScreenData(Screen) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.ScreenDao#getScreensByAppId(int)
	 */
	@Override
	public List<Screen> getScreensByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreensByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScreen)
					.where(qScreen.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingScreenProjection(
					qScreen.screenID, qScreen.screenName, qScreen.description,
					qScreen.appID, qScreen.createdBy, qScreen.createdDateTime,
					qScreen.updatedBy, qScreen.updatedDateTime,
					qScreen.featureID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<Screen> getScreensByAppIdAndFeatureID(int appId, int featureId)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreensByAppIdAndFeatureID(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qScreen)
					.where(qScreen.appID.eq(appId).and(
							qScreen.featureID.eq(featureId)));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingScreenProjection(
					qScreen.screenID, qScreen.screenName, qScreen.description,
					qScreen.appID, qScreen.createdBy, qScreen.createdDateTime,
					qScreen.updatedBy, qScreen.updatedDateTime,
					qScreen.featureID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public List<Screen> getScreensByFeatureId(int featureID)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getScreensByFeatureId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qScreen)
					.where(qScreen.featureID.eq(featureID));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingScreenProjection(
					qScreen.screenID, qScreen.screenName, qScreen.description,
					qScreen.appID, qScreen.createdBy, qScreen.createdDateTime,
					qScreen.updatedBy, qScreen.updatedDateTime,
					qScreen.featureID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
}