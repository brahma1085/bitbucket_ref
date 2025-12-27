package com.sssoft.isatt.data.dao.impl.input;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.sssoft.isatt.data.dao.input.TestDataDao;
import com.sssoft.isatt.data.dao.input.TestParamDataDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QApplication;
import com.sssoft.isatt.data.generated.pojo.QTestdata;
import com.sssoft.isatt.data.pojo.input.TestData;
import com.sssoft.isatt.data.pojo.input.TestParamData;

/**
 * The Class TestDataDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestDataDao interface
 */
public class TestDataDaoImpl implements TestDataDao {

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

	/** The q test data. */
	private QTestdata qTestData = QTestdata.testdata;

	/** The q application. */
	private QApplication qApplication = QApplication.application;

	/** The test param data dao. */
	private TestParamDataDao testParamDataDao;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestDataDaoImpl.class);

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
	 * Sets the test param data dao.
	 * 
	 * @param testParamDataDao
	 *            the new test param data dao
	 */
	public void setTestParamDataDao(TestParamDataDao testParamDataDao) {
		this.testParamDataDao = testParamDataDao;
	}

	/**
	 * The Class MappingTestDataProjection.
	 */
	public class MappingTestDataProjection extends MappingProjection<TestData> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test data projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestDataProjection(Expression<?>... args) {
			super(TestData.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestData map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestData testData = new TestData();
			testData.setTestDataID(tuple.get(qTestData.testDataID));
			testData.setAppID(tuple.get(qTestData.appID));
			testData.setTestDataDescription(tuple.get(qTestData.testDataDescription));
			testData.setStatus(tuple.get(qTestData.status));
			testData.setCreatedBy(tuple.get(qTestData.createdBy));
			testData.setCreatedDateTime(tuple.get(qTestData.createdDateTime));
			testData.setUpdatedBy(tuple.get(qTestData.updatedBy));
			testData.setUpdatedDateTime(tuple.get(qTestData.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Conditions object : " + testData);
			}
			return testData;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testData
	 *            the test data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestData(final TestData testData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestData(TestData) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestData: " + testData);
		try {
			result = template.insert(qTestData, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qTestData.testDataID, qTestData.appID, qTestData.testDataDescription, qTestData.status, qTestData.createdBy,
									qTestData.createdDateTime, qTestData.updatedBy, qTestData.updatedDateTime)
							.values(testData.getTestDataID(), testData.getAppID(), testData.getTestDataDescription(), testData.getStatus(),
									testData.getCreatedBy(), testData.getCreatedDateTime(), testData.getUpdatedBy(), testData.getUpdatedDateTime())
							.execute();
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
			LOG.debug("insertTestData(TestData) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testData
	 *            the test data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestData(final TestData testData) throws DataAccessException {
		LOG.info("Started updating data in TestData: " + testData);
		try {
			long returnlong = template.update(qTestData, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestData.testDataID.eq(testData.getTestDataID()))
							.set(qTestData.testDataDescription, testData.getTestDataDescription()).set(qTestData.status, testData.getStatus())
							.set(qTestData.updatedBy, testData.getUpdatedBy()).set(qTestData.updatedDateTime, testData.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestData(TestData) - end"); //$NON-NLS-1$
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
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getTestData()
	 */
	@Override
	public List<TestData> getTestData() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestData() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.appID.eq(qApplication.appID));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestDataProjection(qTestData.testDataID, qTestData.appID, qTestData.testDataDescription,
					qTestData.status, qTestData.createdBy, qTestData.createdDateTime, qTestData.updatedBy, qTestData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getTestDataById(int)
	 */
	@Override
	public TestData getTestDataById(int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.testDataID.eq(testDataId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestDataProjection(qTestData.testDataID, qTestData.appID, qTestData.testDataDescription,
					qTestData.status, qTestData.createdBy, qTestData.createdDateTime, qTestData.updatedBy, qTestData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getAppIdByAppName(int)
	 */
	@Override
	public int getAppIdByAppName(int appID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppIdByAppName(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.appID.eq(appID));
			LOG.info("generated query : " + sqlQuery);
			Integer testDataID = template.queryForObject(sqlQuery, qTestData.testDataID);
			if (testDataID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getAppIdByAppName(int) - end"); //$NON-NLS-1$
				}
				return testDataID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAppIdByAppName(int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getTestDataIdByAppID(int)
	 */
	@Override
	public int getTestDataIdByAppID(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataIdByAppID(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.appID.eq(appId));
			LOG.info("TestData query : " + sqlQuery);
			Integer testDataID = template.queryForObject(sqlQuery, qTestData.testDataID);
			if (testDataID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestDataIdByAppID(int) - end"); //$NON-NLS-1$
				}
				return testDataID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataIdByAppID(int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestDataDao#insertTestDataGetKey(com.exilant
	 * .tfw.pojo.input.TestData)
	 */
	@Override
	public int insertTestDataGetKey(final TestData testData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestDataGetKey(TestData) - start"); //$NON-NLS-1$
		}

		int testDataId = 0;
		try {
			testDataId = template.insertWithKey(qTestData, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qTestData.testDataID, qTestData.appID, qTestData.testDataDescription, qTestData.status, qTestData.createdBy,
									qTestData.createdDateTime, qTestData.updatedBy, qTestData.updatedDateTime)
							.values(testData.getTestDataID(), testData.getAppID(), testData.getTestDataDescription(), testData.getStatus(),
									testData.getCreatedBy(), testData.getCreatedDateTime(), testData.getUpdatedBy(), testData.getUpdatedDateTime())
							.executeWithKey(qTestData.testDataID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testDataId : " + testDataId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestDataGetKey(TestData) - end"); //$NON-NLS-1$
		}
		return testDataId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.TestDataDao#getTestDataDescriptionFilterByAppId
	 * (int)
	 */
	@Override
	public List<TestData> getTestDataDescriptionFilterByAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDescriptionFilterByAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.appID.eq(appId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestDataProjection(qTestData.testDataID, qTestData.appID, qTestData.testDataDescription,
					qTestData.status, qTestData.createdBy, qTestData.createdDateTime, qTestData.updatedBy, qTestData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getTestDataWithParamCount(int)
	 */
	@Override
	public List<TestData> getTestDataWithParamCount(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataWithParamCount(int) - start"); //$NON-NLS-1$
		}

		List<TestData> resultTestDatas = new ArrayList<TestData>();
		List<TestData> testDatas = this.getTestDataDescriptionFilterByAppId(appId);
		Iterator<TestData> iterator = testDatas.iterator();
		while (iterator.hasNext()) {
			TestData testData = (TestData) iterator.next();
			List<TestParamData> testParamDatas = this.testParamDataDao.getTestParamDataByTestDataId(testData.getTestDataID());
			int count = testParamDatas.size();
			testData.setParamCount(count);
			resultTestDatas.add(testData);
		}
		testDatas = null;

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataWithParamCount(int) - end"); //$NON-NLS-1$
		}
		return resultTestDatas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getDataDescriptionById(int)
	 */
	public String getDataDescriptionById(int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataDescriptionById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.testDataID.eq(testDataId));
			LOG.info("Generated Query : " + sqlQuery);
			return template.queryForObject(sqlQuery, qTestData.testDataDescription);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#getTestDatabyAppId(int)
	 */
	@Override
	public List<TestData> getTestDatabyAppId(int appId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDatabyAppId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData).where(qTestData.appID.eq(appId));
			;
			LOG.info("Generated Query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestDataProjection(qTestData.testDataID, qTestData.appID, qTestData.testDataDescription,
					qTestData.status, qTestData.createdBy, qTestData.createdDateTime, qTestData.updatedBy, qTestData.updatedDateTime));
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.TestDataDao#
	 * getTestDataIdByAppIDAndDataSetDescription(int, java.lang.String)
	 */
	@Override
	public int getTestDataIdByAppIDAndDataSetDescription(int appId, String dataSetDesc) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataIdByAppIDAndDataSetDescription(int, String) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestData)
					.where((qTestData.appID.eq(appId)).and(qTestData.testDataDescription.eq(dataSetDesc)));
			;
			LOG.info("Generated Query : " + sqlQuery);
			Integer testDataID = template.queryForObject(sqlQuery, qTestData.testDataID);
			if (testDataID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestDataIdByAppIDAndDataSetDescription(int, String) - end"); //$NON-NLS-1$
				}
				return testDataID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataIdByAppIDAndDataSetDescription(int, String) - end"); //$NON-NLS-1$
		}
		return 0;
	}

}