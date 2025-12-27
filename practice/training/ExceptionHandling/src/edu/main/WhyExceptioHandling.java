package edu.main;

import edu.dao.StudentDao;
import edu.exceptions.StudentException;

public class WhyExceptioHandling {
	public static void main(String[] args) {
		StudentDao studentDao = new StudentDao();
		try {
			studentDao.insertStudent(5, "N@IT");
			System.out.println("forward --> success.jsp");
		} catch (StudentException e) {
			System.err.println("StudentException=" + e.getMessage());
		}
	}
}
