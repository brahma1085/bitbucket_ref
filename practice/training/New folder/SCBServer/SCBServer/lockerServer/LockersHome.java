package lockerServer;

import java.rmi.RemoteException;
import javax.ejb.CreateException;

public interface LockersHome extends javax.ejb.EJBHome
{
	public LockersRemote create() throws RemoteException, CreateException;
	
	
}