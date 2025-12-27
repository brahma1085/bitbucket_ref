package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mapping.Student;
import edu.util.HibernateSessionFactory;

public class H_IdnetityForeignSelect {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Student student = new Student();
			//student.setStudentNo(new Long(9));
			student.setStudentName("N@IT");
			session.save(student);
		} catch (HibernateException e) {
			System.err.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
		transaction.commit();
	}
}
