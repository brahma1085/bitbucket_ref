package edu.designs;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.nit.ejb2.AddHome;
import com.nit.ejb2.AddRemote;

import edu.exceptions.BusinessDeligateException;
import edu.exceptions.ServiceLocatorException;

public class BuisnessDeligate1 {
	private static final String ADD_JNDI = "add";
	AddRemote remote;

	public BuisnessDeligate1() throws BusinessDeligateException {
		try {
			AddHome home = (AddHome) ServiceLocator1.getLocator1().getEjbHome(
					BuisnessDeligate1.ADD_JNDI);
			remote = home.create();
		} catch (ServiceLocatorException e) {
			throw new BusinessDeligateException();
		} catch (RemoteException e) {
			throw new BusinessDeligateException();
		} catch (CreateException e) {
			throw new BusinessDeligateException();
		}
	}

	public int add(int a, int b) throws BusinessDeligateException {
		int c = 0;
		try {
			c = remote.add(a, b);
		} catch (RemoteException e) {
			throw new BusinessDeligateException();
		}
		return c;
	}
}
