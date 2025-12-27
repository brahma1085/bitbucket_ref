package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.ReplacementStrings;

/**
 * The Interface ReplacementStringsDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides ReplacementStrings records by interacting with the
 * database
 */
public interface ReplacementStringsDao {

	/**
	 * Insert replacement strings get key.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertReplacementStringsGetKey(ReplacementStrings replacementStrings) throws DataAccessException;

	/**
	 * Insert replacement strings.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertReplacementStrings(ReplacementStrings replacementStrings) throws DataAccessException;

	/**
	 * Update replacement strings.
	 * 
	 * @param replacementStrings
	 *            the replacement strings
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateReplacementStrings(ReplacementStrings replacementStrings) throws DataAccessException;

	/**
	 * This method fetches all rows from ReplacementStrings table,.
	 * 
	 * @return ReplacementStrings list
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ReplacementStrings> getReplacementStrings() throws DataAccessException;

	/**
	 * Gets the replacement strings by id.
	 * 
	 * @param replacementId
	 *            the replacement id
	 * @return the replacement strings by id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	ReplacementStrings getReplacementStringsById(int replacementId) throws DataAccessException;

	/**
	 * Gets the replacement strings by app id.
	 * 
	 * @param appId
	 *            the app id
	 * @return the replacement strings by app id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<ReplacementStrings> getReplacementStringsByAppID(int appId) throws DataAccessException;
}
