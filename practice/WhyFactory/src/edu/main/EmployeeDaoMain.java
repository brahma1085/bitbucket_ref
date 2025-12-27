package edu.main;

import edo.factory.DaoFactory;
import edu.dao.EmployeeDao;

public class EmployeeDaoMain {
	public static void main(String... args) {
		EmployeeDao employeeDao1 = DaoFactory.getEmployeeDao();
		employeeDao1.insertEmployee();
		employeeDao1.updateEmployee();
		EmployeeDao employeeDao2 = DaoFactory.getEmployeeDao();
		System.out.println("EmployeeDao1" + employeeDao1);
		System.out.println("EmployeeDao2" + employeeDao2);
	}
}
