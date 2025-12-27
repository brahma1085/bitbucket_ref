package edu.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mappings.Coursedemo;
import edu.mappings.Studentdemo;
import edu.utils.HibernateSessionFactory;

public class OneToManyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Studentdemo studentdemo = new Studentdemo();
			Studentdemo studentdemo2 = new Studentdemo();
			Studentdemo studentdemo3 = new Studentdemo();
			Studentdemo studentdemo4 = new Studentdemo();
			Coursedemo coursedemo = new Coursedemo();
			studentdemo.setSname("anand");
			studentdemo2.setSname("krishna");
			studentdemo3.setSname("ramu");
			studentdemo4.setSname("radha");
			coursedemo.setCname("MCA");
			Set list = new HashSet();
			list.add(studentdemo);
			list.add(studentdemo2);
			list.add(studentdemo3);
			list.add(studentdemo4);
			coursedemo.setStudentdemos(list);
			session.save(coursedemo);
			transaction.commit();
			System.out.println("success");
		} catch (Exception e) {
			transaction.rollback();
			System.out.println("failure");
		}
	}
}
