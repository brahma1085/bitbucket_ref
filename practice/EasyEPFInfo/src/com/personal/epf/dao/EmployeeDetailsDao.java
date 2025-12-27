package com.personal.epf.dao;

import com.personal.epf.exceptions.DaoException;
import com.personal.epf.model.Employee;

/**
 * @author brahma
 * 
 *         The Interface EmployeeDetailsDao. Its a proxy which handles all data
 *         base operations
 */
public interface EmployeeDetailsDao {

	/**
	 * Retrieve employee details.
	 * 
	 * @param empid
	 *            the employee id
	 * @return the employee
	 * @throws DaoException
	 *             the dao exception
	 */
	Employee retrieveEmployeeDetails(int empid) throws DaoException;

	/**
	 * Insert employee details.
	 * 
	 * @param employee
	 *            the employee
	 * @return the int format employee id
	 * @throws DaoException
	 *             the dao exception
	 */
	int insertEmployeeDetails(Employee employee) throws DaoException;

	/**
	 * Calculate employee pf.
	 * 
	 * @param empid
	 *            the employee id
	 * @return the double format calculated PF
	 * @throws DaoException
	 *             the dao exception
	 */
	double calculateEmployeePF(int empid) throws DaoException;
}
