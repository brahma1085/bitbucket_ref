package com.exilant.tfw.dao.impl.input;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;

import com.exilant.tfw.dao.input.CaseStepMappingDao;
import com.exilant.tfw.dao.input.TestStepDao;
import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.generated.pojo.QTeststep;
import com.exilant.tfw.pojo.input.CaseStepMapping;
import com.exilant.tfw.pojo.input.TestStep;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;

/**
 * The Class TestStepDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for TestStepDao interface
 */
public class TestStepDaoImpl implements TestStepDao {

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

	/** The q test step. */
	private QTeststep qTestStep = QTeststep.teststep;

	/** The case step mapping dao. */
	private CaseStepMappingDao caseStepMappingDao;

	/**
	 * Sets the case step mapping dao.
	 * 
	 * @param caseStepMappingDao
	 *            the new case step mapping dao
	 */
	public void setCaseStepMappingDao(CaseStepMappingDao caseStepMappingDao) {
		this.caseStepMappingDao = caseStepMappingDao;
	}

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(TestStepDaoImpl.class);

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
	 * The Class MappingTestStepProjection.
	 */
	public class MappingTestStepProjection extends MappingProjection<TestStep> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping test step projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingTestStepProjection(Expression<?>... args) {
			super(TestStep.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected TestStep map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			TestStep testStep = new TestStep();
			testStep.setTestStepID(tuple.get(qTestStep.testStepID));
			testStep.setStepName(tuple.get(qTestStep.stepName));
			testStep.setDescription(tuple.get(qTestStep.description));
			testStep.setTestStepType(tuple.get(qTestStep.testStepType));
			testStep.setActionID(tuple.get(qTestStep.actionID));
			testStep.setActive(tuple.get(qTestStep.active));
			testStep.setSortOrder(tuple.get(qTestStep.sortOrder));
			testStep.setPreConditionGroupID(tuple.get(qTestStep.preConditionGroupID));
			testStep.setPostConditionGroupID(tuple.get(qTestStep.postConditionGroupID));
			testStep.setInputParamGroupID(tuple.get(qTestStep.inputParamGroupID));
			testStep.setOutputParamGroupID(tuple.get(qTestStep.outputParamGroupID));
			testStep.setRunnerID(tuple.get(qTestStep.runnerID));
			testStep.setExpectedResult(tuple.get(qTestStep.expectedResult));
			testStep.setCreatedBy(tuple.get(qTestStep.createdBy));
			testStep.setCreatedDateTime(tuple.get(qTestStep.createdDateTime));
			testStep.setUpdatedBy(tuple.get(qTestStep.updatedBy));
			testStep.setUpdatedDateTime(tuple.get(qTestStep.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning TestStep object : " + testStep);
			}
			return testStep;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertTestStep(final TestStep testStep) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStep(TestStep) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in TestStep: " + testStep);
		try {
			result = template.insert(qTestStep, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}
					long returnlong = sqlInsertClause.columns(qTestStep.testStepID, qTestStep.stepName, qTestStep.description, qTestStep.testStepType, qTestStep.actionID, qTestStep.active, qTestStep.sortOrder, qTestStep.preConditionGroupID, qTestStep.postConditionGroupID, qTestStep.inputParamGroupID, qTestStep.outputParamGroupID, qTestStep.runnerID, qTestStep.expectedResult, qTestStep.createdBy, qTestStep.createdDateTime, qTestStep.updatedBy, qTestStep.updatedDateTime).values(testStep.getTestStepID(), testStep.getStepName(), testStep.getDescription(), testStep.getTestStepType(), testStep.getActionID(), testStep.getActive(), testStep.getSortOrder(), testStep.getPreConditionGroupID(), testStep.getPostConditionGroupID(), testStep.getInputParamGroupID(), testStep.getOutputParamGroupID(), testStep.getRunnerID(), testStep.getExpectedResult(), testStep.getCreatedBy(), testStep.getCreatedDateTime(), testStep.getUpdatedBy(), testStep.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info(result + " " + "Number of rows inserted in Test Step");
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e.getMessage(), e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStep(TestStep) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateTestStep(final TestStep testStep) throws DataAccessException {
		LOG.info("Started updating data in TestStep: " + testStep);
		try {
			long returnlong = template.update(qTestStep, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qTestStep.testStepID.eq(testStep.getTestStepID())).set(qTestStep.description, testStep.getDescription()).set(qTestStep.stepName, testStep.getStepName()).set(qTestStep.active, String.valueOf(testStep.getActive())).set(qTestStep.sortOrder, testStep.getSortOrder()).set(qTestStep.inputParamGroupID, testStep.getInputParamGroupID()).set(qTestStep.outputParamGroupID, testStep.getOutputParamGroupID()).set(qTestStep.preConditionGroupID, testStep.getPreConditionGroupID()).set(qTestStep.postConditionGroupID, testStep.getPostConditionGroupID()).set(qTestStep.testStepType, testStep.getTestStepType()).set(qTestStep.actionID, testStep.getActionID()).set(qTestStep.expectedResult, testStep.getExpectedResult()).set(qTestStep.updatedBy, testStep.getUpdatedBy()).set(qTestStep.updatedDateTime, testStep.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateTestStep(TestStep) - end"); //$NON-NLS-1$
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
	 * @see com.exilant.tfw.dao.input.TestStepDao#getTestStep()
	 */
	@Override
	public List<TestStep> getTestStep() throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStep() - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStep);
			LOG.info("Generated Query : " + sqlQuery);
			return getTestStepByQuery(sqlQuery);
		} catch (Exception e) {
			LOG.error("Error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestStepDao#getTestStepById(int)
	 */
	@Override
	public TestStep getTestStepById(int testStepId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStep).where(qTestStep.testStepID.eq(testStepId));
			LOG.info("generated query : " + sqlQuery);
			return template.queryForObject(sqlQuery, new MappingTestStepProjection(qTestStep.testStepID, qTestStep.stepName, qTestStep.description,
					qTestStep.testStepType, qTestStep.actionID, qTestStep.active, qTestStep.sortOrder, qTestStep.preConditionGroupID,
					qTestStep.postConditionGroupID, qTestStep.inputParamGroupID, qTestStep.outputParamGroupID, qTestStep.runnerID,
					qTestStep.expectedResult, qTestStep.createdBy, qTestStep.createdDateTime, qTestStep.updatedBy, qTestStep.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestStepDao#getTestStepById(int)
	 */
	@Override
	public List<TestStep> getTestStepByStepsId(int testStepId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepById(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStep).where(qTestStep.testStepID.eq(testStepId));
			LOG.info("generated query : " + sqlQuery);
			return template.query(sqlQuery, new MappingTestStepProjection(qTestStep.testStepID, qTestStep.stepName, qTestStep.description,
					qTestStep.testStepType, qTestStep.actionID, qTestStep.active, qTestStep.sortOrder, qTestStep.preConditionGroupID,
					qTestStep.postConditionGroupID, qTestStep.inputParamGroupID, qTestStep.outputParamGroupID, qTestStep.runnerID,
					qTestStep.expectedResult, qTestStep.createdBy, qTestStep.createdDateTime, qTestStep.updatedBy, qTestStep.updatedDateTime));
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestStepDao#getTestStepsByCaseId(int)
	 */
	@Override
	public List<TestStep> getTestStepsByCaseId(int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepsByCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStep).orderBy(qTestStep.testStepID.asc());
			LOG.info("generated query : " + sqlQuery);
			return getTestStepByQuery(sqlQuery);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}
	}

	/**
	 * Gets the test step by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the test step by query
	 */
	private List<TestStep> getTestStepByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<TestStep> returnList = template.query(sqlQuery, new MappingTestStepProjection(qTestStep.testStepID, qTestStep.stepName, qTestStep.description, qTestStep.testStepType, qTestStep.actionID, qTestStep.active, qTestStep.sortOrder, qTestStep.preConditionGroupID, qTestStep.postConditionGroupID, qTestStep.inputParamGroupID, qTestStep.outputParamGroupID, qTestStep.runnerID, qTestStep.expectedResult, qTestStep.createdBy, qTestStep.createdDateTime, qTestStep.updatedBy, qTestStep.updatedDateTime));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestStepDao#insertTestStepGetKey(com.exilant
	 * .tfw.pojo.input.TestStep)
	 */
	@Override
	public int insertTestStepGetKey(final TestStep testStep) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepGetKey(TestStep) - start"); //$NON-NLS-1$
		}

		int testStepId = 0;
		try {
			testStepId = template.insertWithKey(qTestStep, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert.columns(qTestStep.testStepID, qTestStep.stepName, qTestStep.description, qTestStep.testStepType, qTestStep.actionID, qTestStep.active, qTestStep.sortOrder, qTestStep.preConditionGroupID, qTestStep.postConditionGroupID, qTestStep.inputParamGroupID, qTestStep.outputParamGroupID, qTestStep.runnerID, qTestStep.expectedResult, qTestStep.createdBy, qTestStep.createdDateTime, qTestStep.updatedBy, qTestStep.updatedDateTime).values(testStep.getTestStepID(), testStep.getStepName(), testStep.getDescription(), testStep.getTestStepType(), testStep.getActionID(), testStep.getActive(), testStep.getSortOrder(), testStep.getPreConditionGroupID(), testStep.getPostConditionGroupID(), testStep.getInputParamGroupID(), testStep.getOutputParamGroupID(), testStep.getRunnerID(), testStep.getExpectedResult(), testStep.getCreatedBy(), testStep.getCreatedDateTime(), testStep.getUpdatedBy(), testStep.getUpdatedDateTime()).executeWithKey(qTestStep.testStepID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated genericDataId : " + testStepId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertTestStepGetKey(TestStep) - end"); //$NON-NLS-1$
		}
		return testStepId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestStepDao#getTestStepIDByStepNameAndTestCaseID
	 * (java.lang.String, int)
	 */
	@Override
	public int getTestStepIDByStepNameAndTestCaseID(String testStepName, int caseID) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepIDByStepNameAndTestCaseID(String, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qTestStep);
			LOG.info("generated query : " + sqlQuery);
			Integer testStepID = template.queryForObject(sqlQuery, qTestStep.testStepID);
			if (testStepID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestStepIDByStepNameAndTestCaseID(String, int) - end"); //$NON-NLS-1$
				}
				return testStepID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepIDByStepNameAndTestCaseID(String, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestStepDao#getTestStepIdsByDependents(com.
	 * exilant.tfw.pojo.input.TestStep)
	 */
	@Override
	public int getTestStepIdsByDependents(TestStep testStep) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepIdsByDependents(TestStep) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template
					.newSqlQuery()
					.from(qTestStep)
					.where(qTestStep.stepName.eq(testStep.getStepName()).and(
							qTestStep.actionID.eq(testStep.getActionID()).and(qTestStep.inputParamGroupID.eq(testStep.getInputParamGroupID()))))
					.orderBy(qTestStep.testStepID.asc());
			LOG.info("generated query : " + sqlQuery);
			Integer testStepID = template.queryForObject(sqlQuery, qTestStep.testStepID);
			if (testStepID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getTestStepIdsByDependents(TestStep) - end"); //$NON-NLS-1$
				}
				return testStepID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestStepIdsByDependents(TestStep) - end"); //$NON-NLS-1$
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exilant.tfw.dao.input.TestStepDao#deleteDuplicateData(int, int)
	 */
	@Override
	public long deleteDuplicateData(final int startIndexId, final int lastIndexId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteDuplicateData(int, int) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("deleting duplicate teststep records from the id : " + startIndexId + "  to the id : " + lastIndexId);
		try {
			result = template.delete(qTestStep, new SqlDeleteCallback() {

				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - start"); //$NON-NLS-1$
					}

					long returnlong = delete.where(qTestStep.testStepID.between(startIndexId, lastIndexId)).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlDeleteCallback.doInSqlDeleteClause(SQLDeleteClause) - end"); //$NON-NLS-1$
					}
					return returnlong;
				}
			});
			LOG.info("number of rows deleted : " + result);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteDuplicateData(int, int) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.exilant.tfw.dao.input.TestStepDao#getAllTestStepsByTestCaseId(int)
	 */
	@Override
	public List<TestStep> getAllTestStepsByTestCaseId(int testCaseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllTestStepsByTestCaseId(int) - start"); //$NON-NLS-1$
		}

		try {
			List<TestStep> resultTestSteps = new ArrayList<TestStep>();
			List<CaseStepMapping> caseStepMappings = this.caseStepMappingDao.getCaseStepMappingsByCaseId(testCaseId);
			Iterator<CaseStepMapping> iterator = caseStepMappings.iterator();
			while (iterator.hasNext()) {
				CaseStepMapping caseStepMappings2 = (CaseStepMapping) iterator.next();
				resultTestSteps.add(this.getTestStepById(caseStepMappings2.getTestStepID()));
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("getAllTestStepsByTestCaseId(int) - end"); //$NON-NLS-1$
			}
			return resultTestSteps;
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

	}

}