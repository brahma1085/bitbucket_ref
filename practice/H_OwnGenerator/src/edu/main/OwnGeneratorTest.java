package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class OwnGeneratorTest {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Student student = new Student();
			student.setStudentName("N@IT");
			session.save(student);
		} catch (HibernateException e) {
			System.err.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
		transaction.commit();
	}
}
