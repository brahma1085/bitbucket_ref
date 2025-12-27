package edu.main;

import edu.dao.StudentDao;

public class WhyExceptioHandling {
	public static void main(String[] args) {
		try {
			StudentDao studentDao = new StudentDao();
			studentDao.insertStudent(3, "N@IT");
			System.out.println("forward to succes.jsp");
		} catch (Exception e) {
			System.out
					.println("if any exception occur forward to failure.jsp while propagating the user defined exception"
							+ e.getClass().getName());
		}
	}
}
