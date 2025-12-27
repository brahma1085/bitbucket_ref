package loansOnDepositServer;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;


public interface LoansOnDepositHome extends EJBHome
{
	public LoansOnDepositRemote create() throws RemoteException, CreateException;
	
}