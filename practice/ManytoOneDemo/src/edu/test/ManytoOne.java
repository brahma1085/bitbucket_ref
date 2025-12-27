package edu.test;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mappings.Coursedemo;
import edu.mappings.Studentdemo;
import edu.utils.HibernateSessionFactory;

public class ManytoOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Coursedemo coursedemo = new Coursedemo();
			coursedemo.setCname("M-Tech");
			Coursedemo coursedemo2 = new Coursedemo();
			coursedemo.setCname("B-Tech");
			Studentdemo studentdemo = new Studentdemo();
			studentdemo.setSname("anand");
			Studentdemo studentdemo2 = new Studentdemo();
			studentdemo2.setSname("ramu");
			Studentdemo studentdemo3 = new Studentdemo();
			studentdemo3.setSname("krishna");
			Studentdemo studentdemo4 = new Studentdemo();
			studentdemo4.setSname("anu");
			studentdemo.setCoursedemo(coursedemo);
			studentdemo2.setCoursedemo(coursedemo2);
			studentdemo3.setCoursedemo(coursedemo2);
			studentdemo4.setCoursedemo(coursedemo2);
			session.save(studentdemo);
			session.save(studentdemo2);
			session.save(studentdemo3);
			session.save(studentdemo4);
			transaction.commit();
			System.out.println("success");
		} catch (Exception e) {
			transaction.rollback();
			System.out.println("failed");
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
}