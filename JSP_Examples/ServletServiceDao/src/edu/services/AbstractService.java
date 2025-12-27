package edu.services;

import edu.exceptions.ServiceException;
import edu.models.Details;

public interface AbstractService {
	public boolean pushDetails(Details details) throws ServiceException;
}
