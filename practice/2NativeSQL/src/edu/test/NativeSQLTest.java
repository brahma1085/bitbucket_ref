package edu.test;

import java.util.Iterator;
import java.util.List;

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
			String queryString = "SELECT * FROM STUDENTS students";
			SQLQuery sqlQuery = session.createSQLQuery(queryString);
			List studentsList = sqlQuery.list();
			System.out.println("BP");
			Iterator iterator = studentsList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				System.out.println("student number==>" + objects[0]);
				System.out.println("student name==>" + objects[1]);
			}
			transaction.commit();
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
