package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_merge {
	public static void main(String[] args) {
		Student student = new Student();
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		session.get(Student.class, 1);
		Student student2 = new Student();
		student2.setStudentNo(1);
		student2.setStudentName("N@IT-UP");
		session.merge(student);
		session.merge(student2);
		transaction.commit();
	}
}
