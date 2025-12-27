package pygmyServer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

//import pygmyDeposit.AccountNotFoundException;
import exceptions.AccountNotFoundException;

public interface PygmyHome extends EJBHome {
	public PygmyRemote create()throws RemoteException,CreateException;
	//public PygmyRemote create(String actype,int acno,String veuser,String vetml)throws CreateException,RemoteException,AccountNotFoundException;

//fdsiygsh

}
