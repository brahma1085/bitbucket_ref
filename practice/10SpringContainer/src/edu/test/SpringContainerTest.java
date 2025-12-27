package edu.test;

import edu.dao.StudentDao;
import edu.factory.BeanFactory;
import edu.factory.XMLBeanFactory;

public class SpringContainerTest {
	private static BeanFactory factory = new XMLBeanFactory();

	public static void main(String[] args) {
		StudentDao studentDao = (StudentDao) factory.getBean("studentDao");
		studentDao.daoMethod();
		StudentDao studentDao1 = (StudentDao) factory.getBean("studentDao");
		StudentDao studentDao2 = (StudentDao) factory.getBean("studentDao");
		System.out.println(".studentDao1." + studentDao1);
		System.out.println(".studentDao2." + studentDao2);
	}
}
