package edu.test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Students;

public class NamedQueriesTest {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			// code snippet - start
			Query query = session.getNamedQuery("sqlquery");
			List studentsList = query.list();
			Iterator iterator = studentsList.iterator();
			while (iterator.hasNext()) {
				Students object = (Students) iterator.next();
				System.out.println("student number==>" + object.getStudentNo());
				System.out.println("student name==>" + object.getStudentName());
			}
			// code snippet - end
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
