package com.eris.factories;

import com.eris.services.ClientService;
import com.eris.services.ClientServiceImpl;
import com.eris.services.EmployeeService;
import com.eris.services.EmployeeServiceImpl;
import com.eris.services.LoginService;
import com.eris.services.LoginServiceImpl;

public class ServiceFactory {
	private static LoginService loginService = new LoginServiceImpl();
	private static ClientService clientService = new ClientServiceImpl();
	private static EmployeeService employeeService = new EmployeeServiceImpl();

	private ServiceFactory() {
	}

	public static LoginService getLoginService() {
		return loginService;
	}

	public static ClientService getClientService() {
		return clientService;
	}

	public static EmployeeService getEmployeeService() {
		return employeeService;
	}
}
