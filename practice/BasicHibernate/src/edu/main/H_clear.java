package edu.main;

import org.hibernate.Session;

import edu.factory.HibernateSessionFactory;
import edu.mapping.Student;

public class H_clear {
	public static void main(String[] args) {
		Session session = HibernateSessionFactory.getSession();
		Student student = (Student) session.get(Student.class, 1);
		 session.clear();
		//session.evict(student);
		// student.setStudentName("N@IT---uuu");
		// if(session.contains(student))
		// System.out.println(" contains student");
		// student.setStudentNo(1);
		// student.setStudentName("N@IT-U-U-U");
		// Object id = session.getIdentifier(student);
		// System.out.println("identifier coloumn ==>" + id);
		if (session.isOpen())
			System.out.println("session is opened");
		else
			System.out.println("session not opened");
	}
}
