package edu.main;

import org.hibernate.Session;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_saveOrUpdate {
	public static void main(String[] args) {
		Student student = new Student();
		student.setStudentNo(89);
		student.setStudentName("Harish-UU");
		Session session = HibernateSessionFactory.getSession();
		org.hibernate.Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(student);
		transaction.commit();
	}
}
