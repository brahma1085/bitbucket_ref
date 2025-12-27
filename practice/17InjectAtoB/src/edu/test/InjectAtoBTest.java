package edu.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InjectAtoBTest {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	public static void main(String[] args) {
		B b = (B) context.getBean("b1");
		B b3 = (B) context.getBean("b2");
	}

}
