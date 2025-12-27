package backOfficeServer;


import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface BackOfficeHome extends EJBHome
{
	public BackOfficeRemote create()throws RemoteException,CreateException;
}
