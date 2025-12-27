package edu.main;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;

public class HQL_1 {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			String hqlQuery = "SELECT COUNT(studentNo), studentName FROM Student GROUP BY studentName";
			List studentList = session.createQuery(hqlQuery).list();
			System.out.println("number of records ==>" + studentList.size());
			Iterator iterator = studentList.iterator();
			Object[] studentObjects = null;
			if (iterator.hasNext()) {
				studentObjects = (Object[]) iterator.next();
				System.out.println("number of records in same group==>"
						+ studentObjects[0]);
				System.out.println("grouped name==>" + studentObjects[1]);
			}
		} catch (HibernateException e) {
			System.err.println("HibernateException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} catch (Exception e) {
			System.err.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
		transaction.commit();
	}
}
