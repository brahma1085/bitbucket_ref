package edu.main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;

public class H_getSessionFactory {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		System.out.println("transaction==>" + session.getTransaction());
		SessionFactory sessionFactory = session.getSessionFactory();
		System.out.println("session factory==>" + sessionFactory);
		transaction.rollback();
		if (transaction.isActive())
			System.out.println("transaction is active");
		else
			System.out.println("transaction is not active");
	}
}