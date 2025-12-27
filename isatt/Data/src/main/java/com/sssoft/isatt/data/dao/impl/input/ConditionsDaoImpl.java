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
import com.sssoft.isatt.data.dao.input.ConditionsDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QConditiongroup;
import com.sssoft.isatt.data.generated.pojo.QConditions;
import com.sssoft.isatt.data.pojo.input.Conditions;

/**
 * The Class ConditionsDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ConditionsDao interface
 */
public class ConditionsDaoImpl implements ConditionsDao {

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

	/** The q conditions. */
	private QConditions qConditions = QConditions.conditions;

	/** The q condition group. */
	private QConditiongroup qConditionGroup = QConditiongroup.conditiongroup;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ConditionsDaoImpl.class);

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
	 * The Class MappingConditionsProjection.
	 */
	public class MappingConditionsProjection extends MappingProjection<Conditions> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping conditions projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingConditionsProjection(Expression<?>... args) {
			super(Conditions.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected Conditions map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			Conditions conditions = new Conditions();
			conditions.setConditionID(tuple.get(qConditions.conditionID));
			conditions.setConditionName(tuple.get(qConditions.conditionName));
			conditions.setDescription(tuple.get(qConditions.description));
			conditions.setExpression(tuple.get(qConditions.expression));
			conditions.setConditionGroupID(tuple.get(qConditions.conditionGroupID));
			conditions.setCreatedBy(tuple.get(qConditions.createdBy));
			conditions.setCreatedDateTime(tuple.get(qConditions.createdDateTime));
			conditions.setUpdatedBy(tuple.get(qConditions.updatedBy));
			conditions.setUpdatedDateTime(tuple.get(qConditions.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Conditions object : " + conditions);
			}
			return conditions;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertConditions(final Conditions conditions) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditions(Conditions) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in Conditions: " + conditions);
		try {
			result = template.insert(qConditions, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qConditions.conditionID, qConditions.conditionName, qConditions.description, qConditions.expression, qConditions.conditionGroupID, qConditions.createdBy, qConditions.createdDateTime, qConditions.updatedBy, qConditions.updatedDateTime).values(conditions.getConditionID(), conditions.getConditionName(), conditions.getDescription(), conditions.getExpression(), conditions.getConditionGroupID(), conditions.getCreatedBy(), conditions.getCreatedDateTime(), conditions.getUpdatedBy(), conditions.getUpdatedDateTime()).execute();
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
			LOG.debug("insertConditions(Conditions) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param conditions
	 *            the conditions
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateConditions(final Conditions conditions) throws DataAccessException {
		LOG.info("Started updating data in Conditions: " + conditions);
		try {
			long returnlong = template.update(qConditions, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qConditions.conditionID.eq(conditions.getConditionID())).set(qConditions.description, conditions.getDescription()).set(qConditions.expression, conditions.getExpression()).set(qConditions.updatedBy, conditions.getUpdatedBy()).set(qConditions.updatedDateTime, conditions.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateConditions(Conditions) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ConditionsDao#getConditions()
	 */
	@Override
	public List<Conditions> getConditions() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditions() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditions).where(qConditions.conditionGroupID.eq(qConditionGroup.conditionGroupID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingConditionsProjection(qConditions.conditionID, qConditions.conditionName, qConditions.description,
					qConditions.expression, qConditions.conditionGroupID, qConditions.createdBy, qConditions.createdDateTime, qConditions.updatedBy,
					qConditions.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ConditionsDao#getConditionsById(int)
	 */
	@Override
	public Conditions getConditionsById(int conditionsId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditions).where(qConditions.conditionID.eq(conditionsId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingConditionsProjection(qConditions.conditionID, qConditions.conditionName,
					qConditions.description, qConditions.expression, qConditions.conditionGroupID, qConditions.createdBy, qConditions.createdDateTime,
					qConditions.updatedBy, qConditions.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionsDao#getConditionByConditionGroupId
	 * (int)
	 */
	@Override
	public List<Conditions> getConditionByConditionGroupId(int conditionGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionByConditionGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditions).where(qConditions.conditionGroupID.eq(conditionGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingConditionsProjection(qConditions.conditionID, qConditions.conditionName, qConditions.description,
					qConditions.expression, qConditions.conditionGroupID, qConditions.createdBy, qConditions.createdDateTime, qConditions.updatedBy,
					qConditions.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionsDao#getConditionsByConditionGroupId
	 * (int)
	 */
	@Override
	public Conditions getConditionsByConditionGroupId(int conditionGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionsByConditionGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditions).where(qConditions.conditionGroupID.eq(conditionGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingConditionsProjection(qConditions.conditionID, qConditions.conditionName,
					qConditions.description, qConditions.expression, qConditions.conditionGroupID, qConditions.createdBy, qConditions.createdDateTime,
					qConditions.updatedBy, qConditions.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.ConditionsDao#
	 * getConditionIdByNameByConditionGroupId(java.lang.String, int)
	 */
	@Override
	public int getConditionIdByNameByConditionGroupId(String conditionName, int conditionGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionIdByNameByConditionGroupId(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditions)
					.where(qConditions.conditionGroupID.eq(conditionGroupId).and(qConditions.conditionName.eq(conditionName)));
			LOG.info("Conditions query : " + sqlQuery);
			Integer conditionID = template.queryForObject(sqlQuery, qConditions.conditionID);
			if (conditionID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getConditionIdByNameByConditionGroupId(String, int) - end"); //$NON-NLS-1$
				}
				return conditionID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionIdByNameByConditionGroupId(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionsDao#insertConditionsGetKey(com.exilant
	 * .tfw.pojo.input.Conditions)
	 */
	@Override
	public int insertConditionsGetKey(final Conditions conditions) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionsGetKey(Conditions) - start"); //$NON-NLS-1$
		}

		int conditionsId = 0;
		try {
			conditionsId = template.insertWithKey(qConditions, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qConditions.conditionID, qConditions.conditionName, qConditions.description, qConditions.expression, qConditions.conditionGroupID, qConditions.createdBy, qConditions.createdDateTime, qConditions.updatedBy, qConditions.updatedDateTime).values(conditions.getConditionID(), conditions.getConditionName(), conditions.getDescription(), conditions.getExpression(), conditions.getConditionGroupID(), conditions.getCreatedBy(), conditions.getCreatedDateTime(), conditions.getUpdatedBy(), conditions.getUpdatedDateTime()).executeWithKey(qConditions.conditionID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated conditionsId : " + conditionsId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionsGetKey(Conditions) - end"); //$NON-NLS-1$
		}
		return conditionsId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionsDao#updateConditionsData(com.exilant
	 * .tfw.pojo.input.Conditions)
	 */
	@Override
	public long updateConditionsData(final Conditions conditions) throws DataAccessException {
		LOG.info("Started updating data in Conditions: " + conditions);
		try {
			long returnlong = template.update(qConditions, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qConditions.conditionID.eq(conditions.getConditionID())).set(qConditions.conditionName, conditions.getConditionName()).set(qConditions.description, conditions.getDescription()).set(qConditions.expression, conditions.getExpression()).set(qConditions.conditionGroupID, conditions.getConditionGroupID()).set(qConditions.updatedBy, conditions.getUpdatedBy()).set(qConditions.updatedDateTime, conditions.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateConditionsData(Conditions) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

}