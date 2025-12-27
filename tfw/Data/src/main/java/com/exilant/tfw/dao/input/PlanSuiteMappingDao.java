package com.exilant.tfw.dao.input;

import java.util.List;

import com.exilant.tfw.exception.DataAccessException;
import com.exilant.tfw.pojo.input.PlanSuiteMapping;

/**
 * The Interface PlanSuiteMappingDao.
 *
 * @author mohammedfirdos
 */

/**
 * This interface provides PlanSuiteMappingDao records by interacting with the
 * database
 */
public interface PlanSuiteMappingDao {

	/**
	 * Insert plan suite mapping dao get key.
	 * 
	 * @param planSuiteMapping
	 *            the plan suite mapping
	 * @return number of rows inserted
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int insertPlanSuiteMappingDaoGetKey(PlanSuiteMapping planSuiteMapping) throws DataAccessException;

	/**
	 * Update plan suite mapping.
	 * 
	 * @param planSuiteMapping
	 *            the plan suite mapping
	 * @return number of rows updated
	 * @throws DataAccessException
	 *             the data access exception
	 */
	long updatePlanSuiteMapping(PlanSuiteMapping planSuiteMapping) throws DataAccessException;

	/**
	 * Gets the plan suite id by plan id by suite id.
	 * 
	 * @param planId
	 *            the plan id
	 * @param suiteId
	 *            the suite id
	 * @return the plan suite id by plan id by suite id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	int getPlanSuiteIdByPlanIdBySuiteId(int planId, int suiteId) throws DataAccessException;

	/**
	 * Gets the plan suite mapping by plan id.
	 * 
	 * @param planId
	 *            the plan id
	 * @return the plan suite mapping by plan id
	 * @throws DataAccessException
	 *             the data access exception
	 */
	List<PlanSuiteMapping> getPlanSuiteMappingByPlanId(int planId) throws DataAccessException;
	
	List<PlanSuiteMapping> getPlanSuiteMappingBySuiteId(int suiteId) throws DataAccessException;

	long deletePlanSuiteMappingById(int planId) throws DataAccessException;
	
	long deletePlanSuiteMappingBySuiteId(int suiteId) throws DataAccessException;
}
