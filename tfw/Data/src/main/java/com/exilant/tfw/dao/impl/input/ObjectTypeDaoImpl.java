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

import com.exilant.tfw.dao.ActionsDao;
import com.exilant.tfw.dao.input.ObjectTypeDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QActions;
import com.exilant.tfw.generated.pojo.QObjecttype;
import com.exilant.tfw.pojo.input.ObjectType;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class ObjectTypeDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ObjectTypeDao interface
 */
public class ObjectTypeDaoImpl implements ObjectTypeDao {

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

	/** The q object type. */
	private QObjecttype qObjectType = QObjecttype.objecttype;

	/** The q actions. */
	private QActions qActions = QActions.actions;

	/** The actions dao. */
	@Autowired(required = true)
	private ActionsDao actionsDao;

	/**
	 * Sets the actions dao.
	 * 
	 * @param actionsDao
	 *            the new actions dao
	 */
	public void setActionsDao(ActionsDao actionsDao) {
		this.actionsDao = actionsDao;
	}

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ObjectTypeDaoImpl.class);

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
	 * The Class MappingObjectTypeProjection.
	 */
	public class MappingObjectTypeProjection extends MappingProjection<ObjectType> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping object type projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingObjectTypeProjection(Expression<?>... args) {
			super(ObjectType.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ObjectType map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			ObjectType objectType = new ObjectType();
			objectType.setObjectTypeID(tuple.get(qObjectType.objectTypeID));
			objectType.setObjectTypeName(tuple.get(qObjectType.objectTypeName));
			objectType.setDescription(tuple.get(qObjectType.description));
			objectType.setActionID(tuple.get(qObjectType.actionId));
			objectType.setCreatedBy(tuple.get(qObjectType.createdBy));
			objectType.setCreatedDateTime(tuple.get(qObjectType.createdDateTime));
			objectType.setUpdatedBy(tuple.get(qObjectType.updatedBy));
			objectType.setUpdatedDateTime(tuple.get(qObjectType.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning ObjectType object : " + objectType);
			}
			return objectType;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param objectType
	 *            the object type
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertObjectType(final ObjectType objectType) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectType(ObjectType) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in ObjectType: " + objectType);
		try {
			result = template.insert(qObjectType, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qObjectType.objectTypeID, qObjectType.objectTypeName, qObjectType.description, qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy, qObjectType.updatedDateTime).values(objectType.getObjectTypeID(), objectType.getObjectTypeName(), objectType.getDescription(), objectType.getActionID(), objectType.getCreatedBy(), objectType.getCreatedDateTime(), objectType.getUpdatedBy(), objectType.getUpdatedDateTime()).execute();
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
			LOG.debug("insertObjectType(ObjectType) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param objectType
	 *            the object type
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateObjectType(final ObjectType objectType) throws DataAccessException {
		LOG.info("Started updating data in ObjectType: " + objectType);
		try {
			long returnlong = template.update(qObjectType, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qObjectType.objectTypeID.eq(objectType.getObjectTypeID())).set(qObjectType.description, objectType.getDescription()).set(qObjectType.updatedBy, objectType.getUpdatedBy()).set(qObjectType.updatedDateTime, objectType.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateObjectType(ObjectType) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.input.ObjectTypeDao#getObjectType()
	 */
	@Override
	public List<ObjectType> getObjectType() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectType() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType).where(qObjectType.actionId.eq(qActions.actionID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectTypeProjection(qObjectType.objectTypeID, qObjectType.objectTypeName, qObjectType.description,
					qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy, qObjectType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectTypeDao#getObjectTypeById(int)
	 */
	@Override
	public ObjectType getObjectTypeById(int objectTypeId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType).where(qObjectType.objectTypeID.eq(objectTypeId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingObjectTypeProjection(qObjectType.objectTypeID, qObjectType.objectTypeName,
					qObjectType.description, qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy,
					qObjectType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectTypeDao#getObjectTypeByActionId(int)
	 */
	@Override
	public List<ObjectType> getObjectTypeByActionId(int actionId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeByActionId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType).where(qObjectType.actionId.eq(actionId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectTypeProjection(qObjectType.objectTypeID, qObjectType.objectTypeName, qObjectType.description,
					qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy, qObjectType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ObjectTypeDao#getObjectTypeIdByNameAndActionId
	 * (int, java.lang.String)
	 */
	@Override
	public int getObjectTypeIdByNameAndActionId(int actionId, String objectTypeName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeIdByNameAndActionId(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType)
					.where(qObjectType.actionId.eq(actionId).and(qObjectType.objectTypeName.eq(objectTypeName)));
			LOG.info("IdentifierType query : " + sqlQuery);
			Integer objectTypeID = template.queryForObject(sqlQuery, qObjectType.objectTypeID);
			if (objectTypeID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getObjectTypeIdByNameAndActionId(int, String) - end"); //$NON-NLS-1$
				}
				return objectTypeID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeIdByNameAndActionId(int, String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectTypeDao#getObjectTypeByActionId(int,
	 * java.lang.String)
	 */
	@Override
	public List<ObjectType> getObjectTypeByActionId(int actionId, String objectTypeName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeByActionId(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType)
					.where(qObjectType.actionId.eq(actionId).and(qObjectType.objectTypeName.eq(objectTypeName)));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectTypeProjection(qObjectType.objectTypeID, qObjectType.objectTypeName, qObjectType.description,
					qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy, qObjectType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ObjectTypeDao#getObjectTypeIDByName(java.lang
	 * .String)
	 */
	@Override
	public int getObjectTypeIDByName(String objectName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeIDByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType).where(qObjectType.objectTypeName.eq(objectName));
			LOG.info("IdentifierType query : " + sqlQuery);
			Integer objectTypeID = template.queryForObject(sqlQuery, qObjectType.objectTypeID);
			if (objectTypeID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getObjectTypeIDByName(String) - end"); //$NON-NLS-1$
				}
				return objectTypeID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getObjectTypeIDByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.ObjectTypeDao#insertObjectTypeGetKey(com.exilant
	 * .tfw.pojo.input.ObjectType)
	 */
	@Override
	public int insertObjectTypeGetKey(final ObjectType objectType) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectTypeGetKey(ObjectType) - start"); //$NON-NLS-1$
		}

		int objectTypeId = 0;
		try {
			objectTypeId = template.insertWithKey(qObjectType, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qObjectType.objectTypeID, qObjectType.objectTypeName, qObjectType.description, qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy, qObjectType.updatedDateTime).values(objectType.getObjectTypeID(), objectType.getObjectTypeName(), objectType.getDescription(), objectType.getActionID(), objectType.getCreatedBy(), objectType.getCreatedDateTime(), objectType.getUpdatedBy(), objectType.getUpdatedDateTime()).executeWithKey(qObjectType.objectTypeID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated objectTypeId : " + objectTypeId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertObjectTypeGetKey(ObjectType) - end"); //$NON-NLS-1$
		}
		return objectTypeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.ObjectTypeDao#getAllObjectTypes()
	 */
	@Override
	public List<ObjectType> getAllObjectTypes() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllObjectTypes() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qObjectType);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingObjectTypeProjection(qObjectType.objectTypeID, qObjectType.objectTypeName, qObjectType.description,
					qObjectType.actionId, qObjectType.createdBy, qObjectType.createdDateTime, qObjectType.updatedBy, qObjectType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}