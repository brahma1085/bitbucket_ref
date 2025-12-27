package com.sssoft.isatt.data.dao.impl.input;

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
import com.sssoft.isatt.data.dao.ScreenDao;
import com.sssoft.isatt.data.dao.input.ObjectGroupDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.generated.pojo.QObjectgroup;
import com.sssoft.isatt.data.generated.pojo.QScreen;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;

/**
 * The Class ObjectGroupDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ObjectGroupDao
 * interface
 */
public class ObjectGroupDaoImpl implements ObjectGroupDao {

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

	/** The q object group. */
	private QObjectgroup qObjectGroup = QObjectgroup.objectgroup;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** The q screen. */
	private QScreen qScreen = QScreen.screen;

	/** The screen dao. */
	@SuppressWarnings("unused")
	private ScreenDao screenDao;

	/**
	 * Sets the screen dao.
	 * 
	 * @param screenDao
	 *            the new screen dao
	 */
	public void setScreenDao(ScreenDao screenDao) {
		this.screenDao = screenDao;
	}

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ObjectGroupDaoImpl.class);

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
	 * The Class MappingObjectGroupProjection.
	 */
	public class MappingObjectGroupProjection extends MappingProjection<ObjectGroup> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping object group projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingObjectGroupProjection(Expression<?>... args) {
			super(ObjectGroup.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ObjectGroup map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			ObjectGroup objectGroup = new ObjectGroup();
			objectGroup.setObjectGroupID(tuple.get(qObjectGroup.objectGroupID));
			objectGroup.setObjectGroupName(tuple.get(qObjectGroup.objectGroupName));
			objectGroup.setDescription(tuple.get(qObjectGroup.description));
			objectGroup.setAppID(tuple.get(qObjectGroup.appID));
			objectGroup.setScreenID(tuple.get(qObjectGroup.screenID));
			objectGroup.setCreatedBy(tuple.get(qObjectGroup.createdBy));
			objectGroup.setCreatedDateTime(tuple.get(qObjectGroup.createdDateTime));
			objectGroup.setUpdatedBy(tuple.get(qObjectGroup.updatedBy));
			objectGroup.setUpdatedDateTime(tuple.get(qObjectGroup.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning ObjectGroup object : " + objectGroup);
			}
			return objectGroup;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertObjectGroup(final ObjectGroup objectGroup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroup(ObjectGroup) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in ObjectGroup: " + objectGroup);
		try {
			result = template.insert(qObjectGroup, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName, qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime, qObjectGroup.updatedBy, qObjectGroup.updatedDateTime).values(objectGroup.getObjectGroupID(), objectGroup.getObjectGroupName(), objectGroup.getDescription(), objectGroup.getAppID(), objectGroup.getScreenID(), objectGroup.getCreatedBy(), objectGroup.getCreatedDateTime(), objectGroup.getUpdatedBy(), objectGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in ObjectGroup");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroup(ObjectGroup) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param objectGroup
	 *            the object group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateObjectGroup(final ObjectGroup objectGroup) throws DataAccessException {
		LOG.info("Started updating data in Conditions: " + objectGroup);
		try {
			long returnlong = template.update(qObjectGroup, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qObjectGroup.objectGroupID.eq(objectGroup.getObjectGroupID())).set(qObjectGroup.description, objectGroup.getDescription()).set(qObjectGroup.updatedBy, objectGroup.getUpdatedBy()).set(qObjectGroup.updatedDateTime, objectGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjectGroup(ObjectGroup) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ObjectGroupDao#getObjectGroup()
	 */
	@Override
	public List<ObjectGroup> getObjectGroup() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroup() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectGroup)
					.where(qObjectGroup.appID.eq(qApplication.appID).and(qObjectGroup.screenID.eq(qScreen.screenID)));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectGroupProjection(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName,
					qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime,
					qObjectGroup.updatedBy, qObjectGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ObjectGroupDao#getObjectGroupById(int)
	 */
	@Override
	public ObjectGroup getObjectGroupById(int objectGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectGroup).where(qObjectGroup.objectGroupID.eq(objectGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingObjectGroupProjection(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName,
					qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime,
					qObjectGroup.updatedBy, qObjectGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ObjectGroupDao#getObjectGroupIDByNameAndScreenId
	 * (java.lang.String, int)
	 */
	@Override
	public int getObjectGroupIDByNameAndScreenId(String objectGroupName, int screenID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupIDByNameAndScreenId(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectGroup)
					.where(qObjectGroup.objectGroupName.eq(objectGroupName).and(qObjectGroup.screenID.eq(screenID)));
			LOG.info("ObjectGroup query : " + sqlQuery);
			Integer objectGroupID = template.queryForObject(sqlQuery, qObjectGroup.objectGroupID);
			if (objectGroupID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getObjectGroupIDByNameAndScreenId(String, int) - end"); //$NON-NLS-1$
				}
				return objectGroupID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectGroupIDByNameAndScreenId(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ObjectGroupDao#insertObjectGroupGetKey(com.
	 * exilant.tfw.pojo.input.ObjectGroup)
	 */
	@Override
	public int insertObjectGroupGetKey(final ObjectGroup objectGroup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroupGetKey(ObjectGroup) - start"); //$NON-NLS-1$
		}

		int objectGroupId = 0;
		try {
			objectGroupId = template.insertWithKey(qObjectGroup, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName, qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime, qObjectGroup.updatedBy, qObjectGroup.updatedDateTime).values(objectGroup.getObjectGroupID(), objectGroup.getObjectGroupName(), objectGroup.getDescription(), objectGroup.getAppID(), objectGroup.getScreenID(), objectGroup.getCreatedBy(), objectGroup.getCreatedDateTime(), objectGroup.getUpdatedBy(), objectGroup.getUpdatedDateTime()).executeWithKey(qObjectGroup.objectGroupID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated objectGroupId : " + objectGroupId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectGroupGetKey(ObjectGroup) - end"); //$NON-NLS-1$
		}
		return objectGroupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ObjectGroupDao#getAllObjectGroups()
	 */
	@Override
	public List<ObjectGroup> getAllObjectGroups() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllObjectGroups() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectGroup);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectGroupProjection(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName,
					qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime,
					qObjectGroup.updatedBy, qObjectGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ObjectGroupDao#updateObjectGroupData(com.exilant
	 * .tfw.pojo.input.ObjectGroup)
	 */
	@Override
	public long updateObjectGroupData(final ObjectGroup objectGroup) throws DataAccessException {
		LOG.info("Started updating data in Conditions: " + objectGroup);
		try {
			long returnlong = template.update(qObjectGroup, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qObjectGroup.objectGroupID.eq(objectGroup.getObjectGroupID())).set(qObjectGroup.objectGroupName, objectGroup.getObjectGroupName()).set(qObjectGroup.screenID, objectGroup.getScreenID()).set(qObjectGroup.description, objectGroup.getDescription()).set(qObjectGroup.updatedBy, objectGroup.getUpdatedBy()).set(qObjectGroup.updatedDateTime, objectGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjectGroupData(ObjectGroup) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ObjectGroupDao#getObjGrpsByScreenID(int)
	 */
	@Override
	public List<ObjectGroup> getObjGrpsByScreenID(int scrId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpsByScreenID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectGroup).where(qObjectGroup.screenID.eq(scrId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectGroupProjection(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName,
					qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime,
					qObjectGroup.updatedBy, qObjectGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ObjectGroupDao#getObjGrpsByAppID(int)
	 */
	@Override
	public List<ObjectGroup> getObjGrpsByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjGrpsByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectGroup).where(qObjectGroup.appID.eq(appId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectGroupProjection(qObjectGroup.objectGroupID, qObjectGroup.objectGroupName,
					qObjectGroup.description, qObjectGroup.appID, qObjectGroup.screenID, qObjectGroup.createdBy, qObjectGroup.createdDateTime,
					qObjectGroup.updatedBy, qObjectGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}