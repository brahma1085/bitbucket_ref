package edu.main;

import edu.dao.StudentDao;

public class StudentDaoMain {

	public static void main(String[] args) {
		StudentDao studentDao1 = new StudentDao();
		studentDao1.insertStudent();
		studentDao1.updateStudent();
		StudentDao studentDao2 = new StudentDao();
		studentDao2.insertStudent();
		studentDao2.updateStudent();
		System.out.println("StudentDao 1 =" + studentDao1);
		System.out.println("StudentDao 2 =" + studentDao2);
	}

}
