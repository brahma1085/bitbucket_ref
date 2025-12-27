package edu.main;

import edo.factory.DaoFactory;
import edu.dao.StudentDao;

public class StudentDaoMain {
	public static void main(String[] args) {
		StudentDao studentDao1 = DaoFactory.getStudentDao();
		studentDao1.insertStudent();
		studentDao1.updateStudent();
		StudentDao studentDao2 = DaoFactory.getStudentDao();
		System.out.println("studentDao1" + studentDao1);
		System.out.println("studentDao2" + studentDao2);
	}
}
