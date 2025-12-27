package edu.test;

import edu.dao.EmployeeDao;

public class EmployeeDaoTest {

	public static void main(String[] args) {
		EmployeeDao employeeDao = new EmployeeDao();
		employeeDao.insertEmployee(1, "N@IT");
		System.out.println("SUCCESS");
	}
}