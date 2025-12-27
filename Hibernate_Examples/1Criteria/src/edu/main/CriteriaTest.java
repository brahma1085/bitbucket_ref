package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Students;

public class CriteriaTest {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			// code snippet - start
			Object obj = session.createCriteria(Students.class).setProjection(
					Projections.sum("studentNo")).uniqueResult();
			System.out.println("sum ==> " + obj);// code snippet - end
		} catch (HibernateException e) {
			System.out.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			transaction.rollback();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
