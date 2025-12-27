package com.eris.daos;

import java.util.List;

import com.eris.exceptions.DaoException;
import com.eris.model.Employee;

public interface EmployeeDao {
	List retrieveClientid() throws DaoException;

	boolean insertEmployeeDetails(Employee employee) throws DaoException;

	long retrieveEmpid() throws DaoException;
}
