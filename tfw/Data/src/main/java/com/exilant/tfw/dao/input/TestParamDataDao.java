package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.TestParamData;

/**
 * The Interface TestParamDataDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides TestParamData records by interacting with the
 * database
 */
public interface TestParamDataDao {

	/**
	 * Insert test param data get key.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertTestParamDataGetKey(TestParamData testParamData) throws DataAccessException;

	/**
	 * Insert test param data.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertTestParamData(TestParamData testParamData) throws DataAccessException;

	/**
	 * Update test param data.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateTestParamData(TestParamData testParamData) throws DataAccessException;

	/**
	 * This method fetches all rows from TestParamData table,.
	 * 
	 * @return TestParamData list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestParamData> getTestParamData() throws DataAccessException;

	/**
	 * Gets the test param data by id.
	 * 
	 * @param testParamDataId
	 *            the test param data id
	 * @return the test param data by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestParamData getTestParamDataById(int testParamDataId) throws DataAccessException;

	/**
	 * Gets the test param data by test data id.
	 * 
	 * @param testDataId
	 *            the test data id
	 * @return the test param data by test data id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<TestParamData> getTestParamDataByTestDataId(int testDataId) throws DataAccessException;

	/**
	 * Gets the test param data by param id.
	 * 
	 * @param paramId
	 *            the param id
	 * @param paramGrpId
	 *            the param grp id
	 * @param testDataId
	 *            the test data id
	 * @return the test param data by param id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	TestParamData getTestParamDataByParamId(int paramId, int paramGrpId, int testDataId) throws DataAccessException;

	/**
	 * Gets the test param data id by i ds.
	 * 
	 * @param paramID
	 *            the param id
	 * @param paramGroupID
	 *            the param group id
	 * @param testDataID
	 *            the test data id
	 * @return the test param data id by i ds
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestParamDataIDByIDs(int paramID, int paramGroupID, int testDataID) throws DataAccessException;

	/**
	 * Gets the test param data id by para mid and test data id and param value.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the test param data id by para mid and test data id and param
	 *         value
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestParamDataIDByParaMIDAndTestDataIDAndParamValue(TestParamData testParamData) throws DataAccessException;

	/**
	 * Gets the test param data id by para mid and test data id and value big.
	 * 
	 * @param testParamData
	 *            the test param data
	 * @return the test param data id by para mid and test data id and value big
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getTestParamDataIDByParaMIDAndTestDataIDAndValueBig(TestParamData testParamData) throws DataAccessException;

	/**
	 * Gets the test param data count.
	 * 
	 * @return the test param data count
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long getTestParamDataCount() throws DataAccessException;
}
