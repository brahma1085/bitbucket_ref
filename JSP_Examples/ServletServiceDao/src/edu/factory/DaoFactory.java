package edu.factory;

import edu.daos.StudentDao;
import edu.daos.StudentDaoImpl;

public class DaoFactory {
	private static StudentDao dao = new StudentDaoImpl();

	private DaoFactory() {
	}

	public static StudentDao getAbstractDao() {
		return dao;
	}
}
