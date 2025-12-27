package edu.test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Students;

public class NativeSQLTest {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "SELECT {students.*} FROM STUDENTS students";
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.addEntity("students", edu.mapping.Students.class);
			List studentsList = sqlQuery.list();
			Iterator iterator = studentsList.iterator();
			while (iterator.hasNext()) {
				Students object = (Students) iterator.next();
				System.out.println("student number==>" + object.getStudentNo());
				System.out.println("student name==>" + object.getStudentName());
			}
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
