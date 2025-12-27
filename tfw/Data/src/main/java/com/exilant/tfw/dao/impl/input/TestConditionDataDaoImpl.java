package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.TestConditionDataDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QConditiongroup;
import com.exilant.tfw.generated.pojo.QConditions;
import com.exilant.tfw.generated.pojo.QTestconditiondata;
import com.exilant.tfw.generated.pojo.QTestdata;
import com.exilant.tfw.pojo.input.TestConditionData;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestConditionDataDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestConditionDataDao
 * interface
 */
public class TestConditionDataDaoImpl implements TestConditionDataDao {

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

	/** The q test condition data. */
	private QTestconditiondata qTestConditionData = QTestconditiondata.testconditiondata;

	/** The q test data. */
	private QTestdata qTestData = QTestdata.testdata;

	/** The q condition group. */
	private QConditiongroup qConditionGroup = QConditiongroup.conditiongroup;

	/** The q conditions. */
	private QConditions qConditions = QConditions.conditions;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestConditionDataDaoImpl.class);

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
	 * The Class MappingTestConditionDataProjection.
	 */
	public class MappingTestConditionDataProjection extends MappingProjection<TestConditionData> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test condition data projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestConditionDataProjection(Expression<?>... args) {
			super(TestConditionData.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestConditionData map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestConditionData testConditionData = new TestConditionData();
			testConditionData.setTestConditionDataID(tuple.get(qTestConditionData.testConditionDataID));
			testConditionData.setTestDataID(tuple.get(qTestConditionData.testDataID));
			testConditionData.setConditionGroupID(tuple.get(qTestConditionData.conditionGroupID));
			testConditionData.setConditionID(tuple.get(qTestConditionData.conditionID));
			testConditionData.setConditionValue(tuple.get(qTestConditionData.conditionValue));
			testConditionData.setCreatedBy(tuple.get(qTestConditionData.createdBy));
			testConditionData.setCreatedDateTime(tuple.get(qTestConditionData.createdDateTime));
			testConditionData.setUpdatedBy(tuple.get(qTestConditionData.updatedBy));
			testConditionData.setUpdatedDateTime(tuple.get(qTestConditionData.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestConditionData object : " + testConditionData);
			}
			return testConditionData;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testConditionData
	 *            the test condition data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestConditionData(final TestConditionData testConditionData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestConditionData(TestConditionData) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestConditionData: " + testConditionData);
		try {
			result = template.insert(qTestConditionData, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestConditionData.testConditionDataID, qTestConditionData.testDataID, qTestConditionData.conditionGroupID, qTestConditionData.conditionID, qTestConditionData.conditionValue, qTestConditionData.createdBy, qTestConditionData.createdDateTime, qTestConditionData.updatedBy, qTestConditionData.updatedDateTime).values(testConditionData.getTestConditionDataID(), testConditionData.getTestDataID(), testConditionData.getConditionGroupID(), testConditionData.getConditionID(), testConditionData.getConditionValue(), testConditionData.getCreatedBy(), testConditionData.getCreatedDateTime(), testConditionData.getUpdatedBy(), testConditionData.getUpdatedDateTime()).execute();
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
			LOG.debug("insertTestConditionData(TestConditionData) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testConditionData
	 *            the test condition data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestConditionData(final TestConditionData testConditionData) throws DataAccessException {
		LOG.info("Started updating data in TestConditionData: " + testConditionData);
		try {
			long returnlong = template.update(qTestConditionData, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestConditionData.testConditionDataID.eq(testConditionData.getTestConditionDataID())).set(qTestConditionData.conditionValue, testConditionData.getConditionValue()).set(qTestConditionData.updatedBy, testConditionData.getUpdatedBy()).set(qTestConditionData.updatedDateTime, testConditionData.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestConditionData(TestConditionData) - end"); //$NON-NLS-1$
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
	 * com.exilant.tfw.dao.input.TestConditionDataDao#getTestConditionData()
	 */
	@Override
	public List<TestConditionData> getTestConditionData() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestConditionData() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qTestConditionData)
					.where(qTestConditionData.testDataID.eq(qTestData.testDataID).and(
							qTestConditionData.conditionGroupID.eq(qConditionGroup.conditionGroupID).and(
									qTestConditionData.conditionID.eq(qConditions.conditionID))));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestConditionDataProjection(qTestConditionData.testConditionDataID, qTestConditionData.testDataID,
					qTestConditionData.conditionGroupID, qTestConditionData.conditionID, qTestConditionData.conditionValue, qTestConditionData.createdBy,
					qTestConditionData.createdDateTime, qTestConditionData.updatedBy, qTestConditionData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestConditionDataDao#getTestConditionDataById
	 * (int)
	 */
	@Override
	public TestConditionData getTestConditionDataById(int testCondtionId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestConditionDataById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestConditionData).where(qTestConditionData.testConditionDataID.eq(testCondtionId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestConditionDataProjection(qTestConditionData.testConditionDataID,
					qTestConditionData.testDataID, qTestConditionData.conditionGroupID, qTestConditionData.conditionID, qTestConditionData.conditionValue,
					qTestConditionData.createdBy, qTestConditionData.createdDateTime, qTestConditionData.updatedBy, qTestConditionData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestConditionDataDao#
	 * getTestConditionDataByConditionGroupId(int)
	 */
	@Override
	public List<TestConditionData> getTestConditionDataByConditionGroupId(int conditionGroupId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestConditionDataByConditionGroupId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestConditionData).where(qTestConditionData.conditionGroupID.eq(conditionGroupId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestConditionDataProjection(qTestConditionData.testConditionDataID, qTestConditionData.testDataID,
					qTestConditionData.conditionGroupID, qTestConditionData.conditionID, qTestConditionData.conditionValue, qTestConditionData.createdBy,
					qTestConditionData.createdDateTime, qTestConditionData.updatedBy, qTestConditionData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestConditionDataDao#
	 * getTestConditionIdByDependentIds(int, int)
	 */
	@Override
	public int getTestConditionIdByDependentIds(int testDataId, int conditionId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestConditionIdByDependentIds(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestConditionData)
					.where((qTestConditionData.testDataID.eq(testDataId)).and(qTestConditionData.conditionID.eq(conditionId)));
			LOG.info("Conditions query : " + sqlQuery);
			Integer conditionID = template.queryForObject(sqlQuery, qTestConditionData.testConditionDataID);
			if (conditionID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestConditionIdByDependentIds(int, int) - end"); //$NON-NLS-1$
				}
				return conditionID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestConditionIdByDependentIds(int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestConditionDataDao#insertTestConditionDataGetKey
	 * (com.exilant.tfw.pojo.input.TestConditionData)
	 */
	@Override
	public int insertTestConditionDataGetKey(final TestConditionData testConditionData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestConditionDataGetKey(TestConditionData) - start"); //$NON-NLS-1$
		}

		int testConditionDataId = 0;
		try {
			testConditionDataId = template.insertWithKey(qTestConditionData, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestConditionData.testConditionDataID, qTestConditionData.testDataID, qTestConditionData.conditionGroupID, qTestConditionData.conditionID, qTestConditionData.conditionValue, qTestConditionData.createdBy, qTestConditionData.createdDateTime, qTestConditionData.updatedBy, qTestConditionData.updatedDateTime).values(testConditionData.getTestConditionDataID(), testConditionData.getTestDataID(), testConditionData.getConditionGroupID(), testConditionData.getConditionID(), testConditionData.getConditionValue(), testConditionData.getCreatedBy(), testConditionData.getCreatedDateTime(), testConditionData.getUpdatedBy(), testConditionData.getUpdatedDateTime()).executeWithKey(qTestConditionData.testConditionDataID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testConditionDataId : " + testConditionDataId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestConditionDataGetKey(TestConditionData) - end"); //$NON-NLS-1$
		}
		return testConditionDataId;
	}

}