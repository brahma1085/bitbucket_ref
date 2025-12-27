package edu.factory;

import edu.services.AbstractService;
import edu.services.AbstractServiceImpl;

public class ServiceFactory {
	private static AbstractService service = new AbstractServiceImpl();

	private ServiceFactory() {
	}

	public static AbstractService getAbstractService() {
		return service;
	}
}
