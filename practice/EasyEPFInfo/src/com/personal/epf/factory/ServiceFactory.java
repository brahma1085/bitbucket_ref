package com.personal.epf.factory;

import com.personal.epf.service.EmployeeDetailsService;
import com.personal.epf.service.impl.EmployeeDetailsServiceImpl;

/**
 * @author brahma.
 * 
 *         A factory for creating Service objects.
 */
public class ServiceFactory {

	/** The employee details service. */
	private static EmployeeDetailsService employeeDetailsService = new EmployeeDetailsServiceImpl();

	/**
	 * Gets the employee details service.
	 * 
	 * @return the employee details service
	 */
	public static EmployeeDetailsService getEmployeeDetailsService() {
		return employeeDetailsService;
	}

}
