package edu.test;

import edu.dao.StudentDao;

public class StudentDaoTest {
	public static void main(String[] args) {
		StudentDao studentDao = new StudentDao();
		studentDao.insertStudent(5, "N@IT");
		System.out.println("SUCCESS");
	}
}