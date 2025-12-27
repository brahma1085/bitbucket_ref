package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_save {
	public static void main(String[] args) {
		Student student = new Student();
		Session session = HibernateSessionFactory.getSession();
		student.setStudentNo(167);
		student.setStudentName("Harish-U");
		Transaction tx = session.beginTransaction();
		// Object identifier = session.save(student);
		session.persist(student);
		// System.out
		// .println("identifier===========================" + identifier);
		tx.commit();
	}

}
