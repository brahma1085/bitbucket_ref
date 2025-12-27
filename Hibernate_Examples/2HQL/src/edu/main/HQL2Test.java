package edu.main;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;

public class HQL2Test {

	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			// code snippet-start
			Query query = session
					.createQuery("UPDATE Student12 SET studentName=? WHERE studentNo=:sn");
			query.setString(0, "N@IT-UP");
			query.setString("sn", "6");
			int num = query.executeUpdate();
			System.out.println("number of records updated==>" + num);
			// code snippet-end
			transaction.commit();
		} catch (HibernateException e) {
			System.out.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			transaction.rollback();
		}
	}
}
