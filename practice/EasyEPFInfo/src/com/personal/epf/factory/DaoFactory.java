package com.personal.epf.factory;

import com.personal.epf.dao.EmployeeDetailsDao;
import com.personal.epf.dao.impl.EmployeeDetailsDaoImpl;

/**
 * @author brahma
 * 
 *         A factory for creating Dao objects.
 */
public class DaoFactory {

	/** The employee details dao. */
	private static EmployeeDetailsDao employeeDetailsDao = new EmployeeDetailsDaoImpl();

	/**
	 * Gets the employee details dao.
	 * 
	 * @return the employee details dao
	 */
	public static EmployeeDetailsDao getEmployeeDetailsDao() {
		return employeeDetailsDao;
	}

}
