package com.sssoft.isatt.data.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;
import com.sssoft.isatt.data.dao.input.PlanSuiteMappingDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QPlansuitemapping;
import com.sssoft.isatt.data.generated.pojo.QTestplan;
import com.sssoft.isatt.data.generated.pojo.QTestsuite;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestSuite;

/**
 * The Class PlanSuiteMappingDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for PlanSuiteMappingDao
 * interface
 */
public class PlanSuiteMappingDaoImpl implements PlanSuiteMappingDao {

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

	/** The q plan suite mapping. */
	private QPlansuitemapping qPlanSuiteMapping = QPlansuitemapping.plansuitemapping;

	/** The q test suite. */
	private QTestsuite qTestSuite = QTestsuite.testsuite;

	/** The q test plan. */
	private QTestplan qTestPlan = QTestplan.testplan;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(PlanSuiteMappingDaoImpl.class);

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
	 * The Class PlanSuiteMappingProjection.
	 */
	private class PlanSuiteMappingProjection extends MappingProjection<PlanSuiteMapping> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new plan suite mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public PlanSuiteMappingProjection(Expression<?>... args) {
			super(PlanSuiteMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected PlanSuiteMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
			planSuiteMapping.setPlanSuiteMappingID(tuple.get(qPlanSuiteMapping.planSuiteMappingID));
			planSuiteMapping.setTestPlanID(tuple.get(qPlanSuiteMapping.testPlanID));
			planSuiteMapping.setTestSuiteID(tuple.get(qPlanSuiteMapping.testSuiteID));
			planSuiteMapping.setCreatedBy(tuple.get(qPlanSuiteMapping.createdBy));
			planSuiteMapping.setCreatedDateTime(tuple.get(qPlanSuiteMapping.createdDateTime));
			planSuiteMapping.setUpdatedBy(tuple.get(qPlanSuiteMapping.updatedBy));
			planSuiteMapping.setUpdatedDateTime(tuple.get(qPlanSuiteMapping.updatedDateTime));

			TestSuite testSuite = new TestSuite();
			testSuite.setTestSuiteID(tuple.get(qTestSuite.testSuiteID));
			testSuite.setSuiteName(tuple.get(qTestSuite.suiteName));
			testSuite.setDescription(tuple.get(qTestSuite.description));
			testSuite.setAppID(tuple.get(qTestSuite.appID));
			testSuite.setSortOrder(tuple.get(qTestSuite.sortOrder));
			testSuite.setCreatedBy(tuple.get(qTestSuite.createdBy));
			testSuite.setCreatedDateTime(tuple.get(qTestSuite.createdDateTime));
			testSuite.setUpdatedBy(tuple.get(qTestSuite.updatedBy));
			testSuite.setUpdatedDateTime(tuple.get(qTestSuite.updatedDateTime));
			planSuiteMapping.setTestSuite(testSuite);

			TestPlan testPlan = new TestPlan();
			testPlan.setTestPlanID(tuple.get(qTestPlan.testPlanID));
			testPlan.setPlanName(tuple.get(qTestPlan.planName));
			testPlan.setDescription(tuple.get(qTestPlan.description));
			testPlan.setAppID(tuple.get(qTestPlan.appID));
			testPlan.setPreConditionGroupID(tuple.get(qTestPlan.preConditionGroupID));
			testPlan.setPostConditionGroupID(tuple.get(qTestPlan.postConditionGroupID));
			testPlan.setCreatedBy(tuple.get(qTestPlan.createdBy));
			testPlan.setCreatedDateTime(tuple.get(qTestPlan.createdDateTime));
			testPlan.setUpdatedBy(tuple.get(qTestPlan.updatedBy));
			testPlan.setUpdatedDateTime(tuple.get(qTestPlan.updatedDateTime));
			planSuiteMapping.setTestPlan(testPlan);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning PlanSuiteMapping object : " + planSuiteMapping);
			}
			return planSuiteMapping;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.PlanSuiteMappingDao#updatePlanSuiteMapping(
	 * com.sssoft.isatt.data.pojo.input.PlanSuiteMapping)
	 */
	@Override
	public long updatePlanSuiteMapping(final PlanSuiteMapping planSuiteMapping) throws DataAccessException {
		LOG.info("Started updating data in SuiteScenarioMapping: " + planSuiteMapping);
		try {
			long returnlong = template.update(qPlanSuiteMapping, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qPlanSuiteMapping.planSuiteMappingID.eq(planSuiteMapping.getPlanSuiteMappingID())).set(qPlanSuiteMapping.testPlanID, planSuiteMapping.getTestPlanID()).set(qPlanSuiteMapping.testSuiteID, planSuiteMapping.getTestSuiteID()).set(qPlanSuiteMapping.updatedBy, planSuiteMapping.getUpdatedBy()).set(qPlanSuiteMapping.updatedDateTime, planSuiteMapping.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updatePlanSuiteMapping(PlanSuiteMapping) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param planSuiteMapping
	 *            the plan suite mapping
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public int insertPlanSuiteMappingDaoGetKey(final PlanSuiteMapping planSuiteMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertPlanSuiteMappingDaoGetKey(PlanSuiteMapping) - start"); //$NON-NLS-1$
		}

		int planSuiteMappingDaoId = 0;
		try {
			planSuiteMappingDaoId = template.insertWithKey(qPlanSuiteMapping, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qPlanSuiteMapping.planSuiteMappingID, qPlanSuiteMapping.testPlanID, qPlanSuiteMapping.testSuiteID, qPlanSuiteMapping.createdBy, qPlanSuiteMapping.createdDateTime, qPlanSuiteMapping.updatedBy, qPlanSuiteMapping.updatedDateTime).values(planSuiteMapping.getPlanSuiteMappingID(), planSuiteMapping.getTestPlanID(), planSuiteMapping.getTestSuiteID(), planSuiteMapping.getCreatedBy(), planSuiteMapping.getCreatedDateTime(), planSuiteMapping.getUpdatedBy(), planSuiteMapping.getUpdatedDateTime()).executeWithKey(qPlanSuiteMapping.planSuiteMappingID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Generated planSuiteMappingDaoId : " + planSuiteMappingDaoId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertPlanSuiteMappingDaoGetKey(PlanSuiteMapping) - end"); //$NON-NLS-1$
		}
		return planSuiteMappingDaoId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.PlanSuiteMappingDao#getPlanSuiteIdByPlanIdBySuiteId
	 * (int, int)
	 */
	@Override
	public int getPlanSuiteIdByPlanIdBySuiteId(int planId, int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanSuiteIdByPlanIdBySuiteId(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qPlanSuiteMapping)
					.where((qPlanSuiteMapping.testPlanID.eq(planId)).and(qPlanSuiteMapping.testSuiteID.eq(suiteId)));
			Integer planSuiteMappingID = template.queryForObject(sqlQuery, qPlanSuiteMapping.planSuiteMappingID);
			if (planSuiteMappingID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getPlanSuiteIdByPlanIdBySuiteId(int, int) - end"); //$NON-NLS-1$
				}
				return planSuiteMappingID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanSuiteIdByPlanIdBySuiteId(int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.PlanSuiteMappingDao#getPlanSuiteMappingByPlanId
	 * (int)
	 */
	@Override
	public List<PlanSuiteMapping> getPlanSuiteMappingByPlanId(int planId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanSuiteMappingByPlanId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qPlanSuiteMapping)
				.from(qTestPlan)
				.from(qTestSuite)
				.where(qPlanSuiteMapping.testPlanID.eq(planId).and(qPlanSuiteMapping.testPlanID.eq(qTestPlan.testPlanID))
						.and(qPlanSuiteMapping.testSuiteID.eq(qTestSuite.testSuiteID))).orderBy(qPlanSuiteMapping.planSuiteMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new PlanSuiteMappingProjection(qPlanSuiteMapping.planSuiteMappingID, qPlanSuiteMapping.testPlanID,
				qPlanSuiteMapping.testSuiteID, qPlanSuiteMapping.createdBy, qPlanSuiteMapping.createdDateTime, qPlanSuiteMapping.updatedBy,
				qPlanSuiteMapping.updatedDateTime, qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description, qTestPlan.appID,
				qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime, qTestPlan.updatedBy,
				qTestPlan.updatedDateTime, qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.sortOrder,
				qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime));
	}

	@Override
	public List<PlanSuiteMapping> getPlanSuiteMappingBySuiteId(int suiteId)
			throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanSuiteMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qPlanSuiteMapping)
				.from(qTestPlan)
				.from(qTestSuite)
				.where(qPlanSuiteMapping.testSuiteID.eq(suiteId).and(qPlanSuiteMapping.testPlanID.eq(qTestPlan.testPlanID))
						.and(qPlanSuiteMapping.testSuiteID.eq(qTestSuite.testSuiteID))).orderBy(qPlanSuiteMapping.planSuiteMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new PlanSuiteMappingProjection(qPlanSuiteMapping.planSuiteMappingID, qPlanSuiteMapping.testPlanID,
				qPlanSuiteMapping.testSuiteID, qPlanSuiteMapping.createdBy, qPlanSuiteMapping.createdDateTime, qPlanSuiteMapping.updatedBy,
				qPlanSuiteMapping.updatedDateTime, qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description, qTestPlan.appID,
				qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime, qTestPlan.updatedBy,
				qTestPlan.updatedDateTime, qTestSuite.testSuiteID, qTestSuite.suiteName, qTestSuite.description, qTestSuite.appID, qTestSuite.sortOrder,
				qTestSuite.createdBy, qTestSuite.createdDateTime, qTestSuite.updatedBy, qTestSuite.updatedDateTime));
	}

	@Override
	public long deletePlanSuiteMappingById(final int planId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deletePlanSuiteMappingById(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		try {
			result = template.delete(qPlanSuiteMapping, new SqlDeleteCallback() {
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qPlanSuiteMapping.testPlanID.eq(planId)).execute();

					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
		} catch (Exception e) {
			LOG.error("deletePlanSuiteMappingById(int)", e); //$NON-NLS-1$

			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deletePlanSuiteMappingById(int) - end"); //$NON-NLS-1$
		}
		return result;
	}
	
	@Override
	public long deletePlanSuiteMappingBySuiteId(final int suiteId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deletePlanSuiteMappingBySuiteId(int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		try {
			result = template.delete(qPlanSuiteMapping, new SqlDeleteCallback() {
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qPlanSuiteMapping.testSuiteID.eq(suiteId)).execute();

					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
		} catch (Exception e) {
			LOG.error("deletePlanSuiteMappingBySuiteId(int)", e); //$NON-NLS-1$

			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deletePlanSuiteMappingBySuiteId(int) - end"); //$NON-NLS-1$
		}
		return result;
	}
}