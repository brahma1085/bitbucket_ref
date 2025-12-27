package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.TestParamDataDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QParam;
import com.exilant.tfw.generated.pojo.QTestdata;
import com.exilant.tfw.generated.pojo.QTestparamdata;
import com.exilant.tfw.pojo.input.TestParamData;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestParamDataDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestParamDataDao
 * interface
 */
public class TestParamDataDaoImpl implements TestParamDataDao {

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

	/** The q test param data. */
	private QTestparamdata qTestParamData = QTestparamdata.testparamdata;

	/** The q test data. */
	private QTestdata qTestData = QTestdata.testdata;

	/** The q param. */
	private QParam qParam = QParam.param;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestParamDataDaoImpl.class);

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
	 * The Class MappingTestParamDataProjection.
	 */
	public class MappingTestParamDataProjection extends MappingProjection<TestParamData> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test param data projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestParamDataProjection(Expression<?>... args) {
			super(TestParamData.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestParamData map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestParamData testParamData = new TestParamData();
			testParamData.setTestParamDataID(tuple.get(qTestParamData.testParamDataID));
			testParamData.setTestDataID(tuple.get(qTestParamData.testDataID));
			testParamData.setParamID(tuple.get(qTestParamData.paramID));
			testParamData.setParamValue(tuple.get(qTestParamData.paramValue));
			testParamData.setValueBig(tuple.get(qTestParamData.valueBig));
			testParamData.setCreatedBy(tuple.get(qTestParamData.createdBy));
			testParamData.setCreatedDateTime(tuple.get(qTestParamData.createdDateTime));
			testParamData.setUpdatedBy(tuple.get(qTestParamData.updatedBy));
			testParamData.setUpdatedDateTime(tuple.get(qTestParamData.updatedDateTime));
			testParamData.setParamGroupID(tuple.get(qTestParamData.paramGroupID));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning Conditions object : " + testParamData);
			}
			return testParamData;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestParamData(final TestParamData testParamData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamData(TestParamData) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestParamData: " + testParamData);
		try {
			result = template.insert(qTestParamData, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause.columns(qTestParamData.testParamDataID, qTestParamData.testDataID, qTestParamData.paramID, qTestParamData.paramValue, qTestParamData.valueBig, qTestParamData.createdBy, qTestParamData.createdDateTime, qTestParamData.updatedBy, qTestParamData.updatedDateTime, qTestParamData.paramGroupID).values(testParamData.getTestParamDataID(), testParamData.getTestDataID(), testParamData.getParamID(), testParamData.getParamValue(), testParamData.getValueBig(), testParamData.getCreatedBy(), testParamData.getCreatedDateTime(), testParamData.getUpdatedBy(), testParamData.getUpdatedDateTime(), testParamData.getParamGroupID()).execute();
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
			LOG.debug("insertTestParamData(TestParamData) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestParamData(final TestParamData testParamData) throws DataAccessException {
		LOG.info("Started updating data in TestParamData: " + testParamData);
		try {
			long returnlong = template.update(qTestParamData, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestParamData.testParamDataID.eq(testParamData.getTestParamDataID())).set(qTestParamData.paramValue, testParamData.getParamValue()).set(qTestParamData.valueBig, testParamData.getValueBig()).set(qTestParamData.updatedBy, testParamData.getUpdatedBy()).set(qTestParamData.updatedDateTime, testParamData.getUpdatedDateTime()).set(qTestParamData.paramGroupID, testParamData.getParamGroupID()).set(qTestParamData.paramID, testParamData.getParamID()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}
			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestParamData(TestParamData) - end"); //$NON-NLS-1$
			}
			return returnlong;
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/**
	 * Gets the test param data by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the test param data by query
	 */
	private List<TestParamData> getTestParamDataByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<TestParamData> returnList = template.query(sqlQuery, new MappingTestParamDataProjection(qTestParamData.testParamDataID, qTestParamData.testDataID, qTestParamData.paramID, qTestParamData.paramValue, qTestParamData.valueBig, qTestParamData.createdBy, qTestParamData.createdDateTime, qTestParamData.updatedBy, qTestParamData.updatedDateTime, qTestParamData.paramGroupID));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestParamDataDao#getTestParamData()
	 */
	@Override
	public List<TestParamData> getTestParamData() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamData() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestParamData).where(qTestParamData.testDataID.eq(qTestData.testDataID));
			LOG.info("Generated Query : " + sqlQuery);
			return getTestParamDataByQuery(sqlQuery);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestParamDataDao#getTestParamDataById(int)
	 */
	@Override
	public TestParamData getTestParamDataById(int testParamDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestParamData).where(qTestParamData.testParamDataID.eq(testParamDataId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestParamDataProjection(qTestParamData.testParamDataID, qTestParamData.testDataID,
					qTestParamData.paramID, qTestParamData.paramValue, qTestParamData.valueBig, qTestParamData.createdBy, qTestParamData.createdDateTime,
					qTestParamData.updatedBy, qTestParamData.updatedDateTime, qTestParamData.paramGroupID));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestParamDataDao#getTestParamDataByTestDataId
	 * (int)
	 */
	@Override
	public List<TestParamData> getTestParamDataByTestDataId(int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataByTestDataId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qTestParamData).where(qTestParamData.testDataID.eq(testDataId))
				.orderBy(qTestParamData.testDataID.asc());
		LOG.info("Generated Query : " + sqlQuery);
		return getTestParamDataByQuery(sqlQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestParamDataDao#getTestParamDataByParamId(int,
	 * int, int)
	 */
	@Override
	public TestParamData getTestParamDataByParamId(int paramId, int paramGrpId, int testDataId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataByParamId(int, int, int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template.newSqlQuery().from(qTestParamData)
				.where(qTestParamData.paramID.eq(paramId).and(qTestParamData.testDataID.eq(testDataId)));
		LOG.info("Generated Query : " + sqlQuery);
		return template.queryForObject(sqlQuery, new MappingTestParamDataProjection(qTestParamData.testParamDataID, qTestParamData.testDataID,
				qTestParamData.paramID, qTestParamData.paramValue, qTestParamData.valueBig, qTestParamData.createdBy, qTestParamData.createdDateTime,
				qTestParamData.updatedBy, qTestParamData.updatedDateTime, qTestParamData.paramGroupID));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestParamDataDao#getTestParamDataIDByIDs(int,
	 * int, int)
	 */
	@Override
	public int getTestParamDataIDByIDs(int paramID, int paramGroupID, int testDataID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataIDByIDs(int, int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestParamData)
					.where(qTestParamData.paramID.eq(paramID).and(qTestParamData.testDataID.eq(testDataID)));
			LOG.info("TestParamData query : " + sqlQuery);
			Integer testParamDataID = template.queryForObject(sqlQuery, qTestParamData.testParamDataID);
			if (testParamDataID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestParamDataIDByIDs(int, int, int) - end"); //$NON-NLS-1$
				}
				return testParamDataID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataIDByIDs(int, int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestParamDataDao#insertTestParamDataGetKey(
	 * com.exilant.tfw.pojo.input.TestParamData)
	 */
	@Override
	public int insertTestParamDataGetKey(final TestParamData testParamData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamDataGetKey(TestParamData) - start"); //$NON-NLS-1$
		}

		int testParamDataId = 0;
		try {
			testParamDataId = template.insertWithKey(qTestParamData, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestParamData.testParamDataID, qTestParamData.testDataID, qTestParamData.paramID, qTestParamData.paramValue, qTestParamData.valueBig, qTestParamData.createdBy, qTestParamData.createdDateTime, qTestParamData.updatedBy, qTestParamData.updatedDateTime, qTestParamData.paramGroupID).values(testParamData.getTestParamDataID(), testParamData.getTestDataID(), testParamData.getParamID(), testParamData.getParamValue(), testParamData.getValueBig(), testParamData.getCreatedBy(), testParamData.getCreatedDateTime(), testParamData.getUpdatedBy(), testParamData.getUpdatedDateTime(), testParamData.getParamGroupID()).executeWithKey(qTestParamData.testParamDataID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated testParamDataId : " + testParamDataId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestParamDataGetKey(TestParamData) - end"); //$NON-NLS-1$
		}
		return testParamDataId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestParamDataDao#
	 * getTestParamDataIDByParaMIDAndTestDataIDAndParamValue
	 * (com.exilant.tfw.pojo.input.TestParamData)
	 */
	@Override
	public int getTestParamDataIDByParaMIDAndTestDataIDAndParamValue(TestParamData testParamData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataIDByParaMIDAndTestDataIDAndParamValue(TestParamData) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qTestParamData)
					.where(qTestParamData.paramID.eq(testParamData.getParamID()).and(qTestParamData.testDataID.eq(testParamData.getTestDataID()))
							.and(qTestParamData.paramValue.eq(testParamData.getParamValue())));
			LOG.info("TestParam Data query : " + sqlQuery);
			Integer testParamDataID = template.queryForObject(sqlQuery, qTestParamData.testParamDataID);
			if (testParamDataID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestParamDataIDByParaMIDAndTestDataIDAndParamValue(TestParamData) - end"); //$NON-NLS-1$
				}
				return testParamDataID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataIDByParaMIDAndTestDataIDAndParamValue(TestParamData) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestParamDataDao#
	 * getTestParamDataIDByParaMIDAndTestDataIDAndValueBig
	 * (com.exilant.tfw.pojo.input.TestParamData)
	 */
	@Override
	public int getTestParamDataIDByParaMIDAndTestDataIDAndValueBig(TestParamData testParamData) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataIDByParaMIDAndTestDataIDAndValueBig(TestParamData) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qTestParamData)
					.where(qTestParamData.paramID.eq(testParamData.getParamID()).and(qTestParamData.testDataID.eq(testParamData.getTestDataID()))
							.and(qTestParamData.valueBig.eq(testParamData.getValueBig())));
			LOG.info("TestParam Data query : " + sqlQuery);
			Integer testParamDataID = template.queryForObject(sqlQuery, qTestParamData.testParamDataID);
			if (testParamDataID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestParamDataIDByParaMIDAndTestDataIDAndValueBig(TestParamData) - end"); //$NON-NLS-1$
				}
				return testParamDataID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataIDByParaMIDAndTestDataIDAndValueBig(TestParamData) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestParamDataDao#getTestParamDataCount()
	 */
	public long getTestParamDataCount() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamDataCount() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestParamData).from(qParam).where(qTestParamData.paramID.eq(qParam.paramID));
			LOG.info("TestParam Data query : " + sqlQuery);
			return template.count(sqlQuery);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
}