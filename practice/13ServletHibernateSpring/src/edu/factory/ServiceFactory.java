package edu.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.service.StudentService;

public class ServiceFactory {
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"spring-config.xml");
	private static String STUDENT_SERVICE = "studentService";

	public static StudentService getStudentService() {
		return (StudentService) applicationContext
				.getBean(ServiceFactory.STUDENT_SERVICE);
	}
}
