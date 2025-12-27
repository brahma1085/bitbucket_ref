package edu.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class SpringSpringDataSource {

	public static void main(String[] args) throws SQLException {
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(
				"spring-config.xml"));
		System.out.println("bean factory related class==>" + beanFactory);
		DataSource dataSource = (DataSource) beanFactory.getBean("dataSource");
		Connection connection = dataSource.getConnection();
		System.out.println("connection related class==>" + connection);
	}

}
