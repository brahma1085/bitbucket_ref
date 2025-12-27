package edu.services;

import edu.exceptions.StudentsExceptions;
import edu.models.Students;

public interface StudentService {
	boolean insertStudents(Students students) throws StudentsExceptions;
}
