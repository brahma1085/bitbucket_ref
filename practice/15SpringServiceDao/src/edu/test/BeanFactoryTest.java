package edu.test;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import edu.service.Service;

public class BeanFactoryTest {
	private static BeanFactory factory = new XmlBeanFactory(
			new ClassPathResource("spring-config.xml"));

	public static void main(String[] args) {
		Service service=(Service)factory.getBean("service");
		service.serviceMethod();
	}
}
