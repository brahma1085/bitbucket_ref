package edu.daos;

import edu.exceptions.StudentsExceptions;
import edu.models.Students;

public interface StudentsDao {
	boolean insertStudents(Students students) throws StudentsExceptions;
}
