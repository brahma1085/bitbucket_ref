package com.sssoft.isatt.data.dao.input;

import java.util.List;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;

/**
 * The Interface CaseStepMappingDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides CaseStepMapping records by interacting with the
 * database
 */
public interface CaseStepMappingDao {

	/**
	 * Insert case step mapping get key.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @return the int
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertCaseStepMappingGetKey(CaseStepMapping caseStepMapping) throws DataAccessException;

	/**
	 * Insert case step mapping dao.
	 * 
	 * @param CaseStepMapping
	 *            the case step mapping
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long insertCaseStepMappingDao(CaseStepMapping caseStepMapping) throws DataAccessException;

	/**
	 * Update case step mapping dao.
	 * 
	 * @param caseStepMapping
	 *            the case step mapping
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updateCaseStepMappingDao(CaseStepMapping caseStepMapping) throws DataAccessException;

	/**
	 * Gets the case step mappings by case id.
	 * 
	 * @param caseId
	 *            the case id
	 * @return the case step mappings by case id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<CaseStepMapping> getCaseStepMappingsByCaseId(int caseId) throws DataAccessException;

	/**
	 * Gets the case step mappings by case id by step id.
	 * 
	 * @param caseId
	 *            the case id
	 * @param stepId
	 *            the step id
	 * @return the case step mappings by case id by step id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getCaseStepMappingsByCaseIdByStepId(int caseId, int stepId) throws DataAccessException;
	
	long getCaseStepMappingsByCaseIdCount(int caseId) throws DataAccessException;

}