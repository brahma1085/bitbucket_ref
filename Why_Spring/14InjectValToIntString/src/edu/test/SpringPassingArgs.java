package edu.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringPassingArgs {
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"spring-config.xml");

	public static void main(String[] args) {
		Integer sno = (Integer) context.getBean("sno");
		System.out.println("sno==>" + sno);
		String sname = (String) context.getBean("sname");
		System.out.println("sname==>" + sname);
	}

}
