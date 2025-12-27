package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_update1 {
	public static void main(String[] args) {
		Student student = new Student();
		student.setStudentNo(167);
		student.setStudentName("N@IT--UU");
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		session.update(student);
		transaction.commit();
	}
}
