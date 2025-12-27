package edu.daos;

import edu.exceptions.DaoException;
import edu.models.Details;

public interface StudentDao {
	public boolean insertDetails(Details details) throws DaoException;
}
