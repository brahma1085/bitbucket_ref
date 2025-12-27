package edu.service;

import edu.dao.StudentDao;
import edu.model.Student001;

public class StudentServiceImpl implements StudentService {
	private StudentDao studentDao;

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public void insertStudent(Student001 student001) {
		studentDao.insertStudent(student001);
	}

}
