package edu.dao;

import edu.exceptions.DaoException;
import edu.model.StudentDemo1;

public interface StudentDao extends AbstractDao{
	public boolean insertStudent(StudentDemo1 studentDemo1) throws DaoException;
}
