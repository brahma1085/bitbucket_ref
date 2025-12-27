package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_Assigned {
	public static void main(String[] args) {
		Student student = new Student();
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		// student.setStudentNo(41);
		student.setStudentName("N@IT");
		session.save(student);
		transaction.commit();
	}
}