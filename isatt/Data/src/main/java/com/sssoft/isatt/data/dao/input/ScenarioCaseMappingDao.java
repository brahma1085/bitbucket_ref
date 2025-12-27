package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;

/**
 * The Interface ScenarioCaseMappingDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ScenarioCaseMapping records by interacting with the
 * database
 */
public interface ScenarioCaseMappingDao {

	/**
	 * Insert scenario case mapping get key.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertScenarioCaseMappingGetKey(ScenarioCaseMapping scenarioCaseMapping) throws DataAccessException;

	/**
	 * Insert scenario case mapping dao.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertScenarioCaseMappingDao(ScenarioCaseMapping scenarioCaseMapping) throws DataAccessException;

	/**
	 * Update scenario case mapping dao.
	 * 
	 * @param scenarioCaseMapping
	 *            the scenario case mapping
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateScenarioCaseMappingDao(ScenarioCaseMapping scenarioCaseMapping) throws DataAccessException;

	/**
	 * Gets the scenario case mappings by scenario id.
	 * 
	 * @param scenarioId
	 *            the scenario id
	 * @return the scenario case mappings by scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ScenarioCaseMapping> getScenarioCaseMappingsByScenarioId(int scenarioId) throws DataAccessException;

	/**
	 * Gets the scenario case map by test scenario id.
	 * 
	 * @param testScenarioId
	 *            the test scenario id
	 * @param testCaseId
	 *            the test case id
	 * @return the scenario case map by test scenario id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getScenarioCaseMapByTestScenarioId(int testScenarioId, int testCaseId) throws DataAccessException;
	
	long getScenarioCaseMappingsByScenarioIdCount(int scenarioId) throws DataAccessException;
	
	long getScenarioCaseMappingsByCaseIdCount(int caseId) throws DataAccessException;

	List<ScenarioCaseMapping> getScenarioCaseMappingsByCaseId(int caseId) throws DataAccessException;

	long deleteScenarioCaseMappingById(int scenarioId)
			throws DataAccessException;

	long deleteScenarioCaseMappingByCaseId(int caseId)
			throws DataAccessException;
}