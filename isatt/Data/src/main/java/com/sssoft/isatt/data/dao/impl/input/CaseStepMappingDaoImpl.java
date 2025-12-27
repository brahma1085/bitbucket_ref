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
import com.sssoft.isatt.data.dao.impl.input.SuiteScenarioMappingDaoImpl.MappingSuiteScenarioMappingProjection;
import com.sssoft.isatt.data.dao.input.CaseStepMappingDao;
import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.generated.pojo.QCasestepmapping;
import com.sssoft.isatt.data.generated.pojo.QTestcase;
import com.sssoft.isatt.data.generated.pojo.QTeststep;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestStep;

/**
 * The Class CaseStepMappingDaoImpl.
 *
 * @author mohammedfirdos
 */

/**
 * The Querydsl project provides a framework that let's you write type-safe queries in Java rather than constructing them using strings. 
 * This has several advantages like code completion in your IDE, domain types and properties can be accessed in a type-safe manner reducing the probability of query syntax errors during run-time. 
 * Querydsl has modules that support JPA, JDO, SQL, MongoDB and more. It is the SQL support that is used for the JDBC Extensions project. 
 */

/**
 * This implementation class provides implementation for CaseStepMappingDao
 * interface
 */
public class CaseStepMappingDaoImpl implements CaseStepMappingDao {

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

	/** The q case step mapping. */
	private QCasestepmapping qCaseStepMapping = QCasestepmapping.casestepmapping;

	/** The q test case. */
	private QTestcase qTestCase = QTestcase.testcase;

	/** The q test step. */
	private QTeststep qTestStep = QTeststep.teststep;

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(CaseStepMappingDaoImpl.class);

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
	 * The Class CaseStepMappingProjection.
	 */
	private class CaseStepMappingProjection extends MappingProjection<CaseStepMapping> {

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new case step mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public CaseStepMappingProjection(Expression<?>... args) {
			super(CaseStepMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected CaseStepMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			CaseStepMapping caseStepMapping = new CaseStepMapping();
			caseStepMapping.setCaseStepMappingID(tuple.get(qCaseStepMapping.caseStepMappingID));
			caseStepMapping.setTestCaseID(tuple.get(qCaseStepMapping.testCaseID));
			caseStepMapping.setTestStepID(tuple.get(qCaseStepMapping.testStepID));
			caseStepMapping.setCreatedBy(tuple.get(qCaseStepMapping.createdBy));
			caseStepMapping.setCreatedDateTime(tuple.get(qCaseStepMapping.createdDateTime));
			caseStepMapping.setUpdatedBy(tuple.get(qCaseStepMapping.updatedBy));
			caseStepMapping.setUpdatedDateTime(tuple.get(qCaseStepMapping.updatedDateTime));

			TestCase testCase = new TestCase();
			testCase.setTestCaseID(tuple.get(qTestCase.testCaseID));
			testCase.setClassificationTag(tuple.get(qTestCase.classificationTag));
			testCase.setRunnerID(tuple.get(qTestCase.runnerID));
			testCase.setDescription(tuple.get(qTestCase.description));
			testCase.setActive(tuple.get(qTestCase.active));
			testCase.setPositive(tuple.get(qTestCase.positive));
			testCase.setSortOrder(tuple.get(qTestCase.sortOrder));
			testCase.setCreatedBy(tuple.get(qTestCase.createdBy));
			testCase.setCreatedDateTime(tuple.get(qTestCase.createdDateTime));
			testCase.setUpdatedBy(tuple.get(qTestCase.updatedBy));
			testCase.setUpdatedDateTime(tuple.get(qTestCase.updatedDateTime));
			testCase.setConditionGroupID(tuple.get(qTestCase.conditionGroupID));
			testCase.setCaseName(tuple.get(qTestCase.caseName));
			caseStepMapping.setTestCase(testCase);

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
			caseStepMapping.setTestStep(testStep);

			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SuiteScenarioMapping object : " + caseStepMapping);
			}
			return caseStepMapping;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sssoft.isatt.data.dao.input.CaseStepMappingDao#getCaseStepMappingsByCaseId
	 * (int)
	 */
	@Override
	public List<CaseStepMapping> getCaseStepMappingsByCaseId(int caseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingsByCaseId(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qCaseStepMapping)
				.from(qTestStep)
				.from(qTestCase)
				.where(qCaseStepMapping.testCaseID.eq(caseId).and(qCaseStepMapping.testCaseID.eq(qTestCase.testCaseID))
						.and(qCaseStepMapping.testStepID.eq(qTestStep.testStepID))).orderBy(qCaseStepMapping.caseStepMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.query(sqlQuery, new CaseStepMappingProjection(qCaseStepMapping.caseStepMappingID, qCaseStepMapping.testCaseID,
				qCaseStepMapping.testStepID, qCaseStepMapping.createdBy, qCaseStepMapping.createdDateTime, qCaseStepMapping.updatedBy,
				qCaseStepMapping.updatedDateTime, qTestCase.testCaseID, qTestCase.classificationTag, qTestCase.caseName, qTestCase.runnerID,
				qTestCase.description, qTestCase.active, qTestCase.positive, qTestCase.sortOrder, qTestCase.createdBy, qTestCase.createdDateTime,
				qTestCase.updatedBy, qTestCase.updatedDateTime, qTestCase.conditionGroupID, qTestStep.testStepID, qTestStep.stepName,
				qTestStep.description, qTestStep.testStepType, qTestStep.actionID, qTestStep.active, qTestStep.sortOrder, qTestStep.preConditionGroupID,
				qTestStep.postConditionGroupID, qTestStep.inputParamGroupID, qTestStep.outputParamGroupID, qTestStep.runnerID, qTestStep.expectedResult,
				qTestStep.createdBy, qTestStep.createdDateTime, qTestStep.updatedBy, qTestStep.updatedDateTime));
	}

	/**
	 * The Class MappingCaseStepMappingProjection.
	 */
	public class MappingCaseStepMappingProjection extends MappingProjection<CaseStepMapping> {

		/** Logger for this class. */
		private final Logger LOG = Logger.getLogger(MappingSuiteScenarioMappingProjection.class);

		/** Default serial version id. */
		private static final long serialVersionUID = 1L;

		/**
		 * Instantiates a new mapping case step mapping projection.
		 * 
		 * @param args
		 *            the args
		 */
		public MappingCaseStepMappingProjection(Expression<?>... args) {
			super(CaseStepMapping.class, args);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.mysema.query.types.MappingProjection#map(com.mysema.query.Tuple)
		 */
		@Override
		protected CaseStepMapping map(Tuple tuple) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - start"); //$NON-NLS-1$
			}

			CaseStepMapping caseStepMapping = new CaseStepMapping();
			caseStepMapping.setCaseStepMappingID(tuple.get(qCaseStepMapping.caseStepMappingID));
			caseStepMapping.setTestCaseID(tuple.get(qCaseStepMapping.testCaseID));
			caseStepMapping.setTestStepID(tuple.get(qCaseStepMapping.testStepID));
			caseStepMapping.setCreatedBy(tuple.get(qCaseStepMapping.createdBy));
			caseStepMapping.setCreatedDateTime(tuple.get(qCaseStepMapping.createdDateTime));
			caseStepMapping.setUpdatedBy(tuple.get(qCaseStepMapping.updatedBy));
			caseStepMapping.setUpdatedDateTime(tuple.get(qCaseStepMapping.updatedDateTime));
			if (LOG.isDebugEnabled()) {
				LOG.debug("Returning SuiteScenarioMapping object : " + caseStepMapping);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("map(Tuple) - end"); //$NON-NLS-1$
			}
			return caseStepMapping;
		}
	}

	/**
	 * For inserts we need to call the template's insert method and implement an
	 * SqlInsertCallback to handle the mapping of data from the domain object
	 * values to the insert.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long insertCaseStepMappingDao(final CaseStepMapping caseStepMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMappingDao(CaseStepMapping) - start"); //$NON-NLS-1$
		}

		long result = 0L;
		LOG.info("Started inserting data in CaseStepMapping: " + caseStepMapping);
		try {
			result = template.insert(qCaseStepMapping, new SqlInsertCallback() {

				public long doInSqlInsertClause(SQLInsertClause sqlInsertClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertCallback.doInSqlInsertClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					long returnlong = sqlInsertClause
							.columns(qCaseStepMapping.caseStepMappingID, qCaseStepMapping.testCaseID, qCaseStepMapping.testStepID,
									qCaseStepMapping.createdBy, qCaseStepMapping.createdDateTime, qCaseStepMapping.updatedBy,
									qCaseStepMapping.updatedDateTime)
							.values(caseStepMapping.getCaseStepMappingID(), caseStepMapping.getTestCaseID(), caseStepMapping.getTestStepID(),
									caseStepMapping.getCreatedBy(), caseStepMapping.getCreatedDateTime(), caseStepMapping.getUpdatedBy(),
									caseStepMapping.getUpdatedDateTime()).execute();
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
			LOG.debug("insertCaseStepMappingDao(CaseStepMapping) - end"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Updates are similar to the inserts but we of course call the update
	 * method and implement an SqlUpdateCallback to provide the where clause and
	 * handle the mapping of update parameter values.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @return the long
	 * @throws DataAccessException
	 *             the data access exception
	 */
	@Override
	public long updateCaseStepMappingDao(final CaseStepMapping caseStepMapping) throws DataAccessException {
		LOG.info("Started updating data in CaseStepMapping: " + caseStepMapping);
		try {
			long returnlong = template.update(qCaseStepMapping, new SqlUpdateCallback() {

				public long doInSqlUpdateClause(SQLUpdateClause sqlUpdateClause) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - start"); //$NON-NLS-1$
					}

					long returnlong2 = sqlUpdateClause.where(qCaseStepMapping.caseStepMappingID.eq(caseStepMapping.getCaseStepMappingID()))
							.set(qCaseStepMapping.updatedBy, caseStepMapping.getUpdatedBy())
							.set(qCaseStepMapping.updatedDateTime, caseStepMapping.getUpdatedDateTime()).execute();
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlUpdateCallback.doInSqlUpdateClause(SQLUpdateClause) - end"); //$NON-NLS-1$
					}
					return returnlong2;
				}

			});
			if (LOG.isDebugEnabled()) {
				LOG.debug("updateCaseStepMappingDao(CaseStepMapping) - end"); //$NON-NLS-1$
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
	 * com.sssoft.isatt.data.dao.input.CaseStepMappingDao#insertCaseStepMappingGetKey
	 * (com.sssoft.isatt.data.pojo.input.CaseStepMapping)
	 */
	@Override
	public int insertCaseStepMappingGetKey(final CaseStepMapping caseStepMapping) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMappingGetKey(CaseStepMapping) - start"); //$NON-NLS-1$
		}

		int caseStepMappingId = 0;
		try {
			caseStepMappingId = template.insertWithKey(qCaseStepMapping, new SqlInsertWithKeyCallback<Integer>() {
				@Override
				public Integer doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - start"); //$NON-NLS-1$
					}

					Integer returnInteger = insert
							.columns(qCaseStepMapping.caseStepMappingID, qCaseStepMapping.testCaseID, qCaseStepMapping.testStepID,
									qCaseStepMapping.createdBy, qCaseStepMapping.createdDateTime, qCaseStepMapping.updatedBy,
									qCaseStepMapping.updatedDateTime)
							.values(caseStepMapping.getCaseStepMappingID(), caseStepMapping.getTestCaseID(), caseStepMapping.getTestStepID(),
									caseStepMapping.getCreatedBy(), caseStepMapping.getCreatedDateTime(), caseStepMapping.getUpdatedBy(),
									caseStepMapping.getUpdatedDateTime()).executeWithKey(qCaseStepMapping.caseStepMappingID);
					if (LOG.isDebugEnabled()) {
						LOG.debug("$SqlInsertWithKeyCallback<Integer>.doInSqlInsertWithKeyClause(SQLInsertClause) - end"); //$NON-NLS-1$
					}
					return returnInteger;
				}
			});
			LOG.info("Genrated caseStepMappingId : " + caseStepMappingId);
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertCaseStepMappingGetKey(CaseStepMapping) - end"); //$NON-NLS-1$
		}
		return caseStepMappingId;
	}

	/**
	 * Gets the case step mapping by query.
	 * 
	 * @param sqlQuery
	 *            the sql query
	 * @return the case step mapping by query
	 */
	public List<CaseStepMapping> getCaseStepMappingByQuery(SQLQuery sqlQuery) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingByQuery(SQLQuery) - start"); //$NON-NLS-1$
		}

		List<CaseStepMapping> returnList = template.query(sqlQuery, new MappingCaseStepMappingProjection(qCaseStepMapping.caseStepMappingID,
				qCaseStepMapping.testCaseID, qCaseStepMapping.testStepID, qCaseStepMapping.createdBy, qCaseStepMapping.createdDateTime,
				qCaseStepMapping.updatedBy, qCaseStepMapping.updatedDateTime));
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingByQuery(SQLQuery) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sssoft.isatt.data.dao.input.CaseStepMappingDao#
	 * getCaseStepMappingsByCaseIdByStepId(int, int)
	 */
	@Override
	public int getCaseStepMappingsByCaseIdByStepId(int caseId, int stepId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingsByCaseIdByStepId(int, int) - start"); //$NON-NLS-1$
		}

		try {
			SQLQuery sqlQuery = template.newSqlQuery().from(qCaseStepMapping)
					.where(qCaseStepMapping.testCaseID.eq(caseId).and(qCaseStepMapping.testStepID.eq(stepId)));
			Integer caseStepMappingID = template.queryForObject(sqlQuery, qCaseStepMapping.caseStepMappingID);
			if (caseStepMappingID != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("getCaseStepMappingsByCaseIdByStepId(int, int) - end"); //$NON-NLS-1$
				}
				return caseStepMappingID;
			}
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
			throw new DataAccessException(e.getMessage());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingsByCaseIdByStepId(int, int) - end"); //$NON-NLS-1$
		}
		return 0;
	}
	
	@Override
	public long getCaseStepMappingsByCaseIdCount(int caseId) throws DataAccessException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCaseStepMappingsByCaseIdCount(int) - start"); //$NON-NLS-1$
		}

		SQLQuery sqlQuery = template
				.newSqlQuery()
				.from(qCaseStepMapping)
				.from(qTestStep)
				.from(qTestCase)
				.where(qCaseStepMapping.testCaseID.eq(caseId).and(qCaseStepMapping.testCaseID.eq(qTestCase.testCaseID))
						.and(qCaseStepMapping.testStepID.eq(qTestStep.testStepID))).orderBy(qCaseStepMapping.caseStepMappingID.asc());
		LOG.info("generated query : " + sqlQuery);
		return template.count(sqlQuery);
	}
	

}