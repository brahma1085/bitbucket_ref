package edu.main;

import org.hibernate.LockMode;
import org.hibernate.Session;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_lock {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Student student = (Student) session.get(Student.class, 1);
		session.lock(student, LockMode.UPGRADE);
		System.out.println("break point and try to change the value in DB");
	}
}
