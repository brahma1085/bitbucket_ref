package com.eris.services;

import com.eris.exceptions.ServiceException;
import com.eris.model.Client;

public interface ClientService {
	boolean insertClientDetails(Client client) throws ServiceException;
}
