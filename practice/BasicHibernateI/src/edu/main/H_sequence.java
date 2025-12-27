package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mapping.Student;
import edu.util.HibernateSessionFactory;

public class H_sequence {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Student student = new Student();
			//student.setStudentNo("1");
			student.setStudentName("N@IT");
			session.save(student);
		} catch (HibernateException e) {
			System.err
					.println("HibernateException==>" + e.getClass().getName());
		}
		transaction.commit();
	}
}
