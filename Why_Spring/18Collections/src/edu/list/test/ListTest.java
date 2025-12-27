package edu.list.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.list.pojo.Student;

public class ListTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring-config.xml");

	public static void main(String[] args) {
		Student student = (Student) context.getBean("student");
		System.out.println(student.getList());
		String[] strings = student.getStrings();
		System.out.println(strings[0] + "   " + strings[1] + "   " + strings[2]
				+ "   " + strings[3]);
	}

}
