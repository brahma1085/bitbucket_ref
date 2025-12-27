package com.personal.epf.service;

import com.personal.epf.exceptions.ServiceException;
import com.personal.epf.model.Employee;

/**
 * @author brahma
 * 
 *         The Interface EmployeeDetailsService. Its proxy transaction provider
 */
public interface EmployeeDetailsService {

	/**
	 * Insert employee details and returns the employee id
	 * 
	 * @param employee
	 *            the employee
	 * @return the int
	 * @throws ServiceException
	 *             the service exception
	 */
	int insertEmployeeDetails(Employee employee) throws ServiceException;

	/**
	 * Retrieve employee details based on the employee id
	 * 
	 * @param employee
	 *            id the employee id
	 * @return the employee
	 * @throws ServiceException
	 *             the service exception
	 */
	Employee retrieveEmployeeDetails(int empid) throws ServiceException;

	/**
	 * Calculate epf based on the employee id
	 * 
	 * @param employee
	 *            id the employee id
	 * @return the double
	 * @throws ServiceException
	 *             the service exception
	 */
	double calculateEPF(int empid) throws ServiceException;
}
