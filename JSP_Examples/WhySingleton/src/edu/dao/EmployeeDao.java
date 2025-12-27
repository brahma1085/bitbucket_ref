package edu.dao;

public class EmployeeDao {
	private EmployeeDao() {
	}

	private static EmployeeDao employeeDao;

	public static EmployeeDao getInstance() {
		if (employeeDao == null)
			employeeDao = new EmployeeDao();
		return employeeDao;
	}
	public void insertEmployee(){
		System.out.println("EmployeeDao.insertEmployee()");
	}
	public void updateEmployee(){
		System.out.println("EmployeeDao.updateEmployee()");
	}
}
