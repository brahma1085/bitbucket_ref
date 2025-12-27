package com.eris.services;

import com.eris.exceptions.ServiceException;
import com.eris.model.Login;

public interface LoginService {
	boolean insertUserDetails(Login login) throws ServiceException;

	boolean isAuthenticate(Login login) throws ServiceException;
}
