package edu.main;

import org.hibernate.Session;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_delete {
	public static void main(String[] args) {
		Student student = new Student();
		student.setStudentNo(167);
		student.setStudentName("N@IT");
		Session session = HibernateSessionFactory.getSession();
		org.hibernate.Transaction transaction = session.beginTransaction();
		session.delete(student);
		transaction.commit();
	}
}
