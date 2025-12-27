package edu.factory;

import edu.services.StudentService;
import edu.services.StudentServiceImpl;

public class ServiceFactory {
	private static StudentService studentService = new StudentServiceImpl();

	public static StudentService getStudentService() {
		return studentService;
	}
}
