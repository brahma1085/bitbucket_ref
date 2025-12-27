package edu.dao;

public class StudentDao {
	private StudentDao() {
	}

	private static StudentDao studentDao;

	public static StudentDao getInstance() {
		if (studentDao != null)
			return studentDao;
		else {
			studentDao = new StudentDao();
			return studentDao;
		}
	}

	public void insertStudent() {
		System.out.println("StudentDao.insertStudent()");
	}

	public void updateStudent() {
		System.out.println("StudentDao.updateStudent()");
	}
}