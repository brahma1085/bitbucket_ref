package edu.factory;

import edu.dao.StudentDao;
import edu.dao.StudentDaoImpl;

public class DaoFactory {
	private static StudentDao studentDao = new StudentDaoImpl();

	public static StudentDao getStudentDao() {
		return studentDao;
	}
}
