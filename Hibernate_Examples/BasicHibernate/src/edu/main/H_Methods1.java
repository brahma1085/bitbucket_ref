package edu.main;

import org.hibernate.Session;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_Methods1 {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Student student = (Student) session.get(Student.class, 1);
		System.out.println("student name=====" + student.getStudentName());
		System.out.println("break point and change the db value");
		//session.refresh(student);
		System.out.println("student name====" + student.getStudentName());
	}
}
