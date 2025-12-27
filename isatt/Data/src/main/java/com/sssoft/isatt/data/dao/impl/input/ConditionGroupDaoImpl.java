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
import com.sssoft.isatt.data.dao.input.ConditionGroupDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.generated.pojo.QConditiongroup;
import com.sssoft.isatt.data.pojo.input.ConditionGroup;

/**
 * The Class ConditionGroupDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for ConditionGroupDao
 * interface
 */
public class ConditionGroupDaoImpl implements ConditionGroupDao {

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

	/** The q condition group. */
	private QConditiongroup qConditionGroup = QConditiongroup.conditiongroup;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ConditionGroupDaoImpl.class);

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
	 * The Class MappingConditionGroupProjection.
	 */
	public class MappingConditionGroupProjection extends MappingProjection<ConditionGroup> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping condition group projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingConditionGroupProjection(Expression<?>... args) {
			super(ConditionGroup.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected ConditionGroup map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			ConditionGroup conditionGroup = new ConditionGroup();
			conditionGroup.setConditionGroupID(tuple.get(qConditionGroup.conditionGroupID));
			conditionGroup.setConditionGroupName(tuple.get(qConditionGroup.conditionGroupName));
			conditionGroup.setDescription(tuple.get(qConditionGroup.description));
			conditionGroup.setAppID(tuple.get(qConditionGroup.appID));
			conditionGroup.setCreatedBy(tuple.get(qConditionGroup.createdBy));
			conditionGroup.setCreatedDateTime(tuple.get(qConditionGroup.createdDateTime));
			conditionGroup.setUpdatedBy(tuple.get(qConditionGroup.updatedBy));
			conditionGroup.setUpdatedDateTime(tuple.get(qConditionGroup.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning ConditionGroup object : " + conditionGroup);
			}
			return conditionGroup;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertConditionGroup(final ConditionGroup conditionGroup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionGroup(ConditionGroup) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in ConditionGroup: " + conditionGroup);
		try {
			result = template.insert(qConditionGroup, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qConditionGroup.conditionGroupID, qConditionGroup.conditionGroupName, qConditionGroup.description, qConditionGroup.appID, qConditionGroup.createdBy, qConditionGroup.createdDateTime, qConditionGroup.updatedBy, qConditionGroup.updatedDateTime).values(conditionGroup.getConditionGroupID(), conditionGroup.getConditionGroupName(), conditionGroup.getDescription(), conditionGroup.getAppID(), conditionGroup.getCreatedBy(), conditionGroup.getCreatedDateTime(), conditionGroup.getUpdatedBy(), conditionGroup.getUpdatedDateTime()).execute();
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
			LOG.debug("insertConditionGroup(ConditionGroup) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param conditionGroup
	 *            the condition group
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateConditionGroup(final ConditionGroup conditionGroup) throws DataAccessException {
		LOG.info("Started updating data in ConditionGroup: " + conditionGroup);
		try {
			long returnlong = template.update(qConditionGroup, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qConditionGroup.conditionGroupID.eq(conditionGroup.getConditionGroupID())).set(qConditionGroup.description, conditionGroup.getDescription()).set(qConditionGroup.updatedBy, conditionGroup.getUpdatedBy()).set(qConditionGroup.updatedDateTime, conditionGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateConditionGroup(ConditionGroup) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.ConditionGroupDao#getConditionGroup()
	 */
	@Override
	public List<ConditionGroup> getConditionGroup() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroup() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditionGroup).where(qConditionGroup.appID.eq(qApplication.appID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingConditionGroupProjection(qConditionGroup.conditionGroupID, qConditionGroup.conditionGroupName,
					qConditionGroup.description, qConditionGroup.appID, qConditionGroup.createdBy, qConditionGroup.createdDateTime,
					qConditionGroup.updatedBy, qConditionGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionGroupDao#getConditionGroupById(int)
	 */
	@Override
	public ConditionGroup getConditionGroupById(int conditionGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditionGroup).where(qConditionGroup.conditionGroupID.eq(conditionGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingConditionGroupProjection(qConditionGroup.conditionGroupID,
					qConditionGroup.conditionGroupName, qConditionGroup.description, qConditionGroup.appID, qConditionGroup.createdBy,
					qConditionGroup.createdDateTime, qConditionGroup.updatedBy, qConditionGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionGroupDao#getConditionGroupIdByNameByAppId
	 * (int, java.lang.String)
	 */
	@Override
	public int getConditionGroupIdByNameByAppId(int appId, String cgName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupIdByNameByAppId(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditionGroup)
					.where(qConditionGroup.appID.eq(appId).and(qConditionGroup.conditionGroupName.eq(cgName)));
			LOG.info("Condition Group query : " + sqlQuery);
			Integer conditionGrpId = template.queryForObject(sqlQuery, qConditionGroup.conditionGroupID);
			if (conditionGrpId != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getConditionGroupIdByNameByAppId(int, String) - end"); //$NON-NLS-1$
				}
				return conditionGrpId;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupIdByNameByAppId(int, String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionGroupDao#insertConditionGroupGetKey
	 * (com.sssoft.isatt.data.pojo.input.ConditionGroup)
	 */
	@Override
	public int insertConditionGroupGetKey(final ConditionGroup conditionGroup) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionGroupGetKey(ConditionGroup) - start"); //$NON-NLS-1$
		}

		int conditionGroupId = 0;
		try {
			conditionGroupId = template.insertWithKey(qConditionGroup, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qConditionGroup.conditionGroupID, qConditionGroup.conditionGroupName, qConditionGroup.description, qConditionGroup.appID, qConditionGroup.createdBy, qConditionGroup.createdDateTime, qConditionGroup.updatedBy, qConditionGroup.updatedDateTime).values(conditionGroup.getConditionGroupID(), conditionGroup.getConditionGroupName(), conditionGroup.getDescription(), conditionGroup.getAppID(), conditionGroup.getCreatedBy(), conditionGroup.getCreatedDateTime(), conditionGroup.getUpdatedBy(), conditionGroup.getUpdatedDateTime()).executeWithKey(qConditionGroup.conditionGroupID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated conditionGroupId : " + conditionGroupId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertConditionGroupGetKey(ConditionGroup) - end"); //$NON-NLS-1$
		}
		return conditionGroupId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionGroupDao#getConditionGroupNamesByAppId
	 * (int)
	 */
	@Override
	public List<ConditionGroup> getConditionGroupNamesByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupNamesByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qConditionGroup).where(qConditionGroup.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingConditionGroupProjection(qConditionGroup.conditionGroupID, qConditionGroup.conditionGroupName,
					qConditionGroup.description, qConditionGroup.appID, qConditionGroup.createdBy, qConditionGroup.createdDateTime,
					qConditionGroup.updatedBy, qConditionGroup.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.ConditionGroupDao#updateConditionGroupData(
	 * com.sssoft.isatt.data.pojo.input.ConditionGroup)
	 */
	@Override
	public long updateConditionGroupData(final ConditionGroup conditionGroup) throws DataAccessException {
		LOG.info("Started updating data in ConditionGroup: " + conditionGroup);
		try {
			long returnlong = template.update(qConditionGroup, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qConditionGroup.conditionGroupID.eq(conditionGroup.getConditionGroupID())).set(qConditionGroup.conditionGroupName, conditionGroup.getConditionGroupName()).set(qConditionGroup.description, conditionGroup.getDescription()).set(qConditionGroup.updatedBy, conditionGroup.getUpdatedBy()).set(qConditionGroup.updatedDateTime, conditionGroup.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateConditionGroupData(ConditionGroup) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}
}