package edu.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import edu.pojo.Student;

public class InsjectToStudentTest {
	private static BeanFactory factory = new XmlBeanFactory(
			new ClassPathResource("spring-config.xml"));

	public static void main(String[] args) {
		Student student = (Student) factory.getBean("student");
		System.out.println(student.getSno());
		System.out.println(student.getSname());
		String[] strings = (String[]) factory.getAliases("student");
		System.out.println("first alias name==>" + strings[0]
				+ "   second alias name==>" + strings[1]);
		System.out.println(factory.containsBean("student"));
		System.out.println(factory.isSingleton("student"));
		System.out.println(factory.isPrototype("student"));
	}
}
