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
import com.sssoft.isatt.data.dao.input.ParamGroupDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.generated.pojo.QObjectgroup;
import com.sssoft.isatt.data.generated.pojo.QParamgroup;
import com.sssoft.isatt.data.pojo.input.ParamGroup;

/**
 * The Class ParamGroupDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ParamGroupDao interface
 */
public class ParamGroupDaoImpl implements ParamGroupDao {

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

	/** The q param group. */
	private QParamgroup qParamGroup = QParamgroup.paramgroup;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** The q object group. */
	private QObjectgroup qObjectGroup = QObjectgroup.objectgroup;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ParamGroupDaoImpl.class);

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
	 * The Class MappingParamGroupProjection.
	 */
	public class MappingParamGroupProjection extends MappingProjection<ParamGroup> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping param group projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingParamGroupProjection(Expression<?>... args) {
			super(ParamGroup.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ParamGroup map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			ParamGroup paramGroup = new ParamGroup();
			paramGroup.setParamGroupID(tuple.get(qParamGroup.paramGroupID));
			paramGroup.setParamGroupName(tuple.get(qParamGroup.paramGroupName));
			paramGroup.setDescription(tuple.get(qParamGroup.description));
			paramGroup.setTag(tuple.get(qParamGroup.tag));
			paramGroup.setAppID(tuple.get(qParamGroup.appID));
			paramGroup.setCreatedBy(tuple.get(qParamGroup.createdBy));
			paramGroup.setCreatedDateTime(tuple.get(qParamGroup.createdDateTime));
			paramGroup.setUpdatedBy(tuple.get(qParamGroup.updatedBy));
			paramGroup.setUpdatedDateTime(tuple.get(qParamGroup.updatedDateTime));
			paramGroup.setObjectGroupID(tuple.get(qParamGroup.objectGroupID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning ParamGroup object : " + paramGroup);
			}
			return paramGroup;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertParamGroup(final ParamGroup paramGroup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroup(ParamGroup) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in ParamGroup: " + paramGroup);
		try {
			result = template.insert(qParamGroup, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qParamGroup.paramGroupID, qParamGroup.paramGroupName, qParamGroup.description, qParamGroup.tag, qParamGroup.appID, qParamGroup.createdBy, qParamGroup.createdDateTime, qParamGroup.updatedBy, qParamGroup.updatedDateTime, qParamGroup.objectGroupID).values(paramGroup.getParamGroupID(), paramGroup.getParamGroupName(), paramGroup.getDescription(), paramGroup.getTag(), paramGroup.getAppID(), paramGroup.getCreatedBy(), paramGroup.getCreatedDateTime(), paramGroup.getUpdatedBy(), paramGroup.getUpdatedDateTime(), paramGroup.getObjectGroupID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in ParamGroup");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroup(ParamGroup) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param paramGroup
	 *            the param group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateParamGroup(final ParamGroup paramGroup) throws DataAccessException {
		LOG.info("Started updating data in ParamGroup: " + paramGroup);
		try {
			long returnlong = template.update(qParamGroup, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qParamGroup.paramGroupID.eq(paramGroup.getParamGroupID())).set(qParamGroup.description, paramGroup.getDescription()).set(qParamGroup.tag, paramGroup.getTag()).set(qParamGroup.updatedBy, paramGroup.getUpdatedBy()).set(qParamGroup.updatedDateTime, paramGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateParamGroup(ParamGroup) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ParamGroupDao#getParamGroup()
	 */
	@Override
	public List<ParamGroup> getParamGroup() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroup() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParamGroup)
					.where(qParamGroup.appID.eq(qApplication.appID).and(qParamGroup.objectGroupID.eq(qObjectGroup.objectGroupID)));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingParamGroupProjection(qParamGroup.paramGroupID, qParamGroup.paramGroupName, qParamGroup.description,
					qParamGroup.tag, qParamGroup.appID, qParamGroup.createdBy, qParamGroup.createdDateTime, qParamGroup.updatedBy,
					qParamGroup.updatedDateTime, qParamGroup.objectGroupID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ParamGroupDao#getParamGroupById(int)
	 */
	@Override
	public ParamGroup getParamGroupById(int paramGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParamGroup).where(qParamGroup.paramGroupID.eq(paramGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingParamGroupProjection(qParamGroup.paramGroupID, qParamGroup.paramGroupName,
					qParamGroup.description, qParamGroup.tag, qParamGroup.appID, qParamGroup.createdBy, qParamGroup.createdDateTime,
					qParamGroup.updatedBy, qParamGroup.updatedDateTime, qParamGroup.objectGroupID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamGroupDao#getParamGroupByObjectGroupId(int)
	 */
	@Override
	public ParamGroup getParamGroupByObjectGroupId(int objectGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupByObjectGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParamGroup).where(qParamGroup.objectGroupID.eq(objectGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingParamGroupProjection(qParamGroup.paramGroupID, qParamGroup.paramGroupName,
					qParamGroup.description, qParamGroup.tag, qParamGroup.appID, qParamGroup.createdBy, qParamGroup.createdDateTime,
					qParamGroup.updatedBy, qParamGroup.updatedDateTime, qParamGroup.objectGroupID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamGroupDao#getParamGroupIdByNameByObjectGrpId
	 * (java.lang.String, int)
	 */
	@Override
	public int getParamGroupIdByNameByObjectGrpId(String paramGroupName, int objectGroupID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupIdByNameByObjectGrpId(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParamGroup)
					.where(qParamGroup.paramGroupName.eq(paramGroupName).and(qParamGroup.objectGroupID.eq(objectGroupID)));
			LOG.info("ParamGroup query : " + sqlQuery);
			Integer paramGroupID = template.queryForObject(sqlQuery, qParamGroup.paramGroupID);
			if (paramGroupID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getParamGroupIdByNameByObjectGrpId(String, int) - end"); //$NON-NLS-1$
				}
				return paramGroupID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupIdByNameByObjectGrpId(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamGroupDao#insertParamGroupGetKey(com.exilant
	 * .tfw.pojo.input.ParamGroup)
	 */
	@Override
	public int insertParamGroupGetKey(final ParamGroup paramGroup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroupGetKey(ParamGroup) - start"); //$NON-NLS-1$
		}

		int paramGroupId = 0;
		try {
			paramGroupId = template.insertWithKey(qParamGroup, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qParamGroup.paramGroupID, qParamGroup.paramGroupName, qParamGroup.description, qParamGroup.tag, qParamGroup.appID, qParamGroup.objectGroupID, qParamGroup.createdBy, qParamGroup.createdDateTime, qParamGroup.updatedBy, qParamGroup.updatedDateTime).values(paramGroup.getParamGroupID(), paramGroup.getParamGroupName(), paramGroup.getDescription(), paramGroup.getTag(), paramGroup.getAppID(), paramGroup.getObjectGroupID(), paramGroup.getCreatedBy(), paramGroup.getCreatedDateTime(), paramGroup.getUpdatedBy(), paramGroup.getUpdatedDateTime()).executeWithKey(qParamGroup.paramGroupID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated paramGroupId : " + paramGroupId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGroupGetKey(ParamGroup) - end"); //$NON-NLS-1$
		}
		return paramGroupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamGroupDao#getParamGroupNamesByAppId(int)
	 */
	@Override
	public List<ParamGroup> getParamGroupNamesByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupNamesByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParamGroup).where(qParamGroup.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingParamGroupProjection(qParamGroup.paramGroupID, qParamGroup.paramGroupName, qParamGroup.description,
					qParamGroup.tag, qParamGroup.appID, qParamGroup.objectGroupID, qParamGroup.createdBy, qParamGroup.createdDateTime,
					qParamGroup.updatedBy, qParamGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ParamGroupDao#getParamGroupByAppId(int)
	 */
	@Override
	public List<ParamGroup> getParamGroupByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParamGroup).where(qParamGroup.appID.eq(appId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingParamGroupProjection(qParamGroup.paramGroupID, qParamGroup.paramGroupName, qParamGroup.description,
					qParamGroup.tag, qParamGroup.appID, qParamGroup.createdBy, qParamGroup.createdDateTime, qParamGroup.updatedBy,
					qParamGroup.updatedDateTime, qParamGroup.objectGroupID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamGroupDao#updateParamGroupData(com.exilant
	 * .tfw.pojo.input.ParamGroup)
	 */
	@Override
	public long updateParamGroupData(final ParamGroup paramGroup) throws DataAccessException {
		LOG.info("Started updating data in ParamGroup: " + paramGroup);
		try {
			long returnlong = template.update(qParamGroup, new SqlUpdateCallback() {
				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qParamGroup.paramGroupID.eq(paramGroup.getParamGroupID())).set(qParamGroup.paramGroupName, paramGroup.getParamGroupName()).set(qParamGroup.description, paramGroup.getDescription()).set(qParamGroup.tag, paramGroup.getTag()).set(qParamGroup.updatedBy, paramGroup.getUpdatedBy()).set(qParamGroup.updatedDateTime, paramGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateParamGroupData(ParamGroup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

}