package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.TestData;

/**
 * The Interface TestDataDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestData records by interacting with the database
 */
public interface TestDataDao {

	/**
	 * Insert test data get key.
	 * 
	 * @param testData
	 *            the test data
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestDataGetKey(TestData testData) throws DataAccessException;

	/**
	 * Insert test data.
	 * 
	 * @param testData
	 *            the test data
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestData(TestData testData) throws DataAccessException;

	/**
	 * Update test data.
	 * 
	 * @param testData
	 *            the test data
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestData(TestData testData) throws DataAccessException;

	/**
	 * This method fetches all rows from TestData table,.
	 * 
	 * @return TestData list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestData> getTestData() throws DataAccessException;

	/**
	 * Gets the test data by id.
	 * 
	 * @param testDataId
	 *            the test data id
	 * @return the test data by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestData getTestDataById(int testDataId) throws DataAccessException;

	/**
	 * Gets the app id by app name.
	 * 
	 * @param appID
	 *            the app id
	 * @return the app id by app name
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getAppIdByAppName(int appID) throws DataAccessException;

	/**
	 * Gets the test data id by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test data id by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestDataIdByAppID(int appId) throws DataAccessException;

	/**
	 * Gets the test data id by app id and data set description.
	 * 
	 * @param appId
	 *            the app id
	 * @param dataSetDesc
	 *            the data set desc
	 * @return the test data id by app id and data set description
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestDataIdByAppIDAndDataSetDescription(int appId, String dataSetDesc) throws DataAccessException;

	/**
	 * Gets the test data description filter by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test data description filter by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestData> getTestDataDescriptionFilterByAppId(int appId) throws DataAccessException;

	/**
	 * Gets the test databy app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test databy app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestData> getTestDatabyAppId(int appId) throws DataAccessException;

	/**
	 * Gets the data description by id.
	 * 
	 * @param testDataId
	 *            the test data id
	 * @return the data description by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	String getDataDescriptionById(int testDataId) throws DataAccessException;

	/**
	 * Gets the test data with param count.
	 * 
	 * @param appId
	 *            the app id
	 * @return the test data with param count
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestData> getTestDataWithParamCount(int appId) throws DataAccessException;
}
