package edu.test;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;

public class NativeSQLTest {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "SELECT AVG(SNO) sno FROM STUDENTS";
			SQLQuery sqlQuery = session.createSQLQuery(query);
		//	sqlQuery.addScalar("sno", Hibernate.DOUBLE);
			Object object = sqlQuery.uniqueResult();
			System.out.println("average of student numbers is==>" + object);
			System.out
					.println("class name is==>" + object.getClass().getName());
		} catch (HibernateException e) {
			System.out.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			transaction.rollback();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
			transaction.rollback();
		}
	}
}
