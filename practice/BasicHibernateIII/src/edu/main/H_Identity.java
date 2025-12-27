package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mapping.Students;
import edu.util.HibernateSessionFactory;

public class H_Identity {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Students students = new Students();
			//students.setStudentNo(1);
			students.setStudentName("N@IT");
			session.save(students);
		} catch (HibernateException e) {
			System.err.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
		transaction.commit();
	}
}
