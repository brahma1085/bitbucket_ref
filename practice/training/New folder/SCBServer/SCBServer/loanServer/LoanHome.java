package loanServer;


import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LoanHome extends EJBHome
{
	LoanRemote create() throws RemoteException,CreateException;
	

}
