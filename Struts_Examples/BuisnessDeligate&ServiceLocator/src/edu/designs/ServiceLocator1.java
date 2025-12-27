package edu.designs;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import edu.exceptions.ServiceLocatorException;

public class ServiceLocator1 {
	private InitialContext context;
	private Map map;
	private static ServiceLocator1 locator1=new ServiceLocator1();
	public ServiceLocator1() throws ServiceLocatorException {
		Properties properties = new Properties();
		try {
			properties.load(ServiceLocator1.class.getClassLoader()
					.getResourceAsStream("jndi.properties"));
			context = new InitialContext(properties);
			System.out.println("client connected to naming service");
			map = Collections.synchronizedMap(new HashMap());
		} catch (IOException e) {
			throw new ServiceLocatorException();
		} catch (NamingException e) {
			throw new ServiceLocatorException();
		}
	}

	public static ServiceLocator1 getLocator1() throws ServiceLocatorException {
		return locator1;
	}

	public EJBHome getEjbHome(String jndihomename)
			throws ServiceLocatorException {
		EJBHome home = null;
		try {
			if (map.containsKey(jndihomename))
				home = (EJBHome) map.get(jndihomename);
			else {
				home = (EJBHome) context.lookup(jndihomename);
				map.put(jndihomename, home);
			}
		} catch (NamingException e) {
			throw new ServiceLocatorException();
		} catch (Exception e) {
			throw new ServiceLocatorException();
		}
		return home;
	}
}
