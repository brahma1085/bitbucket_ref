package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mapping.Student;
import edu.utils.HibernateSessionFactory;

public class BasicHibernateTest {

	public static void main(String[] args) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		Student student = (Student) session.load(Student.class, 2);
		System.out.println("2nd record student name:"
				+ student.getStudentName());
		tx.commit();
	}

}
