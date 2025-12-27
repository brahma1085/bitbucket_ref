package edu.factories;

import edu.services.StudentService;
import edu.services.StudentServiceImpl;

public class ServiceFactory {
	private static StudentService studentService = new StudentServiceImpl();

	private ServiceFactory() {
	}

	public static StudentService getStudentService() {
		return studentService;
	}

}
