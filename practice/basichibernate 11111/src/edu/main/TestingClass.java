package edu.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mapping.Students;
import edu.util.HibernateSessionFactory;

public class TestingClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Students students = new Students();
		students.setStudentNo(new Integer(123));
		students.setStudentName("RamojiRao");
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		session.save(students);
		transaction.commit();
	}

}
