package com.eris.factories;

import com.eris.daos.ClientDao;
import com.eris.daos.ClientDaoImpl;
import com.eris.daos.EmployeeDao;
import com.eris.daos.EmployeeDaoImpl;
import com.eris.daos.LoginDao;
import com.eris.daos.LoginDaoImpl;

public class DaoFactory {
	private static LoginDao loginDao = new LoginDaoImpl();
	private static ClientDao clientDao = new ClientDaoImpl();
	private static EmployeeDao employeeDao = new EmployeeDaoImpl();

	private DaoFactory() {
	}

	public static LoginDao getLoginDao() {
		return loginDao;
	}

	public static ClientDao getClientDao() {
		return clientDao;
	}

	public static EmployeeDao getEmployeeDao() {
		return employeeDao;
	}
}
