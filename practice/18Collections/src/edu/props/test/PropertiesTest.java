package edu.props.test;

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.props.pojo.Student;

public class PropertiesTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring-config.xml");

	public static void main(String[] args) {
		Student student = (Student) context.getBean("student2");
		Properties properties = student.getProperties();
		System.out.println("student number==>" + properties.getProperty("sno"));
		System.out.println("student name==>" + properties.getProperty("sname"));
	}
}
