package edu.factory;

import edu.interfaces.AbstractEmployee;
import edu.interfaces.AbstractStudent;

public interface AbstractFactory {
	AbstractStudent createStudent();

	AbstractEmployee createEmployee();
}
