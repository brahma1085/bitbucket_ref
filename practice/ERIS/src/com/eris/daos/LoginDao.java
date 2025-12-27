package com.eris.daos;

import com.eris.exceptions.DaoException;
import com.eris.model.Login;

public interface LoginDao {
	boolean insertUserDetails(Login login) throws DaoException;

	boolean isAuthenticate(Login login) throws DaoException;
}
