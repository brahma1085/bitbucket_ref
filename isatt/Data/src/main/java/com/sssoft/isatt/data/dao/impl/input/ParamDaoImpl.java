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
import com.sssoft.isatt.data.dao.input.ParamDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QObjects;
import com.sssoft.isatt.data.generated.pojo.QParam;
import com.sssoft.isatt.data.generated.pojo.QParamgroup;
import com.sssoft.isatt.data.pojo.input.Param;

/**
 * The Class ParamDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ParamDao interface
 */
public class ParamDaoImpl implements ParamDao {

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

	/** The q param. */
	private QParam qParam = QParam.param;

	/** The q param group. */
	private QParamgroup qParamGroup = QParamgroup.paramgroup;

	/** The q objects. */
	private QObjects qObjects = QObjects.objects;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ParamDaoImpl.class);

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
	 * The Class MappingParamProjection.
	 */
	public class MappingParamProjection extends MappingProjection<Param> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping param projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingParamProjection(Expression<?>... args) {
			super(Param.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Param map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Param param = new Param();
			param.setParamID(tuple.get(qParam.paramID));
			param.setParamName(tuple.get(qParam.paramName));
			param.setDescription(tuple.get(qParam.description));
			param.setSortOrder(tuple.get(qParam.sortOrder));
			param.setParamGroupID(tuple.get(qParam.paramGroupID));
			param.setObjectID(tuple.get(qParam.objectID));
			param.setCreatedBy(tuple.get(qParam.createdBy));
			param.setCreatedDateTime(tuple.get(qParam.createdDateTime));
			param.setUpdatedBy(tuple.get(qParam.updatedBy));
			param.setUpdatedDateTime(tuple.get(qParam.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Param object : " + param);
			}
			return param;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param param
	 *            the param
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertParam(final Param param) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParam(Param) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Param: " + param);
		try {
			result = template.insert(qParam, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qParam.paramID, qParam.paramName, qParam.description, qParam.sortOrder, qParam.paramGroupID, qParam.objectID, qParam.createdBy, qParam.createdDateTime, qParam.updatedBy, qParam.updatedDateTime).values(param.getParamID(), param.getParamName(), param.getDescription(), param.getSortOrder(), param.getParamGroupID(), param.getObjectID(), param.getCreatedBy(), param.getCreatedDateTime(), param.getUpdatedBy(), param.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Param");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParam(Param) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param param
	 *            the param
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateParam(final Param param) throws DataAccessException {
		LOG.info("Started updating data in Param: " + param);
		try {
			long returnlong = template.update(qParam, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qParam.paramID.eq(param.getParamID())).set(qParam.description, param.getDescription()).set(qParam.sortOrder, param.getSortOrder()).set(qParam.updatedBy, param.getUpdatedBy()).set(qParam.updatedDateTime, param.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateParam(Param) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ParamDao#getParam()
	 */
	@Override
	public List<Param> getParam() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParam() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParam)
					.where(qParam.paramGroupID.eq(qParamGroup.paramGroupID).and(qParam.objectID.eq(qObjects.objectID)));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingParamProjection(qParam.paramID, qParam.paramName, qParam.description, qParam.sortOrder,
					qParam.paramGroupID, qParam.objectID, qParam.createdBy, qParam.createdDateTime, qParam.updatedBy, qParam.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ParamDao#getParamById(int)
	 */
	@Override
	public Param getParamById(int paramId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParam).where(qParam.paramID.eq(paramId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingParamProjection(qParam.paramID, qParam.paramName, qParam.description, qParam.sortOrder,
					qParam.paramGroupID, qParam.objectID, qParam.createdBy, qParam.createdDateTime, qParam.updatedBy, qParam.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ParamDao#getParamByParamGroupId(int)
	 */
	@Override
	public List<Param> getParamByParamGroupId(int paramGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamByParamGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qParam).where(qParam.paramGroupID.eq(paramGroupId)).orderBy(qParam.paramID.asc());
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingParamProjection(qParam.paramID, qParam.paramName, qParam.description, qParam.sortOrder,
					qParam.paramGroupID, qParam.objectID, qParam.createdBy, qParam.createdDateTime, qParam.updatedBy, qParam.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamDao#getParamIdByDependents(com.exilant
	 * .tfw.pojo.input.Param)
	 */
	@Override
	public int getParamIdByDependents(Param param) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamIdByDependents(Param) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qParam)
					.where(qParam.paramGroupID.eq(param.getParamGroupID()).and(
							qParam.paramName.eq(param.getParamName()).and(qParam.objectID.eq(param.getObjectID()))));
			LOG.info("Param query : " + sqlQuery);
			Integer paramID = template.queryForObject(sqlQuery, qParam.paramID);
			if (paramID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getParamIdByDependents(Param) - end"); //$NON-NLS-1$
				}
				return paramID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamIdByDependents(Param) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamDao#insertParamGetKey(com.exilant.tfw.
	 * pojo.input.Param)
	 */
	@Override
	public int insertParamGetKey(final Param param) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGetKey(Param) - start"); //$NON-NLS-1$
		}

		int paramId = 0;
		try {
			paramId = template.insertWithKey(qParam, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qParam.paramID, qParam.paramName, qParam.description, qParam.sortOrder, qParam.paramGroupID, qParam.objectID, qParam.createdBy, qParam.createdDateTime, qParam.updatedBy, qParam.updatedDateTime).values(param.getParamID(), param.getParamName(), param.getDescription(), param.getSortOrder(), param.getParamGroupID(), param.getObjectID(), param.getCreatedBy(), param.getCreatedDateTime(), param.getUpdatedBy(), param.getUpdatedDateTime()).executeWithKey(qParam.paramID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated paramId : " + paramId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertParamGetKey(Param) - end"); //$NON-NLS-1$
		}
		return paramId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ParamDao#getParamByAppId(int)
	 */
	@Override
	public List<Param> getParamByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			// SQLQuery sqlQuery =
			// template.newSqlQuery().from(qParam).where(qParam.appID.eq(appId);
			SQLQuery sqlQuery = template.newSqlQuery().from(qParam);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingParamProjection(qParam.paramID, qParam.paramName, qParam.description, qParam.sortOrder,
					qParam.paramGroupID, qParam.objectID, qParam.createdBy, qParam.createdDateTime, qParam.updatedBy, qParam.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ParamDao#updateParamDetails(com.exilant.tfw
	 * .pojo.input.Param)
	 */
	@Override
	public long updateParamDetails(final Param param) throws DataAccessException {
		LOG.info("Started updating data in Param: " + param);
		try {
			long returnlong = template.update(qParam, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qParam.paramID.eq(param.getParamID())).set(qParam.paramName, param.getParamName()).set(qParam.description, param.getDescription()).set(qParam.paramGroupID, param.getParamGroupID()).set(qParam.objectID, param.getObjectID()).set(qParam.sortOrder, param.getSortOrder()).set(qParam.updatedBy, param.getUpdatedBy()).set(qParam.updatedDateTime, param.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateParamDetails(Param) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

}