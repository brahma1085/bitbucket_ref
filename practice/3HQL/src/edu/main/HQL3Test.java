package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;

public class HQL3Test {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			String hqlQuery = "INSERT INTO Students"
					+ "(studentNo,studentName)"
					+ "SELECT student.studentNo,student.studentName"
					+ " FROM Student12 student";
			Query query = session.createQuery(hqlQuery);
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			transaction.rollback();
		}
	}
}
