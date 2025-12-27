/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package exceptions;

import java.rmi.RemoteException;

import javax.ejb.EJBException;

/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LockerMemberNotFoundException extends EJBException
{

	public LockerMemberNotFoundException(RemoteException obj_remoteException)
	{
		System.out.println("obj_remoteException == "+obj_remoteException);
	}
	public String toString()
	{
		return "Locker Member Not Found";
	}

}
