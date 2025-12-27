package edu.set.test;

import java.util.Iterator;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.set.pojo.Student;

public class SetTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring-config.xml");

	public static void main(String[] args) {
		Student student = (Student) context.getBean("student3");
		Set set = student.getSet();
		String[] strings = student.getStrings();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		for (String s : strings) {
			System.out.println(s);
		}
	}
}
