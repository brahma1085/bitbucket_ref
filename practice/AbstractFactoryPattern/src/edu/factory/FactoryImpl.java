package edu.factory;

import edu.concrete.EmployeeImpl1;
import edu.concrete.StudentImpl1;
import edu.interfaces.AbstractEmployee;
import edu.interfaces.AbstractStudent;

public class FactoryImpl implements AbstractFactory {
	AbstractStudent student;
	AbstractEmployee employee;

	@Override
	public AbstractEmployee createEmployee() {
		if (employee == null)
			employee = new EmployeeImpl1();
		return employee;
	}

	@Override
	public AbstractStudent createStudent() {
		if (student == null)
			student = new StudentImpl1();
		return student;
	}

}
