package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_Methods {

	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		// Student student = (Student) session.load(Student.class, 3);
		Student student = (Student) session.get(Student.class, 2);
		//session.evict(student);
		// session.replicate(student, ReplicationMode.LATEST_VERSION);
		student.setStudentName("N@IT-U");
		// session.flush();
		// student.setStudentName("N@IT-U-U");
		transaction.commit();
		// if (student != null)
		// System.out.println("student name =====" + student.getStudentName());
		// else
		// System.out.println(".....no records found.....");
		// transaction.commit();
	}
}
