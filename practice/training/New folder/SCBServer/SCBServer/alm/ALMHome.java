package alm;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.RemoveException;

public interface ALMHome extends EJBHome {

	ALMRemote create()throws CreateException,RemoteException;
	
	
}
