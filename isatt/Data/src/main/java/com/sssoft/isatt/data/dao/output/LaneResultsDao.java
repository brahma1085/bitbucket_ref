package com.sssoft.isatt.data.dao.output;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.output.LaneResults;

/**
 * The Interface LaneResultsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides LaneResults records by interacting with the database
 */
public interface LaneResultsDao {

	/**
	 * Insert lane results get key.
	 * 
	 * @param laneResults
	 *            the lane results
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertLaneResultsGetKey(LaneResults laneResults) throws DataAccessException;

	/**
	 * Insert lane results.
	 * 
	 * @param laneResults
	 *            the lane results
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertLaneResults(LaneResults laneResults) throws DataAccessException;

	/**
	 * Update lane results.
	 * 
	 * @param laneResults
	 *            the lane results
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateLaneResults(LaneResults laneResults) throws DataAccessException;

	/**
	 * This method fetches all rows from LaneResults table,.
	 * 
	 * @return LaneResults list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<LaneResults> getLaneResults() throws DataAccessException;

	/**
	 * Gets the lane results by id.
	 * 
	 * @param laneResultId
	 *            the lane result id
	 * @return the lane results by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	LaneResults getLaneResultsById(int laneResultId) throws DataAccessException;
}
