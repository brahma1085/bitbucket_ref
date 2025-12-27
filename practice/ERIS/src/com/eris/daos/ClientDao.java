package com.eris.daos;

import com.eris.exceptions.DaoException;
import com.eris.model.Client;

public interface ClientDao {
	boolean insertClientDetails(Client client) throws DaoException;
}
