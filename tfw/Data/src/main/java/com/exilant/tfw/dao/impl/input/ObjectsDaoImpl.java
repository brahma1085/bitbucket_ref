package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.IdentifierTypeDao;
import com.exilant.tfw.dao.input.ObjectGroupDao;
import com.exilant.tfw.dao.input.ObjectTypeDao;
import com.exilant.tfw.dao.input.ObjectsDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QIdentifiertype;
import com.exilant.tfw.generated.pojo.QObjectgroup;
import com.exilant.tfw.generated.pojo.QObjects;
import com.exilant.tfw.generated.pojo.QObjecttype;
import com.exilant.tfw.pojo.input.Objects;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class ObjectsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ObjectsDao interface
 */
public class ObjectsDaoImpl implements ObjectsDao {

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

	/** The q objects. */
	private QObjects qObjects = QObjects.objects;

	/** The q object group. */
	private QObjectgroup qObjectGroup = QObjectgroup.objectgroup;

	/** The q object type. */
	private QObjecttype qObjectType = QObjecttype.objecttype;

	/** The q identifier type. */
	private QIdentifiertype qIdentifierType = QIdentifiertype.identifiertype;

	/** The object type dao. */
	@Autowired(required = false)
	private ObjectTypeDao objectTypeDao;

	/** The identifier type dao. */
	@Autowired(required = false)
	private IdentifierTypeDao identifierTypeDao;

	/** The object group dao. */
	@Autowired(required = false)
	private ObjectGroupDao objectGroupDao;

	/**
	 * Sets the object group dao.
	 * 
	 * @param objectGroupDao
	 *            the new object group dao
	 */
	public void setObjectGroupDao(ObjectGroupDao objectGroupDao) {
		this.objectGroupDao = objectGroupDao;
	}

	/**
	 * Sets the object type dao.
	 * 
	 * @param objectTypeDao
	 *            the new object type dao
	 */
	public void setObjectTypeDao(ObjectTypeDao objectTypeDao) {
		this.objectTypeDao = objectTypeDao;
	}

	/**
	 * Sets the identifier type dao.
	 * 
	 * @param identifierTypeDao
	 *            the new identifier type dao
	 */
	public void setIdentifierTypeDao(IdentifierTypeDao identifierTypeDao) {
		this.identifierTypeDao = identifierTypeDao;
	}

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ObjectsDaoImpl.class);

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
	 * The Class MappingObjectsProjection.
	 */
	public class MappingObjectsProjection extends MappingProjection<Objects> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping objects projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingObjectsProjection(Expression<?>... args) {
			super(Objects.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Objects map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Objects objects = new Objects();
			objects.setObjectID(tuple.get(qObjects.objectID));
			objects.setObjectName(tuple.get(qObjects.objectName));
			objects.setDescription(tuple.get(qObjects.description));
			objects.setObjectGroupID(tuple.get(qObjects.objectGroupID));
			objects.setObjectTypeID(tuple.get(qObjects.objectTypeID));
			objects.setIdentifierTypeID(tuple.get(qObjects.identifierTypeID));
			objects.setAppID(tuple.get(qObjects.appID));
			objects.setIdentifier(tuple.get(qObjects.identifier));
			objects.setCreatedBy(tuple.get(qObjects.createdBy));
			objects.setCreatedDateTime(tuple.get(qObjects.createdDateTime));
			objects.setUpdatedBy(tuple.get(qObjects.updatedBy));
			objects.setUpdatedDateTime(tuple.get(qObjects.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Objects object : " + objects);
			}
			return objects;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param objects
	 *            the objects
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertObjects(final Objects objects) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjects(Objects) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Objects: " + objects);
		try {
			result = template.insert(qObjects, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qObjects.objectID, qObjects.objectName, qObjects.description, qObjects.objectGroupID, qObjects.objectTypeID, qObjects.identifierTypeID, qObjects.appID, qObjects.identifier, qObjects.createdBy, qObjects.createdDateTime, qObjects.updatedBy, qObjects.updatedDateTime).values(objects.getObjectID(), objects.getObjectName(), objects.getDescription(), objects.getObjectGroupID(), objects.getObjectTypeID(), objects.getIdentifierTypeID(), objects.getAppID(), objects.getIdentifier(), objects.getCreatedBy(), objects.getCreatedDateTime(), objects.getUpdatedBy(), objects.getUpdatedDateTime()).execute();
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
			LOG.debug("insertObjects(Objects) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param objects
	 *            the objects
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateObjects(final Objects objects) throws DataAccessException {
		LOG.info("Started updating data in Objects: " + objects);
		try {
			long returnlong = template.update(qObjects, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qObjects.objectID.eq(objects.getObjectID())).set(qObjects.objectName, objects.getObjectName()).set(qObjects.description, objects.getDescription()).set(qObjects.objectTypeID, objects.getObjectTypeID()).set(qObjects.identifierTypeID, objects.getIdentifierTypeID()).set(qObjects.updatedBy, objects.getUpdatedBy()).set(qObjects.updatedDateTime, objects.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjects(Objects) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/**
	 * Gets the objects by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the objects by query
	 */
	private List<Objects> getObjectsByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<Objects> returnList = template.query(sqlQuery, new MappingObjectsProjection(qObjects.objectID, qObjects.objectName, qObjects.description, qObjects.objectGroupID, qObjects.objectTypeID, qObjects.identifierTypeID, qObjects.appID, qObjects.identifier, qObjects.createdBy, qObjects.createdDateTime, qObjects.updatedBy, qObjects.updatedDateTime));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectsDao#getObjects()
	 */
	@Override
	public List<Objects> getObjects() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjects() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qObjects)
					.where(qObjects.objectGroupID.eq(qObjectGroup.objectGroupID).and(qObjects.objectTypeID.eq(qObjectType.objectTypeID))
							.and(qObjects.identifierTypeID.eq(qIdentifierType.identifierTypeID)));
			LOG.info("Generated Query : " + sqlQuery);
			return getObjectsByQuery(sqlQuery);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectsDao#getObjectsById(int)
	 */
	@Override
	public Objects getObjectsById(int objectId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjects).where(qObjects.objectID.eq(objectId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingObjectsProjection(qObjects.objectID, qObjects.objectName, qObjects.description,
					qObjects.objectGroupID, qObjects.objectTypeID, qObjects.identifierTypeID, qObjects.appID, qObjects.identifier, qObjects.createdBy,
					qObjects.createdDateTime, qObjects.updatedBy, qObjects.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectsDao#getObjectsByGroupId(int)
	 */
	@Override
	public List<Objects> getObjectsByGroupId(int objectGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectsByGroupId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qObjects).where(qObjects.objectGroupID.eq(objectGroupId)).orderBy(qObjects.objectID.asc());
		LOG.info("generated query : " + sqlQuery);
		return getObjectsByQuery(sqlQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ObjectsDao#getObjectIDByName(java.lang.String)
	 */
	@Override
	public int getObjectIDByName(String objectName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectIDByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjects).where(qObjects.objectName.eq(objectName));
			LOG.info("Objects query : " + sqlQuery);
			Integer objectID = template.queryForObject(sqlQuery, qObjects.objectID);
			if (objectID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getObjectIDByName(String) - end"); //$NON-NLS-1$
				}
				return objectID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectIDByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ObjectsDao#insertObjectsGetKey(com.exilant.
	 * tfw.pojo.input.Objects)
	 */
	@Override
	public int insertObjectsGetKey(final Objects objects) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectsGetKey(Objects) - start"); //$NON-NLS-1$
		}

		int objectsId = 0;
		try {
			objectsId = template.insertWithKey(qObjects, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qObjects.objectID, qObjects.objectName, qObjects.description, qObjects.objectGroupID, qObjects.objectTypeID, qObjects.identifierTypeID, qObjects.appID, qObjects.identifier, qObjects.createdBy, qObjects.createdDateTime, qObjects.updatedBy, qObjects.updatedDateTime).values(objects.getObjectID(), objects.getObjectName(), objects.getDescription(), objects.getObjectGroupID(), objects.getObjectTypeID(), objects.getIdentifierTypeID(), objects.getAppID(), objects.getIdentifier(), objects.getCreatedBy(), objects.getCreatedDateTime(), objects.getUpdatedBy(), objects.getUpdatedDateTime()).executeWithKey(qObjects.objectID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated objectsId : " + objectsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectsGetKey(Objects) - end"); //$NON-NLS-1$
		}
		return objectsId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectsDao#
	 * getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid
	 * (com.exilant.tfw.pojo.input.Objects)
	 */
	@Override
	public int getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(Objects objects) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(Objects) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qObjects)
					.where(qObjects.objectName.eq(objects.getObjectName()).and(
							qObjects.objectGroupID.eq(objects.getObjectGroupID()).and(
									qObjects.objectTypeID.eq(objects.getObjectTypeID()).and(qObjects.identifierTypeID.eq(objects.getIdentifierTypeID())))));
			LOG.info("Objects query : " + sqlQuery);
			Integer objectID = template.queryForObject(sqlQuery, qObjects.objectID);
			if (objectID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(Objects) - end"); //$NON-NLS-1$
				}
				return objectID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectIdByNameByObjGroupidByObjTypeidByIdenTypeid(Objects) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ObjectsDao#updateObjectsDetails(com.exilant
	 * .tfw.pojo.input.Objects)
	 */
	@Override
	public long updateObjectsDetails(final Objects objects) throws DataAccessException {
		LOG.info("Started updating data in Objects: " + objects);
		try {
			long returnlong = template.update(qObjects, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qObjects.objectID.eq(objects.getObjectID())).set(qObjects.objectName, objects.getObjectName()).set(qObjects.description, objects.getDescription()).set(qObjects.objectGroupID, objects.getObjectGroupID()).set(qObjects.objectTypeID, objects.getObjectTypeID()).set(qObjects.identifierTypeID, objects.getIdentifierTypeID()).set(qObjects.identifier, objects.getIdentifier()).set(qObjects.updatedBy, objects.getUpdatedBy()).set(qObjects.updatedDateTime, objects.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjectsDetails(Objects) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

}