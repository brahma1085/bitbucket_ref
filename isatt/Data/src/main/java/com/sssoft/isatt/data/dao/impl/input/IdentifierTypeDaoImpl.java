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
import com.sssoft.isatt.data.dao.input.IdentifierTypeDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.generated.pojo.QIdentifiertype;
import com.sssoft.isatt.data.pojo.input.IdentifierType;

/**
 * The Class IdentifierTypeDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for IdentifierTypeDao
 * interface
 */
public class IdentifierTypeDaoImpl implements IdentifierTypeDao {

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

	/** The q identifier type. */
	private QIdentifiertype qIdentifierType = QIdentifiertype.identifiertype;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(IdentifierTypeDaoImpl.class);

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
	 * The Class MappingIdentifierTypeProjection.
	 */
	public class MappingIdentifierTypeProjection extends MappingProjection<IdentifierType> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping identifier type projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingIdentifierTypeProjection(Expression<?>... args) {
			super(IdentifierType.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected IdentifierType map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			IdentifierType identifierType = new IdentifierType();
			identifierType.setIdentifierTypeID(tuple.get(qIdentifierType.identifierTypeID));
			identifierType.setIdentifierTypeName(tuple.get(qIdentifierType.identifierTypeName));
			identifierType.setDescription(tuple.get(qIdentifierType.description));
			identifierType.setAppID(tuple.get(qIdentifierType.appID));
			identifierType.setCreatedBy(tuple.get(qIdentifierType.createdBy));
			identifierType.setCreatedDateTime(tuple.get(qIdentifierType.createdDateTime));
			identifierType.setUpdatedBy(tuple.get(qIdentifierType.updatedBy));
			identifierType.setUpdatedDateTime(tuple.get(qIdentifierType.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning IdentifierType object : " + identifierType);
			}
			return identifierType;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertIdentifierType(final IdentifierType identifierType) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertIdentifierType(IdentifierType) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in IdentifierType: " + identifierType);
		try {
			result = template.insert(qIdentifierType, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qIdentifierType.identifierTypeID, qIdentifierType.identifierTypeName, qIdentifierType.description, qIdentifierType.appID, qIdentifierType.createdBy, qIdentifierType.createdDateTime, qIdentifierType.updatedBy, qIdentifierType.updatedDateTime).values(identifierType.getIdentifierTypeID(), identifierType.getIdentifierTypeName(), identifierType.getDescription(), identifierType.getAppID(), identifierType.getCreatedBy(), identifierType.getCreatedDateTime(), identifierType.getUpdatedBy(), identifierType.getUpdatedDateTime()).execute();
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
			LOG.debug("insertIdentifierType(IdentifierType) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateIdentifierType(final IdentifierType identifierType) throws DataAccessException {
		LOG.info("Started updating data in IdentifierType: " + identifierType);
		try {
			long returnlong = template.update(qIdentifierType, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qIdentifierType.identifierTypeID.eq(identifierType.getIdentifierTypeID())).set(qIdentifierType.identifierTypeName, identifierType.getIdentifierTypeName()).set(qIdentifierType.description, identifierType.getDescription()).set(qIdentifierType.updatedBy, identifierType.getUpdatedBy()).set(qIdentifierType.updatedDateTime, identifierType.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateIdentifierType(IdentifierType) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getIdentifierType()
	 */
	@Override
	public List<IdentifierType> getIdentifierType() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierType() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType).from(qApplication).where(qIdentifierType.appID.eq(qApplication.appID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingIdentifierTypeProjection(qIdentifierType.identifierTypeID, qIdentifierType.identifierTypeName,
					qIdentifierType.description, qIdentifierType.appID, qIdentifierType.createdBy, qIdentifierType.createdDateTime,
					qIdentifierType.updatedBy, qIdentifierType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getIdentifierTypeById(int)
	 */
	@Override
	public IdentifierType getIdentifierTypeById(int identifierTypeId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType).where(qIdentifierType.identifierTypeID.eq(identifierTypeId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingIdentifierTypeProjection(qIdentifierType.identifierTypeID,
					qIdentifierType.identifierTypeName, qIdentifierType.description, qIdentifierType.appID, qIdentifierType.createdBy,
					qIdentifierType.createdDateTime, qIdentifierType.updatedBy, qIdentifierType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getIdentifierIdByName(java
	 * .lang.String)
	 */
	@Override
	public int getIdentifierIdByName(String identifierName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierIdByName(String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType).where(qIdentifierType.identifierTypeName.eq(identifierName));
			LOG.info("generated query : " + sqlQuery);
			Integer identifierId = template.queryForObject(sqlQuery, qIdentifierType.identifierTypeID);
			if (identifierId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getIdentifierIdByName(String) - end"); //$NON-NLS-1$
				}
				return identifierId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierIdByName(String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getIdentifierTypeIdByAppID
	 * (int)
	 */
	@Override
	public int getIdentifierTypeIdByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeIdByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType).where(qIdentifierType.appID.eq(appId));
			LOG.info("IdentifierType query : " + sqlQuery);
			Integer testIdentifierTypeID = template.queryForObject(sqlQuery, qIdentifierType.identifierTypeID);
			if (testIdentifierTypeID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getIdentifierTypeIdByAppID(int) - end"); //$NON-NLS-1$
				}
				return testIdentifierTypeID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeIdByAppID(int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.IdentifierTypeDao#insertIdentifierTypeGetKey
	 * (com.sssoft.isatt.data.pojo.input.IdentifierType)
	 */
	@Override
	public int insertIdentifierTypeGetKey(final IdentifierType identifierType) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertIdentifierTypeGetKey(IdentifierType) - start"); //$NON-NLS-1$
		}

		int identifierTypeId = 0;
		try {
			identifierTypeId = template.insertWithKey(qIdentifierType, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qIdentifierType.identifierTypeID, qIdentifierType.identifierTypeName, qIdentifierType.description, qIdentifierType.appID, qIdentifierType.createdBy, qIdentifierType.createdDateTime, qIdentifierType.updatedBy, qIdentifierType.updatedDateTime).values(identifierType.getIdentifierTypeID(), identifierType.getIdentifierTypeName(), identifierType.getDescription(), identifierType.getAppID(), identifierType.getCreatedBy(), identifierType.getCreatedDateTime(), identifierType.getUpdatedBy(), identifierType.getUpdatedDateTime()).executeWithKey(qIdentifierType.identifierTypeID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated identifierTypeId : " + identifierTypeId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertIdentifierTypeGetKey(IdentifierType) - end"); //$NON-NLS-1$
		}
		return identifierTypeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getIdentifierIdByNameAndAppId
	 * (java.lang.String, int)
	 */
	@Override
	public int getIdentifierIdByNameAndAppId(String identifierTypeName, int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierIdByNameAndAppId(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType)
					.where(qIdentifierType.identifierTypeName.eq(identifierTypeName).and(qIdentifierType.appID.eq(appId)));
			LOG.info("generated query : " + sqlQuery);
			Integer identifierId = template.queryForObject(sqlQuery, qIdentifierType.identifierTypeID);
			if (identifierId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getIdentifierIdByNameAndAppId(String, int) - end"); //$NON-NLS-1$
				}
				return identifierId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierIdByNameAndAppId(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getAllIdentifierTypes()
	 */
	@Override
	public List<IdentifierType> getAllIdentifierTypes() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllIdentifierTypes() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingIdentifierTypeProjection(qIdentifierType.identifierTypeID, qIdentifierType.identifierTypeName,
					qIdentifierType.description, qIdentifierType.appID, qIdentifierType.createdBy, qIdentifierType.createdDateTime,
					qIdentifierType.updatedBy, qIdentifierType.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.IdentifierTypeDao#getIdentifierTypeByAppID(int)
	 */
	@Override
	public List<IdentifierType> getIdentifierTypeByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getIdentifierTypeByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qIdentifierType).where(qIdentifierType.appID.eq(appId));
			LOG.info("IdentifierType query : " + sqlQuery);
			return template.query(sqlQuery, new MappingIdentifierTypeProjection(qIdentifierType.identifierTypeID, qIdentifierType.identifierTypeName,
					qIdentifierType.description, qIdentifierType.appID, qIdentifierType.createdBy, qIdentifierType.createdDateTime,
					qIdentifierType.updatedBy, qIdentifierType.updatedDateTime));

		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

}