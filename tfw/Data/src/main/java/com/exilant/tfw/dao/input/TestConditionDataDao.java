package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.TestConditionData;

/**
 * The Interface TestConditionDataDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestConditionData records by interacting with the
 * database
 */
public interface TestConditionDataDao {

	/**
	 * Insert test condition data get key.
	 * 
	 * @param testConditionData
	 *            the test condition data
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestConditionDataGetKey(TestConditionData testConditionData) throws DataAccessException;

	/**
	 * Insert test condition data.
	 * 
	 * @param testConditionData
	 *            the test condition data
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestConditionData(TestConditionData testConditionData) throws DataAccessException;

	/**
	 * Update test condition data.
	 * 
	 * @param testConditionData
	 *            the test condition data
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestConditionData(TestConditionData testConditionData) throws DataAccessException;

	/**
	 * This method fetches all rows from TestConditionData table,.
	 * 
	 * @return TestConditionData list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestConditionData> getTestConditionData() throws DataAccessException;

	/**
	 * Gets the test condition data by id.
	 * 
	 * @param testCondtionId
	 *            the test condtion id
	 * @return the test condition data by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestConditionData getTestConditionDataById(int testCondtionId) throws DataAccessException;

	/**
	 * Gets the test condition data by condition group id.
	 * 
	 * @param conditionGroupId
	 *            the condition group id
	 * @return the test condition data by condition group id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestConditionData> getTestConditionDataByConditionGroupId(int conditionGroupId) throws DataAccessException;

	/**
	 * Gets the test condition id by dependent ids.
	 * 
	 * @param testDataId
	 *            the test data id
	 * @param conditionId
	 *            the condition id
	 * @return the test condition id by dependent ids
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestConditionIdByDependentIds(int testDataId, int conditionId) throws DataAccessException;
}
