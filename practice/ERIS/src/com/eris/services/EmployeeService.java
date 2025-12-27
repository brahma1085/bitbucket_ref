package com.eris.services;

import java.util.List;

import com.eris.exceptions.ServiceException;
import com.eris.model.Employee;

public interface EmployeeService {
	boolean insertEmployeeDetails(Employee employee) throws ServiceException;

	List retrieveClientid() throws ServiceException;

	long retrieveEmpid() throws ServiceException;

}
