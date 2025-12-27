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
import com.sssoft.isatt.data.dao.input.TestPlanDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QTestplan;
import com.sssoft.isatt.data.pojo.input.TestPlan;

/**
 * The Class TestPlanDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestPlanDao interface
 */
public class TestPlanDaoImpl implements TestPlanDao {

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

	/** The q test plan. */
	private QTestplan qTestPlan = QTestplan.testplan;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestPlanDaoImpl.class);

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
	 * The Class MappingTestPlanProjection.
	 */
	public class MappingTestPlanProjection extends MappingProjection<TestPlan> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test plan projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestPlanProjection(Expression<?>... args) {
			super(TestPlan.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestPlan map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

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
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Conditions object : " + testPlan);
			}
			return testPlan;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestPlan(final TestPlan testPlan) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlan(TestPlan) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestPlan: " + testPlan);
		try {
			result = template.insert(qTestPlan, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description, qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime, qTestPlan.updatedBy, qTestPlan.updatedDateTime).values(testPlan.getTestPlanID(), testPlan.getPlanName(), testPlan.getDescription(), testPlan.getAppID(), testPlan.getPreConditionGroupID(), testPlan.getPostConditionGroupID(), testPlan.getCreatedBy(), testPlan.getCreatedDateTime(), testPlan.getUpdatedBy(), testPlan.getUpdatedDateTime()).execute();
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
			LOG.debug("insertTestPlan(TestPlan) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testPlan
	 *            the test plan
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestPlanForExcel(final TestPlan testPlan) throws DataAccessException {
		LOG.info("Started updating data in TestPlan: " + testPlan);
		try {
			long returnlong = template.update(qTestPlan, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestPlan.testPlanID.eq(testPlan.getTestPlanID())).set(qTestPlan.description, testPlan.getDescription()).set(qTestPlan.preConditionGroupID, testPlan.getPreConditionGroupID()).set(qTestPlan.postConditionGroupID, testPlan.getPostConditionGroupID()).set(qTestPlan.updatedBy, testPlan.getUpdatedBy()).set(qTestPlan.updatedDateTime, testPlan.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestPlanForExcel(TestPlan) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlan()
	 */
	@Override
	public List<TestPlan> getTestPlan() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlan() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestPlanProjection(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description,
					qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime,
					qTestPlan.updatedBy, qTestPlan.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlanById(int)
	 */
	@Override
	public TestPlan getTestPlanById(int testPlanId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.testPlanID.eq(testPlanId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestPlanProjection(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description,
					qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime,
					qTestPlan.updatedBy, qTestPlan.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	@Override
	public TestPlan getTestPlanByName(String testPlanName) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.planName.eq(testPlanName));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestPlanProjection(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description,
					qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime,
					qTestPlan.updatedBy, qTestPlan.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlanByAppId(int)
	 */
	@Override
	public int getTestPlanByAppId(int appID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.appID.eq(appID));
			LOG.info("generated query : " + sqlQuery);
			Integer testPlanID = template.queryForObject(sqlQuery, qTestPlan.testPlanID);
			if (testPlanID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestPlanByAppId(int) - end"); //$NON-NLS-1$
				}
				return testPlanID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByAppId(int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlanByNameAndAppId(java.
	 * lang.String, int)
	 */
	@Override
	public int getTestPlanByNameAndAppId(String planName, int appID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByNameAndAppId(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.planName.eq(planName).and(qTestPlan.appID.eq(appID)));
			LOG.info("generated query : " + sqlQuery);
			Integer testPlanID = template.queryForObject(sqlQuery, qTestPlan.testPlanID);
			if (testPlanID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestPlanByNameAndAppId(String, int) - end"); //$NON-NLS-1$
				}
				return testPlanID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanByNameAndAppId(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestPlanDao#insertTestPlanGetKey(com.exilant
	 * .tfw.pojo.input.TestPlan)
	 */
	@Override
	public int insertTestPlanGetKey(final TestPlan testPlan) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanGetKey(TestPlan) - start"); //$NON-NLS-1$
		}

		int testPlanId = 0;
		try {
			testPlanId = template.insertWithKey(qTestPlan, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description, qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime, qTestPlan.updatedBy, qTestPlan.updatedDateTime).values(testPlan.getTestPlanID(), testPlan.getPlanName(), testPlan.getDescription(), testPlan.getAppID(), testPlan.getPreConditionGroupID(), testPlan.getPostConditionGroupID(), testPlan.getCreatedBy(), testPlan.getCreatedDateTime(), testPlan.getUpdatedBy(), testPlan.getUpdatedDateTime()).executeWithKey(qTestPlan.testPlanID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testPlanId : " + testPlanId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestPlanGetKey(TestPlan) - end"); //$NON-NLS-1$
		}
		return testPlanId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestPlanDao#getAllTestPlans()
	 */
	@Override
	public List<TestPlan> getAllTestPlans() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestPlans() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan);
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestPlanProjection(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description,
					qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime,
					qTestPlan.updatedBy, qTestPlan.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlanNameFilterByAppId(int)
	 */
	@Override
	public List<TestPlan> getTestPlanNameFilterByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanNameFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.appID.eq(appId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestPlanProjection(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description,
					qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime,
					qTestPlan.updatedBy, qTestPlan.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestPlanDao#updateTestPlan(com.exilant.tfw.
	 * pojo.input.TestPlan)
	 */
	@Override
	public long updateTestPlan(final TestPlan testPlan) throws DataAccessException {

		LOG.info("Started updating data in TestPlan: " + testPlan);
		try {
			long returnlong = template.update(qTestPlan, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestPlan.testPlanID.eq(testPlan.getTestPlanID())).set(qTestPlan.description, testPlan.getDescription()).set(qTestPlan.updatedBy, testPlan.getUpdatedBy()).set(qTestPlan.updatedDateTime, testPlan.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestPlan(TestPlan) - end"); //$NON-NLS-1$
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
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestPlanDao#updateTestPlanData(com.exilant.
	 * tfw.pojo.input.TestPlan)
	 */
	@Override
	public long updateTestPlanData(final TestPlan testPlan) throws DataAccessException {

		LOG.info("Started updating data in TestPlan: " + testPlan);
		try {
			long returnlong = template.update(qTestPlan, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestPlan.testPlanID.eq(testPlan.getTestPlanID())).set(qTestPlan.planName, testPlan.getPlanName()).set(qTestPlan.description, testPlan.getDescription()).set(qTestPlan.updatedBy, testPlan.getUpdatedBy()).set(qTestPlan.updatedDateTime, testPlan.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestPlanData(TestPlan) - end"); //$NON-NLS-1$
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
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlanNamesByTestPlanId(int)
	 */
	@Override
	public List<TestPlan> getTestPlanNamesByTestPlanId(int planId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanNamesByTestPlanId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.testPlanID.eq(planId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestPlanProjection(qTestPlan.planName));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestPlanDao#getPlanNameById(int)
	 */
	public String getPlanNameById(int testPlanId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlanNameById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.testPlanID.eq(testPlanId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.queryForObject(sqlQuery, qTestPlan.planName);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestPlanDao#getTestPlanObjByAppId(int)
	 */
	@Override
	public List<TestPlan> getTestPlanObjByAppId(int appID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestPlanObjByAppId(int) - start"); //$NON-NLS-1$
		}

		// List<TestPlan> testPlan = new ArrayList<TestPlan>();
		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestPlan).where(qTestPlan.appID.eq(appID));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestPlanProjection(qTestPlan.testPlanID, qTestPlan.planName, qTestPlan.description,
					qTestPlan.appID, qTestPlan.preConditionGroupID, qTestPlan.postConditionGroupID, qTestPlan.createdBy, qTestPlan.createdDateTime,
					qTestPlan.updatedBy, qTestPlan.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
		// testPlan;
	}

}