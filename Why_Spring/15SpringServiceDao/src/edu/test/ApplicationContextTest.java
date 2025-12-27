package edu.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.service.Service;

public class ApplicationContextTest {
	private static org.springframework.context.ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring-config.xml");

	public static void main(String[] args) {
		Service service = (Service) context.getBean("service");
		service.serviceMethod();
	}

}
