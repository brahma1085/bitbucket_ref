package edu.dao;

import java.util.List;

import edu.exceptions.StudentException;
import edu.model.Students;

public interface StudentDao {
	List<Students> loadAllStudents() throws StudentException;
}
