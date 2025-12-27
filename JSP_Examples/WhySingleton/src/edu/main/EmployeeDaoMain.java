package edu.main;

import edu.dao.EmployeeDao;

public class EmployeeDaoMain {

	public static void main(String[] args) {
		EmployeeDao employeeDao1 = EmployeeDao.getInstance();
		employeeDao1.insertEmployee();
		employeeDao1.updateEmployee();
		EmployeeDao employeeDao2 = EmployeeDao.getInstance();
		employeeDao2.insertEmployee();
		employeeDao2.updateEmployee();
		System.out.println("EmployeeDao 1 =" + employeeDao1);
		System.out.println("EmployeeDao 2 =" + employeeDao2);
	}

}
