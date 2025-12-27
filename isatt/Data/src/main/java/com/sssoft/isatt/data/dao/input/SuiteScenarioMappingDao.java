package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;

/**
 * The Interface SuiteScenarioMappingDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides SuiteScenarioMapping records by interacting with the
 * database
 */
public interface SuiteScenarioMappingDao {

	/**
	 * Insert suite scenario mapping get key.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertSuiteScenarioMappingGetKey(SuiteScenarioMapping suiteScenarioMapping) throws DataAccessException;

	/**
	 * Insert suite scenario mapping dao.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertSuiteScenarioMappingDao(SuiteScenarioMapping suiteScenarioMapping) throws DataAccessException;

	/**
	 * Update suite scenario mapping dao.
	 * 
	 * @param suiteScenarioMapping
	 *            the suite scenario mapping
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateSuiteScenarioMappingDao(SuiteScenarioMapping suiteScenarioMapping) throws DataAccessException;

	/**
	 * Gets the suite scenario map by test suite id.
	 * 
	 * @param scenarioId
	 *            the scenario id
	 * @param suiteId
	 *            the suite id
	 * @return the suite scenario map by test suite id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getSuiteScenarioMapByTestSuiteId(int scenarioId, int suiteId) throws DataAccessException;

	/**
	 * Gets the suite scenario mapping by suite id.
	 * 
	 * @param suiteId
	 *            the suite id
	 * @return the suite scenario mapping by suite id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<SuiteScenarioMapping> getSuiteScenarioMappingBySuiteId(int suiteId) throws DataAccessException;

	/**
	 * Gets the suite scenario map by dependent ids.
	 * 
	 * @param scenarioId
	 *            the scenario id
	 * @param suiteId
	 *            the suite id
	 * @return the suite scenario map by dependent ids
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getSuiteScenarioMapByDependentIds(int scenarioId, int suiteId) throws DataAccessException;
	
	long getSuiteScenarioMappingsBySuiteIdCount(int suiteId)
			throws DataAccessException;
	
	long getSuiteScenarioMappingsByScenarioIdCount(int scenarioId) throws DataAccessException;

	List<SuiteScenarioMapping> getSuiteScenarioMappingByScenarioId(int scenarioId) throws DataAccessException;

	long deleteSuiteScenarioMappingById(int scenarioId)
			throws DataAccessException;
	
	long deleteSuiteScenarioMappingBySuiteId(int suiteId)
			throws DataAccessException;
}
