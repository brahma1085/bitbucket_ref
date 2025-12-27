package edu.test;

import edu.factory.AbstractFactory;
import edu.factory.FactoryImpl;

public class AbstractFactoryTest {
	private static AbstractFactory factory;

	public static void main(String[] args) {
		factory = new FactoryImpl();
		System.out.println("student number==>"
				+ factory.createStudent().getStudentNo());
		System.out.println("student name==>"
				+ factory.createStudent().getStudentName());
		System.out.println("employee number==>"
				+ factory.createEmployee().getEmpNo());
		System.out.println("employee name==>"
				+ factory.createEmployee().getEmpName());
	}
}