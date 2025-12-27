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
import com.sssoft.isatt.data.dao.GenericDataDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QGenericdata;
import com.sssoft.isatt.data.pojo.GenericData;

/**
 * The Class GenericDataDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for GenericDataDao
 * interface
 */
public class GenericDataDaoImpl implements GenericDataDao {

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

	/** The q generic data. */
	private QGenericdata qGenericData = QGenericdata.genericdata;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(GenericDataDaoImpl.class);

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
	 * The Class MappingGenericDataProjection.
	 */
	public class MappingGenericDataProjection extends MappingProjection<GenericData> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping generic data projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingGenericDataProjection(Expression<?>... args) {
			super(GenericData.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected GenericData map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			GenericData genericData = new GenericData();
			genericData.setID(tuple.get(qGenericData.id));
			genericData.setKeyName(tuple.get(qGenericData.keyName));
			genericData.setValue(tuple.get(qGenericData.value));
			genericData.setAppID(tuple.get(qGenericData.appID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning GenericData object : " + genericData);
			}
			return genericData;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param genericData
	 *            the generic data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertGenericData(final GenericData genericData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertGenericData(GenericData) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in GenericData: " + genericData);
		try {
			result = template.insert(qGenericData, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qGenericData.id, qGenericData.keyName, qGenericData.value, qGenericData.appID).values(genericData.getID(), genericData.getKeyName(), genericData.getValue(), genericData.getAppID()).execute();
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
			LOG.debug("insertGenericData(GenericData) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param genericData
	 *            the generic data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateGenericData(final GenericData genericData) throws DataAccessException {
		LOG.info("Started updating data in GenericData: " + genericData);
		try {
			long returnlong = template.update(qGenericData, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qGenericData.id.eq(genericData.getID())).set(qGenericData.keyName, genericData.getKeyName()).set(qGenericData.value, genericData.getValue()).set(qGenericData.appID, genericData.getAppID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateGenericData(GenericData) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.GenericDataDao#getGenericData()
	 */
	@Override
	public List<GenericData> getGenericData() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getGenericData() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qGenericData);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery,
					new MappingGenericDataProjection(qGenericData.id, qGenericData.keyName, qGenericData.value, qGenericData.appID));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.GenericDataDao#getGenericDataById(int)
	 */
	@Override
	public GenericData getGenericDataById(int genericDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getGenericDataById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qGenericData).where(qGenericData.id.eq(genericDataId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingGenericDataProjection(qGenericData.id, qGenericData.keyName, qGenericData.value,
					qGenericData.appID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.GenericDataDao#insertGenericDataGetKey(com.exilant
	 * .tfw.pojo.GenericData)
	 */
	@Override
	public int insertGenericDataGetKey(final GenericData genericData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertGenericDataGetKey(GenericData) - start"); //$NON-NLS-1$
		}

		int genericDataId = 0;
		try {
			genericDataId = template.insertWithKey(qGenericData, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qGenericData.id, qGenericData.keyName, qGenericData.value, qGenericData.appID).values(genericData.getID(), genericData.getKeyName(), genericData.getValue(), genericData.getAppID()).executeWithKey(qGenericData.appID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated genericDataId : " + genericDataId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertGenericDataGetKey(GenericData) - end"); //$NON-NLS-1$
		}
		return genericDataId;
	}

}
