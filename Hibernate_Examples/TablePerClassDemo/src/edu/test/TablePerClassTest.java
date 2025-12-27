package edu.test;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.model.MBAStream;
import edu.model.MCAStream;
import edu.utils.HibernateSessionFactory;

public class TablePerClassTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MCAStream mcaStream = new MCAStream("anand", "Computers");
		MBAStream mbaStream = new MBAStream("krishna", "Marketting");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			session.save(mcaStream);
			session.save(mbaStream);
			transaction.commit();
			System.out.println("success");
		} catch (Exception e) {
			transaction.rollback();
			System.out.println("failure");
		}
	}

}
