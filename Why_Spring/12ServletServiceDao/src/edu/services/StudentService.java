package edu.services;

import edu.exceptions.ServiceException;
import edu.model.StudentDemo1;

public interface StudentService {
	boolean insertStudentDemo1(StudentDemo1 studentDemo1) throws ServiceException;
}
