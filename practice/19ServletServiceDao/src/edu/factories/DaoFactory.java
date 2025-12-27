package edu.factories;

import edu.daos.StudentsDao;
import edu.daos.StudentsDaoImpl;

public class DaoFactory {
	private static StudentsDao studentsDao = new StudentsDaoImpl();

	private DaoFactory() {
	}

	public static StudentsDao getStudentsDao() {
		return studentsDao;
	}

}
