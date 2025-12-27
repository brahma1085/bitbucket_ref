package edu.map.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.map.pojo.Student;

public class MapTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

	public static void main(String[] args) {
		Student student = (Student) context.getBean("student1");
		Map map = student.getMap();
		Set set = map.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

}
