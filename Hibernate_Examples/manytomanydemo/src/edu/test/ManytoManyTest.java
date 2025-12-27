package edu.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.mappings.Coursedemo;
import edu.mappings.Studentdemo1;
import edu.utils.HibernateSessionFactory;

public class ManytoManyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			Studentdemo1 studentdemo1 = new Studentdemo1();
			Studentdemo1 studentdemo12 = new Studentdemo1();
			List<Studentdemo1> studentdemo1s = new ArrayList<Studentdemo1>();
			Coursedemo coursedemo = new Coursedemo();
			Coursedemo coursedemo2 = new Coursedemo();
			studentdemo1.setSname("anand");
			studentdemo12.setSname("ram");
			coursedemo.setCname("MBA");
			coursedemo2.setCname("MCA");
			studentdemo1s.add(studentdemo1);
			studentdemo1s.add(studentdemo12);
			coursedemo.setStudentdemo1s(studentdemo1s);
			coursedemo2.setStudentdemo1s(studentdemo1s);
			session.save(coursedemo);
			session.save(coursedemo2);
			transaction.commit();
			System.out.println("success");
		} catch (Exception e) {
			transaction.rollback();
			System.out.println("failed");
		}

	}

}
