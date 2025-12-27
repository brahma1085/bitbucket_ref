package administratorServer;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AdministratorHome extends EJBHome
{
	//public AdministratorRemote create(String uid) throws RemoteException, CreateException;
	public AdministratorRemote create() throws RemoteException, CreateException;
	
}

